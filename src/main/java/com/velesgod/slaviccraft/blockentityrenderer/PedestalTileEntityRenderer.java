package com.velesgod.slaviccraft.blockentityrenderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import com.velesgod.slaviccraft.blocks.tile.PedestalTileEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;

public class PedestalTileEntityRenderer implements BlockEntityRenderer<PedestalTileEntity>{

	

	
	private Minecraft mc = Minecraft.getInstance();
	
	

	
	//public PedestalTileEntityRenderer(TileEntityRendererDispatcher renderIn) {
	//	super(renderIn);
//	}

	
	public PedestalTileEntityRenderer(BlockEntityRendererProvider.Context context) {
		
	}	
	



    private int getLightLevel(Level world,BlockPos pos) {
    	int bLight = world.getBrightness(LightLayer.BLOCK, pos);
    	int sLight = world.getBrightness(LightLayer.SKY, pos);
    	return LightTexture.pack(bLight,sLight);
    }

    
    private void renderItem(ItemStack stack,double[] transitions,Quaternion rotation, PoseStack matrixstack,
    		MultiBufferSource buffer,float partialTicks,int combinedOverlay,int lightLevel,float scale) {
    	
    	matrixstack.pushPose();
    	matrixstack.translate(transitions[0], transitions[1], transitions[2]);
    	matrixstack.mulPose(rotation);
    	matrixstack.scale(scale, scale, scale);
    	BakedModel  model = mc.getItemRenderer().getModel(stack, null, null,combinedOverlay);
    	
    	mc.getItemRenderer().render(stack, TransformType.GROUND, true, matrixstack, buffer, 255, 255, model);
    	matrixstack.popPose();
    	
    }


	@Override
	public void render(PedestalTileEntity te, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn,
			int combindOverlayIn, int p_112312_) {
		 
    	int lightLevel = getLightLevel(te.getLevel(),te.getBlockPos());
  
    	
    //	for(int i=0;i<8;i++)
    	//	if(te.getStack(i).getItem() instanceof BaseBundle || te.getStack(i).getItem() == Items.ROTTEN_FLESH)
    	//renderItem(te.getStack(i),
        //		poses.get(i).getFirst(),
        //		poses.get(i).getSecond(),
      	//	matrixStackIn, bufferIn,partialTicks,combindOverlayIn,lightLevel,1.2f);
    	//	else
    	te.angle+=2*partialTicks;
    	//angle = (angle) % 360;
    	//	System.out.println(angle);
    	
    	renderItem(te.getStack(0),
    	    			new double[] {0.5f,1.2f+Math.sin(te.angle*0.06f)*0.06f,0.5f},
    	    			  Vector3f.YP.rotationDegrees(te.angle),
    	       		matrixStackIn, bufferIn,partialTicks,combindOverlayIn,lightLevel,1.0f);


 
   
 
		
	}
	
    
    
    
    
    
    
    
    
    
	
}