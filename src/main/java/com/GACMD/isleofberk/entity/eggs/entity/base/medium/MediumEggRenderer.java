package com.GACMD.isleofberk.entity.eggs.entity.base.medium;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class MediumEggRenderer extends GeoProjectilesRenderer<ADragonMediumEggBase> {

    public MediumEggRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new MediumEggModel());
    }

    @Override
    public void render(ADragonMediumEggBase pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    public RenderType getRenderType(ADragonMediumEggBase animatable, float partialTicks, PoseStack stack, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityCutoutNoCull(textureLocation);
    }

    @Override
    public ResourceLocation getTextureLocation(ADragonMediumEggBase dragonEggBase) {
        return dragonEggBase.getTextureLocation(dragonEggBase);
    }

    public void renderEarly(ADragonMediumEggBase animatable, PoseStack stackIn, float ticks,
                            @javax.annotation.Nullable MultiBufferSource renderTypeBuffer, @javax.annotation.Nullable VertexConsumer vertexBuilder, int packedLightIn,
                            int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        stackIn.scale(1.1F, 1.1F, 1.1F);
    }
}
