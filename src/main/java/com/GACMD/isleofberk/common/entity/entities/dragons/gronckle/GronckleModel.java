package com.GACMD.isleofberk.common.entity.entities.dragons.gronckle;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.common.entity.entities.base.render.model.BaseDragonModelFlying;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

public class GronckleModel extends BaseDragonModelFlying<Gronckle> {
    public GronckleModel(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceLocation getModelLocation(Gronckle entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "geo/dragons/gronckle.geo.json");

    }

    @Override
    public ResourceLocation getTextureLocation(Gronckle entity) {
        switch (entity.getDragonVariant()) {
            default:
            case 0:
                return new ResourceLocation("isleofberk:textures/dragons/gronckle/gronckle.png");
            case 1:
                return new ResourceLocation("isleofberk:textures/dragons/gronckle/green.png");
            case 2:
                return new ResourceLocation("isleofberk:textures/dragons/gronckle/blue.png");
            case 3:
                return new ResourceLocation("isleofberk:textures/dragons/gronckle/meatlug.png");
            case 4:
                return new ResourceLocation("isleofberk:textures/dragons/gronckle/orange.png");
            case 5:
                return new ResourceLocation("isleofberk:textures/dragons/gronckle/purple.png");
            case 6:
                return new ResourceLocation("isleofberk:textures/dragons/gronckle/red.png");
            case 7:
                return new ResourceLocation("isleofberk:textures/dragons/gronckle/rose.png");
            case 8:
                return new ResourceLocation("isleofberk:textures/dragons/gronckle/valentine.png");
            case 9:
                return new ResourceLocation("isleofberk:textures/dragons/gronckle/lime.png");

        }

    }

    @Override
    public ResourceLocation getAnimationFileLocation(Gronckle entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "animations/dragons/gronckle.animation.json");
    }

    @Override
    public void setLivingAnimations(Gronckle entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
    }
}
