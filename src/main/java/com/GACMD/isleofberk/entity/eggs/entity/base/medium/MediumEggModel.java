package com.GACMD.isleofberk.entity.eggs.entity.base.medium;

import com.GACMD.isleofberk.IsleofBerk;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MediumEggModel extends AnimatedGeoModel<ADragonMediumEggBase> {

    @Override
    public ResourceLocation getModelLocation(ADragonMediumEggBase entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "geo/egg/medium_egg_model.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(ADragonMediumEggBase entity) {
        return entity.getTextureLocation(entity);

    }

    @Override
    public ResourceLocation getAnimationFileLocation(ADragonMediumEggBase entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "animations/egg/nightfury.animation.json");
    }

    @Override
    public void setLivingAnimations(ADragonMediumEggBase entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
    }
}
