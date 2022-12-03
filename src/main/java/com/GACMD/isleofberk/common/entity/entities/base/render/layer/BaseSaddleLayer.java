package com.GACMD.isleofberk.common.entity.entities.base.render.layer;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.common.entity.entities.base.ADragonBase;
import com.GACMD.isleofberk.common.entity.entities.base.ADragonRideableUtility;
import com.GACMD.isleofberk.common.entity.entities.base.render.render.BaseRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class BaseSaddleLayer<T extends ADragonBase & IAnimatable> extends GeoLayerRenderer<T> {

    BaseRenderer<T> baseRenderer;

    public BaseSaddleLayer(IGeoRenderer<T> entityRendererIn) {
        super(entityRendererIn);
        this.baseRenderer = (BaseRenderer<T>) entityRendererIn;
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T dragon, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
//        if (dragon instanceof ADragonRideableUtility dragonRideableUtility) {
//            if (dragonRideableUtility.isSaddled()) {
//                RenderType pattern = RenderType.entityTranslucent(getSaddleTexture());
//                getRenderer().render(getEntityModel().getModel(baseRenderer.getGeoModelProvider().getModelLocation(dragon)), dragon, partialTicks, pattern, matrixStackIn, bufferIn, bufferIn.getBuffer(pattern), packedLightIn, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
//            }
//            matrixStackIn.translate(1, -3, 1);
//        }
        // to add a saddle, use the getDragonFolder in the base renderer, it will get a saddle.png inside the folder
        if (dragon instanceof ADragonRideableUtility dragonRideableUtility) {
            if (dragonRideableUtility.isSaddled()) {
                RenderType cameo = RenderType.entityCutout(getSaddleTexture());
                matrixStackIn.pushPose();
                //Move or scale the model as you see fit
//        float scale = 1.15f;
//        matrixStackIn.scale(scale, scale, scale);
//        matrixStackIn.translate(-0.88d, 2.6d, -0.90d);
                matrixStackIn.translate(baseRenderer.getSaddleX(), baseRenderer.getSaddleY(), baseRenderer.getSaddleZ());
                matrixStackIn.scale(baseRenderer.getSaddleScaleX(), baseRenderer.getSaddleScaleY(), baseRenderer.getSaddleScaleZ());
//                matrixStackIn.translate(1, -3, 1);
                this.getRenderer().render(getEntityModel().getModel(baseRenderer.getGeoModelProvider().getModelLocation(dragon)), dragon, partialTicks, cameo, matrixStackIn, bufferIn,
                        bufferIn.getBuffer(cameo), packedLightIn, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
                matrixStackIn.popPose();
            }
        }
    }

    protected ResourceLocation getSaddleTexture() {
        return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/" + baseRenderer.getDragonFolder() + "/saddle.png");
    }
}
