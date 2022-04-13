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

import net.minecraft.client.ParticleStatus;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.VibrationParticleOption;
import net.minecraft.nbt.CompoundTag;

import net.minecraft.network.Connection;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.level.gameevent.PositionSourceType;
import net.minecraft.world.level.gameevent.vibrations.VibrationPath;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.registries.RegistryObject;


public class BaseIdolTileEntity extends BlockEntity{
	
	@SuppressWarnings("rawtypes")
	public static BlockState[] herbs = {
			BlockInit.THORNAPPLES.get().defaultBlockState(),
			BlockInit.HELLEBORE.get().defaultBlockState(), 
			BlockInit.SLEEPGRASS.get().defaultBlockState(), 
			BlockInit.PARIS.get().defaultBlockState(),
			BlockInit.HEMLOCK.get().defaultBlockState(), 
			BlockInit.HENBANE.get() .defaultBlockState(),
			BlockInit.WILDROSEMARY.get().defaultBlockState(),
			BlockInit.FIREWHIP.get().defaultBlockState(), 
			BlockInit.CHRYSANTHS.get().defaultBlockState(),
			BlockInit.IMMORETLE.get().defaultBlockState(), 
			BlockInit.SWORDBLADE.get().defaultBlockState(),
			BlockInit.SWIFTFOOT.get().defaultBlockState(),	
			Blocks.TALL_GRASS.defaultBlockState(),
			Blocks.GRASS.defaultBlockState(),
			Blocks.LARGE_FERN.defaultBlockState(),
			Blocks.DANDELION.defaultBlockState(),
			Blocks.OXEYE_DAISY.defaultBlockState(),
			Blocks.CORNFLOWER.defaultBlockState(),
			Blocks.POPPY.defaultBlockState()
	};
	
	@SuppressWarnings("rawtypes")
	public static BlockState[] sub_herbs = {
			BlockInit.PARIS.get().defaultBlockState(), 
			BlockInit.HENBANE.get() .defaultBlockState(),
			BlockInit.WILDROSEMARY.get().defaultBlockState(),
			Blocks.GRASS.defaultBlockState(),
			Blocks.DANDELION.defaultBlockState(),
			Blocks.OXEYE_DAISY.defaultBlockState(),
			Blocks.CORNFLOWER.defaultBlockState(),
			Blocks.POPPY.defaultBlockState(),
			BlockInit.CHRYSANTHS.get().defaultBlockState(),
			BlockInit.IMMORETLE.get().defaultBlockState(), 

	};
	
	@SuppressWarnings("rawtypes")
	public static BlockState[] simps = {

			Blocks.TALL_GRASS.defaultBlockState(),
			Blocks.GRASS.defaultBlockState(),
			Blocks.LARGE_FERN.defaultBlockState(),
			Blocks.DANDELION.defaultBlockState(),
			Blocks.OXEYE_DAISY.defaultBlockState(),
			Blocks.CORNFLOWER.defaultBlockState(),
			Blocks.POPPY.defaultBlockState()


	};
	
	
	
	
	

	
	public boolean fearIdol = false;
	public boolean active = false;
	public int counter = 0;

	public Player player;
	  public BaseIdolTileEntity(BlockPos pos, BlockState state) {
			super(TileEntitiesInit.IDOL.get(), pos, state);
		}
	
	@Override
	public void onLoad() {

	
	}

	
	@Override
	  public void load(CompoundTag compound) {
		    super.load(compound);
			this.fearIdol = compound.getBoolean("fear");
			this.active = compound.getBoolean("active");
			this.counter = compound.getInt("counter");

			setChanged();
	}

