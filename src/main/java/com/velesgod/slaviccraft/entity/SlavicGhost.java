package com.velesgod.slaviccraft.entity;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.CustomInstructionKeyframeEvent;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;


public class SlavicGhost extends Monster implements IAnimatable, IAnimationTickable {
	private AnimationFactory factory = new AnimationFactory(this);

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.wraith.idle", true));
		return PlayState.CONTINUE;
	}

	public SlavicGhost(EntityType<? extends Monster> type, Level worldIn) {
		super(type, worldIn);
		this.noCulling = true;
		  this.moveControl = new WraithMoveControl(this);
		   this.xpReward = 1;
	}



	   public static AttributeSupplier.Builder createMobAttributes() {
	      return Monster.createMobAttributes()
	    		  .add(Attributes.MAX_HEALTH, 10.0D)
	    		
	    		  .add(Attributes.ATTACK_DAMAGE, 4.0D);
	   }
	
	@Override
	public void registerControllers(AnimationData data) {
		AnimationController<SlavicGhost> controller = new AnimationController<>(this, "controller", 0, this::predicate);
		controller.registerCustomInstructionListener(this::customListener);
		data.addAnimationController(controller);
	}

	@SuppressWarnings("resource")
	private <ENTITY extends IAnimatable> void customListener(CustomInstructionKeyframeEvent<ENTITY> event) {
		final LocalPlayer player = Minecraft.getInstance().player;
		if (player != null) {
			player.displayClientMessage(new TextComponent("KeyFraming"), true);
		}
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}
	@Override
	  protected void defineSynchedData() {
	      super.defineSynchedData();
	      this.entityData.define(DATA_FLAGS_ID, (byte)0);
	   }
	@Override
	protected void registerGoals() {
	      this.goalSelector.addGoal(0, new FloatGoal(this));

		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
	   this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
	      this.goalSelector.addGoal(4, new WraithAttackGoal());
		super.registerGoals();
	}
	@Override
	public void tick() {
	      this.noPhysics = true;
	      super.tick();
	      this.noPhysics = false;
	      this.setNoGravity(true);
	}
	
	@Override
	public int tickTimer() {
		return tickCount;
		
	}
	
	 protected static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(SlavicGhost.class, EntityDataSerializers.BYTE);

	
	
	private boolean getVexFlag(int p_34011_) {
	      int i = this.entityData.get(DATA_FLAGS_ID);
	      return (i & p_34011_) != 0;
	   }

	   private void setVexFlag(int p_33990_, boolean p_33991_) {
	      int i = this.entityData.get(DATA_FLAGS_ID);
	      if (p_33991_) {
	         i |= p_33990_;
	      } else {
	         i &= ~p_33990_;
	      }

	      this.entityData.set(DATA_FLAGS_ID, (byte)(i & 255));
	   }

	   public boolean isCharging() {
	      return this.getVexFlag(1);
	   }

	   public void setIsCharging(boolean p_34043_) {
	      this.setVexFlag(1, p_34043_);
	   }
	
	
	
	

	   class WraithAttackGoal extends Goal {
	      public WraithAttackGoal() {
	         this.setFlags(EnumSet.of(Goal.Flag.MOVE));
	      }

	      public boolean canUse() {
	         if (SlavicGhost.this.getTarget() != null && !SlavicGhost.this.getMoveControl().hasWanted() && SlavicGhost.this.random.nextInt(reducedTickDelay(7)) == 0) {
	            return SlavicGhost.this.distanceToSqr(SlavicGhost.this.getTarget()) > 4.0D;
	         } else {
	            return false;
	         }
	      }

	      public boolean canContinueToUse() {
	         return SlavicGhost.this.getMoveControl().hasWanted() && SlavicGhost.this.isCharging() && SlavicGhost.this.getTarget() != null && SlavicGhost.this.getTarget().isAlive();
	      }

	      public void start() {
	         LivingEntity livingentity = SlavicGhost.this.getTarget();
	         if (livingentity != null) {
	            Vec3 vec3 = livingentity.getEyePosition();
	            SlavicGhost.this.moveControl.setWantedPosition(vec3.x, vec3.y, vec3.z, 1.0D);
	         }

	         SlavicGhost.this.setIsCharging(true);
	         SlavicGhost.this.playSound(SoundEvents.VEX_CHARGE, 1.0F, 1.0F);
	      }

	      public void stop() {
	    	  SlavicGhost.this.setIsCharging(false);
	      }

	      public boolean requiresUpdateEveryTick() {
	         return true;
	      }

	      public void tick() {
	         LivingEntity livingentity = SlavicGhost.this.getTarget();
	         if (livingentity != null) {
	            if (SlavicGhost.this.getBoundingBox().intersects(livingentity.getBoundingBox())) {
	            	SlavicGhost.this.doHurtTarget(livingentity);
	            	SlavicGhost.this.setIsCharging(false);
	            } else {
	               double d0 = SlavicGhost.this.distanceToSqr(livingentity);
	               if (d0 < 9.0D) {
	                  Vec3 vec3 = livingentity.getEyePosition();
	                  SlavicGhost.this.moveControl.setWantedPosition(vec3.x, vec3.y, vec3.z, 1.0D);
	               }
	            }

	         }
	      }
	   }

	
	

		   class WraithMoveControl extends MoveControl {
		      public WraithMoveControl(SlavicGhost p_34062_) {
		         super(p_34062_);
		      }

		      public void tick() {
		         if (this.operation == MoveControl.Operation.MOVE_TO) {
		            Vec3 vec3 = new Vec3(this.wantedX - SlavicGhost.this.getX(), this.wantedY - SlavicGhost.this.getY(), this.wantedZ - SlavicGhost.this.getZ());
		            double d0 = vec3.length();
		            if (d0 < SlavicGhost.this.getBoundingBox().getSize()) {
		               this.operation = MoveControl.Operation.WAIT;
		               SlavicGhost.this.setDeltaMovement(SlavicGhost.this.getDeltaMovement().scale(0.5D));
		            } else {
		            	SlavicGhost.this.setDeltaMovement(SlavicGhost.this.getDeltaMovement().add(vec3.scale(this.speedModifier * 0.01D / d0)));
		               if (SlavicGhost.this.getTarget() == null) {
		                  Vec3 vec31 = SlavicGhost.this.getDeltaMovement();
		                  SlavicGhost.this.setYRot(-((float)Mth.atan2(vec31.x, vec31.z)) * (180F / (float)Math.PI));
		                  SlavicGhost.this.yBodyRot = SlavicGhost.this.getYRot();
		               } else {
		                  double d2 = SlavicGhost.this.getTarget().getX() - SlavicGhost.this.getX();
		                  double d1 = SlavicGhost.this.getTarget().getZ() - SlavicGhost.this.getZ();
		                  SlavicGhost.this.setYRot(-((float)Mth.atan2(d2, d1)) * (180F / (float)Math.PI));
		                  SlavicGhost.this.yBodyRot = SlavicGhost.this.getYRot();
		               }
		            }

		         }
		      }
		   }
	   
	   
	   
	
	
	
	
	
	
	
	
	
	
	
	
}