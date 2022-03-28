package com.velesgod.slaviccraft.blocks;

import com.velesgod.slaviccraft.blocks.tile.BaseIdolTileEntity;
import com.velesgod.slaviccraft.blocks.tile.ClothBlockTileEntity;
import com.velesgod.slaviccraft.core.init.BlockInit;
import com.velesgod.slaviccraft.core.init.ItemInit;
import com.velesgod.slaviccraft.core.init.TileEntitiesInit;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;


public class BaseIdol extends Block implements EntityBlock{

public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
public static final BooleanProperty LIT = BlockStateProperties.LIT;
	
	
	
	
	public VoxelShape makeShape(){
		VoxelShape shape = Shapes.empty();
		shape = Shapes.join(shape, Shapes.box(4D/16D, 0,4D/16D, 12D/16D, 1.5D, 12D/16D), BooleanOp.OR);
		return shape;
	}
	public VoxelShape shapeMain = makeShape();
	
	public BaseIdol() {

		super(Properties.of(Material.WOOD).strength(2.0F).sound(SoundType.WOOD));
	
		this.registerDefaultState(this.defaultBlockState().setValue(LIT, Boolean.valueOf(false)));
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
	   return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());     
}
	
	@Override
		public InteractionResult use(BlockState p_60503_, Level p_60504_, BlockPos p_60505_, Player p_60506_,
				InteractionHand p_60507_, BlockHitResult p_60508_) {
		
			if(p_60506_.getItemInHand(p_60507_).getItem() == ItemInit.LIVINGBLOOD_BOTTLE.get()&& p_60503_.getValue(LIT) == false)
			if(p_60503_.getBlock() == BlockInit.GROWTH_IDOL_BLOCK.get()) {
				
				BaseIdolTileEntity te = (BaseIdolTileEntity)p_60504_.getBlockEntity(p_60505_);
				te.init(p_60506_, false);
				p_60504_.setBlock(p_60505_,p_60503_.setValue(LIT, true), 3);
				
				p_60506_.getItemInHand(p_60507_).shrink(1);
				 ItemEntity itementity = p_60506_.drop(new ItemStack(ItemInit.BLOOD_BOTTLE.get(),1), false);
	                if (itementity != null) {
	                   itementity.setNoPickUpDelay();
	                   itementity.setOwner(p_60506_.getUUID());
	                }
				
				
				return InteractionResult.SUCCESS;
			}
			
			
			
			if(p_60506_.getItemInHand(p_60507_).getItem() == ItemInit.DEADBLOOD_BOTTLE.get()&& p_60503_.getValue(LIT) == false)
			if(p_60503_.getBlock() == BlockInit.FEAR_IDOL_BLOCK.get()) {
				
				BaseIdolTileEntity te = (BaseIdolTileEntity)p_60504_.getBlockEntity(p_60505_);
				te.init(p_60506_, true);
				p_60504_.setBlock(p_60505_,p_60503_.setValue(LIT, true), 3);
				
				p_60506_.getItemInHand(p_60507_).shrink(1);
				 ItemEntity itementity = p_60506_.drop(new ItemStack(ItemInit.BLOOD_BOTTLE.get(),1), false);
	                if (itementity != null) {
	                   itementity.setNoPickUpDelay();
	                   itementity.setOwner(p_60506_.getUUID());
	                }
				
				
				return InteractionResult.SUCCESS;
			}
			
			
			return super.use(p_60503_, p_60504_, p_60505_, p_60506_, p_60507_, p_60508_);
		}
	
	@Override
	   public boolean useShapeForLightOcclusion(BlockState p_220074_1_) {
	      return true;
	   }
	
	@Override
	   public VoxelShape getShape(BlockState p_220053_1_, BlockGetter p_220053_2_, BlockPos pos, CollisionContext p_220053_4_) {
	      return shapeMain;
	   }
	
///	@Override
	//public BlockRenderType getRenderShape(BlockState p_149645_1_) {return BlockRenderType.MODEL;}
	
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

	@Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
            BlockEntityType<T> beType) {
 
         return       	(level0, pos, state0, blockEntity) -> ((BaseIdolTileEntity) blockEntity).tick();
    }



	@Override
	public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
		return TileEntitiesInit.IDOL.get().create(p_153215_, p_153216_);
	}

}