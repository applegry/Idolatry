package com.velesgod.slaviccraft.contaniers;

import com.velesgod.slaviccraft.SlavicCraftMod;
import com.velesgod.slaviccraft.blocks.tile.DrierTileEntity;
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
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class DrierBlockContainer extends AbstractContainerMenu {
    private static DrierTileEntity te;
    private final ContainerLevelAccess containerAccess;
	private IItemHandler playerInv;
	
	
	

	
	public  DrierBlockContainer(int id,Inventory inv) {
		this(id, inv, new ItemStackHandler(8), BlockPos.ZERO);
		//this.te = inv.player.level.getBlockEntity(pos);
		
	}
	
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
	
	
	@OnlyIn(Dist.CLIENT)
	public DrierBlockContainer(int windowId, Inventory inv, IItemHandler h, BlockPos pos) {
		super(ContainerInit.DRIER.get(), windowId);


		this.containerAccess = ContainerLevelAccess.create(inv.player.level, pos);
		this.playerInv = new InvWrapper(inv);
		layoutPlayerInventorySlots(8, 84);
		
	   if(te != null) {
  
            	addSlot(new SlotItemHandler(h, 0, 32, 16));
                addSlot(new SlotItemHandler(h, 1, 64, 16));
                addSlot(new SlotItemHandler(h, 2, 96, 16));
                addSlot(new SlotItemHandler(h, 3, 128, 16));
                addSlot(new SlotItemHandler(h, 4, 32, 48));
                addSlot(new SlotItemHandler(h, 5, 64, 48));
                addSlot(new SlotItemHandler(h, 6, 96, 48));
                addSlot(new SlotItemHandler(h, 7, 128, 48));

        }
            	dispatchTEToNearbyPlayers(te);
	}
	
	
	public boolean getWork(int i) {
		//return false;
		return ((DrierTileEntity) te).getWork(i);
	}

	public int getMax() { 
	//	return 0;
		return ((DrierTileEntity) te).MAX;
		
	}

	public int getProgress(int i) { 
		//return 0;
		return ((DrierTileEntity) te).work[i];
	}
	

	
	@Override
	public boolean stillValid(Player playerIn) {
		dispatchTEToNearbyPlayers(te);
		return stillValid(this.containerAccess, playerIn, BlockInit.DRIER.get());
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
   // @Override
  //  public void removed(PlayerEntity p_75134_1_) {
    	// TODO Auto-generated method stub
    //	super.removed(p_75134_1_);
   // }
	

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

    public static MenuConstructor getServerContainer(DrierTileEntity chest, BlockPos pos) {
    	te = chest;
        return (id, playerInv, player) -> new DrierBlockContainer(id, playerInv, chest.inventory, pos);
    }

	
}