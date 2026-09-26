package com.souls.starcraft.block.custom.entity;

import java.util.List;

import com.souls.starcraft.block.ModBlockEntities;
import com.souls.starcraft.block.ModBlocks;
import com.souls.starcraft.block.custom.GrowableCrystalBlock;
import com.souls.starcraft.fluid.ModFluids;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class CrystalLensBlockEntity extends BlockEntity {
    public CrystalLensBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CRYSTAL_LENS.get(), pos, state);
    }

    private int growthTimer = 0;

    private boolean crystalGrowing = false;
    private BlockPos growingCrystalPos = null;

    public boolean isCrystalGrowing() {
        return crystalGrowing;
    }

    public BlockPos getGrowingCrystalPos() {
        return growingCrystalPos;
    }

    private void syncGrowthState() {
        setChanged();

        if (level != null && !level.isClientSide()) {
            BlockState state = getBlockState();

            level.sendBlockUpdated(worldPosition, state, state, 3);
        }
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, CrystalLensBlockEntity lens) {
        //growing
        BlockPos checkPos = pos.below();

        while (level.getBlockState(checkPos).is(ModBlocks.LENS_FRAME.get())) {
            checkPos = checkPos.below();
        }

        BlockState targState = level.getBlockState(checkPos);

        //grow an existing crystal
        if (targState.is(ModBlocks.GROWABLE_CRYSTAL.get())) {
            int stage = targState.getValue(GrowableCrystalBlock.STAGE);

            if (stage < 3) {
                if (!lens.crystalGrowing || !checkPos.equals(lens.growingCrystalPos)) {
                    lens.crystalGrowing = true;
                    lens.growingCrystalPos = checkPos;
                    lens.syncGrowthState();
                }

                lens.growthTimer++;

                //speed (every 5s for testing = 100)
                if (lens.growthTimer >= 100) {
                    level.setBlock(checkPos, targState.setValue(GrowableCrystalBlock.STAGE, stage + 1), 3);

                    lens.growthTimer = 0;
                }

                return;
            }
        }

        if (lens.crystalGrowing) {
            lens.crystalGrowing = false;
            lens.growingCrystalPos = null;
            lens.syncGrowthState();
        }

        lens.growthTimer = 0;

        //create baby crystal
        if (!level.getFluidState(checkPos).is(ModFluids.LIQUID_STARLIGHT.get())) {
            return;
        }

        AABB area = new AABB(checkPos);

        List<ItemEntity> items = level.getEntitiesOfClass(ItemEntity.class, area, item -> item.getItem().is(Items.QUARTZ));

        if (items.isEmpty()) {
            return;
        }

        ItemEntity quartzEntity = items.get(0);
        ItemStack quartzStack = quartzEntity.getItem();

        //consume 1 quartz
        quartzStack.shrink(1);

        if (quartzStack.isEmpty()) {
            quartzEntity.discard();
        }

        //replace with baby crystal
        BlockState babyCrystal = ModBlocks.GROWABLE_CRYSTAL.get().defaultBlockState().setValue(GrowableCrystalBlock.STAGE, 0);

        level.setBlock(checkPos, babyCrystal, 3);
    }

    @Override 
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        tag.putBoolean("CrystalGrowing", crystalGrowing);

        if (growingCrystalPos != null) {
            tag.putLong("GrowingCrystalPos", growingCrystalPos.asLong());
        }
    }

    @Override 
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        crystalGrowing = tag.getBoolean("CrystalGrowing");

        if (tag.contains("GrowingCrystalPos")) {
            growingCrystalPos = BlockPos.of(tag.getLong("GrowingCrystalPos"));
        } else {
            growingCrystalPos = null;
        }
    }

    @Override 
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override 
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override 
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider registries) {
        super.onDataPacket(net, pkt, registries);
    }
}
