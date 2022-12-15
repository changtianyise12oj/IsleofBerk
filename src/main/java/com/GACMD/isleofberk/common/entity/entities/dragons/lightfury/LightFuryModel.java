package com.GACMD.isleofberk.common.entity.entities.dragons.lightfury;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.common.entity.entities.base.render.model.BaseDragonModel;
import com.GACMD.isleofberk.common.entity.entities.dragons.nightfury.NightFury;
import net.minecraft.resources.ResourceLocation;

public class LightFuryModel extends BaseDragonModel<LightFury> {

    @Override
    public ResourceLocation getModelLocation(LightFury entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "geo/dragons/light_fury.geo.json");

    }

    @Override
    public ResourceLocation getTextureLocation(LightFury entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/light_fury/fight_fury_1.png");

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
