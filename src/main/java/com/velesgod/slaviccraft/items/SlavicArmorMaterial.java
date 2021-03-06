package com.velesgod.slaviccraft.items;



import com.google.common.base.Supplier;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.RegistryObject;

public enum SlavicArmorMaterial implements ArmorMaterial {
   AMBER("amber_amulet", 8, new int[]{1,1,1,1},15, SoundEvents.ARMOR_EQUIP_LEATHER, 2.0F, 0.2F, null),
   DANDELION("dandelion_wreath", 8, new int[]{1,1,1,1}, 15, SoundEvents.ARMOR_EQUIP_LEATHER, 2.0F, 0.2F, null),
   AGILITY("agility_wreath_armor", 8, new int[]{1,1,1,1}, 15, SoundEvents.ARMOR_EQUIP_LEATHER, 2.0F, 0.2F, null),
   ENDURACE("endurance_wreath_armor", 8, new int[]{1,1,1,1}, 15, SoundEvents.ARMOR_EQUIP_LEATHER, 2.0F, 0.2F, null),
   WARDING("warding_wreath_armor", 8, new int[]{1,1,1,1}, 15, SoundEvents.ARMOR_EQUIP_LEATHER, 2.0F, 0.2F, null),
   FATE("fate_wreath_armor", 8, new int[]{1,1,1,1}, 15, SoundEvents.ARMOR_EQUIP_LEATHER, 2.0F, 0.2F, null),
   GROWTH("growth_wreath_armor", 8, new int[]{1,1,1,1}, 15, SoundEvents.ARMOR_EQUIP_LEATHER, 2.0F, 0.2F, null),
   MIGHT("might_wreath_armor", 8, new int[]{1,1,1,1}, 15, SoundEvents.ARMOR_EQUIP_LEATHER, 2.0F, 0.2F, null),
   PERCEPTION("perception_wreath_armor", 8, new int[]{1,1,1,1}, 15, SoundEvents.ARMOR_EQUIP_LEATHER, 2.0F, 0.2F, null),
   VIGOR("vigor_wreath_armor", 8, new int[]{1,1,1,1}, 15, SoundEvents.ARMOR_EQUIP_LEATHER, 2.0F, 0.2F, null),
   LINEN("linen_headband_armor", 8, new int[]{1,1,1,1}, 15, SoundEvents.ARMOR_EQUIP_LEATHER, 2.0F, 0.2F, null),
   LINEN_CLOTHES("linen_clothes", 8, new int[]{1,1,1,1}, 15, SoundEvents.ARMOR_EQUIP_LEATHER, 2.0F, 0.2F, null),
   DESCENDING("descending_wreath_armor", 8, new int[]{1,1,1,1}, 15, SoundEvents.ARMOR_EQUIP_LEATHER, 2.0F, 0.2F, null),
   TEARING("tearing_wreath_armor", 8, new int[]{1,1,1,1}, 15, SoundEvents.ARMOR_EQUIP_LEATHER, 2.0F, 0.2F, null),
   PURITY("purity_wreath_armor", 8, new int[]{1,1,1,1}, 15, SoundEvents.ARMOR_EQUIP_LEATHER, 2.0F, 0.2F, null),
   SUPREME("supreme_wreath_armor", 8, new int[]{1,1,1,1}, 15, SoundEvents.ARMOR_EQUIP_LEATHER, 2.0F, 0.2F, null);
 
	 

	  

	    
	    
   
   private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};
   private final String name;
   private final int durabilityMultiplier;
   private final int[] slotProtections;
   private final int enchantmentValue;
   private final SoundEvent sound;
   private final float toughness;
   private final float knockbackResistance;
   private final Supplier<Ingredient> repairIngredient;

   private SlavicArmorMaterial(String p_i231593_3_, int p_i231593_4_, int[] p_i231593_5_, int p_i231593_6_, SoundEvent p_i231593_7_, float p_i231593_8_, float p_i231593_9_, Supplier<Ingredient> p_i231593_10_) {
      this.name = p_i231593_3_;
      this.durabilityMultiplier = p_i231593_4_;
      this.slotProtections = p_i231593_5_;
      this.enchantmentValue = p_i231593_6_;
      this.sound = p_i231593_7_;
      this.toughness = p_i231593_8_;
      this.knockbackResistance = p_i231593_9_;
      this.repairIngredient = p_i231593_10_;
   }

   
   @Override
   public int getDurabilityForSlot(EquipmentSlot p_200896_1_) {
      return HEALTH_PER_SLOT[p_200896_1_.getIndex()] * this.durabilityMultiplier;
   }
   @Override
   public int getDefenseForSlot(EquipmentSlot p_200902_1_) {
      return this.slotProtections[p_200902_1_.getIndex()];
   }

   public int getEnchantmentValue() {
      return this.enchantmentValue;
   }

   public SoundEvent getEquipSound() {
      return this.sound;
   }

   @Override
   public Ingredient getRepairIngredient() {
   	return this.repairIngredient.get();
   }

   @OnlyIn(Dist.CLIENT)
   public String getName() {
      return this.name;
   }

   public float getToughness() {
      return this.toughness;
   }

   public float getKnockbackResistance() {
      return this.knockbackResistance;
   }







}