package com.GACMD.isleofberk.entity.dragons.gronckle;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.entity.base.render.model.BaseDragonModel;
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
public class GronckleModel extends BaseDragonModel<Gronckle> {
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
        switch (entity.getDragonVariant()) {
            default:
            case 0:
                return new ResourceLocation("isleofberk:textures/dragons/gronckle/meatlug.png");
            case 1:
                return new ResourceLocation("isleofberk:textures/dragons/gronckle/green.png");
            case 2:
                return new ResourceLocation("isleofberk:textures/dragons/gronckle/blue.png");
            case 3:
                return new ResourceLocation("isleofberk:textures/dragons/gronckle/gronckle.png");
            case 4:
                return new ResourceLocation("isleofberk:textures/dragons/gronckle/orange.png");
            case 5:
                return new ResourceLocation("isleofberk:textures/dragons/gronckle/purple.png");
            case 6:
                return new ResourceLocation("isleofberk:textures/dragons/gronckle/red.png");
            case 7:
                return new ResourceLocation("isleofberk:textures/dragons/gronckle/rose.png");
            case 8:
                return new ResourceLocation("isleofberk:textures/dragons/gronckle/valentine.png");
            case 9:
                return new ResourceLocation("isleofberk:textures/dragons/gronckle/lime.png");
        }
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Gronckle entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "animations/dragons/gronckle.animation.json");
    }

    @Override
    public void setLivingAnimations(Gronckle dragon, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(dragon, uniqueID, customPredicate);
        // do not use headtracking when elytra flying
        if (dragon.getOwner() instanceof Player player && !dragon.isDragonFollowing() && !player.isFallFlying()) {
            // call for model bones, all called bones must exist in the model
            IBone head = this.getAnimationProcessor().getBone("head");

            // whatever tf is that, used for head tracking
            // will probably stop working at some point
            EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);

            // floats to fix paused game mess
            float rotHeadY;
            float rotHeadX;

            // checks if game is paused - head spinning/heaven ascension on pause fix
            if (Minecraft.getInstance().isPaused()) {
                rotHeadY = 0;
                rotHeadX = 0;
            } else {
                rotHeadY = head.getRotationY();
                rotHeadX = head.getRotationX();
            }

            // head tracking when not mounted
            if (!dragon.shouldStopMovingIndependently()) {
                head.setRotationY(rotHeadY + extraData.netHeadYaw * ((float) Math.PI / 180F));
                head.setRotationX(rotHeadX + extraData.headPitch * ((float) Math.PI / 180F) / 2);
            }
        }
    }
}
