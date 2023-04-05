package com.GACMD.isleofberk.particles;

import com.GACMD.isleofberk.registery.ModParticles;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SkrillSkillEmitter extends NoRenderParticle {

    private int life;
    private final int lifeTime = 8;

    SkrillSkillEmitter(ClientLevel pLevel, double pX, double pY, double pZ) {
        super(pLevel, pX, pY, pZ, 0.0D, 0.0D, 0.0D);
    }

    public void tick() {
        for(int i = 0; i < 6; ++i) {
            // d0 - d2 radius
            double d0 = this.x + (this.random.nextDouble() - this.random.nextDouble()) * 4.0D;
            double d1 = this.y + (this.random.nextDouble() - this.random.nextDouble()) * 4.0D;
            double d2 = this.z + (this.random.nextDouble() - this.random.nextDouble()) * 4.0D;
            this.level.addParticle(ParticleTypes.ELECTRIC_SPARK, d0, d1, d2, ((float)this.life / (float)this.lifeTime), 0.0D, 0.0D);
        }

        ++this.life;
        if (this.life == this.lifeTime) {
            this.remove();
        }

    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return new SkrillSkillEmitter(pLevel, pX, pY, pZ);
        }
    }
}
