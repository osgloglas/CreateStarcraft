package com.souls.starcraft.gateway;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class CelestialGatewayRegistry {
    private static final Map<UUID, GatewayEntry> GATEWAYS = new HashMap<>();

    public static void register (UUID id, BlockPos pos, ResourceKey<Level> dimension) {
        GATEWAYS.put(id, new GatewayEntry(id, pos.immutable(), dimension));
    }

    public static Collection<GatewayEntry> getGateways() {
        return GATEWAYS.values();
    }

    public record GatewayEntry (UUID id, BlockPos pos, ResourceKey<Level> dimension) {
        
    }
}
