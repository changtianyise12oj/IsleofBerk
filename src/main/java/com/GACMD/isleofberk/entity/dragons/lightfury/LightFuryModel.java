package com.GACMD.isleofberk.entity.dragons.lightfury;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.config.CommonConfig;
import com.GACMD.isleofberk.entity.base.render.model.BaseDragonModel;
import com.GACMD.isleofberk.entity.base.render.model.BaseDragonModelFlying;
import com.GACMD.isleofberk.entity.dragons.montrous_nightmare.MonstrousNightmare;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class LightFuryModel extends BaseDragonModelFlying<LightFury> {

    @Override
    public ResourceLocation getModelLocation(LightFury entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "geo/dragons/light_fury.geo.json");

    }


    @Override
    protected float getAdultSize() { return CommonConfig.USE_LARGER_SCALING.get() ? 1.04f: 1; }

    @Override
    public ResourceLocation getTextureLocation(LightFury entity) {
        switch (entity.getDragonVariant()) {
            default:
            case 0:
                return new ResourceLocation("isleofberk:textures/dragons/light_fury/light_fury.png");
            case 1:
                return new ResourceLocation("isleofberk:textures/dragons/light_fury/sveinn.png");
            case 2:
                return new ResourceLocation("isleofberk:textures/dragons/light_fury/drottinn.png");
            case 3:
                return new ResourceLocation("isleofberk:textures/dragons/light_fury/grogaldr.png");
            case 4:
                return new ResourceLocation("isleofberk:textures/dragons/light_fury/drottinn_grogaldr.png");
            case 5:
                return new ResourceLocation("isleofberk:textures/dragons/light_fury/sveinn_grogaldr.png");
        }
    }

    @Override
    public ResourceLocation getAnimationFileLocation(LightFury entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "animations/dragons/light_fury.animation.json");
    }

    @Override
    public int getMaxRise() {
        return 42;
    }

    @Override
    public int getMinRise() {
        return -42;
    }

    @Override
    public void setLivingAnimations(LightFury dragon, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(dragon, uniqueID, customPredicate);

        // list of bones
        IBone neck = this.getAnimationProcessor().getBone("Neck");
        IBone head = this.getAnimationProcessor().getBone("head");

        // head tracking
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);

        if (!dragon.shouldStopMovingIndependently() && !Minecraft.getInstance().isPaused() && !dragon.isRenderedOnGUI()) {
            neck.setRotationY(neck.getRotationY() + extraData.netHeadYaw * ((float) Math.PI / 180F) / 2);
            head.setRotationY(head.getRotationY() + extraData.netHeadYaw * ((float) Math.PI / 180F) / 2);
            neck.setRotationX(neck.getRotationX() + extraData.headPitch * ((float) Math.PI / 180F) / 2);
            head.setRotationX(head.getRotationX() + extraData.headPitch * ((float) Math.PI / 180F) / 2);
        }
    }

    @Override
    public String getMainBodyBone() {
        return "rotation";
    }
}
