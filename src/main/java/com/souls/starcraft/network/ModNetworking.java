package com.souls.starcraft.network;

import com.souls.starcraft.attachment.ModDataAttachments;
import com.souls.starcraft.client.CelestialGatewayClient;
import com.souls.starcraft.gateway.CelestialGatewayRegistry;
import com.souls.starcraft.mana.StarlightMana;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
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

        registrar.playToServer(
            TeleportToGatewayPayload.TYPE,
            TeleportToGatewayPayload.STREAM_CODEC,
            ModNetworking::handleTeleportToGateway
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
            CelestialGatewayClient.clearDestinationGateways();
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
            CelestialGatewayClient.addDestinationGateway(payload.gatewayId(), payload.pos());
            CelestialGatewayClient.setSourceGatewayPos(payload.sourcePos());
        });
    }

    private static void handleTeleportToGateway (
        final TeleportToGatewayPayload payload,
        final IPayloadContext context
    ) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer player)) {
                return;
            }

            CelestialGatewayRegistry.GatewayEntry destination =
                CelestialGatewayRegistry.getGateway(payload.gatewayId());

            if (destination == null) {
                return;
            }

            BlockPos playerPos = player.blockPosition();

            CelestialGatewayRegistry.GatewayEntry source = null;

            for (CelestialGatewayRegistry.GatewayEntry entry : CelestialGatewayRegistry.getGateways()) {
                if (entry.dimension().equals(player.level().dimension()) && entry.pos().distSqr(playerPos) <= 4.0) {
                    source = entry;
                    break;
                }
            }

            if (source == null) {
                return;
            }

            if (!source.dimension().equals(destination.dimension())) {
                return;
            }

            if (source.pos().distSqr(destination.pos()) > 500.0 * 500.0) {
                return;
            }

            System.out.println("valid teleport destination: " + destination.pos());
            BlockPos targetPos = destination.pos();

            player.teleportTo(targetPos.getX() + 0.5, targetPos.getY() + 1.0, targetPos.getZ() + 0.5);
        });
    }
}
