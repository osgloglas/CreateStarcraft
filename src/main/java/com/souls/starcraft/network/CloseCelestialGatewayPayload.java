package com.souls.starcraft.network;

import com.souls.starcraft.StarCraft;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record CloseCelestialGatewayPayload() implements CustomPacketPayload {
    public static final Type<CloseCelestialGatewayPayload> TYPE = 
        new Type<>(ResourceLocation.fromNamespaceAndPath(StarCraft.MODID, "close_celestial_gateway"));

    public static final StreamCodec<RegistryFriendlyByteBuf, CloseCelestialGatewayPayload> STREAM_CODEC = 
        StreamCodec.of((buffer, payload) -> {
            
        },
        buffer -> new CloseCelestialGatewayPayload(
            
        )
    );

    @Override 
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
