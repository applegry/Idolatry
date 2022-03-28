package com.velesgod.slaviccraft;

import com.velesgod.slaviccraft.core.init.ItemInit;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;


public class SlavicCraftTab extends CreativeModeTab{
	
	public static final SlavicCraftTab SlavicGroup = new SlavicCraftTab(CreativeModeTab.getGroupCountSafe(),"PaganCraft");
	public SlavicCraftTab(int index,String label) {
		super(index,label);

	}
	
	
	@Override
	public ItemStack makeIcon() {
		return new ItemStack(ItemInit.DANDELION_WREATH.get());
		
	}
		
}