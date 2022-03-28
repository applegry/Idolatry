package com.velesgod.slaviccraft;

import com.velesgod.slaviccraft.client.renderer.armor.AmuletRenderer;
import com.velesgod.slaviccraft.client.renderer.armor.CopperAmuletRenderer;
import com.velesgod.slaviccraft.client.renderer.armor.PowerAmuletRenderer;
import com.velesgod.slaviccraft.core.init.EntityInit;
import com.velesgod.slaviccraft.entity.SlavicGhost;
import com.velesgod.slaviccraft.entity.renderer.SlavicGhostRenderer;
import com.velesgod.slaviccraft.items.AmuletItem;
import com.velesgod.slaviccraft.items.CopperAmuletItem;
import com.velesgod.slaviccraft.items.PowerAmuletItem;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import software.bernie.example.client.renderer.entity.LERenderer;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

@Mod.EventBusSubscriber(modid = SlavicCraftMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class SlavicClientListener {


	@SubscribeEvent
	public static void registerRenderers(final EntityRenderersEvent.AddLayers event) {
		
			GeoArmorRenderer.registerArmorRenderer(AmuletItem.class, new AmuletRenderer());
			GeoArmorRenderer.registerArmorRenderer(CopperAmuletItem.class, new CopperAmuletRenderer());
			GeoArmorRenderer.registerArmorRenderer(PowerAmuletItem.class, new PowerAmuletRenderer());
			
	}

	@SubscribeEvent
	public static void registerRendererss(final EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(EntityInit.SLAVIC_GHOST.get(), SlavicGhostRenderer::new);
	}
}