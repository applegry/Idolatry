package com.velesgod.slaviccraft.core.init;

import com.velesgod.slaviccraft.SlavicCraftMod;
import com.velesgod.slaviccraft.blocks.BaseIdol;
import com.velesgod.slaviccraft.blocks.ClothBlock;
import com.velesgod.slaviccraft.blocks.CutOakBlock;
import com.velesgod.slaviccraft.blocks.CutOakIdol;
import com.velesgod.slaviccraft.blocks.DrierBlock;
import com.velesgod.slaviccraft.blocks.DuckweedBlock;
import com.velesgod.slaviccraft.blocks.ElixirCauldron;
import com.velesgod.slaviccraft.blocks.GoldenLeaves;
import com.velesgod.slaviccraft.blocks.HouseBrownieAltar;
import com.velesgod.slaviccraft.blocks.LeshinHead;
import com.velesgod.slaviccraft.blocks.LinenBlock;
import com.velesgod.slaviccraft.blocks.LivingWood;
import com.velesgod.slaviccraft.blocks.PedestalBlock;
import com.velesgod.slaviccraft.blocks.PounderBlock;
import com.velesgod.slaviccraft.blocks.SlavicSack;
import com.velesgod.slaviccraft.blocks.TurnipBlock;
import com.velesgod.slaviccraft.blocks.herbs.BasePlant;
import com.velesgod.slaviccraft.blocks.herbs.BaseTallPlant;
import com.velesgod.slaviccraft.blocks.tile.SlavicSackTileEntity;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SeagrassBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockInit {
	public static final DeferredRegister<Block> SlavicBlocks = DeferredRegister.create(ForgeRegistries.BLOCKS, SlavicCraftMod.MOD_ID);
	
	
	public static final RegistryObject<Block> DRIER =SlavicBlocks.register("dryer",() -> new DrierBlock());

	
	  public static final RegistryObject<Block> HBA_ALTAR = SlavicBlocks.register("house_brownie_altar",() -> new HouseBrownieAltar());
	  
	  public static final RegistryObject<Block> MORTIR =SlavicBlocks.register("mortir",() -> new PounderBlock());
	  public static final RegistryObject<Block> MORTIR_HARM =SlavicBlocks.register("mortir_1",() -> new PounderBlock());
	  public static final RegistryObject<Block> MORTIR_DESTROY = SlavicBlocks.register("mortir_2",() -> new PounderBlock());
	  
      public static final RegistryObject<Block> ELIXIR_CAULDRON =SlavicBlocks.register("elixir_cauldron",() -> new ElixirCauldron());
	 
      public static final RegistryObject<SlavicSack> SACK = SlavicBlocks.register("sack", () -> new SlavicSack());
      
      public static final RegistryObject<Block> CLOTH = SlavicBlocks.register("slaviccloth", () -> new ClothBlock());
      public static final RegistryObject<Block> LESHIN_HEAD = SlavicBlocks.register("leshin_head", () -> new LeshinHead());
  	
      

	  public static final RegistryObject<Block> LIVING_STONE_BLOCK = SlavicBlocks.register("living_stone_block",() -> new Block(Properties.of(Material.HEAVY_METAL).strength(4f,4f)));
	  public static final RegistryObject<Block> DEAD_STONE_BLOCK = SlavicBlocks.register("dead_stone_block",() -> new Block(Properties.of(Material.HEAVY_METAL).strength(4f,4f)));
	  public static final RegistryObject<Block> AMBER_ORE =SlavicBlocks.register("amber_ore",() -> new Block(Properties.of(Material.STONE).strength(3f,3f)));
	  public static final RegistryObject<Block> SWAMP_IRON = SlavicBlocks.register("swamp_ore",() -> new Block(Properties.of(Material.DIRT).strength(0.7f,0.7f)));
	  public static final RegistryObject<Block> AMBER_BLOCK = SlavicBlocks.register("amber_block",() -> new Block(Properties.of(Material.METAL).strength(5f,5f)));
	 	
	  public static final RegistryObject<Block> FEAR_IDOL_BLOCK = SlavicBlocks.register("fear_idol", () -> new BaseIdol());
	  public static final RegistryObject<Block> GROWTH_IDOL_BLOCK = SlavicBlocks.register("growth_idol", () -> new BaseIdol());
	
	  public static final RegistryObject<Block> CUT_OAK_BLOCK = SlavicBlocks.register("cut_oak_block",() -> new CutOakBlock()); 
	  public static final RegistryObject<Block> CUT_BIRCH_BLOCK =SlavicBlocks.register("cut_birch_block",() -> new CutOakBlock());
      public static final RegistryObject<Block> CUT_SPRUCE_BLOCK =SlavicBlocks.register("cut_spruce_block",() -> new CutOakBlock()); 
      public static final RegistryObject<Block> CUT_DARK_OAK_BLOCK =SlavicBlocks.register("cut_dark_oak_block",() -> new CutOakBlock());
	  public static final RegistryObject<Block> CUT_ACACIA_BLOCK = SlavicBlocks.register("cut_acacia_block",() -> new CutOakBlock()); 
	  public static final RegistryObject<Block> CUT_JUNGLE_BLOCK = SlavicBlocks.register("cut_jungle_block",() -> new CutOakBlock()); 
	  public static final RegistryObject<Block> CUT_WARPED_BLOCK =SlavicBlocks.register("cut_warped_block",() -> new CutOakBlock()); 
	  public static final RegistryObject<Block> CUT_CRIMSON_BLOCK =SlavicBlocks.register("cut_crimson_block",() -> new CutOakBlock());
	  
	  public static final RegistryObject<Block> CUT_OAK_IDOL = SlavicBlocks.register("cut_oak_idol",() -> new CutOakIdol()); 
	  public static  final RegistryObject<Block> CUT_BIRCH_IDOL =  SlavicBlocks.register("cut_birch_idol",() -> new CutOakIdol()); 
	  public static final RegistryObject<Block> CUT_SPRUCE_IDOL = SlavicBlocks.register("cut_spruce_idol",() -> new CutOakIdol()); 
	  public static final RegistryObject<Block> CUT_DARK_OAK_IDOL = SlavicBlocks.register("cut_dark_oak_idol",() -> new CutOakIdol()); 
	  public static final RegistryObject<Block> CUT_ACACIA_IDOL = SlavicBlocks.register("cut_acacia_idol",() -> new CutOakIdol()); 
	  public static final RegistryObject<Block> CUT_JUNGLE_IDOL = SlavicBlocks.register("cut_jungle_idol",() -> new CutOakIdol()); 
	  public static final RegistryObject<Block> CUT_WARPED_IDOL = SlavicBlocks.register("cut_warped_idol",() -> new CutOakIdol()); 
	  public static final RegistryObject<Block> CUT_CRIMSON_IDOL =SlavicBlocks.register("cut_crimson_idol",() -> new CutOakIdol());
	 

      public static final RegistryObject<Block> GOLDEN_LEAVES = SlavicBlocks.register("golden_leaves",() -> new GoldenLeaves());
	  public static final RegistryObject<Block> LIVING_WOOD = SlavicBlocks.register("living_wood",() -> new LivingWood());
	  
	
	 //pedestals 
	  public static final RegistryObject<PedestalBlock> PEDESTAL_STONE = SlavicBlocks.register("pedestal_stone",() -> new PedestalBlock());
	  public static final RegistryObject<PedestalBlock> PEDESTAL_ANDESITE = SlavicBlocks.register("pedestal_andesite",() -> new PedestalBlock());
	  public static final RegistryObject<PedestalBlock> PEDESTAL_DIORITE = SlavicBlocks.register("pedestal_diorite",() -> new PedestalBlock());
	  public static final RegistryObject<PedestalBlock> PEDESTAL_GRANITE = SlavicBlocks.register("pedestal_granite",() -> new PedestalBlock());
	  public static final RegistryObject<PedestalBlock> PEDESTAL_DEEPSLATE = SlavicBlocks.register("pedestal_deepslate",() -> new PedestalBlock());
	
	  
	  //Cook 
	  public static final RegistryObject<BasePlant> THYME = SlavicBlocks.register("thyme",() -> new BasePlant()); 
	  public static final RegistryObject<SeagrassBlock> DEEPROOT = SlavicBlocks.register("deeproot", () -> new SeagrassBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_WATER_PLANT).noCollission().instabreak().sound(SoundType.WET_GRASS)));
	  public static final RegistryObject<DuckweedBlock> DUCKWEED = SlavicBlocks.register("duckweed", () -> new DuckweedBlock());
	  public static final RegistryObject<BasePlant> CHANTERELLES =SlavicBlocks.register("chanterelles",() -> new BasePlant());
	  public static final RegistryObject<BasePlant> HONEYAGARIC = SlavicBlocks.register("honeyagaric",() -> new BasePlant()); 
	  public static final RegistryObject<BasePlant> ARTEMISIA =SlavicBlocks.register("artemisia",() -> new BasePlant());
	  public static final RegistryObject<BaseTallPlant> NETTLE = SlavicBlocks.register("nettle",() -> new BaseTallPlant(MobEffects.POISON));
	  public static final RegistryObject<BasePlant> THISTLES =SlavicBlocks.register("thistles",() -> new BasePlant());
	  public static final RegistryObject<BasePlant> HEATHER =SlavicBlocks.register("heather",() -> new BasePlant()); 
	  public static final RegistryObject<BasePlant> LOVAGE = SlavicBlocks.register("lovage",() -> new BasePlant()); 
	  public static final RegistryObject<BasePlant> PRIMROSE = SlavicBlocks.register("primrose",() -> new BasePlant());
	  public static final RegistryObject<BasePlant> CRANESBILL = SlavicBlocks.register("cranesbill",() -> new BasePlant());
	  //Balm
	  public static final RegistryObject<BaseTallPlant> THORNAPPLES =SlavicBlocks.register("thornapples",() -> new BaseTallPlant());
	  public static final RegistryObject<BaseTallPlant> HELLEBORE =SlavicBlocks.register("hellebore",() -> new BaseTallPlant());
	  public static final RegistryObject<BaseTallPlant> SLEEPGRASS =SlavicBlocks.register("sleepgrass",() -> new BaseTallPlant());
	  public static final RegistryObject<BasePlant> PARIS =SlavicBlocks.register("paris",() -> new BasePlant()); 
	  public static final RegistryObject<BaseTallPlant> HEMLOCK =SlavicBlocks.register("hemlock",() -> new BaseTallPlant());
	  public static final RegistryObject<BasePlant> HENBANE =SlavicBlocks.register("henbane",() -> new BasePlant()); 
	  //Elixir
	  
	  public static final RegistryObject<BasePlant> WILDROSEMARY =SlavicBlocks.register("wildrosemary",() -> new BasePlant());
	  public static final RegistryObject<BaseTallPlant> FIREWHIP =SlavicBlocks.register("firewhip",() -> new BaseTallPlant());
	  public static final RegistryObject<BasePlant> CHRYSANTHS =SlavicBlocks.register("chrysanths",() -> new BasePlant());
	  public static final RegistryObject<BasePlant> IMMORETLE =SlavicBlocks.register("immortelle",() -> new BasePlant());
	  public static final RegistryObject<BaseTallPlant> SWORDBLADE =SlavicBlocks.register("swordblade",() -> new BaseTallPlant());
	  public static final RegistryObject<BaseTallPlant> SWIFTFOOT =SlavicBlocks.register("swiftfoot",() -> new BaseTallPlant());

	  //Other
	  
	  public static final RegistryObject<BaseTallPlant> BLOOM_FERN =SlavicBlocks.register("bloom_fern",() -> new BaseTallPlant(false));
	  public static final RegistryObject<BaseTallPlant> CATTAILS =SlavicBlocks.register("cattails",() -> new BaseTallPlant(true));
	  public static final RegistryObject<LinenBlock> LINEN_BLOCK =SlavicBlocks.register("linen_block",() -> new LinenBlock()); 
	  public static final RegistryObject<TurnipBlock> TURNIP_BLOCK = SlavicBlocks.register("turnip_block",() -> new TurnipBlock());

	 
	
	public static final RegistryObject<Block> defineBlock(String name,Material m,MaterialColor mc,int harvestlevel,float hardness,float resist,SoundType s) {
		return  SlavicBlocks.register(name,() -> new Block(BlockBehaviour.Properties.of(m, mc).strength(hardness, resist).sound(s)));
	}

}
