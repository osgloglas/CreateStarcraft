package com.souls.starcraft.block.custom;

import javax.annotation.Nullable;

import com.mojang.serialization.MapCodec;
import com.souls.starcraft.block.custom.entity.StarlightCrafterBlockEntity;
import com.souls.starcraft.menu.StarlightCrafterMenu;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class StarlightCrafterBlock extends BaseEntityBlock {
    public StarlightCrafterBlock(Properties properties) {
        super(properties);
    }

    private static final VoxelShape SHAPE = Shapes.or(
        Block.box(1, 0, 1, 15, 2, 15),
        Block.box(3, 2, 3, 13, 4, 13),
        Block.box(5, 4, 5, 11, 9, 11),
        Block.box(0, 9, 0, 16, 12, 16)
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

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(StarlightCrafterBlock::new);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new StarlightCrafterBlockEntity(pos, state);
    }

    @Override 
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            BlockEntity blockEntity = level.getBlockEntity(pos);

            if (blockEntity instanceof StarlightCrafterBlockEntity crafter) {
                serverPlayer.openMenu(new MenuProvider() {
                    @Override 
                    public Component getDisplayName() {
                        return Component.translatable("container.starcraft.starlight_crafter");
                    }

                    @Override 
                    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
                        return new StarlightCrafterMenu(containerId, inventory, crafter);
                    }
                }, buf -> buf.writeBlockPos(pos));
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide());
    }
}
