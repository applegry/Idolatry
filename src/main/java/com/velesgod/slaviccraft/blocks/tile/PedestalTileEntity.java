package com.velesgod.slaviccraft.blocks.tile;

import com.velesgod.slaviccraft.api.IdolUtils;
import com.velesgod.slaviccraft.blocks.herbs.BasePlant;
import com.velesgod.slaviccraft.blocks.herbs.BaseTallPlant;
import com.velesgod.slaviccraft.core.init.ItemInit;
import com.velesgod.slaviccraft.core.init.TileEntitiesInit;
import com.velesgod.slaviccraft.items.BaseBundle;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;

public class PedestalTileEntity extends InventoryBlockEntity{


	
	  public PedestalTileEntity(BlockPos pos, BlockState state) {
	        super(TileEntitiesInit.PEDESTAL_TE.get(), pos, state, 1);
	    }

	

	

	public float angle = 0f;
	
	public ItemStack getStack(int i) {
		return this.inventory.getStackInSlot(i);
	}

	
	public ItemStackHandler inv() {
		return inventory;
	}
	

	

	@Override
	public ItemStackHandler createInventory() {
		return new ItemStackHandler(1) {
			@Override
			protected void onContentsChanged(int slot) {
				setChanged();
			}
			@Override
			public boolean isItemValid(int slot, ItemStack stack) {
				Item item = stack.getItem();
		
				return (item instanceof BaseBundle)||
						(item == Items.ROTTEN_FLESH)||
						(Block.byItem(item) instanceof BasePlant)||
						(Block.byItem(item) instanceof BaseTallPlant);
			}
			@Override
			public int getSlotLimit(int slot) {
				return 1;
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
		this.inventory.setStackInSlot(0, ItemStack.EMPTY);
	}
	

	int IDOL = 0;
	int HERB = 0;
	@Override
	public void tick() {

		
		if(IdolUtils.isIdolOn(level, worldPosition)) {
			if(IDOL>0) IDOL--; else {
			if(IdolUtils.isRitualReady(level, worldPosition)) {
				if(HERB==0) HERB=100;
			Item i = IdolUtils.checkRitual(level, worldPosition);
			if(i != Items.AIR) { 
				IdolUtils.flameOn(level,worldPosition);
				if(HERB>1) HERB--; else {
				this.inventory.setStackInSlot(0, new ItemStack(i)); IdolUtils.clearCircle(level, worldPosition); 
				HERB=0;
				}
				}
			}
			}
		}else {
			if(inv().getStackInSlot(0).getItem() == ItemInit.LIVINGSTONE.get()) {
				if(level.isClientSide()) IdolUtils.createLilBoom(this.getLevel(),worldPosition);
				if(IdolUtils.checkIdolSystem(level,worldPosition)) IdolUtils.activateIdols(level,worldPosition);
				inventory.setStackInSlot(0, ItemStack.EMPTY);
				IDOL = 100;
			}
			
		}
		
	//	System.out.println(IDOL);
		
		
				
			
		//	System.out.println(IdolUtils.isRitualReady(level, worldPosition));

		//	IdolUtils.createSphere(this.getLevel(),new BlockPos(this.worldPosition.getX()+0.5D,
		//			this.worldPosition.getY()+1.5f,
		//			this.worldPosition.getZ()+0.5D
		//			),5,5);
			
			
		
	}
	
	

	
	

	
	

	
	
	
}