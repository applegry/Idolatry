package com.velesgod.slaviccraft.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.velesgod.slaviccraft.entity.SlavicGhost;
import com.velesgod.slaviccraft.entity.SlavicLeshin;
import com.velesgod.slaviccraft.entity.model.SlavicGhostModel;
import com.velesgod.slaviccraft.entity.model.SlavicLeshinModel;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SlavicLeshinRenderer extends GeoEntityRenderer<SlavicLeshin> {

	public SlavicLeshinRenderer(EntityRendererProvider.Context renderManager) {
		
		super(renderManager, new SlavicLeshinModel());
		this.shadowRadius = 0;
		this.shadowStrength = 0;
	
	}

	@Override
	public RenderType getRenderType(SlavicLeshin animatable, float partialTicks, PoseStack stack,
			MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation) {
		return RenderType.entityCutoutNoCull(getTextureLocation(animatable));
	}
}