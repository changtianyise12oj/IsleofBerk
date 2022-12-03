package com.GACMD.isleofberk.common.entity.entities.dragons.nightfury;

import com.GACMD.isleofberk.common.entity.entities.base.render.render.BaseRendererFlying;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.geo.render.built.GeoModel;

public class NightFuryRender extends BaseRendererFlying<NightFury> {

    public NightFuryRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new NightFuryModel());
    }

    @Override
    public RenderType getRenderType(NightFury animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        return RenderType.entityCutoutNoCull(getTextureLocation(animatable));
    }

    @Override
    public void render(GeoModel model, NightFury animatable, float partialTicks, RenderType type, PoseStack matrixStackIn, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (!animatable.isInvisible())
            super.render(model, animatable, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        if (animatable.isBaby()) {
            matrixStackIn.scale(getBabyScale(), getBabyScale(), getBabyScale());
        } else {
            matrixStackIn.scale(getScale() * 3.2f, getScale() * 3.2f, getScale() * 3.2f); // 0.23f
        }
    }

    public float getScale() {
        return 0.555F;
    }

    public float getBabyScale() {
        return 0.2F;
    }

    @Override
    protected String getRightEyeBodyBone() {
        return "EyesO";
    }

    @Override
    protected String getLeftEyeBodyBone() {
        return "EyesO";
    }

    @Override
    public String getDragonFolder() {
        return "night_fury";
    }


    @Override
    public float getSaddleX() {
        return super.getSaddleX();
    }

    @Override
    public float getSaddleY() {
        return 0.7F;
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
