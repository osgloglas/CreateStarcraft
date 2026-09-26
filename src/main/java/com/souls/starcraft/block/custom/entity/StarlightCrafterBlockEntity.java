package com.souls.starcraft.block.custom.entity;

import com.souls.starcraft.block.ModBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class StarlightCrafterBlockEntity extends BlockEntity {
    public StarlightCrafterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STARLIGHT_CRAFTER.get(), pos, state);
    }
}
