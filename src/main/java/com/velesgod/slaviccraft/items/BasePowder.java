package com.velesgod.slaviccraft.items;

import com.velesgod.slaviccraft.SlavicCraftTab;

import net.minecraft.world.item.Item;




public class BasePowder extends BaseItemColor{

	public BasePowder(Properties prop) {
		super(prop);
		// TODO Auto-generated constructor stub
	}
	
	public BasePowder() {
		super(new Item.Properties().tab(SlavicCraftTab.SlavicGroup));
	} 
	
	public BasePowder(int color) {
		super(new Item.Properties().tab(SlavicCraftTab.SlavicGroup));
		this.setColor(color);
	} 
	

	
}