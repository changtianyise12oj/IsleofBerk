package com.GACMD.isleofberk.entity.projectile.proj_user;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.GlowParticle;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;

public class ABaseScalableGlowParticle<T extends SimpleParticleType> extends GlowParticle {

    protected final SpriteSet spriteSet;

    protected ABaseScalableGlowParticle(ClientLevel level, double xOriginal, double yOriginal, double zOriginal, double xVelocity, double yVelocity, double zVelocity, SpriteSet spriteSet) {
        super(level, xOriginal, yOriginal, zOriginal, xVelocity, yVelocity, zVelocity, spriteSet);
        this.lifetime = 12;
        this.spriteSet = spriteSet;
        this.hasPhysics = false;
        this.friction = 0;

        this.setSpriteFromAge(spriteSet);
    }

    public float getQuadSize(float pScaleFactor) {
        float f = (float) this.age / (float) this.lifetime;
        return this.quadSize * (5.0F - f * f * 5.0F);
    }

    @Override
    public void tick() {
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            super.tick();
            this.setSpriteFromAge(this.spriteSet);
        }
        this.scale(getScale());

    }

    protected float getScale() {
        return 1F;
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }
}
