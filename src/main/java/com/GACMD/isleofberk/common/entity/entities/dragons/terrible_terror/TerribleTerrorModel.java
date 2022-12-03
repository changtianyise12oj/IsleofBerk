package com.GACMD.isleofberk.common.entity.entities.dragons.terrible_terror;

import com.GACMD.isleofberk.IsleofBerk;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class TerribleTerrorModel extends AnimatedGeoModel<TerribleTerror> {

    @Override
    public ResourceLocation getAnimationFileLocation(TerribleTerror terrible_terror) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "animations/dragons/terrible_terror.animation.json");
    }

    @Override
    public ResourceLocation getModelLocation(TerribleTerror terrible_terror) {
        if (terrible_terror.isTitanWing()) {
            return new ResourceLocation(IsleofBerk.MOD_ID, "geo/dragons/terrible_terror_titan_wing.geo.json");
        } else {
            return new ResourceLocation(IsleofBerk.MOD_ID, "geo/dragons/terrible_terror.geo.json");
        }
    }

    @Override
    public ResourceLocation getTextureLocation(TerribleTerror terrible_terror) {
        if (terrible_terror.isTitanWing()) {
            return new ResourceLocation("isleofberk:textures/dragons/terrible_terror/titan_wing.png");
        } else {
            switch (terrible_terror.getDragonVariant()) {
                default:
                case 0:
                    return new ResourceLocation("isleofberk:textures/dragons/terrible_terror/terrible_terror.png");
                case 1:
                    return new ResourceLocation("isleofberk:textures/dragons/terrible_terror/blue.png");
                case 2:
                    return new ResourceLocation("isleofberk:textures/dragons/terrible_terror/cyan.png");
                case 3:
                    return new ResourceLocation("isleofberk:textures/dragons/terrible_terror/green.png");
                case 4:
                    return new ResourceLocation("isleofberk:textures/dragons/terrible_terror/mint.png");
                case 5:
                    return new ResourceLocation("isleofberk:textures/dragons/terrible_terror/orange.png");
                case 6:
                    return new ResourceLocation("isleofberk:textures/dragons/terrible_terror/purple.png");
                case 7:
                    return new ResourceLocation("isleofberk:textures/dragons/terrible_terror/yellow.png");
            }
        }
    }

    @SuppressWarnings({"rawtypes"})
    @Override
    public void setLivingAnimations(TerribleTerror terrible_terror, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(terrible_terror, uniqueID, customPredicate);
        ItemStack itemstack = terrible_terror.getItemBySlot(EquipmentSlot.MAINHAND);

        if (itemstack == null) {
            IBone Body = this.getAnimationProcessor().getBone("Root");
            IBone HeadTrack1 = this.getAnimationProcessor().getBone("HeadTrack1");
            IBone HeadTrack2 = this.getAnimationProcessor().getBone("HeadTrack1");

            // headtracking
            EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
            HeadTrack1.setRotationX(extraData.headPitch * ((float) Math.PI / 180F) / 2);
            HeadTrack1.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F) / 2);
            HeadTrack2.setRotationX(extraData.headPitch * ((float) Math.PI / 180F) / 2);
            HeadTrack2.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F) / 2);


            // age scale override
            if (terrible_terror.isBaby()) {
                Body.setScaleX(0.4F);
                Body.setScaleY(0.4F);
                Body.setScaleZ(0.4F);

            } else if (terrible_terror.isTitanWing()) {
                Body.setScaleX(1.3F);
                Body.setScaleY(1.3F);
                Body.setScaleZ(1.3F);

            } else {
                Body.setScaleX(0.8F);
                Body.setScaleY(0.8F);
                Body.setScaleZ(0.8F);
            }
        }
    }
}
