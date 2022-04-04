

package com.velesgod.slaviccraft.core.init;

import com.velesgod.slaviccraft.SlavicCraftMod;
import com.velesgod.slaviccraft.entity.SlavicGhost;
import com.velesgod.slaviccraft.entity.SlavicLeshin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityInit {
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES,
			SlavicCraftMod.MOD_ID);

	
	
	public static final RegistryObject<EntityType<SlavicGhost>> SLAVIC_GHOST = buildEntity(
			SlavicGhost::new, SlavicGhost.class, .5F, 1.2F);
	
	
	public static final RegistryObject<EntityType<SlavicLeshin>> SLAVIC_LESHIN = buildEntity(
			SlavicLeshin::new, SlavicLeshin.class, 0.8F, 2.0F);


	public static <T extends Entity> RegistryObject<EntityType<T>> buildEntity(EntityType.EntityFactory<T> entity,
			Class<T> entityClass, float width, float height) {
		String name = entityClass.getSimpleName().toLowerCase();
		return ENTITIES.register(name,
				() -> EntityType.Builder.of(entity, MobCategory.CREATURE).sized(width, height).build(name));
	}
}