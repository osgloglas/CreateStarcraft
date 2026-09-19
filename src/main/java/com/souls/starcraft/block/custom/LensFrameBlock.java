package com.souls.starcraft.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LensFrameBlock extends Block {
    public LensFrameBlock(Properties properties) {
        super(properties);
    }

    private static final VoxelShape SHAPE = Shapes.or(
        Block.box(0, 0, 0, 2, 16, 2),
        Block.box(14, 0, 0, 16, 16, 2),
        Block.box(14, 0, 14, 16, 16, 16),
        Block.box(0, 0, 14, 2, 16, 16)
    );

    @Override
    protected VoxelShape getShape(
        BlockState state,
        BlockGetter level,
        BlockPos pos,
        CollisionContext context
    ) {
        return SHAPE;
    }

    @Override 
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
}
