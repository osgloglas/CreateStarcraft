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
        register(ModItems.LIBELLULA_LENS.get(), ConstellationType.FLIGHT);
        register(ModItems.CERVUS_LENS.get(), ConstellationType.SPEED);
        register(ModItems.LUCERNARIUS_LENS.get(), ConstellationType.LIGHT_SOURCE);
        register(ModItems.BALAENA_STELLARIS_LENS.get(), ConstellationType.MAX_HEALTH);
        register(ModItems.ARCUS_STELLARIS_LENS.get(), ConstellationType.RANGE);
        register(ModItems.LUNAE_GEMINAE_LENS.get(), ConstellationType.DAMAGE);
        register(ModItems.CORONA_CRYSTALLINA_LENS.get(), ConstellationType.AOE);
        register(ModItems.VULPES_LENS.get(), ConstellationType.INVISIBILITY);
        register(ModItems.MAGNA_FERRIVIA_LENS.get(), ConstellationType.PROJECTILE);
        register(ModItems.PAPILIO_LENS.get(), ConstellationType.SLOWFALL);
        register(ModItems.GLADIUS_FRACTUS_LENS.get(), ConstellationType.STRENGTH);
        register(ModItems.PORTA_ASTRALIS_LENS.get(), ConstellationType.TELEPORTATION);
        register(ModItems.STELLA_POLARIS_LENS.get(), ConstellationType.BOMBS);
        register(ModItems.PEGASUS_LENS.get(), ConstellationType.ABSORPTION);
        register(ModItems.CUBUS_LENS.get(), ConstellationType.REACH);
        register(ModItems.HOROLOGIUM_LENS.get(), ConstellationType.TICK_SPEED);
        register(ModItems.SPIRALIS_LENS.get(), ConstellationType.DURATION);
        register(ModItems.CATENA_GALACTICA_LENS.get(), ConstellationType.LIFESTEAL);
        register(ModItems.STRENUUS_FAUTOR_LENS.get(), ConstellationType.HEAL);
    }
}
