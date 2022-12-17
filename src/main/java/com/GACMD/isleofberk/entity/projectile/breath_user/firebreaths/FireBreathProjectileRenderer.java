package com.GACMD.isleofberk.entity.projectile.breath_user.firebreaths;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class FireBreathProjectileRenderer extends EntityRenderer<FireBreathProjectile> {

    public FireBreathProjectileRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public ResourceLocation getTextureLocation(FireBreathProjectile pEntity) {
        return null;
    }
}
