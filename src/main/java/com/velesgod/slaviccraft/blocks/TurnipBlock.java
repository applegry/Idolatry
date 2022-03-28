package com.velesgod.slaviccraft.blocks;

import java.util.Random;



import com.velesgod.slaviccraft.blocks.herbs.BaseHerb;
import com.velesgod.slaviccraft.core.init.ItemInit;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.ForgeEventFactory;

public class TurnipBlock extends BaseHerb implements BonemealableBlock {
   public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
   private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};

   public TurnipBlock() {
      super(BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP));
      this.registerDefaultState(this.stateDefinition.any().setValue(this.getAgeProperty(), Integer.valueOf(0)));
   }
   
   @Override
   public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
      return SHAPE_BY_AGE[state.getValue(this.getAgeProperty())];
   }
   
   @Override
   protected boolean mayPlaceOn(BlockState state, BlockGetter world, BlockPos pos) {
      return state.is(Blocks.FARMLAND);
   }
   
   public IntegerProperty getAgeProperty() {
      return AGE;
   }

   public int getMaxAge() {
      return 3;
   }

   protected int getAge(BlockState state) {
      return state.getValue(this.getAgeProperty());
   }
 
   public BlockState getStateForAge(int age) {
      return this.defaultBlockState().setValue(this.getAgeProperty(), Integer.valueOf(age));
   }

   public boolean isMaxAge(BlockState state) {
      return state.getValue(this.getAgeProperty()) >= this.getMaxAge();
   }
   @Override
   public boolean isRandomlyTicking(BlockState state) {
      return !this.isMaxAge(state);
   }
   @SuppressWarnings("deprecation")
   @Override
   public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
      if (!world.isAreaLoaded(pos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
      if (world.getRawBrightness(pos, 0) >= 9) {
         int i = this.getAge(state);
         if (i < this.getMaxAge()) {
            float f = getGrowthSpeed(this, world, pos);
            if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(world, pos, state, random.nextInt((int)(25.0F / f) + 1) == 0)) {
            	world.setBlock(pos, this.getStateForAge(i + 1), 2);
               net.minecraftforge.common.ForgeHooks.onCropsGrowPost(world, pos, state);
            }
         }
      }

   }

   public void growCrops(Level world, BlockPos pos, BlockState state) {
      int i = this.getAge(state) + this.getBonemealAgeIncrease(world);
      int j = this.getMaxAge();
      if (i > j) {
         i = j;
      }

      world.setBlock(pos, this.getStateForAge(i), 2);
   }

   protected int getBonemealAgeIncrease(Level world) {
      return world.getRandom().nextInt(10) % 2+1;
   }

   protected static float getGrowthSpeed(Block block, BlockGetter world, BlockPos pos) {
      float f = 1.0F;
      BlockPos blockpos = pos.below();

      for(int i = -1; i <= 1; ++i) {
         for(int j = -1; j <= 1; ++j) {
            float f1 = 0.0F;
            BlockState blockstate = world.getBlockState(blockpos.offset(i, 0, j));
            if (blockstate.canSustainPlant(world, blockpos.offset(i, 0, j),Direction.UP, (net.minecraftforge.common.IPlantable) block)) {
               f1 = 1.0F;
               if (blockstate.isFertile(world, pos.offset(i, 0, j))) {
                  f1 = 3.0F;
               }
            }

            if (i != 0 || j != 0) {
               f1 /= 4.0F;
            }

            f += f1;
         }
      }

      BlockPos blockpos1 = pos.north();
      BlockPos blockpos2 = pos.south();
      BlockPos blockpos3 = pos.west();
      BlockPos blockpos4 = pos.east();
      boolean flag = block == world.getBlockState(blockpos3).getBlock() || block == world.getBlockState(blockpos4).getBlock();
      boolean flag1 = block == world.getBlockState(blockpos1).getBlock() || block == world.getBlockState(blockpos2).getBlock();
      if (flag && flag1) {
         f /= 2.0F;
      } else {
         boolean flag2 = block == world.getBlockState(blockpos3.north()).getBlock() || block == world.getBlockState(blockpos4.north()).getBlock() || block == world.getBlockState(blockpos4.south()).getBlock() || block == world.getBlockState(blockpos3.south()).getBlock();
         if (flag2) {
            f /= 2.0F;
         }
      }

      return f;
   }
   @Override
   public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
      return (world.getRawBrightness(pos, 0) >= 8 || world.canSeeSky(pos)) && super.canSurvive(state, world, pos);
   }
   
   
   @SuppressWarnings("deprecation")
@Override
	public void entityInside(BlockState state, Level world, BlockPos pos,
		Entity entity) {
	   if (entity instanceof Ravager && ForgeEventFactory.getMobGriefingEvent(world, entity)) {
	    	  world.destroyBlock(pos, true, entity);
	      }

		super.entityInside(state, world, pos, entity);
	}


   protected ItemLike getBaseSeedId() {
	      return ItemInit.TURNIP_SEEDS.get();
	   }
   
   @Override
   public ItemStack getCloneItemStack(BlockGetter world, BlockPos pos, BlockState state) {
      return null;//return new ItemStack(this.getBaseSeedId());
   }
   @Override
   public boolean isValidBonemealTarget(BlockGetter p_50897_, BlockPos p_50898_, BlockState state, boolean p_50900_) {
   	// TODO Auto-generated method stub
   	  return !this.isMaxAge(state);
   }

   @Override
   public boolean isBonemealSuccess(Level p_50901_, Random p_50902_, BlockPos p_50903_, BlockState p_50904_) {
   	// TODO Auto-generated method stub
   	return true;
   }

   @Override
   public void performBonemeal(ServerLevel world, Random p_50894_, BlockPos pos, BlockState state) {
   	this.growCrops(world, pos, state);
   	
   }
   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> build) {
	   build.add(AGE);
   }


}
