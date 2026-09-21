package com.souls.starcraft.spell;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class TimedReachManager {
    private static final ResourceLocation CUBUS_REACH_ID = ResourceLocation.fromNamespaceAndPath("starcraft", "cubus_reach");

    private static final Map<UUID, Long> REACH_END_TIMES = new HashMap<>();

    public static void grantReach(ServerPlayer serverPlayer, int levels, int durationTicks) {
        var attribute = serverPlayer.getAttribute(Attributes.BLOCK_INTERACTION_RANGE);

        if (attribute == null) {
            return;
        }

        //replace bonus
        attribute.removeModifier(CUBUS_REACH_ID);

        double extraReach = levels * 2.0;

        attribute.addTransientModifier(new AttributeModifier(CUBUS_REACH_ID, extraReach, AttributeModifier.Operation.ADD_VALUE));

        long endTime = serverPlayer.serverLevel().getGameTime() + durationTicks;

        REACH_END_TIMES.put(serverPlayer.getUUID(), endTime);
    }

    public static void tick(ServerPlayer serverPlayer) {
        Long endTime = REACH_END_TIMES.get(serverPlayer.getUUID());

        if (endTime == null) {
            return;
        }

        if (serverPlayer.serverLevel().getGameTime() >= endTime) {
            REACH_END_TIMES.remove(serverPlayer.getUUID());

            var attribute = serverPlayer.getAttribute(Attributes.BLOCK_INTERACTION_RANGE);

            if (attribute != null) {
                attribute.removeModifier(CUBUS_REACH_ID);
            }
        }
    }
}
