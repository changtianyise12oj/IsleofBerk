package com.GACMD.isleofberk.common.entity.entities.dragons.deadlynadder;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.common.entity.entities.base.render.model.BaseDragonModelFlying;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class DeadlyNadderModel extends BaseDragonModelFlying<DeadlyNadder> {

    public DeadlyNadderModel(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceLocation getModelLocation(DeadlyNadder entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "geo/dragons/deadly_nadder.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(DeadlyNadder entity) {
        switch (entity.getDragonVariant()) {
            default:
            case 0:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/deadly_nadder/stormfly.png");
            case 1:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/deadly_nadder/scardian.png");
            case 2:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/deadly_nadder/red.png");
            case 3:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/deadly_nadder/purple.png");
            case 4:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/deadly_nadder/pink.png");
            case 5:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/deadly_nadder/mint.png");
            case 6:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/deadly_nadder/green.png");
            case 7:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/deadly_nadder/flystorm.png");
            case 8:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/deadly_nadder/dark_purple.png");
            case 9:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/deadly_nadder/brown.png");
            case 10:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/deadly_nadder/blue.png");
        }

    }

    @Override
    public ResourceLocation getAnimationFileLocation(DeadlyNadder entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "animations/dragons/deadly_nadder.animation.json");
    }

    @Override
    public void setLivingAnimations(DeadlyNadder dragon, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(dragon, uniqueID, customPredicate);
        IBone tailSpike5 = this.getAnimationProcessor().getBone("Tail5Spikes");
        IBone tailSpike4 = this.getAnimationProcessor().getBone("Tail4Spikes");
        IBone tailSpike3 = this.getAnimationProcessor().getBone("Tail3Spikes");
        IBone tailSpike2 = this.getAnimationProcessor().getBone("Tail2Spikes");
        IBone tailSpike1 = this.getAnimationProcessor().getBone("Tail1Spikes"); // base of the tail

        if (dragon.getRemainingSecondFuel() < dragon.getMaxSecondFuel() * 0.80) {
            tailSpike5.setHidden(true);
        } else {
            tailSpike5.setHidden(false);
        }

        if (dragon.getRemainingSecondFuel() < dragon.getMaxSecondFuel() * 0.60) {
            tailSpike4.setHidden(true);

        } else {
            tailSpike4.setHidden(false);
        }

        if (dragon.getRemainingSecondFuel() < dragon.getMaxSecondFuel() * 0.40) {
            tailSpike3.setHidden(true);
        } else {
            tailSpike3.setHidden(false);
        }

        if (dragon.getRemainingSecondFuel() < dragon.getMaxSecondFuel() * 0.20) {
            tailSpike2.setHidden(true);

        } else {
            tailSpike2.setHidden(false);
        }

        if (dragon.getRemainingSecondFuel() == 0) {
            tailSpike1.setHidden(true);
        } else {
            tailSpike1.setHidden(false);
        }

        if (!dragon.shouldStopMovingIndependently()) {
            IBone Neck1 = this.getAnimationProcessor().getBone("Neck1");
            IBone Neck2 = this.getAnimationProcessor().getBone("Neck2");
            IBone Neck3 = this.getAnimationProcessor().getBone("Neck3");
            IBone Head = this.getAnimationProcessor().getBone("Head");

            EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);


            Neck1.setRotationY(Neck1.getRotationY() + extraData.netHeadYaw * ((float) Math.PI / 180F) / 3);
            Neck2.setRotationY(Neck2.getRotationY() + extraData.netHeadYaw * ((float) Math.PI / 180F) / 3);
            Head.setRotationY(Head.getRotationY() + extraData.netHeadYaw * ((float) Math.PI / 180F) / 3);
        }


    }
}

//        if (dragon.getControllingPassenger() instanceof Player pilot) {
// HeadTracking
//
//            Neck1.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F) / 3);
//
//            EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
//            Neck1.setRotationX((float) (pilot.getLookAngle().x * ((float) Math.PI / 180F) / 4 * -1));
//            Neck2.setRotationY((float) (pilot.getLookAngle().y * ((float) Math.PI / 180F) / 4 * -1));
//            Neck3.setRotationX((float) (pilot.getLookAngle().x  * ((float) Math.PI / 180F) / 4 * -1));
//            Head.setRotationY((float) (pilot.getLookAngle().y * ((float) Math.PI / 180F) / 4 * -1));
//
//            float rotChange = dragon.getChangeInYaw();
//
//
//            float currentBodyPitch = Mth.lerp(0.1F, pilot.xRotO, 40);
//            float currentBodyYaw = Mth.lerp(0.1F, pilot.getYRot(), 40);
//           float currentBodyYaw = pilot.getYRot() - pilot.yRotO;
//           float currentBodyYawClamp = Mth.clamp(entity.getYRot() - entity.yRotO, -10, 10);
//
//
//
//            headtracking
//            Tail1.setRotationY((float) (pilot.getLookAngle().y * ((float) Math.PI / 180F) / 4 * -1));
//            Tail1.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F) * 2);
//            Tail2.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F) * 2);
//            Tail3.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F) * 2);
//            Tail4.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F) * 2);
//            Tail5.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F) * 2);
//            Tail6.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F) * 2);
//

//        }
