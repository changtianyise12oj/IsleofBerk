package com.GACMD.isleofberk.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SkrillLightningParticle extends ABaseScalableGlowParticle<SimpleParticleType> {

    protected SkrillLightningParticle(ClientLevel level, double xOriginal, double yOriginal, double zOriginal, double xVelocity, double yVelocity, double zVelocity, SpriteSet spriteSet, SimpleParticleType scalableParticle) {
        super(level, xOriginal, yOriginal, zOriginal, xVelocity, yVelocity, zVelocity, spriteSet);
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    public static class SkrillLightningParticleProvider implements ParticleProvider<SimpleParticleType> {
        SpriteSet spriteSet;

        public SkrillLightningParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(@NotNull SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return new SkrillLightningParticle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, this.spriteSet, pType);
        }
    }
}
