package com.velesgod.slaviccraft.entity;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Nullable;

import org.checkerframework.common.returnsreceiver.qual.This;

import com.mojang.math.Vector3f;
import com.velesgod.slaviccraft.api.IdolUtils;
import com.velesgod.slaviccraft.core.init.ItemInit;
import com.velesgod.slaviccraft.core.init.ParticleInit;
import com.velesgod.slaviccraft.core.init.SoundInit;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.client.renderer.entity.layers.WitchItemLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.BossEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
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
import net.minecraft.world.entity.item.FallingBlockEntity;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
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
	private static final UniformInt ALERT_INTERVAL = TimeUtil.rangeOfSeconds(4, 6);
	private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
	private int remainingPersistentAngerTime;
	@Nullable
	private UUID persistentAngerTarget;
	private int ticksUntilNextAlert;
	private static final UniformInt FIRST_ANGER_SOUND_DELAY = TimeUtil.rangeOfSeconds(0, 1);
	private int playFirstAngerSoundIn;
	private final ServerBossEvent bossInfo = new ServerBossEvent(new TranslatableComponent("entity.slaviccraft.slavicleshin"), BossEvent.BossBarColor.GREEN, BossEvent.BossBarOverlay.PROGRESS);

	
	protected static final EntityDataAccessor<Byte> STATE = SynchedEntityData.defineId(SlavicLeshin.class, EntityDataSerializers.BYTE);
	protected static final EntityDataAccessor<Integer> COUNTER = SynchedEntityData.defineId(SlavicLeshin.class, EntityDataSerializers.INT);

	protected static final EntityDataAccessor<Integer> ANIMATION = SynchedEntityData.defineId(SlavicLeshin.class, EntityDataSerializers.INT);
	enum LeshinAnim{
	    IDLE(0),
	    WALK(1),
	    BARTER(2),
	    RUN(3),
	    ATTACK(4),
	    MAGIC(5),
	    STRIKE(6),
	    APPEAR(7),
	    ROAR(8);
		int index;
	    LeshinAnim(int i) {this.index = i;}
	    int get() {return index;}
	}
	

	public SlavicLeshin(EntityType<? extends PathfinderMob> type, Level worldIn) {
		super(type, worldIn);
		this.noCulling = true;
	}

	public <E extends IAnimatable> PlayState setAnim(AnimationEvent<E> event,String name,PlayState state) {
		event.getController().setAnimation(new AnimationBuilder().addAnimation(name, true));
		return state;
	}
	
	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
	
	
		if(getAnim() == LeshinAnim.WALK.get()) return setAnim(event, "animation.leshiy.walk",  PlayState.CONTINUE);
		if(getAnim() == LeshinAnim.ROAR.get()) return setAnim(event, "animation.leshiy.roar",  PlayState.CONTINUE);
		if(getAnim() == LeshinAnim.RUN.get() || getAnim() == LeshinAnim.ATTACK.get()) return setAnim(event, "animation.leshiy.run", PlayState.CONTINUE);
		if(getAnim() == LeshinAnim.APPEAR.get()) return setAnim(event, "animation.leshiy.appear", PlayState.CONTINUE);
		if(getAnim() == LeshinAnim.BARTER.get()) return setAnim(event, "animation.leshiy.idle2", PlayState.CONTINUE);
		if(getAnim() == LeshinAnim.IDLE.get()) return setAnim(event, "animation.leshiy.idle", PlayState.CONTINUE);
		if(getAnim() == LeshinAnim.STRIKE.get()) return setAnim(event, "animation.leshiy.groundstrike", PlayState.CONTINUE);
		if(getAnim() == LeshinAnim.MAGIC.get()) return setAnim(event, "animation.leshiy.magic", PlayState.CONTINUE);
		
		return PlayState.STOP;
	}
	
	
	
	private <E extends IAnimatable> PlayState predicate1(AnimationEvent<E> event) {
		if(getAnim() == LeshinAnim.ATTACK.get()) return setAnim(event, "animation.leshiy.attack", PlayState.CONTINUE);
		return PlayState.STOP;
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
		  
		  this.goalSelector.addGoal(1, new LeshinAttackGoal(this, 1.0D, true));

	      
	      this.targetSelector.addGoal(2, (new HurtByTargetGoal(this)).setAlertOthers());
	      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
	      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, 10, true, false, this::isAngryAt));
	      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, 10, true, false, this::isAngryAt));
	      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, SnowGolem.class, 10, true, false, this::isAngryAt));
	      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Pillager.class, 10, true, false, this::isAngryAt));
	      this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<>(this, true));
	      
		  this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0D));

		    
		super.registerGoals();
	}

	public boolean isSecondPhase() {
		return (this.getHealth()/this.getMaxHealth())<=0.5f;
	}
	
	
