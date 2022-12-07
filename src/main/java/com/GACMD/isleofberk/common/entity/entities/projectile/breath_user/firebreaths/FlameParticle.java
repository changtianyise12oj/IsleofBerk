package com.GACMD.isleofberk.common.entity.entities.projectile.breath_user.firebreaths;

import com.GACMD.isleofberk.common.entity.entities.projectile.ScalableParticleType;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FlameParticle extends GlowParticle {

    protected final SpriteSet spriteSet;
    protected ScalableParticleType scalableParticle;

    protected FlameParticle(ClientLevel level, double xOriginal, double yOriginal, double zOriginal, double xVelocity, double yVelocity, double zVelocity, SpriteSet spriteSet, ScalableParticleType scalableParticle) {
        super(level, xOriginal, yOriginal, zOriginal, xVelocity, yVelocity, zVelocity, spriteSet);
        this.lifetime = 5;
        this.spriteSet = spriteSet;
        this.hasPhysics = false;
        this.friction=0;
        this.scalableParticle=scalableParticle;

        this.setSpriteFromAge(spriteSet);
    }

    public float getQuadSize(float pScaleFactor) {
        float f = (float)this.age / (float)this.lifetime;
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
        this.scale(scalableParticle.getScale());
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    public static class FlameParticleProvider implements ParticleProvider<ScalableParticleType> {
        SpriteSet spriteSet;

        public FlameParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(ScalableParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return new FlameParticle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, this.spriteSet, pType);
        }
    }
}
