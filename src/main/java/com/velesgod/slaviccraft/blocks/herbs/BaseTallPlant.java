package com.velesgod.slaviccraft.blocks.herbs;

import javax.annotation.Nullable;

import com.velesgod.slaviccraft.core.init.EffectInit;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;



public class BaseTallPlant extends DoublePlantBlock implements LiquidBlockContainer{
   public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   public boolean isWaterPlant = false;
   public MobEffect Eff = null;
   
	public static VoxelShape makeShape(){
		VoxelShape shape = Shapes.empty();
		shape = Shapes.join(shape, Shapes.box(0.2, 0, 0.2, 0.8, 1, 0.8), BooleanOp.OR);
		return shape;
		
	}
	
		public static VoxelShape shapeMain = makeShape();
	
		@Override
		   public VoxelShape getShape(BlockState p_220053_1_, BlockGetter p_220053_2_, BlockPos pos, CollisionContext p_220053_4_) {
		      return Eff == null ? Shapes.box(0, 0, 0, 1, 1, 1):shapeMain;
		   }
	
   
   
   
   
   
   public BaseTallPlant() {
	      super(Properties.copy(Blocks.TALL_GRASS));
      this.registerDefaultState(this.stateDefinition.any().setValue(HALF, DoubleBlockHalf.LOWER).setValue(WATERLOGGED,Boolean.valueOf(false)));
   }
  
   public BaseTallPlant(boolean isWater) {
	      super(Properties.of(Material.PLANT).sound(SoundType.GRASS).noCollission().instabreak().noOcclusion());
	      isWaterPlant = isWater;
	      this.registerDefaultState(this.stateDefinition.any().setValue(HALF, DoubleBlockHalf.LOWER).setValue(WATERLOGGED,Boolean.valueOf(false)));
	   }

   public BaseTallPlant(MobEffect effect) {
	      super(Properties.of(Material.PLANT).sound(SoundType.GRASS).noCollission().instabreak().noOcclusion());
	      this.registerDefaultState(this.stateDefinition.any().setValue(HALF, DoubleBlockHalf.LOWER).setValue(WATERLOGGED,Boolean.valueOf(false)));
	      Eff = effect;
   }
   
   
   public BlockState updateShape(BlockState p_196271_1_, Direction p_196271_2_, BlockState p_196271_3_, LevelAccessor p_196271_4_, BlockPos p_196271_5_, BlockPos p_196271_6_) {
      DoubleBlockHalf doubleblockhalf = p_196271_1_.getValue(HALF);
      if (p_196271_2_.getAxis() != Direction.Axis.Y || doubleblockhalf == DoubleBlockHalf.LOWER != (p_196271_2_ == Direction.UP) || p_196271_3_.is(this) && p_196271_3_.getValue(HALF) != doubleblockhalf) {
         return doubleblockhalf == DoubleBlockHalf.LOWER && p_196271_2_ == Direction.DOWN && !p_196271_1_.canSurvive(p_196271_4_, p_196271_5_) ? Blocks.AIR.defaultBlockState() : super.updateShape(p_196271_1_, p_196271_2_, p_196271_3_, p_196271_4_, p_196271_5_, p_196271_6_);
      } else {
         return Blocks.AIR.defaultBlockState();
      }
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
	   LevelAccessor iworld = context.getLevel();
      BlockPos blockpos = context.getClickedPos();
      boolean flag = iworld.getFluidState(blockpos).getType() == Fluids.WATER;
      return blockpos.getY() < 255 && context.getLevel().getBlockState(blockpos.above()).canBeReplaced(context) 
    		  ? this.defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(flag&&isWaterPlant)) : null;
   }

   public void setPlacedBy(Level p_180633_1_, BlockPos p_180633_2_, BlockState p_180633_3_, LivingEntity p_180633_4_, ItemStack p_180633_5_) {
      p_180633_1_.setBlock(p_180633_2_.above(), this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER), 3);
   }

   public boolean canSurvive(BlockState p_196260_1_, LevelReader p_196260_2_, BlockPos p_196260_3_) {
      if (p_196260_1_.getValue(HALF) != DoubleBlockHalf.UPPER) {
         return super.canSurvive(p_196260_1_, p_196260_2_, p_196260_3_);
      } else {
         BlockState blockstate = p_196260_2_.getBlockState(p_196260_3_.below());
         if (p_196260_1_.getBlock() != this) return super.canSurvive(p_196260_1_, p_196260_2_, p_196260_3_); //Forge: This function is called during world gen and placement, before this block is set, so if we are not 'here' then assume it's the pre-check.
         return blockstate.is(this) && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER;
      }
   }

   public void placeAt(LevelAccessor p_196390_1_, BlockPos p_196390_2_, int p_196390_3_) {
      p_196390_1_.setBlock(p_196390_2_, this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER), p_196390_3_);
      p_196390_1_.setBlock(p_196390_2_.above(), this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER), p_196390_3_);
   }

   public void playerWillDestroy(Level p_176208_1_, BlockPos p_176208_2_, BlockState p_176208_3_, Player p_176208_4_) {
      if (!p_176208_1_.isClientSide) {
         if (p_176208_4_.isCreative()) {
            preventCreativeDropFromBottomPart(p_176208_1_, p_176208_2_, p_176208_3_, p_176208_4_);
         } else {
            dropResources(p_176208_3_, p_176208_1_, p_176208_2_, (BlockEntity)null, p_176208_4_, p_176208_4_.getMainHandItem());
         }
      }

      super.playerWillDestroy(p_176208_1_, p_176208_2_, p_176208_3_, p_176208_4_);
   }

   public void playerDestroy(Level p_180657_1_, Player p_180657_2_, BlockPos p_180657_3_, BlockState p_180657_4_, @Nullable BlockEntity p_180657_5_, ItemStack p_180657_6_) {
      super.playerDestroy(p_180657_1_, p_180657_2_, p_180657_3_, Blocks.AIR.defaultBlockState(), p_180657_5_, p_180657_6_);
   }

   protected static void preventCreativeDropFromBottomPart(Level p_241471_0_, BlockPos p_241471_1_, BlockState p_241471_2_, Player p_241471_3_) {
      DoubleBlockHalf doubleblockhalf = p_241471_2_.getValue(HALF);
      if (doubleblockhalf == DoubleBlockHalf.UPPER) {
         BlockPos blockpos = p_241471_1_.below();
         BlockState blockstate = p_241471_0_.getBlockState(blockpos);
         if (blockstate.getBlock() == p_241471_2_.getBlock() && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER) {
            p_241471_0_.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 35);
            p_241471_0_.levelEvent(p_241471_3_, 2001, blockpos, Block.getId(blockstate));
         }
      }

   }

   @Override
	public void entityInside(BlockState p_196262_1_, Level p_196262_2_, BlockPos p_196262_3_, Entity ent) {
	  if(ent instanceof LivingEntity && Eff !=null && !p_196262_2_.isClientSide())
		  if(! (  ((LivingEntity)ent).hasEffect(Eff)) && ! (  ((LivingEntity)ent).hasEffect(EffectInit.POISON_RESIST.get()))) 
		  {
		  ((LivingEntity)ent).addEffect(new MobEffectInstance(Eff,65));
		
		  }
	}
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_206840_1_) {
      p_206840_1_.add(HALF,WATERLOGGED);
   }

   public BlockBehaviour.OffsetType getOffsetType() {
      return BlockBehaviour.OffsetType.XZ;
   }

