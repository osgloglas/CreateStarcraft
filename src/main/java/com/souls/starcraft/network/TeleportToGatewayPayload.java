package com.souls.starcraft.network;

import java.util.UUID;

import com.souls.starcraft.StarCraft;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record TeleportToGatewayPayload(UUID gatewayId) implements CustomPacketPayload {
    public static final Type<TeleportToGatewayPayload> TYPE =
        new Type<>(ResourceLocation.fromNamespaceAndPath(StarCraft.MODID, "teleport_to_gateway"));
    
    public static final StreamCodec<RegistryFriendlyByteBuf, TeleportToGatewayPayload> STREAM_CODEC =
        StreamCodec.of((buffer, payload) -> buffer.writeUUID(payload.gatewayId()), buffer ->
        new TeleportToGatewayPayload(buffer.readUUID())
    );

    @Override 
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
