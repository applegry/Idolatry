package com.velesgod.slaviccraft;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.gui.screens.MenuScreens.ScreenConstructor;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;

import com.mojang.blaze3d.platform.ScreenManager;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.velesgod.slaviccraft.api.IdolUtils;
import com.velesgod.slaviccraft.blockentityrenderer.DrierTileEntityRenderer;
import com.velesgod.slaviccraft.blockentityrenderer.PedestalTileEntityRenderer;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.GameRules.Category;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.ConfigGuiHandler;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;
import software.bernie.geckolib3.GeckoLib;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.velesgod.slaviccraft.blocks.CutOakBlock;
import com.velesgod.slaviccraft.blocks.ElixirCauldron;
import com.velesgod.slaviccraft.blocks.tile.PedestalTileEntity;
import com.velesgod.slaviccraft.core.init.BlockInit;
import com.velesgod.slaviccraft.core.init.ContainerInit;
import com.velesgod.slaviccraft.core.init.EffectInit;
import com.velesgod.slaviccraft.core.init.EntityInit;
import com.velesgod.slaviccraft.core.init.ItemInit;
import com.velesgod.slaviccraft.core.init.ParticleInit;
import com.velesgod.slaviccraft.core.init.SoundInit;
import com.velesgod.slaviccraft.core.init.TileEntitiesInit;
import com.velesgod.slaviccraft.gui.DrierBlockGui;
import com.velesgod.slaviccraft.gui.ElixirCauldronGui;
import com.velesgod.slaviccraft.gui.SlavicSackGui;
import com.velesgod.slaviccraft.particles.ParticleTypeAmber;
import com.velesgod.slaviccraft.particles.ParticleTypeGoldenLeaf;
import com.velesgod.slaviccraft.particles.ParticleTypeIdol;
import com.velesgod.slaviccraft.particles.ParticleTypeRaven;
import com.velesgod.slaviccraft.world.SlavicOreGeneration;


@Mod("slaviccraft")
@Mod.EventBusSubscriber(modid = SlavicCraftMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)

public class SlavicCraftMod {
	// Directly reference a log4j logger.
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MOD_ID = "slaviccraft";

	public SlavicCraftMod() {
		GeckoLib.initialize();
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		EffectInit.SlavicEffects.register(bus);
		ParticleInit.SlavicParticles.register(bus);
		EntityInit.ENTITIES.register(bus);
		ContainerInit.SlavicContaniers.register(bus);
		SoundInit.SlavicSounds.register(bus);
		BlockInit.SlavicBlocks.register(bus);
		ItemInit.SlavicItems.register(bus);

		TileEntitiesInit.SlavicTileEntities.register(bus);
		MinecraftForge.EVENT_BUS.register(this);
	    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
	
	}

	 public static ArrayList<String> getNoReg() {
		  ArrayList<String> ret = new ArrayList<String>();
		  ret.add("slaviccraft:linen_block");
		  ret.add("slaviccraft:turnip_block");
		  ret.add("slaviccraft:duckweed");
		  return ret;
	  }

	
		@SuppressWarnings("resource")
		@SubscribeEvent(priority = EventPriority.LOWEST)
		public static void registerParticles(ParticleFactoryRegisterEvent event) {
			
			Minecraft.getInstance().particleEngine.register(ParticleInit.RAVEN_PARTICLE.get(),ParticleTypeRaven.Provider::new);
			Minecraft.getInstance().particleEngine.register(ParticleInit.AMBER_PARTICLE.get(),ParticleTypeAmber.Provider::new);
			Minecraft.getInstance().particleEngine.register(ParticleInit.IDOL_PARTICLE.get(),ParticleTypeIdol.Provider::new);
			Minecraft.getInstance().particleEngine.register(ParticleInit.GOLDEN_LEAF_PARTICLE.get(),ParticleTypeGoldenLeaf.Provider::new);
		}
	
