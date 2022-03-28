package com.velesgod.slaviccraft.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Vector3f;
import com.velesgod.slaviccraft.blocks.CutOakIdol;
import com.velesgod.slaviccraft.blocks.tile.PedestalTileEntity;
import com.velesgod.slaviccraft.core.init.BlockInit;
import com.velesgod.slaviccraft.core.init.ItemInit;
import com.velesgod.slaviccraft.core.init.ParticleInit;
import com.velesgod.slaviccraft.items.BaseBundle;
import com.velesgod.slaviccraft.items.BasePowder;

import net.minecraft.client.particle.DustParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class IdolUtils{
	
	
	
	public static List<WreathRecipe> initRecipes(){
		List<WreathRecipe> m = new ArrayList<WreathRecipe>();
		m.add(new WreathRecipe(ItemInit.ARTEMISISA_DRIED.get(),ItemInit.HEATHER_DRIED.get(),ItemInit.AGILITY_WREATH.get()));
		return m;
	}
	
	public static PedestalTileEntity getEnt(Level w,BlockPos p) {
		return  (PedestalTileEntity) w.getBlockEntity(p);
	}
	
	public static ItemStack getItemFrom(Level w,BlockPos p) {
		PedestalTileEntity te = (PedestalTileEntity) w.getBlockEntity(p);
		return te.inventory.getStackInSlot(0);
	}
	public static boolean isRitualReady(Level w,BlockPos p) {
		boolean n = (getItemFrom(w,p.east(3)) != ItemStack.EMPTY)&&
					(getItemFrom(w,p.west(3)) != ItemStack.EMPTY)&&
					(getItemFrom(w,p.north(3)) != ItemStack.EMPTY)&&
					(getItemFrom(w,p.south(3)) != ItemStack.EMPTY)&&
					(getItemFrom(w,p.east(2).north(2)) != ItemStack.EMPTY)&&
					(getItemFrom(w,p.east(2).south(2)) != ItemStack.EMPTY)&&
					(getItemFrom(w,p.west(2).north(2)) != ItemStack.EMPTY)&&
					(getItemFrom(w,p.west(2).south(2)) != ItemStack.EMPTY);
		return n;
	}
	
	
	public static List<WreathRecipe> WreathRecipes = initRecipes();
	
	public static void setIdolHead(Block block,Level world,BlockPos pos,Player player) {
		if(checkIdolBody(world.getBlockState(pos))) {
			if(block == BlockInit.CUT_ACACIA_BLOCK.get()) setIdolHeadOn(BlockInit.CUT_ACACIA_IDOL.get(),world,pos,player);
			if(block == BlockInit.CUT_JUNGLE_BLOCK.get()) setIdolHeadOn(BlockInit.CUT_JUNGLE_IDOL.get(),world,pos,player);
			if(block == BlockInit.CUT_SPRUCE_BLOCK.get()) setIdolHeadOn(BlockInit.CUT_SPRUCE_IDOL.get(),world,pos,player);
			if(block == BlockInit.CUT_BIRCH_BLOCK.get()) setIdolHeadOn(BlockInit.CUT_BIRCH_IDOL.get(),world,pos,player);
			if(block == BlockInit.CUT_DARK_OAK_BLOCK.get()) setIdolHeadOn(BlockInit.CUT_DARK_OAK_IDOL.get(),world,pos,player);
			if(block == BlockInit.CUT_OAK_BLOCK.get()) setIdolHeadOn(BlockInit.CUT_OAK_IDOL.get(),world,pos,player);
			if(block == BlockInit.CUT_WARPED_BLOCK.get()) setIdolHeadOn(BlockInit.CUT_WARPED_IDOL.get(),world,pos,player);
			if(block == BlockInit.CUT_CRIMSON_BLOCK.get()) setIdolHeadOn(BlockInit.CUT_CRIMSON_IDOL.get(),world,pos,player);
		}
	}
	
	 
	 public static void setIdolHeadOn(Block head,Level world,BlockPos pos,Player player) {
		 world.setBlock(pos, head.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING,player.getDirection().getOpposite()), 3);
	 }
	 
	 public static void setIdolBodyOn(Block body,Level world,BlockPos pos) {
		 world.setBlock(pos, body.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.UP), 3);
	 }
	 

	 public static boolean checkIdolBody(BlockState state) {
		 return (state.getValue(BlockStateProperties.FACING) == Direction.UP) || (state.getValue(BlockStateProperties.FACING) == Direction.DOWN);
	 }
	 
	 public static void setIdolBody(Block block,Level world,BlockPos pos) {
		 if(block == Blocks.STRIPPED_OAK_LOG)      setIdolBodyOn(BlockInit.CUT_OAK_BLOCK.get(), world, pos);
		 if(block == Blocks.STRIPPED_JUNGLE_LOG)   setIdolBodyOn(BlockInit.CUT_JUNGLE_BLOCK.get(), world, pos);
		 if(block == Blocks.STRIPPED_ACACIA_LOG)   setIdolBodyOn(BlockInit.CUT_ACACIA_BLOCK.get(), world, pos);
		 if(block == Blocks.STRIPPED_SPRUCE_LOG)   setIdolBodyOn(BlockInit.CUT_SPRUCE_BLOCK.get(), world, pos);
		 if(block == Blocks.STRIPPED_DARK_OAK_LOG) setIdolBodyOn(BlockInit.CUT_DARK_OAK_BLOCK.get(), world, pos);
		 if(block == Blocks.STRIPPED_BIRCH_LOG)    setIdolBodyOn(BlockInit.CUT_BIRCH_BLOCK.get(), world, pos);
		 if(block == Blocks.STRIPPED_WARPED_STEM)  setIdolBodyOn(BlockInit.CUT_WARPED_BLOCK.get(), world, pos);
		 if(block == Blocks.STRIPPED_CRIMSON_STEM) setIdolBodyOn(BlockInit.CUT_CRIMSON_BLOCK.get(), world, pos);
		
	 }
	 
	 
	 public static boolean checkIdolSystem(Level w,BlockPos p) {
		
		 
		 return (w.getBlockState(p.above(2).east(5)).getBlock() instanceof CutOakIdol)&&
		 (w.getBlockState(p.above(2).west(5)).getBlock() instanceof CutOakIdol)&&
		 (w.getBlockState(p.above(2).north(5)).getBlock() instanceof CutOakIdol)&&
		 (w.getBlockState(p.above(2).south(5)).getBlock() instanceof CutOakIdol);
		 
		
	 }
	 
	 public static void activateIdols(Level w,BlockPos p) {
		 w.setBlock(p.above(2).east(5), w.getBlockState(p.above(2).east(5)).setValue(CutOakIdol.LIT, true), 3);
		 w.setBlock(p.above(2).west(5), w.getBlockState(p.above(2).west(5)).setValue(CutOakIdol.LIT, true), 3);
		 w.setBlock(p.above(2).north(5), w.getBlockState(p.above(2).north(5)).setValue(CutOakIdol.LIT, true), 3);
		 w.setBlock(p.above(2).south(5), w.getBlockState(p.above(2).south(5)).setValue(CutOakIdol.LIT, true), 3);
	 }
	 public static void createSphere(Level world,BlockPos pos,double speed, int size)
	    {
	        double d0 = pos.getX()+0.5f;
	        double d1 = pos.getY()+0.5f;
	        double d2 = pos.getZ()+0.5f;
	        Random rand = world.getRandom();

	        for (int i = -size; i <= size; ++i)
	        {
	            for (int j = -size; j <= size; ++j)
	            {
	                for (int k = -size; k <= size; ++k)
	                {
	                    double d3 = (double)j + (rand.nextDouble() - rand.nextDouble()) * 0.1D;
	                    double d4 = (double)i + (rand.nextDouble() - rand.nextDouble()) * 0.1D;
	                    double d5 = (double)k + (rand.nextDouble() - rand.nextDouble()) * 0.1D;
	                    double d6 = (double)Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5) / speed + rand.nextGaussian() * 5.2D;
	            //        world.addParticle(new RedstoneParticleData(0.8f, 0.f,0f, 1.1f),d0, d1, d2, d3 / d6, -d4 / d6, d5 / d6);

	                    world.addParticle(ParticleInit.AMBER_PARTICLE.get(),d0, d1, d2, d3 / d6, d4 / d6, d5 / d6);
	               //     world.addParticle(ParticleTypes.LAVA,d0, d1, d2, d3 / d6, d4 / d6, d5 / d6);
	                    
	                    
	                    if (i != -size && i != size && j != -size && j != size)
	                    {
	                        k += size * 2 - 1;
	                    }
	                }
	            }
	        }
	    }
	    
	 
	 public static boolean weHave(List<Item> l,Item el) {
		return Collections.frequency(l, el) == 4;
	 }
	 
	 
	 public static void clearCircle(Level w,BlockPos p) {
		 getEnt(w,p.east(3)).clear();
		 getEnt(w,p.west(3)).clear();
		 getEnt(w,p.north(3)).clear();
		 getEnt(w,p.south(3)).clear();
		 
		 getEnt(w,p.east(2).north(2)).clear();
		 getEnt(w,p.east(2).south(2)).clear();
		 getEnt(w,p.west(2).north(2)).clear();
		 getEnt(w,p.west(2).south(2)).clear();
		 w.setBlock(p.above(2).east(5), w.getBlockState(p.above(2).east(5)).setValue(CutOakIdol.LIT, false), 3);
		 w.setBlock(p.above(2).west(5), w.getBlockState(p.above(2).west(5)).setValue(CutOakIdol.LIT, false), 3);
		 w.setBlock(p.above(2).north(5), w.getBlockState(p.above(2).north(5)).setValue(CutOakIdol.LIT, false), 3);
		 w.setBlock(p.above(2).south(5), w.getBlockState(p.above(2).south(5)).setValue(CutOakIdol.LIT, false), 3);
	 }
	 
	 
	 public static Item getPedestal(Level w,BlockPos p,int i) {
		 if(i==0)  return getItemFrom(w,p.east(3)).getItem();
		 if(i==1)  return getItemFrom(w,p.west(3)).getItem();
		 if(i==2)  return getItemFrom(w,p.north(3)).getItem();
		 if(i==3)  return getItemFrom(w,p.south(3)).getItem();
		 if(i==4)  return getItemFrom(w,p.east(2).north(2)).getItem();
		 if(i==5)  return getItemFrom(w,p.east(2).south(2)).getItem();
		 if(i==6)  return getItemFrom(w,p.west(2).north(2)).getItem();
		 if(i==7)  return getItemFrom(w,p.west(2).south(2)).getItem();
		 return Items.AIR;
	 }
	 public static List<Item> getCircle(Level w,BlockPos p) {
		 List<Item> circle = new ArrayList<Item>();
		 circle.add(getPedestal(w,p,0));
		 circle.add(getPedestal(w,p,1));
		 circle.add(getPedestal(w,p,2));
		 circle.add(getPedestal(w,p,3));
		 circle.add(getPedestal(w,p,4));
		 circle.add(getPedestal(w,p,5));
		 circle.add(getPedestal(w,p,6));
		 circle.add(getPedestal(w,p,7));
		 return circle;
	 }
	 
	 public static Item checkRitual(Level level,BlockPos pos) {
		 List<Item> c = getCircle(level,pos);
		 for (var entry : WreathRecipes) {
			 	if(weHave(c,entry.component1) && weHave(c,entry.component2))
			 		return entry.result;
			}
		 return Items.AIR;
	 }
	 
		static double random(Random ran) {
			return (ran.nextDouble()-0.5D)*0.5D;
		}
		
		
	public static void flameOn(Level w,BlockPos p) {
	     Random rand = w.getRandom();
		 
	     w.addParticle(ParticleInit.IDOL_PARTICLE.get(),
				 p.east(3).getX()+0.5f+random(rand),
				 p.east(3).getY()+1.8f, 
				 p.east(3).getZ()+0.5f+random(rand), 0, 0, 0);
		 
		 w.addParticle(ParticleInit.IDOL_PARTICLE.get(),
				 p.west(3).getX()+0.5f+random(rand),
				 p.west(3).getY()+1.8f, 
				 p.west(3).getZ()+0.5f+random(rand), 0, 0, 0);
		 
		 w.addParticle(ParticleInit.IDOL_PARTICLE.get(),
				 p.south(3).getX()+0.5f+random(rand),
				 p.south(3).getY()+1.8f, 
				 p.south(3).getZ()+0.5f+random(rand), 0, 0, 0);
		 
		 w.addParticle(ParticleInit.IDOL_PARTICLE.get(),
				 p.north(3).getX()+0.5f+random(rand),
				 p.north(3).getY()+1.8f, 
				 p.north(3).getZ()+0.5f+random(rand), 0, 0, 0);
		 
		 
		 w.addParticle(ParticleInit.IDOL_PARTICLE.get(),
				 p.east(2).north(2).getX()+0.5f+random(rand),
				 p.east(2).north(2).getY()+1.8f, 
				 p.east(2).north(2).getZ()+0.5f+random(rand), 0, 0, 0);
		 
		 w.addParticle(ParticleInit.IDOL_PARTICLE.get(),
				 p.east(2).south(2).getX()+0.5f+random(rand),
				 p.east(2).south(2).getY()+1.8f, 
				 p.east(2).south(2).getZ()+0.5f+random(rand), 0, 0, 0);
		 
		 w.addParticle(ParticleInit.IDOL_PARTICLE.get(),
				 p.west(2).north(2).getX()+0.5f+random(rand),
				 p.west(2).north(2).getY()+1.8f, 
				 p.west(2).north(2).getZ()+0.5f+random(rand), 0, 0, 0);
		 
		 w.addParticle(ParticleInit.IDOL_PARTICLE.get(),
				 p.west(2).south(2).getX()+0.5f+random(rand),
				 p.west(2).south(2).getY()+1.8f, 
				 p.west(2).south(2).getZ()+0.5f+random(rand), 0, 0, 0);
	}
		
	
	public static boolean isIdolOn(Level w,BlockPos p) {
		if(checkIdolSystem(w, p))
		return w.getBlockState(p.above(2).east(5)).getValue(CutOakIdol.LIT)&&
				 w.getBlockState(p.above(2).west(5)).getValue(CutOakIdol.LIT)&&
				 w.getBlockState(p.above(2).north(5)).getValue(CutOakIdol.LIT)&&
				 w.getBlockState(p.above(2).south(5)).getValue(CutOakIdol.LIT);
		else
			return false;
	}
		
		
		
	 public static void createLilBoom(Level w,BlockPos p) {
	        double d0 = p.getX()+0.5f;
	        double d1 = p.getY()+1.5f;
	        double d2 = p.getZ()+0.5f;
	        
	        Random rand = w.getRandom();
	        w.addParticle(ParticleInit.IDOL_PARTICLE.get(),p.getX()+0.5f+random(rand),p.getY()+1.8f, p.getZ()+0.5f+random(rand), 0, 0, 0);
	        w.addParticle(ParticleInit.IDOL_PARTICLE.get(),p.getX()+0.5f+random(rand),p.getY()+1.8f, p.getZ()+0.5f+random(rand), 0, 0, 0);
	        w.addParticle(ParticleInit.IDOL_PARTICLE.get(),p.getX()+0.5f+random(rand),p.getY()+1.8f, p.getZ()+0.5f+random(rand), 0, 0, 0);
	        w.addParticle(ParticleInit.IDOL_PARTICLE.get(),p.getX()+0.5f+random(rand),p.getY()+1.8f, p.getZ()+0.5f+random(rand), 0, 0, 0);
	        w.addParticle(ParticleInit.IDOL_PARTICLE.get(),p.getX()+0.5f+random(rand),p.getY()+1.8f, p.getZ()+0.5f+random(rand), 0, 0, 0);
	        w.addParticle(ParticleInit.IDOL_PARTICLE.get(),p.getX()+0.5f+random(rand),p.getY()+1.8f, p.getZ()+0.5f+random(rand), 0, 0, 0);
	        int size = 3;

	        for (int i = -size; i <= size; ++i)
	        {
	            for (int j = -size; j <= size; ++j)
	            {
	                for (int k = -size; k <= size; ++k)
	                {
	                    double d3 = (double)j + (rand.nextDouble() - rand.nextDouble()) * 3.1D;
	                    double d4 = (double)i + (rand.nextDouble() - rand.nextDouble()) * 3.1D;
	                    double d5 = (double)k + (rand.nextDouble() - rand.nextDouble()) * 3.1D;
	                    double d6 = (double)Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5) / 20 + rand.nextGaussian() * 5.2D;
	              //      w.addParticle(new DustParticleOptions(new Vector3f(1.f,0.f,0.f),1.f),d0, d1, d2, d3 / d6, -d4 / d6, d5 / d6);
	                    
	                	
	                 //   world.addParticle(ParticleInit.AMBER_PARTICLE.get(),d0, d1, d2, d3 / d6, d4 / d6, d5 / d6);
	               //     world.addParticle(ParticleTypes.LAVA,d0, d1, d2, d3 / d6, d4 / d6, d5 / d6);
	                    
	                    
	                    if (i != -size && i != size && j != -size && j != size)
	                    {
	                        k += size * 2 - 1;
	                    }
	                }
	            }
	        }
	 }
	 
}