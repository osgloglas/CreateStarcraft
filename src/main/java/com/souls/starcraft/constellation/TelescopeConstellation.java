package com.souls.starcraft.constellation;

import java.util.List;

public record TelescopeConstellation(String cId, List<SkyPoint> stars) {
    public record SkyPoint(int x, int y) {}
}
