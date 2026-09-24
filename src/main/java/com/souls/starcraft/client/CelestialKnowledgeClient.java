package com.souls.starcraft.client;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CelestialKnowledgeClient {
    private static final Set<String> discoveredConstellations = new HashSet<>();

    public static void set(List<String> cIds) {
        discoveredConstellations.clear();
        discoveredConstellations.addAll(cIds);
    }

    public static boolean hasDiscovered(String cId) {
        return discoveredConstellations.contains(cId);
    }

    public static void clear() {
        discoveredConstellations.clear();
    }
}
