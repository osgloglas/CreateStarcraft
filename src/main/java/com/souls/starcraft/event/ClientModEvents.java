package com.souls.starcraft.event;

import com.souls.starcraft.ModMenuTypes;
import com.souls.starcraft.client.StarWandScreen;
import com.souls.starcraft.spell.TimedFlightManager;
import com.souls.starcraft.spell.TimedLightManager;
import com.souls.starcraft.spell.TimedMaxHealthManager;
import com.souls.starcraft.spell.TimedReachManager;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

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

    @SubscribeEvent 
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            TimedFlightManager.tick(player);
            TimedLightManager.tick(player);
            TimedMaxHealthManager.tick(player);
            TimedReachManager.tick(player);
        }
    }
}
