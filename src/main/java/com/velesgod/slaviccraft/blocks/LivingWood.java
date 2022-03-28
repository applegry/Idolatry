package com.velesgod.slaviccraft.blocks;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;

public class LivingWood extends Block{

	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	
	
	
	public LivingWood() {
		
		super(Properties.of(Material.WOOD).strength(2.0F).sound(SoundType.WOOD));
		// TODO Auto-generated constructor stub
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
	   return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());     
}

}