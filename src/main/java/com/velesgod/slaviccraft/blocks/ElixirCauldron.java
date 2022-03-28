package com.velesgod.slaviccraft.blocks;


import java.util.Random;

import com.velesgod.slaviccraft.blocks.tile.DrierTileEntity;
import com.velesgod.slaviccraft.blocks.tile.ElixirCauldronTileEntity;
import com.velesgod.slaviccraft.contaniers.DrierBlockContainer;
import com.velesgod.slaviccraft.contaniers.ElixirCauldronContanier;
import com.velesgod.slaviccraft.core.init.TileEntitiesInit;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.api.distmarker.Dist;




public class ElixirCauldron extends Block implements EntityBlock{
	
	
	public static BooleanProperty LEVEL = BlockStateProperties.WATERLOGGED;
	
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	
	public static BooleanProperty LIT = BlockStateProperties.LIT;
	
	public static VoxelShape makeShape(){
		VoxelShape shape = Shapes.empty();
		shape = Shapes.join(shape, Shapes.box(0.125, 0.375, 0.125, 0.875, 0.5, 0.875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.125, 0.5, 0.125, 0.25, 1, 0.875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.75, 0.5, 0.125, 0.875, 1, 0.875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.25, 0.5, 0.75, 0.75, 1, 0.875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.25, 0.5, 0.125, 0.75, 1, 0.25), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0, 0.00625, 0, 1, 0.38125, 1), BooleanOp.OR);

