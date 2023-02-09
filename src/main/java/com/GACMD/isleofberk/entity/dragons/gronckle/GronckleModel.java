package com.GACMD.isleofberk.entity.dragons.gronckle;

import com.GACMD.isleofberk.IsleofBerk;
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
public class GronckleModel extends BaseDragonModelFlying<Gronckle> {
    public GronckleModel(EntityRendererProvider.Context renderManager) {
        super();
    }

    @Override
    protected float getBabyHeadSize() {
        return 1.2f;
    }

    @Override
    public ResourceLocation getModelLocation(Gronckle entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "geo/dragons/gronckle.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Gronckle entity) {
        int i = entity.getDragonVariant();
        if (i == 0) {
            return new ResourceLocation("isleofberk:textures/dragons/gronckle/meatlug.png");
        } else if (i == 1) {

            return new ResourceLocation("isleofberk:textures/dragons/gronckle/hjarta.png");
        } else if (i == 2) {

            return new ResourceLocation("isleofberk:textures/dragons/gronckle/junior_tuffnut_junior.png");
        } else if (i == 3) {

            return new ResourceLocation("isleofberk:textures/dragons/gronckle/gronckle.png");
        } else if (i == 4) {

            return new ResourceLocation("isleofberk:textures/dragons/gronckle/cheesemonger.png");
        } else if (i == 5) {

            return new ResourceLocation("isleofberk:textures/dragons/gronckle/exiled.png");
        } else if (i == 6) {

            return new ResourceLocation("isleofberk:textures/dragons/gronckle/rubblegrubber.png");
        } else if (i == 7) {

            return new ResourceLocation("isleofberk:textures/dragons/gronckle/barn.png");
        } else if (i == 8) {

            return new ResourceLocation("isleofberk:textures/dragons/gronckle/crubble.png");
        } else if (i == 9) {

            return new ResourceLocation("isleofberk:textures/dragons/gronckle/yawnckle.png");
        } else {

            return new ResourceLocation("isleofberk:textures/dragons/gronckle/meatlug.png");
        }
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Gronckle entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "animations/dragons/gronckle.animation.json");
    }

    @Override
    public void setLivingAnimations(Gronckle dragon, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(dragon, uniqueID, customPredicate);
        if (dragon.getOwner() instanceof Player player && !dragon.isDragonFollowing() && !player.isFallFlying()) {
            IBone head = this.getAnimationProcessor().getBone("head");

            EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);

            if (!dragon.shouldStopMovingIndependently() && !Minecraft.getInstance().isPaused() && !dragon.isRenderedOnGUI()) {
                head.setRotationY(head.getRotationY() + extraData.netHeadYaw * ((float) Math.PI / 180F) / 2);
                head.setRotationX(head.getRotationX() + extraData.headPitch * ((float) Math.PI / 180F) / 2);
            }
        }
    }

    @Override
    public String getMainBodyBone() {
        return "rotation";
    }
}
