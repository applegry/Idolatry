package com.velesgod.slaviccraft.blocks.tile;

import java.util.Map;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import com.velesgod.slaviccraft.core.init.EffectInit;
import com.velesgod.slaviccraft.core.init.ItemInit;
import com.velesgod.slaviccraft.core.init.TileEntitiesInit;
import com.velesgod.slaviccraft.effects.SlavicEffect;
import com.velesgod.slaviccraft.items.BasePowder;
import com.velesgod.slaviccraft.items.SlavicPotionBase;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.items.ItemStackHandler;

public class ElixirCauldronTileEntity extends InventoryBlockEntity{

	

	
	  public ElixirCauldronTileEntity(BlockPos pos, BlockState state) {
	        super(TileEntitiesInit.CAULDRON_TE.get(), pos, state, 6);
	    }

	

	
	public int smelting = 0;
	
	public int cookProcess = 0;
	
	

	
	@Override
	public ItemStackHandler createInventory() {
		return new ItemStackHandler(6) {
			@Override
			protected void onContentsChanged(int slot) {
				setChanged();
			}
			@SuppressWarnings("deprecation")
			@Override
			public boolean isItemValid(int slot, ItemStack stack) {
				switch(slot) {
				case 0: return AbstractFurnaceBlockEntity.getFuel().containsKey(stack.getItem());
				case 1: return stack.getItem() instanceof BasePowder;
				case 2: return stack.getItem() instanceof BasePowder;
				case 3: return true;
				case 4: return true;
				case 5: return stack.getItem() == ItemInit.LIVINGBLOOD_BOTTLE.get()
							|| stack.getItem() == ItemInit.DEADBLOOD_BOTTLE.get();
				default:return false;
				}
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
	

	

	
	public boolean startSmelt() {
		return (!inventory.getStackInSlot(0).isEmpty() &&
				recipeExist(inventory.getStackInSlot(1),inventory.getStackInSlot(2)) && 
				inventory.getStackInSlot(3).getCount() > 0 &&
				inventory.getStackInSlot(3).getCount() < 64 &&
				inventory.getStackInSlot(3).getItem() == ItemInit.ELIXIR_BOLLTE.get() &&
				smelting<=0);
	}
	
	public boolean canCook() {
		
		return 	recipeExist(inventory.getStackInSlot(1),inventory.getStackInSlot(2)) && 
				!inventory.getStackInSlot(3).isEmpty() &&
				inventory.getStackInSlot(3).getCount() < 64 &&
				!inventory.getStackInSlot(4).isEmpty() &&
				inventory.getStackInSlot(4).getCount() < 64 &&
				!inventory.getStackInSlot(1).isEmpty() &&
				inventory.getStackInSlot(1).getCount() < 64 &&
				!inventory.getStackInSlot(2).isEmpty() &&
				inventory.getStackInSlot(2).getCount() < 64;
	}
	
	
	public ItemStack getRecipe(ItemStack i,ItemStack j,int k) {
		if(recipeExist(i,j)) return getElixirRecipe(i,j,k);
		return ItemStack.EMPTY;
	}
	
	public int MAX = 0;
	public int CMAX = 400;
	
	@SuppressWarnings("deprecation")
	@Override
	public void tick() {
		
		
		if(startSmelt()) {
			smelting=AbstractFurnaceBlockEntity.getFuel().get(inventory.getStackInSlot(0).getItem());
			MAX = smelting;
			level.setBlock(getBlockPos(), getBlockState().setValue(BlockStateProperties.LIT, true), 2);
			ItemStack stack = inventory.getStackInSlot(0);
			stack.shrink(1);
			inventory.setStackInSlot(0, stack.copy());
		}
		if(smelting==0)	{level.setBlock(getBlockPos(), getBlockState().setValue(BlockStateProperties.LIT, false), 2);
			if(	cookProcess>0) 	cookProcess-=2;
		}
			//if(!canCook()) cookProcess=0;
		
			if(smelting>0) {
				smelting--;
				if(canCook()) {
				cookProcess++;
			
				if(cookProcess==270 && canCook()) {
					cookProcess = 0;
					Boolean b = recipeExist(inventory.getStackInSlot(1),inventory.getStackInSlot(2));
				
				if(!this.level.isClientSide()) {
				ItemStack I1 = getRecipe(inventory.getStackInSlot(1),inventory.getStackInSlot(2),1);
				ItemStack I2 = getRecipe(inventory.getStackInSlot(1),inventory.getStackInSlot(2),2);
				inventory.setStackInSlot(3,I1.copy());
				inventory.setStackInSlot(4,I2.copy());
				inventory.setStackInSlot(1,  ItemStack.EMPTY);
				inventory.setStackInSlot(2,  ItemStack.EMPTY);
				}
			}
						
			}
		}
	}
	

	
	//RECIPES
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Pair<BasePowder,BasePowder> getPowders(BasePowder F,BasePowder T) {
		return new Pair(F,T);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Pair<BasePowder,BasePowder> getPowders(BasePowder F) {
		return new Pair(F,F);
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Pair<ItemStack,ItemStack> getElixirs(BasePowder F,BasePowder T) {
		return new Pair(F,T);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Pair<ItemStack,ItemStack> getElixirs(BasePowder F) {
		return new Pair(F,F);
	}
	
	
	public Pair<ItemStack,ItemStack> getElixirs(SlavicEffect E) {
		ItemStack F = new ItemStack(ItemInit.ELIXIR.get(),1);
		F = SlavicPotionBase.getCustomElixir(new ItemStack(F.getItem()), new MobEffectInstance(E,20*30) );
		return new Pair(F,F);
	}
	
	
	public boolean recipeExist(ItemStack i,ItemStack j) {
		Pair<BasePowder,BasePowder> temp = new Pair(i.getItem(),j.getItem());
		return ElixirRecipes.containsKey(temp);
	}
	
	public  ItemStack getElixirRecipe(ItemStack i,ItemStack j,int slot) {
		Pair<BasePowder,BasePowder> temp = new Pair(i.getItem(),j.getItem());
		if(ElixirRecipes.containsKey(temp)) 
			if(slot == 0)
				return ElixirRecipes.get(temp).getFirst();
				else
					return ElixirRecipes.get(temp).getSecond();
		return ItemStack.EMPTY;
	}
	
	
	public  Map<Pair<BasePowder,BasePowder>,Pair<ItemStack,ItemStack>> createRecipes(){
		 Map<Pair<BasePowder,BasePowder>,Pair<ItemStack,ItemStack>> temp =  Maps.newLinkedHashMap();
		 
		 
		 temp.put(getPowders(ItemInit.SWIFTFOOT_POWDER.get()), getElixirs(EffectInit.LIGHT_FEET.get()));
		 temp.put(getPowders(ItemInit.FIREWHIP_POWDER.get()), getElixirs(EffectInit.SVAROG_CIRCLE.get()));
		 
		return temp;
	}
	
	
	
	
	
		public  Map<Pair<BasePowder,BasePowder>,Pair<ItemStack,ItemStack>> ElixirRecipes = createRecipes();
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}