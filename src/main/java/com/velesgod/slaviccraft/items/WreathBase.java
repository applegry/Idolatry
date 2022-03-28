package com.velesgod.slaviccraft.items;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public class WreathBase extends ArmorItem{
	
    public WreathBase(ArmorMaterial materialIn,EquipmentSlot equipmentSlotIn)
    {
        super(materialIn,equipmentSlotIn,new Item.Properties().tab(CreativeModeTab.TAB_COMBAT));
    }
}