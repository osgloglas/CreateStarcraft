package com.souls.starcraft.network;

import com.souls.starcraft.StarCraft;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record OpenCelestialGatewayPayload() implements CustomPacketPayload {
    public static final Type<OpenCelestialGatewayPayload> TYPE = 
        new Type<>(ResourceLocation.fromNamespaceAndPath(StarCraft.MODID, "open_celestial_gateway"));

    public static final StreamCodec<RegistryFriendlyByteBuf, OpenCelestialGatewayPayload> STREAM_CODEC = 
        StreamCodec.of((buffer, payload) -> {
            
        },
        buffer -> new OpenCelestialGatewayPayload(
            
        )
    );

    @Override 
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
