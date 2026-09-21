package com.souls.starcraft.spell;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class TeleportSpellHandler {
    public static Vec3 findDestination(ServerPlayer serverPlayer, double maxDistance) {
        Vec3 start = serverPlayer.getEyePosition();
        Vec3 end = start.add(serverPlayer.getLookAngle().scale(maxDistance));

        BlockHitResult hit = serverPlayer.level().clip(new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, serverPlayer));

        if (hit.getType() != HitResult.Type.BLOCK) {
            return null;
        }

        BlockPos feetPos = hit.getBlockPos().above();
        BlockPos headPos = feetPos.above();

        boolean feetClear = serverPlayer.level()
            .getBlockState(feetPos)
            .getCollisionShape(serverPlayer.level(), feetPos)
            .isEmpty();

        boolean headClear = serverPlayer.level()
            .getBlockState(headPos)
            .getCollisionShape(serverPlayer.level(), headPos)
            .isEmpty();

        if (!feetClear || !headClear) {
            return null;
        }

        return Vec3.atBottomCenterOf(feetPos);
    }

    public static void teleport(ServerPlayer serverPlayer, Vec3 destination) {
        serverPlayer.teleportTo(destination.x, destination.y, destination.z);
    }
}
