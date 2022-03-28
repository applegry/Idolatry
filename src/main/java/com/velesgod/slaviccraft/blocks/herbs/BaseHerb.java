package com.velesgod.slaviccraft.blocks.herbs;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;

public class BaseHerb extends Block implements net.minecraftforge.common.IPlantable {
	
	
   public BaseHerb(Properties prop) {
	  
      super(prop);
   }

 
   protected boolean mayPlaceOn(BlockState state, BlockGetter world, BlockPos pos) {
      return state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.DIRT) || state.is(Blocks.COARSE_DIRT) || state.is(Blocks.PODZOL) || state.is(Blocks.FARMLAND);
   }


   @SuppressWarnings("deprecation")
public BlockState updateShape(BlockState state, Direction dir, BlockState updatestate, LevelAccessor world, BlockPos pos, BlockPos updatepos) {
   return !state.canSurvive(world, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, dir, updatestate, world, pos, updatepos);
   }

   
   public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
      BlockPos blockpos = pos.below();
      if (state.getBlock() == this) 
    	  //Forge: This function is called during world gen and placement, before this block is set, so if we are not 'here' then assume it's the pre-check.
         return world.getBlockState(blockpos).canSustainPlant(world, blockpos, Direction.UP, this);
      return this.mayPlaceOn(world.getBlockState(blockpos), world, blockpos);
   }

   public boolean propagatesSkylightDown(BlockState state, BlockGetter world, BlockPos pos) {
      return state.getFluidState().isEmpty();
   }

   @SuppressWarnings("deprecation")
public boolean isPathfindable(BlockState state, BlockGetter world, BlockPos pos, PathComputationType path) {
      return path == PathComputationType.AIR && !this.hasCollision ? true : super.isPathfindable(state, world, pos, path);
   }

   @Override
   public BlockState getPlant(BlockGetter world, BlockPos pos) {
      BlockState state = world.getBlockState(pos);
      if (state.getBlock() != this) return defaultBlockState();
      return state;
   }


   
}