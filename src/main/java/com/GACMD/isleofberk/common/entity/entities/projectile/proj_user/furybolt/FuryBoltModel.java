package com.GACMD.isleofberk.common.entity.entities.projectile.proj_user.furybolt;

import com.GACMD.isleofberk.IsleofBerk;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FuryBoltModel extends AnimatedGeoModel<FuryBolt> {


    @Override
    public ResourceLocation getModelLocation(FuryBolt entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "geo/projectile/fury.bolt.geo.json");

    }

    @Override
    public ResourceLocation getTextureLocation(FuryBolt entity) {
        if(entity.isLightFuryTexture()) {
            return new ResourceLocation(IsleofBerk.MOD_ID, "textures/projectile/light_fury_blast.png");
        } else {
            return new ResourceLocation(IsleofBerk.MOD_ID, "textures/projectile/night_fury_blast.png");
        }

    }

    @Override
    public ResourceLocation getAnimationFileLocation(FuryBolt entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "animations/projectile/fury_bolt.animation.json");
    }

    @Override
    public void setLivingAnimations(FuryBolt entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
    }
}
