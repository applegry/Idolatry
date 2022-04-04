package com.velesgod.slaviccraft.items;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class WreathBase extends ArmorItem{
	
    public WreathBase(ArmorMaterial materialIn,EquipmentSlot equipmentSlotIn,Properties prop)
    {
        super(materialIn,equipmentSlotIn,prop);
    }
    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
    	context.getPlayer().addEffect(new MobEffectInstance(MobEffects.ABSORPTION,100,10));
    	return super.onItemUseFirst(stack, context);
    }
    @Override
    public void onArmorTick(ItemStack stack, Level world, Player player) {
    	player.heal(0.1f);
  
    	if(!player.hasEffect(MobEffects.ABSORPTION))
    		player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION,20*5,10));
    	super.onArmorTick(stack, world, player);
    }
}