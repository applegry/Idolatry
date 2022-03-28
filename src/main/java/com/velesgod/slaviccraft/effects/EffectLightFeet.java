package com.velesgod.slaviccraft.effects;

import java.util.List;

import com.velesgod.slaviccraft.SlavicCraftMod;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class EffectLightFeet extends SlavicEffect {
	
	private final ResourceLocation ICON = new ResourceLocation(SlavicCraftMod.MOD_ID,"textures/effects/lightfeet.png");
	
	public Potion potion = new Potion(new MobEffectInstance(this));
	
	
	public EffectLightFeet() {
		super("light_feet", MobEffectCategory.BENEFICIAL, 0xE0FFFF);
		
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
	
	//	private final MobEffect PHANTOM_FIRE_RESISTENCE = Effects.FIRE_RESISTANCE.
	   @Override
	    public boolean isDurationEffectTick(int duration, int amplifier) {
	        return true;
	    }
	   
	    @Override
	    public void applyEffectTick(LivingEntity entity, int tick) {

	    }

	    
	    @Override
	    public void applyInstantenousEffect(Entity entity, Entity p_180793_2_, LivingEntity p_180793_3_,
	    		int p_180793_4_, double p_180793_5_) {
	    	
	    	super.applyInstantenousEffect(entity, p_180793_2_, p_180793_3_, p_180793_4_, p_180793_5_);
	    	
	    	
	    	
	    }
	    
	    
	    
	    

//	    
//	    @Override
//	    public void renderHUDEffect(MobEffectInstance effect, AbstractGui gui, MatrixStack mStack, int x, int y, float z,float alpha) {
//	    	
//		
//	    }
//	    
//	    @Override
//	    public void renderInventoryEffect(MobEffectInstance effect, DisplayEffectsScreen<?> gui, MatrixStack mStack, int x,
//	    		int y, float z) {
//	
//	    }
	    
	    public List<Entity> getEnts(Level w,double x, double y, double z,double radius) {
			return w.getEntitiesOfClass(Entity.class, new AABB(x,0,z,x+1,257,z+1).inflate(radius));
		}
	    
	    

	    
	    
	
	

}