package com.GACMD.isleofberk.entity.dragons.nightlight;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.entity.base.render.model.BaseDragonModelFlying;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

@OnlyIn(Dist.CLIENT)
public class NightLightModel extends BaseDragonModelFlying<NightLight> {

    @Override
    public ResourceLocation getModelLocation(NightLight entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "geo/dragons/night_light.geo.json");

    }

    @Override
    public ResourceLocation getTextureLocation(NightLight entity) {
        switch (entity.getDragonVariant()) {
            default:
            case 0:
                return new ResourceLocation(IsleofBerk.MOD_ID,"textures/dragons/night_light/dart.png");
            case 1:
                return new ResourceLocation(IsleofBerk.MOD_ID,"textures/dragons/night_light/pouncer.png");
            case 2:
                return new ResourceLocation(IsleofBerk.MOD_ID,"textures/dragons/night_light/ruffrunner.png");
            case 3:
                return new ResourceLocation(IsleofBerk.MOD_ID,"textures/dragons/night_light/night_light_4.png");
            case 4:
                return new ResourceLocation(IsleofBerk.MOD_ID,"textures/dragons/night_light/night_light_5.png");
            case 5:
                return new ResourceLocation(IsleofBerk.MOD_ID,"textures/dragons/night_light/night_light_6.png");
            case 6:
                return new ResourceLocation(IsleofBerk.MOD_ID,"textures/dragons/night_light/night_light_7.png");
            case 7:
                return new ResourceLocation(IsleofBerk.MOD_ID,"textures/dragons/night_light/night_light_8.png");
        }
    }

    @Override
    public ResourceLocation getAnimationFileLocation(NightLight entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "animations/dragons/night_fury.animation.json");
    }

    @Override
    public int getMaxRise() {
        return 47;
    }

    @Override
    public int getMinRise() {
        return -47;
    }

    @Override
    public void setLivingAnimations(NightLight dragon, Integer uniqueID, AnimationEvent customPredicate) {
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