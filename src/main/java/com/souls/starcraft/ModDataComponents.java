package com.souls.starcraft;

import java.util.function.Supplier;

import com.mojang.serialization.Codec;
import com.souls.starcraft.spell.StarWandSpells;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModDataComponents {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = 
        DeferredRegister.createDataComponents(StarCraft.MODID);

    public static final Supplier<DataComponentType<StarWandSpells>> STAR_WAND_SPELLS = 
        DATA_COMPONENTS.register("star_wand_spells", () -> DataComponentType.<StarWandSpells>builder()
            .persistent(StarWandSpells.CODEC).build()
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> SELECTED_SPELL =
        DATA_COMPONENTS.register("selected_spell", () -> DataComponentType.<Integer>builder()
            .persistent(Codec.INT)
            .networkSynchronized(ByteBufCodecs.INT)
            .build());
}
