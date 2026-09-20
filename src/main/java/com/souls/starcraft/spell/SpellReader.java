package com.souls.starcraft.spell;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;

public class SpellReader {
    public static List<ConstellationType> readSpell(Container container, int spell) {
        List<ConstellationType> components = new ArrayList<>();

        int startSlot = spell * 12;

        for (int i = 0; i < 12; i++) {
            ItemStack stack = container.getItem(startSlot + i);

            if (stack.isEmpty()) {
                continue;
            }

            ConstellationType type = ConstellationRegistry.get(stack);

            if (type != null) {
                components.add(type);
            }
        }

        return components;
    }

    public static List<ConstellationType> readSpell(ItemStack wand, int spell) {
        List<ConstellationType> components = new ArrayList<>();

        ItemContainerContents contents = wand.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);

        List<ItemStack> stacks = contents.stream().toList();

        int startSlot = spell * 12;

        for (int i = 0; i < 12; i++) {
            int index = startSlot + i;

            if (index >= stacks.size()) {
                continue;
            }

            ItemStack stack = stacks.get(index);

            if (stack.isEmpty()) {
                continue;
            }

            ConstellationType type = ConstellationRegistry.get(stack);

            if (type != null) {
                components.add(type);
            }
        }

        return components;
    }
}
