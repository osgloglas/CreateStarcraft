package com.souls.starcraft.block.custom.entity;

import com.souls.starcraft.block.ModBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TelescopeBlockEntity extends BlockEntity {
    public TelescopeBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.TELESCOPE_BE.get(), pos, blockState);
    }
}
