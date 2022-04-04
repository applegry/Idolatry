package com.velesgod.slaviccraft.core.init;

import com.velesgod.slaviccraft.SlavicCraftMod;
import com.velesgod.slaviccraft.SlavicCraftTab;
//import com.velesgod.slaviccraft.entity.SlavicEntities;
import com.velesgod.slaviccraft.items.AmberStuff;
import com.velesgod.slaviccraft.items.AmuletItem;
//import com.velesgod.slaviccraft.items.BaseAmulet;
import com.velesgod.slaviccraft.items.BaseBalm;
import com.velesgod.slaviccraft.items.BaseBundle;
import com.velesgod.slaviccraft.items.BaseFood;
import com.velesgod.slaviccraft.items.BasePowder;
import com.velesgod.slaviccraft.items.BaseSeed;
//import com.velesgod.slaviccraft.items.BaseSeed;
//import com.velesgod.slaviccraft.items.BaseSpawnEgg;
import com.velesgod.slaviccraft.items.ChiselItem;
import com.velesgod.slaviccraft.items.CopperAmuletItem;
import com.velesgod.slaviccraft.items.Duckweed;
import com.velesgod.slaviccraft.items.EnchBirchBark;
import com.velesgod.slaviccraft.items.LeshinAmuletItem;
import com.velesgod.slaviccraft.items.LinenClothes;
import com.velesgod.slaviccraft.items.PowerAmuletItem;
import com.velesgod.slaviccraft.items.RavenStuff;
import com.velesgod.slaviccraft.items.RitualDagger;
import com.velesgod.slaviccraft.items.Sickle;
import com.velesgod.slaviccraft.items.SlavicArmorMaterial;
//import com.velesgod.slaviccraft.items.SlavicPotionBase;
import com.velesgod.slaviccraft.items.SlavicPotionBase;
import com.velesgod.slaviccraft.items.WreathBase;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
	public static final DeferredRegister<Item> SlavicItems = DeferredRegister.create(ForgeRegistries.ITEMS,
			SlavicCraftMod.MOD_ID);
	
	
	
	
	
	//public static final RegistryObject<BaseSpawnEgg> RAVEN_EGG = SlavicItems.register("raven_spawn_egg",()->
