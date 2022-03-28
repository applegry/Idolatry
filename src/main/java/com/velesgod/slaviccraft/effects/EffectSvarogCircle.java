package com.velesgod.slaviccraft.effects;


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
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;



public class EffectSvarogCircle extends SlavicEffect {
	
	private final ResourceLocation ICON = new ResourceLocation(SlavicCraftMod.MOD_ID,"textures/effects/svarog_circle.png");
	
	public Potion potion = new Potion(new MobEffectInstance(this));
	
	
	public EffectSvarogCircle() {
		super("svarog_circle", MobEffectCategory.BENEFICIAL, 0xFF5500);
		
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
	
	   @Override
	    public boolean isDurationEffectTick(int duration, int amplifier) {
	        return true;
	    }
	   
	    @Override
	    public void applyEffectTick(LivingEntity entity, int tick) {
	    	// TODO Auto-generated method stub
	    	super.applyEffectTick(entity, tick);
	    	if(entity instanceof Player) {
	    		Player player = (Player) entity;
	    		Level world = player.level;
	    		
	    		
	    		if(player.isOnFire()) player.clearFire();
	    	
	    		
	    		
	    		
	    		Angle = (Angle+1) % 360;
	    		for(Entity ent: getEnts(player,4)) { 
	    			if(! (ent instanceof Player) &&
	    				 (ent instanceof LivingEntity || ent instanceof Arrow)
	    				 && Math.abs(player.getY()-ent.getY())<3) {
	    			
	   	
	    				
	    				ent.setSecondsOnFire(4);
	    			
	    			}
	    			
	    		}
	    		createBall(world,player,0.5);
	    		BlockPos pos = new BlockPos(player.getPosition(1.f)).below();
	    		if(world.getBlockState(pos).getBlock() == Blocks.SNOW ||
	    				world.getBlockState(pos).getBlock() == Blocks.ICE	) {
	    			world.destroyBlock(pos,true);

					
					
	    		}
	    		
	    		
	    	}
	    }

	    
	    @Override
	    public void applyInstantenousEffect(Entity entity, Entity p_180793_2_, LivingEntity p_180793_3_,
	    		int p_180793_4_, double p_180793_5_) {
	    	// TODO Auto-generated method stub
	    	super.applyInstantenousEffect(entity, p_180793_2_, p_180793_3_, p_180793_4_, p_180793_5_);
	    	
	    
	    	
	    }
	    
	    
	    
	 
	
	    
	    private void createBall(Level world,Entity ent,double size)
	    {
	        double d0 = ent.getX();
	        double d1 = ent.getY()-1;
	        double d2 = ent.getZ();
	      //  Random rand = world.getRandom();
           double angle = 0;
            for(float i = 0; i < 180; i += 45)
            {
                  angle = i*2+Angle;
           
                 
                  double x1 = size * Math.cos(angle * Math.PI / 180);
                  double z1 = size * Math.sin(angle * Math.PI / 180);
                  world.addParticle(ParticleTypes.FLAME,d0+x1, d1+1, d2+z1, 0, 0, 0);
            }
	    }
	    
	    @SuppressWarnings("unused")
		private void createSphere(Level world,Entity ent,double speed, int size)
	    {
	        double d0 = ent.getX();
	        double d1 = ent.getY();
	        double d2 = ent.getZ();
	        Random rand = world.getRandom();

	        for (int i = -size; i <= size; ++i)
	        {
	            for (int j = -size; j <= size; ++j)
	            {
	                for (int k = -size; k <= size; ++k)
	                {
	                    double d3 = (double)j + (rand.nextDouble() - rand.nextDouble()) * 0.5D;
	                    double d4 = (double)i + (rand.nextDouble() - rand.nextDouble()) * 0.5D;
	                    double d5 = (double)k + (rand.nextDouble() - rand.nextDouble()) * 0.5D;
	                    double d6 = (double)Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5) / speed + rand.nextGaussian() * 0.05D;
	                    world.addParticle(ParticleTypes.FLAME,d0, d1, d2, d3 / d6, d4 / d6, d5 / d6);

	                    if (i != -size && i != size && j != -size && j != size)
	                    {
	                        k += size * 2 - 1;
	                    }
	                }
	            }
	        }
	    }
	    
	    
	    
	    
	
	

}