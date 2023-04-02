package com.GACMD.isleofberk.entity.dragons.skrill;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.entity.base.render.model.BaseDragonModel;
import com.GACMD.isleofberk.entity.base.render.model.BaseDragonModelFlying;
import com.GACMD.isleofberk.entity.dragons.nightfury.NightFury;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class SkrillModel extends BaseDragonModelFlying<Skrill> {

    @Override
    protected float getAdultSize() { return 1.1f; }

    public SkrillModel(EntityRendererProvider.Context renderManager) {

    }

    @Override
    public ResourceLocation getModelLocation(Skrill entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "geo/dragons/skrill.geo.json");

    }

    @Override
    public ResourceLocation getTextureLocation(Skrill entity) {
        switch (entity.getDragonVariant()) {
            default:
            case 0:
                return new ResourceLocation(IsleofBerk.MOD_ID,"textures/dragons/skrill/skrill.png");
            case 1:
                return new ResourceLocation(IsleofBerk.MOD_ID,"textures/dragons/skrill/skrill_black.png");
            case 2:
                return new ResourceLocation(IsleofBerk.MOD_ID,"textures/dragons/skrill/skrill_blue.png");
            case 3:
                return new ResourceLocation(IsleofBerk.MOD_ID,"textures/dragons/skrill/skrill_brown.png");
            case 4:
                return new ResourceLocation(IsleofBerk.MOD_ID,"textures/dragons/skrill/skrill_crimson.png");
            case 5:
                return new ResourceLocation(IsleofBerk.MOD_ID,"textures/dragons/skrill/skrill_cyan.png");
            case 6:
                return new ResourceLocation(IsleofBerk.MOD_ID,"textures/dragons/skrill/skrill_gold.png");
            case 7:
                return new ResourceLocation(IsleofBerk.MOD_ID,"textures/dragons/skrill/skrill_green.png");
            case 8:
                return new ResourceLocation(IsleofBerk.MOD_ID,"textures/dragons/skrill/skrill_red.png");
            case 9:
                return new ResourceLocation(IsleofBerk.MOD_ID,"textures/dragons/skrill/skrill_yellow.png");
            case 10:
                return new ResourceLocation(IsleofBerk.MOD_ID,"textures/dragons/skrill/skrill_albino.png");
        }

    }

    @Override
    public ResourceLocation getAnimationFileLocation(Skrill entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "animations/dragons/skrill.animation.json");
    }

    @Override
    public void setLivingAnimations(Skrill dragon, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(dragon, uniqueID, customPredicate);

        // list of bones
        IBone neck = this.getAnimationProcessor().getBone("Neck");
        IBone head = this.getAnimationProcessor().getBone("head");
        IBone spike = this.getAnimationProcessor().getBone("spikeRemove");

        // hide spikes when saddled
        spike.setHidden(dragon.isSaddled());

        // head tracking
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);

        if (!dragon.shouldStopMovingIndependently() && !Minecraft.getInstance().isPaused() && !dragon.isRenderedOnGUI()) {
            neck.setRotationY(neck.getRotationY() + extraData.netHeadYaw * ((float) Math.PI / 180F) / 2);
            head.setRotationY(head.getRotationY() + extraData.netHeadYaw * ((float) Math.PI / 180F) / 2);
            neck.setRotationX(neck.getRotationX() + extraData.headPitch * ((float) Math.PI / 180F) / 2);
            head.setRotationX(head.getRotationX() + extraData.headPitch * ((float) Math.PI / 180F) / 2);
        }
    }


    @Override
    public String getMainBodyBone() {
        return "rotation";
    }
}
