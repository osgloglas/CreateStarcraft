package com.souls.starcraft.block.custom.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.souls.starcraft.StarCraft;
import com.souls.starcraft.block.custom.entity.AttunementAltarBlockEntity;
import com.souls.starcraft.block.custom.entity.AttunementAltarOrbs;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class AttunementAltarRenderer implements BlockEntityRenderer<AttunementAltarBlockEntity> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(StarCraft.MODID, "textures/block/attunement_altar_orbs.png");

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

        poseStack.popPose();
    }
}
