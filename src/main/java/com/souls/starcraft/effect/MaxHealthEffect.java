package com.souls.starcraft.effect;

import com.souls.starcraft.StarCraft;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class MaxHealthEffect extends MobEffect {
    public MaxHealthEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFF6B8A);

        this.addAttributeModifier(Attributes.MAX_HEALTH, ResourceLocation.fromNamespaceAndPath(
            StarCraft.MODID, 
            "balaena_stellaris_health"), 
            2.0, 
            AttributeModifier.Operation.ADD_VALUE);
    }
}
