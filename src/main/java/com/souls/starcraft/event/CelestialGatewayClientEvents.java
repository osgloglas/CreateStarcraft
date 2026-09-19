package com.souls.starcraft.event;

import com.souls.starcraft.client.CelestialGatewayClient;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber (
    modid = "starcraft",
    value = Dist.CLIENT,
    bus = EventBusSubscriber.Bus.GAME
)

public class CelestialGatewayClientEvents {
    @SubscribeEvent 
    public static void onClientTick(ClientTickEvent.Post event) {
        if (!Minecraft.getInstance().isPaused()) {
            CelestialGatewayClient.tick();
        }
    }
}
