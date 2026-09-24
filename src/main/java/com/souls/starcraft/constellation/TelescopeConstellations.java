package com.souls.starcraft.constellation;

import java.util.List;

public class TelescopeConstellations {
    private TelescopeConstellations() {}

    public static final TelescopeConstellation LIBELLULA = new TelescopeConstellation(
        "libellula", List.of(
            new TelescopeConstellation.SkyPoint(1158, 552),
            new TelescopeConstellation.SkyPoint(1182, 574),
            new TelescopeConstellation.SkyPoint(1206, 549),
            new TelescopeConstellation.SkyPoint(1206, 593),
            new TelescopeConstellation.SkyPoint(1229, 572),
            new TelescopeConstellation.SkyPoint(1228, 615),
            new TelescopeConstellation.SkyPoint(1253, 638)
        )
    );

    private static final List<TelescopeConstellation> ALL = List.of(
        LIBELLULA
    );

    public static TelescopeConstellation getById(String id) {
        for (TelescopeConstellation constellation : ALL) {
            if (constellation.cId().equals(id)) {
                return constellation;
            }
        }

        return null;
    }
}
