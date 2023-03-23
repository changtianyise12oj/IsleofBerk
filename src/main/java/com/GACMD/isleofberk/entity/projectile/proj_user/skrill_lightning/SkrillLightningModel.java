package com.GACMD.isleofberk.entity.projectile.proj_user.skrill_lightning;

import com.GACMD.isleofberk.IsleofBerk;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SkrillLightningModel extends AnimatedGeoModel<SkrillLightning> {


    @Override
    public ResourceLocation getModelLocation(SkrillLightning entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "geo/projectile/skrill_lightning.geo.json");

    }

    @Override
    public ResourceLocation getTextureLocation(SkrillLightning entity) {
            return new ResourceLocation(IsleofBerk.MOD_ID, "textures/projectile/skrill_lightning.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(SkrillLightning entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "animations/projectile/skrill_lightning.animation.json");
    }

    @Override
    public void setLivingAnimations(SkrillLightning entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
    }
}
