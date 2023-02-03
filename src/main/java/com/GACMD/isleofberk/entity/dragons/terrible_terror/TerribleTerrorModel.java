package com.GACMD.isleofberk.entity.dragons.terrible_terror;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.entity.base.render.model.BaseDragonModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

@OnlyIn(Dist.CLIENT)
public class TerribleTerrorModel extends BaseDragonModel<TerribleTerror> {

    @Override
    public ResourceLocation getAnimationFileLocation(TerribleTerror terrible_terror) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "animations/dragons/terrible_terror.animation.json");
    }
    @Override
    protected float getAdultSize() { return 0.7f; }
    @Override
    protected float getTitanSize() { return 1; }
    @Override
    protected float getBabySize() { return 0.3f; }

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
                    return new ResourceLocation("isleofberk:textures/dragons/terrible_terror/blar.png");
                case 2:
                    return new ResourceLocation("isleofberk:textures/dragons/terrible_terror/sneaky.png");
                case 3:
                    return new ResourceLocation("isleofberk:textures/dragons/terrible_terror/terror.png");
                case 4:
                    return new ResourceLocation("isleofberk:textures/dragons/terrible_terror/sharpshot.png");
                case 5:
                    return new ResourceLocation("isleofberk:textures/dragons/terrible_terror/iggy.png");
                case 6:
                    return new ResourceLocation("isleofberk:textures/dragons/terrible_terror/pain.png");
                case 7:
                    return new ResourceLocation("isleofberk:textures/dragons/terrible_terror/head.png");
            }
        }
    }

    @Override
    public void setLivingAnimations(TerribleTerror terrible_terror, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(terrible_terror, uniqueID, customPredicate);
        ItemStack itemstack = terrible_terror.getItemBySlot(EquipmentSlot.MAINHAND);

        if (itemstack.isEmpty()) {
            IBone HeadTrack1 = this.getAnimationProcessor().getBone("HeadTrack1");
            IBone HeadTrack2 = this.getAnimationProcessor().getBone("HeadTrack1");

            // headtracking
            EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
            HeadTrack1.setRotationX(extraData.headPitch * ((float) Math.PI / 180F) / 2);
            HeadTrack1.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F) / 2);
            HeadTrack2.setRotationX(extraData.headPitch * ((float) Math.PI / 180F) / 2);
            HeadTrack2.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F) / 2);
        }
    }

    @Override
    protected String getMainBodyBone() {
        return "root";
    }
}
