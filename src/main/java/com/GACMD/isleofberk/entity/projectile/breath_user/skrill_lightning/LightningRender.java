package com.GACMD.isleofberk.entity.projectile.breath_user.skrill_lightning;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class LightningRender extends EntityRenderer<SkrillLightning> {

    public LightningRender(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public ResourceLocation getTextureLocation(SkrillLightning pEntity) {
        return null;
    }
}