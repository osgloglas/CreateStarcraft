package com.souls.starcraft.constellation;

import com.souls.starcraft.item.ModItems;

import net.minecraft.world.item.Item;

public class ConstellationLenses {
    public static Item getLensForConstellation(String constellationId) {
        return switch (constellationId) {
            case "libellula" -> ModItems.LIBELLULA_LENS.get();
            case "cervus" -> ModItems.CERVUS_LENS.get();
            case "lucernarius" -> ModItems.LUCERNARIUS_LENS.get();
            case "balaena_stellaris" -> ModItems.BALAENA_STELLARIS_LENS.get();
            case "lunae_geminae" -> ModItems.LUNAE_GEMINAE_LENS.get();
            case "corona_crystallina" -> ModItems.CORONA_CRYSTALLINA_LENS.get();
            case "vulpes" -> ModItems.VULPES_LENS.get();
            case "magna_ferrivia" -> ModItems.MAGNA_FERRIVIA_LENS.get();
            case "papilio" -> ModItems.PAPILIO_LENS.get();
            case "gladius_fractus" -> ModItems.GLADIUS_FRACTUS_LENS.get();
            case "arcus_stellaris" -> ModItems.ARCUS_STELLARIS_LENS.get();
            case "porta_astralis" -> ModItems.PORTA_ASTRALIS_LENS.get();
            case "stella_polaris" -> ModItems.STELLA_POLARIS_LENS.get();
            case "pegasus" -> ModItems.PEGASUS_LENS.get();
            case "cubus" -> ModItems.CUBUS_LENS.get();
            case "horologium" -> ModItems.HOROLOGIUM_LENS.get();
            case "spiralis" -> ModItems.SPIRALIS_LENS.get();
            case "catena_galactica" -> ModItems.CATENA_GALACTICA_LENS.get();
            case "strenuus_fautor" -> ModItems.STRENUUS_FAUTOR_LENS.get();

            default -> null;
        };
    }

    private ConstellationLenses() {}
}
