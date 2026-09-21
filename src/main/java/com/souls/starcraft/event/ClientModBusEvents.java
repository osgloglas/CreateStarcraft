package com.souls.starcraft.event;

import com.souls.starcraft.client.ModKeyMappings;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@EventBusSubscriber (
    modid = "starcraft",
    value = Dist.CLIENT,
    bus = EventBusSubscriber.Bus.MOD
)

public class ClientModBusEvents {

    @SubscribeEvent 
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(ModKeyMappings.PREVIOUS_SPELL);
        event.register(ModKeyMappings.NEXT_SPELL);
    }
}
