package com.GACMD.isleofberk.entity.dragons.deadlynadder;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.entity.base.render.model.BaseDragonModel;
import com.GACMD.isleofberk.entity.base.render.model.BaseDragonModelFlying;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

@OnlyIn(Dist.CLIENT)
public class DeadlyNadderModel extends BaseDragonModelFlying<DeadlyNadder> {

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
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/deadly_nadder/deadly_nadder.png");
            case 2:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/deadly_nadder/kingstail.png");
            case 3:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/deadly_nadder/scardian.png");
            case 4:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/deadly_nadder/springshedder.png");
            case 5:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/deadly_nadder/hjarta.png");
            case 6:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/deadly_nadder/bork_week.png");
            case 7:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/deadly_nadder/flystorm.png");
            case 8:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/deadly_nadder/hjaldr.png");
            case 9:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/deadly_nadder/barklethorn.png");
            case 10:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/deadly_nadder/lethal_lancebeak.png");
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

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);

        if (!dragon.shouldStopMovingIndependently() && !Minecraft.getInstance().isPaused() && !dragon.isRenderedOnGUI()) {
            neck1.setRotationY(neck1.getRotationY() + (extraData.netHeadYaw * ((float) Math.PI / 180F) / 3));
            neck2.setRotationY(neck2.getRotationY() + (extraData.netHeadYaw * ((float) Math.PI / 180F) / 3));
            head.setRotationY(head.getRotationY() + (extraData.netHeadYaw * ((float) Math.PI / 180F) / 3));
            neck1.setRotationX(neck1.getRotationX() + (extraData.headPitch * ((float) Math.PI / 180F) / 3));
            head.setRotationX(head.getRotationX() + (extraData.headPitch * ((float) Math.PI / 180F) / 3));

        }

        // hides tail spikes depending on amount of remaining shots
        tailSpike5.setHidden(dragon.getRemainingSecondFuel() < dragon.getMaxSecondFuel() * 0.80);
        tailSpike4.setHidden(dragon.getRemainingSecondFuel() < dragon.getMaxSecondFuel() * 0.60);
        tailSpike3.setHidden(dragon.getRemainingSecondFuel() < dragon.getMaxSecondFuel() * 0.40);
        tailSpike2.setHidden(dragon.getRemainingSecondFuel() < dragon.getMaxSecondFuel() * 0.20);
        tailSpike1.setHidden(dragon.getRemainingSecondFuel() == 0);

    }

    @Override
    public String getMainBodyBone() {
        return "rotation";
    }
}
