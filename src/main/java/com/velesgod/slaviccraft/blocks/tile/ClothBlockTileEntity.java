package com.velesgod.slaviccraft.blocks.tile;

import java.awt.Component;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;

import com.google.common.collect.Sets;
import com.mojang.math.Vector3f;
import com.velesgod.slaviccraft.blocks.herbs.BaseHerb;
import com.velesgod.slaviccraft.core.init.BlockInit;
import com.velesgod.slaviccraft.core.init.EntityInit;
import com.velesgod.slaviccraft.core.init.TileEntitiesInit;
import com.velesgod.slaviccraft.entity.SlavicGhost;

import net.minecraft.client.Minecraft;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.Vec3;

public class ClothBlockTileEntity extends BlockEntity{
	

	public int step = -1;
	public int progress = 0;
	Player pl;
	  public ClothBlockTileEntity(BlockPos pos, BlockState state) {
			super(TileEntitiesInit.CLOTH_TILE.get(), pos, state);
		}
	
	@Override
	public void onLoad() {

	
	}

	
	@Override
	  public void load(CompoundTag compound) {
		    super.load(compound);
			this.step = compound.getInt("step");
			this.progress = compound.getInt("progress");
			setChanged();
	}

		@Override
		public void saveAdditional(CompoundTag compound)
		{
			CompoundTag nbt = new CompoundTag();
			nbt.putInt("step",this.step);
			nbt.putInt("progress",this.progress);
			level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 12);
			level.updateNeighborsAt(getBlockPos(),getBlockState().getBlock());
		}
		
		@Override
		public CompoundTag getUpdateTag()
		{
			CompoundTag nbt = new CompoundTag();
			nbt.putInt("step",this.step);
			nbt.putInt("progress",this.progress);
			return nbt;
		}
		

		  
	    @Override
	    public void handleUpdateTag(CompoundTag tag) {
			super.handleUpdateTag(tag);
			load(tag);
			this.step = tag.getInt("step");
			this.progress = tag.getInt("progress");
	    }
		

	    @Override
	    public Packet<ClientGamePacketListener> getUpdatePacket() {
	    	CompoundTag nbt = getUpdateTag();
			nbt.putInt("step",this.step);
			nbt.putInt("progress",this.progress);
	        return ClientboundBlockEntityDataPacket.create(this);
	    }
		
		
		@Override
		public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt)
		{
			this.load(pkt.getTag());
			this.step = pkt.getTag().getInt("step");
			this.progress = pkt.getTag().getInt("progress");
		}
		
	
	
	
		private final ServerBossEvent bossInfo = 
				new ServerBossEvent(new TextComponent("Обряд"), BossEvent.BossBarColor.GREEN, BossEvent.BossBarOverlay.NOTCHED_6);

	
	public void setPlayer(ServerPlayer player) {
		step = 0;
		bossInfo.addPlayer(player);
		bossInfo.setProgress(0.f);
		pl = player;
	}
		
	public float MAX = 300;
	public void tick() {
		
		
		if(getNearFern() != null) {
			BlockPos p = getNearFern();
		
		}
		
		
		
		if(step == 0) { step = 1;	
		if(getNearFern() != null)
			pl.lookAt(Anchor.EYES, new Vec3(getNearFern().getX(),getNearFern().getY(),getNearFern().getZ()));
		}	else
		if(step == 1 && progress < MAX) {
			progress++;
			bossInfo.setProgress((progress/MAX));
			spawnVex(worldPosition,95);
		} else
		if(progress>=MAX && step == 1) {
			step = -1;
			progress = 0;

				if(getNearFern() != null) {
					BlockPos p = getNearFern();
					level.setBlock(p.above(),BlockInit.BLOOM_FERN.get().defaultBlockState().setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER), 8);
					level.setBlock(p,BlockInit.BLOOM_FERN.get().defaultBlockState().setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER), 8);
					level.setBlock(p.above(),BlockInit.BLOOM_FERN.get().defaultBlockState().setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER), 8);
				}
			bossInfo.removeAllPlayers();
		}
	}
	
	
	
	public BlockPos getNearFern() {
		BlockPos near = null;
		
		for(int x = worldPosition.getX()-5;x<worldPosition.getX()+6;x++)
			for(int z = worldPosition.getZ()-5;z<worldPosition.getZ()+6;z++) {
				BlockPos p = new BlockPos(x,worldPosition.getY(),z);
				if(level.getBlockState(p).getBlock() == Blocks.LARGE_FERN)
				if(near == null)  near = p;
				else {
					double l1 = worldPosition.distSqr(near);
					double l2 = worldPosition.distSqr(p);
					if(l2<l1)  
						near = p;
				}
			}
		
		return near;
				
	}
	
	
	public void spawnVex(BlockPos pos,int chance) {
		Random rand = this.level.getRandom();
		if(rand.nextInt(100) < chance) return;
		
		SlavicGhost mcc = new SlavicGhost(EntityInit.SLAVIC_GHOST.get(), level);
		
		

	
		mcc.moveTo(pos.getX()+r(4),pos.getY()+3,pos.getZ()+r(4));

		mcc.setCustomNameVisible(true);
		mcc.setCustomName(new TextComponent("Призрак"));
		this.level.addFreshEntity(mcc);	
	}
	
	public int r(int m) {
		Random rr = this.level.getRandom();
		return rr.nextInt(m*2)-m;
	}
	   
	  
	   
	
	
	
}