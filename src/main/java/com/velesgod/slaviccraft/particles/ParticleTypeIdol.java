package com.velesgod.slaviccraft.particles;

import java.util.Random;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.RisingParticle;
import net.minecraft.client.particle.SoulParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;


@OnlyIn(Dist.CLIENT)
public class ParticleTypeIdol extends RisingParticle {
   private final SpriteSet sprites;

   ParticleTypeIdol(ClientLevel p_107717_, double p_107718_, double p_107719_, double p_107720_, double p_107721_, double p_107722_, double p_107723_, SpriteSet p_107724_) {
      super(p_107717_, p_107718_, p_107719_, p_107720_, p_107721_, p_107722_, p_107723_);
      this.sprites = p_107724_;
      this.scale(4F);
      this.lifetime = 8;
      this.setSpriteFromAge(p_107724_);
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
   }

   public void tick() {
      super.tick();
      this.setSpriteFromAge(this.sprites);
      
   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprite;

      public Provider(SpriteSet p_107739_) {
         this.sprite = p_107739_;
      }

      public Particle createParticle(SimpleParticleType p_107750_, ClientLevel p_107751_, double p_107752_, double p_107753_, double p_107754_, double p_107755_, double p_107756_, double p_107757_) {
         ParticleTypeIdol soulparticle = new ParticleTypeIdol(p_107751_, p_107752_, p_107753_, p_107754_, p_107755_, p_107756_, p_107757_, this.sprite);
         soulparticle.setAlpha(1.0F);
         soulparticle.setColor(1.0F, 0.0F, 0.0F);
         return soulparticle;
      }
   }
}