package com.GACMD.isleofberk.entity.dragons.skrill;

import com.GACMD.isleofberk.entity.base.render.render.BaseRendererFlying;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class SkrillRenderer extends BaseRendererFlying<Skrill> {

    public SkrillRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SkrillModel(renderManager));
        this.addLayer(new SkrillSkillLayer<>(this));
    }

    @Override
    public RenderType getRenderType(Skrill animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        return RenderType.entityCutoutNoCull(getTextureLocation(animatable));
    }

    @Override
    public String getDragonFolder() {
        return "skrill";
    }

}
