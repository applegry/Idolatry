package com.velesgod.slaviccraft.effects;

import java.util.List;
import java.util.Random;

import com.velesgod.slaviccraft.SlavicCraftMod;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class EffectFear extends SlavicEffect {
	
	private final ResourceLocation ICON = new ResourceLocation(SlavicCraftMod.MOD_ID,"textures/effects/fear.png");
	
	public Potion potion = new Potion(new MobEffectInstance(this));
	
	
	public EffectFear() {
		super("fear", MobEffectCategory.NEUTRAL, 0xF0F0F0);
		
	}


	public boolean shouldRenderHUD(MobEffectInstance effect) {
		// TODO Auto-generated method stub
		return true;
	}
	

	public boolean shouldRenderInvText(MobEffectInstance effect) {
		// TODO Auto-generated method stub
		return true;
	}
	public ResourceLocation getICON() {
		return ICON;
	
	}
	
	

	public boolean shouldRender(MobEffectInstance effect) {
		// TODO Auto-generated method stub
		return true;
	}
	
	//	private final Effect PHANTOM_FIRE_RESISTENCE = Effects.FIRE_RESISTANCE.
	   @Override
	    public boolean isDurationEffectTick(int duration, int amplifier) {
	        return true;
	    }
	   
	    @Override
	    public void applyEffectTick(LivingEntity entity, int tick) {
	    		if(entity instanceof Monster) {
	    			((Monster)entity).getNavigation().setSpeedModifier(0.1f);
	    			
	    		}
	    		
	    }

	    
	    @Override
	    public void applyInstantenousEffect(Entity entity, Entity p_180793_2_, LivingEntity p_180793_3_,
	    		int p_180793_4_, double p_180793_5_) {
	    	
	    	super.applyInstantenousEffect(entity, p_180793_2_, p_180793_3_, p_180793_4_, p_180793_5_);

	    }
	    
	    
	    

}