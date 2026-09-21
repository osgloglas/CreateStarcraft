package com.souls.starcraft.effect;

import com.souls.starcraft.StarCraft;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ReachEffect extends MobEffect {
    public ReachEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xB79CFF);

        this.addAttributeModifier(
            Attributes.BLOCK_INTERACTION_RANGE, 
            ResourceLocation.fromNamespaceAndPath(StarCraft.MODID, "cubus_building_reach"), 
            2.0,
            AttributeModifier.Operation.ADD_VALUE
        );
    }
}
