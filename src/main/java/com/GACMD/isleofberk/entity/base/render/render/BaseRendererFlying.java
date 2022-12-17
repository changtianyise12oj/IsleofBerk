package com.GACMD.isleofberk.entity.base.render.render;

import com.GACMD.isleofberk.entity.dragons.nightfury.NightFury;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideable;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;

@OnlyIn(Dist.CLIENT)
public class BaseRendererFlying<T extends ADragonBaseFlyingRideable & IAnimatable> extends BaseRenderer<T> {

    public float changeInYaw;
    protected float currentBodyPitch;
    protected float currentBodyYawForRoll;
    protected float currentBodyYaw;
    protected float boostedBodyPitch;
    protected float finalBodyPitch;
    int pitch = 0;

    protected BaseRendererFlying(EntityRendererProvider.Context renderManager, AnimatedGeoModel<T> modelProvider) {
        super(renderManager, modelProvider);
    }

    @Override
    public void render(GeoModel model, T dragon, float partialTicks, RenderType type, PoseStack matrixStackIn, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (!dragon.isRenderedOnGUI())
            modifyPitch(model, dragon, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        // Lastly we super the render method.
        super.render(model, dragon, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    protected String getMainBodyBone() {
        return "Chest";
    }

    /**
     * Mojang yaw pitch and roll and Geckolib yaw pitch and roll are inversed, use negative and positive values
     */
    protected void
    modifyPitch(GeoModel model, T dragon, float partialTicks, RenderType type, PoseStack matrixStackIn, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder,
                int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        GeoBone body = getBone(model, getMainBodyBone()).get();
        if (dragon.getPassengers().size() < 2 || dragon.getControllingPassenger() == null) {
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
                    finalBodyPitch = currentBodyPitch + boostedBodyPitch;
                    body.setRotationX(toRadians(Mth.clamp(-finalBodyPitch, getMinRise(), getMaxRise())));

                }

                if (hasDynamicYawAndRoll() && dragon.getControllingPassenger() instanceof Player) {
                    float f = Mth.rotLerp(partialTicks, dragon.yBodyRotO, dragon.yBodyRot);
                    float f1 = Mth.rotLerp(partialTicks, dragon.yHeadRotO, dragon.yHeadRot);
                    changeInYaw = (f1 - f) * ((float) Math.PI / 180F) * -1.5F;
                    dragon.setChangeInYaw((f1 - f) * ((float) Math.PI / 180F) * -1.5F);
//                         the rotation is based on yaw
                    body.setRotationZ(Mth.clamp(dragon.getChangeInYaw(), -8, 8));
                    body.setRotationZ(changeInYaw);
                } else if (dragon.getControllingPassenger() == null) {
                    currentBodyPitch = Mth.lerp(0.1F, dragon.xRotO, getMaxRise());
                }
                if (dragon.isDragonFollowing() && dragon.getOwner() instanceof Player player && dragon instanceof NightFury nightFury) {
                    double ydist = nightFury.getY() - player.getY();
                    if (ydist > 8.3F) {
                        pitch -= 4;
                        body.setRotationX(toRadians(Mth.clamp(pitch, -90, 0)));
                        System.out.println("rotX" + body.getRotationX());
                        System.out.println("pitch" + pitch);
                    } else {
                        pitch = 0;
                    }
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


    public boolean hasDynamicYawAndRoll() {
        return true;
    }

    protected int getMaxRise() {
        return 40;
    }

    protected int getMinRise() {
        return -40;
    }
}
