package com.souls.starcraft.block.custom;

import javax.annotation.Nullable;

import com.mojang.serialization.MapCodec;
import com.souls.starcraft.block.ModBlockEntities;
import com.souls.starcraft.block.custom.entity.AttunementAltarBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class AttunementAltarBlock extends BaseEntityBlock {
    public static final MapCodec<AttunementAltarBlock> CODEC = simpleCodec(AttunementAltarBlock::new);

    public AttunementAltarBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    private static final VoxelShape BASE = Block.box(0, 0, 0, 16, 4, 16);
    private static final VoxelShape TOP = Block.box(2, 4, 2, 14, 8, 14);

    private static final VoxelShape SHAPE = Shapes.or(BASE, TOP);

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
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override 
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override 
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AttunementAltarBlockEntity(pos, state);
    }

    @Override 
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
        Level level,
        BlockState state,
        BlockEntityType<T> blockEntityType
    ) {
        return createTickerHelper(blockEntityType, ModBlockEntities.ATTUNEMENT_ALTAR.get(), AttunementAltarBlockEntity::tick);
    }

    //temp
    @Override 
    protected InteractionResult useWithoutItem(
        BlockState state,
        Level level,
        BlockPos pos,
        Player player,
        BlockHitResult hitResult
    ) {
        if (!level.isClientSide()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);

            if (blockEntity instanceof AttunementAltarBlockEntity altar) {
                altar.startRitual();
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide());
    }
}
