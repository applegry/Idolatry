package com.velesgod.slaviccraft.core.init;

import com.velesgod.slaviccraft.SlavicCraftMod;
import com.velesgod.slaviccraft.blocks.tile.BaseIdolTileEntity;
import com.velesgod.slaviccraft.blocks.tile.ClothBlockTileEntity;
import com.velesgod.slaviccraft.blocks.tile.DrierTileEntity;
import com.velesgod.slaviccraft.blocks.tile.ElixirCauldronTileEntity;
import com.velesgod.slaviccraft.blocks.tile.HBATileEntity;
import com.velesgod.slaviccraft.blocks.tile.LeshinIdolTileEntity;
import com.velesgod.slaviccraft.blocks.tile.PedestalTileEntity;
import com.velesgod.slaviccraft.blocks.tile.PounderTileEntity;
import com.velesgod.slaviccraft.blocks.tile.SlavicSackTileEntity;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TileEntitiesInit{
	  public static final DeferredRegister<BlockEntityType<?>> SlavicTileEntities = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES,SlavicCraftMod.MOD_ID);
	  
	  
	  public static final RegistryObject<BlockEntityType<HBATileEntity>> HBA_TILE = 
			SlavicTileEntities.register("house_brownie_altar", 
					() -> build(BlockEntityType.Builder.of(HBATileEntity::new, new Block[] {BlockInit.HBA_ALTAR.get()})));
	    
	  
	  
	  public static final RegistryObject<BlockEntityType<ClothBlockTileEntity>> CLOTH_TILE = 
			SlavicTileEntities.register("cloth_block", 
					() -> build(BlockEntityType.Builder.of(ClothBlockTileEntity::new, new Block[] {BlockInit.CLOTH.get()})));
	    
	  
	  
	  public static final RegistryObject<BlockEntityType<BaseIdolTileEntity>> IDOL = 
			SlavicTileEntities.register("base_idol", 
					() -> build(BlockEntityType.Builder.of(BaseIdolTileEntity::new, new Block[] 
							{BlockInit.FEAR_IDOL_BLOCK.get(),
							 BlockInit.GROWTH_IDOL_BLOCK.get()})));
	    
	  
	  
	  public static final RegistryObject<BlockEntityType<LeshinIdolTileEntity>> LESHINIDOL = 
			SlavicTileEntities.register("leshinidol", 
					() -> build(BlockEntityType.Builder.of(LeshinIdolTileEntity::new, new Block[] {BlockInit.LESHIN_IDOL.get()})));
	  
	  
	  
	  
	  public static final RegistryObject<BlockEntityType<DrierTileEntity>> DRIER_TE = 
			SlavicTileEntities.register("dryer", 
					() -> build(BlockEntityType.Builder.of(DrierTileEntity::new, new Block[] {BlockInit.DRIER.get()})));
	  
		
		  public static final RegistryObject<BlockEntityType<ElixirCauldronTileEntity>>
		  CAULDRON_TE = SlavicTileEntities.register("elixir_cauldron", () ->
		  build(BlockEntityType.Builder.of(ElixirCauldronTileEntity::new, new Block[]
		  {BlockInit.ELIXIR_CAULDRON.get()})));
		  
		 

	  
	  public static final RegistryObject<BlockEntityType<PounderTileEntity>> POUNDER_TE =
			  SlavicTileEntities.register("mortir", 
					  () -> build(BlockEntityType.Builder.of(PounderTileEntity::new, new Block[] {BlockInit.MORTIR.get()})));



	  
	  
	  
	  public static final RegistryObject<BlockEntityType<SlavicSackTileEntity>>
	  SACK_TE = SlavicTileEntities.register("sack", () ->
	  build(BlockEntityType.Builder.of(SlavicSackTileEntity::new, new Block[] {
	 
	  BlockInit.SACK.get() })));
	  

		  
		  
		  public static final RegistryObject<BlockEntityType<PedestalTileEntity>>
		  PEDESTAL_TE = SlavicTileEntities.register("pedestal", () ->
		  build(BlockEntityType.Builder.of(PedestalTileEntity::new, new Block[] {
		 
		  BlockInit.PEDESTAL_STONE.get(), BlockInit.PEDESTAL_DIORITE.get(),
		  BlockInit.PEDESTAL_ANDESITE.get(), BlockInit.PEDESTAL_GRANITE.get(),
		  BlockInit.PEDESTAL_DEEPSLATE.get() })));
		 
	  
	  private static <T extends BlockEntity> BlockEntityType<T> build(BlockEntityType.Builder<T> builder) {
		  
		  return builder.build(null);
	        
	      }

}




