package com.velesgod.slaviccraft.items;

import net.minecraft.world.item.Item;

public class BaseItemColor extends Item{
	
	private int color = 0;
	
	public void setColor(int c) {
		color = c;
	}
	public int getColor() {
		return color;
	}
	
	public BaseItemColor(Properties prop) {
		super(prop);
		// TODO Auto-generated constructor stub
	}

}