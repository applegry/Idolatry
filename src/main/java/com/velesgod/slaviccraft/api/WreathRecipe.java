package com.velesgod.slaviccraft.api;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class WreathRecipe{
	Item result = Items.AIR;
	Item component1 = Items.AIR;
	Item component2 = Items.AIR;
	public WreathRecipe(Item c1,Item c2,Item r) {
		result = r;
		component1 = c1;
		component2 = c2;
	}
}