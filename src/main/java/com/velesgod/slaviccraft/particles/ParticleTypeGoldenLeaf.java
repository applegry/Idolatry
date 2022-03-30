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

public class ParticleTypeGoldenLeaf extends TextureSheetParticle{

	protected ParticleTypeGoldenLeaf(ClientLevel p_i232448_1_, double p_i232448_2_, double p_i232448_4_, double p_i232448_6_,
			double p_i232448_8_, double p_i232448_10_, double p_i232448_12_) {
		super(p_i232448_1_, p_i232448_2_, p_i232448_4_, p_i232448_6_, p_i232448_8_, p_i232448_10_, p_i232448_12_);
	

		this.setSize(0.015f, 0.015f);
		this.gravity = 0.3f;
		this.quadSize *= 1F;
		this.xd *= (double)0.0f;
		this.yd *= -(double)this.gravity;
		this.zd *= (double)0.0f;
		this.age = (int)(50.0D / (Math.random() * 1.0D));
		this.hasPhysics = true;
		
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
		//this.age = (int)(20.0D / (Math.random() * 2.0D));
		//this.setSpriteFromAge((IAnimatedSprite)this.sprite);

		if(this.age % 11 == 0) this.setSprite(this.spriteSet.get(new Random()));
		if(this.age-- <= 0) {
			this.remove();
		} else {
			this.move(this.xd, this.yd, this.zd);
			this.xd *= 1.0D;
			this.yd *= 1.0D;
			this.zd *= 1.0D;
		}
	
	}
	
	@OnlyIn(Dist.CLIENT)
	public static class Provider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet spriteSet;
		
		public Provider(SpriteSet sprite) {
			this.spriteSet = sprite;
		}
		
		@Override
		public Particle createParticle(SimpleParticleType p_199234_1_, ClientLevel p_199234_2_, double p_199234_3_,
				double p_199234_5_, double p_199234_7_, double p_199234_9_, double p_199234_11_, double p_199234_13_) {
			ParticleTypeGoldenLeaf particle = new ParticleTypeGoldenLeaf(p_199234_2_,p_199234_3_,p_199234_5_,  p_199234_7_,  p_199234_9_, p_199234_11_,  p_199234_13_);
			float a = (float)(Math.random() % 0.5);
			particle.setColor(0.3f+a,
					0.3f+a,
					0.3f+a);
			particle.setSprite(this.spriteSet.get(new Random()));
			particle.setSpriteAnim(this.spriteSet);
			return particle;
		}
	
		
	}
	
	
}