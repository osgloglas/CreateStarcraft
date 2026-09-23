package com.souls.starcraft.block.custom.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.souls.starcraft.StarCraft;
import com.souls.starcraft.block.custom.entity.AttunementAltarBlockEntity;
import com.souls.starcraft.block.custom.entity.AttunementAltarOrbs;
import com.souls.starcraft.constellation.ConstellationPattern;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;

public class AttunementAltarRenderer implements BlockEntityRenderer<AttunementAltarBlockEntity> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(StarCraft.MODID, "textures/block/attunement_altar_orbs.png");
    private static final ResourceLocation BEAM_TEXTURE = ResourceLocation.fromNamespaceAndPath(StarCraft.MODID, "textures/misc/constellation_beam.png");

    private final AttunementAltarOrbs model;

    public AttunementAltarRenderer(BlockEntityRendererProvider.Context context) {
        this.model = new AttunementAltarOrbs(context.bakeLayer(AttunementAltarOrbs.LAYER_LOCATION));
    }

    @Override 
    public void render(
        AttunementAltarBlockEntity blockEntity,
        float partialTick,
        PoseStack poseStack,
        MultiBufferSource bufferSource,
        int packedLight,
        int packedOverlay
    ) {
        poseStack.pushPose();

        //center model
        poseStack.translate(0.5F, 0.0F, 0.5F);

        float ritualTime = blockEntity.getRitualTime(partialTick);
        model.applyAnimation(ritualTime);

        VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityCutout(TEXTURE));

        model.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, 0xFFFFFFF);

        ConstellationPattern constellation = blockEntity.getActiveConstellation();
        int rotation = blockEntity.getConstellationRotation();

        if (constellation != null && rotation != -1) {
            for (ConstellationPattern.Connection connection : constellation.connections()) {
                BlockPos firstStar = constellation.stars().get(connection.from());
                BlockPos secondStar = constellation.stars().get(connection.to());

                BlockPos first = rotateStar(firstStar, rotation);
                BlockPos second = rotateStar(secondStar, rotation);

                StarlightBeamRenderer.render(poseStack, bufferSource, first.getX(), 0.95F, first.getZ(), second.getX(), 0.95F, second.getZ());
            }
        }

        poseStack.popPose();
    }

    private BlockPos rotateStar(BlockPos star, int rotation) {
        int x = star.getX();
        int y = star.getY();
        int z = star.getZ();

        return switch (rotation) {
            case 0 -> new BlockPos(x, y, z);
            case 1 -> new BlockPos(-z, y, x);
            case 2 -> new BlockPos(-x, y, -z);
            case 3 -> new BlockPos(z, y, -x);
            default -> throw new IllegalArgumentException("Invalid Rotation");
        };
    }

    @Override 
    public AABB getRenderBoundingBox(AttunementAltarBlockEntity blockEntity) {
        return new AABB(blockEntity.getBlockPos()).inflate(16.0);
    }
}
