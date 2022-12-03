package com.GACMD.isleofberk.common.entity.entities.dragons.speedstingerleader;

import com.GACMD.isleofberk.IsleofBerk;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SpeedStingerLeaderModel extends AnimatedGeoModel<SpeedStingerLeader> {

	public static final ResourceLocation SPEED_STINGER = new ResourceLocation("isleofberk:textures/dragons/speed_stinger_leader/speed_stinger.png");
	public static final ResourceLocation FLOUTSCOUT = new ResourceLocation("isleofberk:textures/dragons/speed_stinger_leader/floutscout.png");
	public static final ResourceLocation ICE_BREAKER = new ResourceLocation("isleofberk:textures/dragons/speed_stinger_leader/ice_breaker.png");
	public static final ResourceLocation SWEET_STING = new ResourceLocation("isleofberk:textures/dragons/speed_stinger_leader/sweet_sting.png");

	@Override
	public ResourceLocation getAnimationFileLocation(SpeedStingerLeader speed_stinger) {
		return new ResourceLocation(IsleofBerk.MOD_ID, "animations/dragons/speed_stinger.animation.json");
	}

	@Override
	public ResourceLocation getModelLocation(SpeedStingerLeader speed_stinger) {
		return new ResourceLocation(IsleofBerk.MOD_ID, "geo/dragons/speed_stinger_leader.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(SpeedStingerLeader speed_stinger_leader) {
		switch (speed_stinger_leader.getDragonVariant()) {
			default:
			case 0:
				return SPEED_STINGER; // taiga
			case 1:
				return FLOUTSCOUT; // caves
			case 2:
				return ICE_BREAKER; // ice
			case 3:
				return SWEET_STING; // jungle
		}
	}
}
