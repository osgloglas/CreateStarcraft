package com.souls.starcraft.client;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.joml.Vector3f;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

public class CelestialGatewayClient {
    private static boolean active = false;
    private static float fadeProgress = 0.0F;

    private static final Map<UUID, BlockPos> destinationGateways = new HashMap<>();
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

        Minecraft minecraft = Minecraft.getInstance();

        if (!active || minecraft.player == null || sourceGatewayPos == null) {
            targetedGatewayId = null;
            return;
        }

        Vector3f cameraLook = minecraft.gameRenderer.getMainCamera().getLookVector();

        Vec3 look = new Vec3(cameraLook.x, cameraLook.y, cameraLook.z);

        double bestDot = 0.995;
        UUID bestGateway = null;

        for (Map.Entry<UUID, BlockPos> entry : destinationGateways.entrySet()) {
            BlockPos destinationPos = entry.getValue();

            Vec3 direction = new Vec3(
                destinationPos.getX() - sourceGatewayPos.getX(),
                destinationPos.getY() - sourceGatewayPos.getY(),
                destinationPos.getZ() - sourceGatewayPos.getZ() 
            ).normalize();

            double dot = look.dot(direction);

            if (dot > bestDot) {
                bestDot = dot;
                bestGateway = entry.getKey();
            }
        }

        targetedGatewayId = bestGateway;
    }

    public static void addDestinationGateway(UUID id, BlockPos pos) {
        destinationGateways.put(id, pos);
    }

    public static Map<UUID, BlockPos> getDestinationGateways() {
        return destinationGateways;
    }

    public static void setSourceGatewayPos(BlockPos pos) {
        sourceGatewayPos = pos;
    }

    public static BlockPos getSourceGatewayPos() {
        return sourceGatewayPos;
    }

    public static void clearDestinationGateways() {
        destinationGateways.clear();
    }

    //teleport
    public static UUID targetedGatewayId;

    public static void setTargetedGatewayId(UUID id) {
        targetedGatewayId = id;
    }

    public static UUID getTargetedGatewayId() {
        return targetedGatewayId;
    }

    public static int getTargetedGatewayDistance() {
        if (targetedGatewayId == null || sourceGatewayPos == null) {
            return -1;
        }

        BlockPos destinationPos = destinationGateways.get(targetedGatewayId);

        if (destinationPos == null) {
            return -1;
        }

        return (int) Math.round(Math.sqrt(sourceGatewayPos.distSqr(destinationPos)));
    }
}
