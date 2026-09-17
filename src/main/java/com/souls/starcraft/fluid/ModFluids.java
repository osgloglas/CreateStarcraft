package com.souls.starcraft.fluid;

import com.souls.starcraft.StarCraft;
import com.souls.starcraft.block.ModBlocks;
import com.souls.starcraft.item.ModItems;

import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModFluids {
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(
        NeoForgeRegistries.Keys.FLUID_TYPES, StarCraft.MODID);

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(
        Registries.FLUID, StarCraft.MODID);

    public static final DeferredHolder<FluidType, FluidType> LIQUID_STARLIGHT_TYPE = FLUID_TYPES.register("liquid_starlight", () ->
        new FluidType(FluidType.Properties.create()
            .density(1000)
            .viscosity(1000)
            .temperature(300)
            .lightLevel(8)
            .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
            .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)));

    private static BaseFlowingFluid.Properties createLiquidStarlightProperties() {
        return new BaseFlowingFluid.Properties(LIQUID_STARLIGHT_TYPE, LIQUID_STARLIGHT, FLOWING_LIQUID_STARLIGHT)
            .block(ModBlocks.LIQUID_STARLIGHT_BLOCK)
            .bucket(ModItems.LIQUID_STARLIGHT_BUCKET);
    }

    public static final DeferredHolder<Fluid, BaseFlowingFluid.Source> LIQUID_STARLIGHT = FLUIDS.register("liquid_starlight", () ->
        new BaseFlowingFluid.Source(createLiquidStarlightProperties()));

    public static final DeferredHolder<Fluid, BaseFlowingFluid.Flowing> FLOWING_LIQUID_STARLIGHT = FLUIDS.register("flowing_liquid_starlight", () ->
        new BaseFlowingFluid.Flowing(createLiquidStarlightProperties()));
}
