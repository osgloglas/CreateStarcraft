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

            output.accept(ModBlocks.AQUAMARINE_SAND);
            output.accept(ModBlocks.MARBLE_BLOCK);

            output.accept(ModBlocks.STARLIGHT_CRAFTER);
            output.accept(ModBlocks.STARLIGHT_BASIN);
        }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}