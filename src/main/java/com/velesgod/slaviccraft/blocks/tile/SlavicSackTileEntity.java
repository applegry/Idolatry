package com.velesgod.slaviccraft.blocks.tile;

import javax.annotation.Nullable;

import com.velesgod.slaviccraft.api.IdolUtils;
import com.velesgod.slaviccraft.blocks.herbs.BasePlant;
import com.velesgod.slaviccraft.blocks.herbs.BaseTallPlant;
import com.velesgod.slaviccraft.core.init.ItemInit;
import com.velesgod.slaviccraft.core.init.TileEntitiesInit;
import com.velesgod.slaviccraft.items.BaseBundle;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemStackHandler;

public class SlavicSackTileEntity extends InventoryBlockEntity{


	
	  public SlavicSackTileEntity(BlockPos pos, BlockState state) {
	        super(TileEntitiesInit.SACK_TE.get(), pos, state, 9);
	    }

	
	public ItemStack getStack(int i) {
		return this.inventory.getStackInSlot(i);
	}

	
	public ItemStackHandler inv() {
		return inventory;
	}
	

	

	@Override
	public ItemStackHandler createInventory() {
		return new ItemStackHandler(9) {
			@Override
			protected void onContentsChanged(int slot) {
				setChanged();
			}
			@Override
			public boolean isItemValid(int slot, ItemStack stack) {

				return true;
			}
			@Override
			public int getSlotLimit(int slot) {
				return 64;
			}
			@Override
			public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
				if(!isItemValid(slot, stack))
				return stack;
				return super.insertItem(slot, stack, simulate);
			}
		};
	}
	

	public void clear() {
		for(int i=0;i<9;i++)
		this.inventory.setStackInSlot(i, ItemStack.EMPTY);
	}


	 


	
}