package com.souls.starcraft.network;

import java.util.ArrayList;
import java.util.List;

import com.souls.starcraft.StarCraft;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record CelestialKnowledgePayload(List<String> discoveredConstellations) implements CustomPacketPayload {
    public static final Type<CelestialKnowledgePayload> TYPE = new Type<>(
        ResourceLocation.fromNamespaceAndPath(StarCraft.MODID, "celestial_knowledge")
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, CelestialKnowledgePayload> STREAM_CODEC =
        StreamCodec.of((buffer, payload) -> {
            buffer.writeVarInt(payload.discoveredConstellations().size());

            for (String cId : payload.discoveredConstellations()) {
                buffer.writeUtf(cId);
            }
        }, buffer -> {
            int size = buffer.readVarInt();

            List<String> discovered = new ArrayList<>(size);

            for (int i = 0; i < size; i++) {
                discovered.add(buffer.readUtf());
            }

            return new CelestialKnowledgePayload(discovered);
        });

    @Override 
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
