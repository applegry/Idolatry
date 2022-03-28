package com.velesgod.slaviccraft.items;



import com.velesgod.slaviccraft.SlavicCraftTab;
import com.velesgod.slaviccraft.blocks.herbs.BaseHerb;


import net.minecraft.core.BlockPos;

import net.minecraft.world.InteractionResult;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.DoublePlantBlock;

import net.minecraft.world.level.block.state.BlockState;

public class Sickle extends TieredItem{

	public Sickle() {
	
		super(Tiers.STONE, new Item.Properties().tab(SlavicCraftTab.SlavicGroup));
		//attackDamageIn, attackSpeedIn, builder
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public InteractionResult useOn(UseOnContext context) {
		BlockPos pos = context.getClickedPos();
		Level world = context.getLevel();
		context.getPlayer().swing(context.getHand());

		if(world.getBlockState(pos).getBlock() instanceof BushBlock || 
				   world.getBlockState(pos).getBlock() instanceof BaseHerb  ||
				   world.getBlockState(pos).getBlock() instanceof DoublePlantBlock)
		for(int x = pos.getX()-2;x<pos.getX()+3;x++)
			for(int y = pos.getY()-2;y<pos.getY()+3;y++)
			for(int z = pos.getZ()-2;z<pos.getZ()+3;z++) {
				BlockPos p = new BlockPos(x,y,z);
				if(world.getBlockState(p).getBlock() instanceof BushBlock || 
				   world.getBlockState(p).getBlock() instanceof BaseHerb  ||
				   world.getBlockState(p).getBlock() instanceof DoublePlantBlock)
				{
					world.destroyBlock(p, true);

				}
				
			}
		
		
		return super.useOn(context);
	}
	


	@Override
	   public float getDestroySpeed(ItemStack p_150893_1_, BlockState p_150893_2_) {
		      return 0f;
	}

	

	
	
}