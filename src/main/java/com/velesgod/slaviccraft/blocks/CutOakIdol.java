package com.velesgod.slaviccraft.blocks;

import java.util.Random;

import com.velesgod.slaviccraft.core.init.ParticleInit;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RedstoneLampBlock;
import net.minecraft.world.level.block.RedstoneTorchBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CutOakIdol extends Block{

	
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	
	public static final BooleanProperty LIT = BlockStateProperties.LIT;

	
	public VoxelShape makeShape(){
		VoxelShape shape = Shapes.empty();
		shape = Shapes.join(shape, Shapes.box(2D/16D, 0,2D/16D, 14D/16D, 1, 14D/16D), BooleanOp.OR);
		return shape;
	}
	public VoxelShape shapeMain = makeShape();
	
	public CutOakIdol() {
		
		super(Properties.of(Material.WOOD).strength(2.0F).sound(SoundType.WOOD));
		this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(LIT,false)));
	}
	
	
	@SuppressWarnings("deprecation")
	@Override
		   public BlockState mirror(BlockState state, Mirror mirr) {
		      return state.rotate(mirr.getRotation(state.getValue(FACING)));
		   }
	@Override
		   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING,LIT);
		   }
	
	@Override
	   public BlockState rotate(BlockState state, Rotation rot) {
		      return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
		   }
	@Override
	   public BlockState getStateForPlacement(BlockPlaceContext context) {
	   return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(LIT, false);     
}
	
	
	
	
	@Override
	   public boolean useShapeForLightOcclusion(BlockState p_220074_1_) {
	      return true;
	   }
	
	@Override
	   public VoxelShape getShape(BlockState p_220053_1_, BlockGetter p_220053_2_, BlockPos pos, CollisionContext p_220053_4_) {
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
	
	
	double random(Random ran) {
		return (ran.nextDouble()-0.5D)*0.5D;
	}
	
	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, Random ran) {
		if(state.getValue(LIT)) {
			level.addParticle(ParticleInit.IDOL_PARTICLE.get(),pos.getX()+0.5f+random(ran),pos.getY()+1.8f, pos.getZ()+0.5f+random(ran), 0, 0, 0);
		}
		super.animateTick(state, level, pos, ran);
	}
	
	
	
	

}