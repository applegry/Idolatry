package com.velesgod.slaviccraft.items;

import java.util.List;

import com.velesgod.slaviccraft.SlavicCraftTab;

import net.minecraft.ChatFormatting;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;


public class BaseBalm extends Item{
	
	MobEffectInstance effect;
	
	
	public BaseBalm(MobEffect eff,int timedur) {
		super(new Item.Properties().tab(SlavicCraftTab.SlavicGroup));
		effect = new MobEffectInstance(eff,timedur);
	}
	
	@Override
	public void appendHoverText(ItemStack p_77624_1_, Level p_77624_2_, List<Component> txt,
			TooltipFlag flag) {
		// TODO Auto-generated method stub
		txt.add(new TranslatableComponent("item.slaviccraft."+this.getRegistryName().getPath()+".effect").withStyle(ChatFormatting.GOLD));
		super.appendHoverText(p_77624_1_, p_77624_2_, txt, flag);
	}
	public BaseBalm() {
		super(new Item.Properties().tab(SlavicCraftTab.SlavicGroup));
	}


	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		
		
		if(player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof SwordItem){
			ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND).copy();
			CompoundTag nbt = stack.getOrCreateTag();
			nbt.putBoolean("balmed", true);
			nbt.putInt("balm_value", 13);
			nbt.putInt("balm_effect", MobEffect.getId(MobEffects.GLOWING));
		    stack.setTag(nbt);
			player.setItemInHand(InteractionHand.MAIN_HAND,stack);
			player.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
		}
		
		return super.use(world, player, hand);
	}


}





