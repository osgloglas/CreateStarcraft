package com.souls.starcraft.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class StarlightBasinBlock extends Block {
    public StarlightBasinBlock(Properties properties) {
        super(properties);
    }

    private static final VoxelShape SHAPE = Shapes.or(
        Block.box(2, 0, 2, 14, 2, 14),
        Block.box(0, 2, 0, 16, 4, 16),
        Block.box(0, 4, 0, 16, 16, 2),
        Block.box(0, 4, 14, 16, 16, 16),
        Block.box(0, 4, 2, 2, 16, 14),
        Block.box(14, 4, 2, 16, 16, 14)
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
}
