package com.velesgod.slaviccraft.contaniers;


import com.velesgod.slaviccraft.blocks.tile.SlavicSackTileEntity;
import com.velesgod.slaviccraft.core.init.BlockInit;
import com.velesgod.slaviccraft.core.init.ContainerInit;

import net.minecraft.client.gui.screens.inventory.DispenserScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DispenserMenu;
import net.minecraft.world.inventory.MenuConstructor;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class SlavicSackContainer extends AbstractContainerMenu{
    private static SlavicSackTileEntity te;
    private final ContainerLevelAccess containerAccess;
	private IItemHandler playerInv;
	
	
	public  SlavicSackContainer(int id,Inventory inv) {
		this(id, inv, new ItemStackHandler(9), BlockPos.ZERO);
	}

	
	
	
	@OnlyIn(Dist.CLIENT)
	public SlavicSackContainer(int windowId, Inventory inv, IItemHandler h, BlockPos pos) {
		super(ContainerInit.SACK.get(), windowId);
		this.containerAccess = ContainerLevelAccess.create(inv.player.level, pos);
		this.playerInv = new InvWrapper(inv);
		layoutPlayerInventorySlots(8, 84);
	    if(te != null) {
	    		
        
                
                int i = 0;
                for(int x = 0; x < 3; ++x) {
                    for(int y = 0; y < 3; ++y) {
                    	 addSlot(new SlotItemHandler(h, i, 62+x*18, 17+y*18));
                    	 i++;
                    }
                 }
      
        }
	    dispatchTEToNearbyPlayers(te);
	}
	

	
	
	@SuppressWarnings({ "resource", "rawtypes" })
	public static void dispatchTEToNearbyPlayers(BlockEntity tile) {
		if (tile.getLevel() instanceof ServerLevel) {
			Packet packet = tile.getUpdatePacket();
			if (packet != null) {
				BlockPos pos = tile.getBlockPos(); 
				((ServerChunkCache) tile.getLevel().getChunkSource()).chunkMap
						.getPlayers(new ChunkPos(pos), false)
						.forEach(e -> e.connection.send(packet));
			}
		}
	}
	
	
	
	@Override
	public boolean stillValid(Player playerIn) {
		dispatchTEToNearbyPlayers(te);
		return stillValid(this.containerAccess, playerIn, BlockInit.SACK.get());
	}
	
	
	
	private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0; i < amount; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }

        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0; j < verAmount; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }

        return index;
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        addSlotBox(playerInv, 9, leftCol, topRow, 9, 18, 3, 18);
        topRow += 58;
        addSlotRange(playerInv, 0, leftCol, topRow, 9, 18);
    }


    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        var retStack = ItemStack.EMPTY;
        final Slot slot = getSlot(index);
        if (slot.hasItem()) {
            final ItemStack item = slot.getItem();
            retStack = item.copy();
            if (index < 27) {
                if (!moveItemStackTo(item, 27, this.slots.size(), true))
                    return ItemStack.EMPTY;
            } else if (!moveItemStackTo(item, 0, 27, false))
                return ItemStack.EMPTY;

            if (item.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return retStack;
    }

    public static MenuConstructor getServerContainer(SlavicSackTileEntity chest, BlockPos pos) {
    	te = chest;
        return (id, playerInv, player) -> new SlavicSackContainer(id, playerInv, chest.inventory, pos);
    }
    
    
}