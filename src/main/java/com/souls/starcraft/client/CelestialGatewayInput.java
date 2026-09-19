package com.souls.starcraft.client;

import java.util.UUID;

import com.souls.starcraft.StarCraft;
import com.souls.starcraft.network.TeleportToGatewayPayload;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(
    modid = StarCraft.MODID,
    value = Dist.CLIENT,
    bus = EventBusSubscriber.Bus.GAME
)

public class CelestialGatewayInput {
    @SubscribeEvent 
    public static void onMouseButton(InputEvent.MouseButton.Pre event) {
        //left mouse button, press only
        if (event.getButton() != 0 || event.getAction() != 1) {
            return;
        }

        if (!CelestialGatewayClient.isActive()) {
            return;
        }

        UUID target = CelestialGatewayClient.getTargetedGatewayId();

        if (target == null) {
            return;
        }

        PacketDistributor.sendToServer(new TeleportToGatewayPayload(target));
    }
}
