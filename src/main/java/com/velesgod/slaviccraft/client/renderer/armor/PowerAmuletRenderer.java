package com.velesgod.slaviccraft.client.renderer.armor;

import com.velesgod.slaviccraft.client.model.armor.PowerAmuletModel;
import com.velesgod.slaviccraft.items.PowerAmuletItem;

import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class PowerAmuletRenderer extends GeoArmorRenderer<PowerAmuletItem> {
	public PowerAmuletRenderer() {
		super(new PowerAmuletModel());

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