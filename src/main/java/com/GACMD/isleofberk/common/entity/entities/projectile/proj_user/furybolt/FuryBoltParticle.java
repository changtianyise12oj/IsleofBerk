package com.GACMD.isleofberk.common.entity.entities.projectile.proj_user.furybolt;

import com.GACMD.isleofberk.common.entity.entities.projectile.proj_user.ABaseScalableGlowParticle;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FuryBoltParticle extends ABaseScalableGlowParticle<SimpleParticleType> {

    protected FuryBoltParticle(ClientLevel level, double xOriginal, double yOriginal, double zOriginal, double xVelocity, double yVelocity, double zVelocity, SpriteSet spriteSet, SimpleParticleType scalableParticle) {
        super(level, xOriginal, yOriginal, zOriginal, xVelocity, yVelocity, zVelocity, spriteSet);
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
