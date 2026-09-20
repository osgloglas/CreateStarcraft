package com.souls.starcraft.entity.projectile;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class StarlightBoltProjectile extends Snowball{
    private float spellDamage = 1.5F;
    private int maxLifeTime = 5;

    public StarlightBoltProjectile(Level level, LivingEntity owner) {
        super(level, owner);
    }

    public void setSpellDamage(double damage) {
        this.spellDamage = (float) damage;
    }

    public void setSpellRange(double range) {
        this.maxLifeTime = (int) (range * 5);
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);

        if (!level().isClientSide()) {
            level().explode(this, getX(), getY(), getZ(), spellDamage, Level.ExplosionInteraction.NONE);

            discard();
        }
    }

    @Override 
    public void tick() {
        super.tick();

        if (!level().isClientSide && this.tickCount >= maxLifeTime) {
            discard();
        }
    }
}
