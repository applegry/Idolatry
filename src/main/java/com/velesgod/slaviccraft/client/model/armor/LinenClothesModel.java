package com.velesgod.slaviccraft.client.model.armor;

import com.velesgod.slaviccraft.SlavicCraftMod;
import com.velesgod.slaviccraft.items.AmuletItem;
import com.velesgod.slaviccraft.items.LinenClothes;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class LinenClothesModel extends AnimatedGeoModel<LinenClothes> {
	@Override
	public ResourceLocation getModelLocation(LinenClothes object) {
		return new ResourceLocation(SlavicCraftMod.MOD_ID, "geo/linen.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(LinenClothes object) {
		return new ResourceLocation(SlavicCraftMod.MOD_ID, "textures/amulet/linen.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(LinenClothes animatable) {
		return new ResourceLocation(SlavicCraftMod.MOD_ID, "animations/amulet.animation.json");
	}
}