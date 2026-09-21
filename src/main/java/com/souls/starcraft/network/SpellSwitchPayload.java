package com.souls.starcraft.network;

import com.souls.starcraft.StarCraft;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SpellSwitchPayload(int direction) implements CustomPacketPayload {
    public static final Type<SpellSwitchPayload> TYPE = new Type<>(
        ResourceLocation.fromNamespaceAndPath(StarCraft.MODID, "spell_switch"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SpellSwitchPayload> STREAM_CODEC = 
        StreamCodec.of((buf, payload) -> buf.writeInt(payload.direction()), 
        buf -> new SpellSwitchPayload(buf.readInt()));

    @Override 
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
