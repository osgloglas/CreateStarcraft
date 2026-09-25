package com.souls.starcraft.constellation;

import java.util.List;

import net.minecraft.core.BlockPos;

public class Constellations {
    private Constellations() {}

    public static final ConstellationPattern LIBELLULA = new ConstellationPattern(
        "libellula",
        "Libellula", List.of(
            new BlockPos(-6, 0, -4),
            new BlockPos(-3, 0, -1),
            new BlockPos(0, 0, -4),
            new BlockPos(-1, 0, 1),
            new BlockPos(2, 0, -2),
            new BlockPos(2, 0, 4),
            new BlockPos(4, 0, 6)
        ), List.of(
            new ConstellationPattern.Connection(0, 1),
            new ConstellationPattern.Connection(1, 2),
            new ConstellationPattern.Connection(1, 3),
            new ConstellationPattern.Connection(3, 4),
            new ConstellationPattern.Connection(3, 5),
            new ConstellationPattern.Connection(5, 6)
        ));

    public static final ConstellationPattern CERVUS = new ConstellationPattern(
        "cervus",
        "Cervus", List.of(
            new BlockPos(-6, 0, -2),
            new BlockPos(-4, 0, -3),
            new BlockPos(-2, 0, -4),
            new BlockPos(-0, 0, -6),
            new BlockPos(-2, 0, -1),
            new BlockPos(-3, 0, 2),
            new BlockPos(-1, 0, 2),
            new BlockPos(3, 0, 0),
            new BlockPos(2, 0, 3),
            new BlockPos(4, 0, 3)
        ), List.of(
            new ConstellationPattern.Connection(0, 1),
            new ConstellationPattern.Connection(1, 2),
            new ConstellationPattern.Connection(2, 3),
            new ConstellationPattern.Connection(1, 4),
            new ConstellationPattern.Connection(4, 5),
            new ConstellationPattern.Connection(4, 6),
            new ConstellationPattern.Connection(4, 7),
            new ConstellationPattern.Connection(7, 8),
            new ConstellationPattern.Connection(7, 9)
        ));

    public static final ConstellationPattern LUCERNARIUS = new ConstellationPattern(
        "lucernarius",
        "Lucernarius", List.of(
            new BlockPos(-1, 0, -3),
            new BlockPos(-1, 0, -1),
            new BlockPos(-3, 0, 1),
            new BlockPos(-1, 0, 2),
            new BlockPos(-2, 0, 4),
            new BlockPos(0, 0, 4),
            new BlockPos(2, 0, 0),
            new BlockPos(3, 0, -2),
            new BlockPos(4, 0, 0),
            new BlockPos(3, 0, 2)
        ), List.of(
            new ConstellationPattern.Connection(0, 1),
            new ConstellationPattern.Connection(1, 2),
            new ConstellationPattern.Connection(1, 3),
            new ConstellationPattern.Connection(3, 4),
            new ConstellationPattern.Connection(3, 5),
            new ConstellationPattern.Connection(1, 6),
            new ConstellationPattern.Connection(6, 7),
            new ConstellationPattern.Connection(7, 8),
            new ConstellationPattern.Connection(8, 9),
            new ConstellationPattern.Connection(9, 6)
        ));

    public static final ConstellationPattern BALAENA_STELLARIS = new ConstellationPattern(
        "balaena_stellaris",
        "Balaena Stellaris", List.of(
            new BlockPos(-6, 0, 2),
            new BlockPos(-4, 0, 3),
            new BlockPos(-1, 0, 4),
            new BlockPos(2, 0, 2),
            new BlockPos(3, 0, -1),
            new BlockPos(2, 0, -4),
            new BlockPos(6, 0, 0)
        ), List.of(
            new ConstellationPattern.Connection(0, 1),
            new ConstellationPattern.Connection(1, 2),
            new ConstellationPattern.Connection(2, 3),
            new ConstellationPattern.Connection(3, 4),
            new ConstellationPattern.Connection(4, 5),
            new ConstellationPattern.Connection(4, 6)
        ));

    public static final ConstellationPattern LUNA_GEMINAE = new ConstellationPattern(
        "luna_geminae",
        "Luna Geminae", List.of(
            new BlockPos(-5, 0, 1),
            new BlockPos(-3, 0, -1),
            new BlockPos(-1, 0, 1),
            new BlockPos(-3, 0, 3),
            new BlockPos(-1, 0, -1),
            new BlockPos(1, 0, -3),
            new BlockPos(3, 0, -1),
            new BlockPos(1, 0, 1)
        ), List.of(
            new ConstellationPattern.Connection(0, 1),
            new ConstellationPattern.Connection(1, 2),
            new ConstellationPattern.Connection(2, 3),
            new ConstellationPattern.Connection(3, 0),
            new ConstellationPattern.Connection(4, 5),
            new ConstellationPattern.Connection(5, 6),
            new ConstellationPattern.Connection(6, 7),
            new ConstellationPattern.Connection(7, 4)
        ));

