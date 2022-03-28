package com.velesgod.slaviccraft.core.init;

import com.velesgod.slaviccraft.SlavicCraftMod;
import com.velesgod.slaviccraft.contaniers.DrierBlockContainer;
import com.velesgod.slaviccraft.contaniers.ElixirCauldronContanier;

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
	
	
//	public static final RegistryObject<ContainerType<ElixirCauldronContanier>>  CAULDRON_CONTANIER =
//		 SlavicContaniers.register("elixir_cauldron_container",
//				 () -> IForgeContainerType.create( (windowId,inv,data)-> {
//					 BlockPos pos = data.readBlockPos();
//					 World world = inv.player.getEntity().level;
//					 return new ElixirCauldronContanier(windowId,world,pos,inv);
//					 
//				 }));
	
//	public static final RegistryObject<MenuType<DrierBlockContainer>>  DRIER =
//			 SlavicContaniers.register("dryer",
//					 () -> IForgeMenuType.create( (windowId,inv,data)-> {
//						 BlockPos pos = data.readBlockPos();
//						 Level world = inv.player.getLevel();
//						 return new DrierBlockContainer(windowId,world,pos,inv);
//						 
//					 }));
	
	
	   public static final RegistryObject<MenuType<DrierBlockContainer>> DRIER = SlavicContaniers
	            .register("dryer", () -> new MenuType<>(DrierBlockContainer::new));
	
	   public static final RegistryObject<MenuType<ElixirCauldronContanier>> CAULDRON_CONTANIER = SlavicContaniers
	            .register("elixir_cauldron_container", () -> new MenuType<>(ElixirCauldronContanier::new));
	
	
	public static void register(IEventBus eventBus) {
		SlavicContaniers.register(eventBus);
	}
}









































