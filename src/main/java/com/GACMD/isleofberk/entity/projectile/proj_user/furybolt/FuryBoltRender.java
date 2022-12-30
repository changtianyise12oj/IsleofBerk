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
        return RenderType.entityCutoutNoCull(getTextureLocation(animatable));
    }

    @Override
    public void renderEarly(FuryBolt animatable, PoseStack stackIn, float ticks, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
        if (animatable.getOwner() instanceof NightFury furyEntity) {
            if(animatable.getProjectileSize() == 0) {
                stackIn.scale(2.2F, 2.2F, 2.2F);
            } else if(animatable.getProjectileSize() == 1) {
                stackIn.scale(2.6F, 2.6F, 2.6F);
            } else if(animatable.getProjectileSize() == 2) {
                stackIn.scale(3.1F, 3.1F, 3.1F);
            } else if(animatable.getProjectileSize() == 3) {
                stackIn.scale(3.1F, 3.1F, 3.1F);
            }
        }
    }

    @Override
    public void render(GeoModel model, FuryBolt animatable, float partialTicks, RenderType type, PoseStack matrixStackIn, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        super.render(model, animatable, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

}
