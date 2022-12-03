//package com.GACMD.isleofberk.common.entity.entities.projectile.breath_user.skrill_lightning;
//
//import com.GACMD.isleofberk.common.entity.entities.base.ADragonBaseFlyingRideableBreathUser;
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.blaze3d.vertex.VertexConsumer;
//import com.mojang.math.Matrix4f;
//import net.minecraft.client.renderer.MultiBufferSource;
//import net.minecraft.client.renderer.RenderType;
//import net.minecraft.client.renderer.entity.EntityRenderer;
//import net.minecraft.client.renderer.entity.EntityRendererProvider;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.inventory.InventoryMenu;
//import net.minecraft.world.phys.EntityHitResult;
//import net.minecraft.world.phys.Vec3;
//
//public class SkrillLightningRenderer extends EntityRenderer<S> {
//
//    public SkrillLightningRenderer(EntityRendererProvider.Context context) {
//        super(context);
//    }
//
//    @Override
//    public ResourceLocation getTextureLocation(Entity entity) {
//        return null;
//    }
//
//    public void render(SkrillLightning pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
//        VertexConsumer vertexconsumer = pBuffer.getBuffer(RenderType.lightning());
//        Matrix4f matrix4f = pMatrixStack.last().pose();
//
//        if (pEntity.getSkrill() != null && pEntity.getSkrill().getVehicle()!= null && pEntity.getSkrill().getVehicle() instanceof Player pilot) {
//            EntityHitResult entityHitResult = RayTracer.rayTraceEntities(pilot.level, pEntity.getSkrill(),pEntity, pilot, pEntity.tickCount, 80);
//            ADragonBaseFlyingRideableBreathUser skrill = pEntity.getSkrill();
//            Vec3 start = skrill.getThroatPos(skrill);
//            Vec3 end = pEntity.getHitResult().getLocation();
////            vertexconsumer.vertex(matrix4f, (float) start.x, (float) start.z, (float) start.y).color(0.45F, 0.45F, 0.5F, 0.3F).endVertex();
////            vertexconsumer.vertex(matrix4f, (float) end.x, (float) end.z, (float) end.y).color(0.45F, 0.45F, 0.5F, 0.3F).endVertex();
//            pMatrixStack.pushPose();
//            pMatrixStack.translate(0F, 0.5F, 0F);
//            pMatrixStack.translate(0F, -0.25F, 0F);
//            pMatrixStack.translate(0F, 0.25F, 0F);
//            pMatrixStack.popPose();
//
//            System.out.println("start: " + start);
//            System.out.println("end: " + end);
//        }
//
//    }
//
//    private static void quad(Matrix4f matrix, VertexConsumer vertexConsumer, float pX, float pY, int pZ, float p_115278_, float p_115279_, float r, float g, float b, float p_115283_, float p_115284_, boolean boolean1, boolean boolean2, boolean boolean3, boolean boolean4) {
//        vertexConsumer.vertex(matrix, pX + (boolean1 ? p_115284_ : -p_115284_), (float) (pZ * 16), pY + (boolean2 ? p_115284_ : -p_115284_)).color(r, g, b, 0.3F).endVertex();
//        vertexConsumer.vertex(matrix, p_115278_ + (boolean1 ? p_115283_ : -p_115283_), (float) ((pZ + 1) * 16), p_115279_ + (boolean2 ? p_115283_ : -p_115283_)).color(r, g, b, 0.3F).endVertex();
//        vertexConsumer.vertex(matrix, p_115278_ + (boolean3 ? p_115283_ : -p_115283_), (float) ((pZ + 1) * 16), p_115279_ + (boolean4 ? p_115283_ : -p_115283_)).color(r, g, b, 0.3F).endVertex();
//        vertexConsumer.vertex(matrix, pX + (boolean3 ? p_115284_ : -p_115284_), (float) (pZ * 16), pY + (boolean4 ? p_115284_ : -p_115284_)).color(r, g, b, 0.3F).endVertex();
//    }
//
//    @Override
//    public ResourceLocation getTextureLocation(SkrillLightning pEntity) {
//        return InventoryMenu.BLOCK_ATLAS;
//    }
//}
