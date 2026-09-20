package com.souls.starcraft.spell;

import java.util.HashMap;
import java.util.Map;

import com.souls.starcraft.item.ModItems;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ConstellationRegistry {
    private static final Map<Item, ConstellationType> CONSTELLATIONS = new HashMap<>();

    public static void register(Item item, ConstellationType type) {
        CONSTELLATIONS.put(item, type);
    }

    public static ConstellationType get(ItemStack stack) {
        return CONSTELLATIONS.get(stack.getItem());
    }

    public static void bootstrap() {
        register(ModItems.ARCUS_STELLARIS_LENS.get(), ConstellationType.RANGE);
        register(ModItems.LUNAE_GEMINAE_LENS.get(), ConstellationType.DAMAGE);
        register(ModItems.MAGNA_FERRIVIA_LENS.get(), ConstellationType.PROJECTILE);
    }
}
