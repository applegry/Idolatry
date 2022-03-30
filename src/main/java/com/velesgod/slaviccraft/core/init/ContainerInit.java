package com.velesgod.slaviccraft.core.init;

import com.velesgod.slaviccraft.SlavicCraftMod;
import com.velesgod.slaviccraft.contaniers.DrierBlockContainer;
import com.velesgod.slaviccraft.contaniers.ElixirCauldronContanier;
import com.velesgod.slaviccraft.contaniers.SlavicSackContainer;

import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ContainerInit{
	
	public static DeferredRegister<MenuType<?>> SlavicContaniers
	= DeferredRegister.create(ForgeRegistries.CONTAINERS,SlavicCraftMod.MOD_ID);
	
	
	   public static final RegistryObject<MenuType<DrierBlockContainer>> DRIER = SlavicContaniers
	            .register("dryer", () -> new MenuType<>(DrierBlockContainer::new));
	
	   public static final RegistryObject<MenuType<ElixirCauldronContanier>> CAULDRON_CONTANIER = SlavicContaniers
	            .register("elixir_cauldron_container", () -> new MenuType<>(ElixirCauldronContanier::new));
	
	   public static final RegistryObject<MenuType<SlavicSackContainer>> SACK = SlavicContaniers
	            .register("sack_container", () -> new MenuType<>(SlavicSackContainer::new));
	
	
	public static void register(IEventBus eventBus) {
		SlavicContaniers.register(eventBus);
	}
}









































