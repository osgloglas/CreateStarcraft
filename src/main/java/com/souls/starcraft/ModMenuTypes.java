package com.souls.starcraft;

import java.util.function.Supplier;

import com.souls.starcraft.menu.StarWandMenu;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(
        Registries.MENU, StarCraft.MODID);

    public static final Supplier<MenuType<StarWandMenu>> STAR_WAND = MENUS.register("star_wand", () ->
        IMenuTypeExtension.create(StarWandMenu::new)
    );
}
