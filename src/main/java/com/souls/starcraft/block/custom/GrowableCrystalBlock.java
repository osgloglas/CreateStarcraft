package com.souls.starcraft.block.custom;

import com.mojang.serialization.MapCodec;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class GrowableCrystalBlock extends Block {
    public static final MapCodec<GrowableCrystalBlock> CODEC =
        simpleCodec(GrowableCrystalBlock::new);

    public static final IntegerProperty STAGE = IntegerProperty.create("stage", 0, 2);

    public GrowableCrystalBlock(Properties properties) {
        super(properties);

        this.registerDefaultState(this.stateDefinition.any().setValue(STAGE, 0));
    }

    @Override 
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    @Override 
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(STAGE);
    }
}