    public static final ConstellationPattern CORONA_CRYSTALLINA = new ConstellationPattern(
        "corona_crystallina",
        "Corona Crystallina", List.of(
            new BlockPos(-5, 0, -1),
            new BlockPos(-4, 0, 1),
            new BlockPos(-3, 0, -1),
            new BlockPos(-2, 0, 1),
            new BlockPos(0, 0, -3),
            new BlockPos(2, 0, 1),
            new BlockPos(3, 0, -1),
            new BlockPos(4, 0, 1),
            new BlockPos(5, 0, -1)
        ), List.of(
            new ConstellationPattern.Connection(0, 1),
            new ConstellationPattern.Connection(1, 2),
            new ConstellationPattern.Connection(2, 3),
            new ConstellationPattern.Connection(3, 4),
            new ConstellationPattern.Connection(4, 5),
            new ConstellationPattern.Connection(5, 6),
            new ConstellationPattern.Connection(6, 7),
            new ConstellationPattern.Connection(7, 8)
        ));

    public static final ConstellationPattern VULPES = new ConstellationPattern(
        "vulpes",
        "Vulpes", List.of(
            new BlockPos(-6, 0, 1),
            new BlockPos(-5, 0, -1),
            new BlockPos(-3, 0, 1),
            new BlockPos(-4, 0, 3),
            new BlockPos(-2, 0, 3),
            new BlockPos(1, 0, -1),
            new BlockPos(0, 0, 1),
            new BlockPos(2, 0, 1),
            new BlockPos(3, 0, -2),
            new BlockPos(2, 0, -5)
        ), List.of(
            new ConstellationPattern.Connection(0, 1),
            new ConstellationPattern.Connection(1, 2),
            new ConstellationPattern.Connection(2, 3),
            new ConstellationPattern.Connection(2, 4),
            new ConstellationPattern.Connection(2, 5),
            new ConstellationPattern.Connection(5, 6),
            new ConstellationPattern.Connection(5, 7),
            new ConstellationPattern.Connection(5, 8),
            new ConstellationPattern.Connection(8, 9)
        ));

    public static final ConstellationPattern MAGNA_FERRIVIA = new ConstellationPattern(
        "magna_ferrivia",
        "Magna Ferrivia", List.of(
            new BlockPos(-4, 0, 0),
            new BlockPos(-2, 0, 2),
            new BlockPos(0, 0, 4),
            new BlockPos(0, 0, -4),
            new BlockPos(2, 0, -2),
            new BlockPos(4, 0, 0),
            new BlockPos(-4, 0, 2),
            new BlockPos(2, 0, -4),
            new BlockPos(-2, 0, 4),
            new BlockPos(4, 0, -2)
        ), List.of(
            new ConstellationPattern.Connection(0, 1),
            new ConstellationPattern.Connection(1, 2),
            new ConstellationPattern.Connection(3, 4),
            new ConstellationPattern.Connection(4, 5),
            new ConstellationPattern.Connection(6, 7),
            new ConstellationPattern.Connection(8, 9)
        ));

    public static final ConstellationPattern PAPILIO = new ConstellationPattern(
        "papilio",
        "Papilio", List.of(
            new BlockPos(-5, 0, 0),
            new BlockPos(-2, 0, -2),
            new BlockPos(0, 0, -5),
            new BlockPos(3, 0, -3),
            new BlockPos(5, 0, 0),
            new BlockPos(2, 0, 2),
            new BlockPos(0, 0, 5),
            new BlockPos(-3, 0, 3)
        ), List.of(
            new ConstellationPattern.Connection(0, 1),
            new ConstellationPattern.Connection(1, 2),
            new ConstellationPattern.Connection(2, 3),
            new ConstellationPattern.Connection(3, 4),
            new ConstellationPattern.Connection(4, 5),
            new ConstellationPattern.Connection(5, 6),
            new ConstellationPattern.Connection(6, 7),
            new ConstellationPattern.Connection(7, 0),
            new ConstellationPattern.Connection(1, 5)
        ));

