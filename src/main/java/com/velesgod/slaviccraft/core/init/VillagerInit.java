package com.velesgod.slaviccraft.core.init;

import java.lang.reflect.InvocationTargetException;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.velesgod.slaviccraft.SlavicCraftMod;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Items;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class VillagerInit{
	public static final DeferredRegister<PoiType> PoiTypes = DeferredRegister.create(ForgeRegistries.POI_TYPES, SlavicCraftMod.MOD_ID);
	public static final DeferredRegister<VillagerProfession> SlavicVillagers = DeferredRegister.create(ForgeRegistries.PROFESSIONS, SlavicCraftMod.MOD_ID);
	
	
	public static final RegistryObject<PoiType> HERBALIST_POI = PoiTypes.register("herbalist", 
			() -> new PoiType("herbalist", PoiType.getBlockStates(BlockInit.ELIXIR_CAULDRON.get()), 1, 1));
	
	public static final RegistryObject<VillagerProfession> HERBALIST = SlavicVillagers.register("herbalist",
			()-> new VillagerProfession("herbalist", HERBALIST_POI.get(),ImmutableSet.of(),ImmutableSet.of(),SoundEvents.VILLAGER_WORK_CLERIC));

	public static void registerPoi() {
		try {
			ObfuscationReflectionHelper.findMethod(PoiType.class, "registerBlockStates", PoiType.class).invoke(null, HERBALIST_POI.get());
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	
}















