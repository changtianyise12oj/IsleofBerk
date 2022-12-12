package com.GACMD.isleofberk.common.entity.entities.dragons.speedstingerleader;

import com.GACMD.isleofberk.common.entity.entities.base.render.render.BaseRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpeedStingerLeaderRender extends BaseRenderer<SpeedStingerLeader> {

	public SpeedStingerLeaderRender(EntityRendererProvider.Context renderManager){
		super(renderManager, new SpeedStingerLeaderModel());
	}

	@Override
	public RenderType getRenderType(SpeedStingerLeader animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
		return RenderType.entityCutoutNoCull(getTextureLocation(animatable));
	}

	@Override
	public String getDragonFolder() {
		return "speed_stinger";
	}
}
