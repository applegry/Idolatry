package com.velesgod.slaviccraft.core.init;

import com.velesgod.slaviccraft.SlavicCraftMod;
import com.velesgod.slaviccraft.effects.EffectFear;
import com.velesgod.slaviccraft.effects.EffectHaze;
import com.velesgod.slaviccraft.effects.EffectHerbvision;
import com.velesgod.slaviccraft.effects.EffectLightFeet;
import com.velesgod.slaviccraft.effects.EffectPoisonResist;
import com.velesgod.slaviccraft.effects.EffectPossession;
import com.velesgod.slaviccraft.effects.EffectRebirth;
import com.velesgod.slaviccraft.effects.EffectSvarogCircle;
import com.velesgod.slaviccraft.effects.SlavicEffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EffectInit{
	public static final DeferredRegister<MobEffect> SlavicEffects = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, SlavicCraftMod.MOD_ID);
	
	public static final RegistryObject<SlavicEffect> SVAROG_CIRCLE = SlavicEffects.register("svarog_circle",() -> new EffectSvarogCircle());
	
	public static final RegistryObject<SlavicEffect> POISON_RESIST = SlavicEffects.register("poison_resist",() -> new EffectPoisonResist());
	
	public static final RegistryObject<SlavicEffect> LIGHT_FEET = SlavicEffects.register("light_feet",() -> new EffectLightFeet());
	
	public static final RegistryObject<SlavicEffect> REBIRTH = SlavicEffects.register("rebirth",() -> new EffectRebirth());
	
	public static final RegistryObject<SlavicEffect> HAZE = SlavicEffects.register("haze",() -> new EffectHaze()); 
	
	public static final RegistryObject<SlavicEffect> POSSESSION = SlavicEffects.register("possession",() -> new EffectPossession()); 
	
	public static final RegistryObject<SlavicEffect> HERBVISION = SlavicEffects.register("herbvision",() -> new EffectHerbvision()); 

//	public static final RegistryObject<SlavicEffect> FEAR = SlavicEffects.register("fear",() -> new EffectFear()); 

}