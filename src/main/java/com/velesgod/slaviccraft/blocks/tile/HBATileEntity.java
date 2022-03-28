package com.velesgod.slaviccraft.blocks.tile;

import java.util.Random;

import com.velesgod.slaviccraft.blocks.herbs.BaseHerb;
import com.velesgod.slaviccraft.core.init.TileEntitiesInit;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class HBATileEntity extends BlockEntity{

	
	
	

	  public HBATileEntity(BlockPos pos, BlockState state) {
			super(TileEntitiesInit.HBA_TILE.get(), pos, state);
		}
	
	

  public void setStack(ItemStack i) {
	  data++;
	  stack = i;
	  this.setChanged();
  }
  
  public ItemStack getStack() {
	  return stack;
  }

  public int max = 3;
  public int data = 0;
  
  public boolean isWork = false;
  
  public ItemStack stack = ItemStack.EMPTY;
  
	

	public void setWork(boolean bool) {
		isWork = bool;
		setChanged();
	}
	

		
	public int getPercent() {
		return  data;
	}
	
	
	@Override
	public void onLoad() {

		if(getBlockState().getValue(BlockStateProperties.ENABLED)) 
			{
			isWork = true;
			data=max;
			}
	}

	
	@Override
	  public void load(CompoundTag compound) {
		  super.load(compound);

			this.data = compound.getInt("int");
			ItemStack stc = ItemStack.of(compound.getCompound("stack"));
			stack = stc;
			setChanged();
	}



		@Override
		public void saveAdditional(CompoundTag compound)
		{
			
			
			CompoundTag nbt = new CompoundTag();
			nbt.putInt("int",this.data);
			nbt.put("stack", (Tag) stack.serializeNBT());
			
			level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 12);
			level.updateNeighborsAt(getBlockPos(),getBlockState().getBlock());
	       


		}
		
		@Override
		public CompoundTag getUpdateTag()
		{

			CompoundTag nbt = new CompoundTag();
			nbt.putInt("int",this.data);
			nbt.putBoolean("iswork",this.isWork);
			nbt.put("stack", (Tag) stack.serializeNBT());

			return nbt;
		}
		

		  
	    @Override
	    public void handleUpdateTag(CompoundTag tag) {

	        
			super.handleUpdateTag(tag);
			load(tag);
			this.data = tag.getInt("int");
			ItemStack stc = ItemStack.of(tag.getCompound("stack"));
			stack = stc;
	        
	        
	        
	    }
		

	    @Override
	    public Packet<ClientGamePacketListener> getUpdatePacket() {
	    	CompoundTag nbt = getUpdateTag();
			nbt.putInt("int",this.data);
			nbt.put("stack", (Tag) stack.serializeNBT());
			
			
	        return ClientboundBlockEntityDataPacket.create(this);
	    }
		
		
		@Override
		public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt)
		{
			this.load(pkt.getTag());
			this.data = pkt.getTag().getInt("int");
			ItemStack stc = ItemStack.of(pkt.getTag().getCompound("stack")); stack = stc;
		}
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	@Override
	public void tick() {
	//	System.out.print(isWork+" , "+data+"\n");
		if(isWork) { 
		
			Player player = Minecraft.getInstance().player;
		if(!level.isClientSide){
		
			if(player == null) return;
				
		if(player.isSleepingLongEnough()) {
			//System.out.print("\n\nworked\n\n");
			data-=1;
			BlockPos pos = getBlockPos();
	
			BlockPos temp;
		
			BlockState state;
			for(int x=pos.getX()-5;x<pos.getX()+6;x++)
				for(int z=pos.getZ()-5;z<pos.getZ()+6;z++) {
					temp = new BlockPos(x, pos.getY(), z);
					state = level.getBlockState(temp);
					if(state.getBlock() instanceof BushBlock 
							|| state.getBlock() instanceof BaseHerb 
							|| level.getBlockState(temp.below()).getBlock() == Blocks.FARMLAND)
						applyBonemeal(level,temp,player);
				}
		
			}
		} 


		
		if(data<=0)  { isWork=false; level.setBlock(getBlockPos(), getBlockState().setValue(BlockStateProperties.ENABLED, false), 3);}
		}
		//if(level.isClientSide()) {
//			System.out.println(level.isClientSide());
//			BlockPos pos = getBlockPos();
//			for(int x=pos.getX()-5;x<pos.getX()+6;x++)
//				for(int z=pos.getZ()-5;z<pos.getZ()+6;z++)
//					if(level.getBlockState(new BlockPos(x,pos.getY()-1,z)).getBlock() == Blocks.FARMLAND
//					&& level.getRandom().nextInt() % 100 == 0 
//					&& (level.getBlockState(new BlockPos(x,pos.getY(),z)).getBlock() instanceof  BushBlock ||
//							level.getBlockState(new BlockPos(x,pos.getY(),z)).getBlock() instanceof  BaseHerb))
//						addParticle(level,new BlockPos(x,pos.getY()-1,z));
			
	//	}
		
	}
	
	
   public static void addParticle(Level world,BlockPos pos) {
		Random random =  world.getRandom();
	
      for(int i = 0; i < 10; ++i) {
	         double d3 = random.nextGaussian() * 0.02D;
	         double d4 = random.nextGaussian() * 0.02D;
	         double d5 = random.nextGaussian() * 0.02D;
	         
	         world.addParticle(ParticleTypes.CLOUD, 
	        		 (double)pos.getX() + (double)0.13125F + (double)0.7375F * (double)random.nextFloat(), 
	        		 (double)pos.getY() + 0.5D + (double)random.nextFloat() * 0.5D, 
	        		 (double)pos.getZ() + (double)0.13125F + (double)0.7375F * (double)random.nextFloat(),
	        		 d3, d4, d5);
	         
	     //    world.addParticle(new BlockParticleOption(ParticleTypes.BLOCK_MARKER, blockstate), (double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, 0.0D, 0.0D, 0.0D);
	         
	         
	      }	
		
}

	
	
	
	
	
	   public static boolean applyBonemeal(Level p_195966_1_, BlockPos p_195966_2_, Player player) {
		      BlockState blockstate = p_195966_1_.getBlockState(p_195966_2_);
		      int hook = net.minecraftforge.event.ForgeEventFactory.onApplyBonemeal(player, p_195966_1_, p_195966_2_, blockstate, new ItemStack(Items.BONE_MEAL));
		      if (hook != 0) return hook > 0;
		      if (blockstate.getBlock() instanceof BonemealableBlock) {
		    	  BonemealableBlock igrowable = (BonemealableBlock)blockstate.getBlock();
		         if (igrowable.isValidBonemealTarget(p_195966_1_, p_195966_2_, blockstate, p_195966_1_.isClientSide)) {
		            if (p_195966_1_ instanceof ServerLevel) {
		               if (igrowable.isBonemealSuccess(p_195966_1_, p_195966_1_.random, p_195966_2_, blockstate)) {
		                  igrowable.performBonemeal((ServerLevel)p_195966_1_, p_195966_1_.random, p_195966_2_, blockstate);
		               }

		              
		            }

		            return true;
		         }
		      }

		      return false;
		   }
	
	
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	
	
	
	
	
}