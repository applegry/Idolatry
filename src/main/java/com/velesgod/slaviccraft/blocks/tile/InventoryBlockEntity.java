//This code was written by TurtyWurty. Huge thanks to him!
//https://github.com/DaRealTurtyWurty/1.18-Tutorial-Mod/blob/4c1edb15260c8762eaa6c2e8006d2a0260a20fbc/src/main/java/io/github/darealturtywurty/tutorialmod/common/block/entity/InventoryBlockEntity.java#L19


package com.velesgod.slaviccraft.blocks.tile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class InventoryBlockEntity extends BlockEntity {
    public final int size;
    protected int timer;
    protected boolean requiresUpdate;
    
    public final ItemStackHandler inventory;
    protected LazyOptional<ItemStackHandler> handler;

    public InventoryBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int size) {
        super(type, pos, state);
        if (size <= 0) {
            size = 1;
        }

        this.size = size;
        this.inventory = createInventory();
        this.handler = LazyOptional.of(() -> this.inventory);
    }

    public ItemStack extractItem(int slot) {
        final int count = getItemInSlot(slot).getCount();
        this.requiresUpdate = true;
        return this.handler.map(inv -> inv.extractItem(slot, count, false)).orElse(ItemStack.EMPTY);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? this.handler.cast()
                : super.getCapability(cap, side);
    }
    
    public LazyOptional<ItemStackHandler> getHandler() {
        return this.handler;
    }
    
    public ItemStack getItemInSlot(int slot) {
        return this.handler.map(inv -> inv.getStackInSlot(slot)).orElse(ItemStack.EMPTY);
    }
    

    @Override
    public CompoundTag getUpdateTag() {
        return serializeNBT();
    }

    
    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
    	//Debug
       // System.out.println("[DEBUG]:Client recived tile sync packet");

        load(pkt.getTag());
        setChanged();
        handleUpdateTag(pkt.getTag());
    //    level.markAndNotifyBlock(worldPosition, null, getBlockState(), getBlockState(), timer, size);
      //  worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    	super.onDataPacket(net, pkt);
        load(pkt.getTag());
        setChanged();
        handleUpdateTag(pkt.getTag());
        
    }
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
    	CompoundTag tagCompound = new CompoundTag();
        this.saveAdditional(tagCompound);
        return ClientboundBlockEntityDataPacket.create(this);
    }
    
    
    
 
    
    
    
    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        load(tag);
    }
    
    public ItemStack insertItem(int slot, ItemStack stack) {
        final ItemStack copy = stack.copy();
        stack.shrink(copy.getCount());
        this.requiresUpdate = true;
        return this.handler.map(inv -> inv.insertItem(slot, copy, false)).orElse(ItemStack.EMPTY);
    }
    
    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.handler.invalidate();
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.inventory.deserializeNBT(tag.getCompound("Inventory"));
    }
    
  
    public void tick() {
        this.timer++;
        if (this.requiresUpdate && this.level != null) {
            update();
            this.requiresUpdate = false;
        }
    }
    
    public void update() {
        requestModelDataUpdate();
        setChanged();
        if (this.level != null) {
            this.level.setBlockAndUpdate(this.worldPosition, getBlockState());
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("Inventory", this.inventory.serializeNBT());
    }

    public ItemStackHandler createInventory() {
        return new ItemStackHandler(this.size) {
            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate) {
                InventoryBlockEntity.this.update();
                return super.extractItem(slot, amount, simulate);
            }
            
            @Override
            public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
                InventoryBlockEntity.this.update();
                return super.insertItem(slot, stack, simulate);
            }
        };
    }
}