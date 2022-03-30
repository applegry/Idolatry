package com.velesgod.slaviccraft.blocks;

import java.util.Random;

import com.velesgod.slaviccraft.core.init.ParticleInit;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;

public class GoldenLeaves extends LeavesBlock{

	public GoldenLeaves() {
		super(Properties
				.of(Material.LEAVES)
				.strength(0.2F)
				.randomTicks()
				.sound(SoundType.GRASS)
				.noOcclusion());

	}
	
	@Override
	public void animateTick(BlockState p_54431_, Level p_54432_, BlockPos p_54433_, Random p_54434_) {
		if(p_54432_.getBlockState(p_54433_.below()).isAir()) {
			Random random = new Random();
			BlockPos pos = p_54433_;
			  double d0 = (double)0.03125D;
		      double d1 = (double)0.13125F;
		      double d2 = (double)0.7375F;
		  //  for(int i = 0; i < 3; ++i) {
		         double d3 = random.nextGaussian() * 0.02D;
		         double d4 = random.nextGaussian() * 0.02D;
		         double d5 = random.nextGaussian() * 0.02D;
		         p_54432_.addParticle(ParticleInit.GOLDEN_LEAF_PARTICLE.get(), 
		        		 (double)pos.getX() + (double)0.13125F + (double)0.7375F-random.nextDouble(), 
		        		 (double)pos.getY(), 
		        		 (double)pos.getZ() + (double)0.13125F + (double)0.7375F-random.nextDouble(), 
		        		 0, 0.0001, 0);
		  //    }
			
			
		
		}
		super.animateTick(p_54431_, p_54432_, p_54433_, p_54434_);
	}
	
}