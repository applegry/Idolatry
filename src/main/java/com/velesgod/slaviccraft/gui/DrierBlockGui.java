package com.velesgod.slaviccraft.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.velesgod.slaviccraft.SlavicCraftMod;
import com.velesgod.slaviccraft.contaniers.DrierBlockContainer;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class DrierBlockGui extends AbstractContainerScreen<DrierBlockContainer>{

	private final ResourceLocation GUI = new ResourceLocation(SlavicCraftMod.MOD_ID,"textures/gui/dryer.png");

	
	public DrierBlockGui(DrierBlockContainer p_i51105_1_, Inventory p_i51105_2_, Component p_i51105_3_) {
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
	
	private boolean slotIsWork(int i) {
		return this.menu.getWork(i);
	}

	private int getStep(int i) {
		return Math.round( (this.menu.getProgress(i)*1.f)/this.menu.getMax()*19);
	}
	
	protected void renderBgr(PoseStack stack, float tick, int x, int y) {

		int i = this.getGuiLeft();
		int j = this.getGuiTop();
        renderBackground(stack);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, GUI);
        blit(stack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
		for(int xx=0;xx<4;xx++)
		for(int yy=0;yy<2;yy++)
		{
			
			if(slotIsWork(xx+yy*4))
			this.blit(stack, i+31+xx*32, j+35+yy*32, 176, 0, getStep(xx+yy*4),4);
		}
	}

	@Override
	protected void renderBg(PoseStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
		renderBgr(p_230450_1_,p_230450_2_,p_230450_3_,p_230450_4_);
		
	}
	
	
	
}