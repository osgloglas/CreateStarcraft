package com.souls.starcraft.block.custom;

import com.mojang.serialization.MapCodec;
import com.souls.starcraft.block.custom.entity.TelescopeBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TelescopeBlock extends BaseEntityBlock {
    public TelescopeBlock(Properties properties) {
        super(properties);
    }

    @Override 
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    public static final MapCodec<TelescopeBlock> CODEC = simpleCodec(TelescopeBlock::new);

    @Override 
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TelescopeBlockEntity(pos, state);
    }
}
