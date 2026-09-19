package com.souls.starcraft.client;

import java.util.UUID;

import net.minecraft.core.BlockPos;

public class CelestialGatewayClient {
    private static boolean active = false;
    private static float fadeProgress = 0.0F;

    private static UUID destinationGatewayId;
    private static BlockPos destinationGatewayPos;
    private static BlockPos sourceGatewayPos;

    public static void activate() {
        active = true;
    }

    public static void deactivate() {
        active = false;
    }

    public static boolean isActive() {
        return active;
    }

    public static float getFadeProgress() {
        return fadeProgress;
    }

    public static void tick() {
        float speed = 0.1F;

        if (active) {
            fadeProgress = Math.min(1.0F, fadeProgress + speed);
        } else {
            fadeProgress = Math.max(0.0F, fadeProgress - speed);
        }
    }

    public static void setDestinationGateway(UUID id, BlockPos pos) {
        destinationGatewayId = id;
        destinationGatewayPos = pos;
    }

    public static UUID getDestinationGatewayId() {
        return destinationGatewayId;
    }

    public static BlockPos getDestinationGatewayPos() {
        return destinationGatewayPos;
    }

    public static void setSourceGatewayPos(BlockPos pos) {
        sourceGatewayPos = pos;
    }

    public static BlockPos getSourceGatewayPos() {
        return sourceGatewayPos;
    }
}
