package com.GACMD.isleofberk.particles;

import com.GACMD.isleofberk.registery.ModParticles;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GasAoEEmitter extends NoRenderParticle {
    private int life;

    GasAoEEmitter(ClientLevel pLevel, double pX, double pY, double pZ) {
        super(pLevel, pX, pY, pZ, 0.0D, 0.0D, 0.0D);
    }

    public void tick() {
        int lifeTime = 8;
        if (this.getLifetime()  % 15 == 0) {
            double d0 = this.x + (this.random.nextDouble() - this.random.nextDouble()) * 2.0D;
            double d1 = this.y + (this.random.nextDouble() - this.random.nextDouble()) * 2.0D;
            double d2 = this.z + (this.random.nextDouble() - this.random.nextDouble()) * 2.0D;
        this.level.addParticle(ModParticles.GAS.get(), d0, d1, d2, ((float)this.life / (float) lifeTime), 0.0D, 0.0D);
        }

        ++this.life;
        if (this.life == lifeTime) {
            this.remove();
        }

    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return new GasAoEEmitter(pLevel, pX, pY, pZ);
        }
    }
}
