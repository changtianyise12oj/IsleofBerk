package com.GACMD.isleofberk.common.entity.entities.eggs.entity.base.small;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class SmallEggRenderer extends GeoProjectilesRenderer<ADragonSmallEggBase> {

    public SmallEggRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SmallEggModel());
    }

    @Override
    public void render(ADragonSmallEggBase pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(ADragonSmallEggBase dragonEggBase) {
        return dragonEggBase.getTextureLocation(dragonEggBase);
    }

    @Override
    public RenderType getRenderType(ADragonSmallEggBase animatable, float partialTicks, PoseStack stack, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityCutoutNoCull(textureLocation);
    }

    public void renderEarly(ADragonSmallEggBase animatable, PoseStack stackIn, float ticks,
                            @javax.annotation.Nullable MultiBufferSource renderTypeBuffer, @javax.annotation.Nullable VertexConsumer vertexBuilder, int packedLightIn,
                            int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        stackIn.scale(animatable.scale(), animatable.scale(), animatable.scale());
    }
}
