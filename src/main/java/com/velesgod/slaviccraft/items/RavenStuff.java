package com.velesgod.slaviccraft.items;

import com.velesgod.slaviccraft.SlavicCraftTab;
import com.velesgod.slaviccraft.core.init.ParticleInit;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RavenStuff extends Item{

	public RavenStuff() {
		super(new Item.Properties().tab(SlavicCraftTab.SlavicGroup));
	} 
	
	@Override
	public void onUseTick(Level world, LivingEntity entity, ItemStack stack, int tick) {

		super.onUseTick(world, entity, stack, tick);
	
	}
	
@Override
public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot,
		boolean bool) {
		Player player = (Player)entity;
		
	//	player.sendMessage(new StringTextComponent(String.valueOf(player.getUseItemRemainingTicks())), player.getUUID());
		
		if(player.isHolding(this) && stack.getOrCreateTag().getBoolean("isUsed")&& player.fallDistance > 0 && !player.isShiftKeyDown()) {
			player.setDeltaMovement(player.getDeltaMovement().x(),
					-0.07f,
					player.getDeltaMovement().z());
			createBall(world,player,-1);
		} else
		
		if(!player.isHolding(this) || player.isOnGround()){
			//player.fallDistance = 0;
			ItemStack st = stack;
			CompoundTag nbt = stack.getOrCreateTag();
			nbt.putBoolean("isUsed", false);
			st.setTag(nbt);
			player.getInventory().setItem(slot, st);
		}


	super.inventoryTick(stack, world, entity, slot, bool);
}


	

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		if(player.isOnGround() && player.fallDistance == 0) {
		player.setDeltaMovement(player.getDeltaMovement().x(),
				player.getDeltaMovement().y()+1.5f
				,player.getDeltaMovement().z());
			createBall(world,player,1);
			ItemStack stack = player.getItemInHand(hand);
			CompoundTag nbt = stack.getOrCreateTag();
			nbt.putBoolean("isUsed", true);
			stack.setTag(nbt);
			player.setItemInHand(hand, stack);
			player.getCooldowns().addCooldown(this, 200);
			world.addParticle(ParticleInit.RAVEN_PARTICLE.get(), player.getPosition(1).x(), player.getPosition(1).y(), player.getPosition(1).z(), 0, 0, 0);
		}
		return super.use(world, player, hand);
		
	}

    private void createBall(Level world,Entity ent,double size)
    {
        double d0 = ent.getX();
        double d1 = ent.getY()+size;
        size = Math.abs(size/2);
        double d2 = ent.getZ();
       // Random rand = world.getRandom();
       double angle = 0;
        for(float i = 0; i < 10; i += 1)
        {
              angle = i*36;
       
             
              double x1 = size * Math.cos(angle * Math.PI / 180);
              double z1 = size * Math.sin(angle * Math.PI / 180);
              world.addParticle(ParticleInit.RAVEN_PARTICLE.get(),d0+x1, d1+1, d2+z1, 0, 0, 0);
             // world.addParticle(ParticleTypes.ASH,d0+x1, d1+1, d2+z1, 0, 0, 0);
        }
    }
	
}