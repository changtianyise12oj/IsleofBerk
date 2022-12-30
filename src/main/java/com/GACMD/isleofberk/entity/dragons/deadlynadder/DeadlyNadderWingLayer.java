package com.GACMD.isleofberk.entity.dragons.deadlynadder;

import com.GACMD.isleofberk.IsleofBerk;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

@OnlyIn(Dist.CLIENT)
public class DeadlyNadderWingLayer<T extends DeadlyNadder & IAnimatable> extends GeoLayerRenderer<T> {

    private final ResourceLocation dragonModel = new ResourceLocation(IsleofBerk.MOD_ID, "geo/dragons/deadly_nadder.geo.json");

    public DeadlyNadderWingLayer(IGeoRenderer<T> entityRendererIn, EntityRendererProvider.Context renderManager) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T dragon, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        RenderType cameo = RenderType.entityCutout(getNadderEntityTexture(dragon));
            this.getRenderer().render(this.getEntityModel().getModel(dragonModel), dragon, partialTicks, cameo, matrixStackIn, bufferIn,
                    bufferIn.getBuffer(cameo), packedLightIn, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
    }

    protected ResourceLocation getNadderEntityTexture(DeadlyNadder entity) {
        return switch (entity.getDragonVariant()) {
            case 0 -> new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/deadly_nadder/stormfly_membranes.png");
            case 1 -> new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/deadly_nadder/deadly_nadder_membranes.png");
            case 2 -> new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/deadly_nadder/kingstail_membranes.png");
            case 3 -> new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/deadly_nadder/scardian_membranes.png");
            case 4 -> new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/deadly_nadder/springshedder_membranes.png");
            case 5 -> new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/deadly_nadder/hjarta_membranes.png");
            case 6 -> new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/deadly_nadder/bork_week_membranes.png");
            case 7 -> new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/deadly_nadder/flystorm_membranes.png");
            case 8 -> new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/deadly_nadder/hjaldr_membranes.png");
            case 9 -> new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/deadly_nadder/barklethorn_membranes.png");
            case 10 -> new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/deadly_nadder/lethal_lancebeak_membranes.png");
            default -> throw new IllegalStateException("Unexpected value: " + entity.getDragonVariant());
        };
    }

    @Override
    public RenderType getRenderType(ResourceLocation textureLocation) {
        return RenderType.entityCutout(textureLocation);
    }
}
