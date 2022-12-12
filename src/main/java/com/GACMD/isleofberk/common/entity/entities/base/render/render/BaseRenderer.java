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

    protected BaseRenderer(EntityRendererProvider.Context renderManager, AnimatedGeoModel<T> modelProvider) {
        super(renderManager, modelProvider);
        this.addLayer(new BaseSaddleLayer<>(this));
    }

    protected String getMainBodyBone() {
        return "Chest";
    }

    /**
     * Get bone also checks if the bone is present
     *
     * @return
     */
    public Optional<GeoBone> getBone(GeoModel model, String boneString) {
        return model.getBone(boneString);
    }

    @Override
    public void render(GeoModel model, T dragon, float partialTicks, RenderType type, PoseStack matrixStackIn, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        super.render(model, dragon, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        if(dragon instanceof ADragonRideableUtility dragonRideableUtility && !dragonRideableUtility.guiLocked()) {
            getBone(model, "Bags").get().setHidden(!dragonRideableUtility.hasChest());
        }
    }
    // dragon textures location
    public String getDragonFolder() {
        return "";
    }
    private static final float DEGREES_TO_RADIANS = 0.017453292519943295F;

    public static float toRadians(float angdeg) {
        return angdeg * DEGREES_TO_RADIANS;
    }


}
