package com.velesgod.slaviccraft.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LeshinHead extends Block{

	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	
	public VoxelShape makeShapeNorth(){
		VoxelShape shape = Shapes.empty();
		shape = Shapes.join(shape, Shapes.box(0.31250000000000006, -0.011104465955477094, 0.3883041822664447, 0.6875, 0.1138955340445229, 0.8883041822664446), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(-0.0625, 0.2604466383621715, 0.8194056228676523, 0.31250000000000006, 0.3854466383621715, 0.9444056228676523), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.6875, 0.2604466383621715, 0.8194056228676523, 1.0625, 0.3854466383621715, 0.9444056228676523), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(-0.0625, 0.3854466383621715, 0.8194056228676523, 0.06250000000000006, 0.6354466383621715, 0.9444056228676523), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.9375, 0.3854466383621715, 0.8194056228676523, 1.0625, 0.6354466383621715, 0.9444056228676523), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.31250000000000006, 0.07294663836217152, 0.3819056228676524, 0.6875, 0.4479466383621715, 1.0069056228676523), BooleanOp.OR);

		return shape;
	}
	
	public VoxelShape makeShapeSouth(){
		VoxelShape shape = Shapes.empty();
		shape = Shapes.join(shape, Shapes.box(0.31250000000000006, -0.011104465955477094, 0.13190562286765256, 0.6875, 0.1138955340445229, 0.6319056228676525), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.6875, 0.2604466383621715, 0.07580418226644486, 1.0625, 0.3854466383621715, 0.20080418226644492), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(-0.0625, 0.2604466383621715, 0.07580418226644486, 0.31250000000000006, 0.3854466383621715, 0.20080418226644492), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.9375, 0.3854466383621715, 0.07580418226644486, 1.0625, 0.6354466383621715, 0.20080418226644492), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(-0.0625, 0.3854466383621715, 0.07580418226644486, 0.06250000000000006, 0.6354466383621715, 0.20080418226644492), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.31250000000000006, 0.07294663836217152, 0.013304182266444864, 0.6875, 0.4479466383621715, 0.6383041822664448), BooleanOp.OR);

		return shape;
	}
	
	public VoxelShape makeShapeWest(){
		VoxelShape shape = Shapes.empty();
		shape = Shapes.join(shape, Shapes.box(0.3781992796993961, -0.011104465955477094, 0.32260490256704866, 0.878199279699396, 0.1138955340445229, 0.6976049025670485), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.8093007203006037, 0.2604466383621715, 0.6976049025670485, 0.9343007203006037, 0.3854466383621715, 1.0726049025670485), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.8093007203006037, 0.2604466383621715, -0.0523950974329514, 0.9343007203006037, 0.3854466383621715, 0.32260490256704866), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.8093007203006037, 0.3854466383621715, 0.9476049025670485, 0.9343007203006037, 0.6354466383621715, 1.0726049025670485), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.8093007203006037, 0.3854466383621715, -0.0523950974329514, 0.9343007203006037, 0.6354466383621715, 0.07260490256704866), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.3718007203006038, 0.07294663836217152, 0.32260490256704866, 0.9968007203006037, 0.4479466383621715, 0.6976049025670485), BooleanOp.OR);

		return shape;
	}
	
	
	public VoxelShape makeShapeEast(){
		VoxelShape shape = Shapes.empty();
		shape = Shapes.join(shape, Shapes.box(0.12180072030060402, -0.011104465955477094, 0.32260490256704866, 0.621800720300604, 0.1138955340445229, 0.6976049025670485), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.06569927969939632, 0.2604466383621715, -0.0523950974329514, 0.19069927969939632, 0.3854466383621715, 0.32260490256704866), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.06569927969939632, 0.2604466383621715, 0.6976049025670485, 0.19069927969939632, 0.3854466383621715, 1.0726049025670485), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.06569927969939632, 0.3854466383621715, -0.0523950974329514, 0.19069927969939632, 0.6354466383621715, 0.0726049025670486), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.06569927969939632, 0.3854466383621715, 0.9476049025670485, 0.19069927969939632, 0.6354466383621715, 1.0726049025670485), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.003199279699396318, 0.07294663836217152, 0.32260490256704866, 0.6281992796993963, 0.4479466383621715, 0.6976049025670485), BooleanOp.OR);

		return shape;
	}
	
	
	public VoxelShape northShape =makeShapeNorth();
	
	
	public VoxelShape southShape =makeShapeSouth();
	
	
	public VoxelShape eastShape =makeShapeEast();
	
	
	public VoxelShape westShape =makeShapeWest();
	
	
	public LeshinHead() {
		
		super(Properties.copy(Blocks.CALCITE).strength(0.1f).noOcclusion());
		this.registerDefaultState((BlockState)((BlockState)((BlockState)
				this.stateDefinition.any()).setValue(FACING,Direction.SOUTH)));
	}
	
	@SuppressWarnings("deprecation")
	@Override
		   public BlockState mirror(BlockState state, Mirror mirr) {
		      return state.rotate(mirr.getRotation(state.getValue(FACING)));
		   }
	@Override
		   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING);
		   }
	
	@Override
	   public BlockState rotate(BlockState state, Rotation rot) {
		      return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
		   }
	@Override
	   public BlockState getStateForPlacement(BlockPlaceContext context) {
		if(context.getClickedFace() == Direction.UP || context.getClickedFace() == Direction.DOWN)
			 return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());  
			else
			
	   return this.defaultBlockState().setValue(FACING, context.getClickedFace());     
}

	@Override
	   public boolean useShapeForLightOcclusion(BlockState p_220074_1_) {
	      return true;
	   }
	@Override
	   public VoxelShape getShape(BlockState p_220053_1_, BlockGetter p_220053_2_, BlockPos pos, CollisionContext p_220053_4_) {
		switch (((Direction)p_220053_1_.getValue(FACING))) {
	    default:
	      return northShape;
	    case EAST:
	      return eastShape;
	    case WEST:
		      return westShape;
	    case NORTH:
		      return northShape;
	    case SOUTH:
		      return southShape;
	  }
	   }
}