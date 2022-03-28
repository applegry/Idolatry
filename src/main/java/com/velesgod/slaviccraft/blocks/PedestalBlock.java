package com.velesgod.slaviccraft.blocks;


import java.util.Random;

import com.velesgod.slaviccraft.blocks.tile.DrierTileEntity;
import com.velesgod.slaviccraft.blocks.tile.PedestalTileEntity;
import com.velesgod.slaviccraft.core.init.ParticleInit;
import com.velesgod.slaviccraft.core.init.TileEntitiesInit;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PedestalBlock extends Block implements EntityBlock{

	
	public VoxelShape makeShape(){
		VoxelShape shape = Shapes.empty();
		shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.25, 1), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0, 0.75, 0, 1, 1, 1), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.125, 0.25, 0.125, 0.875, 0.75, 0.875), BooleanOp.OR);

		return shape;
	}
	
	
	public VoxelShape shapeMain = makeShape();

	
	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player,
			InteractionHand hand, BlockHitResult res) {
		
		
		PedestalTileEntity te = (PedestalTileEntity)world.getBlockEntity(pos);
		ItemStack playerhand = player.getItemInHand(hand).copy();
		ItemStack pedestal  = 	te.inv().getStackInSlot(0);
		pedestal.setTag(pedestal.getOrCreateTag());
		//if(!player.level.isClientSide())	{
		if(te.inv().getStackInSlot(0) == ItemStack.EMPTY) {
			if(playerhand == ItemStack.EMPTY)
				te.inv().setStackInSlot(0,  ItemStack.EMPTY);
			else {
				ItemStack stack = playerhand.copy();
				stack.setCount(1);
			te.inv().setStackInSlot(0, stack.copy());

			playerhand.shrink(1);
			if(!player.level.isClientSide()) player.setItemInHand(hand,playerhand);
			}
		}else {
				
			 ItemEntity itementity = player.drop(pedestal, false);
             if (itementity != null) {
                itementity.setNoPickUpDelay();
                itementity.setOwner(player.getUUID());
             }
    	te.inv().setStackInSlot(0,ItemStack.EMPTY);
			
		}
		player.inventoryMenu.broadcastChanges();
		//}
		System.out.println(player.getItemInHand(hand));
		
		return InteractionResult.SUCCESS;
		
		
		
		
		
		
		
		
		
	}
	

	
	public PedestalBlock() {
		super(
				Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F)

		);

	}
	
	@Override
	   public boolean useShapeForLightOcclusion(BlockState p_220074_1_) {
	      return true;
	   }
	
	@Override
	   public VoxelShape getShape(BlockState p_220053_1_, BlockGetter p_220053_2_, BlockPos pos, CollisionContext p_220053_4_) {
	      return shapeMain;
	   }
	
	
	@Override
	public RenderShape getRenderShape(BlockState p_60550_) {
		// TODO Auto-generated method stub
		return RenderShape.MODEL;
	}
	

	
	public static void dispatchTEToNearbyPlayers(BlockEntity tile) {
		if (tile.getLevel() instanceof ServerLevel) {
			Packet packet = tile.getUpdatePacket();
			if (packet != null) {
				BlockPos pos = tile.getBlockPos();
				((ServerChunkCache) tile.getLevel().getChunkSource()).chunkMap
						.getPlayers(new ChunkPos(pos), false)
						.forEach(e -> e.connection.send(packet));
			}
		}
	}
	
	


	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		// TODO Auto-generated method stub
		return TileEntitiesInit.PEDESTAL_TE.get().create(pos,state);
	}

	
	@Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
            BlockEntityType<T> beType) {
     //   return level.isClientSide ? null
      //          : 
                	return (level0, pos, state0, blockEntity) -> ((PedestalTileEntity) blockEntity).tick();
    }

	


	

}