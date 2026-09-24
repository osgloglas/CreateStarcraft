package com.souls.starcraft.network;

import com.souls.starcraft.StarCraft;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record RequestCelestialKnowledgePayload () implements CustomPacketPayload {
    public static final Type<RequestCelestialKnowledgePayload> TYPE = 
        new Type<>(ResourceLocation.fromNamespaceAndPath(StarCraft.MODID, "request_celestial_knowledge"));

    public static final StreamCodec<RegistryFriendlyByteBuf, RequestCelestialKnowledgePayload> STREAM_CODEC = 
        StreamCodec.of((buffer, payload) -> {

        }, buffer -> new RequestCelestialKnowledgePayload());

    @Override 
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
