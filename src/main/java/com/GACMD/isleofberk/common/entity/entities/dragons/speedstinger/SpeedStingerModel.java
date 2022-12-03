package com.GACMD.isleofberk.common.entity.entities.dragons.speedstinger;

import com.GACMD.isleofberk.IsleofBerk;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SpeedStingerModel extends AnimatedGeoModel<SpeedStinger> {

	public static final ResourceLocation SPEED_STINGER = new ResourceLocation("isleofberk:textures/dragons/speed_stinger/speed_stinger.png");
	public static final ResourceLocation FLOUTSCOUT = new ResourceLocation("isleofberk:textures/dragons/speed_stinger/floutscout.png");
	public static final ResourceLocation ICE_BREAKER = new ResourceLocation("isleofberk:textures/dragons/speed_stinger/ice_breaker.png");
	public static final ResourceLocation SWEET_STING = new ResourceLocation("isleofberk:textures/dragons/speed_stinger/sweet_sting.png");

	@Override
	public ResourceLocation getAnimationFileLocation(SpeedStinger speed_stinger) {
		return new ResourceLocation(IsleofBerk.MOD_ID, "animations/dragons/speed_stinger.animation.json");
	}

	@Override
	public ResourceLocation getModelLocation(SpeedStinger speed_stinger) {
		return new ResourceLocation(IsleofBerk.MOD_ID, "geo/dragons/speed_stinger.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(SpeedStinger speed_stinger) {
		switch (speed_stinger.getDragonVariant()) {
			default:
			case 0:
				return SPEED_STINGER; // taiga
			case 1:
				return FLOUTSCOUT;   // caves
			case 2:
				return ICE_BREAKER; // ice
			case 3:
				return SWEET_STING; // jungle
		}
	}
}
