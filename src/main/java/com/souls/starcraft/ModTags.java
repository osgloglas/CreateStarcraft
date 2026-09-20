package com.souls.starcraft;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTags {
    public static final TagKey<Item> CONSTELLATIONS = TagKey.create(
        Registries.ITEM, ResourceLocation.fromNamespaceAndPath(StarCraft.MODID, "constellations"));
}
