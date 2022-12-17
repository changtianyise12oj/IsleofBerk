package com.GACMD.isleofberk.entity.base.render.layer;

import com.GACMD.isleofberk.entity.dragons.terrible_terror.TerribleTerror;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

@OnlyIn(Dist.CLIENT)
public class DragonHeldItemLayer extends GeoLayerRenderer<TerribleTerror> {
    public DragonHeldItemLayer(IGeoRenderer<TerribleTerror> entityRendererIn) {
        super(entityRendererIn);
    }

    public void render(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, TerribleTerror pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        boolean flag = pLivingEntity.isSleeping();
        boolean flag1 = pLivingEntity.isBaby();
        pMatrixStack.pushPose();
        if (flag1) {
            float f = 0.75F;
            pMatrixStack.scale(0.90F, 0.90F, 0.90F);
            pMatrixStack.translate(0.0D, 0.5D, (double) 0.209375F);
            pMatrixStack.translate(-0.1D, -0.2D, -0.2D);
        } else {
            pMatrixStack.translate(-0.1D, 0.0D, 0.1D);
        }
        if (pLivingEntity.isDragonSleeping()) {
            pMatrixStack.translate(-0.05D, -0.05D, -0.5D);
//            pMatrixStack.mulPose(Vector3f.XP.rotationDegrees(65.0F));
//            pMatrixStack.mulPose(Vector3f.ZP.rotationDegrees(20.0F));
            pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(40.0F));
            pMatrixStack.mulPose(Vector3f.XP.rotationDegrees(-15.0F));
        } else {
            pMatrixStack.translate(0.15D, pLivingEntity.getVehicle() == null ? 0.3D : 0.55D, pLivingEntity.getVehicle() == null ? -0.6D : -0.58D);

        }
        pMatrixStack.translate(0, -0.0D, 0.1D);

        pMatrixStack.mulPose(Vector3f.XP.rotationDegrees(65.0F));
        pMatrixStack.mulPose(Vector3f.ZP.rotationDegrees(135.0F));

        ItemStack itemstack = pLivingEntity.getItemBySlot(EquipmentSlot.MAINHAND);
        Minecraft.getInstance().getItemInHandRenderer().renderItem(pLivingEntity, itemstack, ItemTransforms.TransformType.GROUND, false, pMatrixStack, pBuffer, pPackedLight);
        pMatrixStack.popPose();
    }
}