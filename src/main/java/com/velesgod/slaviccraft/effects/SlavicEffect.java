package com.velesgod.slaviccraft.effects;

import java.util.List;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.phys.AABB;

public class SlavicEffect extends MobEffect{
	public String name;
	
	public int color;
	
	public Potion potion = new Potion(new MobEffectInstance(this));
	
	public SlavicEffect(String name,MobEffectCategory type,int c) {
		super(type, c);

	}
	
	 public boolean hasEffect(LivingEntity entity) {

	        return hasEffect(entity, this);

	    }



	    public boolean hasEffect(LivingEntity entity, MobEffect effect) {

	        return entity.getEffect(effect) != null;
	        
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
	    
	    
}