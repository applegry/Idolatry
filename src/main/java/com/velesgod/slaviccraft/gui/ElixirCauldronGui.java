package com.velesgod.slaviccraft.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.velesgod.slaviccraft.SlavicCraftMod;

import com.velesgod.slaviccraft.contaniers.ElixirCauldronContanier;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;


public class ElixirCauldronGui extends AbstractContainerScreen<ElixirCauldronContanier>{

	private final ResourceLocation GUI = new ResourceLocation(SlavicCraftMod.MOD_ID,"textures/gui/elixir_cauldron.png");

	
	public ElixirCauldronGui(ElixirCauldronContanier p_i51105_1_, Inventory p_i51105_2_, Component p_i51105_3_) {
		super(p_i51105_1_, p_i51105_2_, p_i51105_3_);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void render(PoseStack stack, int x, int y, float tick) {
		//this.renderBackground(stack);
		this.renderBgr(stack, tick, x, y);
		super.render(stack, x, y, tick);
	//	this.renderTooltip(stack, x,y);
	
	}
	

	public int getPercent() {
		//System.out.print((this.menu.getSmelt()*1.f)/this.menu.getMAX()+"\n");
		return Math.round(((this.menu.getSmelt()*1.f)/this.menu.getMAX())*13);
	}
	
	public int getProgress() {
		//System.out.print((this.menu.getSmelt()*1.f)/this.menu.getMAX()+"\n");
		return Math.round(((this.menu.getCooking()*1.f)/this.menu.getCookingMAX())*32);
	}
	
	protected void renderBgr(PoseStack stack, float tick, int x, int y) {
		int i = this.getGuiLeft();
		int j = this.getGuiTop()+2;
	      renderBackground(stack);
	        RenderSystem.setShader(GameRenderer::getPositionTexShader);
	        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
	        RenderSystem.setShaderTexture(0, GUI);
		this.blit(stack, i, j, 0, 0, this.getXSize(),this.getYSize());
		if(this.menu.getSmelt() > 0) {
	
		this.blit(stack, i+32, j+31+13-getPercent(), 176, 13-getPercent(),13,getPercent());
		
		}
		this.blit(stack, i+102, j+33, 176, 14,8,getProgress()); //getProgress
	}

	@Override
	protected void renderBg(PoseStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
		renderBgr(p_230450_1_,p_230450_2_,p_230450_3_,p_230450_4_);
		
	}
	
	
	
}