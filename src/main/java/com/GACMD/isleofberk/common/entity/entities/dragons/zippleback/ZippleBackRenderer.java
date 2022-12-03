package com.GACMD.isleofberk.common.entity.entities.dragons.zippleback;

import com.GACMD.isleofberk.common.entity.entities.base.render.render.BaseRendererFlying;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.geo.render.built.GeoModel;

public class ZippleBackRenderer extends BaseRendererFlying<ZippleBack> {

    protected ZippleBackRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ZippleBackModel());
    }

    @Override
    public RenderType getRenderType(ZippleBack animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        return RenderType.entityCutoutNoCull(getTextureLocation(animatable));
    }

    @Override
    public float getScale() {
        return super.getScale();
    }

    @Override
    public float getBabyScale() {
        return super.getBabyScale();
    }

    @Override
    public void render(GeoModel model, ZippleBack animatable, float partialTicks, RenderType type, PoseStack matrixStackIn, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        super.render(model, animatable, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @Override
    public String getDragonFolder() {
        return "zippleback";
    }

    @Override
    public float getSaddleY() {
        return 0.7F;
    }

    @Override
    public float getSaddleZ() {
        return 0.7F;
    }

    @Override
    public float getSaddleScaleX() {
        return 0.7f;
    }

    @Override
    public float getSaddleScaleY() {
        return super.getSaddleScaleY();
    }

    @Override
    public float getSaddleScaleZ() {
        return super.getSaddleScaleZ();
    }

}
