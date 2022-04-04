package com.velesgod.slaviccraft.entity;

import java.util.Random;

import org.checkerframework.common.returnsreceiver.qual.This;

import com.velesgod.slaviccraft.core.init.ParticleInit;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveThroughVillageGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
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

public class SlavicLeshin extends PathfinderMob implements IAnimatable, IAnimationTickable {
	private AnimationFactory factory = new AnimationFactory(this);

	public int onAttack = -1;
	
	
	
	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
	
		if ((Math.abs(this.getDeltaMovement().x())>0)||(Math.abs(this.getDeltaMovement().z())>0)) {
			this.setNoAi(false);
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.leshiy.walk", true));
			return PlayState.CONTINUE;
		
		}
		
	
		
		if (this.tickCount < 1.5*20) {
			this.setNoAi(true);
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.leshiy.appear", true));
			return PlayState.CONTINUE;
		
		}
		
		
		if (this.tickCount > 1.5*20) {
			this.setNoAi(false);
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.leshiy.idle", true));
			return PlayState.CONTINUE;
		}
		
	
		
		return PlayState.STOP;
	}
	
	
	
	private <E extends IAnimatable> PlayState predicate1(AnimationEvent<E> event) {
	
		System.out.println(level.isClientSide());
		    
		if (this.isAggressive() && onAttack>0) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.leshiy.roar", true));
			return PlayState.CONTINUE;
		
		}
	
		return PlayState.STOP;
	}
	
	

	public void setOnAttack() {
		onAttack = 500;
	
	}
	public SlavicLeshin(EntityType<? extends PathfinderMob> type, Level worldIn) {
		super(type, worldIn);
		this.noCulling = true;
	}

	@Override
	public void registerControllers(AnimationData data) {
		AnimationController<SlavicLeshin> controller1 = new AnimationController<>(this, "controller1", 0, this::predicate1);
		AnimationController<SlavicLeshin> controller = new AnimationController<>(this, "controller", 0, this::predicate);
		controller.registerCustomInstructionListener(this::customListener);
		controller1.registerCustomInstructionListener(this::customListener1);
		data.addAnimationController(controller);
		data.addAnimationController(controller1);
	}

	@SuppressWarnings("resource")
	private <ENTITY extends IAnimatable> void customListener(CustomInstructionKeyframeEvent<ENTITY> event) {
		final LocalPlayer player = Minecraft.getInstance().player;
		if (player != null) {
			player.displayClientMessage(new TextComponent("KeyFraming"), true);
		}
	}

	@SuppressWarnings("resource")
	private <ENTITY extends IAnimatable> void customListener1(CustomInstructionKeyframeEvent<ENTITY> event) {
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
	protected void registerGoals() {
		
		  this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));
		  this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 6.0F));
		  this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
	      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
	      this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
	      this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, SnowGolem.class, true));
	      this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, Pillager.class, true));
	      this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
		  this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0D));

		    
		super.registerGoals();
	}

	

	
	
	   public void aiStep() {
		      super.aiStep();
		   
		      double dist  =0;
		      if(getTarget() != null) 
		    	   dist = getTarget().getPosition(1.f).distanceTo(this.getPosition(1.f));
		      
		    	  if(dist<=1.2)
		    		  onAttack  =1;
		    	  else
		    		  onAttack  =0;
		    
		    
			  
System.out.println(onAttack);
		      
		      Random random = new Random();
				Vec3 pos = this.getPosition(1);
				  double d0 = (double)0.03125D;
			      double d1 = (double)0.13125F;
			      double d2 = (double)0.7375F;
			  //  for(int i = 0; i < 3; ++i) {
			         double d3 = random.nextGaussian() * 0.02D;
			         double d4 = random.nextGaussian() * 0.02D;
			         double d5 = random.nextGaussian() * 0.02D;
			         level.addParticle(ParticleTypes.MYCELIUM, 
			        		 (double)pos.x() + (double)0.13125F + (double)0.7375F-random.nextDouble(), 
			        		 (double)pos.y()+ 2*(double)0.13125F + (double)0.7375F-random.nextDouble(), 
			        		 (double)pos.z() + (double)0.13125F + (double)0.7375F-random.nextDouble(), 
			        		 0, 0.0001, 0);
			  //    }
		      
		      

		//      Vec3 vec3 = this.getDeltaMovement();
		//      if (!this.onGround && vec3.y < 0.0D) {
		//         this.setDeltaMovement(vec3.multiply(1.0D, 0.6D, 1.0D));
		//      }


		   }
	

	   public static AttributeSupplier.Builder createMobAttributes() {
	      return Monster.createMobAttributes()
	    		  .add(Attributes.MAX_HEALTH, 180.0D)
	    		  .add(Attributes.KNOCKBACK_RESISTANCE,2.0)
	    		  .add(Attributes.MOVEMENT_SPEED, 0.15D)
	    		  .add(Attributes.ATTACK_DAMAGE, 8.0D);
	   }
	
	
	@Override
	public int tickTimer() {
		
		return tickCount;
	}
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
}