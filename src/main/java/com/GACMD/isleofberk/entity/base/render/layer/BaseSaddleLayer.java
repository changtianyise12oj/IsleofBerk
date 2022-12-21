package com.GACMD.isleofberk.entity.base.render.layer;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import com.GACMD.isleofberk.entity.base.dragon.ADragonRideableUtility;
import com.GACMD.isleofberk.entity.base.render.render.BaseRenderer;
import com.GACMD.isleofberk.entity.base.render.render.BaseRendererFlying;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

import java.util.Optional;

public class BaseSaddleLayer<T extends ADragonBase & IAnimatable> extends GeoLayerRenderer<T> {

    BaseRenderer<T> baseRenderer;

    public float changeInYaw;
    protected float currentBodyPitch;
    protected float currentBodyYawForRoll;
    protected float currentBodyYaw;
    protected float boostedBodyPitch;
    protected float finalBodyPitch;
    int pitch = 0;
    private static final float DEGREES_TO_RADIANS = 0.017453292519943295F;

    public BaseSaddleLayer(IGeoRenderer<T> entityRendererIn) {
        super(entityRendererIn);
        this.baseRenderer = (BaseRenderer<T>) entityRendererIn;
    }

    /**
     * Get bone also checks if the bone is present
     *
     * @return
     */
    public Optional<GeoBone> getBone(GeoModel model, String boneString) {
        return model.getBone(boneString);
    }

    public static float toRadians(float angdeg) {
        return angdeg * DEGREES_TO_RADIANS;
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T dragon1, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

        // to add a saddle, use the getDragonFolder in the base renderer, it will get a saddle.png inside the folder
        if (dragon1 instanceof ADragonRideableUtility dragonRideableUtility) {
            if (dragonRideableUtility.isSaddled()) {
                if(baseRenderer instanceof BaseRendererFlying rendererFlying) {
                    RenderType cameo = RenderType.entityCutout(getSaddleTexture());
                    GeoModel model = getEntityModel().getModel(baseRenderer.getGeoModelProvider().getModelLocation(dragon1));
                    this.getRenderer().render(model, dragon1, partialTicks, cameo, matrixStackIn, bufferIn,
                            bufferIn.getBuffer(cameo), packedLightIn, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
                    if (getBone(model, "Saddle").isPresent()) {
                        getBone(model, "Saddle").get().setRotationX(toRadians(Mth.clamp(-finalBodyPitch, rendererFlying.getMinRise(), rendererFlying.getMaxRise())));
                    }
                }
            }
        }
    }

    protected ResourceLocation getSaddleTexture() {
        return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/" + baseRenderer.getDragonFolder() + "/saddle.png");
    }
}
