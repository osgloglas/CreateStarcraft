package com.souls.starcraft.event;

import com.souls.starcraft.ModMenuTypes;
import com.souls.starcraft.client.StarWandScreen;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber (
    modid = "starcraft",
    value = Dist.CLIENT,
    bus = EventBusSubscriber.Bus.GAME
)

public class ClientModEvents {
    @SubscribeEvent 
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.STAR_WAND.get(), StarWandScreen::new);
    }
}
