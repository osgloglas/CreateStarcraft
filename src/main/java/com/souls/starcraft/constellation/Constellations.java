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

    public static ConstellationPattern getById(String id) {
        for (ConstellationPattern constellation : ALL) {
            if (constellation.id().equals(id)) {
                return constellation;
            }
        }

        return null;
    }

    public static final List<ConstellationPattern> ALL = List.of(
        LIBELLULA
    );
}
