package com.GACMD.isleofberk.common.entity.entities.eggs.entity.base.small;

import com.GACMD.isleofberk.IsleofBerk;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SmallEggModel extends AnimatedGeoModel<ADragonSmallEggBase> {

    @Override
    public ResourceLocation getModelLocation(ADragonSmallEggBase entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "geo/egg/small_egg_model.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(ADragonSmallEggBase entity) {
        return entity.getTextureLocation(entity);

    }

    @Override
    public ResourceLocation getAnimationFileLocation(ADragonSmallEggBase entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "animations/dragons/nightfury.animation.json");
    }

    @Override
    public void setLivingAnimations(ADragonSmallEggBase entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
    }
}
