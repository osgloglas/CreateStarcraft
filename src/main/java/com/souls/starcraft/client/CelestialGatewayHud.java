package com.souls.starcraft.client;

import com.souls.starcraft.StarCraft;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

@EventBusSubscriber(
    modid = StarCraft.MODID,
    value = Dist.CLIENT,
    bus = EventBusSubscriber.Bus.GAME
)

public class CelestialGatewayHud {
    @SubscribeEvent
    public static void onRenderGui(RenderGuiEvent.Post event) {
        if (CelestialGatewayClient.getTargetedGatewayId() == null) {
            return;
        }

        int distance = CelestialGatewayClient.getTargetedGatewayDistance();

        if (distance < 0) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();

        String title = "Celestial Gateway";
        String distanceText = distance + " blocks";

        int centerX = event.getGuiGraphics().guiWidth() / 2;
        int centerY = event.getGuiGraphics().guiHeight() / 2;

        int titleX = centerX - minecraft.font.width(title) / 2;
        int distanceX = centerX - minecraft.font.width(distanceText) / 2;

        event.getGuiGraphics().drawString(minecraft.font, title, titleX, centerY + 18, 0xFFFFFF, true);

        event.getGuiGraphics().drawString(minecraft.font, distanceText, distanceX, centerY + 30, 0xAAAAAA, true);
    }
}
