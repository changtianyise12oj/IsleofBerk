package com.GACMD.isleofberk.common.entity.entities.dragons.deadlynadder;

import com.GACMD.isleofberk.common.entity.entities.base.render.render.BaseRendererFlying;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.geo.render.built.GeoModel;

public class DeadlyNadderRender extends BaseRendererFlying<DeadlyNadder> {

    public String leftWingBone = "leftWingArm";
    public String rightWingBone = "rightWingArm";
    public String rightCalw = "rightCalw";
    public String rightClaw = "leftCalw";
    public String Claw = "Claw";

    public DeadlyNadderRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DeadlyNadderModel(renderManager));
        this.addLayer(new DeadlyNadderWingLayer<>(this, renderManager));
    }

    public float getScale() {
        return 1f;
    }

    //    @Override
    public float getBabyScale() {
        return 0.4F;
    }

    @Override
    public RenderType getRenderType(DeadlyNadder animatable, float partialTicks, PoseStack stack, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityCutoutNoCull(textureLocation);
    }

    @Override
    public void render(GeoModel model, DeadlyNadder deadlyNadder, float partialTicks, RenderType type, PoseStack stack, @Nullable MultiBufferSource bufferIn, @Nullable VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        super.render(model, deadlyNadder, partialTicks, type, stack, bufferIn, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        if (deadlyNadder.isBaby()) {
            stack.scale(getScale(), getScale(), getScale());
        } else {
            stack.scale(getScale(), getScale(), getScale()); // 0.23f
        }
    }

    private static float getBoundedScale(float scale, float min, float max) {
        return min + scale * (max - min);
    }

    //    @Override
    protected String getLeftEyeBodyBone() {
        return "leftEye";
    }

    //    @Override
    protected String getRightEyeBodyBone() {
        return "rightEye";
    }

    //    @Override
    public String getDragonFolder() {
        return "deadly_nadder";
    }


    @Override
    public float getSaddleX() {
        return 0;
    }

    @Override
    public float getSaddleY() {
        return -0.02F;
    }

    @Override
    public float getSaddleZ() {
        return -0.005f;
    }

    @Override
    public float getSaddleScaleX() {
        return 1.01F;
    }

    @Override
    public float getSaddleScaleY() {
        return 1.01F;
    }

    @Override
    public float getSaddleScaleZ() {
        return 1.01F;
    }
}
