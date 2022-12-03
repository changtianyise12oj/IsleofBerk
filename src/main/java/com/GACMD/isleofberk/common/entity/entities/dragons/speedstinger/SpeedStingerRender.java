package com.GACMD.isleofberk.common.entity.entities.dragons.speedstinger;

import com.GACMD.isleofberk.common.entity.entities.base.render.render.BaseRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class SpeedStingerRender extends BaseRenderer<SpeedStinger> {


	public SpeedStingerRender(EntityRendererProvider.Context renderManager){
		super(renderManager, new SpeedStingerModel());
	}

	@Override
	public float getScale() {
		return 1.1F;
	}

	@Override
	public float getBabyScale() {
		return 0.3F;
	}

	@Override
	public RenderType getRenderType(SpeedStinger animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
		return RenderType.entityCutoutNoCull(getTextureLocation(animatable));
	}

	@Override
	protected String getLeftEyeBodyBone() {
		return "leftTeethlower2";
	}

	@Override
	protected String getRightEyeBodyBone() {
		return "rightTeethlower3";
	}

	@Override
	public String getDragonFolder() {
		return "speed_stinger";
	}
}
