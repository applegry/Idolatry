package com.velesgod.slaviccraft.items;

import com.velesgod.slaviccraft.SlavicCraftTab;

import net.minecraft.world.item.Item;

import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Tiers;



public class ChiselItem extends TieredItem{

	public ChiselItem() {
		super(Tiers.WOOD, (new Item.Properties()).tab(SlavicCraftTab.SlavicGroup));
		 
	}
	
	
}