		return shape;
	}
	
		public static VoxelShape shapeMain = makeShape();
	
	
	
		public ElixirCauldron() {
			super(BlockBehaviour.Properties.of(Material.METAL,MaterialColor.COLOR_GRAY).dynamicShape().strength(2f,2.f));
			this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, false).setValue(LIT, Boolean.valueOf(false)));
		}
	
		@Override
	   public boolean useShapeForLightOcclusion(BlockState p_220074_1_) {
	      return true;
	   }
		
		@Override
		public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_,
				CollisionContext p_60558_) {
			 return shapeMain;
		}



	@Override
	public RenderShape getRenderShape(BlockState p_60550_) {
		// TODO Auto-generated method stub
		return RenderShape.MODEL;
	}
	
	@Override
	public VoxelShape getCollisionShape(BlockState p_60572_, BlockGetter p_60573_, BlockPos p_60574_,
			CollisionContext p_60575_) {
		// TODO Auto-generated method stub
		return shapeMain;
	}

		@SuppressWarnings("deprecation")
		@Override
	   public BlockState mirror(BlockState state, Mirror mirr) {
	      return state.rotate(mirr.getRotation(state.getValue(FACING)));
	   }
		@Override
	   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
			builder.add(LEVEL,FACING,LIT);
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
		public InteractionResult use(BlockState p_60503_, Level level, BlockPos pos, Player player,
				InteractionHand hand, BlockHitResult p_60508_) {
			if(!level.isClientSide()) {
				if(!player.isHolding(Items.WATER_BUCKET) && !player.isHolding(Items.BUCKET))
				  { 
				  
					 if (!level.isClientSide && level.getBlockEntity(pos) instanceof final ElixirCauldronTileEntity chest) {
				            final MenuProvider container = new SimpleMenuProvider(ElixirCauldronContanier.getServerContainer(chest, pos),
				            		new TextComponent(""));
				            NetworkHooks.openGui((ServerPlayer) player, container, pos);
				        }
					
					
				  }else { 
				  if(player.isHolding(Items.WATER_BUCKET) && level.getBlockState(pos).getValue(BlockStateProperties.WATERLOGGED) == false)
				  { 
						  player.setItemInHand(hand,new ItemStack(Items.BUCKET));
						  BlockState s = level.getBlockState(pos); 
						  level.setBlock(pos,s.setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(true)),12);
				  return InteractionResult.PASS; 
				  } 
			      else 
			      if(player.isHolding(Items.BUCKET) && level.getBlockState(pos).getValue(BlockStateProperties.WATERLOGGED) == true)
				  {
			    	  	player.setItemInHand(hand,new ItemStack(Items.WATER_BUCKET)); 
			    	  	BlockState s = level.getBlockState(pos); 
			    	  	level.setBlock(pos, s.setValue(BlockStateProperties.WATERLOGGED, false), 12);//getItem() }
				  return InteractionResult.PASS; 
				  } 
			   }	  
			}
			return InteractionResult.SUCCESS;
		}
		
		
		@Override
		public void onBlockExploded(BlockState state, Level world, BlockPos pos, Explosion explosion) {
			// TODO Auto-generated method stub
			super.onBlockExploded(state, world, pos, explosion);
		}
		
		@Override
		public boolean onDestroyedByPlayer(BlockState state, Level world, BlockPos pos, Player player, boolean willHarvest,
				FluidState fluid) {
			// TODO Auto-generated method stub
			return super.onDestroyedByPlayer(state, world, pos, player, willHarvest, fluid);
		}
		
		
		
		
		
		
	//	@Override
	//	public boolean removedByPlayer(BlockState state, Level world, BlockPos pos, Player player,
	//			boolean willHarvest, FluidState fluid) {
	//		TileEntity te = world.getBlockEntity(pos);
	//		 if(te instanceof ElixirCauldronTileEntity) {
	//			 
		//		 for(int i=0;i<6;i++)
		//			 if(!((ElixirCauldronTileEntity)te).inv().getStackInSlot(i).isEmpty())
		//					world.addFreshEntity(new ItemEntity(world, 
		//							pos.getX(), 
		//							pos.getY() , 
		//							pos.getZ(), 
		//							 ((ElixirCauldronTileEntity)te).inv().getStackInSlot(i)));
		//		
		//	 }
	//		 return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
	//	}
		
		
		@Override
		public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
			// TODO Auto-generated method stub
			return state.getValue(LIT) ? 15: 0;
		}
		
		
		@Override
		public int getLightBlock(BlockState state, BlockGetter p_200011_2_, BlockPos p_200011_3_) {
			// TODO Auto-generated method stub
			return state.getValue(LIT) ? 15: 0;
		}

		
		
		@Override
	    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
	            BlockEntityType<T> beType) {
	        return level.isClientSide ? null
	                : (level0, pos, state0, blockEntity) -> ((ElixirCauldronTileEntity) blockEntity).tick();
	    }
		
		
		@Override
		public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
			return TileEntitiesInit.CAULDRON_TE.get().create(p_153215_,p_153216_);
			
		}
		
		
		
		
		
		@SuppressWarnings("resource")
		@Override
		@OnlyIn(Dist.CLIENT)
		   public void animateTick(BlockState p_180655_1_, Level p_180655_2_, BlockPos pos, Random p_180655_4_) {
		      if (p_180655_1_.getValue(LIT)) {
		         double d0 = (double)pos.getX() + 0.5D;
		         double d1 = (double)pos.getY();
		         double d2 = (double)pos.getZ() + 0.5D;
		         if (p_180655_4_.nextDouble() < 0.1D) {
		            p_180655_2_.playLocalSound(d0, d1, d2, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
		         }
		         Player player = Minecraft.getInstance().player;
		         Direction direction = p_180655_1_.getValue(FACING);
		         Direction.Axis direction$axis = direction.getAxis();
	
		         double d4 = p_180655_4_.nextDouble() * 0.6D - 0.3D;
		         double d5 = direction$axis == Direction.Axis.X ? (double)direction.getStepX() * 0.52D : d4;
		         double d6 = p_180655_4_.nextDouble() * 6.0D / 16.0D;
		         double d7 = direction$axis == Direction.Axis.Z ? (double)direction.getStepZ() * 0.52D : d4;
		         p_180655_2_.addParticle(ParticleTypes.SMOKE, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
		         p_180655_2_.addParticle(ParticleTypes.FLAME, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
		         p_180655_2_.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, pos.getX()+0.5,pos.getY()+0.9,pos.getZ()+0.5, 0.0D, 0.01D, 0.0D);
		         p_180655_2_.playSound(player, pos, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.MASTER, 1.4f, 1.f);
		      }
		   }

		
}











