package com.souls.starcraft.fluid;

import org.antlr.v4.runtime.misc.NotNull;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;

public class LiquidStarlightClientExtensions implements IClientFluidTypeExtensions {
    @Override
    public @NotNull ResourceLocation getStillTexture() {
        return ResourceLocation.fromNamespaceAndPath("starcraft", "block/liquid_starlight_still");
    }

    @Override 
    public @NotNull ResourceLocation getFlowingTexture() {
        return ResourceLocation.fromNamespaceAndPath("starcraft", "block/liquid_starlight_flow");
    }
}
