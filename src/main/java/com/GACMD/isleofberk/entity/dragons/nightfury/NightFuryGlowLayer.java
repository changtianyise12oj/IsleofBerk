package com.GACMD.isleofberk.entity.dragons.nightfury;

import com.GACMD.isleofberk.entity.base.render.render.BaseRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class NightFuryGlowLayer<T extends NightFury & IAnimatable> extends GeoLayerRenderer<T> {

    protected BaseRenderer<T> baseRenderer;

    public NightFuryGlowLayer(IGeoRenderer<T> entityRendererIn) {
        super(entityRendererIn);
        this.baseRenderer = (BaseRenderer<T>) entityRendererIn;
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T dragon, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        RenderType cameo = RenderType.eyes(getTextureLocation(dragon));
        this.getRenderer().render(getEntityModel().getModel(baseRenderer.getGeoModelProvider().getModelLocation(dragon)), dragon, partialTicks, cameo, matrixStackIn, bufferIn,
                bufferIn.getBuffer(cameo), 15728640, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
    }

    public ResourceLocation getTextureLocation(NightFury entity) {
        switch (entity.getGlowVariants()) {
            default:
            case 0:
                return new ResourceLocation("isleofberk:textures/dragons/night_fury/nightfury_glow_1.png");
            case 1:
                return new ResourceLocation("isleofberk:textures/dragons/night_fury/nightfury_glow_2.png");
        }
    }
}
