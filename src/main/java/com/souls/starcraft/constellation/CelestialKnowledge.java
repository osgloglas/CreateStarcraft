package com.souls.starcraft.constellation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class CelestialKnowledge {
    private final Set<String> discoveredConstellations = new HashSet<>();

    public static final Codec<CelestialKnowledge> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(Codec.STRING.listOf().fieldOf("discovered_constellations").forGetter(
            knowledge -> knowledge.discoveredConstellations.stream().toList())).apply(instance, CelestialKnowledge::new));

    public CelestialKnowledge() {}

    private CelestialKnowledge(List<String> discoveredConstellations) {
        this.discoveredConstellations.addAll(discoveredConstellations);
    }

    public boolean discover(String cId) {
        return discoveredConstellations.add(cId);
    }

    public boolean hasDiscovered(String cId) {
        return discoveredConstellations.contains(cId);
    }

    public Set<String> getDiscoveredConstellations() {
        return Set.copyOf(discoveredConstellations);
    }

    public void reset() {
        discoveredConstellations.clear();
    }
}
