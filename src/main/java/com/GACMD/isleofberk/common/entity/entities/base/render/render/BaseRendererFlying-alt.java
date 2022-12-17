package com.GACMD.isleofberk.common.entity.entities.base.render.render;

import com.GACMD.isleofberk.common.entity.entities.base.dragon.ADragonBaseFlyingRideable;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;

class BaseRendererFlyingalt<T extends ADragonBaseFlyingRideable & IAnimatable> extends BaseRenderer<T> {
    protected float changeInYaw;
    protected float currentBodyPitch;
    protected float currentBodyYawForRoll;
    protected float currentBodyYaw;
    protected float boostedBodyPitch;
    protected float finalBodyPitch;

    protected BaseRendererFlyingalt(EntityRendererProvider.Context renderManager, AnimatedGeoModel<T> modelProvider) {
        super(renderManager, modelProvider);
    }

    @Override
    public void render(GeoModel model, T animatable, float partialTicks, RenderType type, PoseStack matrixStackIn, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        modifyPitch(model, animatable, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        // Lastly we super the render method.
        super.render(model, animatable, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
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

    /**
     * Mojang yaw pitch and roll and Geckolib yaw pitch and roll are inversed, use negative and positive values
     */
    protected void
    modifyPitch(GeoModel model, T dragon, float partialTicks, RenderType type, PoseStack matrixStackIn, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder,
                int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        GeoBone body = getBone(model, getMainBodyBone()).get();
        if (dragon.getPassengers().size() < 2) {
            if (dragon.isFlying()) {
                if (dragon.getControllingPassenger() instanceof Player pilot) {
                    // approach to 0 if not boosting
                    // approach to maxRise if boosting
                    if (dragon.isGoingUp() && boostedBodyPitch >= -40) {
                        boostedBodyPitch -= 1;
                    } else if (!dragon.isGoingUp()) {
                        boostedBodyPitch = Mth.approach(boostedBodyPitch, 0, -1);
                    }

                    // +90 dive -90 rise
                    currentBodyPitch = Mth.lerp(0.1F, pilot.xRotO, getMaxRise());
//                    System.out.println("currentBodyPitch: " + currentBodyPitch);
//                    System.out.println("boostedBodyPitch: " + boostedBodyPitch);
//                    System.out.println("-finalBodyPitch: " + -finalBodyPitch);
//                    System.out.println("-finalBodyPitch radians: " + -finalBodyPitch);
//                    finalBodyPitch -40: is dive max
//                    finalBodyPitch 40: is riseMax
                    finalBodyPitch = currentBodyPitch + boostedBodyPitch;
                    body.setRotationX(toRadians(Mth.clamp(-finalBodyPitch, getMinRise(), getMaxRise())));

                    if (hasDynamicYawAndRoll()) {
                        float f = Mth.rotLerp(partialTicks, dragon.yBodyRotO, dragon.yBodyRot);
                        float f1 = Mth.rotLerp(partialTicks, dragon.yHeadRotO, dragon.yHeadRot);
                        changeInYaw = (f1 - f) * ((float) Math.PI / 180F) * -1.5F;
                        // the rotation is based on yaw
                        body.setRotationZ(changeInYaw);
                    }
                }
            }

            if (!dragon.isFlying()) {
                boostedBodyPitch = 0;
                currentBodyPitch = 0;
                finalBodyPitch = 0;

                currentBodyYawForRoll = 0;
                currentBodyYaw = 0;
            }

        }
    }

    public float getCurrentBodyPitch() {
        return currentBodyPitch;
    }

    public void setCurrentBodyPitch(float currentBodyPitch) {
        this.currentBodyPitch = currentBodyPitch;
    }

    public float getCurrentBodyYaw() {
        return currentBodyYaw;
    }

    public void setCurrentBodyYaw(float currentBodyYaw) {
        this.currentBodyYaw = currentBodyYaw;
    }

    public float getCurrentBodyYawForRoll() {
        return currentBodyYawForRoll;
    }

    public void setCurrentBodyYawForRoll(float currentBodyYawForRoll) {
        this.currentBodyYawForRoll = currentBodyYawForRoll;
    }

    void approachValue(GeoBone bone, float current, float goal) {
        if (current < goal) {
            bone.setRotationX(current + 1);
        }
    }

    float approach(float flGoal, float flCurrent, float dt) {
        float flDifference = Mth.degreesDifference(flCurrent, flGoal);
        if (flDifference > dt) {
            return flCurrent + dt;
        }
        if (flDifference < -dt) {
            return flCurrent - dt;
        }

        return flGoal;
    }

    protected boolean hasDynamicYawAndRoll() {
        return true;
    }

    protected int getMaxRise() {
        return 40;
    }

    protected int getMinRise() {
        return -40;
    }

    /**
     * Used to clamp a given value between a Minimum and Maximum value.
     *
     * @param val The given Value to be clamped.
     * @param min The Minimum value the given value should have.
     * @param max The Maximum value the given value should have.
     * @return A Value based on the given Value, while ensuring its fitting the Minimum to Maximum range.
     */
    private float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }
}


//    /**
//     * This method is used to modify the models of Entities. It gets called between the rotation values thus making it ideal for manipulations that require rotation updates.
//     */
//    protected void modifyPitch(GeoModel model, T dragon, float partialTicks, RenderType type, PoseStack
//            matrixStackIn, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder,
//                               int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha, float s) {
////        currentBodyPitch = getBone(model, getMainBodyBone()).get().getRotationX();
//        boostedBodyPitch = Mth.lerp(0.001F, dragon.xRotO, getMaxRise());
////        boostedBodyPitch = getMaxRiseBoost();
//
//        // player xRot and geckolib rotations are flipped in negative and positive values
//        // xRotO is prevXRot or prevPitch or xRotOld
//        if (dragon.getPassengers().size() < 2) {
//            if (dragon.isFlying()) {
//                if (dragon.getControllingPassenger() instanceof Player player) {
////                    dragon.setXRot(player.getXRot());
//                    dragon.setXRot((float) Mth.lerp(0.2, player.xRotO, player.getXRot()));
//                    dragon.setXRot(MathX.clamp(dragon.getXRot(), -45, 40));
//                }
//                currentBodyPitch = toRadians(dragon.getXRot());
//            } else if (!dragon.isFlying()) {
//                // reset pitch to default when walking on ground
//                currentBodyPitch = 0;
//            }
//            GeoBone bone = getBone(model, getMainBodyBone()).get();
//
//            // 1 radian (90) is facing straight up
//            // 2 radians (180) is facing it's face towards the camera in straight line with it's back lying towards the ground
//            // rotation is dragon's vertical clockwise
//            // -currentBodyPitch is geckoLib x rot
//            // getMaxRise() = 30 is -0.4803983 java radians
//            if (dragon.isFlying()) {
//                // a separate variable for the inversed pitch
//                float bodyPitchInversed = -currentBodyPitch;
//                // setRotationPitch or X by interpolating between boostedBodyPitch and lookBodyPitch(currentBodyPitch)
//                if (dragon.isGoingUp()) {
//                    bone.setRotationX(toRadians(getMaxRise()));
////                    bone.setRotationX((float) Mth.clamp(Mth.lerp(0.2, dragon.xRotO, getMaxRiseBoost()),,getMaxRise()));
//                } else {
//                    bone.setRotationX(bodyPitchInversed);
//                }
//            }
//
//
////            TODO: smooth appraoch between the circumference of the rotation arc of the dragon
////             when it boosts up and returns to currentBodyPitch controlled by the player's view and mouse rotation.
////             Try making a new method instead of setting a rotation rather transitioning that rotation
//
////            supposed to be xRot
////            System.out.println("geckolib x rot is: " + bone.getRotationX());
////             mojang xRot
////            System.out.println("Mojang x rot is: " + dragon.getXRot());
////             radian of dragon.getXRot()
////            System.out.println("currentBodyPitch is: " + currentBodyPitch);
////            System.out.println("- currentBodyPitch is: " + -currentBodyPitch);
////            System.out.println();
//        }
//    }

