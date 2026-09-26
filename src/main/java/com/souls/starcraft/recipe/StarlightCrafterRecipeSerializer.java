package com.souls.starcraft.recipe;

import com.mojang.serialization.MapCodec;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;

public class StarlightCrafterRecipeSerializer implements RecipeSerializer<StarlightCrafterRecipe> {
    public static final MapCodec<StarlightCrafterRecipe> CODEC = ShapedRecipe.Serializer.CODEC.xmap(
        StarlightCrafterRecipe::new, recipe -> recipe.internalRecipe());

    public static final StreamCodec<RegistryFriendlyByteBuf, StarlightCrafterRecipe> STREAM_CODEC = 
        ShapedRecipe.Serializer.STREAM_CODEC.map(StarlightCrafterRecipe::new, recipe -> recipe.internalRecipe());

    @Override 
    public MapCodec<StarlightCrafterRecipe> codec() {
        return CODEC;
    }

    @Override 
    public StreamCodec <RegistryFriendlyByteBuf, StarlightCrafterRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
