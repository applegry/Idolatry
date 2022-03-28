package com.velesgod.slaviccraft.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CutOakBlock extends Block{
	 public static final DirectionProperty FACING = BlockStateProperties.FACING;
	 // public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;
	public VoxelShape makeShape(){
		VoxelShape shape = Shapes.empty();
		shape = Shapes.join(shape, Shapes.box(2D/16D, 0,2D/16D, 14D/16D, 1, 14D/16D), BooleanOp.OR);
		return shape;
	}
	
	public VoxelShape makeShapeX(){
		VoxelShape shape = Shapes.empty();
		shape = Shapes.join(shape, Shapes.box(0, 2D/16D,2D/16D, 1,14D/16D, 14D/16D), BooleanOp.OR);
		return shape;
	}
	
	public VoxelShape makeShapeZ(){
		VoxelShape shape = Shapes.empty();
		shape = Shapes.join(shape, Shapes.box(2D/16D,2D/16D,0, 14D/16D, 14D/16D, 1), BooleanOp.OR);
		return shape;
	}
	
	public VoxelShape shapeMain = makeShape();
	public VoxelShape shapeMainX = makeShapeX();
	public VoxelShape shapeMainZ = makeShapeZ();
	
	public CutOakBlock() {

		super(Properties.of(Material.WOOD).strength(2.0F).sound(SoundType.WOOD));
	this.registerDefaultState((BlockState)((BlockState)((BlockState)
			this.stateDefinition.any()).setValue(FACING,Direction.SOUTH)));

	}
	
	@Override
	 protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_206840_1_) {
		    p_206840_1_.add(FACING);
		  }
	@Override
		  public BlockState rotate(BlockState p_185499_1_, Rotation p_185499_2_) {
		    return (BlockState)p_185499_1_.setValue(FACING, p_185499_2_.rotate((Direction)p_185499_1_.getValue(FACING)));
		  }
		  
	@SuppressWarnings("deprecation")
	@Override
		  public BlockState mirror(BlockState p_185471_1_, Mirror p_185471_2_) {
		    return p_185471_1_.rotate(p_185471_2_.getRotation((Direction)p_185471_1_.getValue(FACING)));
		  }
		  

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext p_196258_1_) {
			    return (BlockState)defaultBlockState().setValue(FACING, p_196258_1_.getClickedFace().getOpposite());
			  } 
		  
		  
	@Override
	   public boolean useShapeForLightOcclusion(BlockState p_220074_1_) {
	      return true;
	   }
	
	@Override
	   public VoxelShape getShape(BlockState p_220053_1_, BlockGetter p_220053_2_, BlockPos pos, CollisionContext p_220053_4_) {
		switch (((Direction)p_220053_1_.getValue(FACING)).getAxis()) {
	    default:
	      return shapeMainX;
	    case Z:
	      return shapeMainZ;
	    case Y:
	      break;
	  } 
	  return shapeMain;
	   }
	
//	@Override
//	public BlockRenderType getRenderShape(BlockState p_149645_1_) {return BlockRenderType.MODEL;}
	
	@Override
	public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		// TODO Auto-generated method stub
		return 250;
	}
}