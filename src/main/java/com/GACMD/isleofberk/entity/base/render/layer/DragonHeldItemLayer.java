package com.GACMD.isleofberk.entity.base.render.layer;

import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideable;
import com.GACMD.isleofberk.entity.dragons.terrible_terror.TerribleTerror;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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

    public void render(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, TerribleTerror terror, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        boolean flag = terror.isSleeping();
        boolean baby = terror.isBaby();
        pMatrixStack.pushPose();
        Entity entity = terror.getVehicle();

        if (baby) {
            pMatrixStack.scale(0.60F, 0.60F, 0.60F);
        } else {
            pMatrixStack.translate(-0.1D, -0.05D, 0.1D);
        }
        if (terror.isDragonSitting() && !terror.isDragonSleeping()) {
            pMatrixStack.translate(0.0D,  (baby ? 0.18D : 0.10D), (baby ? 0.1D : -0.03D));
            pMatrixStack.mulPose(Vector3f.XP.rotationDegrees(10.0F));
        } else if (terror.isDragonSleeping()) {
            pMatrixStack.translate((baby ? 0.1D : 0.3D), (baby ? -0.12D : -0.24D), (baby ? 0.1D : 0.0D));
            pMatrixStack.mulPose(Vector3f.XP.rotationDegrees(0F));
            pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(40F));
        } else if (entity != null) {
            Entity entity1 = entity.getVehicle();
            if (entity1 instanceof LivingEntity livingEntity) {
                if ((livingEntity instanceof ADragonBaseFlyingRideable dragonFly && dragonFly.isFlying()) || !livingEntity.isOnGround() ||
                        (livingEntity instanceof ADragonBase dragonBase && !dragonBase.isDragonOnGround())) {
                    pMatrixStack.translate(0.0D, 0.0D, 0.0D);
                }
            }

            if (entity instanceof Player player && player.isFallFlying()) {
                pMatrixStack.translate(0.0D, -0.25D, 0.0D);
            } else {
                pMatrixStack.translate(0.0D, 0.05D, 0.0D);
            }
        }

        pMatrixStack.translate(0.15D, entity == null ? 0.3D : 0.55D, entity == null ? -0.6D : -0.58D);
        pMatrixStack.translate(0, (baby ? -0.2D : 0.0D), (baby ? 0.2D : 0.1D));

        pMatrixStack.mulPose(Vector3f.XP.rotationDegrees(65.0F));
        pMatrixStack.mulPose(Vector3f.ZP.rotationDegrees(135.0F));

        ItemStack itemstack = terror.getItemBySlot(EquipmentSlot.MAINHAND);
        Minecraft.getInstance().getItemInHandRenderer().renderItem(terror, itemstack, ItemTransforms.TransformType.GROUND, false, pMatrixStack, pBuffer, pPackedLight);
        pMatrixStack.popPose();
    }
}