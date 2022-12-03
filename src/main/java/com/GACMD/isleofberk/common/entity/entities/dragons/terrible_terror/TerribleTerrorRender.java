package com.GACMD.isleofberk.common.entity.entities.dragons.terrible_terror;

import com.GACMD.isleofberk.common.entity.entities.base.render.render.BaseRenderer;
import com.GACMD.isleofberk.common.entity.entities.base.render.layer.DragonHeldItemLayer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class TerribleTerrorRender extends BaseRenderer<TerribleTerror> {

    public TerribleTerrorRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TerribleTerrorModel());
        this.addLayer(new DragonHeldItemLayer(this));
    }

    @Override
    public float getScale() {
        return 0.6F;
    }

    @Override
    public float getBabyScale() {
        return 0.6F / 3;
    }

    @Override
    public RenderType getRenderType(TerribleTerror animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        return RenderType.entityCutoutNoCull(getTextureLocation(animatable));
    }

    @Override
    protected String getRightEyeBodyBone() {
        return "rightEye";
    }

    @Override
    protected String getLeftEyeBodyBone() {
        return "leftEye";
    }

    @Override
    public String getDragonFolder() {
        return "terrible_terror";
    }
}
