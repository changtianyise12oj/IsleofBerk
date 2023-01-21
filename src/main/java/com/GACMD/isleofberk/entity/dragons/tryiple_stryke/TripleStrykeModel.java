package com.GACMD.isleofberk.entity.dragons.tryiple_stryke;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.config.CommonConfig;
import com.GACMD.isleofberk.entity.base.render.model.BaseDragonModel;
import com.GACMD.isleofberk.entity.base.render.model.BaseDragonModelFlying;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

@OnlyIn(Dist.CLIENT)
public class TripleStrykeModel extends BaseDragonModelFlying<TripleStryke> {

    public TripleStrykeModel(EntityRendererProvider.Context renderManager) {
        super();
    }

    @Override
    protected float getAdultSize() { return CommonConfig.USE_LARGER_SCALING.get() ? 1.40f: 1.15f; }

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
                    return new ResourceLocation("isleofberk:textures/dragons/triple_stryke/starstreak.png");
                case 4:
                    return new ResourceLocation("isleofberk:textures/dragons/triple_stryke/triple_stryke.png");
                case 5:
                    return new ResourceLocation("isleofberk:textures/dragons/triple_stryke/spyro.png");
                case 6:
                    return new ResourceLocation("isleofberk:textures/dragons/triple_stryke/blue.png");
                case 7:
                    return new ResourceLocation("isleofberk:textures/dragons/triple_stryke/rosethorn.png");

            }
        }
    }

    @Override
    public void setLivingAnimations(TripleStryke dragon, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(dragon, uniqueID, customPredicate);

        IBone neck1 = this.getAnimationProcessor().getBone("neck1");
        IBone neck2 = this.getAnimationProcessor().getBone("neck3");
        IBone head = this.getAnimationProcessor().getBone("head");
        IBone spike = this.getAnimationProcessor().getBone("spikeremove");

        // whatever tf is that, used for head tracking
        // will probably stop working at some point
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);

        // removes back spikes when dragon is saddled
        spike.setHidden(dragon.hasSaddle());

        // floats to fix paused game mess
        float rotNeck1Y;
        float rotNeck2Y;
        float rotHeadY;
        float rotNeck1X;
        float rotHeadX;

        // checks if game is paused - head spinning/heaven ascension on pause fix
        if (Minecraft.getInstance().isPaused()) {
            rotHeadY = 0;
            rotNeck1Y = 0;
            rotNeck2Y = 0;
            rotHeadX = 0;
            rotNeck1X = 0;
        } else {
            rotHeadY = head.getRotationY();
            rotNeck1Y = neck1.getRotationY();
            rotNeck2Y = neck2.getRotationY();
            rotHeadX = head.getRotationX();
            rotNeck1X = neck1.getRotationX();
        }

        // head tracking when not mounted
        if (!dragon.shouldStopMovingIndependently()) {
            neck1.setRotationY(rotNeck1Y + extraData.netHeadYaw * ((float) Math.PI / 180F) / 3);
            neck2.setRotationY(rotNeck2Y + extraData.netHeadYaw * ((float) Math.PI / 180F) / 3);
            head.setRotationY(rotHeadY + extraData.netHeadYaw * ((float) Math.PI / 180F) / 3);
            neck1.setRotationX(rotNeck1X + extraData.headPitch * ((float) Math.PI / 180F) / 4);
            head.setRotationX(rotHeadX + extraData.headPitch * ((float) Math.PI / 180F) / 4);
        }
    }

    @Override
    public String getMainBodyBone() {
        return "main";
    }
}
