package com.souls.starcraft.constellation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class CelestialKnowledge {
    private final Set<String> discoveredConstellations = new HashSet<>();
    private final Set<String> assignedConstellationPapers = new HashSet<>();

    public static final Codec<CelestialKnowledge> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(Codec.STRING.listOf().fieldOf("discovered_constellations").forGetter(
            knowledge -> knowledge.discoveredConstellations.stream().toList()),

        Codec.STRING.listOf().optionalFieldOf("assigned_constellation_papers", List.of()).forGetter(
            knowledge -> knowledge.assignedConstellationPapers.stream().toList())
        ).apply(instance, CelestialKnowledge::new));

    public CelestialKnowledge() {}

    private CelestialKnowledge(List<String> discoveredConstellations, List<String> assignedConstellationPapers) {
        this.discoveredConstellations.addAll(discoveredConstellations);
        this.assignedConstellationPapers.addAll(assignedConstellationPapers);
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
        assignedConstellationPapers.clear();
    }

    public boolean markPaperAssigned(String cId) {
        return assignedConstellationPapers.add(cId);
    }

    public boolean hasPaperBeenAssigned(String cId) {
        return assignedConstellationPapers.contains(cId);
    }

    public Set<String> getAssignedConstellationPapers() {
        return Set.copyOf(assignedConstellationPapers);
    }
}
