package com.GACMD.isleofberk.entity.dragons.stinger;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.entity.base.render.model.BaseDragonModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;

@OnlyIn(Dist.CLIENT)
public class StingerModel extends BaseDragonModel<Stinger> {

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
                    return new ResourceLocation("isleofberk:textures/dragons/stinger/wildroar.png");
                case 1:
                    return new ResourceLocation("isleofberk:textures/dragons/stinger/mudsmasher.png");
            }
        }
    }

    @Override
    public void setLivingAnimations(Stinger stinger, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(stinger, uniqueID, customPredicate);

        IBone saddle = this.getAnimationProcessor().getBone("Saddle");
        IBone collar = this.getAnimationProcessor().getBone("Collar");
        IBone spike2 = this.getAnimationProcessor().getBone("Spike2");

/*        IBone headTrack1 = this.getAnimationProcessor().getBone("HeadTrack1");
        IBone headTrack2 = this.getAnimationProcessor().getBone("HeadTrack2");
        IBone headTrack3 = this.getAnimationProcessor().getBone("HeadTrack3"); */

        // hides spikes when dragon is saddled
        if(stinger.hasSaddle()) {
            saddle.setHidden(false);
            spike2.setHidden(true);
        }

        if(!stinger.isTame()) {
            collar.setHidden(true);
        }

        // to fix when Ill find new model
/*        if (!stinger.isRenderedOnGUI()) {

            //headtracking
            EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
            headTrack1.setRotationX(extraData.headPitch * ((float) Math.PI / 180F) / 3);
            headTrack1.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F) / 3);
            headTrack2.setRotationX(extraData.headPitch * ((float) Math.PI / 180F) / 3);
            headTrack2.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F) / 3);
            headTrack3.setRotationX(extraData.headPitch * ((float) Math.PI / 180F) / 3);
            headTrack3.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F) / 3);
        } */
    }

    @Override
    protected String getMainBodyBone() {
        return "Root";
    }
}
