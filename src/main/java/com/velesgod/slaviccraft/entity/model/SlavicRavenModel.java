package com.velesgod.slaviccraft.entity.model;

import com.velesgod.slaviccraft.SlavicCraftMod;
import com.velesgod.slaviccraft.entity.SlavicGhost;
import com.velesgod.slaviccraft.entity.SlavicLeshin;
import com.velesgod.slaviccraft.entity.SlavicRaven;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class SlavicRavenModel extends AnimatedTickingGeoModel<SlavicRaven> {
	@Override
	public ResourceLocation getAnimationFileLocation(SlavicRaven entity) {
		return new ResourceLocation(SlavicCraftMod.MOD_ID, "animations/raven.animation.json");
	}

	@Override
	public ResourceLocation getModelLocation(SlavicRaven entity) {
		return new ResourceLocation(SlavicCraftMod.MOD_ID, "geo/raven.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(SlavicRaven entity) {
		return new ResourceLocation(SlavicCraftMod.MOD_ID, "textures/entity/raven.png");
	}

	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	@Override
	public void setLivingAnimations(SlavicRaven entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("head");

		LivingEntity entityIn = (LivingEntity) entity;
		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		//head.setRotationX(extraData.headPitch*0.1f* ((float) Math.PI / 180F));
		//head.setRotationY(extraData.netHeadYaw *0.1f* ((float) Math.PI / 180F));
	}
}