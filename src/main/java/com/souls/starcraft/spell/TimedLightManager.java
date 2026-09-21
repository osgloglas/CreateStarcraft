package com.souls.starcraft.spell;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.souls.starcraft.ModEffects;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Blocks;

public class TimedLightManager {
    private static final Map<UUID, BlockPos> LIGHT_POSITIONS = new HashMap<>();

    public static void tick(ServerPlayer serverPlayer) {
        UUID playerId = serverPlayer.getUUID();
        BlockPos oldPos = LIGHT_POSITIONS.get(playerId);

        if (!serverPlayer.hasEffect(ModEffects.LIGHT_SOURCE)) {
            if (oldPos != null) {
                removeOurLight(serverPlayer, oldPos);
                LIGHT_POSITIONS.remove(playerId);
            }

            return;
        }

        BlockPos newPos = serverPlayer.blockPosition().above();

        //havent moved to new block
        if (newPos.equals(oldPos)) {
            return;
        }

        //remove previous light
        if (oldPos != null) {
            removeOurLight(serverPlayer, oldPos);
        }

        //put light into air
        if (serverPlayer.level().getBlockState(newPos).isAir()) {
            serverPlayer.level().setBlock(newPos, Blocks.LIGHT.defaultBlockState(), 3);

            LIGHT_POSITIONS.put(playerId, newPos);
        } else {
            LIGHT_POSITIONS.remove(playerId);
        }
    }

    private static void removeOurLight(ServerPlayer serverPlayer, BlockPos pos) {
        if (serverPlayer.level().getBlockState(pos).is(Blocks.LIGHT)) {
            serverPlayer.level().removeBlock(pos, false);
        }
    }
}
