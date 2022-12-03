package com.GACMD.isleofberk.common.entity.entities.dragons.stinger;

import com.GACMD.isleofberk.IsleofBerk;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class StingerModel extends AnimatedGeoModel<Stinger> {

    @Override
    public ResourceLocation getAnimationFileLocation(Stinger stinger) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "animations/dragons/stinger.animation.json");
    }

    @Override
    public ResourceLocation getModelLocation(Stinger stinger) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "geo/dragons/stinger.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Stinger stinger) {
        if (stinger.isTitanWing()) {
            return new ResourceLocation("isleofberk:textures/dragons/stinger/titanstinger.png");
        } else {
            switch (stinger.getDragonVariant()) {
                default:
                case 0:
                    return new ResourceLocation("isleofberk:textures/dragons/stinger/mudsmasher.png");
                case 1:
                    return new ResourceLocation("isleofberk:textures/dragons/stinger/wildroar.png");
            }
        }
    }

    @SuppressWarnings({"rawtypes"})
    @Override
    public void setLivingAnimations(Stinger stinger, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(stinger, uniqueID, customPredicate);
        IBone saddle = this.getAnimationProcessor().getBone("Saddle");
        IBone collar = this.getAnimationProcessor().getBone("Collar");
        IBone Spike2 = this.getAnimationProcessor().getBone("Spike2");

        IBone Tail1 = this.getAnimationProcessor().getBone("Tail1");
        IBone Tail2 = this.getAnimationProcessor().getBone("Tail2");
        IBone Tail3 = this.getAnimationProcessor().getBone("Tail3");
        IBone Tail4 = this.getAnimationProcessor().getBone("Tail4");

        if(stinger.hasSaddle()) {
            saddle.setHidden(false);
            Spike2.setHidden(true);
        }

        if(!stinger.isTame()) {
            collar.setHidden(true);
        }

        if (!stinger.isRenderedOnGUI()) {
            IBone Body = this.getAnimationProcessor().getBone("Root");
            IBone HeadTrack1 = this.getAnimationProcessor().getBone("HeadTrack1");
            IBone HeadTrack2 = this.getAnimationProcessor().getBone("HeadTrack2");
            IBone HeadTrack3 = this.getAnimationProcessor().getBone("HeadTrack3");

            //headtracking
            EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
            HeadTrack1.setRotationX(extraData.headPitch * ((float) Math.PI / 180F) / 3);
            HeadTrack1.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F) / 3);
            HeadTrack2.setRotationX(extraData.headPitch * ((float) Math.PI / 180F) / 3);
            HeadTrack2.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F) / 3);
            HeadTrack3.setRotationX(extraData.headPitch * ((float) Math.PI / 180F) / 3);
            HeadTrack3.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F) / 3);

            // body scale
            if (stinger.isBaby()) {
                Body.setScaleX(0.4F);
                Body.setScaleY(0.4F);
                Body.setScaleZ(0.4F);

            } else if (stinger.isTitanWing()) {
                Body.setScaleX(1.3F);
                Body.setScaleY(1.3F);
                Body.setScaleZ(1.3F);

            } else {
                Body.setScaleX(1.0F);
                Body.setScaleY(1.0F);
                Body.setScaleZ(1.0F);
            }
        }
    }
}
