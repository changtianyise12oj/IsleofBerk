package com.GACMD.isleofberk.entity.dragons.zippleback;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.entity.base.render.model.BaseDragonModel;
import com.GACMD.isleofberk.entity.base.render.model.BaseDragonModelFlying;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

public class ZippleBackModel extends BaseDragonModelFlying<ZippleBack> {

    @Override
    public ResourceLocation getModelLocation(ZippleBack entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "geo/dragons/zippleback.geo.json");

    }

    @Override
    public ResourceLocation getTextureLocation(ZippleBack entity) {
        switch (entity.getDragonVariant()) {
            default:
            case 0:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/zippleback/pistill.png");
            case 1:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/zippleback/sandr.png");
            case 2:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/zippleback/fart_n_sniff.png");
            case 3:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/zippleback/hodd.png");
            case 4:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/zippleback/hjarta.png");
            case 5:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/zippleback/exiled.png");
            case 6:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/zippleback/whip_n_lash.png");
            case 7:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/zippleback/hamfeist.png");
        }
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
