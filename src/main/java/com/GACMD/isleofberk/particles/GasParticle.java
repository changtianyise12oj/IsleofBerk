package com.GACMD.isleofberk.particles;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class GasParticle extends TextureSheetParticle {

    private final SpriteSet sprites;
    private final int rotation;
    private final boolean isFlipped;

    GasParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pQuadSizeMulitiplier, SpriteSet pSprites) {
        super(pLevel, pX, pY, pZ, 0.0D, 0.0D, 0.0D);
        this.lifetime = 15 + this.random.nextInt(10);
        this.quadSize = 0.6F * (1.0F - (float)pQuadSizeMulitiplier * 0.3F);
        this.sprites = pSprites;
        this.pickSprite(pSprites);
        Random random = new Random();
        this.rotation = random.nextInt(4) * 90;
        this.isFlipped = random.nextInt(2) == 0;
    }

    @Override
    public void render(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
        // Corners
        Vector3f[] cornerVectors = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
        // Position
        float posX = (float)(Mth.lerp(partialTicks, this.xo, this.x) - renderInfo.getPosition().x());
        float posY = (float)(Mth.lerp(partialTicks, this.yo, this.y) - renderInfo.getPosition().y());
        float posZ = (float)(Mth.lerp(partialTicks, this.zo, this.z) - renderInfo.getPosition().z());
        // Rotation
        Quaternion quaternion;
        if (this.roll == 0.0F) {
            quaternion = renderInfo.rotation();
        } else {
            quaternion = new Quaternion(renderInfo.rotation());
            quaternion.mul(Vector3f.ZP.rotation(Mth.lerp(partialTicks, this.oRoll, this.roll)));
        }
        // Rotation Randomizer
        if (this.rotation != 0) {
            quaternion.mul(Vector3f.ZP.rotationDegrees(this.rotation));
        }
        // Transform the Quad's corner Vectors based on Camera Position and Rotation
        for(int i = 0; i < 4; ++i) {
            Vector3f vector3f = cornerVectors[i];
            vector3f.transform(quaternion);
            vector3f.mul(this.getQuadSize(partialTicks));
            vector3f.add(posX, posY, posZ);
        }

        // Render the Quad
        buffer.vertex(cornerVectors[0].x(), cornerVectors[0].y(), cornerVectors[0].z()).uv(this.isFlipped ? this.getU0() : this.getU1(), this.getV1()).color(1.0F, 1.0F, 1.0F, 1.0F).uv2(LightTexture.FULL_BRIGHT).endVertex();
        buffer.vertex(cornerVectors[1].x(), cornerVectors[1].y(), cornerVectors[1].z()).uv(this.isFlipped ? this.getU0() : this.getU1(), this.getV0()).color(1.0F, 1.0F, 1.0F, 1.0F).uv2(LightTexture.FULL_BRIGHT).endVertex();
        buffer.vertex(cornerVectors[2].x(), cornerVectors[2].y(), cornerVectors[2].z()).uv(this.isFlipped ? this.getU1() : this.getU0(), this.getV0()).color(1.0F, 1.0F, 1.0F, 1.0F).uv2(LightTexture.FULL_BRIGHT).endVertex();
        buffer.vertex(cornerVectors[3].x(), cornerVectors[3].y(), cornerVectors[3].z()).uv(this.isFlipped ? this.getU1() : this.getU0(), this.getV1()).color(1.0F, 1.0F, 1.0F, 1.0F).uv2(LightTexture.FULL_BRIGHT).endVertex();
    }

    public void tick() {
        this.xo = this.x;
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

    public static class GasParticleProvider implements ParticleProvider<SimpleParticleType> {
        SpriteSet spriteSet;

        public GasParticleProvider (SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return new GasParticle(pLevel, pX, pY, pZ, pXSpeed, this.spriteSet);
        }
    }
}

