package com.souls.starcraft.network;

import java.util.UUID;

import com.souls.starcraft.StarCraft;

import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record GatewayDestinationPayload(UUID gatewayId, BlockPos pos, BlockPos sourcePos) implements CustomPacketPayload {
    public static final Type<GatewayDestinationPayload> TYPE = 
        new Type<>(ResourceLocation.fromNamespaceAndPath(StarCraft.MODID, "gateway_destination"));

    public static final StreamCodec<RegistryFriendlyByteBuf, GatewayDestinationPayload> STREAM_CODEC = 
        StreamCodec.of((buffer, payload) -> {
            buffer.writeUUID(payload.gatewayId());
            buffer.writeBlockPos(payload.pos());
            buffer.writeBlockPos(payload.sourcePos());
        },
        buffer -> new GatewayDestinationPayload(
            buffer.readUUID(),
            buffer.readBlockPos(),
            buffer.readBlockPos()
        )
    );

    @Override 
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
