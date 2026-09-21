package com.souls.starcraft;

import com.souls.starcraft.effect.FlightEffect;
import com.souls.starcraft.effect.LightSourceEffect;
import com.souls.starcraft.effect.MaxHealthEffect;
import com.souls.starcraft.effect.ReachEffect;
import com.souls.starcraft.effect.TimeAccelerationEffect;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = 
        DeferredRegister.create(Registries.MOB_EFFECT, StarCraft.MODID);

    public static final DeferredHolder<MobEffect, MobEffect> FLIGHT = MOB_EFFECTS.register(
        "flight", FlightEffect::new);

    public static final DeferredHolder<MobEffect, MobEffect> LIGHT_SOURCE = MOB_EFFECTS.register(
        "light_source", LightSourceEffect::new);

    public static final DeferredHolder<MobEffect, MobEffect> MAX_HEALTH = MOB_EFFECTS.register(
        "max_health", MaxHealthEffect::new);

    public static final DeferredHolder<MobEffect, MobEffect> BUILDING_REACH = MOB_EFFECTS.register(
        "building_reach", ReachEffect::new);

    public static final DeferredHolder<MobEffect, MobEffect> TIME_ACCELERATION = MOB_EFFECTS.register(
        "time_acceleration", TimeAccelerationEffect::new);
}
