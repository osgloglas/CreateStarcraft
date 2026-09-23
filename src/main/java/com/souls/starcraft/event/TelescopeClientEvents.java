package com.souls.starcraft.event;

import com.souls.starcraft.StarCraft;
import com.souls.starcraft.block.ModBlocks;
import com.souls.starcraft.client.TelescopeScreen;

import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;

@EventBusSubscriber(
        modid = StarCraft.MODID,
        bus = EventBusSubscriber.Bus.GAME
)

public class TelescopeClientEvents {
    @SubscribeEvent
    public static void onInteraction(InputEvent.InteractionKeyMappingTriggered event) {
        Minecraft minecraft = Minecraft.getInstance();

        if (!event.isUseItem()) {
            return;
        }

        if (!(minecraft.hitResult instanceof BlockHitResult hit)) {
            return;
        }

        if (minecraft.level == null) {
            return;
        }

        if (!minecraft.level.getBlockState(hit.getBlockPos())
                .is(ModBlocks.TELESCOPE.get())) {
            return;
        }

        minecraft.setScreen(new TelescopeScreen());
    }
}
