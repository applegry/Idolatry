package com.velesgod.slaviccraft.entity.model;

import com.velesgod.slaviccraft.SlavicCraftMod;
import com.velesgod.slaviccraft.entity.SlavicGhost;
import com.velesgod.slaviccraft.entity.SlavicLeshin;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class SlavicLeshinModel extends AnimatedTickingGeoModel<SlavicLeshin> {
	@Override
	public ResourceLocation getAnimationFileLocation(SlavicLeshin entity) {
		return new ResourceLocation(SlavicCraftMod.MOD_ID, "animations/leshiy.animation.json");
	}

	@Override
	public ResourceLocation getModelLocation(SlavicLeshin entity) {
		return new ResourceLocation(SlavicCraftMod.MOD_ID, "geo/leshiy.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(SlavicLeshin entity) {
		return new ResourceLocation(SlavicCraftMod.MOD_ID, "textures/entity/leshiy.png");
	}

	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	@Override
	public void setLivingAnimations(SlavicLeshin entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("head");
		if(entity.getState() != -1) {
		LivingEntity entityIn = (LivingEntity) entity;
		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
		head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
		}
	}
}