package com.GACMD.isleofberk.common.entity.entities.dragons.skrill;

import com.GACMD.isleofberk.common.entity.entities.base.render.render.BaseRendererFlying;
import com.GACMD.isleofberk.common.entity.entities.projectile.breath_user.skrill_lightning.LightningBoltData;
import com.GACMD.isleofberk.common.entity.entities.projectile.breath_user.skrill_lightning.LightningRender;
import com.GACMD.isleofberk.common.entity.entities.projectile.breath_user.skrill_lightning.RayTracer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.geo.render.built.GeoModel;

public class SkrillRenderer extends BaseRendererFlying<Skrill> {

    LightningRender lightningRender = new LightningRender();

    protected SkrillRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SkrillModel(renderManager));
    }

    @Override
    public RenderType getRenderType(Skrill skrill, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        return RenderType.entityCutoutNoCull(getTextureLocation(skrill));
    }

    public void renderEarly(Skrill skrill, PoseStack stackIn, float ticks,
                            @javax.annotation.Nullable MultiBufferSource renderTypeBuffer, @javax.annotation.Nullable VertexConsumer vertexBuilder, int packedLightIn,
                            int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        if (skrill.isBaby()) {
            stackIn.scale(0.555F / 3, 0.555F / 3, 0.555F / 3);

        } else {
            stackIn.scale(0.555F, 0.555F, 0.555F);
        }
    }

    @Override
    public void render(GeoModel model, Skrill skrill, float partialTicks, RenderType type, PoseStack stack, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        super.render(model, skrill, partialTicks, type, stack, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        stack.pushPose();
        stack.pushPose();
        if (skrill.getControllingPassenger() != null && skrill.getControllingPassenger() instanceof Player player) {
            if (skrill.isUsingAbility()) {
                assert Minecraft.getInstance().player != null;
                double dist = Minecraft.getInstance().player.distanceTo(skrill);
                if (dist <= Math.max(256, Minecraft.getInstance().gameRenderer.getRenderDistance() * 16F)) {

                    HitResult hitResult = RayTracer.rayTrace(skrill.level, skrill, player, skrill.tickCount, 180);
                    Vec3 throatPos = skrill.getThroatPos(skrill);

                    // pitch is inversed on throat pos
                    Vec3 start = new Vec3(throatPos.x(), throatPos.y(), throatPos.z() - 5);
                    Vec3 end = new Vec3(-Math.cos(hitResult.getLocation().x()), -Math.sin(hitResult.getLocation().y()), Math.sin(hitResult.getLocation().z()));

                    LightningBoltData bolt = new LightningBoltData(LightningBoltData.BoltRenderInfo.ELECTRICITY, start, end, 8)
                            .size(0.10F)
                            .lifespan(4)
                            .spawn(LightningBoltData.SpawnFunction.NO_DELAY);

                    lightningRender.update(null, bolt, partialTicks);
                    stack.translate(-skrill.getX(), -skrill.getY(), -skrill.getZ());
                    assert renderTypeBuffer != null;
                    lightningRender.render(partialTicks, stack, renderTypeBuffer);
                }
            }
        }
        stack.popPose();
    }

    private static float getBoundedScale(float scale, float min, float max) {
        return min + scale * (max - min);
    }

    @Override
    public String getDragonFolder() {
        return "skrill";
    }

}