    public static final ConstellationPattern GLADIUS_FRACTUS = new ConstellationPattern(
        "gladius_fractus",
        "Gladius Fractus", List.of(
            new BlockPos(-5, 0, 5),
            new BlockPos(-5, 0, 1),
            new BlockPos(-3, 0, 3),
            new BlockPos(-2, 0, 4),
            new BlockPos(0, 0, 6),
            new BlockPos(3, 0, -3),
            new BlockPos(1, 0, 1)
        ), List.of(
            new ConstellationPattern.Connection(0, 2),
            new ConstellationPattern.Connection(1, 2),
            new ConstellationPattern.Connection(2, 3),
            new ConstellationPattern.Connection(3, 4),
            new ConstellationPattern.Connection(2, 5),
            new ConstellationPattern.Connection(5, 6),
            new ConstellationPattern.Connection(6, 3)
        ));

    public static final ConstellationPattern ARCUS_STELLARIS = new ConstellationPattern(
        "arcus_stellaris",
        "Arcus Stellaris", List.of(
            new BlockPos(-3, 0, 5),
            new BlockPos(-4, 0, 0),
            new BlockPos(0, 0, -4),
            new BlockPos(5, 0, -3),
            new BlockPos(1, 0, 1),
            new BlockPos(-4, 0, -4)
        ), List.of(
            new ConstellationPattern.Connection(0, 1),
            new ConstellationPattern.Connection(1, 2),
            new ConstellationPattern.Connection(2, 3),
            new ConstellationPattern.Connection(3, 4),
            new ConstellationPattern.Connection(4, 0),
            new ConstellationPattern.Connection(4, 5)
        ));

    public static final ConstellationPattern PORTA_ASTRALIS = new ConstellationPattern(
        "porta_astralis",
        "Porta Astralis", List.of(
            new BlockPos(-4, 0, 4),
            new BlockPos(-4, 0, 0),
            new BlockPos(-2, 0, -3),
            new BlockPos(2, 0, -3),
            new BlockPos(4, 0, 0),
            new BlockPos(4, 0, 4)
        ), List.of(
            new ConstellationPattern.Connection(0, 1),
            new ConstellationPattern.Connection(1, 2),
            new ConstellationPattern.Connection(2, 3),
            new ConstellationPattern.Connection(3, 4),
            new ConstellationPattern.Connection(4, 5)
        ));

    public static final ConstellationPattern STELLA_POLARIS = new ConstellationPattern(
        "stella_polaris",
        "Stella Polaris", List.of(
            new BlockPos(-2, 0, 0),
            new BlockPos(2, 0, 0),
            new BlockPos(0, 0, -2),
            new BlockPos(0, 0, 2)
        ), List.of(
            new ConstellationPattern.Connection(0, 1),
            new ConstellationPattern.Connection(2, 3)
        ));

    public static final ConstellationPattern PEGASUS = new ConstellationPattern(
        "pegasus",
        "Pegasus", List.of(
            new BlockPos(-6, 0, 0),
            new BlockPos(-4, 0, -1),
            new BlockPos(-3, 0, 1),
            new BlockPos(-4, 0, 3),
            new BlockPos(-2, 0, 3),
            new BlockPos(2, 0, 0),
            new BlockPos(1, 0, 2),
            new BlockPos(3, 0, 2),
            new BlockPos(-2, 0, -2),
            new BlockPos(0, 0, -4),
            new BlockPos(3, 0, -5)
        ), List.of(
            new ConstellationPattern.Connection(0, 1),
            new ConstellationPattern.Connection(1, 2),
            new ConstellationPattern.Connection(2, 3),
            new ConstellationPattern.Connection(2, 4),
            new ConstellationPattern.Connection(2, 5),
            new ConstellationPattern.Connection(5, 6),
            new ConstellationPattern.Connection(5, 7),
            new ConstellationPattern.Connection(2, 8),
            new ConstellationPattern.Connection(8, 9),
            new ConstellationPattern.Connection(9, 10)
        ));

    public static final ConstellationPattern CUBUS = new ConstellationPattern(
        "cubus",
        "Cubus", List.of(
            new BlockPos(0, 0, -3),
            new BlockPos(3, 0, -1),
            new BlockPos(3, 0, 3),
            new BlockPos(0, 0, 5),
            new BlockPos(-3, 0, 3),
            new BlockPos(-3, 0, -1),
            new BlockPos(0, 0, 1)
        ), List.of(
            new ConstellationPattern.Connection(0, 1),
            new ConstellationPattern.Connection(1, 2),
            new ConstellationPattern.Connection(2, 3),
            new ConstellationPattern.Connection(3, 4),
            new ConstellationPattern.Connection(4, 5),
            new ConstellationPattern.Connection(5, 0),
            new ConstellationPattern.Connection(5, 6),
            new ConstellationPattern.Connection(6, 1),
            new ConstellationPattern.Connection(6, 3)
        ));

