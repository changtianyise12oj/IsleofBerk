package com.GACMD.isleofberk.common.entity.entities.projectile.proj_user.fire_bolt;

import com.GACMD.isleofberk.common.entity.entities.projectile.ScalableParticleType;
import com.GACMD.isleofberk.common.entity.entities.projectile.proj_user.ABaseScalableGlowParticle;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FireBoltParticle extends ABaseScalableGlowParticle<SimpleParticleType> {

    protected FireBoltParticle(ClientLevel level, double xOriginal, double yOriginal, double zOriginal, double xVelocity, double yVelocity, double zVelocity, SpriteSet spriteSet, SimpleParticleType scalableParticle) {
        super(level, xOriginal, yOriginal, zOriginal, xVelocity, yVelocity, zVelocity, spriteSet);
    }

    @Override
    protected float getScale() {
        return 0.65F;
    }

    public static class FireBoltParticleProvider implements ParticleProvider<SimpleParticleType> {
        SpriteSet spriteSet;

        public FireBoltParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return new FireBoltParticle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, this.spriteSet, pType);
        }
    }
}