public static ItemStack[] BarterList = {
	new ItemStack(Items.BIRCH_SAPLING,2),
	new ItemStack(Items.DARK_OAK_SAPLING,2),
	new ItemStack(Items.OAK_SAPLING,2),
	new ItemStack(Items.SPRUCE_SAPLING,2),
	new ItemStack(Blocks.MOSS_BLOCK,2),
	new ItemStack(Blocks.MOSS_CARPET,2),
	new ItemStack(Blocks.MOSSY_COBBLESTONE,2),
	new ItemStack(Blocks.DANDELION,3),
	new ItemStack(Blocks.POPPY,3),
	new ItemStack(Blocks.BLUE_ORCHID,3),
	new ItemStack(Blocks.ALLIUM,3),
	new ItemStack(Blocks.CORNFLOWER,3),
	new ItemStack(Blocks.OXEYE_DAISY,3),
	new ItemStack(Blocks.LILY_OF_THE_VALLEY,3),
	new ItemStack(Blocks.AZURE_BLUET,3),
	new ItemStack(Blocks.AZALEA,1),
	new ItemStack(Blocks.AZALEA_LEAVES,2),
	new ItemStack(Blocks.FLOWERING_AZALEA,1),
	new ItemStack(ItemInit.RAVENEYE_FRESH.get(),2),
	new ItemStack(ItemInit.FIREWHIP_FRESH.get(),2),
	new ItemStack(ItemInit.CHRYSANTHS_FRESH.get(),2),
	new ItemStack(ItemInit.HENBANE_FRESH.get(),2),
	new ItemStack(ItemInit.SWORDBLADE_FRESH.get(),2),
	new ItemStack(ItemInit.HEMLOCK_FRESH.get(),2),
	new ItemStack(ItemInit.HELLEBORE_FRESH.get(),2)
};
	
	public ItemStack getRandomBarter() {
		return BarterList[level.getRandom().nextInt(BarterList.length)];
	}
	
	
	
	
	
	
	
	
	
	
	ItemEntity itemTrade;
	@Override
	public void tick() {
		super.tick();
		bossInfo.setProgress(this.getHealth()/this.getMaxHealth());
		if(!isAggressive()) {
			setNoAi(false);
		    for(Entity ent: getEnts(this, 3)) {
		    	  if(ent.isOnGround())
		    	  if(ent instanceof ItemEntity) {
		    		  ItemEntity item = ((ItemEntity)ent);
		    		  if(item.getItem().getItem() == ItemInit.AMBER.get()) {
		    		  setState(1);
		    		  setCounter(100);
		    		  itemTrade = item;
		     		  itemTrade.setNeverPickUp();
		    		  itemTrade.setAirSupply(0);
		    		  itemTrade.setNoGravity(true);
		    		  for(Player p: level.players()) {
						level.playSound(p, this,SoundInit.L_BARTER.get(), SoundSource.MASTER, 5.f * 1/p.distanceTo(this), 1.f);
		    		  }    		  
		    		}
		    	 }
		      }
			
			
			
			
			
			
			if(getState() == 1) {
				
				if(getCounter() > 0 && itemTrade != null) {
					
					this.setAnim(LeshinAnim.BARTER.get());
					this.setDeltaMovement(new Vec3(0,0,0));
					this.lookControl.setLookAt(itemTrade.getX(), itemTrade.getY()+1.5f, itemTrade.getZ());
					
					double dX = this.getX()-itemTrade.getX();
					double dZ = this.getZ()-itemTrade.getZ();
					
					float rotation = (float) (Math.atan2(dZ, dX) * 180 / 3.14159265);
					this.setYBodyRot(rotation+90);
					this.setYHeadRot(rotation+90);
					addMagicParticles(ParticleTypes.EFFECT);
				
					itemTrade.setDeltaMovement(0.f, 0.01f, 0.f); 
					setCounter(getCounter()-1);
				}else {
					setState(0);
					
				
					ItemStack stack = getRandomBarter();
					stack.setTag(new CompoundTag());
					itemTrade.setNoGravity(false);
					itemTrade.setItem(stack);
					itemTrade.setDefaultPickUpDelay();
					itemTrade.setDeltaMovement( (this.lookControl.getWantedX() - this.getX()) * 0.12f, 0.05f, (this.lookControl.getWantedZ() - this.getZ() )* 0.12f);
					
				}
				
			}else {
			if(Math.abs(this.getDeltaMovement().x+this.getDeltaMovement().z) > 0)
				setAnim(LeshinAnim.WALK.get());
			else
				setAnim(LeshinAnim.IDLE.get());
			}
		}else {
			if(isSecondPhase()) {
				setNoAi(true);
			
				setDeltaMovement(new Vec3(0,0,0));
				
				
			
				
				
				if(getCounter() > 0) {
					if(getCounter() > 590) {
						
						setAnim(LeshinAnim.STRIKE.get());
					} else
					if(getCounter() > 10) {
						
							//if(getCounter() % 60 < 40) 
								createParticles(this);
							if(isOnFire()) this.clearFire();
							System.out.println(getCounter());
							if(getTarget() != null) summonLighting(getTarget());
							setAnim(LeshinAnim.MAGIC.get());
					} else
					{
						
					setAnim(LeshinAnim.BARTER.get());
					} 
					setCounter(getCounter()-1);
				}else setCounter(600);
				
				
				
			}else {
				setNoAi(false);
				if(getCounter() > 0) {
					setDeltaMovement(new Vec3(0,0,0));
					setAnim(LeshinAnim.ROAR.get());
					setCounter(getCounter()-1);
				}else {
					this.getNavigation().setSpeedModifier(2.2f);
				}
			}
		}
		addAmbientParticles(ParticleTypes.MYCELIUM);	
	}
	

	
	public void poisonedBreathe() {
		
	}
	
	public void setWave(BlockPos pos) {
		
	}
	
	public void summonLighting(LivingEntity target) {
		
		if(level.getRandom().nextDouble() > 0.93D) {
			LightningBolt bolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
			if(level.getRandom().nextDouble() > 0.9D)
			bolt.moveTo(target.getX()+level.getRandom().nextInt(10)-5,target.getY()-1,target.getZ()+level.getRandom().nextInt(10)-5);
			else
			bolt.moveTo(target.getX()+level.getRandom().nextInt(20)-10,target.getY()-1,target.getZ()+level.getRandom().nextInt(20)-10);
			level.addFreshEntity(bolt);
		}
	}
	
	
	
	
	public void createParticles(LivingEntity pos) {
     double d0 = pos.getX();
     double d1 = pos.getY();//+3.9;
     double d2 = pos.getZ();

    double angle = 0;
   	float c = getCounter();
     for(float i = 180; i > 0; i -= 6.125/2)
     {
  
     	angle = i*2+c;
     	
     
       
           double x1 = level.getRandom().nextDouble()*1.0f*  Math.cos(angle * Math.PI / 180);
           double z1 = level.getRandom().nextDouble()*1.0f* Math.sin(angle * Math.PI / 180);

         //  for(float j = 3; j > 0; j -= 0.5f)
         //  level.addParticle(
        //		new DustColorTransitionOptions(new Vector3f(0.f,1.f,0.f), 
        //				new Vector3f(0.f,0.f,1.f), 1.f)
        //		,d0+x1, d1, d2+z1, -x1*50.2,1.6, -z1*5.2);
           
           level.addParticle(
           		ParticleTypes.WAX_OFF
           		,d0+x1, d1+level.getRandom().nextDouble()+5, d2+z1, x1*10,40.5, z1*10);


           //sneezw
     }
 	 for(Player p: this.level.players()) {
 		 if(p.distanceTo(pos) < 2)
 		 this.doHurtTarget(p);
 	 }
 }
	
	
	
	
	@Override
	public void aiStep() {
		   super.aiStep();

	}
	   
	   
	@Override
	protected void defineSynchedData() {
		      super.defineSynchedData();
		      this.entityData.define(ANIMATION, (int)0);
		      this.entityData.define(STATE, (byte)0);
		      this.entityData.define(COUNTER, (int)0);
	}
		
		
		
		
		public int getAnim() { return this.entityData.get(ANIMATION);}
		
		public void setAnim(int flag) { this.entityData.set(ANIMATION, (int)(flag));}

		public int getState() { return this.entityData.get(STATE);}
		
		public void setState(int flag) { this.entityData.set(STATE, (byte)(flag));}
		
		public int getCounter() { return this.entityData.get(COUNTER);}
		
		public void setCounter(int flag) { this.entityData.set(COUNTER, (int)(flag));}
	
		
		
	    public static AttributeSupplier.Builder createMobAttributes() {
	      return Monster.createMobAttributes()
	    		  .add(Attributes.MAX_HEALTH, 300.0D)
	    		  .add(Attributes.KNOCKBACK_RESISTANCE,2.0)
	    		  .add(Attributes.MOVEMENT_SPEED, 0.15D)
	    		  .add(Attributes.ATTACK_DAMAGE, 9.0D);
	   }
	
	    
	public void addMagicParticles(ParticleOptions op) {
	    Random random = new Random();
		Vec3 pos = itemTrade.getPosition(1);
		  double d0 = (double)0.03125D;
	      double d1 = (double)0.13125F;
	      double d2 = (double)0.7375F;

	         double d3 = random.nextGaussian() * 0.1D;
	         double d4 = random.nextGaussian() * 0.1D;
	         double d5 = random.nextGaussian() * 0.1D;
	         level.addParticle(op, 
	        		 (double)pos.x()+2*random.nextDouble()-1d, 
	        		 (double)pos.y()+2*random.nextDouble()-1d, 
	        		 (double)pos.z()+2*random.nextDouble()-1d, 
	        		 0, 0.0001, 0);

	}




	public void addAmbientParticles(ParticleOptions op) {
	      Random random = new Random();
						Vec3 pos = this.getPosition(1);
						  double d0 = (double)0.03125D;
					      double d1 = (double)0.13125F;
					      double d2 = (double)0.7375F;
				
					         double d3 = random.nextGaussian() * 0.02D;
					         double d4 = random.nextGaussian() * 0.02D;
					         double d5 = random.nextGaussian() * 0.02D;
					         level.addParticle(op, 
					        		 (double)pos.x() + (double)0.13125F + (double)0.7375F-random.nextDouble(), 
					        		 (double)pos.y()+ 2.7*(double)0.13125F + (double)0.7375F-random.nextDouble(), 
					        		 (double)pos.z() + (double)0.13125F + (double)0.7375F-random.nextDouble(), 
					        		 0, 0.0001, 0);
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
			protected void actuallyHurt(DamageSource p_21240_, float p_21241_) {
				alertAll();
				if(p_21240_ == DamageSource.LIGHTNING_BOLT)
				{
					
				}
				else
				super.actuallyHurt(p_21240_, p_21241_);
			}
			
			@Override
			public void onAddedToWorld() {
				for(Player p: level.players()) {
					if(p.distanceTo(this) < 32)
					bossInfo.addPlayer((ServerPlayer) p);
				}
				setState(-1);
				
				super.onAddedToWorld();
			}
			
			
			@Override
			public void onRemovedFromWorld() {
				bossInfo.removeAllPlayers();
				ItemEntity amul = new ItemEntity(EntityType.ITEM,level);
				amul.setItem(new ItemStack(ItemInit.LESHIN_AMULET.get(),1));
				amul.moveTo(this.getPosition(1.f));
				level.addFreshEntity(amul);
				super.onRemovedFromWorld();
			}
			
			 @Override
			public void killed(ServerLevel p_19929_, LivingEntity p_19930_) {
					bossInfo.removeAllPlayers();
					ItemEntity amul = new ItemEntity(EntityType.ITEM,level);
					amul.setItem(new ItemStack(ItemInit.LESHIN_AMULET.get(),1));
					amul.moveTo(this.getPosition(1.f));
					level.addFreshEntity(amul);
				super.killed(p_19929_, p_19930_);
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

					}
				}
			}
			
			
			
			public class LeshinAttackGoal extends MeleeAttackGoal {
				SlavicLeshin lesh;

			   public LeshinAttackGoal(SlavicLeshin p_26019_, double p_26020_, boolean p_26021_) {
			      super(p_26019_, p_26020_, p_26021_);
			      this.lesh = p_26019_;
			   }

			   public void start() {
			    super.start();
			    this.lesh.setAnim(LeshinAnim.ROAR.get());
			    this.lesh.setCounter(26*2);
		 
			   }

			   public void stop() {
			      super.stop();
					
			
				    bossInfo.removeAllPlayers();
			   }

			   
			      protected void checkAndPerformAttack(LivingEntity p_29589_, double p_29590_) {
			    	  super.checkAndPerformAttack(p_29589_, p_29590_);
			
			    	
			    	  if(this.lesh.getHealth()/this.lesh.getMaxHealth() > 0.5) {
			    		
					    	
				          double d0 = this.getAttackReachSqr(p_29589_);
				          if (p_29590_*0.5D <= d0 && this.isTimeToAttack()) {
				             this.resetAttackCooldown();
				             this.mob.doHurtTarget(p_29589_);
				        	
				           
				          } else if (p_29590_ <= d0*3.0) {
				             if (this.isTimeToAttack()) {
				                this.resetAttackCooldown();
				           
				                this.lesh.setAnim(LeshinAnim.RUN.get());
				             }
				       
				             if (this.getTicksUntilNextAttack() <= 20) {
				            	 this.lesh.setAnim(LeshinAnim.ATTACK.get());
				      
				            
				             }
				           
				          } else {
				             this.resetAttackCooldown();
				             this.lesh.setAnim(LeshinAnim.RUN.get());
				          }
				    	   	
			    	  }
			    	  
			    	 
			       }
			   
			   public void tick() {
				   super.tick();
			//	   System.out.println(this.lesh.level.isClientSide());
			//	   if(this.lesh.getHealth()/this.lesh.getMaxHealth() < 0.5) {
			//	
			//				double dX =  this.lesh.getX()- this.lesh.getTarget().getX();
			//				double dZ =  this.lesh.getZ()-this.lesh.getTarget().getZ();
			//				
			//				float rotation = (float) (Math.atan2(dZ, dX) * 180 / 3.14159265);
			//				 this.lesh.setYBodyRot(rotation+90);
			//				 this.lesh.setYHeadRot(rotation+90);
			//	
				//   }
			   }
}

		
			
				@Override
				protected SoundEvent getHurtSound(DamageSource p_21239_) {
					return SoundInit.L_HURT.get();
				}
	
				 @Override
			      protected SoundEvent getAmbientSound() {
				      return SoundInit.L_AMBIENT.get();
				   }

				@Override
				public int tickTimer() {
					
					return tickCount;
				}
			

	
	
	
}