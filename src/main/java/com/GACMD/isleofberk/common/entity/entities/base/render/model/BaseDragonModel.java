package com.GACMD.isleofberk.common.entity.entities.base.render.model;

import com.GACMD.isleofberk.common.entity.entities.base.ADragonBase;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;

/** used only for getGoeBone method */
@OnlyIn(Dist.CLIENT)
public class BaseDragonModel<T extends ADragonBase & IAnimatable> extends AnimatedGeoModel<T> {

    // override if dragon is different size than modelled
    protected float getAdultSize() { return 1; }
    protected float getTitanSize() { return 1.4f; }
    protected float getBabySize() { return 0.4f; }
    protected float getBabyHeadSize() { return 1.5f; }

    protected GeoBone getGeoBone(String name)
    {
        IBone bone = this.getAnimationProcessor().getBone(name);
        if(bone instanceof GeoBone geoBone)
            return geoBone;
        return null;
    }

    @Override
    public ResourceLocation getModelLocation(T object) {return null; }

    @Override
    public ResourceLocation getTextureLocation(T object) { return null; }

    @Override
    public ResourceLocation getAnimationFileLocation(T animatable) {return null; }

    @Override
    public void setLivingAnimations(T dragon, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(dragon, uniqueID, customPredicate);

        GeoBone head = getGeoBone("head");
        GeoBone root = getGeoBone("root");

        float adultSize = this.getAdultSize();
        float babySize = this.getBabySize();
        float babyHeadSize = this.getBabyHeadSize();
        float titanSize = this.getTitanSize();

        // sets scale for baby and adult
        if (dragon.isBaby()) {
            root.setScale(babySize, babySize, babySize);
            head.setScale(babyHeadSize, babyHeadSize, babyHeadSize);

        } else if (dragon.isTitanWing()) {
            root.setScale(titanSize, titanSize, titanSize);

        }else {
            root.setScale(adultSize, adultSize, adultSize);
        }
    }
}
