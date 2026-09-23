package com.souls.starcraft.block.custom.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.souls.starcraft.StarCraft;
import com.souls.starcraft.block.custom.TelescopeBlock;
import com.souls.starcraft.block.custom.entity.TelescopeBlockEntity;
import com.souls.starcraft.block.custom.entity.TelescopeModel;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class TelescopeRenderer implements BlockEntityRenderer<TelescopeBlockEntity> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(StarCraft.MODID, "textures/block/telescope.png");

    private final TelescopeModel<Entity> model;

    public TelescopeRenderer(BlockEntityRendererProvider.Context context) {
        this.model = new TelescopeModel<>(context.bakeLayer(TelescopeModel.LAYER_LOCATION));
    }

    @Override 
    public void render(TelescopeBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();

        poseStack.translate(0.5F, 1.5F, 0.5F);

        Direction facing = blockEntity.getBlockState().getValue(TelescopeBlock.FACING);

        float rotation = switch (facing) {
            case NORTH -> 0.0F;
            case EAST -> 270.0F;
            case SOUTH -> 180.0F;
            case WEST -> 90.0F;
            default -> 0.0F;
        };

        poseStack.mulPose(Axis.YP.rotationDegrees(rotation));

        poseStack.scale(1.0F, -1.0F, -1.0F);

        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutout(TEXTURE));

        model.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay, 0xFFFFFFF);

        poseStack.popPose();
    }
}
