package com.souls.starcraft;

import java.util.function.Supplier;

import com.souls.starcraft.spell.StarWandSpells;

import net.minecraft.core.component.DataComponentType;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModDataComponents {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = 
        DeferredRegister.createDataComponents(StarCraft.MODID);

    public static final Supplier<DataComponentType<StarWandSpells>> STAR_WAND_SPELLS = 
        DATA_COMPONENTS.register("star_wand_spells", () -> DataComponentType.<StarWandSpells>builder()
            .persistent(StarWandSpells.CODEC).build()
    );
}
