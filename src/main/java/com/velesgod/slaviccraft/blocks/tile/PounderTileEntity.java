package com.velesgod.slaviccraft.blocks.tile;

import com.velesgod.slaviccraft.core.init.BlockInit;
import com.velesgod.slaviccraft.core.init.TileEntitiesInit;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class PounderTileEntity extends BlockEntity{

	

	  public PounderTileEntity(BlockPos pos, BlockState state) {
			super(TileEntitiesInit.POUNDER_TE.get(), pos, state);
		}
	
	  
public int durability = 10;
  
	public void damaged() {
		Level world = this.level;
		BlockPos pos = this.getBlockPos();
		durability--;
		if(durability==0) {
			if(world.getBlockState(pos) == BlockInit.MORTIR.get().defaultBlockState()) {
				world.setBlock(pos,  BlockInit.MORTIR_HARM.get().defaultBlockState(), 3);
				return;
			}
			if(world.getBlockState(pos) == BlockInit.MORTIR_HARM.get().defaultBlockState()) {
				world.setBlock(pos,  BlockInit.MORTIR_DESTROY.get().defaultBlockState(), 3); 
			return;
		}
			if(world.getBlockState(pos) == BlockInit.MORTIR_DESTROY.get().defaultBlockState()) 
				world.destroyBlock(pos, true);
		}
	}
  @Override
  public void load(CompoundTag compound) {
	  super.load(compound);
		this.durability = compound.getInt("int");
		setChanged();
}



	@Override
	public void saveAdditional(CompoundTag compound)
	{
		//saveAdditional(compound);
		CompoundTag nbt = new CompoundTag();//.save();
		nbt.putInt("int",this.durability);
		level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 12);
		level.updateNeighborsAt(getBlockPos(),getBlockState().getBlock());
      //  return nbt;
	}
	
	@Override
	public CompoundTag getUpdateTag()
	{
		CompoundTag nbt = new CompoundTag();
		nbt.putInt("int",this.durability);
		return nbt;
	}
	

	  
    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        load(tag);
        this.durability = tag.getInt("int");
    }
	

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
    	CompoundTag nbt = getUpdateTag();
		nbt.putInt("int",this.durability);
        return ClientboundBlockEntityDataPacket.create(this);
    }
	
	
	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt)
	{
		this.load(pkt.getTag());
		this.durability = pkt.getTag().getInt("int");
	}
	
	
	
	
	
}