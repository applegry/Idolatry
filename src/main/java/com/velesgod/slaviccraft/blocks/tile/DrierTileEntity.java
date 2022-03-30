package com.velesgod.slaviccraft.blocks.tile;

import java.util.Map;

import com.google.common.collect.Maps;
import com.velesgod.slaviccraft.blocks.herbs.BasePlant;
import com.velesgod.slaviccraft.blocks.herbs.BaseTallPlant;
import com.velesgod.slaviccraft.core.init.ItemInit;
import com.velesgod.slaviccraft.core.init.TileEntitiesInit;
import com.velesgod.slaviccraft.items.BaseBundle;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.TickingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import com.velesgod.slaviccraft.SlavicCraftMod;

public class DrierTileEntity extends InventoryBlockEntity{
	
	
	  public DrierTileEntity(BlockPos pos, BlockState state) {
	        super(TileEntitiesInit.DRIER_TE.get(), pos, state, 8);
	    }


//	
	   public static Map<Item, Item> initRecipe() {
		      Map<Item, Item> map = Maps.newLinkedHashMap();
		      //Elixir
		      map.put( ItemInit.FIREWHIP_FRESH.get(), ItemInit.FIREWHIP_DRIED.get());
		      map.put(  ItemInit.SWIFTFOOT_FRESH.get(), ItemInit.SWIFTFOOT_DRIED.get());
	      map.put(  ItemInit.SWORDBLADE_FRESH.get(), ItemInit.SWORDBLADE_DRIED.get());
		      map.put( ItemInit.WILDROSEMARY_FRESH.get(), ItemInit.WILDROSEMARY_DRIED.get());
	      map.put(  ItemInit.CHRYSANTHS_FRESH.get(), ItemInit.CHRYSANTHS_DRIED.get());
	      map.put(  ItemInit.IMMORTELLE_FRESH.get(), ItemInit.IMMORTELLE_DRIED.get());
		      
		      map.put(  ItemInit.HELLEBORE_FRESH.get(), ItemInit.HELLEBORE_DRIED.get());
		      map.put( ItemInit.HENBANE_FRESH.get(), ItemInit.HENBANE_DRIED.get());
		      map.put(  ItemInit.HEMLOCK_FRESH.get(), ItemInit.HEMLOCK_DRIED.get());
		      map.put(  ItemInit.RAVENEYE_FRESH.get(), ItemInit.RAVENEYE_DRIED.get());
		      map.put(  ItemInit.THORNAPPLES_FRESH.get(), ItemInit.THORNAPPLES_DRIED.get());
		      map.put(  ItemInit.SLEEPGRASS_FRESH.get(), ItemInit.SLEEPGRASS_DRIED.get());
		      
		      map.put(  ItemInit.MUSH_BUNDLE.get(), ItemInit.DRIED_MUSH_BUNDLE.get());
		      
		      //Vanila
		      map.put(  Items.ROTTEN_FLESH, Items.LEATHER);
		      
		      
		      
		      return map;
		   }
//	
//	
//	
	private Map<Item,Item> recipes = initRecipe();
//	
	@Override
	public ItemStackHandler createInventory() {
		return new ItemStackHandler(8) {
			@Override
			protected void onContentsChanged(int slot) {
				setChanged();
			}
			@Override
			public boolean isItemValid(int slot, ItemStack stack) {
				Item item = stack.getItem();
		
				return (item instanceof BaseBundle)||
						(item == Items.ROTTEN_FLESH)||
						(item == ItemInit.MUSH_BUNDLE.get())||
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

	public int[] work = {0,0,0,0,0,0,0,0};
	public int MAX = 20;//100*20;

	@Override
	public void tick() {
		this.timer++;
        if (this.requiresUpdate && this.level != null) {
            update();
            this.requiresUpdate = false;
        }
		for(int i=0;i<8;i++) {
			if(!inventory.getStackInSlot(i).isEmpty() && recipes.containsKey(inventory.getStackInSlot(i).getItem())) work[i]+=1; else work[i]*=0;
			if(work[i]>=MAX) { work[i]=0;
			ItemStack stacc = new ItemStack(recipes.get(inventory.getStackInSlot(i).getItem()));      
			stacc.setTag(stacc.getOrCreateTag());
			
		inventory.setStackInSlot(i,stacc   ); }
		}
	//	SlavicCraftMod.LOGGER.info(this.level.getBlockEntity(this.getBlockPos()));
	}	


	
	public boolean getWork(int i) {
		return work[i] > 0;
	}
	
}