package com.GACMD.isleofberk.common.entity.entities.dragons.stinger;

import com.GACMD.isleofberk.common.entity.entities.base.render.render.BaseRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.geo.render.built.GeoModel;

public class StingerRender extends BaseRenderer<Stinger> {

    public StingerRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new StingerModel());
    }

    @Override
    public RenderType getRenderType(Stinger animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        return RenderType.entityCutoutNoCull(getTextureLocation(animatable));
    }

    @Override
    public float getScale() {
        return 1F;
    }

    @Override
    public float getBabyScale() {
        return 1F;
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
    public String getDragonFolder() {
        return "stinger";
    }

    protected String getMainBodyBone() {
        return "Root";
    }

    @Override
    public void render(GeoModel model, Stinger animatable, float partialTicks, RenderType type, PoseStack matrixStackIn, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        super.render(model, animatable, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @Override
    public float getSaddleX() {
        return super.getSaddleX();
    }

    @Override
    public float getSaddleY() {
        return super.getSaddleY();
    }

    @Override
    public float getSaddleZ() {
        return super.getSaddleZ();
    }

    @Override
    public float getSaddleScaleX() {
        return super.getSaddleScaleX();
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