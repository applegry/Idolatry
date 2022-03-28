package com.velesgod.slaviccraft.items;

import com.velesgod.slaviccraft.SlavicCraftTab;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class EnchBirchBark extends Item
{
    public EnchBirchBark() {
    	 super(new Item.Properties().tab(SlavicCraftTab.SlavicGroup));
    }

    @Override
    public boolean isFoil(ItemStack p_77636_1_) {return true;}
}