    public static final ConstellationPattern HOROLOGIUM = new ConstellationPattern(
        "horologium",
        "Horologium", List.of(
            new BlockPos(-6, 0, 0),
            new BlockPos(-4, 0, -4),
            new BlockPos(0, 0, -6),
            new BlockPos(4, 0, -4),
            new BlockPos(6, 0, 0),
            new BlockPos(4, 0, 4),
            new BlockPos(0, 0, 6),
            new BlockPos(-4, 0, 4),
            new BlockPos(0, 0, 1),
            new BlockPos(0, 0, -4),
            new BlockPos(2, 0, -1)
        ), List.of(
            new ConstellationPattern.Connection(0, 1),
            new ConstellationPattern.Connection(1, 2),
            new ConstellationPattern.Connection(2, 3),
            new ConstellationPattern.Connection(3, 4),
            new ConstellationPattern.Connection(4, 5),
            new ConstellationPattern.Connection(5, 6),
            new ConstellationPattern.Connection(6, 7),
            new ConstellationPattern.Connection(7, 0),
            new ConstellationPattern.Connection(8, 9),
            new ConstellationPattern.Connection(8, 10)
        ));

    public static final ConstellationPattern SPIRALIS = new ConstellationPattern(
        "spiralis",
        "Spiralis", List.of(
            new BlockPos(-1, 0, 0),
            new BlockPos(-2, 0, -2),
            new BlockPos(-4, 0, -2),
            new BlockPos(-5, 0, 1),
            new BlockPos(1, 0, 3),
            new BlockPos(4, 0, 0),
            new BlockPos(2, 0, -5)
        ), List.of(
            new ConstellationPattern.Connection(0, 1),
            new ConstellationPattern.Connection(1, 2),
            new ConstellationPattern.Connection(2, 3),
            new ConstellationPattern.Connection(3, 4),
            new ConstellationPattern.Connection(4, 5),
            new ConstellationPattern.Connection(5, 6)
        ));

    public static final ConstellationPattern CATENA_GALACTICA = new ConstellationPattern(
        "catena_galactica",
        "Catena Galactica", List.of(
            new BlockPos(-3, 0, 4),
            new BlockPos(-5, 0, 1),
            new BlockPos(-5, 0, -1),
            new BlockPos(-2, 0, -3),
            new BlockPos(1, 0, -4),
            new BlockPos(3, 0, -2),
            new BlockPos(3, 0, 0),
            new BlockPos(1, 0, 1)
        ), List.of(
            new ConstellationPattern.Connection(0, 1),
            new ConstellationPattern.Connection(1, 2),
            new ConstellationPattern.Connection(2, 3),
            new ConstellationPattern.Connection(3, 4),
            new ConstellationPattern.Connection(4, 5),
            new ConstellationPattern.Connection(5, 6),
            new ConstellationPattern.Connection(6, 7)
        ));

    public static final ConstellationPattern STRENUUS_FAUTOR = new ConstellationPattern(
        "strenuus_fautor",
        "Strenuus Fautor", List.of(
            new BlockPos(0, 0, 1),
            new BlockPos(0, 0, -2),
            new BlockPos(-2, 0, -4),
            new BlockPos(3, 0, 1),
            new BlockPos(5, 0, -1),
            new BlockPos(2, 0, 3),
            new BlockPos(4, 0, 4),
            new BlockPos(-2, 0, 3),
            new BlockPos(-3, 0, 5),
            new BlockPos(-3, 0, 0),
            new BlockPos(-6, 0, 0)
        ), List.of(
            new ConstellationPattern.Connection(0, 1),
            new ConstellationPattern.Connection(1, 2),
            new ConstellationPattern.Connection(0, 3),
            new ConstellationPattern.Connection(3, 4),
            new ConstellationPattern.Connection(0, 5),
            new ConstellationPattern.Connection(5, 6),
            new ConstellationPattern.Connection(0, 7),
            new ConstellationPattern.Connection(7, 8),
            new ConstellationPattern.Connection(0, 9),
            new ConstellationPattern.Connection(9, 10)
        ));

    public static final List<ConstellationPattern> ALL = List.of(
        LIBELLULA,
        CERVUS,
        LUCERNARIUS,
        BALAENA_STELLARIS,
        LUNA_GEMINAE,
        CORONA_CRYSTALLINA,
        VULPES,
        MAGNA_FERRIVIA,
        PAPILIO,
        GLADIUS_FRACTUS,
        ARCUS_STELLARIS,
        PORTA_ASTRALIS,
        STELLA_POLARIS,
        PEGASUS,
        CUBUS,
        HOROLOGIUM,
        SPIRALIS,
        CATENA_GALACTICA,
        STRENUUS_FAUTOR
    );

    public static ConstellationPattern getById(String id) {
        for (ConstellationPattern constellation : ALL) {
            if (constellation.id().equals(id)) {
                return constellation;
            }
        }

        return null;
    }
}
