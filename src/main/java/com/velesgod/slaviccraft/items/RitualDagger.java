package com.velesgod.slaviccraft.items;

import java.util.Arrays;

import com.velesgod.slaviccraft.SlavicCraftTab;
import com.velesgod.slaviccraft.core.init.ItemInit;


import net.minecraft.core.particles.DustParticleOptions;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.state.BlockState;

public class RitualDagger extends TieredItem{

	public RitualDagger() {
	
		super(Tiers.STONE, new Item.Properties().tab(SlavicCraftTab.SlavicGroup));
		//attackDamageIn, attackSpeedIn, builder
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("rawtypes")
	public static EntityType[] DeadTypes = {EntityType.ZOMBIE,
											EntityType.HUSK,
											EntityType.ZOMBIE_VILLAGER,
											EntityType.ZOMBIE_HORSE,
											EntityType.DROWNED};
	@SuppressWarnings("rawtypes")
	public static EntityType[] LiveTypes = {EntityType.PLAYER,
									 		EntityType.VILLAGER,
									 		EntityType.PIG,
									 		EntityType.COW,
									 		EntityType.HORSE,
									 		EntityType.BAT,
									 		EntityType.RABBIT,
									 		EntityType.SHEEP,
									 		EntityType.CAT,
									 		EntityType.WOLF,
									 		EntityType.FOX,
									 		EntityType.CHICKEN,
									 		EntityType.WITCH,
									 		EntityType.WANDERING_TRADER,
									 		EntityType.DONKEY,
									 		EntityType.MULE,
									 		EntityType.OCELOT,
									 		EntityType.PANDA,
									 		EntityType.PARROT,
									 		EntityType.POLAR_BEAR,
									 		EntityType.DOLPHIN,
									 		EntityType.LLAMA};

	@Override
	public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
	

	
			if(!player.level.isClientSide()) {
			for (ItemStack itemStack : player.inventoryMenu.getItems()) {
			    if(itemStack.getItem() == ItemInit.BLOOD_BOTTLE.get())
			    {
			  
			    	int slot = player.inventoryMenu.getItems().indexOf(itemStack);
			    	itemStack.shrink(1);
			    	if(Arrays.asList(LiveTypes).contains( entity.getType())) {
			    			player.inventoryMenu.setItem(slot, slot, itemStack);  
			    			
			
			    			 ItemEntity itementity = player.drop(new ItemStack(ItemInit.LIVINGBLOOD_BOTTLE.get(),1), false);
			                 if (itementity != null) {
			                    itementity.setNoPickUpDelay();
			                    itementity.setOwner(player.getUUID());
			                 }
			    		//DO THIS	DustParticleOptions.REDSTONE
			    		//	entity.level.playSound(null, new BlockPos(player.getPosition(1.f)), SoundInit.BLOOD_GET.get(), SoundSource.MASTER, 3f, 1f);
			    			//player.level.addParticle(new RedstoneParticleData(0.4f, 0.f,0.0f, 1.1f),entity.getPosition(1).x(), entity.getPosition(1).y(), entity.getPosition(1).z(),0,0, 0);
			    			player.level.addParticle(DustParticleOptions.REDSTONE,entity.getPosition(1).x(), entity.getPosition(1).y(), entity.getPosition(1).z(),0,0, 0);
				    			
			    	}
			    	if(Arrays.asList(DeadTypes).contains( entity.getType())) {
		    			player.inventoryMenu.setItem(slot, slot, itemStack);  
		    			
		    			
		    			 ItemEntity itementity = player.drop(new ItemStack(ItemInit.DEADBLOOD_BOTTLE.get(),1), false);
		                 if (itementity != null) {
		                    itementity.setNoPickUpDelay();
		                    itementity.setOwner(player.getUUID());
		                 }
		               //DO THIS DustParticle(0.4f, 0.f,0.2f, 1.1f)
		    		//	entity.level.playSound(null, new BlockPos(player.getPosition(1.f)), SoundInit.BLOOD_GET.get(), SoundSource.MASTER, 3f, 1f);
		    		//	player.level.addParticle(new DustParticle(0.4f, 0.f,0.2f, 1.1f),entity.getPosition(1).x(), entity.getPosition(1).y(), entity.getPosition(1).z(),0,0, 0);
		    			player.level.addParticle(DustParticleOptions.REDSTONE,entity.getPosition(1).x(), entity.getPosition(1).y(), entity.getPosition(1).z(),0,0, 0);
			    		
			    	}
			    	 
			    	 
			    }
			}
			}

		return super.onLeftClickEntity(stack, player, entity);
	}

	@Override
	   public float getDestroySpeed(ItemStack p_150893_1_, BlockState p_150893_2_) {
		      return 1.5F;
	}


	
	
}