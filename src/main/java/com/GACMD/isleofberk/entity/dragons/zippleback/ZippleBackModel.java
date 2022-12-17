package com.GACMD.isleofberk.entity.dragons.zippleback;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.entity.base.render.model.BaseDragonModel;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

public class ZippleBackModel extends BaseDragonModel<ZippleBack> {

    @Override
    public ResourceLocation getModelLocation(ZippleBack entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "geo/dragons/zippleback.geo.json");

    }

    @Override
    public ResourceLocation getTextureLocation(ZippleBack entity) {
        switch (entity.getDragonVariant()) {
            default:
            case 0:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/zippleback/green.png");
            case 1:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/zippleback/black.png");
            case 2:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/zippleback/blue.png");
            case 3:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/zippleback/gold.png");
            case 4:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/zippleback/grey.png");
            case 5:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/zippleback/purple.png");
            case 6:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/zippleback/cyan.png");
            case 7:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/zippleback/yellow.png");
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
