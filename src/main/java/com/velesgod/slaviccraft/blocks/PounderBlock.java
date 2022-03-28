package com.velesgod.slaviccraft.blocks;

import java.util.Map;
import java.util.Random;

import com.google.common.collect.Maps;
import com.velesgod.slaviccraft.blocks.tile.PounderTileEntity;
import com.velesgod.slaviccraft.core.init.ItemInit;
import com.velesgod.slaviccraft.core.init.TileEntitiesInit;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PounderBlock extends Block implements EntityBlock{
	
	public static VoxelShape shapeMain = Block.box(4D, 0, 4D,12D, 8D, 12D);
	

	   public static Map<Item, Item> getRecipe() {
		      Map<Item, Item> map = Maps.newLinkedHashMap();
		      //Elixir
		      map.put(  ItemInit.FIREWHIP_DRIED.get(), ItemInit.FIREWHIP_POWDER.get());
		      map.put(  ItemInit.SWIFTFOOT_DRIED.get(), ItemInit.SWIFTFOOT_POWDER.get());
		      map.put(  ItemInit.SWORDBLADE_DRIED.get(), ItemInit.SWORDBLADE_POWDER.get());
		      map.put(  ItemInit.WILDROSEMARY_DRIED.get(), ItemInit.WILDROSEMARY_POWDER.get());
		      map.put(  ItemInit.CHRYSANTHS_DRIED.get(), ItemInit.CHRYSANTHS_POWDER.get());
		      map.put(  ItemInit.IMMORTELLE_DRIED.get(), ItemInit.IMMORTELLE_POWDER.get());
		      //Balm
		      map.put(  ItemInit.HELLEBORE_DRIED.get(), ItemInit.HELLEBORE_POWDER.get());
		      map.put(  ItemInit.HENBANE_DRIED.get(), ItemInit.HENBANE_POWDER.get());
		      map.put(  ItemInit.HEMLOCK_DRIED.get(), ItemInit.HEMLOCK_POWDER.get());
		      map.put(  ItemInit.RAVENEYE_DRIED.get(), ItemInit.RAVENEYE_POWDER.get());
		      map.put( ItemInit.THORNAPPLES_DRIED.get(), ItemInit.THORNAPPLES_POWDER.get());
		      map.put(  ItemInit.SLEEPGRASS_DRIED.get(), ItemInit.SLEEPGRASS_POWDER.get());
		      
		      //Vanila
		  //    map.put(  ItemInit.LINEN_SEEDS.get(), ItemInit.LINEN_GROUND.get());
		      map.put(  ItemInit.AMBER.get(), ItemInit.AMBER_DUST.get());
		      map.put(  Items.BONE, Items.BONE_MEAL);
		      map.put(  Items.SANDSTONE, Items.SAND);
		      
		      return map;
		   }
	
	
	
	
	   
	   
	public PounderBlock() {
		super(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(3.5f,4.f));

		
	}
	

	@Override
	public boolean useShapeForLightOcclusion(BlockState p_60576_) {
		// TODO Auto-generated method stub
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

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos,CollisionContext context) {return shapeMain;}

	
	
	
	
	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player,
			InteractionHand hand, BlockHitResult p_60508_) {
		
		
	  	
	  	ItemStack stack = player.getItemInHand(hand);
	  	Item item = stack.getItem();
	  	
	  	
		if(getRecipe().containsKey(item)) {
		
			stack.shrink(1);
			player.setItemInHand(hand, stack.copy());
			world.addFreshEntity(new ItemEntity(world, 
					pos.getX()+0.5, 
					pos.getY()+shapeMain.bounds().getYsize(), 
					pos.getZ()+0.5, 
					new ItemStack(getRecipe().get(item),1)));
			world.playSound(player, pos,SoundEvents.BAMBOO_FALL, SoundSource.MASTER, 5.f, 1.f);
			
		     Random random = world.getRandom();

		      for(int i = 0; i < 10; ++i) {
		         double d3 = random.nextGaussian() * 0.02D;
		         double d4 = random.nextGaussian() * 0.02D;
		         double d5 = random.nextGaussian() * 0.02D;
		         world.addParticle(ParticleTypes.COMPOSTER, (double)pos.getX() + (double)0.13125F + (double)0.7375F * (double)random.nextFloat(), (double)pos.getY() + 0.5D + (double)random.nextFloat() * 0.5D, (double)pos.getZ() + (double)0.13125F + (double)0.7375F * (double)random.nextFloat(), d3, d4, d5);
		      }	
		      if(!world.isClientSide()) {
		      PounderTileEntity te =  (PounderTileEntity) world.getBlockEntity(pos);
		  	te.damaged();
		    world.sendBlockUpdated(pos, state, state, 12);
		    world.updateNeighborsAt(pos, this);
		    te.setChanged();
		    dispatchTEToNearbyPlayers(te);
		      }
		      return InteractionResult.SUCCESS;
		}
		
		return InteractionResult.SUCCESS;
		
		
	
		
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
		return TileEntitiesInit.POUNDER_TE.get().create(pos,state);
	}
	
}