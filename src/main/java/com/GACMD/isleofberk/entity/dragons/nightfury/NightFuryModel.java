package com.GACMD.isleofberk.entity.dragons.nightfury;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.entity.base.render.model.BaseDragonModel;
import com.GACMD.isleofberk.entity.base.render.model.BaseDragonModelFlying;
import com.GACMD.isleofberk.entity.dragons.lightfury.LightFury;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

@OnlyIn(Dist.CLIENT)
public class NightFuryModel extends BaseDragonModelFlying<NightFury> {

    @Override
    public ResourceLocation getModelLocation(NightFury entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "geo/dragons/night_fury.geo.json");

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
    public void setLivingAnimations(NightFury dragon, Integer uniqueID, AnimationEvent customPredicate) {
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
