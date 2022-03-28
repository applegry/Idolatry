package com.velesgod.slaviccraft.items;

import java.util.List;
import java.util.Random;

import com.velesgod.slaviccraft.SlavicCraftTab;
import com.velesgod.slaviccraft.core.init.ParticleInit;


import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;


public class AmberStuff extends Item{

	public AmberStuff() {
		super(new Item.Properties().tab(SlavicCraftTab.SlavicGroup));
		
	} 



	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		createSphere(world,player,10,5);
		player.swing(hand);
		player.getCooldowns().addCooldown(this, 10);
		for(Entity e : getEnts(player, 5))
			if(e instanceof LivingEntity) {
				LivingEntity l = (LivingEntity)e;
				if(!l.hasEffect(MobEffects.HEAL)) l.addEffect(new MobEffectInstance(MobEffects.HEAL,1,0));
			}
		return super.use(world, player, hand);
		
	}

	
    public static List<Entity> getEnts(Player player, double radius) {
    	
    	List<Entity> entities = 
    			player.level.getEntities(player, new AABB(player.getX() - radius, 
    					player.getY() - radius, 
    					player.getZ() - radius, 
    					player.getX() + radius, 
    					player.getY() + radius, 
    					player.getZ() + radius));
        return entities;
    }
    
	    
	
	 private void createSphere(Level world,Entity ent,double speed, int size)
	    {
	        double d0 = ent.getX();
	        double d1 = ent.getY()+0.5f;
	        double d2 = ent.getZ();
	        Random rand = world.getRandom();

	        for (int i = -size; i <= size; ++i)
	        {
	            for (int j = -size; j <= size; ++j)
	            {
	                for (int k = -size; k <= size; ++k)
	                {
	                    double d3 = (double)j + (rand.nextDouble() - rand.nextDouble()) * 0.1D;
	                    double d4 = (double)i + (rand.nextDouble() - rand.nextDouble()) * 0.1D;
	                    double d5 = (double)k + (rand.nextDouble() - rand.nextDouble()) * 0.1D;
	                    double d6 = (double)Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5) / speed + rand.nextGaussian() * 5.2D;
	            //        world.addParticle(new RedstoneParticleData(0.8f, 0.f,0f, 1.1f),d0, d1, d2, d3 / d6, -d4 / d6, d5 / d6);

	                    world.addParticle(ParticleInit.AMBER_PARTICLE.get(),d0, d1, d2, d3 / d6, d4 / d6, d5 / d6);
	               //     world.addParticle(ParticleTypes.LAVA,d0, d1, d2, d3 / d6, d4 / d6, d5 / d6);
	                    
	                    
	                    if (i != -size && i != size && j != -size && j != size)
	                    {
	                        k += size * 2 - 1;
	                    }
	                }
	            }
	        }
	    }
	    
	    
}