package com.velesgod.slaviccraft.contaniers;


import com.velesgod.slaviccraft.blocks.tile.ElixirCauldronTileEntity;
import com.velesgod.slaviccraft.core.init.BlockInit;
import com.velesgod.slaviccraft.core.init.ContainerInit;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuConstructor;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class ElixirCauldronContanier extends AbstractContainerMenu{
    private static ElixirCauldronTileEntity te;
    private final ContainerLevelAccess containerAccess;
	private IItemHandler playerInv;
	
	
	public  ElixirCauldronContanier(int id,Inventory inv) {
		this(id, inv, new ItemStackHandler(6), BlockPos.ZERO);
	}

	
	
	
	@OnlyIn(Dist.CLIENT)
	public ElixirCauldronContanier(int windowId, Inventory inv, IItemHandler h, BlockPos pos) {
		super(ContainerInit.CAULDRON_CONTANIER.get(), windowId);
		this.containerAccess = ContainerLevelAccess.create(inv.player.level, pos);
		this.playerInv = new InvWrapper(inv);
		layoutPlayerInventorySlots(8, 86);
	    if(te != null) {
         
            	addSlot(new SlotItemHandler(h, 0, 32, 50));
                addSlot(new SlotItemHandler(h, 1, 71, 14));
                addSlot(new SlotItemHandler(h, 2, 89, 14));
                addSlot(new SlotItemHandler(h, 3, 64, 62));
                addSlot(new SlotItemHandler(h, 4, 96, 62));
                addSlot(new SlotItemHandler(h, 5, 130, 28));

        }
	    dispatchTEToNearbyPlayers(te);
	}
	
	
	public int getSmelt() {return ((ElixirCauldronTileEntity)te).smelting;}
	
	public int getCooking() {return ((ElixirCauldronTileEntity)te).cookProcess;}
	
	public int getMAX() { return ((ElixirCauldronTileEntity)te).MAX;}

	public int getCookingMAX() { return ((ElixirCauldronTileEntity)te).CMAX; }

	
	
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
		return stillValid(this.containerAccess, playerIn, BlockInit.ELIXIR_CAULDRON.get());
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

    public static MenuConstructor getServerContainer(ElixirCauldronTileEntity chest, BlockPos pos) {
    	te = chest;
        return (id, playerInv, player) -> new ElixirCauldronContanier(id, playerInv, chest.inventory, pos);
    }
    
    
}