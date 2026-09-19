package com.souls.starcraft.network;

import com.souls.starcraft.attachment.ModDataAttachments;
import com.souls.starcraft.client.CelestialGatewayClient;
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

        registrar.playToClient(
            OpenCelestialGatewayPayload.TYPE,
            OpenCelestialGatewayPayload.STREAM_CODEC,
            ModNetworking::handleOpenCelestialGateway
        );

        registrar.playToClient(
            CloseCelestialGatewayPayload.TYPE,
            CloseCelestialGatewayPayload.STREAM_CODEC,
            ModNetworking::handleCloseCelestialGateway
        );

        registrar.playToClient(
            GatewayDestinationPayload.TYPE,
            GatewayDestinationPayload.STREAM_CODEC,
            ModNetworking::handleGatewayDestination
        );
    }

    //mana
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

    //celestial gateway
    private static void handleOpenCelestialGateway (
        final OpenCelestialGatewayPayload payload,
        final IPayloadContext context
    ) {
        context.enqueueWork(() -> {
            CelestialGatewayClient.activate();
            System.out.println("Gateway OPEN");
        });
    }

    private static void handleCloseCelestialGateway (
        final CloseCelestialGatewayPayload payload,
        final IPayloadContext context
    ) {
        context.enqueueWork(() -> {
            CelestialGatewayClient.deactivate();
            System.out.println("Gateway CLOSED");
        });
    }

    private static void handleGatewayDestination (
        final GatewayDestinationPayload payload,
        final IPayloadContext context
    ) {
        context.enqueueWork(() -> {
            CelestialGatewayClient.setDestinationGateway(payload.gatewayId(), payload.pos());
            CelestialGatewayClient.setSourceGatewayPos(payload.sourcePos());
        });
    }
}
