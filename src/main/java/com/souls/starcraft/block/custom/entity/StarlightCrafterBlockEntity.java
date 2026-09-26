package com.souls.starcraft.block.custom.entity;

import com.souls.starcraft.block.ModBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

public class StarlightCrafterBlockEntity extends BlockEntity {
    private final ItemStackHandler craftingItems = new ItemStackHandler(9) {
        @Override 
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    public StarlightCrafterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STARLIGHT_CRAFTER.get(), pos, state);
    }

    public ItemStackHandler getCraftingItems() {
        return craftingItems;
    }

    @Override 
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        tag.put("CraftingItems", tag);
    }

    @Override 
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        if (tag.contains("CraftingItems")) {
            craftingItems.deserializeNBT(registries, tag.getCompound("CraftingItems"));
        }
    }
}
