package com.velesgod.slaviccraft.blocks;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.velesgod.slaviccraft.blocks.tile.ClothBlockTileEntity;
import com.velesgod.slaviccraft.blocks.tile.HBATileEntity;
import com.velesgod.slaviccraft.core.init.TileEntitiesInit;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;


public class ClothBlock extends CarpetBlock implements EntityBlock{

	public ClothBlock() {
		super(Properties.copy(Blocks.WHITE_CARPET));
		
	}
	UUID entid;
	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player,
			InteractionHand hand, BlockHitResult result) {
		MagmaCube mcc = new MagmaCube(EntityType.MAGMA_CUBE,world);
		mcc.moveTo(pos.getX()+0.5f,pos.getY()-0.5f,pos.getZ()+0.5f);
		mcc.setSilent(true);
		mcc.setNoAi(true);
		mcc.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY,12000,12000,false,false));
		mcc.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE,12000,12000,false,false));
		mcc.setInvulnerable(true);
		mcc.setCustomNameVisible(false);
		mcc.setCustomName(new TextComponent("cloth"));
		world.addFreshEntity(mcc);	
		player.startRiding(mcc);
		ClothBlockTileEntity te;
		te = (ClothBlockTileEntity)world.getBlockEntity(pos);
		if(!world.isClientSide) te.setPlayer((ServerPlayer) player);
		
		return super.use(state, world, pos, player, hand, result);
	}
	@Override
	public void animateTick(BlockState p_49888_, Level level, BlockPos pos, Random p_49891_) {
		for(Entity ent: getEnts(level,pos,3)) {
			if(ent.hasCustomName())
		///	System.out.println((ent.getCustomName().getContents() == "cloth" && ent.getPassengers().size() == 0));
			if(ent.hasCustomName())
				if(ent.getCustomName().getContents() == "cloth" && ent.getPassengers().size() == 0) ent.discard();
		}
		super.animateTick(p_49888_, level, pos, p_49891_);
	}
	
    public static List<Entity> getEnts(Level level,BlockPos pos, double radius) {
    	
    	List<Entity> entities = 
    			level.getEntities(null, new AABB(pos.getX() - radius, 
    					pos.getY() - radius, 
    					pos.getZ() - radius, 
    					pos.getX() + radius, 
    					pos.getY() + radius, 
    					pos.getZ() + radius));
        return entities;
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest,
    		FluidState fluid) {
		for(Entity ent: getEnts(level,pos,3)) {
			if(ent.hasCustomName())
		///	System.out.println((ent.getCustomName().getContents() == "cloth" && ent.getPassengers().size() == 0));
			if(ent.hasCustomName())
				if(ent.getCustomName().getContents() == "cloth" && ent.getPassengers().size() == 0) ent.discard();
		}
    	return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }
	@Override
	public void onRemove(BlockState p_60515_, Level level, BlockPos pos, BlockState p_60518_,
			boolean p_60519_) {
		for(Entity ent: getEnts(level,pos,3)) {
			if(ent.hasCustomName())
		///	System.out.println((ent.getCustomName().getContents() == "cloth" && ent.getPassengers().size() == 0));
			if(ent.hasCustomName())
				if(ent.getCustomName().getContents() == "cloth" && ent.getPassengers().size() == 0) ent.discard();
		}
		super.onRemove(p_60515_, level, pos, p_60518_, p_60519_);
	}
    
	@Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
            BlockEntityType<T> beType) {
    //    return level.isClientSide ? null
      //          : 
         return       	(level0, pos, state0, blockEntity) -> ((ClothBlockTileEntity) blockEntity).tick();
    }



	@Override
	public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
		return TileEntitiesInit.CLOTH_TILE.get().create(p_153215_, p_153216_);
	}
   
	
}