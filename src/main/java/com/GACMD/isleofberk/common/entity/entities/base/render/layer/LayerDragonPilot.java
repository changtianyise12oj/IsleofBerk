package com.GACMD.isleofberk.common.entity.entities.base.render.layer;

import com.GACMD.isleofberk.common.entity.entities.base.ADragonBaseFlyingRideable;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class LayerDragonPilot<T extends ADragonBaseFlyingRideable & IAnimatable> extends GeoLayerRenderer<T> {
    protected final EntityRendererProvider.Context entityRenderDispatcher;

    public LayerDragonPilot(IGeoRenderer<T> entityRendererIn, EntityRendererProvider.Context renderManager) {
        super(entityRendererIn);
        this.entityRenderDispatcher = renderManager;
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T dragon, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        matrixStackIn.pushPose();
//        if (!dragon.getPassengers().isEmpty()) {
//            if (dragon.isFlying()) {
//                float dragonScale = dragon.getScale();
//                for (Entity passenger : dragon.getPassengers()) {
//                    matrixStackIn.translate(0, -0.01F * dragonScale, -0.035F * dragonScale);
//
//                    float riderRot1 = dragon.getChangeInYaw();
//                    float riderRot = passenger.yRotO + (passenger.getYRot() - passenger.yRotO) * partialTicks;
//
//                    matrixStackIn.mulPose(new Quaternion(Vector3f.ZP, riderRot1, true));
//                    matrixStackIn.mulPose(new Quaternion(Vector3f.YP, riderRot + 180, true));
//                    matrixStackIn.scale(1 / dragonScale, 1 / dragonScale, 1 / dragonScale);
//                    matrixStackIn.translate(0, 2.25F, 0);
//                    renderEntity(passenger, 0.0D, 0.0D, 0.0D, 0.0F, partialTicks, matrixStackIn, bufferIn, packedLightIn);
//                }
//            }
//        }
        matrixStackIn.popPose();
    }

    public static <T extends Entity> EntityRenderer<T> getRenderer(T entity) {
        EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
        return (EntityRenderer<T>) renderManager.getRenderer(entity);
    }


    public <E extends Entity> void renderEntity(E entityIn, double x, double y, double z, float yaw, float partialTicks, PoseStack matrixStack, MultiBufferSource bufferIn, int packedLight) {
        EntityRenderer<? super E> render = getRenderer(entityIn);

        render.render(entityIn, 0, partialTicks, matrixStack, bufferIn, packedLight);
    }

}
