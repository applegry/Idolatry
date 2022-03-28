package com.velesgod.slaviccraft.core.init;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import com.velesgod.slaviccraft.SlavicCraftMod;

public class ParticleInit {
	public static final DeferredRegister<ParticleType<?>> SlavicParticles = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, SlavicCraftMod.MOD_ID);
	
	public static final RegistryObject<SimpleParticleType> RAVEN_PARTICLE = SlavicParticles.register("raven_particles", () -> new SimpleParticleType(true));
	
	public static final RegistryObject<SimpleParticleType> AMBER_PARTICLE = SlavicParticles.register("amber_particles", () -> new SimpleParticleType(true));

	public static final RegistryObject<SimpleParticleType> IDOL_PARTICLE = SlavicParticles.register("idol_particles", () -> new SimpleParticleType(true));

}