		@Override
		public void saveAdditional(CompoundTag compound)
		{
			CompoundTag nbt = new CompoundTag();
			nbt.putBoolean("fear",this.fearIdol);
			nbt.putBoolean("active",this.active);
			nbt.putInt("counter",this.counter);
			level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 12);
			level.updateNeighborsAt(getBlockPos(),getBlockState().getBlock());
		}
		
		@Override
		public CompoundTag getUpdateTag()
		{
			CompoundTag nbt = new CompoundTag();

			nbt.putBoolean("fear",this.fearIdol);
			nbt.putBoolean("active",this.active);
			nbt.putInt("counter",this.counter);
			return nbt;
		}
		

		  
	    @Override
	    public void handleUpdateTag(CompoundTag tag) {
			super.handleUpdateTag(tag);
			load(tag);
			this.fearIdol = tag.getBoolean("fear");
			this.active = tag.getBoolean("active");
			this.counter = tag.getInt("counter");
	    }
		

	    @Override
	    public Packet<ClientGamePacketListener> getUpdatePacket() {
	    	CompoundTag nbt = getUpdateTag();
			nbt.putBoolean("fear",this.fearIdol);
			nbt.putBoolean("active",this.active);
			nbt.putInt("counter",this.counter);
	        return ClientboundBlockEntityDataPacket.create(this);
	    }
		
		
		@Override
		public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt)
		{
			this.load(pkt.getTag());
			this.fearIdol = pkt.getTag().getBoolean("fear");
			this.active = pkt.getTag().getBoolean("active");
			this.counter = pkt.getTag().getInt("counter");

		}
		
	
		Chicken chika; 
		public void init(Player pla,boolean isFear) {
			fearIdol = isFear;
			player = pla;
			active = true;
			if(fearIdol) {
				chika = new Chicken(EntityType.CHICKEN, level);
	  if(level.getBlockState(worldPosition).getValue(HorizontalDirectionalBlock.FACING) == Direction.EAST) 
		  chika.moveTo(worldPosition.east(64).getX(),worldPosition.east(64).getY(),worldPosition.east(64).getZ());
	  if(level.getBlockState(worldPosition).getValue(HorizontalDirectionalBlock.FACING) == Direction.WEST) 
		  chika.moveTo(worldPosition.west(64).getX(),worldPosition.west(64).getY(),worldPosition.west(64).getZ());
	  if(level.getBlockState(worldPosition).getValue(HorizontalDirectionalBlock.FACING) == Direction.NORTH) 
		  chika.moveTo(worldPosition.north(64).getX(),worldPosition.north(64).getY(),worldPosition.north(64).getZ());
	  if(level.getBlockState(worldPosition).getValue(HorizontalDirectionalBlock.FACING) == Direction.SOUTH)
		  chika.moveTo(worldPosition.south(64).getX(),worldPosition.south(64).getY(),worldPosition.south(64).getZ());
	  chika.setNoAi(true);
	  chika.setNoGravity(true);
	 chika.setInvisible(true);
	 chika.setInvulnerable(true);
	 
	 chika.moveTo(chika.getX(),chika.getY()-1,chika.getZ());
				level.addFreshEntity(chika);
			}
			
		}

		public int MAX = 20*60;
	public void tick() {
	if(fearIdol) {
		if(active) {
		counter++;
		createSphere(level,worldPosition,1,10);
		for(Entity ent : getEnts(worldPosition,20)) {
			if(ent instanceof Monster) {
				((Monster) ent).setTarget(chika);
				((Monster) ent).getNavigation().setSpeedModifier(1.4f);
				((Monster) ent).addEffect(new MobEffectInstance(MobEffects.BLINDNESS,100,100,false,false));
				player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY,100,100,false,false));
			}
		}
		
		
		if(counter>=MAX) { active=false; counter*=0; chika.kill();
		level.setBlock(worldPosition,level.getBlockState(worldPosition).setValue(BlockStateProperties.LIT, false), 8);
		}
	}
	}else {
		if(active) {
			counter++;
			if(r() % 5 == 0) 
				tryToGrow();
			if(counter>=MAX) { active=false; counter*=0; 
			level.setBlock(worldPosition,level.getBlockState(worldPosition).setValue(BlockStateProperties.LIT, false), 8);
			}
		}
	}
	}
	
	
    public List<Entity> getEnts(BlockPos pos, double radius) {
    	
    	List<Entity> entities = 
    			level.getEntities(null, new AABB(
    					pos.getX() - radius, 
    					pos.getY() - radius, 
    					pos.getZ() - radius, 
    					pos.getX() + radius, 
    					pos.getY() + radius, 
    					pos.getZ() + radius));
        return entities;
    }
	
	public void placeHerb(BlockPos p,BlockState state) {
		if(ArrayUtils.contains(sub_herbs, state)) 
			level.setBlock(p,state, 8);
		else {
		level.setBlock(p.above(),state.setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER), 8);
		level.setBlock(p,state.setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER), 8);
		level.setBlock(p.above(),state.setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER), 8);
		}
	}
	
	
	
	public void tryToGrow() {
		int x = level.getRandom().nextInt(10*2)-10;
		int y = level.getRandom().nextInt(10*2)-10;
		int z = level.getRandom().nextInt(10*2)-10;
		BlockPos p = new BlockPos(worldPosition.getX()+x,
								  worldPosition.getY()+y,
								  worldPosition.getZ()+z);
		if(r() > 250) {
			if(level.getBlockState(p).getBlock() == Blocks.GRASS_BLOCK) 
				if(level.getBlockState(p.above()).getBlock() == Blocks.AIR) 
				placeHerb(p.above(),herbs[level.getRandom().nextInt(herbs.length-1)]);
		}else {
			if(level.getBlockState(p).getBlock() == Blocks.GRASS_BLOCK) 
				if(level.getBlockState(p.above()).getBlock() == Blocks.AIR) 
				placeHerb(p.above(),simps[level.getRandom().nextInt(simps.length-1)]);
		}

		
	
	}
	
	

	
	public int r() {
		Random rr = this.level.getRandom();
		return rr.nextInt(300);
	}
	
	 private void createSphere(Level world,BlockPos ent,double speed, int size)
	    {
		   //System.out.println();
	        double d0 = ent.getX()+0.5;
	        double d1 = ent.getY();
	        double d2 = ent.getZ()+0.5;
	      //  Random rand = world.getRandom();
           double angle = 0;
            for(float i = 0; i < 180; i += 11.25)
            {
                  angle = i*2+counter;
           
              
                  double x1 = size*(1.f-(counter/(MAX*1.f)))*  Math.cos(angle * Math.PI / 180);
                  double z1 = size*(1.f-(counter/(MAX*1.f)))* Math.sin(angle * Math.PI / 180);
                  world.addParticle(ParticleTypes.SCRAPE,d0+x1, d1+1, d2+z1, 0, -5f, 0);
                  world.addParticle(ParticleTypes.SCRAPE,d0+x1, d1+1, d2+z1, 0, -5f, 0);
            }
	       
	    }
	   
	  
	   
	
	
	
}