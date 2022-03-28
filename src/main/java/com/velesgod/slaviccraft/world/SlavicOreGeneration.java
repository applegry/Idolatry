package com.velesgod.slaviccraft.world;

import java.util.List;

import com.velesgod.slaviccraft.SlavicCraftMod;
import com.velesgod.slaviccraft.core.init.BlockInit;

import net.minecraft.core.BlockPos;
import net.minecraft.data.worldgen.biome.Biomes;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.util.valueproviders.ClampedInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockColumnConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class SlavicOreGeneration {
	
    protected static ConfiguredFeature<?, ?> AMBER_ORE_FEATURE = Feature.ORE.configured(new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES, BlockInit.AMBER_ORE.get().defaultBlockState(),8));
    
    public static final ConfiguredFeature<DiskConfiguration, ?> DISK_SWAMP_FEATURE = 
    	FeatureUtils.register("disk_swamp", Feature.DISK.configured(
    			new DiskConfiguration(BlockInit.SWAMP_IRON.get().defaultBlockState(), UniformInt.of(2, 3), 1, 
    					List.of(Blocks.DIRT.defaultBlockState(), Blocks.CLAY.defaultBlockState()))));

    
    
    public static final ConfiguredFeature<RandomPatchConfiguration, ?> SLAVIC_PLAINS_FLOWERS = 
    FeatureUtils.register("slavic_plains_flower", Feature.FLOWER
    		.configured(grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
    				.add(BlockInit.ARTEMISIA.get().defaultBlockState(), 2)
    				.add(BlockInit.FIREWHIP.get().defaultBlockState(), 2)
    				.add(BlockInit.IMMORETLE.get().defaultBlockState(), 2)
    				.add(BlockInit.HEMLOCK.get().defaultBlockState(), 2)
    				.add(BlockInit.HENBANE.get().defaultBlockState(), 2)
    				.add(BlockInit.THYME.get().defaultBlockState(), 1)), 64)));
    
    
    public static final ConfiguredFeature<RandomPatchConfiguration, ?> SLAVIC_TAIGA_FLOWERS = 
    FeatureUtils.register("slavic_taiga_flower", Feature.FLOWER
    		.configured(grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
    				.add(BlockInit.CHANTERELLES.get().defaultBlockState(), 32)), 64)));
    
    public static final ConfiguredFeature<RandomPatchConfiguration, ?> SLAVIC_SWAMP_FLOWERS = 
    FeatureUtils.register("slavic_swamp_flower", Feature.FLOWER
    		.configured(grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
    				.add(BlockInit.HEATHER.get().defaultBlockState(), 32)
    				.add(BlockInit.SWORDBLADE.get().defaultBlockState(), 32)
    				.add(BlockInit.WILDROSEMARY.get().defaultBlockState(), 32)), 64)));
    
  //  public static final ConfiguredFeature<RandomPatchConfiguration, ?> SLAVIC_OAK_FLOWERS = 
 //   FeatureUtils.register("slavic_oak_flower", Feature.FLOWER
 //   		.configured(grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
 ////   				.add(BlockInit.CHRYSANTHS.get().defaultBlockState(), 32)
  //  				.add(BlockInit.HONEYAGARIC.get().defaultBlockState(), 32)), 64)));
    
    
    public static final ConfiguredFeature<RandomPatchConfiguration, ?> SLAVIC_DARK_FLOWERS = 
    FeatureUtils.register("slavic_dark_flower", Feature.FLOWER
    		.configured(grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
    				.add(BlockInit.PARIS.get().defaultBlockState(), 32)
    				.add(BlockInit.THORNAPPLES.get().defaultBlockState(), 32)), 64)));

    public static final ConfiguredFeature<RandomPatchConfiguration, ?> SLAVIC_MOUNTS_FLOWERS = 
    FeatureUtils.register("slavic_mounts_flower", Feature.FLOWER
    		.configured(grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
    				.add(BlockInit.SWIFTFOOT.get().defaultBlockState(), 32)
    				.add(BlockInit.THYME.get().defaultBlockState(), 32)
    				.add(BlockInit.CRANESBILL.get().defaultBlockState(), 32)), 64)));
    
    public static final ConfiguredFeature<RandomPatchConfiguration, ?> SLAVIC_BIRCH_FLOWERS = 
    FeatureUtils.register("slavic_birch_flower", Feature.FLOWER
    		.configured(grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
    				.add(BlockInit.SLEEPGRASS.get().defaultBlockState(), 32)), 64)));
    
    public static final ConfiguredFeature<RandomPatchConfiguration, ?> SLAVIC_SNOW_TAIGA_FLOWERS = 
    FeatureUtils.register("slavic_snow_taiga_flower", Feature.FLOWER
    		.configured(grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
    				.add(BlockInit.HELLEBORE.get().defaultBlockState(), 32)), 64)));
    
    public static final ConfiguredFeature<RandomPatchConfiguration, ?> SLAVIC_SNOW_PLAINS_FLOWERS = 
    FeatureUtils.register("slavic_snow_plains_flower", Feature.FLOWER
    		.configured(grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
    				.add(BlockInit.PRIMROSE.get().defaultBlockState(), 32)), 64)));
    
    public static final ConfiguredFeature<RandomPatchConfiguration, ?> SLAVIC_HILL_TAIGA_FLOWERS = 
    FeatureUtils.register("slavic_hill_taiga_flower", Feature.FLOWER
    		.configured(grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
    				.add(BlockInit.THISTLES.get().defaultBlockState(), 32)), 64)));
    
    public static final ConfiguredFeature<RandomPatchConfiguration, ?> SLAVIC_LOVAGE_FLOWERS = 
    FeatureUtils.register("slavic_lovage_flower", Feature.FLOWER
    		.configured(grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
    				.add(BlockInit.LOVAGE.get().defaultBlockState(), 32)), 64)));
    
    public static final ConfiguredFeature<RandomPatchConfiguration, ?> SLAVIC_NETTLE_FLOWERS = 
    FeatureUtils.register("slavic_nettle_flower", Feature.FLOWER
    		.configured(grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
    				.add(BlockInit.LOVAGE.get().defaultBlockState(), 32)), 64)));
    

    
    public static final ConfiguredFeature<RandomPatchConfiguration, ?> CATTAIL = 
    	    FeatureUtils.register("slavic_nettle_flower", Feature.FLOWER
    	    		.configured(grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
    	    				
    	    				.add(BlockInit.CATTAILS.get().defaultBlockState(), 32)), 64)));
    
    
    
    public static final ConfiguredFeature<SimpleRandomFeatureConfiguration, ?> SLAVIC_OAK_FLOWERS = FeatureUtils.register("slavic_oak_flower", Feature.SIMPLE_RANDOM_SELECTOR.configured(new SimpleRandomFeatureConfiguration(List.of(() -> {
        return Feature.RANDOM_PATCH.configured(FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK.configured(new SimpleBlockConfiguration(BlockStateProvider.simple(BlockInit.HONEYAGARIC.get()))))).placed();
     }, () -> {
        return Feature.NO_BONEMEAL_FLOWER.configured(FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK.configured(new SimpleBlockConfiguration(BlockStateProvider.simple(BlockInit.CHRYSANTHS.get()))))).placed();
     }))));
    
    
    

    
    
    
    public static final PlacedFeature CATTAILS = PlacementUtils.register("slavic_cattail", CATTAIL.placed(RarityFilter.onAverageOnceEvery(23), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
    
    
    public static final PlacedFeature PLAIN_FLOWER = PlacementUtils.register("slavic_plains_flower", SLAVIC_PLAINS_FLOWERS.placed(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
    
    public static final PlacedFeature TAIGA_FLOWER = PlacementUtils.register("slavic_taiga_flower", SLAVIC_TAIGA_FLOWERS.placed(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
    
    public static final PlacedFeature SNOW_TAIGA_FLOWER = PlacementUtils.register("slavic_snow_taiga_flower", SLAVIC_SNOW_TAIGA_FLOWERS.placed(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
    
    public static final PlacedFeature MOUNTS_FLOWER = PlacementUtils.register("slavic_mounts_flower", SLAVIC_MOUNTS_FLOWERS.placed(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
    
    public static final PlacedFeature DARK_FLOWER = PlacementUtils.register("slavic_dark_flower", SLAVIC_DARK_FLOWERS.placed(RarityFilter.onAverageOnceEvery(70), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, CountPlacement.of(ClampedInt.of(UniformInt.of(-3, 1), 0, 4)), BiomeFilter.biome()));
    
    public static final PlacedFeature OAK_FLOWER= PlacementUtils.register("slavic_oak_flower", SLAVIC_OAK_FLOWERS.placed(RarityFilter.onAverageOnceEvery(7), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, CountPlacement.of(ClampedInt.of(UniformInt.of(-3, 1), 0, 1)), BiomeFilter.biome()));
    
    public static final PlacedFeature BIRCH_FLOWER = PlacementUtils.register("slavic_brich_flower", SLAVIC_BIRCH_FLOWERS.placed(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
    
    public static final PlacedFeature SWAMP_FLOWER = PlacementUtils.register("slavic_swamp_flower", SLAVIC_SWAMP_FLOWERS.placed(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
    
    public static final PlacedFeature SNOW_PLAINS_FLOWER = PlacementUtils.register("slavic_snow_plains_flower", SLAVIC_SNOW_PLAINS_FLOWERS.placed(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
    
    public static final PlacedFeature HILLS_TAIGA_FLOWER = PlacementUtils.register("slavic_hill_taiga_flower", SLAVIC_HILL_TAIGA_FLOWERS.placed(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
    
    public static final PlacedFeature LOVAGE = PlacementUtils.register("slavic_lovage_flower", SLAVIC_LOVAGE_FLOWERS.placed(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
    
    public static final PlacedFeature NETTLE = PlacementUtils.register("slavic_nettle_flower", SLAVIC_NETTLE_FLOWERS.placed(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
    
    //ORES
    
    public static final PlacedFeature AMBER_ORE = AMBER_ORE_FEATURE.placed(commonOrePlacement(8, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(128))));

    public static final PlacedFeature DISK_SWAMP = PlacementUtils.register("disk_swamp", DISK_SWAMP_FEATURE.placed(InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()));
    
    
    
    private static RandomPatchConfiguration grassPatch(BlockStateProvider p_195203_, int p_195204_) {
        return FeatureUtils.simpleRandomPatchConfiguration(p_195204_, Feature.SIMPLE_BLOCK.configured(new SimpleBlockConfiguration(p_195203_)).onlyWhenEmpty());
     }
    
    
    
    
    
    public static void onBiomeLoading(BiomeLoadingEvent event)
    {
        if (event.getCategory() != BiomeCategory.DESERT && event.getCategory() != BiomeCategory.MESA && event.getCategory() != BiomeCategory.SAVANNA && event.getCategory() != BiomeCategory.JUNGLE && event.getCategory() != BiomeCategory.THEEND && event.getCategory() != BiomeCategory.NETHER) 
        	if(event.getName().getPath().contains("taiga")|| (event.getName().getPath().contains("beach") && event.getClimate().temperature > 0.1f))
        {
            event.getGeneration().getFeatures(Decoration.UNDERGROUND_DECORATION).add(() -> AMBER_ORE);
          
        }
        if(event.getCategory() == Biome.BiomeCategory.SWAMP) {
        	event.getGeneration().addFeature(Decoration.TOP_LAYER_MODIFICATION, DISK_SWAMP).build();
        	event.getGeneration().addFeature(Decoration.VEGETAL_DECORATION, SWAMP_FLOWER).build();
        	event.getGeneration().addFeature(Decoration.VEGETAL_DECORATION, CATTAILS).build();
        }
        
        if(event.getCategory() == BiomeCategory.PLAINS && event.getClimate().temperature > 0)
    	event.getGeneration().addFeature(Decoration.VEGETAL_DECORATION, PLAIN_FLOWER).build();
        
        if(event.getCategory() == BiomeCategory.PLAINS && event.getClimate().temperature < 0)
    	event.getGeneration().addFeature(Decoration.VEGETAL_DECORATION, SNOW_PLAINS_FLOWER).build();
        
        if(event.getCategory() == BiomeCategory.TAIGA && event.getClimate().temperature < 0)
    	event.getGeneration().addFeature(Decoration.VEGETAL_DECORATION, SNOW_TAIGA_FLOWER).build();
        
        if(event.getCategory() == BiomeCategory.TAIGA && event.getClimate().temperature > 0)
    	event.getGeneration().addFeature(Decoration.VEGETAL_DECORATION, TAIGA_FLOWER).build();
        
        if(event.getCategory() == BiomeCategory.MOUNTAIN)
    	event.getGeneration().addFeature(Decoration.VEGETAL_DECORATION, MOUNTS_FLOWER).build();
        
        if(event.getCategory() == BiomeCategory.FOREST && event.getName().getPath().contains("dark"))
    	event.getGeneration().addFeature(Decoration.VEGETAL_DECORATION, DARK_FLOWER).build();
        
        if(event.getCategory() == BiomeCategory.FOREST && !event.getName().getPath().contains("taiga"))
    	event.getGeneration().addFeature(Decoration.VEGETAL_DECORATION, OAK_FLOWER).build();
        
        if(event.getCategory() == BiomeCategory.FOREST && event.getName().getPath().contains("birch"))
        	event.getGeneration().addFeature(Decoration.VEGETAL_DECORATION, BIRCH_FLOWER).build();

        
        if(event.getCategory() == BiomeCategory.TAIGA && event.getName().getPath().contains("hill"))
        	event.getGeneration().addFeature(Decoration.VEGETAL_DECORATION, SNOW_PLAINS_FLOWER).build();
        
        if(event.getCategory() == BiomeCategory.RIVER && event.getClimate().temperature > 0)
        	event.getGeneration().addFeature(Decoration.VEGETAL_DECORATION, NETTLE).build();
        
        	event.getGeneration().addFeature(Decoration.VEGETAL_DECORATION, LOVAGE).build();
    }

    
    
    public static void initFeatures()
    {
        FeatureUtils.register(new ResourceLocation(SlavicCraftMod.MOD_ID, "amber_ore_overworld").toString(), AMBER_ORE_FEATURE);
        
        PlacementUtils.register(new ResourceLocation(SlavicCraftMod.MOD_ID, "amber_ore_overworld").toString(), AMBER_ORE);
    }

    
    private static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier placementModifier)
    {
        return List.of(CountPlacement.of(count), InSquarePlacement.spread(), placementModifier, BiomeFilter.biome());
    }
}