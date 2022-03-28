/**
    Copyright (C) 2017 by jabelar
    This file is part of jabelar's Minecraft Forge modding examples; as such,
    you can redistribute it and/or modify it under the terms of the GNU
    General Public License as published by the Free Software Foundation,
    either version 3 of the License, or (at your option) any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.
    For a copy of the GNU General Public License see <http://www.gnu.org/licenses/>.
*/
package com.velesgod.slaviccraft.items;

import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;


// TODO: Auto-generated Javadoc
@OnlyIn(Dist.CLIENT)
public class ItemColor implements  net.minecraft.client.color.item.ItemColor
{
    public static final  net.minecraft.client.color.item.ItemColor INSTANCE = new ItemColor();
    public static Random rand = new Random(); 
 
    public static void registerItemColors()
    {
      //Minecraft.getInstance().getBlockColors().registerBlockColorHandler(INSTANCE, ModBlocks.cloud_leaves);
    }


	@Override
	public int getColor(ItemStack stack, int color) {
		return ((BaseItemColor) stack.getItem()).getColor();
	}
}






