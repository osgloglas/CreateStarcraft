package com.souls.starcraft.client;

import com.souls.starcraft.StarCraft;
import com.souls.starcraft.block.ModBlockEntities;
import com.souls.starcraft.block.custom.entity.renderer.StarlightBasinRenderer;
import com.souls.starcraft.fluid.LiquidStarlightClientExtensions;
import com.souls.starcraft.fluid.ModFluids;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@EventBusSubscriber(
    modid = StarCraft.MODID,
    bus = EventBusSubscriber.Bus.MOD,
    value = Dist.CLIENT
)

public class StarCraftClient {
    @SubscribeEvent 
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerFluidType(new LiquidStarlightClientExtensions(), ModFluids.LIQUID_STARLIGHT_TYPE.get());
    }

    @SubscribeEvent 
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.STARLIGHT_BASIN.get(), StarlightBasinRenderer::new);
    }
}
