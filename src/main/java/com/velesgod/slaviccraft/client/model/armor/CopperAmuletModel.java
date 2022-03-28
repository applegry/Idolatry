package com.velesgod.slaviccraft.client.model.armor;

import com.velesgod.slaviccraft.SlavicCraftMod;
import com.velesgod.slaviccraft.items.CopperAmuletItem;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CopperAmuletModel extends AnimatedGeoModel<CopperAmuletItem> {
	@Override
	public ResourceLocation getModelLocation(CopperAmuletItem object) {
		return new ResourceLocation(SlavicCraftMod.MOD_ID, "geo/amulet.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(CopperAmuletItem object) {
		return new ResourceLocation(SlavicCraftMod.MOD_ID, "textures/amulet/copper_amulet.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(CopperAmuletItem animatable) {
		return new ResourceLocation(SlavicCraftMod.MOD_ID, "animations/amulet.animation.json");
	}
}