//   @OnlyIn(Dist.CLIENT)
//   public long getSeed(BlockState p_209900_1_, BlockPos p_209900_2_) {
//      return MathHelper.getSeed(p_209900_2_.getX(), p_209900_2_.below(p_209900_1_.getValue(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), p_209900_2_.getZ());
//   }
   
   @Override
	public long getSeed(BlockState p_52891_, BlockPos p_52892_) {
		// TODO Auto-generated method stub
		return super.getSeed(p_52891_, p_52892_);
	}
   
   @Override
   public FluidState getFluidState(BlockState state) {
	   if(state.getValue(WATERLOGGED) && state.getValue(HALF)== DoubleBlockHalf.LOWER) return Fluids.WATER.getSource(false); else
		   return Fluids.EMPTY.defaultFluidState();
	   //   return p_204507_1_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_204507_1_);
	   }

   
   @Override
   public boolean placeLiquid(LevelAccessor world, BlockPos pos, BlockState state, FluidState fluidState) {
	   if(!isWaterPlant) return false;
	   if(state.getValue(HALF) == DoubleBlockHalf.LOWER) {
		   state.setValue(BlockStateProperties.WATERLOGGED,Boolean.valueOf(true));
		   world.setBlock(pos, state, 12);
		   return true;
	   }else return false;
	   }
   
   @Override
	public InteractionResult use(BlockState p_60503_, Level p_60504_, BlockPos p_60505_, Player p_60506_,
			InteractionHand p_60507_, BlockHitResult p_60508_) {
		// TODO Auto-generated method stub
		return InteractionResult.PASS;
	}
   
 


@Override
public boolean canPlaceLiquid(BlockGetter p_54766_, BlockPos p_54767_, BlockState p_54768_, Fluid p_54769_) {
	// TODO Auto-generated method stub
	return false;
}
   
}
