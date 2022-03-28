package com.velesgod.slaviccraft.items;

import com.velesgod.slaviccraft.SlavicCraftTab;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class BaseSeed extends Item{

	public Block crop = Blocks.WHEAT;
	public int level = 0; 
	public BaseSeed(Properties prop) {
		super(prop);
		// TODO Auto-generated constructor stub
	}
	
	
	public BaseSeed(Block crop,int l) {
		super(new Item.Properties().tab(SlavicCraftTab.SlavicGroup));
		this.crop = crop;
		this.level = l;
	}
	

	
	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level world = context.getLevel();
		BlockPos pos = context.getClickedPos();

		if(world.getBlockState(pos).getBlock() == Blocks.FARMLAND) {
			if(level==5) world.setBlock(pos.above(), crop.defaultBlockState().setValue(BlockStateProperties.AGE_5, 0), 12);
			if(level==3) world.setBlock(pos.above(), crop.defaultBlockState().setValue(BlockStateProperties.AGE_3, 0), 12);
			context.getPlayer().swing(context.getHand());
			world.playSound(context.getPlayer(), pos,SoundEvents.CROP_PLANTED, SoundSource.MASTER, 1.f, 1.f);
			if(context.getPlayer().isCreative()) return super.useOn(context);
			ItemStack stack = context.getPlayer().getItemInHand(context.getHand());
			stack.shrink(1);
			if(stack.getCount()>0)
			context.getPlayer().setItemInHand(context.getHand(), stack.copy());
			else
				context.getPlayer().setItemInHand(context.getHand(), ItemStack.EMPTY);
		}
		return super.useOn(context);
	}
	
	
	
	
	
}