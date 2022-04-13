package com.velesgod.slaviccraft.entity;

import java.util.EnumSet;
import java.util.UUID;

import javax.annotation.Nullable;

import com.velesgod.slaviccraft.core.init.ParticleInit;
import com.velesgod.slaviccraft.entity.SlavicGhost.WraithMoveControl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowMobGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LandOnOwnersShoulderGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
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

public class SlavicRaven extends Animal implements IAnimatable, IAnimationTickable,NeutralMob {
	private AnimationFactory factory = new AnimationFactory(this);

	   private static final UniformInt ALERT_INTERVAL = TimeUtil.rangeOfSeconds(4, 6);
	   private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
	   private int remainingPersistentAngerTime;
	   @Nullable
	   private UUID persistentAngerTarget;
	   private int ticksUntilNextAlert;
	   private static final UniformInt FIRST_ANGER_SOUND_DELAY = TimeUtil.rangeOfSeconds(0, 1);
	   private int playFirstAngerSoundIn;
	   
	   
	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if(this.onGround) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.raven.idle", true));
			return PlayState.CONTINUE;
		}
		if(!this.onGround) {
		event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.raven.fly", true));
		return PlayState.CONTINUE;
		}
		return PlayState.CONTINUE;
	}

	public SlavicRaven(EntityType<? extends Animal> type, Level worldIn) {
		super(type, worldIn);
		  this.moveControl = new RavenMoveControl(this);
		this.noCulling = true;
	}

	@Override
	public void registerControllers(AnimationData data) {
		AnimationController<SlavicRaven> controller = new AnimationController<>(this, "controller", 0, this::predicate);
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


	   public static AttributeSupplier.Builder createMobAttributes() {
		      return Parrot.createMobAttributes()
		    		  .add(Attributes.MAX_HEALTH, 6.0D)
		    		  .add(Attributes.FLYING_SPEED, (double)0.3F)
		    		  .add(Attributes.MOVEMENT_SPEED, (double)0.7F)
		    		  .add(Attributes.ATTACK_DAMAGE, 1.0D);
		   }

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	
	@Override
	public void tick() {
	    //  this.noPhysics = true;
	      super.tick();
	    //  this.noPhysics = false;
	      if(this.getTarget() != null )
	    	  this.setNoGravity(true);
	      else {
	      this.setNoGravity(false);
	      if(!onGround) {
	    	Vec3 vec3 = this.getDeltaMovement();
	      if (!this.onGround && vec3.y < 0.0D) {
	         this.setDeltaMovement(vec3.multiply(1.0D, 0.6D, 1.0D));
	      }
	      }
	      }
	   
	}
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
	      this.goalSelector.addGoal(4, new RavenAttackGoal());
	      this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
	      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
	      this.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal<>(this, true));
	
	}

	@Override
	 public boolean causeFallDamage(float p_148989_, float p_148990_, DamageSource p_148991_) {
	      return false;
	   }

	@Override
	public int tickTimer() {
		return tickCount;
	}

	   public void startPersistentAngerTimer() {
		      this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
		   }

	   public void setPersistentAngerTarget(@Nullable UUID p_34444_) {
		      this.persistentAngerTarget = p_34444_;
		   }

	   @Nullable
	   public UUID getPersistentAngerTarget() {
	      return this.persistentAngerTarget;
	   }
	   
	   
	   public void setRemainingPersistentAngerTime(int p_34448_) {
		      this.remainingPersistentAngerTime = p_34448_;
		   }

		   public int getRemainingPersistentAngerTime() {
		      return this.remainingPersistentAngerTime;
		   }
	   @Override
	protected void customServerAiStep() {
		   this.updatePersistentAnger((ServerLevel)this.level, true);
		    if (this.getTarget() != null) {
		       this.maybeAlertOthers();
		    }
		super.customServerAiStep();
	}

	   private void maybeAlertOthers() {
		      if (this.ticksUntilNextAlert > 0) {
		         --this.ticksUntilNextAlert;
		      } else {
		         if (this.getSensing().hasLineOfSight(this.getTarget())) {
		            this.alertOthers();
		         }

		         this.ticksUntilNextAlert = ALERT_INTERVAL.sample(this.random);
		      }
		   }
  	   @Override
	   public void setTarget(@Nullable LivingEntity p_34478_) {
		      if (this.getTarget() == null && p_34478_ != null) {
		         this.playFirstAngerSoundIn = FIRST_ANGER_SOUND_DELAY.sample(this.random);
		         this.ticksUntilNextAlert = ALERT_INTERVAL.sample(this.random);
		      }

		      if (p_34478_ instanceof Player) {
		         this.setLastHurtByPlayer((Player)p_34478_);
		      }

		      super.setTarget(p_34478_);
		   }
  	   
  	   
	   private void alertOthers() {
		      double d0 = this.getAttributeValue(Attributes.FOLLOW_RANGE);
		      AABB aabb = AABB.unitCubeFromLowerCorner(this.position()).inflate(d0, 10.0D, d0);
		      this.level.getEntitiesOfClass(SlavicRaven.class, aabb, EntitySelector.NO_SPECTATORS).stream().filter((p_34463_) -> {
		         return p_34463_ != this;
		      }).filter((p_34461_) -> {
		         return p_34461_.getTarget() == null;
		      }).filter((p_34456_) -> {
		         return !p_34456_.isAlliedTo(this.getTarget());
		      }).forEach((p_34440_) -> {
		         p_34440_.setTarget(this.getTarget());
		      });
		   }

	@Override
	public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	

	   class RavenAttackGoal extends Goal {
	      public RavenAttackGoal() {
	         this.setFlags(EnumSet.of(Goal.Flag.MOVE));
	      }

	      public boolean canUse() {
	         if (SlavicRaven.this.getTarget() != null && !SlavicRaven.this.getMoveControl().hasWanted()) {
	            return true;//SlavicRaven.this.distanceToSqr(SlavicRaven.this.getTarget()) > 4.0D;
	         } else {
	            return false;
	         }
	      }

	      public boolean canContinueToUse() {
	         return SlavicRaven.this.getMoveControl().hasWanted()  && SlavicRaven.this.getTarget() != null && SlavicRaven.this.getTarget().isAlive();
	      }

	      public void start() {
	         LivingEntity livingentity = SlavicRaven.this.getTarget();
	         if (livingentity != null) {
	            Vec3 vec3 = livingentity.getEyePosition();
	            SlavicRaven.this.moveControl.setWantedPosition(vec3.x, vec3.y, vec3.z, 1.0D);
	         }

	         SlavicRaven.this.playSound(SoundEvents.VEX_CHARGE, 1.0F, 1.0F);
	      }

	      public void stop() {
	    	
	      }

	      public boolean requiresUpdateEveryTick() {
	         return true;
	      }

	      public void tick() {
	         LivingEntity livingentity = SlavicRaven.this.getTarget();
	       
	         if (livingentity != null) {
	        	 
	            if (SlavicRaven.this.getBoundingBox().intersects(livingentity.getBoundingBox())) {
	             	SlavicRaven.this.doHurtTarget(livingentity);
	             	 System.out.println(SlavicRaven.this.getBoundingBox().intersects(livingentity.getBoundingBox()));
	 	        	
	         
	            } else {
	               double d0 = SlavicRaven.this.distanceToSqr(livingentity);
	               if (d0 < 6.0D) {
	                  Vec3 vec3 = livingentity.getEyePosition();
	                  SlavicRaven.this.moveControl.setWantedPosition(vec3.x, vec3.y, vec3.z, 3.0D);
	               }
	            }
	          
	         }
	      }
	   }

	
	
	   class RavenMoveControl extends MoveControl {
		      public RavenMoveControl(SlavicRaven p_34062_) {
		         super(p_34062_);
		      }

		      public void tick() {
		   
		         if (this.operation == MoveControl.Operation.MOVE_TO) {
		            Vec3 vec3 = new Vec3(
		            		this.wantedX - SlavicRaven.this.getX(), 
		            		this.wantedY - SlavicRaven.this.getY(), 
		            		this.wantedZ - SlavicRaven.this.getZ());
		            double d0 = vec3.length();
		            if (d0 < SlavicRaven.this.getBoundingBox().getSize()) {
		               this.operation = MoveControl.Operation.WAIT;
		               SlavicRaven.this.setDeltaMovement(SlavicRaven.this.getDeltaMovement().scale(0.1));
		            } else {
		            	SlavicRaven.this.setDeltaMovement(SlavicRaven.this.getDeltaMovement().add(vec3.scale(this.speedModifier * 0.01D / d0)));
		               if (SlavicRaven.this.getTarget() == null) {
		                  Vec3 vec31 = SlavicRaven.this.getDeltaMovement();
		                  SlavicRaven.this.setYRot(-((float)Mth.atan2(vec31.x, vec31.z)) * (180F / (float)Math.PI));
		                  SlavicRaven.this.yBodyRot = SlavicRaven.this.getYRot();
		               } else {
		                  double d2 = SlavicRaven.this.getTarget().getX() - SlavicRaven.this.getX();
		                  double d1 = SlavicRaven.this.getTarget().getZ() - SlavicRaven.this.getZ();
		                  SlavicRaven.this.setYRot(-((float)Mth.atan2(d2, d1)) * (180F / (float)Math.PI));
		                  SlavicRaven.this.yBodyRot = SlavicRaven.this.getYRot();
		               }
		            }

		         }
		      }
		   }
	   
	   
	
	
	
	
	
	
	
	
	
	   
}