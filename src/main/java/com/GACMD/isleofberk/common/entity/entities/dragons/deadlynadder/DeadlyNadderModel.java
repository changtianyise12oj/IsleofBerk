package com.GACMD.isleofberk.common.entity.entities.dragons.deadlynadder;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.common.entity.entities.base.render.model.BaseDragonModel;
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
public class DeadlyNadderModel extends BaseDragonModel<DeadlyNadder> {

    public DeadlyNadderModel(EntityRendererProvider.Context renderManager) {
        super();
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

        // call for model bones, all called bones must exist in the model
        IBone tailSpike5 = this.getAnimationProcessor().getBone("Tail5Spikes");
        IBone tailSpike4 = this.getAnimationProcessor().getBone("Tail4Spikes");
        IBone tailSpike3 = this.getAnimationProcessor().getBone("Tail3Spikes");
        IBone tailSpike2 = this.getAnimationProcessor().getBone("Tail2Spikes");
        IBone tailSpike1 = this.getAnimationProcessor().getBone("Tail1Spikes"); // base of the tail

        IBone neck1 = this.getAnimationProcessor().getBone("Neck1");
        IBone neck2 = this.getAnimationProcessor().getBone("Neck2");
        IBone head = this.getAnimationProcessor().getBone("head");

        // whatever tf is that, used for head tracking
        // will probably stop working at some point
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);

        // floats to fix paused game mess
        float rotNeck1Y;
        float rotNeck2Y;
        float rotHeadY;
        float rotNeck1X;
        float rotNeck2X;
        float rotHeadX;

        // checks if game is paused - head spinning/heaven ascension on pause fix
        if (Minecraft.getInstance().isPaused()) {
            rotHeadY = 0;
            rotNeck1Y = 0;
            rotNeck2Y = 0;
            rotHeadX = 0;
            rotNeck1X = 0;
            rotNeck2X = 0;
        } else {
            rotHeadY = head.getRotationY();
            rotNeck1Y = neck1.getRotationY();
            rotNeck2Y = neck2.getRotationY();
            rotHeadX = head.getRotationX();
            rotNeck1X = neck1.getRotationX();
            rotNeck2X = neck2.getRotationX();
        }

        // head tracking when not mounted
        if (!dragon.shouldStopMovingIndependently() && dragon.getControllingPassenger() instanceof Player) {
            neck1.setRotationY(rotNeck1Y + extraData.netHeadYaw * ((float) Math.PI / 180F) / 3);
            neck2.setRotationY(rotNeck2Y + extraData.netHeadYaw * ((float) Math.PI / 180F) / 3);
            head.setRotationY(rotHeadY + extraData.netHeadYaw * ((float) Math.PI / 180F) / 3);
            neck1.setRotationX(rotNeck1X + extraData.headPitch * ((float) Math.PI / 180F) / 6);
            neck2.setRotationX(rotNeck2X + extraData.headPitch * ((float) Math.PI / 180F) / 6);
            head.setRotationX(rotHeadX + extraData.headPitch * ((float) Math.PI / 180F) / 6);
        }

        // hides tail spikes depending on amount of remaining shots
        tailSpike5.setHidden(dragon.getRemainingSecondFuel() < dragon.getMaxSecondFuel() * 0.80);
        tailSpike4.setHidden(dragon.getRemainingSecondFuel() < dragon.getMaxSecondFuel() * 0.60);
        tailSpike3.setHidden(dragon.getRemainingSecondFuel() < dragon.getMaxSecondFuel() * 0.40);
        tailSpike2.setHidden(dragon.getRemainingSecondFuel() < dragon.getMaxSecondFuel() * 0.20);
        tailSpike1.setHidden(dragon.getRemainingSecondFuel() == 0);

    }
}
