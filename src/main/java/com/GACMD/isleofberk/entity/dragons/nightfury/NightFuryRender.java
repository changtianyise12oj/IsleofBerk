package com.GACMD.isleofberk.entity.dragons.nightfury;

import com.GACMD.isleofberk.entity.base.render.render.BaseRendererFlying;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.geo.render.built.GeoModel;

@OnlyIn(Dist.CLIENT)
public class NightFuryRender extends BaseRendererFlying<NightFury> {

    public NightFuryRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new NightFuryModel());
//        this.addLayer(new NightFuryGlowLayer<>(this));
    }

    @Override
    public RenderType getRenderType(NightFury animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        return RenderType.entityCutoutNoCull(getTextureLocation(animatable));
    }

    @Override
    public void render(GeoModel model, NightFury animatable, float partialTicks, RenderType type, PoseStack matrixStackIn, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (!animatable.isInvisible()) {
            super.render(model, animatable, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }
    }

    @Override
    protected int getMaxRise() {
        return 45;
    }

    @Override
    protected int getMinRise() {
        return -75;
    }

    @Override
    public String getDragonFolder() {
        return "night_fury";
    }
}
