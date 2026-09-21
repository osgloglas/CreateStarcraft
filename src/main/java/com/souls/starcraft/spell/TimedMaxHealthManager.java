package com.souls.starcraft.spell;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class TimedMaxHealthManager {
    private static final ResourceLocation WHALE_HEALTH_ID =
        ResourceLocation.fromNamespaceAndPath("starcraft", "balaena_stellaris_health");

    private static final Map<UUID, Long> HEALTH_END_TIMES = new HashMap<>();

    public static void grantMaxHealth(ServerPlayer serverPlayer, int levels, int durationTicks) {
        var attribute = serverPlayer.getAttribute(Attributes.MAX_HEALTH);

        if (attribute == null) {
            return;
        }

        //remove previous modifier
        attribute.removeModifier(WHALE_HEALTH_ID);

        double extraHealth = levels * 2.0;

        attribute.addTransientModifier(new AttributeModifier(WHALE_HEALTH_ID, extraHealth, AttributeModifier.Operation.ADD_VALUE));

        long endTime = serverPlayer.serverLevel().getGameTime() + durationTicks;

        HEALTH_END_TIMES.put(serverPlayer.getUUID(), endTime);
    }

    public static void tick(ServerPlayer serverPlayer) {
        Long endTime = HEALTH_END_TIMES.get(serverPlayer.getUUID());

        if (endTime == null) {
            return;
        }

        if (serverPlayer.serverLevel().getGameTime() >= endTime) {
            HEALTH_END_TIMES.remove(serverPlayer.getUUID());

            var attribute = serverPlayer.getAttribute(Attributes.MAX_HEALTH);

            if (attribute != null) {
                attribute.removeModifier(WHALE_HEALTH_ID);

                if (serverPlayer.getHealth() > serverPlayer.getMaxHealth()) {
                    serverPlayer.setHealth(serverPlayer.getMaxHealth());
                }
            }
        }
    }
}
