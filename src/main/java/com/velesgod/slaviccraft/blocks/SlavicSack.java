package com.velesgod.slaviccraft.blocks;


import javax.annotation.Nullable;

import com.velesgod.slaviccraft.blocks.tile.DrierTileEntity;
import com.velesgod.slaviccraft.blocks.tile.SlavicSackTileEntity;
import com.velesgod.slaviccraft.contaniers.DrierBlockContainer;
import com.velesgod.slaviccraft.contaniers.SlavicSackContainer;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

public class SlavicSack extends Block  implements EntityBlock{


	
	public SlavicSack() {
		
		super(Properties.of(Material.WOOL).strength(1.0F).sound(SoundType.WOOD));
		// TODO Auto-generated constructor stub
		
	}

	   public BlockEntity newBlockEntity(BlockPos p_154552_, BlockState p_154553_) {
		      return new SlavicSackTileEntity(p_154552_,p_154553_);
		   }

	
			@Override
			public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
					InteractionHand p_60507_, BlockHitResult p_60508_) {
				if(!level.isClientSide()) {
					   if (!level.isClientSide && level.getBlockEntity(pos) instanceof final SlavicSackTileEntity chest) {
				            final MenuProvider container = new SimpleMenuProvider(SlavicSackContainer.getServerContainer(chest, pos),
				            		new TextComponent("Suck"));
				            NetworkHooks.openGui((ServerPlayer) player, container, pos);
				        }
				}
				return InteractionResult.SUCCESS;
			}
			
			

			   public void playerWillDestroy(Level p_56212_, BlockPos p_56213_, BlockState p_56214_, Player p_56215_) {
				      BlockEntity blockentity = p_56212_.getBlockEntity(p_56213_);
				      if (blockentity instanceof SlavicSackTileEntity) {
				    	  SlavicSackTileEntity shulkerboxblockentity = (SlavicSackTileEntity)blockentity;
				         if (!p_56212_.isClientSide) {
				            ItemStack itemstack = new ItemStack(this);
				     
				            blockentity.saveToItem(itemstack);


				            ItemEntity itementity = new ItemEntity(p_56212_, (double)p_56213_.getX() + 0.5D, (double)p_56213_.getY() + 0.5D, (double)p_56213_.getZ() + 0.5D, itemstack);
				            itementity.setDefaultPickUpDelay();
				            if(!(shulkerboxblockentity.isClear() && p_56215_.isCreative()))
				            p_56212_.addFreshEntity(itementity);
				         } 
				      }

				      super.playerWillDestroy(p_56212_, p_56213_, p_56214_, p_56215_);
				   }
}