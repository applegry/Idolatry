package com.velesgod.slaviccraft.client.model.armor;

import com.velesgod.slaviccraft.SlavicCraftMod;
import com.velesgod.slaviccraft.items.AmuletItem;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AmuletModel extends AnimatedGeoModel<AmuletItem> {
	@Override
	public ResourceLocation getModelLocation(AmuletItem object) {
		return new ResourceLocation(SlavicCraftMod.MOD_ID, "geo/amulet.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(AmuletItem object) {
		return new ResourceLocation(SlavicCraftMod.MOD_ID, "textures/amulet/amber_amulet.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(AmuletItem animatable) {
		return new ResourceLocation(SlavicCraftMod.MOD_ID, "animations/amulet.animation.json");
	}
}