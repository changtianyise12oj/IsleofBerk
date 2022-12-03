package com.GACMD.isleofberk.common.entity.entities.dragons.deadlynadder;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.common.entity.entities.base.render.render.BaseRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

/**
 * Separate wing layer for nadders since their wings are rendered with a different cutOutWithNoCull
 * @param <T>
 */
public class DeadlyNadderWingLayer<T extends DeadlyNadder & IAnimatable> extends GeoLayerRenderer<T> {

    BaseRenderer<T> baseRenderer;
    private final ResourceLocation dragonModel = new ResourceLocation(IsleofBerk.MOD_ID, "geo/dragons/deadly_nadder.geo.json");

    public DeadlyNadderWingLayer(IGeoRenderer<T> entityRendererIn, EntityRendererProvider.Context renderManager) {
        super(entityRendererIn);
        this.baseRenderer = (BaseRenderer<T>) entityRendererIn;
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T dragon, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        RenderType cameo =  RenderType.entityCutout(getNadderEntityTexture(dragon));
        matrixStackIn.pushPose();
        //Move or scale the model as you see fit
        if(dragon.isBaby()) {
            float scale = 2.85f;
            matrixStackIn.scale(scale * baseRenderer.getScale(), scale * baseRenderer.getScale(), scale * baseRenderer.getScale());
//        matrixStackIn.translate(-0.88d, 2.6d, -0.90d);
            matrixStackIn.translate(baseRenderer.getScale() * -0.001, baseRenderer.getScale() * -0.00F, baseRenderer.getScale() * -0F);
            this.getRenderer().render(this.getEntityModel().getModel(dragonModel), dragon, partialTicks, cameo, matrixStackIn, bufferIn,
                    bufferIn.getBuffer(cameo), packedLightIn, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
        }else {
            float scale = 1.2f;
            matrixStackIn.scale(scale * baseRenderer.getScale(), scale * baseRenderer.getScale(), scale * baseRenderer.getScale());
//        matrixStackIn.translate(-0.88d, 2.6d, -0.90d);
            matrixStackIn.translate(baseRenderer.getScale() * -0.001, baseRenderer.getScale() * 0.04, baseRenderer.getScale() * -0.05F);
            this.getRenderer().render(this.getEntityModel().getModel(dragonModel), dragon, partialTicks, cameo, matrixStackIn, bufferIn,
                    bufferIn.getBuffer(cameo), packedLightIn, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
        }
        matrixStackIn.popPose();
    }

    protected ResourceLocation getNadderEntityTexture(DeadlyNadder entity) {
        return switch (entity.getDragonVariant()) {
            case 0 -> new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/deadly_nadder/stormfly_membranes.png");
            case 1 -> new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/deadly_nadder/scardian_membranes.png");
            case 2 -> new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/deadly_nadder/red_membranes.png");
            case 3 -> new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/deadly_nadder/purple_membranes.png");
            case 4 -> new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/deadly_nadder/pink_membranes.png");
            case 5 -> new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/deadly_nadder/mint_membranes.png");
            case 6 -> new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/deadly_nadder/green_membranes.png");
            case 7 -> new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/deadly_nadder/flystorm_membranes.png");
            case 8 -> new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/deadly_nadder/dark_purple_membranes.png");
            case 9 -> new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/deadly_nadder/brown_membranes.png");
            case 10 -> new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/deadly_nadder/blue_membranes.png");
            default -> throw new IllegalStateException("Unexpected value: " + entity.getDragonVariant());
        };
    }

    @Override
    public RenderType getRenderType(ResourceLocation textureLocation) {
        return RenderType.entityCutout(textureLocation);
    }
}
