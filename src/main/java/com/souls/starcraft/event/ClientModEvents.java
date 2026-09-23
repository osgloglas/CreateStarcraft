package com.souls.starcraft.event;

import com.souls.starcraft.ModEffects;
import com.souls.starcraft.ModMenuTypes;
import com.souls.starcraft.client.ModKeyMappings;
import com.souls.starcraft.client.StarWandScreen;
import com.souls.starcraft.network.SpellSwitchPayload;
import com.souls.starcraft.spell.TimedLightManager;
import com.souls.starcraft.spell.TimedTickSpeedManager;

import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

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
            if (player.hasEffect(ModEffects.FLIGHT)) {
                if (!player.getAbilities().mayfly) {
                    player.getAbilities().mayfly = true;
                    player.onUpdateAbilities();
                }
            } else if (!player.isCreative() && !player.isSpectator()) {
                if (player.getAbilities().mayfly) {
                    player.getAbilities().mayfly = false;
                    player.getAbilities().flying = false;
                    player.onUpdateAbilities();
                }
            }

            TimedLightManager.tick(player);

            TimedTickSpeedManager.tick(player);
        }
    }

    @SubscribeEvent 
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.player == null) return;

        while (ModKeyMappings.PREVIOUS_SPELL.consumeClick()) {
            PacketDistributor.sendToServer(new SpellSwitchPayload(-1));
        }

        while (ModKeyMappings.NEXT_SPELL.consumeClick()) {
            PacketDistributor.sendToServer(new SpellSwitchPayload(1));
        }
    }
}
