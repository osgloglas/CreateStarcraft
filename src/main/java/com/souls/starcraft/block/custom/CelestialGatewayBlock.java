package com.souls.starcraft.block.custom;

import javax.annotation.Nullable;

import com.mojang.serialization.MapCodec;
import com.souls.starcraft.block.ModBlockEntities;
import com.souls.starcraft.block.custom.entity.CelestialGatewayBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CelestialGatewayBlock extends BaseEntityBlock {
    public static final MapCodec<CelestialGatewayBlock> CODEC = simpleCodec(CelestialGatewayBlock::new);

    public CelestialGatewayBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override 
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CelestialGatewayBlockEntity(pos, state);
    }

    @Override 
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide) {
            return null;
        }

        return createTickerHelper(type, ModBlockEntities.CELESTIAL_GATEWAY.get(), CelestialGatewayBlockEntity::serverTick);
    }
}
