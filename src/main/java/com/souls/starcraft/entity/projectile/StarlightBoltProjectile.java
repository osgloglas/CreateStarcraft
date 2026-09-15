package com.souls.starcraft.entity.projectile;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class StarlightBoltProjectile extends Snowball{
    public StarlightBoltProjectile(Level level, LivingEntity owner) {
        super(level, owner);
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);

        if (!level().isClientSide()) {
            level().explode(this, getX(), getY(), getZ(), 1.5F, Level.ExplosionInteraction.NONE);

            discard();
        }
    }
}
