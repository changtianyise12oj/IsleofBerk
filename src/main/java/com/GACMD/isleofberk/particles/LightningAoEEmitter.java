package com.GACMD.isleofberk.particles;

import com.GACMD.isleofberk.registery.ModParticles;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class LightningAoEEmitter extends NoRenderParticle {
    private int life;

    LightningAoEEmitter(ClientLevel pLevel, double pX, double pY, double pZ) {
        super(pLevel, pX, pY, pZ, 0.0D, 0.0D, 0.0D);
    }

    public void tick() {

        int lifeTime = 8;
        double d0 = this.x + (this.random.nextDouble() - this.random.nextDouble()) * 1.5D;
        double d1 = this.y + (this.random.nextDouble() - this.random.nextDouble()) * 1.5D;
        double d2 = this.z + (this.random.nextDouble() - this.random.nextDouble()) * 1.5D;

        if (this.getLifetime()  % 20 == 0) {
            this.level.addParticle(ModParticles.SKRILL_SKILL_PARTICLES.get(), d0, d1, d2, ((float)this.life / (float) lifeTime), 0.0D, 0.0D);
        }
        if (this.getLifetime()  % 10 == 0) {
            this.level.addParticle(ParticleTypes.ELECTRIC_SPARK,
                    d0,
                    d1,
                    d2,
                    2f * (random.nextFloat() - 0.5f),
                    2f * (random.nextFloat() - 0.5f),
                    2f * (random.nextFloat() - 0.5f));
        }

        ++this.life;
        if (this.life == lifeTime) {
            this.remove();
        }

    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return new LightningAoEEmitter(pLevel, pX, pY, pZ);
        }
    }
}