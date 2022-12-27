package com.GACMD.isleofberk.entity.dragons.montrous_nightmare;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.entity.base.render.model.BaseDragonModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

public class MonstrousNightmareModel extends BaseDragonModel<MonstrousNightmare> {
    public MonstrousNightmareModel(EntityRendererProvider.Context renderManager) {

    }

    @Override
    public ResourceLocation getModelLocation(MonstrousNightmare entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "geo/dragons/nightmare.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(MonstrousNightmare entity) {
        switch (entity.getDragonVariant()) {
            default:
            case 0:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/nightmare/hookfang.png");
            case 1:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/nightmare/blue.png");
            case 2:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/nightmare/yellow.png");
            case 3:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/nightmare/mint.png");
            case 4:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/nightmare/pink.png");
            case 5:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/nightmare/cyan.png");
            case 6:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/nightmare/orange.png");
            case 7:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/nightmare/green.png");
            case 8:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/nightmare/magenta.png");
            case 9:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/nightmare/black.png");
        }
    }

    @Override
    public ResourceLocation getAnimationFileLocation(MonstrousNightmare entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "animations/dragons/nightfury.animation.json");
    }


    @Override
    public void setLivingAnimations(MonstrousNightmare entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
    }
}
