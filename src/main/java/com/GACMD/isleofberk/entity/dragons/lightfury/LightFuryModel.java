package com.GACMD.isleofberk.entity.dragons.lightfury;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.entity.base.render.model.BaseDragonModel;
import com.GACMD.isleofberk.entity.base.render.model.BaseDragonModelFlying;
import net.minecraft.resources.ResourceLocation;

public class LightFuryModel extends BaseDragonModelFlying<LightFury> {

    @Override
    public ResourceLocation getModelLocation(LightFury entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "geo/dragons/light_fury.geo.json");

    }

    @Override
    public ResourceLocation getTextureLocation(LightFury entity) {
        switch (entity.getDragonVariant()) {
            default:
            case 0:
                return new ResourceLocation("isleofberk:textures/dragons/light_fury/light_fury.png");
            case 1:
                return new ResourceLocation("isleofberk:textures/dragons/light_fury/sveinn.png");
            case 2:
                return new ResourceLocation("isleofberk:textures/dragons/light_fury/dróttinn.png");
            case 3:
                return new ResourceLocation("isleofberk:textures/dragons/light_fury/grógaldr.png");
            case 4:
                return new ResourceLocation("isleofberk:textures/dragons/light_fury/dróttinn_grógaldr.png");
            case 5:
                return new ResourceLocation("isleofberk:textures/dragons/light_fury/sveinn_grógaldr.png");
        }
    }

    @Override
    public ResourceLocation getAnimationFileLocation(LightFury entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "animations/dragons/nightfury.animation.json");
    }

/*    @Override
    public void setLivingAnimations(NightFury entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
    }*/
}
