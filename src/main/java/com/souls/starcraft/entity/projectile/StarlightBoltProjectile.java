package com.souls.starcraft.entity.projectile;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class StarlightBoltProjectile extends Snowball{
    private float spellDamage = 2.0F;
    private int maxLifeTime = 5;
    private float aoeRadius = 0.0F;
    private int bombCount = 0;

    public StarlightBoltProjectile(Level level, LivingEntity owner) {
        super(level, owner);
    }

    public void setSpellDamage(double damage) {
        this.spellDamage = (float) damage;
    }

    public void setSpellRange(double range) {
        this.maxLifeTime = (int) (range * 5);
    }

    public void setAoeRadius(double radius) {
        this.aoeRadius = (float) radius;
    }

    public void setBombCount(int bombCount) {
        this.bombCount = bombCount;
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);

        if (!level().isClientSide()) {
            if (result instanceof EntityHitResult entityHit) {
                Entity target = entityHit.getEntity();

                target.hurt(damageSources().indirectMagic(this, getOwner()), spellDamage);
            }

            //aoe
            if (aoeRadius > 0.0F) {
                AABB area = getBoundingBox().inflate(aoeRadius);

                for (LivingEntity target : level().getEntitiesOfClass(LivingEntity.class, area, entity ->
                    entity != getOwner() && (!(result instanceof EntityHitResult hit) || entity != hit.getEntity())
                )) {
                    target.hurt(damageSources().indirectMagic(this, getOwner()), spellDamage);
                }
            }

            //bombs
            if (bombCount > 0) {
                for (int i = 0; i < bombCount; i++) {
                    level().explode(this, getX(), getY(), getZ(), 2.0F, Level.ExplosionInteraction.NONE);
                }
            }

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
