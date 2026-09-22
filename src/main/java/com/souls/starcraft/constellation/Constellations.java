package com.souls.starcraft.constellation;

import java.util.List;

import net.minecraft.core.BlockPos;

public class Constellations {
    public static final ConstellationPattern LIBELLULA = new ConstellationPattern(
        "Libellula1", List.of(
            new BlockPos(-6, 0, -4),
            new BlockPos(-3, 0, -1),
            new BlockPos(0, 0, -4),
            new BlockPos(-1, 0, 1),
            new BlockPos(2, 0, -2),
            new BlockPos(2, 0, 4),
            new BlockPos(4, 0, 6)
        ));

    private Constellations() {}
}
