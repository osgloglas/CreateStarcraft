package com.souls.starcraft.recipe;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;

public class StarlightCrafterRecipe implements Recipe<CraftingInput> {
    private final ShapedRecipe internalRecipe;

    public StarlightCrafterRecipe(ShapedRecipe internalRecipe) {
        this.internalRecipe = internalRecipe;
    }

    public ShapedRecipe internalRecipe() {
        return internalRecipe;
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        return internalRecipe.matches(input, level);
    }

    @Override 
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        return internalRecipe.assemble(input, registries);
    }

    @Override 
    public boolean canCraftInDimensions(int width, int height) {
        return internalRecipe.canCraftInDimensions(width, height);
    }

    @Override 
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return internalRecipe.getResultItem(registries);
    }

    @Override 
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.STARLIGHT_CRAFTING_SERIALIZER.get();
    }

    @Override 
    public RecipeType<?> getType() {
        return ModRecipes.STARLIGHT_CRAFTING_TYPE.get();
    }
}
