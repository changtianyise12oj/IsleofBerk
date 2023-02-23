package com.GACMD.isleofberk.entity.eggs.entity.base.large;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class ADragonLargeEggRenderer extends GeoProjectilesRenderer<ADragonLargeEggBase> {

    public ADragonLargeEggRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ADragonLargeEggModel());
    }

    @Override
    public void render(ADragonLargeEggBase entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, yaw, partialTicks, poseStack, buffer, packedLight);
        if (entity.displayProgressTicks != 0) {
            poseStack.pushPose();
            poseStack.translate(0.0F, entity.getBbHeight() + 0.0625F, 0.0F);
            poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
            poseStack.scale(-0.025F, -0.025F, 0.025F);
            float scale = 0.5F;
            poseStack.scale(scale, scale, scale);
            String text = entity.getHatchProgress() + "%";
            Font font = Minecraft.getInstance().font;
            float centerOffset = (float) (-font.width(text) / 2);
            font.drawInBatch(text, centerOffset, -9, -1, false, poseStack.last().pose(), buffer, false, 0, packedLight);
            poseStack.popPose();
        }
    }

    @Override
    public RenderType getRenderType(ADragonLargeEggBase animatable, float partialTicks, PoseStack stack, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityCutoutNoCull(textureLocation);
    }

    @Override
    public ResourceLocation getTextureLocation(ADragonLargeEggBase dragonEggBase) {
        return dragonEggBase.getTextureLocation(dragonEggBase);
    }
}
