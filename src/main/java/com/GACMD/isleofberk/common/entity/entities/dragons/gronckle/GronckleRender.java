package com.GACMD.isleofberk.common.entity.entities.dragons.gronckle;

import com.GACMD.isleofberk.common.entity.entities.base.render.render.BaseRendererFlying;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.geo.render.built.GeoModel;

public class GronckleRender extends BaseRendererFlying<Gronckle> {

    public GronckleRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new GronckleModel(renderManager));
    }

    @Override
    public RenderType getRenderType(Gronckle animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        return RenderType.entityCutoutNoCull(getTextureLocation(animatable));
    }

    @Override
    public float getBabyScale() {
        return 0.45F;
    }

    @Override
    public float getScale() {
        return 0.92F;
    }

    @Override
    public void render(GeoModel model, Gronckle animatable, float partialTicks, RenderType type, PoseStack matrixStackIn, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        super.render(model, animatable, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @Override
    protected int getMaxRise() {
        return 10;
    }

    @Override
    protected int getMinRise() {
        return -28;
    }

    @Override
    public boolean hasDynamicYawAndRoll() {
        return false;
    }

    @Override
    public String getDragonFolder() {
        return "gronckle";
    }

    @Override
    protected String getMainBodyBone() {
        return "Root";
    }

    @Override
    protected String getLeftEyeBodyBone() {
        return "leftEye";
    }

    @Override
    protected String getRightEyeBodyBone() {
        return "rightEye";
    }

    @Override
    public float getSaddleX() {
        return super.getSaddleX();
    }

    @Override
    public float getSaddleY() {
        return 0F;
    }

    @Override
    public float getSaddleZ() {
        return super.getSaddleZ();
    }

    @Override
    public float getSaddleScaleX() {
        return 1.1F;
    }

    @Override
    public float getSaddleScaleY() {
        return 1.1F;
    }

    @Override
    public float getSaddleScaleZ() {
        return 1.1F;
    }
}
