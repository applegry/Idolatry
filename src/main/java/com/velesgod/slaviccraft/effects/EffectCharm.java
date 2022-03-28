package com.velesgod.slaviccraft.effects;


import com.velesgod.slaviccraft.SlavicCraftMod;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.alchemy.Potion;


public class EffectCharm extends SlavicEffect {
	
	private final ResourceLocation ICON = new ResourceLocation(SlavicCraftMod.MOD_ID,"textures/effects/charm.png");
	
	public Potion potion = new Potion(new MobEffectInstance(this));
	
	
	public EffectCharm() {
		super("charm", MobEffectCategory.BENEFICIAL, 0x7CFC00);
		
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
	    	// TODO Auto-generated method stub
	    	super.applyEffectTick(entity, tick);

	    }

	    
	    @Override
	    public void applyInstantenousEffect(Entity entity, Entity p_180793_2_, LivingEntity p_180793_3_,
	    		int p_180793_4_, double p_180793_5_) {
	    	
	    	super.applyInstantenousEffect(entity, p_180793_2_, p_180793_3_, p_180793_4_, p_180793_5_);
	    	
	    	
	    	
	    }
	    
	    
	    
	    

	
	

}