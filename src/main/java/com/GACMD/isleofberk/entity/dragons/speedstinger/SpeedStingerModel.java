package com.GACMD.isleofberk.entity.dragons.speedstinger;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.entity.base.render.model.BaseDragonModel;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

@OnlyIn(Dist.CLIENT)
public class SpeedStingerModel extends BaseDragonModel<SpeedStinger> {

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
	@Override
	public void setLivingAnimations(SpeedStinger dragon, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(dragon, uniqueID, customPredicate);

		IBone neck1 = this.getAnimationProcessor().getBone("Neck1");
		IBone neck2 = this.getAnimationProcessor().getBone("Neck2");
		IBone head = this.getAnimationProcessor().getBone("head");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);

		if (!dragon.shouldStopMovingIndependently() && !Minecraft.getInstance().isPaused()) {
			neck1.setRotationY(neck1.getRotationY() + extraData.netHeadYaw * ((float) Math.PI / 180F) / 3);
			neck2.setRotationY(neck2.getRotationY() + extraData.netHeadYaw * ((float) Math.PI / 180F) / 3);
			head.setRotationY(head.getRotationY() + extraData.netHeadYaw * ((float) Math.PI / 180F) / 3);
			neck1.setRotationX(neck1.getRotationX() + extraData.headPitch * ((float) Math.PI / 180F) / 4);
			head.setRotationX(head.getRotationX() + extraData.headPitch * ((float) Math.PI / 180F) / 4);
		}
	}

	@Override
	public String getMainBodyBone() {
		return "root";
	}
}
