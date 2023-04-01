package com.GACMD.isleofberk.entity.dragons.skrill;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.entity.base.render.model.BaseDragonModel;
import com.GACMD.isleofberk.entity.base.render.model.BaseDragonModelFlying;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

public class SkrillModel extends BaseDragonModelFlying<Skrill> {

    @Override
    protected float getAdultSize() { return 1.1f; }

    public SkrillModel(EntityRendererProvider.Context renderManager) {

    }

    @Override
    public ResourceLocation getModelLocation(Skrill entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "geo/dragons/skrill.geo.json");

    }

    @Override
    public ResourceLocation getTextureLocation(Skrill entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/skrill/skrill.png");

    }

    @Override
    public ResourceLocation getAnimationFileLocation(Skrill entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "animations/dragons/skrill.animation.json");
    }

    @Override
    public void setLivingAnimations(Skrill entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
    }
}
