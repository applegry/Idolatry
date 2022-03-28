package com.velesgod.slaviccraft.blocks;

import java.util.Random;

import com.velesgod.slaviccraft.blocks.herbs.BaseHerb;
import com.velesgod.slaviccraft.blocks.tile.DrierTileEntity;
import com.velesgod.slaviccraft.blocks.tile.HBATileEntity;
import com.velesgod.slaviccraft.core.init.TileEntitiesInit;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class HouseBrownieAltar extends Block implements EntityBlock{
	
	public static BooleanProperty EMPTY = BlockStateProperties.ENABLED;
	
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	
	
	public static VoxelShape shapeMain = Block.box(0.0D, 0.01D, 0.0D, 16.0D, 9.0D, 16.0D);
	

	public HouseBrownieAltar() {
		super(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_GRAY)
				.dynamicShape()
				.strength(3.5f,4.f)
				.sound(SoundType.STONE));
		this.registerDefaultState(this.stateDefinition.any().setValue(EMPTY, Boolean.valueOf(false)));
	}
	

	
	@SuppressWarnings("deprecation")
	@Override
		   public BlockState mirror(BlockState state, Mirror mirr) {
		      return state.rotate(mirr.getRotation(state.getValue(FACING)));
		   }
	@Override
		   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(EMPTY,FACING);
		   }
	
	@Override
	   public BlockState rotate(BlockState state, Rotation rot) {
		      return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
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
		   public BlockState getStateForPlacement(BlockPlaceContext context) {
		   return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());     
	}
	

	
	@Override
	public RenderShape getRenderShape(BlockState p_149645_1_) {return RenderShape.MODEL;}

	
	@Override
	public VoxelShape getCollisionShape(BlockState p_60572_, BlockGetter p_60573_, BlockPos p_60574_,
			CollisionContext p_60575_) {

		return shapeMain;
	}
	


	@SuppressWarnings({ "rawtypes", "resource" })
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
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player,
			InteractionHand hand, BlockHitResult hit) {
		
		
		if(!world.isClientSide()) {
			HBATileEntity te = (HBATileEntity)world.getBlockEntity(pos);
			if(!(te instanceof HBATileEntity)) return InteractionResult.PASS;
			ItemStack item = te.getStack();
			ItemStack itemhand = player.getItemInHand(hand);
			if(itemhand.getItem() == Items.MILK_BUCKET && item.isEmpty())
			{
				world.setBlock(pos, te.getBlockState().setValue(EMPTY, true), 3);
				player.setItemInHand(hand,new ItemStack(Items.BUCKET));
				ServerPlayer s = (ServerPlayer)player;
				s.sendMessage(new TextComponent(te.max+"/"+te.max), ChatType.GAME_INFO, s.getUUID());
			}
			else 
			if(itemhand.getItem() == Items.BUCKET && item.isEmpty() && te.isWork && te.data == 3)
			{
					world.setBlock(pos, te.getBlockState().setValue(EMPTY, false), 3);
					player.setItemInHand(hand,new ItemStack(Items.MILK_BUCKET));
					ServerPlayer s = (ServerPlayer)player;
					s.sendMessage(new TextComponent("0/"+te.max), ChatType.GAME_INFO, s.getUUID());
			} else {
				ServerPlayer s = (ServerPlayer)player;
			s.sendMessage(new TextComponent(String.valueOf(te.getPercent())+"/"+te.max), ChatType.GAME_INFO, s.getUUID());
		
			}
			
			
		
			    world.sendBlockUpdated(pos, state, state, 12);
				    world.updateNeighborsAt(pos, this);
				    te.setChanged();
				    dispatchTEToNearbyPlayers(te);
				
				 //   System.out.print(te);
			} 
		
			return InteractionResult.SUCCESS;
		
		
		
		
	}
	
	 public static void addParticle(Level world,BlockPos pos) {
			Random random =  world.getRandom();
		
	      for(int i = 0; i < 10; ++i) {
		         double d3 = random.nextGaussian() * 0.02D;
		         double d4 = random.nextGaussian() * 0.02D;
		         double d5 = random.nextGaussian() * 0.02D;
		         
		         world.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.DIRT.defaultBlockState()), 
		        		 (double)pos.getX() + (double)0.13125F + (double)0.7375F * (double)random.nextFloat(), 
		        		 (double)pos.getY() + 0.5D + (double)random.nextFloat() * 0.5D, 
		        		 (double)pos.getZ() + (double)0.13125F + (double)0.7375F * (double)random.nextFloat(),
		        		 d3, d4, d5);
		         
		     //    world.addParticle(new BlockParticleOption(ParticleTypes.BLOCK_MARKER, blockstate), (double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, 0.0D, 0.0D, 0.0D);
		         
		         
		      }	
			
	}
	
	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, Random p_49891_) {
		for(int x=pos.getX()-5;x<pos.getX()+6;x++)
			for(int z=pos.getZ()-5;z<pos.getZ()+6;z++)
				if(level.getBlockState(new BlockPos(x,pos.getY()-1,z)).getBlock() == Blocks.FARMLAND
				&& level.getRandom().nextInt() % 100 == 0 
				&& (level.getBlockState(new BlockPos(x,pos.getY(),z)).getBlock() instanceof  BushBlock ||
						level.getBlockState(new BlockPos(x,pos.getY(),z)).getBlock() instanceof  BaseHerb))
					addParticle(level,new BlockPos(x,pos.getY()-1,z));
	}
	 
	 

//	
//	
	@Override
	public void onRemove(BlockState state, Level world, BlockPos pos, BlockState p_196243_4_,
			boolean p_196243_5_) {
		super.onRemove(state, world, pos, p_196243_4_, p_196243_5_);
		if(world.getBlockEntity(pos) instanceof HBATileEntity) world.removeBlockEntity(pos);
	}
//
//	   
//	   @OnlyIn(Dist.CLIENT)
//	   public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {
//
//	   }
//	

	   
		@Override
	    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
	            BlockEntityType<T> beType) {
	        return level.isClientSide ? null
	                : (level0, pos, state0, blockEntity) -> ((HBATileEntity) blockEntity).tick();
	    }



		@Override
		public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
			return TileEntitiesInit.HBA_TILE.get().create(p_153215_, p_153216_);
		}
	   
	
}

























