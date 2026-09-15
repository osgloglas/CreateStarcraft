package com.souls.starcraft.mana;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class StarlightMana {
    public static final Codec<StarlightMana> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.FLOAT.fieldOf("mana").forGetter(StarlightMana::getMana),
        Codec.FLOAT.fieldOf("max_mana").forGetter(StarlightMana::getMaxMana)
    ).apply(instance, StarlightMana::new));

    private float mana;
    private float maxMana;

    public StarlightMana() {
        this.maxMana = 100.0F;
        this.mana = maxMana;
    }

    public StarlightMana(float mana, float maxMana) {
        this.maxMana = Math.max(0.0F, maxMana);
        this.mana = Math.max(0.0F, Math.min(mana, this.maxMana));
    }

    public float getMana() {
        return mana;
    }

    public float getMaxMana() {
        return maxMana;
    }

    public void setMana(float amount) {
        mana = Math.max(0.0F, Math.min(amount, maxMana));
    }

    public void setMaxMana(float amount) {
        maxMana = Math.max(0.0F, amount);

        if (mana > maxMana) {
            mana = maxMana;
        }
    }

    public void addMana(float amount) {
        setMana(mana + amount);
    }

    public boolean consumeMana(float amount) {
        if (mana < amount) {
            return false;
        }

        setMana(mana - amount);
        return true;
    }
}
