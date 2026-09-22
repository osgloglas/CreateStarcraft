package com.souls.starcraft.block.custom.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.souls.starcraft.StarCraft;
import com.souls.starcraft.block.custom.entity.renderer.AttunementAltarAnimation;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class AttunementAltarOrbs extends Model {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(StarCraft.MODID, "attunement_altar_orbs"), "main");
	private final ModelPart root;
	private final ModelPart orbs;
	private final ModelPart straight;
	private final ModelPart diagonal;

	public AttunementAltarOrbs(ModelPart root) {
        super(RenderType::entityCutout);

		this.root = root.getChild("root");
		this.orbs = this.root.getChild("orbs");
		this.straight = this.orbs.getChild("straight");
		this.diagonal = this.orbs.getChild("diagonal");
	}

	public void applyAnimation(float time) {
		float[] rotation = AttunementAltarAnimation.getOrbRotation(time);
		float[] straightPos = AttunementAltarAnimation.getStraightPosition(time);
		float[] diagonalPos = AttunementAltarAnimation.getDiagonalPosition(time);

		orbs.xRot = radians(rotation[0]);
		orbs.yRot = radians(rotation[1]);
		orbs.zRot = radians(rotation[2]);

		straight.x = straightPos[0];
		straight.y = straightPos[1];
		straight.z = straightPos[2];

		diagonal.x = diagonalPos[0];
		diagonal.y = diagonalPos[1];
		diagonal.z = diagonalPos[2];
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, -13.0F));

		PartDefinition orbs = root.addOrReplaceChild("orbs", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 13.0F));

		PartDefinition straight = orbs.addOrReplaceChild("straight", CubeListBuilder.create().texOffs(8, 12).addBox(13.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(8, 4).addBox(-1.0F, -1.0F, -15.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(8, 8).addBox(-15.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 12).addBox(-1.0F, -1.0F, 13.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition diagonal = orbs.addOrReplaceChild("diagonal", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r1 = diagonal.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(8, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.0F, 1.0F, -10.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r2 = diagonal.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 4).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.0F, 1.0F, 10.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r3 = diagonal.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.0F, 1.0F, -10.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r4 = diagonal.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 8).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.0F, 1.0F, 10.0F, 0.0F, -0.7854F, 0.0F));

		return LayerDefinition.create(meshdefinition, 16, 16);
	}

	private static float radians(float degrees) {
		return degrees * ((float) Math.PI / 180.0F);
	}

    @Override 
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int packedColor) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, packedColor);
	}
}
