package com.souls.starcraft.block.custom.entity.renderer;

import org.joml.Matrix4f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.souls.starcraft.StarCraft;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class StarlightBeamRenderer {
    public static final ResourceLocation BEAM_TEXTURE = ResourceLocation.fromNamespaceAndPath(
        StarCraft.MODID, "textures/misc/constellation_beam.png");

    public static void render(PoseStack poseStack,
            MultiBufferSource bufferSource,
            float x1, float y1, float z1,
            float x2, float y2, float z2
    ) {
        VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityTranslucentEmissive(BEAM_TEXTURE));

        Matrix4f matrix = poseStack.last().pose();

        float dx = x2 - x1;
        float dz = z2 - z1;

        float length = (float) Math.sqrt(dx * dx + dz * dz);
        if (length == 0.0F) return;

        float sideX = -dz / length;
        float sideZ = dx / length;

        float radius = 0.06F;

        float sx = sideX * radius;
        float sz = sideZ * radius;

        // Horizontal ribbon
        addVertex(consumer, matrix, x1 + sx, y1, z1 + sz, 0.0F, 0.0F);
        addVertex(consumer, matrix, x1 - sx, y1, z1 - sz, 0.0F, 1.0F);
        addVertex(consumer, matrix, x2 - sx, y2, z2 - sz, 1.0F, 1.0F);
        addVertex(consumer, matrix, x2 + sx, y2, z2 + sz, 1.0F, 0.0F);

        // Vertical ribbon
        addVertex(consumer, matrix, x1, y1 + radius, z1, 0.0F, 0.0F);
        addVertex(consumer, matrix, x1, y1 - radius, z1, 0.0F, 1.0F);
        addVertex(consumer, matrix, x2, y2 - radius, z2, 1.0F, 1.0F);
        addVertex(consumer, matrix, x2, y2 + radius, z2, 1.0F, 0.0F);
    }

    private static void addVertex(
            VertexConsumer consumer,
            Matrix4f matrix,
            float x, float y, float z, float u, float v
    ) {
        consumer.addVertex(matrix, x, y, z)
                .setColor(255, 255, 255, 255)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(0xF000F0)
                .setNormal(0.0F, 1.0F, 0.0F);
    }
}
