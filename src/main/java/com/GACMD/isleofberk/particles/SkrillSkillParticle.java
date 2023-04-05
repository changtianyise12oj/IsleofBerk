package com.GACMD.isleofberk.particles;


import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class SkrillSkillParticle extends TextureSheetParticle {

    private final SpriteSet sprites;

    SkrillSkillParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pQuadSizeMulitiplier, SpriteSet pSprites) {
        super(pLevel, pX, pY, pZ, 0.0D, 0.0D, 0.0D);
        this.lifetime = 6 + this.random.nextInt(4);
        this.quadSize = 2.0F * (1.0F - (float)pQuadSizeMulitiplier * 0.5F);
        this.sprites = pSprites;
        this.setSpriteFromAge(pSprites);
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.setSpriteFromAge(this.sprites);
        }
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        SpriteSet spriteSet;

        public Provider (SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return new SkrillSkillParticle(pLevel, pX, pY, pZ, pXSpeed, this.spriteSet);
        }
    }
}
