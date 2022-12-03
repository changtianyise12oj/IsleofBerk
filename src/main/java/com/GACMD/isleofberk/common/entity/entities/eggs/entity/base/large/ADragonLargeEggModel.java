package com.GACMD.isleofberk.common.entity.entities.eggs.entity.base.large;

import com.GACMD.isleofberk.IsleofBerk;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ADragonLargeEggModel extends AnimatedGeoModel<ADragonLargeEggBase> {

    @Override
    public ResourceLocation getModelLocation(ADragonLargeEggBase entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "geo/egg/large_egg_model.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(ADragonLargeEggBase entity) {
        return entity.getTextureLocation(entity);

    }

    @Override
    public ResourceLocation getAnimationFileLocation(ADragonLargeEggBase entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "animations/egg/nightfury.animation.json");
    }

    @Override
    public void setLivingAnimations(ADragonLargeEggBase entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
    }
}
