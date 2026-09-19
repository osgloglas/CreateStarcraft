package com.souls.starcraft.block.custom.entity.renderer;

import java.util.Random;
import java.util.UUID;

import org.codehaus.plexus.util.dag.Vertex;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.souls.starcraft.StarCraft;
import com.souls.starcraft.client.CelestialGatewayClient;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

@EventBusSubscriber(
    modid = StarCraft.MODID,
    value = Dist.CLIENT,
    bus = EventBusSubscriber.Bus.GAME
)

public class CelestialGatewayRenderer {
    private static final int STAR_COUNT = 180;
    private static final long STAR_SEED = 4449L;

    private static final int [][] STAR_COLORS = {
        {255, 255, 255},
        {255, 244, 232},
        {255, 232, 208},
        {244, 247, 255},
        {232, 240, 255},
        {221, 233, 255}
    };

    @SubscribeEvent 
    public static void onRenderLevel(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_SKY) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.player == null) {
            return;
        }

        // Keep rendering while fadeProgress is above zero.
        // This will matter later when we restore the fade-out.
        if (CelestialGatewayClient.getFadeProgress() <= 0.0F) {
            return;
        }

        PoseStack poseStack = event.getPoseStack();

        poseStack.pushPose();
        poseStack.mulPose(minecraft.gameRenderer.getMainCamera().rotation().conjugate());

        MultiBufferSource.BufferSource buffers =
                minecraft.renderBuffers().bufferSource();

        renderBlackSphere(poseStack, buffers);
        renderStars(poseStack, buffers);
        renderGatewayStar(poseStack, buffers);

        poseStack.popPose();
    }

    private static void renderBlackSphere(
            PoseStack poseStack,
            MultiBufferSource.BufferSource buffers
    ) {
        float fade = CelestialGatewayClient.getFadeProgress();
        int alpha = (int) (fade * 255.0F);

        VertexConsumer consumer =
                buffers.getBuffer(RenderType.debugQuads());

        Matrix4f matrix = poseStack.last().pose();

        // Tiny sphere around the camera.
        // This was the radius that successfully hid the world.
        float radius = 0.5F;

        int latitudeSegments = 24;
        int longitudeSegments = 48;

        for (int lat = 0; lat < latitudeSegments; lat++) {
            double theta1 = Math.PI * lat / latitudeSegments;
            double theta2 = Math.PI * (lat + 1) / latitudeSegments;

            for (int lon = 0; lon < longitudeSegments; lon++) {
                double phi1 = 2.0 * Math.PI * lon / longitudeSegments;
                double phi2 = 2.0 * Math.PI * (lon + 1) / longitudeSegments;

                float x1 = (float) (radius * Math.sin(theta1) * Math.cos(phi1));
                float y1 = (float) (radius * Math.cos(theta1));
                float z1 = (float) (radius * Math.sin(theta1) * Math.sin(phi1));

                float x2 = (float) (radius * Math.sin(theta1)* Math.cos(phi2));
                float y2 = (float) (radius * Math.cos(theta1));
                float z2 = (float) (radius * Math.sin(theta1) * Math.sin(phi2));

                float x3 = (float) (radius * Math.sin(theta2) * Math.cos(phi2));
                float y3 = (float) (radius * Math.cos(theta2));
                float z3 = (float) (radius * Math.sin(theta2) * Math.sin(phi2));

                float x4 = (float) (radius * Math.sin(theta2) * Math.cos(phi1));
                float y4 = (float) (radius * Math.cos(theta2));
                float z4 = (float) (radius * Math.sin(theta2) * Math.sin(phi1));

                consumer.addVertex(matrix, x1, y1, z1)
                        .setColor(0, 0, 0, alpha);

                consumer.addVertex(matrix, x2, y2, z2)
                        .setColor(0, 0, 0, alpha);

                consumer.addVertex(matrix, x3, y3, z3)
                        .setColor(0, 0, 0, alpha);

                consumer.addVertex(matrix, x4, y4, z4)
                        .setColor(0, 0, 0, alpha);
            }
        }

        buffers.endBatch(RenderType.debugQuads());
    }

    private static void renderStars(PoseStack poseStack, MultiBufferSource.BufferSource buffers) {
        VertexConsumer consumer = buffers.getBuffer(RenderType.debugQuads());

        Matrix4f matrix = poseStack.last().pose();

        Random random = new Random(STAR_SEED);

        float radius = 0.48F;

        for (int i = 0; i < STAR_COUNT; i++) {
            double phi = random.nextDouble() * Math.PI * 2.0;
            double y = random.nextDouble() * 2.0 - 1.0;

            double horizontal = Math.sqrt(1.0 - y * y);

            float x = (float) (radius * horizontal * Math.cos(phi));

            float starY = (float) (radius * y);

            float z = (float) (radius * horizontal * Math.sin(phi));

            float size = 0.0015F + random.nextFloat() * 0.002F;

            int[] color = STAR_COLORS[random.nextInt(STAR_COLORS.length)];

            int red = color[0];
            int green = color[1];
            int blue = color[2];

            Vector3f normal = new Vector3f(x, starY, z).normalize();

            Vector3f reference = Math.abs(normal.y) < 0.9F ? new Vector3f(0.0F, 1.0F, 0.0F) : new Vector3f(1.0F, 0.0F, 0.0F);

            Vector3f right = new Vector3f(reference).cross(normal).normalize().mul(size);
            Vector3f up = new Vector3f(normal).cross(right).normalize().mul(size);

            //corners of the star
            Vector3f p1 = new Vector3f(x, starY, z).sub(right).sub(up);
            Vector3f p2 = new Vector3f(x, starY, z).add(right).sub(up);
            Vector3f p3 = new Vector3f(x, starY, z).add(right).add(up);
            Vector3f p4 = new Vector3f(x, starY, z).sub(right).add(up);

            consumer.addVertex(matrix, p1.x, p1.y, p1.z).setColor(red, green, blue, 255);
            consumer.addVertex(matrix, p2.x, p2.y, p2.z).setColor(red, green, blue, 255);
            consumer.addVertex(matrix, p3.x, p3.y, p3.z).setColor(red, green, blue, 255);
            consumer.addVertex(matrix, p4.x, p4.y, p4.z).setColor(red, green, blue, 255);
        }

        buffers.endBatch(RenderType.debugQuads());
    }

    private static void renderGatewayStar(PoseStack poseStack, MultiBufferSource.BufferSource buffers) {
        UUID gatewayId = CelestialGatewayClient.getDestinationGatewayId();

        if (gatewayId == null) {
            return;
        }

        VertexConsumer consumer = buffers.getBuffer(RenderType.debugQuads());

        Matrix4f matrix = poseStack.last().pose();

        //actual star
        BlockPos sourcePos = CelestialGatewayClient.getSourceGatewayPos();
        BlockPos destinationPos = CelestialGatewayClient.getDestinationGatewayPos();

        if (sourcePos == null || destinationPos == null) {
            return;
        }

        float dx = destinationPos.getX() - sourcePos.getX();
        float dy = destinationPos.getY() - sourcePos.getY();
        float dz = destinationPos.getZ() - sourcePos.getZ();

        Vector3f center = new Vector3f(dx, dy, dz);

        if (center.lengthSquared() == 0.0F) {
            return;
        }

        center.normalize().mul(0.47F);

        float size = 0.009F;

        Vector3f normal = new Vector3f(center).normalize();
        Vector3f reference = Math.abs(normal.y) < 0.9F ? new Vector3f(0, 1, 0) : new Vector3f(1, 0, 0);
        Vector3f right = new Vector3f(reference).cross(normal).normalize().mul(size);
        Vector3f up = new Vector3f(normal).cross(right).normalize().mul(size);

        Vector3f p1 = new Vector3f(center).sub(right).sub(up);
        Vector3f p2 = new Vector3f(center).add(right).sub(up);
        Vector3f p3 = new Vector3f(center).add(right).add(up);
        Vector3f p4 = new Vector3f(center).sub(right).add(up);

        consumer.addVertex(matrix, p1.x, p1.y, p1.z).setColor(180, 120, 255, 255);
        consumer.addVertex(matrix, p2.x, p2.y, p2.z).setColor(180, 120, 255, 255);
        consumer.addVertex(matrix, p3.x, p3.y, p3.z).setColor(180, 120, 255, 255);
        consumer.addVertex(matrix, p4.x, p4.y, p4.z).setColor(180, 120, 255, 255);

        buffers.endBatch(RenderType.debugQuads());
    }
}