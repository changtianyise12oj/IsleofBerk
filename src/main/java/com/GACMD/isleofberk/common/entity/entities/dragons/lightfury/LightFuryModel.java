package com.GACMD.isleofberk.common.entity.entities.dragons.lightfury;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.common.entity.entities.base.render.model.BaseDragonModel;
import com.GACMD.isleofberk.common.entity.entities.dragons.nightfury.NightFury;
import net.minecraft.resources.ResourceLocation;

public class LightFuryModel extends BaseDragonModel<LightFury> {

    @Override
    protected float getAdultSize() { return 0.5f; }

    @Override
    public ResourceLocation getModelLocation(LightFury entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "geo/dragons/nightfury.geo.json");

    }

    @Override
    public ResourceLocation getTextureLocation(LightFury entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/night_fury/night_fury.png");

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
