package com.souls.starcraft.spell;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class TimedLightManager {
    private record LightData(long endTime, BlockPos lightPos) {}

    private static final Map<UUID, LightData> ACTIVE_LIGHTS = new HashMap<>();

    public static void grantLight(ServerPlayer serverPlayer, int durationTicks) {
        long endTime = serverPlayer.serverLevel().getGameTime() + durationTicks;

        ACTIVE_LIGHTS.put(serverPlayer.getUUID(), new LightData(endTime, null));
    }

    public static void tick(ServerPlayer serverPlayer) {
        LightData data = ACTIVE_LIGHTS.get(serverPlayer.getUUID());

        if (data == null) {
            return;
        }

        var level = serverPlayer.serverLevel();

        //time up!
        if (level.getGameTime() >= data.endTime()) {
            removeOurLight(serverPlayer, data.lightPos());
            ACTIVE_LIGHTS.remove(serverPlayer.getUUID());
            return;
        }

        BlockPos newPos = serverPlayer.blockPosition().above();

        //havent moved to new block
        if (newPos.equals(data.lightPos())) {
            return;
        }

        //remove previous light
        removeOurLight(serverPlayer, data.lightPos());

        //put light into air
        if (level.getBlockState(newPos).isAir()) {
            level.setBlockAndUpdate(newPos, Blocks.LIGHT.defaultBlockState());

            ACTIVE_LIGHTS.put(serverPlayer.getUUID(), new LightData(data.endTime(), newPos));
        } else {
            ACTIVE_LIGHTS.put(serverPlayer.getUUID(), new LightData(data.endTime(), null));
        }
    }

    private static void removeOurLight(ServerPlayer serverPlayer, BlockPos pos) {
        if (pos == null) {
            return;
        }

        var level = serverPlayer.serverLevel();
        BlockState state = level.getBlockState(pos);

        if (state.is(Blocks.LIGHT)) {
            level.removeBlock(pos, false);
        }
    }
}
