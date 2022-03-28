package com.velesgod.slaviccraft.particles;

import java.util.Random;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

public class ParticleTypeAmber extends TextureSheetParticle{

	protected ParticleTypeAmber(ClientLevel p_i232448_1_, double p_i232448_2_, double p_i232448_4_, double p_i232448_6_,
			double p_i232448_8_, double p_i232448_10_, double p_i232448_12_) {
		super(p_i232448_1_, p_i232448_2_, p_i232448_4_, p_i232448_6_, p_i232448_8_, p_i232448_10_, p_i232448_12_);
	

		
	      this.xd *= (double)1.9F;
	      this.yd *= (double)0.9F;
	      this.zd *= (double)1.9F;
	      this.yd = (double)(this.random.nextFloat() * 0.4F + 0.05F);
	      this.quadSize *= this.random.nextFloat() * 2.0F + 0.2F;
	      this.lifetime = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
	 
		

	}

	/*
	 * Just like all other SpriteTexturedParticles this class is more or less copied, take a look at existing
	 * particles and add change some numbers around until you get what you need!
	 */

	

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}
	
	@SuppressWarnings("unused")
	private SpriteSet spriteSet;
	

	protected void setSpriteAnim(SpriteSet sp) {
		spriteSet = sp;
	}
	
	@Override
	   public void tick() {
	      this.xo = this.x;
	      this.yo = this.y;
	      this.zo = this.z;
	 
	      if (this.age++ >= this.lifetime) {
	         this.remove();
	      } else {
	         this.yd -= 0.04D;
	         this.move(this.xd, this.yd, this.zd);
	         this.xd *= (double)0.999F;
	         this.yd *= (double)0.999F;
	         this.zd *= (double)0.999F;
	         if (this.onGround) {
	            this.xd *= (double)0.7F;
	            this.zd *= (double)0.7F;
	         }

	      }
	   }
	
	@OnlyIn(Dist.CLIENT)
	public static class Provider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet spriteSet;
		
		public Provider(SpriteSet sprite) {
			this.spriteSet = sprite;
		}
		
	@Override
	public Particle createParticle(SimpleParticleType p_105804_, ClientLevel p_105805_, double p_105806_, double p_105807_, double p_105808_, double p_105809_, double p_105810_, double p_105811_) {
		ParticleTypeAmber particle = new ParticleTypeAmber(p_105805_, p_105806_, p_105807_, p_105808_, p_105809_, p_105810_, p_105811_);
			particle.setSprite(this.spriteSet.get(new Random()));
			particle.setSpriteAnim(this.spriteSet);
		
			return particle;
	}

	}
	
	
}