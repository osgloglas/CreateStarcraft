package com.souls.starcraft.recipe;

import java.util.function.Supplier;

import com.souls.starcraft.StarCraft;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
        DeferredRegister.create(Registries.RECIPE_TYPE, StarCraft.MODID);

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
        DeferredRegister.create(Registries.RECIPE_SERIALIZER, StarCraft.MODID);

    public static final Supplier<RecipeType<StarlightCrafterRecipe>> STARLIGHT_CRAFTING_TYPE =
        RECIPE_TYPES.register("starlight_crafting", () -> new RecipeType<StarlightCrafterRecipe>() {
            @Override 
            public String toString() {
                return StarCraft.MODID + ":starlight_crafting";
            }
        });

    public static final Supplier<RecipeSerializer<StarlightCrafterRecipe>> STARLIGHT_CRAFTING_SERIALIZER =
        RECIPE_SERIALIZERS.register("starlight_crafting", StarlightCrafterRecipeSerializer::new);

    private ModRecipes() {}
}
