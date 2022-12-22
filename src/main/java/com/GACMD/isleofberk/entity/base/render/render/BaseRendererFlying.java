package com.GACMD.isleofberk.entity.base.render.render;

import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideable;
import com.GACMD.isleofberk.entity.dragons.deadlynadder.DeadlyNadder;
import com.GACMD.isleofberk.entity.dragons.montrous_nightmare.MonstrousNightmare;
import com.GACMD.isleofberk.entity.dragons.nightfury.NightFury;
import com.GACMD.isleofberk.entity.dragons.tryiple_stryke.TripleStryke;
import com.GACMD.isleofberk.entity.dragons.zippleback.ZippleBack;
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
    public float finalBodyPitch;
    int pitch = 0;

    protected BaseRendererFlying(EntityRendererProvider.Context renderManager, AnimatedGeoModel<T> modelProvider) {
        super(renderManager, modelProvider);
//        this.addLayer(new LayerDragonRider<>(this));
    }

    @Override
    public void render(GeoModel model, T dragon, float partialTicks, RenderType type, PoseStack matrixStackIn, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (!dragon.isRenderedOnGUI())
            modifyPitch(model, dragon, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        // Lastly we super the render method.
        super.render(model, dragon, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    public String getMainBodyBone() {
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
//                    if (dragon.isGoingUp() && boostedBodyPitch >= -40) {
//                        boostedBodyPitch -= 1;
//                    } else if (!dragon.isGoingUp()) {
//                    }

                    if (!dragon.isGoingUp()) {
                        // +90 dive -90 rise
                        currentBodyPitch = Mth.lerp(0.1F, pilot.xRotO, getMaxRise());
                        finalBodyPitch = currentBodyPitch;
                        body.setRotationX(toRadians(Mth.clamp(-finalBodyPitch, getMinRise(), getMaxRise())));
                    } else {
                        body.setRotationX(getMaxRise());
                    }
                }

                if (dragon.getControllingPassenger() == null) {
                    currentBodyPitch = Mth.lerp(0.1F, dragon.xRotO, getMaxRise());
                }
                if (dragon.isDragonFollowing() && dragon.getOwner() instanceof Player player && (dragon instanceof NightFury || dragon instanceof DeadlyNadder || dragon instanceof TripleStryke || dragon instanceof MonstrousNightmare || dragon instanceof ZippleBack) && player.getVehicle() == null) {
                    double ydist = dragon.getY() - player.getY();
                    if (ydist > 8.3F) {
                        pitch -= 4;
                        body.setRotationX(toRadians(Mth.clamp(pitch, -90, 0)));
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

    public int getMaxRise() {
        return 40;
    }

    public int getMinRise() {
        return -40;
    }
}

