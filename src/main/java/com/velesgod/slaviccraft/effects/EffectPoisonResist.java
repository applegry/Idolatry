package com.velesgod.slaviccraft.effects;



import com.velesgod.slaviccraft.SlavicCraftMod;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.alchemy.Potion;



public class EffectPoisonResist extends SlavicEffect {
	
	private final ResourceLocation ICON = new ResourceLocation(SlavicCraftMod.MOD_ID,"textures/effects/poison_resist.png");
	
	
	public Potion potion = new Potion(new MobEffectInstance(this));
	
	
	public EffectPoisonResist() {
		super("poison_resist", MobEffectCategory.BENEFICIAL, 0x55d9b1);
		
	}
	public double Angle = 0;

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
	
	//	private final MobEffect PHANTOM_FIRE_RESISTENCE = Effects.FIRE_RESISTANCE.
	   @Override
	    public boolean isDurationEffectTick(int duration, int amplifier) {
	        return true;
	    }
	   
	    @Override
	    public void applyEffectTick(LivingEntity entity, int tick) {
	    	// TODO Auto-generated method stub
	    	super.applyEffectTick(entity, tick);
	    //	if(entity.hasEffect(Effects.POISON)) {
	    		entity.removeEffect(MobEffects.POISON);
	    	//}
	    }

	 
	    
	    
	    
	    
	    
	   
	    
	    
	    
	    
	
	

}