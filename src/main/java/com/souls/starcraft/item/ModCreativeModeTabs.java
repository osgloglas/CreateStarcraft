package com.souls.starcraft.item;

import java.util.function.Supplier;

import com.souls.starcraft.StarCraft;
import com.souls.starcraft.block.ModBlocks;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, StarCraft.MODID);

    public static final Supplier<CreativeModeTab> STARCRAFT_TAB = CREATIVE_MODE_TAB.register("starcraft_tab", () -> CreativeModeTab.builder()
        .icon(() -> new ItemStack(ModItems.AQUAMARINE.get()))
        .title(Component.translatable("creativetab.starcraft.starcraft_tab"))
        .displayItems((itemDisplayParameters, output) -> {
            output.accept(ModItems.AQUAMARINE);
            output.accept(ModItems.STAR_WAND);
            output.accept(ModItems.LIQUID_STARLIGHT_BUCKET);
            output.accept(ModItems.STARMETAL);

            output.accept(ModItems.LENS);
            output.accept(ModItems.LIBELLULA_LENS);
            output.accept(ModItems.CERVUS_LENS);
            output.accept(ModItems.LUCERNARIUS_LENS);
            output.accept(ModItems.BALAENA_STELLARIS_LENS);
            output.accept(ModItems.LUNAE_GEMINAE_LENS);
            output.accept(ModItems.CORONA_CRYSTALLINA_LENS);
            output.accept(ModItems.VULPES_LENS);
            output.accept(ModItems.MAGNA_FERRIVIA_LENS);
            output.accept(ModItems.PAPILIO_LENS);
            output.accept(ModItems.GLADIUS_FRACTUS_LENS);
            output.accept(ModItems.ARCUS_STELLARIS_LENS);
            output.accept(ModItems.PORTA_ASTRALIS_LENS);
            output.accept(ModItems.STELLA_POLARIS_LENS);
            output.accept(ModItems.PEGASUS_LENS);
            output.accept(ModItems.CUBUS_LENS);
            output.accept(ModItems.HOROLOGIUM_LENS);
            output.accept(ModItems.SPIRALIS_LENS);
            output.accept(ModItems.CATENA_GALACTICA_LENS);
            output.accept(ModItems.STRENUUS_FAUTOR_LENS);

            output.accept(ModBlocks.AQUAMARINE_SAND);
            output.accept(ModBlocks.MARBLE_BLOCK);
            output.accept(ModBlocks.MARBLE_STAIRS);
            output.accept(ModBlocks.MARBLE_SLAB);
            output.accept(ModBlocks.MARBLE_WALL);
            output.accept(ModBlocks.BLACK_MARBLE_BLOCK);
            output.accept(ModBlocks.MARBLE_PEDESTAL);

            output.accept(ModBlocks.STARLIGHT_CRAFTER);
            output.accept(ModBlocks.STARLIGHT_BASIN);
            output.accept(ModBlocks.CELESTIAL_GATEWAY);
            output.accept(ModBlocks.CRYSTAL_LENS);
            output.accept(ModBlocks.LENS_FRAME);
            output.accept(ModBlocks.ATTUNEMENT_ALTAR);
        }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}