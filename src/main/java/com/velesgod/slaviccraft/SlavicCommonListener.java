package com.velesgod.slaviccraft;

import com.velesgod.slaviccraft.core.init.EntityInit;
import com.velesgod.slaviccraft.entity.SlavicGhost;
import com.velesgod.slaviccraft.entity.SlavicLeshin;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;



@Mod.EventBusSubscriber(modid = SlavicCraftMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SlavicCommonListener {
	@SubscribeEvent
	public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
		

			event.put(EntityInit.SLAVIC_GHOST.get(),SlavicGhost.createMobAttributes().build());

			event.put(EntityInit.SLAVIC_LESHIN.get(),SlavicLeshin.createMobAttributes().build());
	
	}
}