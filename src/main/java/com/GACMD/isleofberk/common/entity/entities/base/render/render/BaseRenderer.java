package com.GACMD.isleofberk.common.entity.entities.base.render.render;

import com.GACMD.isleofberk.common.entity.entities.base.ADragonBase;
import com.GACMD.isleofberk.common.entity.entities.base.ADragonRideableUtility;
import com.GACMD.isleofberk.common.entity.entities.base.render.layer.BaseSaddleLayer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Optional;

public class BaseRenderer<T extends ADragonBase & IAnimatable> extends GeoEntityRenderer<T> {

    public AnimatedGeoModel<T> modelProvider;

    protected BaseRenderer(EntityRendererProvider.Context renderManager, AnimatedGeoModel<T> modelProvider) {
        super(renderManager, modelProvider);
        this.modelProvider = modelProvider;
        this.addLayer(new BaseSaddleLayer<>(this));
    }

    protected String getMainBodyBone() {
        return "Chest";
    }

    protected String getLeftEyeBodyBone() {
        return "EyeL";
    }

    protected String getRightEyeBodyBone() {
        return "EyeR";
    }

    public float getSaddleX() {
        return 0;
    }

    public float getSaddleY() {
        return 0;
    }

    public float getSaddleZ() {
        return 0;
    }

    public float getSaddleScaleX() {
        return 1;
    }

    public float getSaddleScaleY() {
        return 1;
    }

    public float getSaddleScaleZ() {
        return 1;
    }

    /**
     * Get bone also checks if the bone is present
     *
     * @return
     */
    public Optional<GeoBone> getBone(GeoModel model, String boneString) {
        Optional<GeoBone> bone = model.getBone(boneString);
        return bone.isPresent() ? bone : Optional.empty();
    }

    @Override
    public void render(GeoModel model, T dragon, float partialTicks, RenderType type, PoseStack matrixStackIn, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        super.render(model, dragon, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        GeoBone bone = getBone(model, getMainBodyBone()).get();
        // A separate model spawns identical to the dragon when rendering layers
//        if (dragon.getPassengers().size() < 2 && dragon.getControllingPassenger() instanceof Player pilot) {
//            bone.setRotationY(toRadians(pilot.getYRot()));
//        }

//        if (dragon.isDragonSleeping()) {
//            getBone(model, getLeftEyeBodyBone()).get().setHidden(true);
//            getBone(model, getRightEyeBodyBone()).get().setHidden(true);
//        } else {
//            getBone(model, getLeftEyeBodyBone()).get().setHidden(false);
//            getBone(model, getRightEyeBodyBone()).get().setHidden(false);
//
//        }
        if(dragon instanceof ADragonRideableUtility dragonRideableUtility && !dragonRideableUtility.guiLocked()) {
            if(dragonRideableUtility.hasChest()) {
                getBone(model, "Bags").get().setHidden(false);
            } else {
                getBone(model, "Bags").get().setHidden(true);
            }
        }
    }

    @Override
    public void renderEarly(T animatable, PoseStack stack, float ticks,
                            @javax.annotation.Nullable MultiBufferSource bufferIn, @javax.annotation.Nullable VertexConsumer vertexBuilder, int packedLightIn,
                            int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable,stack,ticks,bufferIn,vertexBuilder,packedLightIn,packedOverlayIn,red,green,blue,partialTicks);
        if (animatable.isBaby()) {
            stack.scale(getBabyScale(), getBabyScale(), getBabyScale());
        } else {
            stack.scale(getScale(), getScale(), getScale());
        }
    }

    public String getDragonFolder() {
        return "";
    }

    public float getScale() {
        return 1F;
    }

    public float getBabyScale() {
        return 0.3F;
    }

    private static final float DEGREES_TO_RADIANS = 0.017453292519943295F;

    public static float toRadians(float angdeg) {
        return angdeg * DEGREES_TO_RADIANS;
    }


}
