package com.GACMD.isleofberk.entity.projectile.proj_user.furybolt;

import com.GACMD.isleofberk.entity.dragons.nightfury.NightFury;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class FuryBoltRender extends GeoProjectilesRenderer<FuryBolt> {

    public FuryBoltRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new FuryBoltModel());
    }

    @Override
    public RenderType getRenderType(FuryBolt animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        return RenderType.eyes(getTextureLocation(animatable));
    }

    @Override
    public void renderEarly(FuryBolt animatable, PoseStack stackIn, float ticks, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);

        float scale;
        switch (animatable.getProjectileSize()) {
            default -> scale = 1.5F;
            case 1 -> scale = 2.0F;
            case 2 -> scale = 2.5F;
            case 3 -> scale = 3.0F;
        }
        stackIn.scale(scale, scale, scale);
    }

    @Override
    public void render(GeoModel model, FuryBolt animatable, float partialTicks, RenderType type, PoseStack matrixStackIn, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        super.render(model, animatable, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

}
