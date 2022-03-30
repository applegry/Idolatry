package com.velesgod.slaviccraft.items;


import java.util.List;

import javax.annotation.Nullable;

import com.velesgod.slaviccraft.SlavicCraftMod;
import com.velesgod.slaviccraft.core.init.EffectInit;
import com.velesgod.slaviccraft.core.init.ItemInit;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.api.distmarker.Dist;

public class SlavicPotionBase extends Item{
	
	   public SlavicPotionBase(Item.Properties p_i48476_1_) {
		      super(p_i48476_1_);
		   }

		   public ItemStack getDefaultInstance() {
		      return PotionUtils.setPotion(super.getDefaultInstance(), Potions.WATER);
		   }

		   public ItemStack finishUsingItem(ItemStack stack, Level p_77654_2_, LivingEntity p_77654_3_) {
		      Player playerentity = p_77654_3_ instanceof Player ? (Player)p_77654_3_ : null;
		      if (playerentity instanceof ServerPlayer) {
		         CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer)playerentity, stack);
		      }
		    
		      if (!p_77654_2_.isClientSide) {
		         for(MobEffectInstance effectinstance : PotionUtils.getMobEffects(stack)) {
		            if (effectinstance.getEffect().isInstantenous()) {
		               effectinstance.getEffect().applyInstantenousEffect(playerentity, playerentity, p_77654_3_, effectinstance.getAmplifier(), 1.0D);
		            } else {
		               p_77654_3_.addEffect(new MobEffectInstance(effectinstance));
		            }
		         }
		      }

		      if (playerentity != null) {
		         playerentity.awardStat(Stats.ITEM_USED.get(this));
		         if (!playerentity.getAbilities().instabuild) {
		        	 stack.shrink(1);
		         }
		      }

		      if (playerentity == null || !playerentity.getAbilities().instabuild) {
		         if (stack.isEmpty()) {
		            return new ItemStack(ItemInit.ELIXIR_BOLLTE.get());
		         }

		         if (playerentity != null) {
		            playerentity.getInventory().add(new ItemStack(ItemInit.ELIXIR_BOLLTE.get()));
		         }
		      }

		      return stack;
		   }
		   
		   public int getUseDuration(ItemStack p_77626_1_) {
		      return 16;
		   }

		   
		   public UseAnim getUseAnimation(ItemStack p_77661_1_) {
		      return UseAnim.DRINK;
		   }
		
		   public InteractionResultHolder<ItemStack> use(Level p_77659_1_, Player p_77659_2_, InteractionHand p_77659_3_) {
			   System.out.print(getEffectName(p_77659_2_.getItemInHand(p_77659_3_))+"\n\n\n");
		      return ItemUtils.startUsingInstantly(p_77659_1_, p_77659_2_, p_77659_3_);
		   }

		   public String getDescriptionId(ItemStack stack) {
			
		      return new TranslatableComponent("item."+SlavicCraftMod.MOD_ID+".elixir.effect."+getEffectName(stack)).getString();
		   }

		   @OnlyIn(Dist.CLIENT)
		   public void appendHoverText(ItemStack p_77624_1_, @Nullable Level p_77624_2_, List<Component> p_77624_3_, TooltipFlag p_77624_4_) {
		      PotionUtils.addPotionTooltip(p_77624_1_, p_77624_3_, 1.0F);
		   }

		   public boolean isFoil(ItemStack stack) {
		      return true;
		   }
		   
		   
		   public static String getEffectName(ItemStack stack) {
			   CompoundTag nbt = stack.getTag();
			   ListTag listnbt = nbt.getList("CustomPotionEffects", 10);
			   String name = MobEffect.byId(((CompoundTag)listnbt.get(0)).getInt("Id")).getRegistryName().getPath();
			   return name;
		   }
		   
		   
		   
		   public static ItemStack getCustomElixir(ItemStack stack, MobEffectInstance effect) {
			      if (effect == null) {
			         return stack;
			      } else {
			    	  
			         CompoundTag nbt = stack.getOrCreateTag();
			         nbt.putInt("CustomPotionColor",effect.getEffect().getColor());
			         ListTag listnbt = nbt.getList("CustomPotionEffects", 9);
			         listnbt.add(effect.save(new CompoundTag()));
			         nbt.put("CustomPotionEffects", listnbt);
			         stack.setTag(nbt);
			         return stack;
			      }
			   }

		   public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> list) {
				if(allowdedIn(group)) {
				for(RegistryObject<MobEffect> effect: EffectInit.SlavicEffects.getEntries()) {

					list.add(getCustomElixir(new ItemStack(this),new MobEffectInstance(effect.get(),1200)));
				}
			
			//	list.add(getCustomElixir(new ItemStack(this),new EffectInstance(EffectInit.POISON_RESIST.get(),1200)));
					
				}
			   }
		   

		   
		   
		   
}