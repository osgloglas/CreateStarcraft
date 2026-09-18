package com.souls.starcraft.block.custom.entity;

import com.souls.starcraft.block.ModBlockEntities;
import com.souls.starcraft.fluid.ModFluids;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

public class StarlightBasinBlockEntity extends BlockEntity {
    public StarlightBasinBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STARLIGHT_BASIN.get(), pos, state);
    }

    @Override 
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        tag.put("FluidTank", fluidTank.writeToNBT(registries, new CompoundTag()));
        tag.putBoolean("HasAquamarine", hasAquamarine);
        tag.putInt("AquamarineGenerated", aquamarineGenerated);
    }

    @Override 
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        if (tag.contains("FluidTank")) {
            fluidTank.readFromNBT(registries, tag.getCompound("FluidTank"));
        }

        hasAquamarine = tag.getBoolean("HasAquamarine");
        aquamarineGenerated = tag.getInt("AquamarineGenerated");
    }

    private static final int CAPACITY = 1000;

    private final FluidTank fluidTank = new FluidTank(CAPACITY) {
        @Override 
        public boolean isFluidValid(FluidStack stack) {
            return stack.is(ModFluids.LIQUID_STARLIGHT.get());
        }

        @Override 
        protected void onContentsChanged() {
            setChanged();

            if (level != null && !level.isClientSide()) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
            }
        }
    };

    public FluidTank getFluidTank() {
        return fluidTank;
    }

    //create interaction
    private final IFluidHandler outputHandler = new IFluidHandler() {
        @Override 
        public int getTanks() {
            return fluidTank.getTanks();
        }

        @Override 
        public FluidStack getFluidInTank(int tank) {
            return fluidTank.getFluidInTank(tank);
        }

        @Override 
        public int getTankCapacity(int tank) {
            return fluidTank.getTankCapacity(tank);
        }

        @Override 
        public boolean isFluidValid(int tank, FluidStack stack) {
            return false;
        }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            return 0;
        }

        @Override 
        public FluidStack drain(FluidStack resource, FluidAction action) {
            return fluidTank.drain(resource, action);
        }

        @Override 
        public FluidStack drain(int maxDrain, FluidAction action) {
            return fluidTank.drain(maxDrain, action);
        }
    };

    public IFluidHandler getOutputHandler() {
        return outputHandler;
    }

    //aquamarine
    private boolean hasAquamarine = false;

    public boolean hasAquamarine() {
        return hasAquamarine;
    }

    public boolean insertAquamarine() {
        if (hasAquamarine) {
            return false;
        }

        hasAquamarine = true;
        aquamarineGenerated = 0;

        setChanged();
        
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }

        return true;
    }

    @Override 
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    @Override 
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    //ticking
    private int generationTicks = 0;
    private int aquamarineGenerated = 0;
    
    public static void tick(Level level, BlockPos pos, BlockState state, StarlightBasinBlockEntity basin) {
        if (level.isClientSide()) {
            return;
        }

        if (!basin.hasAquamarine()) {
            basin.generationTicks = 0;
            return;
        }

        if (basin.fluidTank.getFluidAmount() >= CAPACITY) {
            return;
        }

        basin.generationTicks++;

        if (basin.generationTicks >= 240) {
            basin.generationTicks = 0;

            int amount = level.isNight() ? 100 : 50;

            int remainingFromAquamarine = 1000 - basin.aquamarineGenerated;
            int roomInTank = CAPACITY - basin.fluidTank.getFluidAmount();

            int amountToGenerate = Math.min(amount, Math.min(remainingFromAquamarine, roomInTank));

            int filled = basin.fluidTank.fill(new FluidStack(ModFluids.LIQUID_STARLIGHT.get(), amountToGenerate), IFluidHandler.FluidAction.EXECUTE);

            basin.aquamarineGenerated += filled;

            if (basin.aquamarineGenerated >= 1000) {
                basin.hasAquamarine = false;
                basin.aquamarineGenerated = 0;
            }

            basin.setChanged();

            level.sendBlockUpdated(pos, state, state, 3);
        }
    }
}
