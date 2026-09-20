package com.souls.starcraft.spell;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.mojang.serialization.Codec;

import net.minecraft.resources.ResourceLocation;

public class StarWandSpells {
    public static final int SPELL_COUNT = 3;
    public static final int SLOTS_PER_SPELL = 12;

    private final List<List<Optional<ResourceLocation>>> spells;

    public static final Codec<StarWandSpells> CODEC = ResourceLocation.CODEC
        .optionalFieldOf("constellation")
        .codec()
        .listOf()
        .listOf()
        .xmap(StarWandSpells::new, StarWandSpells::getSpells);

    public StarWandSpells() {
        List<List<Optional<ResourceLocation>>> newSpells = new ArrayList<>();

        for (int spell = 0; spell < SPELL_COUNT; spell++) {
            List<Optional<ResourceLocation>> slots = new ArrayList<>();

            for (int slot = 0; slot < SLOTS_PER_SPELL; slot++) {
                slots.add(Optional.empty());
            }

            newSpells.add(List.copyOf(slots));
        }

        this.spells = List.copyOf(newSpells);
    }

    public StarWandSpells(List<List<Optional<ResourceLocation>>> spells) {
        this.spells = spells.stream().map(List::copyOf).toList();
    }

    public List<List<Optional<ResourceLocation>>> getSpells() {
        return spells;
    }

    public Optional<ResourceLocation> getConstellation (int spell, int slot) {
        return spells.get(spell).get(slot);
    }

    public StarWandSpells withConstellation (int spell, int slot, ResourceLocation constellation) {
        List<List<Optional<ResourceLocation>>> copy = spells.stream()
            .map(ArrayList::new)
            .map(list -> (List<Optional<ResourceLocation>>) list)
            .collect(Collectors.toCollection(ArrayList::new));

        copy.get(spell).set(slot, Optional.of(constellation));

        return new StarWandSpells(copy);
    }

    public StarWandSpells withoutConstellation (int spell, int slot) {
        List<List<Optional<ResourceLocation>>> copy = spells.stream()
            .map(ArrayList::new)
            .map(list -> (List<Optional<ResourceLocation>>) list)
            .collect(Collectors.toCollection(ArrayList::new));

        copy.get(spell).set(slot, Optional.empty());

        return new StarWandSpells(copy);
    }

    @Override 
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof StarWandSpells other)) return false;

        return spells.equals(other.spells);
    }

    @Override 
    public int hashCode() {
        return spells.hashCode();
    }
}
