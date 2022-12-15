package com.GACMD.isleofberk.common.entity.entities.dragons.nightfury;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.common.entity.entities.base.render.model.BaseDragonModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class NightFuryModel extends BaseDragonModel<NightFury> {

    @Override
    protected float getAdultSize() { return 1.1f; }

    @Override
    public ResourceLocation getModelLocation(NightFury entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "geo/dragons/nightfury.geo.json");

    }

    @Override
    public ResourceLocation getTextureLocation(NightFury entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/night_fury/toothless.png");

    }

    @Override
    public ResourceLocation getAnimationFileLocation(NightFury entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "animations/dragons/nightfury.animation.json");
    }

/*    @Override
    public void setLivingAnimations(NightFury entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
    }*/
}
