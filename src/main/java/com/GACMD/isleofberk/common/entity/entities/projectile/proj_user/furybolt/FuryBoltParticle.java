package com.GACMD.isleofberk.common.entity.entities.projectile.proj_user.furybolt;

import com.GACMD.isleofberk.common.entity.entities.projectile.ScalableParticleType;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FuryBoltParticle<T extends SimpleParticleType> extends GlowParticle {

    protected final SpriteSet spriteSet;
    protected SimpleParticleType scalableParticle;

    protected FuryBoltParticle(ClientLevel level, double xOriginal, double yOriginal, double zOriginal, double xVelocity, double yVelocity, double zVelocity, SpriteSet spriteSet, T scalableParticle) {
        super(level, xOriginal, yOriginal, zOriginal, xVelocity, yVelocity, zVelocity, spriteSet);
        this.lifetime = 12;
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

//        this.scale(scalableParticle.getScale());

    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    public static class FuryBoltParticleProvider implements ParticleProvider<SimpleParticleType> {
        SpriteSet spriteSet;

        public FuryBoltParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(@NotNull SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return new FuryBoltParticle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, this.spriteSet, pType);
        }
    }
}
