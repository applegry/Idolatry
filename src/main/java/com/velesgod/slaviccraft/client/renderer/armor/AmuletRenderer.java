package com.velesgod.slaviccraft.client.renderer.armor;

import com.velesgod.slaviccraft.client.model.armor.AmuletModel;
import com.velesgod.slaviccraft.items.AmuletItem;

import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class AmuletRenderer extends GeoArmorRenderer<AmuletItem> {
	public AmuletRenderer() {
		super(new AmuletModel());

		// These values are what each bone name is in blockbench. So if your head bone
		// is named "bone545", make sure to do this.headBone = "bone545";
		// The default values are the ones that come with the default armor template in
		// the geckolib blockbench plugin.
		this.headBone = "none";
		this.bodyBone = "body";
		this.rightArmBone = "none";
		this.leftArmBone = "none";
		this.rightLegBone = "none";
		this.leftLegBone = "none";
		this.rightBootBone = "none";
		this.leftBootBone = "none";
	}
}