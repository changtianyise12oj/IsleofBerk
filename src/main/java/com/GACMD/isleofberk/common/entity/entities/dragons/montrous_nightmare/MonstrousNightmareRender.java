package com.GACMD.isleofberk.common.entity.entities.dragons.montrous_nightmare;

import com.GACMD.isleofberk.common.entity.entities.base.render.render.BaseRendererFlying;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.geo.render.built.GeoModel;

public class MonstrousNightmareRender extends BaseRendererFlying<MonstrousNightmare> {

    protected MonstrousNightmareRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new MonstrousNightmareModel(renderManager));
    }

    @Override
    public RenderType getRenderType(MonstrousNightmare animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        return RenderType.entityCutoutNoCull(getTextureLocation(animatable));
    }


    @Override
    public void renderEarly(MonstrousNightmare animatable, PoseStack stack, float ticks,
                            @javax.annotation.Nullable MultiBufferSource bufferIn, @javax.annotation.Nullable VertexConsumer vertexBuilder, int packedLightIn,
                            int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable,stack,ticks,bufferIn,vertexBuilder,packedLightIn,packedOverlayIn,red,green,blue,partialTicks);
        if (animatable.isBaby()) {
            stack.scale(0.555F / 3, 0.555F / 3, 0.555F / 3);

        } else {
            stack.scale(0.555F, 0.555F, 0.555F);
        }
    }

    @Override
    public void render(GeoModel model, MonstrousNightmare animatable, float partialTicks, RenderType type, PoseStack matrixStackIn, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        super.render(model, animatable, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
    @Override
    public String getDragonFolder() {
        return "monstrous_nightmare";
    }



}
