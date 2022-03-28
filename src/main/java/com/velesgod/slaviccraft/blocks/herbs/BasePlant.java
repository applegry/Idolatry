package com.velesgod.slaviccraft.blocks.herbs;



import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BasePlant extends BaseHerb {
   protected static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D);
   private final MobEffect suspiciousStewEffect;
   public BasePlant() {
      super(Properties.copy(Blocks.WHEAT));
	   MobEffect effect = MobEffects.GLOWING;
	 
      this.suspiciousStewEffect = effect;
      if (effect.isInstantenous()) {
      } else {
      }

   }

   public VoxelShape getShape(BlockState p_220053_1_, BlockGetter p_220053_2_, BlockPos p_220053_3_, CollisionContext p_220053_4_) {
      Vec3 vector3d = p_220053_1_.getOffset(p_220053_2_, p_220053_3_);
      return SHAPE.move(vector3d.x, vector3d.y, vector3d.z);
   }

   public BlockBehaviour.OffsetType getOffsetType() {
      return BlockBehaviour.OffsetType.XZ;
   }

   public MobEffect getSuspiciousStewEffect() {
      return this.suspiciousStewEffect;
   }

   public int getEffectDuration() {
      return 0;
   }
}