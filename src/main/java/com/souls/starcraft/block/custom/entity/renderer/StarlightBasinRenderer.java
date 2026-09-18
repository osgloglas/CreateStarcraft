package com.souls.starcraft.block.custom.entity.renderer;

import org.joml.Matrix4f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.souls.starcraft.block.custom.entity.StarlightBasinBlockEntity;
import com.souls.starcraft.item.ModItems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class StarlightBasinRenderer implements BlockEntityRenderer<StarlightBasinBlockEntity> {
    private final ItemRenderer itemRenderer;

    public StarlightBasinRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }
    
    @Override 
        public void render(
            StarlightBasinBlockEntity basin,
            float partialTick,
            PoseStack poseStack,
            MultiBufferSource bufferSource,
            int packedLight,
            int packedOverlay
        ) {
            float fillPercent = (float) basin.getFluidTank().getFluidAmount() / basin.getFluidTank().getCapacity();

                if (basin.hasAquamarine()) {
                poseStack.pushPose();

                float time = basin.getLevel().getGameTime() + partialTick;
                float bob = (float) Math.sin(time * 0.1F) * 0.05F;

                poseStack.translate(0.5, 1.25 + bob, 0.5);

                float rotation = time * 2.0F;

                poseStack.mulPose(Axis.YP.rotationDegrees(rotation));

                ItemStack aquamarine = new ItemStack(ModItems.AQUAMARINE.get());

                itemRenderer.renderStatic(aquamarine, ItemDisplayContext.GROUND, packedLight, packedOverlay, poseStack, bufferSource, basin.getLevel(), 0);

                poseStack.popPose();
                }

                if (fillPercent > 0.0F) {
                    TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS)
                        .apply(ResourceLocation.fromNamespaceAndPath("starcraft", "block/liquid_starlight_still"));

                    float u0 = sprite.getU0();
                    float u1 = sprite.getU1();
                    float v0 = sprite.getV0();
                    float v1 = sprite.getV1();

                    poseStack.pushPose();

                    float liquidY = 0.35F + (fillPercent * 0.5F);

                    poseStack.translate(0.0F, liquidY, 0.0F);

                    VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityCutout(InventoryMenu.BLOCK_ATLAS));

                    Matrix4f matrix = poseStack.last().pose();

                    consumer.addVertex(matrix, 0.15F, 0.0F, 0.15F)
                        .setUv(u0, v0)
                        .setColor(255, 255, 255, 255)
                        .setLight(0xF000F0)
                        .setOverlay(packedOverlay)
                        .setNormal(0.0F, 1.0F, 0.0F);

                    consumer.addVertex(matrix, 0.15F, 0.0F, 0.85F)
                        .setUv(u0, v1)
                        .setColor(255, 255, 255, 255)
                        .setLight(0xF000F0)
                        .setOverlay(packedOverlay)
                        .setNormal(0.0F, 1.0F, 0.0F);

                    consumer.addVertex(matrix, 0.85F, 0.0F, 0.85F)
                        .setUv(u1, v1)
                        .setColor(255, 255, 255, 255)
                        .setLight(0xF000F0)
                        .setOverlay(packedOverlay)
                        .setNormal(0.0F, 1.0F, 0.0F);

                    consumer.addVertex(matrix, 0.85F, 0.0F, 0.15F)
                        .setUv(u1, v0)
                        .setColor(255, 255, 255, 255)
                        .setLight(0xF000F0)
                        .setOverlay(packedOverlay)
                        .setNormal(0.0F, 1.0F, 0.0F);


                    //sidewalls
                    float bottomY = -liquidY + 0.15F;

                    //north
                    consumer.addVertex(matrix, 0.85F, bottomY, 0.15F)
                        .setUv(u1, v1)
                        .setColor(255, 255, 255, 255)
                        .setLight(0xF000F0)
                        .setOverlay(packedOverlay)
                        .setNormal(0.0F, 0.0F, -1.0F);

                    consumer.addVertex(matrix, 0.15F, bottomY, 0.15F)
                        .setUv(u0, v1)
                        .setColor(255, 255, 255, 255)
                        .setLight(0xF000F0)
                        .setOverlay(packedOverlay)
                        .setNormal(0.0F, 0.0F, -1.0F);

                    consumer.addVertex(matrix, 0.15F, 0.0F, 0.15F)
                        .setUv(u0, v0)
                        .setColor(255, 255, 255, 255)
                        .setLight(0xF000F0)
                        .setOverlay(packedOverlay)
                        .setNormal(0.0F, 0.0F, -1.0F);

                    consumer.addVertex(matrix, 0.85F, 0.0F, 0.15F)
                        .setUv(u0, v0)
                        .setColor(255, 255, 255, 255)
                        .setLight(0xF000F0)
                        .setOverlay(packedOverlay)
                        .setNormal(0.0F, 0.0F, -1.0F);

                    //south
                    consumer.addVertex(matrix, 0.15F, bottomY, 0.85F)
                        .setUv(u1, v1)
                        .setColor(255, 255, 255, 255)
                        .setLight(0xF000F0)
                        .setOverlay(packedOverlay)
                        .setNormal(0.0F, 0.0F, 1.0F);

                    consumer.addVertex(matrix, 0.85F, bottomY, 0.85F)
                        .setUv(u0, v1)
                        .setColor(255, 255, 255, 255)
                        .setLight(0xF000F0)
                        .setOverlay(packedOverlay)
                        .setNormal(0.0F, 0.0F, 1.0F);

                    consumer.addVertex(matrix, 0.85F, 0.0F, 0.85F)
                        .setUv(u0, v0)
                        .setColor(255, 255, 255, 255)
                        .setLight(0xF000F0)
                        .setOverlay(packedOverlay)
                        .setNormal(0.0F, 0.0F, 1.0F);

                    consumer.addVertex(matrix, 0.15F, 0.0F, 0.85F)
                        .setUv(u1, v0)
                        .setColor(255, 255, 255, 255)
                        .setLight(0xF000F0)
                        .setOverlay(packedOverlay)
                        .setNormal(0.0F, 0.0F, 1.0F);

                    //east
                    consumer.addVertex(matrix, 0.85F, bottomY, 0.85F)
                        .setUv(u1, v1)
                        .setColor(255, 255, 255, 255)
                        .setLight(0xF000F0)
                        .setOverlay(packedOverlay)
                        .setNormal(1.0F, 0.0F, 0.0F);

                    consumer.addVertex(matrix, 0.85F, bottomY, 0.15F)
                        .setUv(u0, v1)
                        .setColor(255, 255, 255, 255)
                        .setLight(0xF000F0)
                        .setOverlay(packedOverlay)
                        .setNormal(1.0F, 0.0F, 0.0F);

                    consumer.addVertex(matrix, 0.85F, 0.0F, 0.15F)
                        .setUv(u0, v0)
                        .setColor(255, 255, 255, 255)
                        .setLight(0xF000F0)
                        .setOverlay(packedOverlay)
                        .setNormal(1.0F, 0.0F, 0.0F);

                    consumer.addVertex(matrix, 0.85F, 0.0F, 0.85F)
                        .setUv(u1, v0)
                        .setColor(255, 255, 255, 255)
                        .setLight(0xF000F0)
                        .setOverlay(packedOverlay)
                        .setNormal(1.0F, 0.0F, 0.0F);

                    //west
                    consumer.addVertex(matrix, 0.15F, bottomY, 0.15F)
                        .setUv(u1, v1)
                        .setColor(255, 255, 255, 255)
                        .setLight(0xF000F0)
                        .setOverlay(packedOverlay)
                        .setNormal(-1.0F, 0.0F, 0.0F);

                    consumer.addVertex(matrix, 0.15F, bottomY, 0.85F)
                        .setUv(u0, v1)
                        .setColor(255, 255, 255, 255)
                        .setLight(0xF000F0)
                        .setOverlay(packedOverlay)
                        .setNormal(-1.0F, 0.0F, 0.0F);

                    consumer.addVertex(matrix, 0.15F, 0.0F, 0.85F)
                        .setUv(u0, v0)
                        .setColor(255, 255, 255, 255)
                        .setLight(0xF000F0)
                        .setOverlay(packedOverlay)
                        .setNormal(-1.0F, 0.0F, 0.0F);

                    consumer.addVertex(matrix, 0.15F, 0.0F, 0.15F)
                        .setUv(u1, v0)
                        .setColor(255, 255, 255, 255)
                        .setLight(0xF000F0)
                        .setOverlay(packedOverlay)
                        .setNormal(-1.0F, 0.0F, 0.0F);

                    poseStack.popPose();
                }
    }
}