	@SubscribeEvent
	public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
		BlockInit.SlavicBlocks.getEntries().stream().map(RegistryObject::get).forEach(block -> {
			if(!getNoReg().contains(block.getRegistryName().toString()))
			event.getRegistry().register(new BlockItem(block, new Item.Properties().tab(SlavicCraftTab.SlavicGroup))
					.setRegistryName(block.getRegistryName()));

		});
	}

	 private  void clientSetup(FMLClientSetupEvent event) {
			
		
			ItemBlockRenderTypes.setRenderLayer(BlockInit.ARTEMISIA.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(BlockInit.IMMORETLE.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(BlockInit.THYME.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(BlockInit.THISTLES.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(BlockInit.WILDROSEMARY.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(BlockInit.HONEYAGARIC.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(BlockInit.CHRYSANTHS.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(BlockInit.CHANTERELLES.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(BlockInit.NETTLE.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(BlockInit.HEATHER.get(), RenderType.cutout());

			ItemBlockRenderTypes.setRenderLayer(BlockInit.HENBANE.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(BlockInit.HEMLOCK.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(BlockInit.LOVAGE.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(BlockInit.CRANESBILL.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(BlockInit.PARIS.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(BlockInit.PRIMROSE.get(), RenderType.cutout()); 
			
			ItemBlockRenderTypes.setRenderLayer(BlockInit.FIREWHIP.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(BlockInit.HELLEBORE.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(BlockInit.SWORDBLADE.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(BlockInit.SWIFTFOOT.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(BlockInit.THORNAPPLES.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(BlockInit.SLEEPGRASS.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(BlockInit.BLOOM_FERN.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(BlockInit.CATTAILS.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(BlockInit.LINEN_BLOCK.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(BlockInit.TURNIP_BLOCK.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(BlockInit.HBA_ALTAR.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(BlockInit.GOLDEN_LEAVES.get(), RenderType.translucentMovingBlock());
			ItemBlockRenderTypes.setRenderLayer(BlockInit.DRIER.get(), RenderType.cutoutMipped());
			ItemBlockRenderTypes.setRenderLayer(BlockInit.DEEPROOT.get(), RenderType.cutoutMipped());
			ItemBlockRenderTypes.setRenderLayer(BlockInit.DUCKWEED.get(), RenderType.cutoutMipped());
		
			MenuScreens.register(ContainerInit.DRIER.get(),DrierBlockGui::new);
			MenuScreens.register(ContainerInit.CAULDRON_CONTANIER.get(),ElixirCauldronGui::new);
			MenuScreens.register(ContainerInit.SACK.get(),SlavicSackGui::new);
		
		  }

	 
	 
	public boolean isAmulet(Entity e) {
		for(ItemStack i:((Player)e).getArmorSlots()) {
			if(i.getItem() == ItemInit.LESHIN_AMULET.get()) return true; 
		}
		return false;
	}
	 
     @SubscribeEvent
	public void onLivingDeath(LivingDeathEvent event){
		Entity e = event.getEntity();
		
		if (e instanceof Player) 
		if(isAmulet(e)){
			Level l  = e.level;
			Player p = ((Player)e);
			BlockPos respos = new BlockPos(0,0,0);
			
			Chicken ent = new Chicken(EntityType.CHICKEN, l);
			ent.setInvulnerable(true);
			
		//	ent.setInvisible(true);
			ent.setAge(0);
			ent.setNoAi(true);
			ent.setBoundingBox(new AABB(0,0,0,0,0,0));
			ent.setSilent(true);
			ent.setNoGravity(true);
			ent.setCustomNameVisible(false);
			ent.setCustomName(new TextComponent("__inv"));
			

			ListTag list = new ListTag();
			p.getInventory().save(list);
			
			ItemStack saver = new ItemStack(Items.EGG);
			CompoundTag tag = new CompoundTag();
			tag = saver.getOrCreateTag();
			tag.putInt("size", list.size());
			tag.put("inv",list);
			saver.setTag(tag);
			ent.setItemInHand(InteractionHand.MAIN_HAND, saver);
			System.out.println(saver.getTag());
			
			
			ent.moveTo(respos.getX(),respos.getY(), respos.getZ());
			l.addFreshEntity(ent);
			
			
		}
	}
     

     
     @SubscribeEvent
     public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
	  for(Entity e: event.getPlayer().level.getEntities(
			   new Chicken(EntityType.CHICKEN,event.getPlayer().level)
			   , new AABB(new BlockPos(-1,-1,-1),new BlockPos(1,1,1))) ) {
		  if(e.hasCustomName())
			  if(e.getCustomName().getContents() == "__inv")
			  {
				  ListTag list =new ListTag();
				  
				  CompoundTag tag  = new CompoundTag();
				  tag = ((Chicken)e).getItemInHand(InteractionHand.MAIN_HAND).getTag();
				  int size = tag.getInt("size");
				  list = (ListTag) tag.get("inv");
				//  list = 
				  System.out.println(list);
				  event.getPlayer().getInventory().load(list);
				  e.kill();
			  }
				 
	
	   }
     }
	 
   public BlockPos getRespawn(Player player) {
	   return ((ServerPlayer)player).getRespawnPosition();
   }
     

     
   
	 
		@SubscribeEvent
		public void biomeLoadingEffect(final BiomeLoadingEvent event) {
		
			SlavicOreGeneration.onBiomeLoading(event);
		}
		
	 
	 @SubscribeEvent
	    public static void onCommonSetup(FMLCommonSetupEvent event)
	    {
	        SlavicOreGeneration.initFeatures();
	    }
	 
	 
	 	@OnlyIn(Dist.CLIENT)
	  public static void registerClient(ModLoadingContext context) {
	  //  context.registerExtensionPoint(ConfigGuiHandler.ConfigGuiFactory.class, () -> ScreenConfigurationCommon.getInstance());
	  }

	 	@SubscribeEvent
	 	public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
	 		event.registerBlockEntityRenderer(TileEntitiesInit.PEDESTAL_TE.get(),PedestalTileEntityRenderer::new);
	 		event.registerBlockEntityRenderer(TileEntitiesInit.DRIER_TE.get(),DrierTileEntityRenderer::new);
	 	}

	 
		@SubscribeEvent
		public void onRightClick(final PlayerInteractEvent.RightClickBlock ev) {
			if(ev.getItemStack().getItem() == ItemInit.AMBER_CHISEL.get())
				if(ev.getWorld().getBlockState(ev.getPos()).getBlock() instanceof CutOakBlock) {
					if(!ev.getWorld().isClientSide()) {
						Block block = ev.getWorld().getBlockState(ev.getPos()).getBlock();
						IdolUtils.setIdolHead(block,ev.getWorld(),ev.getPos(),ev.getPlayer());
						ev.getPlayer().swing(ev.getHand());
						ev.getWorld().playSound(ev.getPlayer(), ev.getPos(),SoundEvents.AXE_STRIP, SoundSource.MASTER, 3f, 1f);
						
					}
				}
			if(ev.getItemStack().getItem() instanceof AxeItem) {
				if(ev.getWorld().getBlockState(ev.getPos()).getBlock() == Blocks.BIRCH_LOG ) {
					ev.getWorld().addFreshEntity(new ItemEntity(ev.getWorld(), 
							ev.getPos().getX(), 
							ev.getPos().getY() , 
							ev.getPos().getZ(), 
							new ItemStack(ItemInit.DIR_BB.get(),(int)(Math.random()*2+1))));
				      ev.getPlayer().swing(ev.getHand());
				}
				IdolUtils.setIdolBody(ev.getWorld().getBlockState(ev.getPos()).getBlock(),ev.getWorld(),ev.getPos());
			}				
		}
		
		
		@SubscribeEvent
		public void onToolTip(final ItemTooltipEvent ev) {
			if(ev.getItemStack().getOrCreateTag().getInt("balm_value") > 0) {
				
				String t = new TranslatableComponent("tooltip.slaviccraft.balm").getString();
				ev.getToolTip().add(new TextComponent(""+t).withStyle(ChatFormatting.DARK_GRAY));
				
				String trans = new TranslatableComponent("tooltip.slaviccraft.balm_of").getString();
				ev.getToolTip().add(new TextComponent(" "+trans+": "+ev.getItemStack().getOrCreateTag().getInt("balm_value")).withStyle(ChatFormatting.GOLD));
			}
		} 
	 
		@SubscribeEvent
		public void onAttackEntity(final AttackEntityEvent ev) {
			ItemStack stack = ev.getEntityLiving().getMainHandItem();
			CompoundTag nbt = stack.getOrCreateTag();
			int balm = nbt.getInt("balm_value");
			
			if(balm > 0)
			{
				MobEffect eff = MobEffect.byId(nbt.getInt("balm_effect"));
				LivingEntity ent = (LivingEntity) ev.getTarget();
				ent.addEffect(new MobEffectInstance(eff,10));
				balm-=1;
				if(balm>0) {
					nbt.putInt("balm_value", balm);
				}else {
					nbt.putBoolean("balmed", false);
					nbt.putInt("balm_value",0);
				}
				stack.setTag(nbt);
			}
		}

		@SubscribeEvent
		public void onEntityAttacked(final net.minecraftforge.event.entity.living.LivingAttackEvent ev) {
			if(ev.getEntity() instanceof Player) {
				Player player = (Player)ev.getEntity();
				if(player.hasEffect(EffectInit.SVAROG_CIRCLE.get())) 
					if(ev.getSource() == DamageSource.LAVA || ev.getSource() == DamageSource.IN_FIRE || ev.getSource() == DamageSource.ON_FIRE 
					||ev.getSource() == DamageSource.HOT_FLOOR) ev.setCanceled(true);
			}
		}
		
		@SubscribeEvent
		public void livingFall(LivingFallEvent event)
		{
		    if (!(event.getEntity() instanceof Player)) return;
		    Player player = (Player)event.getEntity();
		    Boolean bool = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTag().getBoolean("isUsed") || 
		    		       player.getItemInHand(InteractionHand.OFF_HAND).getOrCreateTag().getBoolean("isUsed");
		    if(player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == ItemInit.RAVEN_STUFF.get() && bool)
		    event.setDistance(0);
		   
		}
		
	    @SubscribeEvent
	    public static void registerItemColors(ColorHandlerEvent.Item event) {

	    	ItemColors itemcolors = event.getItemColors();
	        BlockColors blockcolors = event.getBlockColors();
	        
	        blockcolors.register((p_228060_0_, p_228060_1_, p_228060_2_, p_228060_3_) -> {
	            return p_228060_1_ != null && p_228060_2_ != null ? BiomeColors.getAverageWaterColor(p_228060_1_, p_228060_2_) : 0;
	         }, BlockInit.ELIXIR_CAULDRON.get());
	        
	        itemcolors.register((p_210238_0_, p_210238_1_) -> {
	            return p_210238_1_ > 0 ? -1 : PotionUtils.getColor(p_210238_0_);
	         }, ItemInit.ELIXIR.get());
	        
	    }
	 
		@SubscribeEvent
		public void tick(final TickEvent.PlayerTickEvent ev) {
		
			Player player = ev.player;
		//	System.out.println(player.getBedOrientation());
			Level world = ev.player.level;
			if(player.hasEffect(EffectInit.LIGHT_FEET.get()) && world.getBlockState(new BlockPos(player.getPosition(1))).getBlock() == Blocks.WATER && 
				world.getBlockState(new BlockPos(player.getPosition(1)).above()).getBlock() == Blocks.AIR &&
					!player.isShiftKeyDown()){
				double dy = player.getDeltaMovement().y() > 0 ? player.getDeltaMovement().y():0;
				player.setDeltaMovement(player.getDeltaMovement().x(),dy,player.getDeltaMovement().z());
				player.setOnGround(true);
				player.bob = 0.1f;
			
			}
		}
	 

		
		
		
}


