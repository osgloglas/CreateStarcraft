package com.souls.starcraft.network;

import com.souls.starcraft.StarCraft;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record StarlightManaPayload(float mana, float maxMana) implements CustomPacketPayload {
    public static final Type<StarlightManaPayload> TYPE = 
        new Type<>(ResourceLocation.fromNamespaceAndPath(StarCraft.MODID, "starlight_mana"

        ));

    public static final StreamCodec<RegistryFriendlyByteBuf, StarlightManaPayload> STREAM_CODEC = 
        StreamCodec.of((buffer, payload) -> {
            buffer.writeFloat(payload.mana());
            buffer.writeFloat(payload.maxMana());
        },
        buffer -> new StarlightManaPayload(
            buffer.readFloat(),
            buffer.readFloat()
        )
    );

    @Override 
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
