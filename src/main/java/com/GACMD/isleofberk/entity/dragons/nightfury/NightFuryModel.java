package com.GACMD.isleofberk.entity.dragons.nightfury;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.config.CommonConfig;
import com.GACMD.isleofberk.entity.base.render.model.BaseDragonModel;
import com.GACMD.isleofberk.entity.base.render.model.BaseDragonModelFlying;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class NightFuryModel extends BaseDragonModelFlying<NightFury> {

    @Override
    protected float getAdultSize() { return CommonConfig.USE_LARGER_SCALING.get() ? 1.1f: 1; }

    @Override
    public ResourceLocation getModelLocation(NightFury entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "geo/dragons/nightfury.geo.json");

    }

    @Override
    public ResourceLocation getTextureLocation(NightFury entity) {
        switch (entity.getDragonVariant()) {
            default:
            case 0:
                return new ResourceLocation(IsleofBerk.MOD_ID,"textures/dragons/night_fury/night_fury.png");
            case 1:
                return new ResourceLocation(IsleofBerk.MOD_ID,"textures/dragons/night_fury/sentinel.png");
            case 2:
                return new ResourceLocation(IsleofBerk.MOD_ID,"textures/dragons/night_fury/karma.png");
            case 3:
                return new ResourceLocation(IsleofBerk.MOD_ID,"textures/dragons/night_fury/arsian.png");
            case 4:
                return new ResourceLocation(IsleofBerk.MOD_ID,"textures/dragons/night_fury/svartr.png");
            case 5:
                return new ResourceLocation(IsleofBerk.MOD_ID,"textures/dragons/night_fury/albino.png");
            case 101:
                return new ResourceLocation(IsleofBerk.MOD_ID,"textures/dragons/night_fury/toothless.png");
        }
    }

    @Override
    public ResourceLocation getAnimationFileLocation(NightFury entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "animations/dragons/nightfury.animation.json");
    }

    @Override
    public int getMaxRise() {
        return 47;
    }

    @Override
    public int getMinRise() {
        return -47;
    }

/*    @Override
    public void setLivingAnimations(NightFury entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
    }*/
}
