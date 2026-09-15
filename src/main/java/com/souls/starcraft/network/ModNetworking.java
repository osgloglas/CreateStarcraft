package com.souls.starcraft.network;

import com.souls.starcraft.attachment.ModDataAttachments;
import com.souls.starcraft.mana.StarlightMana;

import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class ModNetworking {
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");

        registrar.playToClient(
            StarlightManaPayload.TYPE,
            StarlightManaPayload.STREAM_CODEC,
            ModNetworking::handleStarlightMana
        );
    }

    private static void handleStarlightMana (
        final StarlightManaPayload payload,
        final IPayloadContext context
    ) {
        context.enqueueWork(() -> {
            StarlightMana mana = context.player().getData(ModDataAttachments.STARLIGHT_MANA);

            mana.setMaxMana(payload.maxMana());
            mana.setMana(payload.mana());
        });
    }
}
