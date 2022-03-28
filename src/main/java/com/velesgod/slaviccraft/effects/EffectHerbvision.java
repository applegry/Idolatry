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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class EffectHerbvision extends SlavicEffect {
	
	private final ResourceLocation ICON = new ResourceLocation(SlavicCraftMod.MOD_ID,"textures/effects/herbvision.png");
	
	public Potion potion = new Potion(new MobEffectInstance(this));
	
	
	public EffectHerbvision() {
		super("herbvision", MobEffectCategory.BENEFICIAL, 0x05F010);
		
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
	    		
	    		if(entity instanceof Player) {
	    			Player player = (Player)entity;
	    			BlockPos pos = new BlockPos(player.getPosition(1.f));
	    			Random r = player.level.random;
	    		for(int x=-5;x<=6;x++)
	    			for(int z=-5;z<=6;z++) {
	    				BlockPos tmp =new BlockPos(pos.getX()+x,pos.getY(),pos.getZ()+z);
	    				BlockState state = player.level.getBlockState(tmp);
	    				double d = pos.distSqr(tmp);
	    				if(d<21 && (state.getBlock() instanceof FlowerBlock || state.getBlock() instanceof TallFlowerBlock )) {
	    					
	    				      for(int i = 0; i < 1; ++i) {
	    					         double d3 = r.nextGaussian() * 0.02D;
	    					         double d4 = r.nextGaussian() * 0.02D;
	    					         double d5 = r.nextGaussian() * 0.02D;
	    					         player.level.addParticle(
	    					        		 ParticleTypes.END_ROD, 
	    					        		 (double)tmp.getX() + (double)0.13125F + (double)0.7375F * (double)r.nextFloat(), 
	    					        		 (double)tmp.getY() + 0.5D + (double)r.nextFloat() * 0.5D, 
	    					        		 (double)tmp.getZ() + (double)0.13125F + (double)0.7375F * (double)r.nextFloat(), 
	    					        		 d3, d4, d5);
	    				}	
	    			}
	    		}
	    	}
	    }

	    
	    @Override
	    public void applyInstantenousEffect(Entity entity, Entity p_180793_2_, LivingEntity p_180793_3_,
	    		int p_180793_4_, double p_180793_5_) {
	    	
	    	super.applyInstantenousEffect(entity, p_180793_2_, p_180793_3_, p_180793_4_, p_180793_5_);

	    }
	    
	    
	    
	    

//	    
//	    @Override
//	    public void renderHUDEffect(EffectInstance effect, AbstractGui gui, MatrixStack mStack, int x, int y, float z,float alpha) {
//	    	
//		
//	    }
//	    
//	    @Override
//	    public void renderInventoryEffect(EffectInstance effect, DisplayEffectsScreen<?> gui, MatrixStack mStack, int x,
//	    		int y, float z) {
//	
//	    }
	    
	    public List<Entity> getEnts(Level w,double x, double y, double z,double radius) {
			return w.getEntitiesOfClass(Entity.class, new AABB(x,0,z,x+1,257,z+1).inflate(radius));
		}
	    
	    

	    
	    
	
	

}