package com.souls.starcraft.block.custom;

import com.mojang.serialization.MapCodec;
import com.souls.starcraft.block.ModBlockEntities;
import com.souls.starcraft.block.custom.entity.StarlightBasinBlockEntity;
import com.souls.starcraft.item.ModItems;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.fluids.FluidUtil;

public class StarlightBasinBlock extends BaseEntityBlock {
    public static final MapCodec<StarlightBasinBlock> CODEC = simpleCodec(StarlightBasinBlock::new);

    public StarlightBasinBlock(Properties properties) {
        super(properties);
    }

    @Override 
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new StarlightBasinBlockEntity(pos, state);
    }

    @Override 
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    //shape
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

    @Override 
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    //interaction stuffs
    @Override 
    protected ItemInteractionResult useItemOn(
        ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit
    ) {
        if (stack.is(ModItems.AQUAMARINE.get()) && level.getBlockEntity(pos) instanceof StarlightBasinBlockEntity basin) {
            if (!basin.hasAquamarine()) {
                if (!level.isClientSide()) {
                    if (basin.insertAquamarine()) {
                        if (!player.getAbilities().instabuild) {
                            stack.shrink(1);
                        }
                    }
                }

                return ItemInteractionResult.sidedSuccess(level.isClientSide());
            }

            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if (stack.is(Items.BUCKET) 
            && !level.isClientSide() 
            && player instanceof ServerPlayer serverPlayer 
            && level.getBlockEntity(pos) instanceof StarlightBasinBlockEntity basin) {
                boolean transferred = FluidUtil.interactWithFluidHandler(serverPlayer, hand, basin.getFluidTank());

                if (transferred) {
                    return ItemInteractionResult.SUCCESS;
                }
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    //fluid stuff
    @Override 
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, ModBlockEntities.STARLIGHT_BASIN.get(), StarlightBasinBlockEntity::tick);
    }
}
