package com.velesgod.slaviccraft.client.model.armor;

import com.velesgod.slaviccraft.SlavicCraftMod;
import com.velesgod.slaviccraft.items.LeshinAmuletItem;
import com.velesgod.slaviccraft.items.PowerAmuletItem;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class LeshinAmuletModel extends AnimatedGeoModel<LeshinAmuletItem> {
	@Override
	public ResourceLocation getModelLocation(LeshinAmuletItem object) {
		return new ResourceLocation(SlavicCraftMod.MOD_ID, "geo/amulet.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(LeshinAmuletItem object) {
		return new ResourceLocation(SlavicCraftMod.MOD_ID, "textures/amulet/leshin_amulet.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(LeshinAmuletItem animatable) {
		return new ResourceLocation(SlavicCraftMod.MOD_ID, "animations/amulet.animation.json");
	}
}