//	new BaseSpawnEgg(SlavicEntities.RAVEN, 0x25262b, 0x25262b, new Item.Properties().stacksTo(64).tab(SlavicCraftTab.SlavicGroup)));
	
	
	
    public static final RegistryObject<ArmorItem> DANDELION_WREATH = defineWreath("dandelion_wreath",SlavicArmorMaterial.DANDELION);
    public static final RegistryObject<ArmorItem> AGILITY_WREATH = defineWreath("agility_wreath_armor",SlavicArmorMaterial.AGILITY);
    public static final RegistryObject<ArmorItem> ENDURACE_WREATH = defineWreath("endurance_wreath_armor",SlavicArmorMaterial.ENDURACE);
    public static final RegistryObject<ArmorItem> FATE_WREATH = defineWreath("fate_wreath_armor",SlavicArmorMaterial.FATE);
    public static final RegistryObject<ArmorItem> GROWTH_WREATH = defineWreath("growth_wreath_armor",SlavicArmorMaterial.GROWTH);
    public static final RegistryObject<ArmorItem> PURITY_WREATH = defineWreath("purity_wreath_armor",SlavicArmorMaterial.PURITY);
    public static final RegistryObject<ArmorItem> WARDING_WREATH = defineWreath("warding_wreath_armor",SlavicArmorMaterial.WARDING);
    public static final RegistryObject<ArmorItem> VIGOR_WREATH = defineWreath("vigor_wreath_armor",SlavicArmorMaterial.VIGOR);
    public static final RegistryObject<ArmorItem> TEARING_WREATH = defineWreath("tearing_wreath_armor",SlavicArmorMaterial.TEARING);
    public static final RegistryObject<ArmorItem> PERCEPTION_WREATH = defineWreath("perception_wreath_armor",SlavicArmorMaterial.PERCEPTION);
    public static final RegistryObject<ArmorItem> MIGHT_WREATH = defineWreath("might_wreath_armor",SlavicArmorMaterial.MIGHT);
    public static final RegistryObject<ArmorItem> DESCENDING_WREATH = defineWreath("descending_wreath_armor",SlavicArmorMaterial.DESCENDING);
    public static final RegistryObject<ArmorItem> SUPREME_WREATH = defineWreath("supreme_wreath_armor",SlavicArmorMaterial.SUPREME);
    public static final RegistryObject<ArmorItem> LINEN_WREATH = defineWreath("linen_headband",SlavicArmorMaterial.LINEN);
    
  //  public static final RegistryObject<ArmorItem> LINEN_TUNIC = SlavicItems.register("linen_tunic", () ->
  //  		new ArmorItem(SlavicArmorMaterial.LINEN, EquipmentSlot.CHEST, (new Item.Properties()).tab(SlavicCraftTab.SlavicGroup)));
    
    
    
    
	public static final RegistryObject<AmuletItem> AMULET = SlavicItems.register("amulet",
			() -> new AmuletItem(SlavicArmorMaterial.AMBER, EquipmentSlot.CHEST, (new Item.Properties()).tab(SlavicCraftTab.SlavicGroup)));
    
	public static final RegistryObject<CopperAmuletItem> COPPER_AMULET = SlavicItems.register("copper_amulet",
			() -> new CopperAmuletItem(SlavicArmorMaterial.AMBER, EquipmentSlot.CHEST, (new Item.Properties()).tab(SlavicCraftTab.SlavicGroup)));
    
	public static final RegistryObject<PowerAmuletItem> POWER_COPPER_AMULET = SlavicItems.register("power_amulet",
			() -> new PowerAmuletItem(SlavicArmorMaterial.AMBER, EquipmentSlot.CHEST, (new Item.Properties()).tab(SlavicCraftTab.SlavicGroup)));
    
	public static final RegistryObject<LeshinAmuletItem> LESHIN_AMULET = SlavicItems.register("leshin_amulet",
			() -> new LeshinAmuletItem(SlavicArmorMaterial.AMBER, EquipmentSlot.CHEST, (new Item.Properties()).tab(SlavicCraftTab.SlavicGroup)));
    

	
    public static final RegistryObject<LinenClothes> LINEN_TUNIC = SlavicItems.register("linen_tunic", () ->
	new LinenClothes(SlavicArmorMaterial.LINEN_CLOTHES, EquipmentSlot.CHEST, (new Item.Properties()).tab(SlavicCraftTab.SlavicGroup)));
    
    
    public static final RegistryObject<LinenClothes> LINEN_PANTS = SlavicItems.register("linen_pants", () ->
	new LinenClothes(SlavicArmorMaterial.LINEN_CLOTHES, EquipmentSlot.LEGS, (new Item.Properties()).tab(SlavicCraftTab.SlavicGroup)));
    
    

    
    
    //-----------Items
    public static final RegistryObject<Item> AMBER = defineItem("amber");//#������� ������//#Dead Stone
	public static final RegistryObject<BasePowder> AMBER_DUST = definePowder("amber_dust");//#�������� ����//#Amber dust
	public static final RegistryObject<Item> KUTYA = SlavicItems.register("kutya", () ->new BaseFood(2,2));//#�����//#Amber dust
	public static final RegistryObject<Item> AMBER_STUFF = SlavicItems.register("amber_stuff", () ->new AmberStuff());//#�������� �����//#Amber Staff
	public static final RegistryObject<Item> RAVEN_FEATHER= defineItem("raven_feather");//#���� ������//#Raven Feather
	public static final RegistryObject<Item> RAVEN_STUFF = SlavicItems.register("raven_stuff", () ->new RavenStuff());//#����� �����//#Wind Staff
	public static final RegistryObject<Item> DEADSTONE = defineItem("deadstone");//#������� ������//#Dead Stone
	public static final RegistryObject<Item> LIVINGSTONE = defineItem("livingstone");//#����� ������//#Living stone
	public static final RegistryObject<Item> BLOOD_BOTTLE = defineItem("blood_bottle");//#������� ��� �����//#Blood Bottle
	public static final RegistryObject<Item> DEADBLOOD_BOTTLE = defineItem("deadblood_bottle");//#������� ������� �����//#Dead Blood Bottle
	public static final RegistryObject<Item> LIVINGBLOOD_BOTTLE = defineItem("liveblood_bottle");//#������� ����� �����//#Living Blood Bottle
	public static final RegistryObject<Item> RITUAL_DAGGER = SlavicItems.register("ritual_dagger",() -> new RitualDagger());//#���������� ������//#Ritual dagger
	public static final RegistryObject<Item> SICKLE = SlavicItems.register("slavic_sickle",() -> new Sickle());//#���������� ������//#Ritual dagger
	
	public static final RegistryObject<Item> LINEN = defineItem("linen");//#˸�//#Linen
	public static final RegistryObject<Item> LINEN_SEEDS = SlavicItems.register("linen_seeds",() -> new BaseSeed(BlockInit.LINEN_BLOCK.get(),5));//#������ ����//#Linen seeds
	public static final RegistryObject<Item> LINEN_GROUND = defineItem("linen_ground");//#������������ ������ ����//#Grounded Linen
	public static final RegistryObject<Item> LINEN_CLOTH = defineItem("linen_cloth");//#������� �����//#Linen Cloth
	public static final RegistryObject<Item> TURNIP_SEEDS = SlavicItems.register("turnip_seeds",() -> new BaseSeed(BlockInit.TURNIP_BLOCK.get(),3));//#������ ����//#Turnip Seeds
	public static final RegistryObject<Item> TURNIP = defineItem("turnip");//#����//#Turnip
	public static final RegistryObject<Item> TURNIP_STEAMED = SlavicItems.register("turnip_steamed", () ->new BaseFood(5,5));//#������� ����//#Steamed Turnip
	
	public static final RegistryObject<Item> MUSH_BUNDLE = defineItem("mush_bundle");
	public static final RegistryObject<Item> DRIED_MUSH_BUNDLE = SlavicItems.register("dried_mush_bundle", () ->new BaseFood(5,5));
	
	public static final RegistryObject<EnchBirchBark> ENC_BB = 	SlavicItems.register("ench_birch_bark",() -> new EnchBirchBark());//#������������ �������//#Enchancted Bark
	public static final RegistryObject<Item> EMP_BB = defineItem("empty_birch_bark");//#�������� �������//#Empty Birch Bark
	public static final RegistryObject<Item> DIR_BB =  defineItem("dirty_birch_bark");//#�������//#Birch Bark
	public static final RegistryObject<Item> AMBER_CHISEL =  SlavicItems.register("slavic_amber_chisel",() ->new ChiselItem());
	public static final RegistryObject<Item> ELIXIR_BOLLTE = defineItem("elixir_bottle");//#������� ��� ��������//#Elixir Bottle
	public static final RegistryObject<SlavicPotionBase> ELIXIR =  SlavicItems.register("elixir", () ->new SlavicPotionBase((new Item.Properties()).rarity(Rarity.EPIC).stacksTo(1).tab(SlavicCraftTab.SlavicGroup))); //#�������//#Elixir

	//-----Dried 
	public static final RegistryObject<BaseBundle> FIREWHIP_DRIED = defineBundle("firewhip_bundle");//#������� ���-����//#Dried Firewhip
	public static final RegistryObject<BaseBundle> HELLEBORE_DRIED = defineBundle("hellebore_bundle");//#������� ��������//#Dried Hellebore
	public static final RegistryObject<BaseBundle> SLEEPGRASS_DRIED = defineBundle("sleepgrass_bundle");//#������� ���-�����//#Dried Sleepgrass
	public static final RegistryObject<BaseBundle> SWIFTFOOT_DRIED = defineBundle("swiftfoot_bundle");//#������� ���������//#Dried Swiftfoot
	public static final RegistryObject<BaseBundle> SWORDBLADE_DRIED = defineBundle("swordblade_bundle");//#������� ���-�����//#Dried Swordblade
	public static final RegistryObject<BaseBundle> THORNAPPLES_DRIED = defineBundle("thornapples_bundle");//#������� ����������//#Dried Thornapple
	public static final RegistryObject<BaseBundle> RAVENEYE_DRIED = defineBundle("paris_bundle");//#������� ������� ����//#Dried Paris
	public static final RegistryObject<BaseBundle> HEMLOCK_DRIED = defineBundle("hemlock_bundle");//#������� ���������//#Dried Hemlock
	public static final RegistryObject<BaseBundle> HENBANE_DRIED = defineBundle("henbane_bundle");//#������� ������//#Dried Henbane
	public static final RegistryObject<BaseBundle> IMMORTELLE_DRIED = defineBundle("immortelle_bundle");//#������� ��������//#Dried Immortelle
	public static final RegistryObject<BaseBundle> CHRYSANTHS_DRIED = defineBundle("crysanth_bundle");//#������� �������������//#Dried Crysanths
	public static final RegistryObject<BaseBundle> WILDROSEMARY_DRIED = defineBundle("wildrosemary_bundle");//#������� ���������//#Dried Wild Rosemary
	
	
	//--Others
	public static final RegistryObject<BaseBundle> ARTEMISISA_DRIED = defineBundle("artemisia_bundle");//#������� ���������//#Dried Wild Rosemary
	public static final RegistryObject<BaseBundle> HEATHER_DRIED = defineBundle("heather_bundle");//#������� ���������//#Dried Wild Rosemary
	public static final RegistryObject<BaseBundle> THISTLES_DRIED = defineBundle("thistles_bundle");//#������� ���������//#Dried Wild Rosemary
	public static final RegistryObject<BaseBundle> CRANESBILL_DRIED = defineBundle("cranesbill_bundle");//#������� ���������//#Dried Wild Rosemary
	public static final RegistryObject<BaseBundle> LOVAGE_DRIED = defineBundle("lovage_bundle");//#������� ���������//#Dried Wild Rosemary
	public static final RegistryObject<BaseBundle> KELP_DRIED = defineBundle("dried_kelp_bundle");//#������� ���������//#Dried Wild Rosemary
	public static final RegistryObject<BaseBundle> DEEPROOT_DRIED = defineBundle("deeproot_bundle");//#������� ���������//#Dried Wild Rosemary
	public static final RegistryObject<BaseBundle> PRIMROSE_DRIED = defineBundle("primrose_bundle");//#������� ���������//#Dried Wild Rosemary
	public static final RegistryObject<BaseBundle> NETTLE_DRIED = defineBundle("nettle_bundle");//#������� ���������//#Dried Wild Rosemary
	public static final RegistryObject<BaseBundle> HERBS_DRIED = defineBundle("dried_herbs_bundle");//#������� ���������//#Dried Wild Rosemary
	
	
	
	//-----Fresh 
		public static final RegistryObject<BaseBundle> FIREWHIP_FRESH = defineBundle("firewhip_fresh");//#������� ���-����//#Dried Firewhip
		public static final RegistryObject<BaseBundle> HELLEBORE_FRESH = defineBundle("hellebore_fresh");//#������� ��������//#Dried Hellebore
		public static final RegistryObject<BaseBundle> SLEEPGRASS_FRESH = defineBundle("sleepgrass_fresh");//#������� ���-�����//#Dried Sleepgrass
		public static final RegistryObject<BaseBundle> SWIFTFOOT_FRESH = defineBundle("swiftfoot_fresh");//#������� ���������//#Dried Swiftfoot
		public static final RegistryObject<BaseBundle> SWORDBLADE_FRESH = defineBundle("swordblade_fresh");//#������� ���-�����//#Dried Swordblade
		public static final RegistryObject<BaseBundle> THORNAPPLES_FRESH = defineBundle("thornapples_fresh");//#������� ����������//#Dried Thornapple
		public static final RegistryObject<BaseBundle> RAVENEYE_FRESH = defineBundle("paris_fresh");//#������� ������� ����//#Dried Paris
		public static final RegistryObject<BaseBundle> HEMLOCK_FRESH = defineBundle("hemlock_fresh");//#������� ���������//#Dried Hemlock
		public static final RegistryObject<BaseBundle> HENBANE_FRESH = defineBundle("henbane_fresh");//#������� ������//#Dried Henbane
		public static final RegistryObject<BaseBundle> IMMORTELLE_FRESH = defineBundle("immortelle_fresh");//#������� ��������//#Dried Immortelle
		public static final RegistryObject<BaseBundle> CHRYSANTHS_FRESH = defineBundle("crysanth_fresh");//#������� �������������//#Dried Crysanths
		public static final RegistryObject<BaseBundle> WILDROSEMARY_FRESH = defineBundle("wildrosemary_fresh");//#������� ���������//#Dried Wild Rosemary
		public static final RegistryObject<BaseBundle> KELP_FRESH = defineBundle("kelp_fresh");//#������� ���������//#Dried Wild Rosemary
	
	//-----Powders
	public static final RegistryObject<BasePowder> FIREWHIP_POWDER = definePowder("firewhip_powder");//#������� �� ���-�����//#Firewhip Powder
	public static final RegistryObject<BasePowder> HELLEBORE_POWDER = definePowder("hellebore_powder");//#������� �� ���������//#Hellebore Balm
	public static final RegistryObject<BasePowder> SLEEPGRASS_POWDER = definePowder("sleepgrass_powder");//#������� �� ���-�����//#Sleepgrass Powder
	public static final RegistryObject<BasePowder> SWIFTFOOT_POWDER = definePowder("swiftfoot_powder");//#������� �� ����������//#Swiftfoot Powder
	public static final RegistryObject<BasePowder> SWORDBLADE_POWDER = definePowder("swordblade_powder");//#������� �� ���-������//#Swordblade Powder
	public static final RegistryObject<BasePowder> THORNAPPLES_POWDER = definePowder("thornapples_powder");//#������� �� �����������//#Thornapples Powder
	public static final RegistryObject<BasePowder> RAVENEYE_POWDER = definePowder("paris_powder");//#������� �� ��������� �����//#Paris Powder
	public static final RegistryObject<BasePowder> HEMLOCK_POWDER = definePowder("hemlock_powder");//#������� �� ����������//#Hemlock Powder
	public static final RegistryObject<BasePowder> HENBANE_POWDER = definePowder("henbane_powder");//#������� �� ������//#Henbane Powder
	public static final RegistryObject<BasePowder> IMMORTELLE_POWDER = definePowder("immortelle_powder");//#������� �� ���������//#Immoretelle Powder
	public static final RegistryObject<BasePowder> CHRYSANTHS_POWDER= definePowder("crysanths_powder");//#������� �� ��������������//#Crysanth Powder
	public static final RegistryObject<BasePowder> WILDROSEMARY_POWDER = definePowder("wildrosemary_powder");//#������� �� ����������//#Wild Rosemary Powder

	
	
	//---Balms
	public static final RegistryObject<BaseBalm> HELLEBORE_BALM = defineBalm("hellebore_balm");//#������� �� ���������//#Hellebore Balm
	public static final RegistryObject<BaseBalm> THORNAPPLES_BALM = defineBalm("thornapples_balm");//#������� �� �����������//#Thornapples Balm
	public static final RegistryObject<BaseBalm> SLEEPGRASS_BALM = defineBalm("sleepgrass_balm");//#������� �� ���-�����//#Sleepgrass Balm
	public static final RegistryObject<BaseBalm> HEMLOCK_BALM = defineBalm("hemlock_balm");//#������� �� ����������//#Hemlock Balm
	public static final RegistryObject<BaseBalm> RAVENEYE_BALM = defineBalm("paris_balm");//#������� �� ��������� �����//#Paris Balm
	public static final RegistryObject<BaseBalm> HANBANE_BALM = defineBalm("henbane_balm");//#������� �� ������ //#Henbane Balm
   
	
	public static final RegistryObject<Duckweed> DUCKWEED_ITEM = SlavicItems.register("duckweed_item", () ->new Duckweed());//#������� �� ������ //#Henbane Balm
	
	
	
	//Spawn around hand
	//@//w.spawnParticle("flame", e.posX + Math.sin((double)i) * 0.3D, e.posY + 0.4D, e.posZ + Math.cos((double)i) * 0.3D, 0.0D, 0.0D, 0.0D);

	public static final RegistryObject<ArmorItem> defineWreath(String name,ArmorMaterial M){
		  return SlavicItems.register(name,() -> new WreathBase(M, EquipmentSlot.HEAD,(new Item.Properties()).tab(SlavicCraftTab.SlavicGroup)));
	}
	
	
	public static final RegistryObject<Item> defineItem(String name) {
		return  SlavicItems.register(name,() -> new Item(new Item.Properties().tab(SlavicCraftTab.SlavicGroup)));
	}
	
	public static final RegistryObject<BaseBalm> defineBalm(String name) {
		return  SlavicItems.register(name,() -> new BaseBalm());
	}
	public static final RegistryObject<BasePowder> definePowder(String name) {
		return  SlavicItems.register(name,() -> new BasePowder());
		
	}
	public static final RegistryObject<BaseBundle> defineBundle(String name) {
		return  SlavicItems.register(name,() -> new BaseBundle());
		
	}
}
