package com.velesgod.slaviccraft.blockentityrenderer;

import java.util.ArrayList;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Quaternion;
import com.velesgod.slaviccraft.blocks.tile.DrierTileEntity;
import com.velesgod.slaviccraft.items.BaseBundle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;


public class DrierTileEntityRenderer implements BlockEntityRenderer<DrierTileEntity>{

	
	
	
	public DrierTileEntityRenderer(BlockEntityRendererProvider.Context context) {
		
	}	
	
	
	private Minecraft mc = Minecraft.getInstance();
	
	private static  Pair<double[],Quaternion> getPair(double[] f,Quaternion s) {
	
		 return  new Pair<double[],Quaternion>(f, s);
	}
	
	
	private static ArrayList<Pair<double[],Quaternion>> initPoses(boolean simple) {
		ArrayList<Pair<double[],Quaternion>> arr = new ArrayList<Pair<double[],Quaternion>>();
		if(simple) {
		arr.add(getPair( new double[] {0.6d,5.5D/16D,0.85d},new Quaternion(180,0f,45f,true)));
		arr.add(getPair( new double[] {0.6d,5.5D/16D,0.15d},new Quaternion(180,0f,45f,true)));
		arr.add(getPair( new double[] {0.85d,5.5D/16D,0.6d},new Quaternion(180,90,45,true)));
		arr.add(getPair( new double[] {0.15d,5.5D/16D,0.6d},new Quaternion(180,90,45,true)));
		
		arr.add(getPair( new double[] {0.12d,5.5D/16D,0.88d},new Quaternion(180,135,45,true)));
		arr.add(getPair( new double[] {0.88d,5.5D/16D,0.12d}, new Quaternion(180,-45,45,true)));
		arr.add(getPair(new double[] {0.12d,5.5D/16D,0.12d},new Quaternion(180,-135,45,true)));
		arr.add(getPair( new double[] {0.88d,5.5D/16D,0.88d}, new Quaternion(180,45,45,true)));
		}else {
			arr.add(getPair( new double[] {0.5d,4.5D/16D,0.85d},new Quaternion(180,0f,0,true)));
			arr.add(getPair( new double[] {0.5d,4.5D/16D,0.15d},new Quaternion(180,0f,0,true)));
			arr.add(getPair( new double[] {0.85d,4.5D/16D,0.5d},new Quaternion(180,90,0,true)));
			arr.add(getPair( new double[] {0.15d,4.5D/16D,0.5d},new Quaternion(180,90,0,true)));
			
			arr.add(getPair( new double[] {0.18d,4.5D/16D,0.82d},new Quaternion(180,135,0,true)));// new Quaternion(180,135,0,true)
			
			arr.add(getPair( new double[] {0.82d,4.5D/16D,0.18d}, new Quaternion(180,-45,0,true)));// new Quaternion(180,-45,0,true)
			arr.add(getPair(new double[] {0.18d,4.5D/16D,0.18d},new Quaternion(180,-135,0,true)));
			arr.add(getPair( new double[] {0.82d,4.5D/16D,0.82d}, new Quaternion(180,45,0,true)));
		}
		return arr;
	}
	
	
	
	private ArrayList<Pair<double[],Quaternion>> poses = initPoses(true);
	
	private ArrayList<Pair<double[],Quaternion>> simpleposes = initPoses(false);
	



	//render()
    

    private int getLightLevel(Level world,BlockPos pos) {
    	int bLight = world.getBrightness(LightLayer.BLOCK, pos);
    	int sLight = world.getBrightness(LightLayer.SKY, pos);
    	return LightTexture.pack(bLight,sLight);
    }


	@Override
	public void render(DrierTileEntity te, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn,
			int combinedLightIn, int combindOverlayIn) {
    	int lightLevel = getLightLevel(te.getLevel(),te.getBlockPos());
    	//System.out.println("asd");
    	
    	for(int i=0;i<8;i++)
    		if(te.getItemInSlot(i).getItem() instanceof BaseBundle || te.getItemInSlot(i).getItem() == Items.ROTTEN_FLESH)
    	renderItem(te.getItemInSlot(i),
        		poses.get(i).getFirst(),
        		poses.get(i).getSecond(),
      		matrixStackIn, bufferIn,partialTicks,combindOverlayIn,lightLevel,1.2f);
  		else
    	    	renderItem(te.getItemInSlot(i),
    	    			simpleposes.get(i).getFirst(),
    	    			simpleposes.get(i).getSecond(),
    	       		matrixStackIn, bufferIn,partialTicks,combindOverlayIn,lightLevel,1.2f);
  
	}




    private void renderItem(ItemStack stack,double[] transitions,Quaternion rotation, PoseStack matrixstack,
    		MultiBufferSource buffer,float partialTicks,int combinedOverlay,int lightLevel,float scale) {
    	
    	matrixstack.pushPose();
    	matrixstack.translate(transitions[0], transitions[1], transitions[2]);
    	matrixstack.mulPose(rotation);
    	matrixstack.scale(scale, scale, scale);
    	BakedModel  model = mc.getItemRenderer().getModel(stack, null, null,combinedOverlay);
    	
    	mc.getItemRenderer().render(stack, TransformType.GROUND, true, matrixstack, buffer, lightLevel, combinedOverlay, model);
    	matrixstack.popPose();
    	
    }

    
    
    
    
    
    
    
    
	
}