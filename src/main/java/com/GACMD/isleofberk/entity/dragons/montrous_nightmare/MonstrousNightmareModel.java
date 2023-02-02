package com.GACMD.isleofberk.entity.dragons.montrous_nightmare;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.entity.base.render.model.BaseDragonModel;
import com.GACMD.isleofberk.entity.base.render.model.BaseDragonModelFlying;
import com.GACMD.isleofberk.entity.dragons.tryiple_stryke.TripleStryke;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class MonstrousNightmareModel extends BaseDragonModelFlying<MonstrousNightmare> {
    public MonstrousNightmareModel(EntityRendererProvider.Context renderManager) {

    }

    @Override
    public ResourceLocation getModelLocation(MonstrousNightmare entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "geo/dragons/nightmare.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(MonstrousNightmare entity) {
        switch (entity.getDragonVariant()) {
            default:
            case 0:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/nightmare/hookfang.png");
            case 1:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/nightmare/blue.png");
            case 2:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/nightmare/yellow.png");
            case 3:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/nightmare/mint.png");
            case 4:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/nightmare/pink.png");
            case 5:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/nightmare/cyan.png");
            case 6:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/nightmare/orange.png");
            case 7:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/nightmare/green.png");
            case 8:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/nightmare/magenta.png");
            case 9:
                return new ResourceLocation(IsleofBerk.MOD_ID, "textures/dragons/nightmare/black.png");
        }
    }

    @Override
    public ResourceLocation getAnimationFileLocation(MonstrousNightmare entity) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "animations/dragons/nightmare.animation.json");
    }

    @Override
    public void setLivingAnimations(MonstrousNightmare dragon, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(dragon, uniqueID, customPredicate);

        // list of bones
        IBone neck1 = this.getAnimationProcessor().getBone("Neck1");
        IBone neck2 = this.getAnimationProcessor().getBone("Neck2");
        IBone neck3 = this.getAnimationProcessor().getBone("Neck3");
        IBone neck4 = this.getAnimationProcessor().getBone("Neck4");
        IBone head = this.getAnimationProcessor().getBone("head");

        // head tracking
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);

        if (!dragon.shouldStopMovingIndependently() && !Minecraft.getInstance().isPaused()) {
            neck1.setRotationZ(neck1.getRotationZ() + extraData.netHeadYaw * ((float) Math.PI / 180F) / 5);
            neck2.setRotationZ(neck2.getRotationZ() + extraData.netHeadYaw * ((float) Math.PI / 180F) / 5);
            neck3.setRotationZ(neck3.getRotationZ() + extraData.netHeadYaw * ((float) Math.PI / 180F) / 5);
            neck4.setRotationZ(neck3.getRotationZ() + extraData.netHeadYaw * ((float) Math.PI / 180F) / 5);
            head.setRotationZ(head.getRotationZ() + extraData.netHeadYaw * ((float) Math.PI / 180F) / 5);
            neck1.setRotationX(neck1.getRotationX() + extraData.headPitch * ((float) Math.PI / 180F) / 5);
            neck2.setRotationX(neck2.getRotationX() + extraData.headPitch * ((float) Math.PI / 180F) / 5);
            neck3.setRotationX(neck3.getRotationX() + extraData.headPitch * ((float) Math.PI / 180F) / 5);
            neck4.setRotationX(neck4.getRotationX() + extraData.headPitch * ((float) Math.PI / 180F) / 5);
            head.setRotationX(head.getRotationX() + extraData.headPitch * ((float) Math.PI / 180F) / 5);
        }
    }

    @Override
    public String getMainBodyBone() {
        return "main";
    }
}
