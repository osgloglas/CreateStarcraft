package com.souls.starcraft.network;

import com.souls.starcraft.StarCraft;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record DiscoverConstellationPayload(String cId) implements CustomPacketPayload {
    public static final Type<DiscoverConstellationPayload> TYPE = new Type<>(
        ResourceLocation.fromNamespaceAndPath(StarCraft.MODID, "discover_constellation"));

    public static final StreamCodec<RegistryFriendlyByteBuf, DiscoverConstellationPayload> STREAM_CODEC =
        StreamCodec.of((buffer, payload) -> buffer.writeUtf(payload.cId()), 
            buffer -> new DiscoverConstellationPayload(buffer.readUtf()));

    @Override 
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
