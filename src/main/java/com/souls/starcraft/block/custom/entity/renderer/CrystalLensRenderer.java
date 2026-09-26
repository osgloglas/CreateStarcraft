package com.souls.starcraft.block.custom.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.souls.starcraft.block.custom.entity.CrystalLensBlockEntity;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;

public class CrystalLensRenderer implements BlockEntityRenderer<CrystalLensBlockEntity> {
    public CrystalLensRenderer(BlockEntityRendererProvider.Context context) {}

    @Override 
    public void render(CrystalLensBlockEntity lens, float partialTick, PoseStack poseStack, 
        MultiBufferSource bufferSource, int packedLight, int packedOverlay
    ) {
        if (!lens.isCrystalGrowing()) {
            return;
        }

        BlockPos crystalPos = lens.getGrowingCrystalPos();

        if (crystalPos == null) {
            return;
        }

        BlockPos lensPos = lens.getBlockPos();

        //convert crystal's world position into coords
        float crystalX = crystalPos.getX() - lensPos.getX() + 0.5F;
        float crystalY = crystalPos.getY() - lensPos.getY() + 0.5F;
        float crystalZ = crystalPos.getZ() - lensPos.getZ() + 0.5F;

        StarlightBeamRenderer.render(poseStack, bufferSource, 0.5F, 0.5F, 0.5F, crystalX, crystalY, crystalZ);
    }

    @Override 
    public boolean shouldRenderOffScreen(CrystalLensBlockEntity blockEntity) {
        return true;
    }

    @Override 
    public AABB getRenderBoundingBox(CrystalLensBlockEntity lens) {
        BlockPos lensPos = lens.getBlockPos();
        BlockPos crystalPos = lens.getGrowingCrystalPos();

        if (crystalPos == null) {
            return new AABB(lensPos);
        }

        return new AABB(
            Math.min(lensPos.getX(), crystalPos.getX()),
            Math.min(lensPos.getY(), crystalPos.getY()),
            Math.min(lensPos.getZ(), crystalPos.getZ()),
            Math.max(lensPos.getX(), crystalPos.getX()) + 1,
            Math.max(lensPos.getY(), crystalPos.getY()) + 1,
            Math.max(lensPos.getZ(), crystalPos.getZ()) + 1
        ).inflate(1.0);
    }
}
