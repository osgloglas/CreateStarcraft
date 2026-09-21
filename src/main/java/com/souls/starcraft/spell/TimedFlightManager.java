package com.souls.starcraft.spell;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.server.level.ServerPlayer;

public class TimedFlightManager {
    private static final Map<UUID, Long> FLIGHT_END_TIMES = new HashMap<>();

    public static void grantFlight(ServerPlayer serverPlayer, int durationTicks) {
        long endTime = serverPlayer.serverLevel().getGameTime() + durationTicks;

        FLIGHT_END_TIMES.put(serverPlayer.getUUID(), endTime);

        serverPlayer.getAbilities().mayfly = true;
        serverPlayer.onUpdateAbilities();
    }

    public static void tick(ServerPlayer serverPlayer) {
        Long endTime = FLIGHT_END_TIMES.get(serverPlayer.getUUID());

        if (endTime == null) {
            return;
        }

        if (serverPlayer.serverLevel().getGameTime() >= endTime) {
            FLIGHT_END_TIMES.remove(serverPlayer.getUUID());

            if (!serverPlayer.isCreative() && !serverPlayer.isSpectator()) {
                serverPlayer.getAbilities().mayfly = false;
                serverPlayer.getAbilities().flying = false;
                serverPlayer.onUpdateAbilities();
            }
        }
    }
}
