package com.GACMD.isleofberk.common.entity.entities.dragons.tryiple_stryke;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.common.entity.entities.base.render.model.BaseDragonModelFlying;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

public class TripleStrykeModel extends BaseDragonModelFlying<TripleStryke> {

    public TripleStrykeModel(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public void setLivingAnimations(TripleStryke entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
    }

    @Override
    public ResourceLocation getModelLocation(TripleStryke object) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "geo/dragons/triple_stryke.geo.json");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(TripleStryke stinger) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "animations/dragons/triple_stryke.animation.json");
    }

    @Override
    public ResourceLocation getTextureLocation(TripleStryke stinger) {
        if (stinger.isTitanWing()) {
            return new ResourceLocation("isleofberk:textures/dragons/stinger/titanstinger.png");
        } else {
            switch (stinger.getDragonVariant()) {
                default:
                case 0:
                    return new ResourceLocation("isleofberk:textures/dragons/triple_stryke/champion.png");
                case 1:
                    return new ResourceLocation("isleofberk:textures/dragons/triple_stryke/eclipser.png");
                case 2:
                    return new ResourceLocation("isleofberk:textures/dragons/triple_stryke/nikora_triple_stryke.png");
                case 3:
                    return new ResourceLocation("isleofberk:textures/dragons/triple_stryke/rosethorn.png");
                case 4:
                    return new ResourceLocation("isleofberk:textures/dragons/triple_stryke/starstreak.png");
                case 5:
                    return new ResourceLocation("isleofberk:textures/dragons/triple_stryke/triple_stryke.png");
                case 6:
                    return new ResourceLocation("isleofberk:textures/dragons/triple_stryke/blue.png");
                case 7:
                    return new ResourceLocation("isleofberk:textures/dragons/triple_stryke/spyro.png");

            }
        }
    }

    @Override
    public void setMolangQueries(IAnimatable animatable, double currentTick) {
        super.setMolangQueries(animatable, currentTick);
    }
}
