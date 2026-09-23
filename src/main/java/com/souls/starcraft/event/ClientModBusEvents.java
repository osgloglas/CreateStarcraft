package com.souls.starcraft.event;

import com.souls.starcraft.block.ModBlockEntities;
import com.souls.starcraft.block.custom.entity.AttunementAltarOrbs;
import com.souls.starcraft.block.custom.entity.TelescopeModel;
import com.souls.starcraft.block.custom.entity.renderer.AttunementAltarRenderer;
import com.souls.starcraft.block.custom.entity.renderer.TelescopeRenderer;
import com.souls.starcraft.client.ModKeyMappings;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
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

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(AttunementAltarOrbs.LAYER_LOCATION, AttunementAltarOrbs::createBodyLayer);
        event.registerLayerDefinition(TelescopeModel.LAYER_LOCATION, TelescopeModel::createBodyLayer);
    }

    @SubscribeEvent 
    public static void registerBlockEntityRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.ATTUNEMENT_ALTAR.get(), AttunementAltarRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.TELESCOPE_BE.get(), TelescopeRenderer::new);
    }
}
