package com.GACMD.isleofberk.entity.dragons.zippleback;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.entity.base.render.model.BaseDragonModel;
import com.GACMD.isleofberk.entity.base.render.model.BaseDragonModelFlying;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class ZippleBackModel extends BaseDragonModelFlying<ZippleBack> {

    @Override
    public ResourceLocation getModelLocation(ZippleBack entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "geo/dragons/zippleback.geo.json");

    }

    @Override
    public ResourceLocation getTextureLocation(ZippleBack entity) {
        switch (entity.getDragonVariant()) {
            default:
            case 0:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/zippleback/pistill.png");
            case 1:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/zippleback/sandr.png");
            case 2:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/zippleback/fart_n_sniff.png");
            case 3:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/zippleback/hodd.png");
            case 4:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/zippleback/hjarta.png");
            case 5:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/zippleback/exiled.png");
            case 6:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/zippleback/whip_n_lash.png");
            case 7:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/zippleback/hamfeist.png");
        }
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ZippleBack entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "animations/dragons/zippleback.animation.json");
    }

    @Override
    public void setLivingAnimations(ZippleBack dragon, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(dragon, uniqueID, customPredicate);

        // list of bones
        IBone right1Neck = this.getAnimationProcessor().getBone("right1Neck");
        IBone right2Neck = this.getAnimationProcessor().getBone("right2Neck");
        IBone right3Neck = this.getAnimationProcessor().getBone("right3Neck");
        IBone right4Neck = this.getAnimationProcessor().getBone("right4Neck");
        IBone right5Neck = this.getAnimationProcessor().getBone("right5Neck");
        IBone right6Neck = this.getAnimationProcessor().getBone("right6Neck");
        IBone rightHead = this.getAnimationProcessor().getBone("rightHead");
        IBone left1Neck = this.getAnimationProcessor().getBone("left1Neck");
        IBone left2Neck = this.getAnimationProcessor().getBone("left2Neck");
        IBone left3Neck = this.getAnimationProcessor().getBone("left3Neck");
        IBone left4Neck = this.getAnimationProcessor().getBone("left4Neck");
        IBone left5Neck = this.getAnimationProcessor().getBone("left5Neck");
        IBone left6Neck = this.getAnimationProcessor().getBone("left6Neck");
        IBone head = this.getAnimationProcessor().getBone("head");

        // head tracking
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);

        if (!dragon.shouldStopMovingIndependently() && !Minecraft.getInstance().isPaused()) {
            right1Neck.setRotationZ(right1Neck.getRotationZ() + extraData.netHeadYaw * ((float) Math.PI / 180F) / 7);
            right2Neck.setRotationZ(right2Neck.getRotationZ() + extraData.netHeadYaw * ((float) Math.PI / 180F) / 7);
            right3Neck.setRotationZ(right3Neck.getRotationZ() + extraData.netHeadYaw * ((float) Math.PI / 180F) / 7);
            right4Neck.setRotationZ(right4Neck.getRotationZ() + extraData.netHeadYaw * ((float) Math.PI / 180F) / 7);
            right5Neck.setRotationZ(right5Neck.getRotationZ() + extraData.netHeadYaw * ((float) Math.PI / 180F) / 7);
            right6Neck.setRotationZ(right6Neck.getRotationZ() + extraData.netHeadYaw * ((float) Math.PI / 180F) / 7);
            rightHead.setRotationZ(rightHead.getRotationZ() + extraData.netHeadYaw * ((float) Math.PI / 180F) / 3);
            left1Neck.setRotationZ(left1Neck.getRotationZ() + extraData.netHeadYaw * ((float) Math.PI / 180F) / 7);
            left2Neck.setRotationZ(left2Neck.getRotationZ() + extraData.netHeadYaw * ((float) Math.PI / 180F) / 7);
            left3Neck.setRotationZ(left3Neck.getRotationZ() + extraData.netHeadYaw * ((float) Math.PI / 180F) / 7);
            left4Neck.setRotationZ(left4Neck.getRotationZ() + extraData.netHeadYaw * ((float) Math.PI / 180F) / 7);
            left5Neck.setRotationZ(left5Neck.getRotationZ() + extraData.netHeadYaw * ((float) Math.PI / 180F) / 7);
            left6Neck.setRotationZ(left6Neck.getRotationZ() + extraData.netHeadYaw * ((float) Math.PI / 180F) / 7);
            head.setRotationZ(head.getRotationZ() + extraData.netHeadYaw * ((float) Math.PI / 180F) / 3);
            right4Neck.setRotationX(right4Neck.getRotationX() + extraData.headPitch * ((float) Math.PI / 180F) / 7);
            right5Neck.setRotationX(right5Neck.getRotationX() + extraData.headPitch * ((float) Math.PI / 180F) / 7);
            right6Neck.setRotationX(right6Neck.getRotationX() + extraData.headPitch * ((float) Math.PI / 180F) / 7);
            rightHead.setRotationX(rightHead.getRotationX() + extraData.headPitch * ((float) Math.PI / 180F) / 7);
            left4Neck.setRotationX(left4Neck.getRotationX() + extraData.headPitch * ((float) Math.PI / 180F) / 7);
            left5Neck.setRotationX(left5Neck.getRotationX() + extraData.headPitch * ((float) Math.PI / 180F) / 7);
            left6Neck.setRotationX(left6Neck.getRotationX() + extraData.headPitch * ((float) Math.PI / 180F) / 7);
            head.setRotationX(head.getRotationX() + extraData.headPitch * ((float) Math.PI / 180F) / 7);
        }
    }

    @Override
    public String getMainBodyBone() {
        return "rotation";
    }
}
