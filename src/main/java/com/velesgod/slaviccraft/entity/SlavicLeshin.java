package com.velesgod.slaviccraft.entity;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Nullable;

import org.checkerframework.common.returnsreceiver.qual.This;

import com.velesgod.slaviccraft.api.IdolUtils;
import com.velesgod.slaviccraft.core.init.ItemInit;
import com.velesgod.slaviccraft.core.init.ParticleInit;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.client.renderer.entity.layers.WitchItemLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
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
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.ItemStack;
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

public class SlavicLeshin extends PathfinderMob implements IAnimatable, IAnimationTickable,NeutralMob {
	private AnimationFactory factory = new AnimationFactory(this);

	 protected static final EntityDataAccessor<Byte> STATE = SynchedEntityData.defineId(SlavicLeshin.class, EntityDataSerializers.BYTE);
	 protected static final EntityDataAccessor<Integer> COUNTER = SynchedEntityData.defineId(SlavicLeshin.class, EntityDataSerializers.INT);

	
	public int onAttack = -1;
	
	
	
	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
	
		
		if (getState() == -1) {
			this.setNoAi(false);
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.leshiy.idle2", true));
			return PlayState.CONTINUE;
		}
		
		
		
		
		if ((Math.abs(this.getDeltaMovement().x())>0)||(Math.abs(this.getDeltaMovement().z())>0)) {
			if(this.isAggressive()) {
				this.setNoAi(false);
		
				event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.leshiy.run", true));
				return PlayState.CONTINUE;
			}else {
			this.setNoAi(false);
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.leshiy.walk", true));
			return PlayState.CONTINUE;
			}
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
	
	//	System.out.println(level.isClientSide());
		    
		if (getState() > 0) {
		//	if(level.getRandom().nextInt(2) == 0)
		//	event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.leshiy.attack3", true));
		//	else
		//	event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.leshiy.attack", true));
		//	event.getController().setAnimationSpeed(1.5d);
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

	
    
    public static List<Entity> getEnts(Entity player, double radius) {
    	
    	List<Entity> entities = 
    			player.level.getEntities(player, new AABB(player.getX() - radius, 
    					player.getY() - radius, 
    					player.getZ() - radius, 
    					player.getX() + radius, 
    					player.getY() + radius, 
    					player.getZ() + radius));
        return entities;
    }
	
	
	public void alertAll() {
		for(Entity ent:getEnts(this,60)) {
			if(ent instanceof Bee || ent instanceof Spider || ent instanceof SlavicRaven || ent instanceof Wolf) {
				((Mob)ent).addEffect(new MobEffectInstance(MobEffects.LEVITATION));
				((Mob)ent).setAggressive(true);
				((Mob)ent).setTarget(this.getTarget());

				System.out.println("asd");
			}
		}
	}
	
	@Override
	protected void registerGoals() {
		  
		  this.goalSelector.addGoal(1, new LeshinAttackGoal(this, 1.0D, true));
		  this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 6.0F));

	      
	      this.targetSelector.addGoal(2, (new HurtByTargetGoal(this)).setAlertOthers());
	      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
	      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, 10, true, false, this::isAngryAt));
	      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, 10, true, false, this::isAngryAt));
	      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, SnowGolem.class, 10, true, false, this::isAngryAt));
	      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Pillager.class, 10, true, false, this::isAngryAt));
	      this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<>(this, true));
	      
	      
	     // this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
		  this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0D));

		    
		super.registerGoals();
	}

	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		super.tick();

	      
			if(getState() == -1) {
				if(getCounter() == 100) {
		    		  itemTrade.setNeverPickUp();
		    		  itemTrade.setAirSupply(0);
		    		  itemTrade.setNoGravity(true);
				}
				
				
				this.setDeltaMovement(new Vec3(0,0,0));
				this.lookControl.setLookAt(itemTrade.getX(), itemTrade.getY()+1.5f, itemTrade.getZ());
				
				
				double dX = this.getX()-itemTrade.getX();//вектор , колинеарный прямой, которая пересекает спрайт и курсор
				double dZ = this.getZ()-itemTrade.getZ();//он же, координата y
				float rotation = (float) (Math.atan2(dZ, dX) * 180 / 3.14159265);//получаем угол в радианах и переводим его в градусы
				System.out.println(rotation);
				
				this.setYBodyRot(rotation+90);
				
			      Random random = new Random();
						Vec3 pos = itemTrade.getPosition(1);
						  double d0 = (double)0.03125D;
					      double d1 = (double)0.13125F;
					      double d2 = (double)0.7375F;
				
					         double d3 = random.nextGaussian() * 0.1D;
					         double d4 = random.nextGaussian() * 0.1D;
					         double d5 = random.nextGaussian() * 0.1D;
					         level.addParticle(ParticleTypes.EFFECT, 
					        		 (double)pos.x()+2*random.nextDouble()-1d, 
					        		 (double)pos.y()+2*random.nextDouble()-1d, 
					        		 (double)pos.z()+2*random.nextDouble()-1d, 
					        		 0, 0.0001, 0);

				
				
				
				
				
				
				
				
				
				if(getCounter() <=0) {
					setState(0); 
					
				
				
					itemTrade.setNoGravity(false);
					ItemStack stack = itemTrade.getItem();
				
					if(random.nextDouble() < 0.25) {
						if(random.nextDouble() < 0.25) {
							if(random.nextDouble() < 0.25) {
								stack = new ItemStack(ItemInit.CHRYSANTHS_FRESH.get(),2);
							}else {
								stack = new ItemStack(ItemInit.FIREWHIP_FRESH.get(),2);
							}
						}else {
							stack = new ItemStack(Items.MOSS_BLOCK,2);
						}
					}else {
						stack = new ItemStack(Items.BIRCH_SAPLING,2);
					}
					stack.setTag(new CompoundTag());
					itemTrade.setItem(stack);
					itemTrade.setDefaultPickUpDelay();
					
					itemTrade.setDeltaMovement(
							(this.lookControl.getWantedX() - this.getX()) * 0.12f, 
							0.05f, 
							(this.lookControl.getWantedZ() - this.getZ() )* 0.12f);
					
			
					
			
				}else {
					setCounter(getCounter()-1);
					
				  itemTrade.setDeltaMovement(0.f, 0.01f, 0.f);
				  
				}
				
			}
			
			      
			      if(!isAggressive() && getState() != -1)
			      for(Entity ent: getEnts(this, 3)) {
			    	  if(ent.isOnGround())
			    	  if(ent instanceof ItemEntity) {
			    		  ItemEntity item = ((ItemEntity)ent);
			    		  if(item.getItem().getItem() == ItemInit.AMBER.get()) {
			    		  setState(-1);
			    		  setCounter(100);
			    		  itemTrade = item;
			
			    		  }
			    	  }
			      }
			 
			    
				  
			    	//  System.out.println(getState());
			      
			      Random random = new Random();
					Vec3 pos = this.getPosition(1);
					  double d0 = (double)0.03125D;
				      double d1 = (double)0.13125F;
				      double d2 = (double)0.7375F;
			
				         double d3 = random.nextGaussian() * 0.02D;
				         double d4 = random.nextGaussian() * 0.02D;
				         double d5 = random.nextGaussian() * 0.02D;
				         level.addParticle(ParticleTypes.MYCELIUM, 
				        		 (double)pos.x() + (double)0.13125F + (double)0.7375F-random.nextDouble(), 
				        		 (double)pos.y()+ 2*(double)0.13125F + (double)0.7375F-random.nextDouble(), 
				        		 (double)pos.z() + (double)0.13125F + (double)0.7375F-random.nextDouble(), 
				        		 0, 0.0001, 0);

		
		
		
	}

	
		ItemEntity itemTrade;
	   public void aiStep() {
		  if(tickCount < 1.6*22)
			  this.setDeltaMovement(new Vec3(0,0,0));
		   super.aiStep();
		   if(isAggressive())
				this.getNavigation().setSpeedModifier(3.3f);
		   else
				this.getNavigation().setSpeedModifier(1f);
		   
		  }
		@Override
		  protected void defineSynchedData() {
		      super.defineSynchedData();
		      this.entityData.define(STATE, (byte)0);
		      this.entityData.define(COUNTER, (int)0);
		   }
		
		
		
		

		public int getState() {
		      return this.entityData.get(STATE);
		}
		
		
		   public void setState(int flag) {
		  

		      this.entityData.set(STATE, (byte)(flag));
		   }
		
		
			public int getCounter() {
			      return this.entityData.get(COUNTER);
			}
			
			
			   public void setCounter(int flag) {
			  

			      this.entityData.set(COUNTER, (int)(flag));
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
	
	
	
	

	public class LeshinAttackGoal extends MeleeAttackGoal {
			SlavicLeshin lesh;

		   public LeshinAttackGoal(SlavicLeshin p_26019_, double p_26020_, boolean p_26021_) {
		      super(p_26019_, p_26020_, p_26021_);
		      this.lesh = p_26019_;
		   }

		   public void start() {
		      super.start();
		    
		     
		   }

		   public void stop() {
		      super.stop();
	
		   }

		   
		      protected void checkAndPerformAttack(LivingEntity p_29589_, double p_29590_) {
		    	  super.checkAndPerformAttack(p_29589_, p_29590_);
		    	  /*
		          double d0 = this.getAttackReachSqr(p_29589_);
		          
		          if (p_29590_ <= d0 && this.isTimeToAttack()) {
		             this.resetAttackCooldown();
		             this.mob.doHurtTarget(p_29589_);
		             
		         //    PolarBear.this.setStanding(false);
		          } else if (p_29590_ <= d0 * 2.0D) {
		             if (this.isTimeToAttack()) {
		           //     PolarBear.this.setStanding(false);
		                this.resetAttackCooldown();
		             }

		             if (this.getTicksUntilNextAttack() <= 20) {
		            	 lesh.setState(3);
		          //      PolarBear.this.setStanding(true);
		         //       PolarBear.this.playWarningSound();
		             }
		          } else {
		             this.resetAttackCooldown();
		          //   PolarBear.this.setStanding(false);
		          }
		    	   	*/
		       }
		   
		   public void tick() {
			   
			//   LivingEntity livingentity = this.mob.getTarget();
			//      if (livingentity != null) 
			//         this.mob.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
			//         double d0 = this.mob.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
			   
			   
		  //  if(this.getTicksUntilNextAttack()==20)
		  //  	if(lesh.getState()==0) {
		   // 		     lesh.setState(30);
		  //  		     this.checkAndPerformAttack(lesh, d0);
		  //  	}
		 
		 // if(lesh.getState()>0)lesh.setState(lesh.getState()-1);
		 //-----------------------
		   super.tick();
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   }
		   
		   
		}



	
	 private static final UniformInt ALERT_INTERVAL = TimeUtil.rangeOfSeconds(4, 6);
	   private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
	   private int remainingPersistentAngerTime;
	   @Nullable
	   private UUID persistentAngerTarget;
	   private int ticksUntilNextAlert;
	   private static final UniformInt FIRST_ANGER_SOUND_DELAY = TimeUtil.rangeOfSeconds(0, 1);
	   private int playFirstAngerSoundIn;


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
	
	

	
	
	
	
	
	
	
	
}