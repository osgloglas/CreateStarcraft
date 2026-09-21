package com.souls.starcraft.spell;

import com.souls.starcraft.ModDataComponents;

import net.minecraft.world.item.ItemStack;

public interface SpellCastingItem {
    int getSpellCount(ItemStack stack);

    default boolean canCastSpells(ItemStack stack) {
        return getSpellCount(stack) > 0;
    }

    default int getSelectedSpell(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.SELECTED_SPELL, 0);
    }

    default void nextSpell(ItemStack stack) {
        int spellCount = getSpellCount(stack);
        if (spellCount <= 0) return;

        int current = getSelectedSpell(stack);
        int next = (current + 1) % spellCount;

        stack.set(ModDataComponents.SELECTED_SPELL, next);
    }

    default void previousSpell(ItemStack stack) {
        int spellCount = getSpellCount(stack);
        if (spellCount <= 0) return;

        int current = getSelectedSpell(stack);
        int previous = (current - 1 + spellCount) % spellCount;

        stack.set(ModDataComponents.SELECTED_SPELL, previous);
    }
}
