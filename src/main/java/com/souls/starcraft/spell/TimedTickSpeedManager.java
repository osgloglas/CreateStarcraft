package com.souls.starcraft.spell;

import com.souls.starcraft.ModEffects;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.block.state.BlockState;

public class TimedTickSpeedManager {
    public static void tick(ServerPlayer serverPlayer) {
        MobEffectInstance effect = serverPlayer.getEffect(ModEffects.TIME_ACCELERATION);

        if (effect == null) {
            return;
        }

        int horologiumLevel = effect.getAmplifier() + 1;

        if (!(serverPlayer.level() instanceof ServerLevel level)) {
            return;
        }

        int radius = 8;
        int attempts = 16 * horologiumLevel;

        for (int i = 0; i < attempts; i++) {
            int x = serverPlayer.getBlockX() + level.random.nextInt(radius * 2 + 1) - radius;
            int y = serverPlayer.getBlockY() + level.random.nextInt(radius * 2 + 1) - radius;
            int z = serverPlayer.getBlockZ() + level.random.nextInt(radius * 2 + 1) - radius;

            BlockPos pos = new BlockPos(x, y, z);
            BlockState state = level.getBlockState(pos);

            if (state.isRandomlyTicking()) {
                state.randomTick(level, pos, level.random);
            }
        }

        if (level.random.nextInt(3) == 0) {
            level.sendParticles(ParticleTypes.ENCHANT,
                serverPlayer.getX() + (level.random.nextDouble() - 0.5) * 2.0,
                serverPlayer.getY() + level.random.nextDouble() * 2.0,
                serverPlayer.getZ() + (level.random.nextDouble() - 0.5) * 2.0,
                2, 0.25, 0.25, 0.25, 0.0);
        }
    }
}
