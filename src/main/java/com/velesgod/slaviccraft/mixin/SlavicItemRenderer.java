package com.velesgod.slaviccraft.mixin;


import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;

import net.minecraft.client.gui.Font;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.velesgod.slaviccraft.SlavicCraftMod;

import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;

@Mixin(ItemRenderer.class)

abstract class SlavicItemRenderer{


	@Inject(at = @At("RETURN"),
	method = "Lnet/minecraft/client/renderer/entity/ItemRenderer;renderGuiItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V")
	
	//@Inject(at = @At("RhETURN"), method = "renderGuiItemDecorations", cancellable = true)
	public void renderGuiItemDecorations(Font font, ItemStack stack, int x, int y, @Nullable String str,CallbackInfo info) {		
		
		            int j = stack.getOrCreateTag().getInt("balm_value");
		            if (j > 0) {
		            RenderSystem.disableDepthTest();
		            RenderSystem.disableTexture();
		            
		            RenderSystem.disableBlend();
		            Tesselator tessellator = Tesselator.getInstance();
		            BufferBuilder bufferbuilder = tessellator.getBuilder();
		            
		           // double health = stack.getItem().getDurabilityForDisplay(stack);
		          //  int i = Math.round(13.0F - (float)health * 13.0F);
		            this.fillRect(bufferbuilder, x + 2, y + 11, 13, 2, 0, 0, 0, 255);
		            this.fillRect(bufferbuilder, x + 2, y + 11, j, 1, 221, 174,82, 255);
		            RenderSystem.enableBlend();
		          //  RenderSystem.enableAlphaTest();
		            RenderSystem.enableTexture();
		            RenderSystem.enableDepthTest();
		         }
		      }

	@Shadow
	   private void fillRect(BufferBuilder p_181565_1_, int p_181565_2_, int p_181565_3_, int p_181565_4_, int p_181565_5_, int p_181565_6_, int p_181565_7_, int p_181565_8_, int p_181565_9_) {
		      p_181565_1_.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
		      p_181565_1_.vertex((double)(p_181565_2_ + 0), (double)(p_181565_3_ + 0), 0.0D).color(p_181565_6_, p_181565_7_, p_181565_8_, p_181565_9_).endVertex();
		      p_181565_1_.vertex((double)(p_181565_2_ + 0), (double)(p_181565_3_ + p_181565_5_), 0.0D).color(p_181565_6_, p_181565_7_, p_181565_8_, p_181565_9_).endVertex();
		      p_181565_1_.vertex((double)(p_181565_2_ + p_181565_4_), (double)(p_181565_3_ + p_181565_5_), 0.0D).color(p_181565_6_, p_181565_7_, p_181565_8_, p_181565_9_).endVertex();
		      p_181565_1_.vertex((double)(p_181565_2_ + p_181565_4_), (double)(p_181565_3_ + 0), 0.0D).color(p_181565_6_, p_181565_7_, p_181565_8_, p_181565_9_).endVertex();
		      Tesselator.getInstance().end();
		   }
}