package com.velesgod.slaviccraft.entity.model;

import com.velesgod.slaviccraft.SlavicCraftMod;
import com.velesgod.slaviccraft.entity.SlavicGhost;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class SlavicGhostModel extends AnimatedTickingGeoModel<SlavicGhost> {
	@Override
	public ResourceLocation getAnimationFileLocation(SlavicGhost entity) {
		return new ResourceLocation(SlavicCraftMod.MOD_ID, "animations/wraith.animation.json");
	}

	@Override
	public ResourceLocation getModelLocation(SlavicGhost entity) {
		return new ResourceLocation(SlavicCraftMod.MOD_ID, "geo/wraith.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(SlavicGhost entity) {
		return new ResourceLocation(SlavicCraftMod.MOD_ID, "textures/entity/wraith.png");
	}

	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	@Override
	public void setLivingAnimations(SlavicGhost entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("head");

		LivingEntity entityIn = (LivingEntity) entity;
		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
		head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
	}
}