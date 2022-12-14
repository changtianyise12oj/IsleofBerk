package com.GACMD.isleofberk.common.entity.entities.dragons.zippleback;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.common.entity.entities.dragons.montrous_nightmare.MonstrousNightmare;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ZippleBackModel extends AnimatedGeoModel<ZippleBack> {
//    @Override
//    public ResourceLocation getModelLocation(ZippleBack entity) {
//        return new ResourceLocation(IsleofBerk.MOD_ID, "geo/dragons/zippleback.geo.json");
//
//    }
//
//    @Override
//    public ResourceLocation getTextureLocation(ZippleBack entity) {
//        return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/zippleback/zippleback.png");
//
//    }
//
//    @Override
//    public ResourceLocation getAnimationFileLocation(ZippleBack entity) {
//        return new ResourceLocation(IsleofBerk.MOD_ID, "animations/dragons/zippleback.animation.json");
//    }

    @Override
    public ResourceLocation getModelLocation(ZippleBack entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "geo/dragons/nightfury.geo.json");

    }

    @Override
    public ResourceLocation getTextureLocation(ZippleBack entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/night_fury/night_fury.png");

    }

    @Override
    public ResourceLocation getAnimationFileLocation(ZippleBack entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "animations/dragons/nightfury.animation.json");
    }

    @Override
    public void setLivingAnimations(ZippleBack entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
    }
}
