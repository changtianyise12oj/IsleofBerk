package com.GACMD.isleofberk.common.entity.entities.dragons.montrous_nightmare;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.common.entity.entities.base.render.model.BaseDragonModel;
import com.GACMD.isleofberk.common.entity.entities.dragons.nightfury.NightFury;
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
        return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/nightmare/hookfang.png");

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
