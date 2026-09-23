package com.souls.starcraft.block.custom.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.souls.starcraft.StarCraft;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class TelescopeModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(StarCraft.MODID, "telescope"), "main");
	private final ModelPart tripod;
	private final ModelPart rotation;
	private final ModelPart lenses;

	public TelescopeModel(ModelPart root) {
		this.tripod = root.getChild("tripod");
		this.rotation = this.tripod.getChild("rotation");
		this.lenses = this.rotation.getChild("lenses");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tripod = partdefinition.addOrReplaceChild("tripod", CubeListBuilder.create().texOffs(32, 18).addBox(-4.0F, -5.0F, -3.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, 0.0F));

		PartDefinition cube_r1 = tripod.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(16, 38).addBox(-2.0F, -14.0F, 0.0F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 9.0F, 6.0F, 0.3927F, -0.7854F, 0.0F));

		PartDefinition cube_r2 = tripod.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(8, 38).addBox(-2.0F, -14.0F, 0.0F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, 9.0F, 5.0F, 0.3927F, 0.7854F, 0.0F));

		PartDefinition cube_r3 = tripod.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 38).addBox(-2.0F, -14.0F, 0.0F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 8.0F, -7.0F, -0.3927F, 0.0F, 0.0F));

		PartDefinition rotation = tripod.addOrReplaceChild("rotation", CubeListBuilder.create().texOffs(36, 12).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(36, 0).addBox(-5.0F, -4.0F, -1.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(36, 4).addBox(3.0F, -10.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 34).addBox(-6.0F, -9.5F, -0.75F, 12.0F, 1.5F, 1.5F, new CubeDeformation(0.0F))
		.texOffs(24, 38).addBox(-5.0F, -10.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, 1.0F));

		PartDefinition lenses = rotation.addOrReplaceChild("lenses", CubeListBuilder.create(), PartPose.offset(4.0F, -8.75F, 0.0F));

		PartDefinition cube_r4 = lenses.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -6.0F, 0.0F, 6.0F, 6.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 5.75F, -4.0F, 0.3927F, 0.0F, 0.0F));

		PartDefinition cube_r5 = lenses.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 18).addBox(-8.0F, -8.0F, 0.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.75F, 7.0F, 0.3927F, 0.0F, 0.0F));

		PartDefinition cube_r6 = lenses.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(32, 28).addBox(-4.0F, -4.0F, 0.0F, 4.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 7.75F, -11.0F, 0.3927F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		tripod.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}
}