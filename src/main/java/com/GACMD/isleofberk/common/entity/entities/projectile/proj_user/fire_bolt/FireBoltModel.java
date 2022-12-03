package com.GACMD.isleofberk.common.entity.entities.projectile.proj_user.fire_bolt;

import com.GACMD.isleofberk.IsleofBerk;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FireBoltModel extends AnimatedGeoModel<FireBolt> {


    @Override
    public ResourceLocation getModelLocation(FireBolt entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "geo/projectile/projectile.medium.geo.json");

    }

    @Override
    public ResourceLocation getTextureLocation(FireBolt entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "textures/projectile/fireball.png");

    }

    @Override
    public ResourceLocation getAnimationFileLocation(FireBolt entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "animations/projectile/projectile.medium.animation.json");
    }

    @Override
    public void setLivingAnimations(FireBolt entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
    }
}
