package com.velesgod.slaviccraft.items;

import com.velesgod.slaviccraft.SlavicCraftTab;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class BaseFood extends Item
{
    public BaseFood(int amount, float saturation)
    { 
    super(new Item.Properties().food((new FoodProperties.Builder()).saturationMod(saturation).nutrition(amount).build()).tab(SlavicCraftTab.SlavicGroup));
    }
    
    
    @Override
    public boolean isEdible() {
    	return true;
    }
}