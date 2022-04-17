package com.velesgod.slaviccraft.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.velesgod.slaviccraft.core.init.EffectInit;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MilkBucketItem;
import net.minecraft.world.level.Level;


@Mixin(MilkBucketItem.class)

public abstract class SlavicMilkBucket{

	
	
//	@Inject(at = @At("HEAD"),
 //    method = "Lnet/minecraft/world/item/MilkBucketItem;finishUsingItem(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;)Lnet/minecraft/world/item/ItemStack;")
	@Overwrite
   public ItemStack finishUsingItem(ItemStack p_42923_, Level p_42924_, LivingEntity p_42925_) {
		if(!p_42925_.hasEffect(EffectInit.FOREST_WRATH.get()))
      if (!p_42924_.isClientSide) p_42925_.curePotionEffects(p_42923_); 
      if (p_42925_ instanceof ServerPlayer) {
         ServerPlayer serverplayer = (ServerPlayer)p_42925_;
         CriteriaTriggers.CONSUME_ITEM.trigger(serverplayer, p_42923_);
         serverplayer.awardStat(Stats.ITEM_USED.get(p_42923_.getItem()));
      }

      if (p_42925_ instanceof Player && !((Player)p_42925_).getAbilities().instabuild) {
         p_42923_.shrink(1);
      }

      return p_42923_.isEmpty() ? new ItemStack(Items.BUCKET) : p_42923_;
   }


}
