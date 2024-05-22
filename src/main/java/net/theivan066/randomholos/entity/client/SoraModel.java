package net.theivan066.randomholos.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.theivan066.randomholos.entity.animations.ModAnimationDefinitions;
import net.theivan066.randomholos.entity.custom.SoraEntity;

public class SoraModel<T extends SoraEntity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	private final ModelPart sora;
	private final ModelPart head;

	public SoraModel(ModelPart root) {
		this.sora = root.getChild("sora");

		this.head = sora.getChild("full").getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition sora = partdefinition.addOrReplaceChild("sora", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition full = sora.addOrReplaceChild("full", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_leg = full.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-6.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -12.0F, 0.0F));

		PartDefinition right_pants = right_leg.addOrReplaceChild("right_pants", CubeListBuilder.create().texOffs(0, 32).addBox(-6.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_leg = full.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(16, 48).addBox(2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -12.0F, 0.0F));

		PartDefinition left_pants = left_leg.addOrReplaceChild("left_pants", CubeListBuilder.create().texOffs(0, 48).addBox(2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_arm = full.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-2.0F, -1.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -23.0F, 0.0F));

		PartDefinition right_sleve = right_arm.addOrReplaceChild("right_sleve", CubeListBuilder.create().texOffs(40, 32).addBox(-12.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(10.0F, 1.0F, 0.0F));

		PartDefinition left_arm = full.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0F, -1.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -23.0F, 0.0F));

		PartDefinition left_sleve = left_arm.addOrReplaceChild("left_sleve", CubeListBuilder.create().texOffs(48, 48).addBox(9.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-10.0F, 1.0F, 0.0F));

		PartDefinition body = full.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -24.0F, 0.0F));

		PartDefinition jacket = body.addOrReplaceChild("jacket", CubeListBuilder.create().texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head = full.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -24.0F, 0.0F));

		PartDefinition headwear = head.addOrReplaceChild("headwear", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition kamikazari = headwear.addOrReplaceChild("kamikazari", CubeListBuilder.create(), PartPose.offsetAndRotation(3.5F, -6.5F, -4.5F, -0.0524F, 0.0F, 0.3927F));

		PartDefinition leaf1 = kamikazari.addOrReplaceChild("leaf1", CubeListBuilder.create().texOffs(56, 44).addBox(-1.0F, -2.0F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(-0.4F))
				.texOffs(4, 74).addBox(-0.8F, -1.25F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.4F))
				.texOffs(4, 74).addBox(-0.8F, -1.75F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.4F))
				.texOffs(4, 74).addBox(-1.2F, -1.25F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.4F))
				.texOffs(4, 74).addBox(-1.2F, -1.75F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.4F))
				.texOffs(4, 74).addBox(-0.6F, -0.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.4F))
				.texOffs(13, 74).addBox(-0.6F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.4F))
				.texOffs(4, 74).addBox(-1.4F, -0.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.4F))
				.texOffs(13, 74).addBox(-1.4F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.4F)), PartPose.offset(0.5F, -2.5F, 0.0F));

		PartDefinition leaf2 = kamikazari.addOrReplaceChild("leaf2", CubeListBuilder.create().texOffs(13, 74).addBox(-1.4F, -1.5F, -0.45F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.4F))
				.texOffs(4, 74).addBox(-1.4F, -0.5F, -0.45F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.4F))
				.texOffs(13, 74).addBox(-0.6F, -1.5F, -0.45F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.4F))
				.texOffs(4, 74).addBox(-0.6F, -0.5F, -0.45F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.4F))
				.texOffs(4, 74).addBox(-1.2F, -1.75F, -0.45F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.4F))
				.texOffs(4, 80).addBox(-1.2F, -1.25F, -0.45F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.4F))
				.texOffs(4, 80).addBox(-0.8F, -1.25F, -0.45F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.4F))
				.texOffs(13, 79).addBox(-1.0F, -2.0F, -0.45F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.4F))
				.texOffs(4, 74).addBox(-0.8F, -1.75F, -0.45F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(1.0F, -1.5F, 0.0F, -0.0175F, 0.0F, 0.6109F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(SoraEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(entity, netHeadYaw, headPitch, ageInTicks);

		this.animateWalk(ModAnimationDefinitions.SORA_WALK, limbSwing, limbSwingAmount, 1f, 2.5f);
		this.animate(entity.idleAnimationState, ModAnimationDefinitions.SORA_IDLE, ageInTicks, 1f);
		this.animate(entity.attackAnimationState, ModAnimationDefinitions.SORA_ATTACK, ageInTicks, 1f);
	}

	private void applyHeadRotation(SoraEntity pEntity, float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
		this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		sora.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return sora;
	}
}