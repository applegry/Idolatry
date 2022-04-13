package com.velesgod.slaviccraft.blocks.tile;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.apache.commons.lang3.ArrayUtils;

import com.velesgod.slaviccraft.blocks.herbs.BasePlant;
import com.velesgod.slaviccraft.blocks.herbs.BaseTallPlant;
import com.velesgod.slaviccraft.core.init.BlockInit;
import com.velesgod.slaviccraft.core.init.EntityInit;
import com.velesgod.slaviccraft.core.init.ParticleInit;
import com.velesgod.slaviccraft.core.init.TileEntitiesInit;
import com.velesgod.slaviccraft.entity.SlavicGhost;
import com.velesgod.slaviccraft.entity.SlavicLeshin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.ParticleStatus;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.VibrationParticleOption;
import net.minecraft.nbt.CompoundTag;

import net.minecraft.network.Connection;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;



public class LeshinIdolTileEntity extends BlockEntity{
	
	

	  public LeshinIdolTileEntity(BlockPos pos, BlockState state) {
			super(TileEntitiesInit.LESHINIDOL.get(), pos, state);
			counter = 5*20;
		}

	
	@Override
	  public void load(CompoundTag compound) {
		    super.load(compound);

			this.counter = compound.getInt("counter");

			setChanged();
	}

		@Override
		public void saveAdditional(CompoundTag compound)
		{
			CompoundTag nbt = new CompoundTag();
	
			nbt.putInt("counter",this.counter);
			level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 12);
			level.updateNeighborsAt(getBlockPos(),getBlockState().getBlock());
		}
		
		@Override
		public CompoundTag getUpdateTag()
		{
			CompoundTag nbt = new CompoundTag();


			nbt.putInt("counter",this.counter);
			return nbt;
		}
		

		  
	    @Override
	    public void handleUpdateTag(CompoundTag tag) {
			super.handleUpdateTag(tag);
			load(tag);

			this.counter = tag.getInt("counter");
	    }
		

	    @Override
	    public Packet<ClientGamePacketListener> getUpdatePacket() {
	    	CompoundTag nbt = getUpdateTag();

			nbt.putInt("counter",this.counter);
	        return ClientboundBlockEntityDataPacket.create(this);
	    }
		
		
		@Override
		public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt)
		{
			this.load(pkt.getTag());

			this.counter = pkt.getTag().getInt("counter");

		}
		
		public int counter = 5*20;

		public Player player;
	public void tick() {
		if(this.getBlockState().getValue(BlockStateProperties.LIT) == true) {
			counter--;

			createParticles(level,worldPosition,level.getRandom(),this.getBlockState());
			if(counter<=0) {
				
				
				SlavicLeshin lesh = new SlavicLeshin(EntityInit.SLAVIC_LESHIN.get(),level);
				lesh.tickCount = 0;
				if(player != null) {
				double dX = lesh.getX()-player.getX();//вектор , колинеарный прямой, которая пересекает спрайт и курсор
				double dZ = lesh.getZ()-player.getY();//он же, координата y
				float rotation = (float) (Math.atan2(dZ, dX) * 180 / 3.14159265);//получаем угол в радианах и переводим его в градусы
				
				lesh.setYBodyRot(rotation+90);
				}
				lesh.moveTo(this.worldPosition.getX()+0.5f,this.worldPosition.getY(),this.worldPosition.getZ()+0.5f);
				level.addFreshEntity(lesh);
				level.destroyBlock(this.worldPosition, false);
			}
		}
	}
	
	


	public void createParticles(Level world,BlockPos pos,Random rand,BlockState state) {
		   //System.out.println();
        double d0 = pos.getX()+0.5;
        double d1 = pos.getY();
        double d2 = pos.getZ()+0.5;
      //  Random rand = world.getRandom();
       double angle = 0;
      	float c = counter;
        for(float i = 180; i > 0; i -= 22.5)
        {
     
        	angle = i*2+counter;
        	
        
          
              double x1 = 5*(1.f-(c/100.f))*0.5f*  Math.cos(angle * Math.PI / 180);
              double z1 = 5*(1.f-(c/100.f))*0.5f* Math.sin(angle * Math.PI / 180);
           //   world.addParticle(ParticleInit.AMBER_PARTICLE.get(),d0+x1, d1+rand.nextDouble(), d2+z1, 0, (1.f-(c*1.f/100.f))*0.01, 0);
              world.addParticle(ParticleTypes.DRIPPING_LAVA,d0+x1, d1, d2+z1, 0, (1.f-(c*1.f/100.f))*0.1, 0);
              world.addParticle(ParticleTypes.MYCELIUM,d0+x1, d1, d2+z1, 0,6.5, 0);
        }
       
    }
	
	
	
}