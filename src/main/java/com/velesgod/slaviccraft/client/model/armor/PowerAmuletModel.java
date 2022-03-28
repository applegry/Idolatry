package com.velesgod.slaviccraft.client.model.armor;

import com.velesgod.slaviccraft.SlavicCraftMod;
import com.velesgod.slaviccraft.items.PowerAmuletItem;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PowerAmuletModel extends AnimatedGeoModel<PowerAmuletItem> {
	@Override
	public ResourceLocation getModelLocation(PowerAmuletItem object) {
		return new ResourceLocation(SlavicCraftMod.MOD_ID, "geo/amulet.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(PowerAmuletItem object) {
		return new ResourceLocation(SlavicCraftMod.MOD_ID, "textures/amulet/power_amulet.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(PowerAmuletItem animatable) {
		return new ResourceLocation(SlavicCraftMod.MOD_ID, "animations/amulet.animation.json");
	}
}