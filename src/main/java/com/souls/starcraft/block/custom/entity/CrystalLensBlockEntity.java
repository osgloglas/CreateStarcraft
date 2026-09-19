package com.souls.starcraft.block.custom.entity;

import com.souls.starcraft.block.ModBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CrystalLensBlockEntity extends BlockEntity {
    public CrystalLensBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CRYSTAL_LENS.get(), pos, state);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, CrystalLensBlockEntity lens) {
        BlockPos belowPos = pos.below();
        BlockState belowState = level.getBlockState(belowPos);

        System.out.println("Block below lens: " + belowState.getBlock());
    }
}
