package com.velesgod.slaviccraft.client.renderer.armor;

import com.velesgod.slaviccraft.client.model.armor.LinenClothesModel;
import com.velesgod.slaviccraft.items.LinenClothes;

import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class LinenClothesRenderer extends GeoArmorRenderer<LinenClothes> {
    	public LinenClothesRenderer() {
    		super(new LinenClothesModel());
    
    		//These values are what each bone name is in blockbench. So if your head bone is named "bone545", 
    		// make sure to do this.headBone = "bone545";
    		
    		// The default values are the ones that come with the default armor template in the geckolib blockbench plugin.
    		this.headBone = "none";
    		this.bodyBone = "body";
    		this.rightArmBone = "rightArm";
    		this.leftArmBone = "leftArm";
    		this.rightLegBone = "rightLeg";
    		this.leftLegBone = "leftLeg";
    		this.rightBootBone = "none";
    		this.leftBootBone = "none";
    	}
    }