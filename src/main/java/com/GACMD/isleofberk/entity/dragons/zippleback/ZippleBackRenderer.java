package com.GACMD.isleofberk.entity.dragons.zippleback;

import com.GACMD.isleofberk.entity.base.render.render.BaseRendererFlying;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class ZippleBackRenderer extends BaseRendererFlying<ZippleBack> {

    public ZippleBackRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ZippleBackModel());
    }

    @Override
    public RenderType getRenderType(ZippleBack animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        return RenderType.entityCutoutNoCull(getTextureLocation(animatable));
    }

    @Override
    public String getDragonFolder() {
        return "zippleback";
    }

}
