package com.souls.starcraft.network;

import com.souls.starcraft.attachment.ModDataAttachments;
import com.souls.starcraft.client.CelestialGatewayClient;
import com.souls.starcraft.client.CelestialKnowledgeClient;
import com.souls.starcraft.constellation.Constellations;
import com.souls.starcraft.gateway.CelestialGatewayRegistry;
import com.souls.starcraft.mana.StarlightMana;
import com.souls.starcraft.spell.SpellCastingItem;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
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

        registrar.playToServer(
            SpellSwitchPayload.TYPE,
            SpellSwitchPayload.STREAM_CODEC,
            ModNetworking::handleSpellSwitch
        );

        registrar.playToServer(
            DiscoverConstellationPayload.TYPE,
            DiscoverConstellationPayload.STREAM_CODEC,
            ModNetworking::handleDiscoverConstellation
        );

        registrar.playToClient(
            CelestialKnowledgePayload.TYPE,
            CelestialKnowledgePayload.STREAM_CODEC,
            ModNetworking::handleCelestialKnowledge
        );

        registrar.playToServer(
            RequestCelestialKnowledgePayload.TYPE,
            RequestCelestialKnowledgePayload.STREAM_CODEC,
            ModNetworking::handleRequestCelestialKnowledge
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

    //spell swapping
    private static void handleSpellSwitch(final SpellSwitchPayload payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            ItemStack stack = context.player().getMainHandItem();

            if (!(stack.getItem() instanceof SpellCastingItem spellItem)) {
                return;
            }

            if (payload.direction() > 0) {
                spellItem.nextSpell(stack);
            } else if (payload.direction() < 0) {
                spellItem.previousSpell(stack);
            }
        });
    }

    //constellation discovery
    private static void handleDiscoverConstellation(final DiscoverConstellationPayload payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer player)) {
                return;
            }

            var constellation = Constellations.getById(payload.cId());

            if (constellation == null) {
                return;
            }

            var knowledge = player.getData(ModDataAttachments.CELESTIAL_KNOWLEDGE.get());

            boolean newlyDiscovered = knowledge.discover(payload.cId());

            if (newlyDiscovered) {
                player.sendSystemMessage(Component.literal("Discovered " + constellation.name() + "!"));

                PacketDistributor.sendToPlayer(player, new CelestialKnowledgePayload(knowledge.getDiscoveredConstellations().stream().toList()));
            }
        });
    }

    private static void handleCelestialKnowledge(final CelestialKnowledgePayload payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            CelestialKnowledgeClient.set(payload.discoveredConstellations());
        });
    }

    private static void handleRequestCelestialKnowledge(final RequestCelestialKnowledgePayload payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer player)) {
                return;
            }

            var knowledge = player.getData(ModDataAttachments.CELESTIAL_KNOWLEDGE.get());

            PacketDistributor.sendToPlayer(player, new CelestialKnowledgePayload(knowledge.getDiscoveredConstellations().stream().toList()));
        });
    }
}
