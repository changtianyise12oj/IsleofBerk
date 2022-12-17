package com.GACMD.isleofberk.entity.projectile;

import com.mojang.brigadier.StringReader;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;

public class ScalableParticleType extends ParticleType<ScalableParticleType> implements ParticleOptions {
    protected float scale;

    private static final ParticleOptions.Deserializer<ScalableParticleType> DESERIALIZER = new ParticleOptions.Deserializer<ScalableParticleType>() {
        public ScalableParticleType fromCommand(ParticleType<ScalableParticleType> p_123846_, StringReader p_123847_) {
            return (ScalableParticleType)p_123846_;
        }

        public ScalableParticleType fromNetwork(ParticleType<ScalableParticleType> p_123849_, FriendlyByteBuf p_123850_) {
            return (ScalableParticleType)p_123849_;
        }
    };
    private final Codec<ScalableParticleType> codec = Codec.unit(this::getType);

    public ScalableParticleType(boolean pOverrideLimiter) {
        super(pOverrideLimiter, DESERIALIZER);
    }

    public ScalableParticleType getType() {
        return this;
    }

    public Codec<ScalableParticleType> codec() {
        return this.codec;
    }



    public String writeToString() {
        return Registry.PARTICLE_TYPE.getKey(this).toString();
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf pBuffer) {
        pBuffer.writeFloat(scale);
    }
}
