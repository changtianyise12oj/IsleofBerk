package com.GACMD.isleofberk.common.entity.entities.base.render.model;

import com.GACMD.isleofberk.common.entity.entities.base.ADragonBase;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BaseDragonModel<T extends ADragonBase & IAnimatable> extends AnimatedGeoModel<T> {

    @Override
    public ResourceLocation getModelLocation(T object) {
        return null;
    }

    @Override
    public ResourceLocation getTextureLocation(T object) {
        return null;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(T animatable) {
        return null;
    }
}
