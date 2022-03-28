package com.velesgod.slaviccraft.core.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import com.velesgod.slaviccraft.SlavicCraftMod;

public class SoundInit{
	public static final DeferredRegister<SoundEvent> SlavicSounds = 
			DeferredRegister.create(ForgeRegistries.SOUND_EVENTS,SlavicCraftMod.MOD_ID);
	
	
	public static final RegistryObject<SoundEvent> BLOOD_GET = registerSoundEvent("blood_get");
	
	public static final RegistryObject<SoundEvent> POUNDER = registerSoundEvent("pounder");
	
	private static RegistryObject<SoundEvent> registerSoundEvent(String name){
		return SlavicSounds.register(name,()-> new SoundEvent(new ResourceLocation(SlavicCraftMod.MOD_ID,name)));
		
	}
	
	public static void register(IEventBus eventBus) {
		SlavicSounds.register(eventBus);
	}
}