package com.GACMD.isleofberk.entity.projectile.breath_user.firebreaths;

import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideable;
import com.GACMD.isleofberk.entity.projectile.abase.BaseLinearFlightProjectile;
import com.GACMD.isleofberk.registery.ModEntities;
import com.GACMD.isleofberk.registery.ModParticles;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class FireBreathSmallProjectile extends BaseLinearFlightProjectile {

    public FireBreathSmallProjectile(EntityType<? extends FireBreathSmallProjectile> projectile, Level level) {
        super(projectile, level);
    }

    public FireBreathSmallProjectile(ADragonBaseFlyingRideable dragonOwner, Vec3 throat, Vec3 end, Level level) {
        super(ModEntities.FIRE_SMALL_PROJ.get(), dragonOwner, throat, end, level, 1);

    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    public void playParticles() {

        // size increasing with projectile age
        float scale = (float)ticksExisted * 2f + 5;

        // position randomness increasing with projectile age
        double posX = this.xo + (this.random.nextDouble() - this.random.nextDouble()) * (ticksExisted / 6);
        double posY = this.yo + (this.random.nextDouble() - this.random.nextDouble()) * (ticksExisted / 6);
        double posZ = this.zo + (this.random.nextDouble() - this.random.nextDouble()) * (ticksExisted / 6);

        ParticleOptions particleOptions = ModParticles.FLAME.get();
        level.addParticle(particleOptions, true,
                posX,
                posY,
                posZ,
                (scale * 0.4) + 0.1 * (random.nextFloat() - 0.5f),
                (scale * 0.4) * 0.1 * (random.nextFloat() - 0.5f),
                (scale * 0.4) + 0.1 * (random.nextFloat() - 0.5f));

        if (this.tickCount % 5 == 0) {
            level.addParticle(ParticleTypes.LAVA, true,
                    posX,
                    posY,
                    posZ,
                    0.2f * (random.nextFloat() - 0.5f),
                    0.2f * (random.nextFloat() - 0.5f),
                    0.2f * (random.nextFloat() - 0.5f));
        }
    }


    @Override
    protected boolean canHitEntity(Entity hitEntity) {
        return super.canHitEntity(hitEntity) && hitEntity != dragon;
    }

    @Override
    protected @NotNull ParticleOptions getTrailParticle() {
        return ModParticles.FLAME.get();
    }

    @Override
    protected int threshHoldForDeletion() {
        return 10;
    }

    @Override
    public boolean ignoreExplosion() {
        return true;
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public float getBrightness() {
        return 0;
    }

    /**
     * Custom Explosion method used for making explosions with DragonSoulFire.
     *
     * @return The Explosion Object
     * @see net.minecraft.world.level.Explosion
     * @see Explosion#explode()
     */
    public Explosion explode(ADragonBase pEntity, double pX, double pY, double pZ, float pExplosionRadius, boolean pCausesFire, Explosion.BlockInteraction pMode) {
        return this.explode(pEntity, null, null, pX, pY, pZ, pExplosionRadius, pCausesFire, pMode);
    }

    private Explosion explode(@Nullable Entity pExploder, @Nullable DamageSource pDamageSource, @Nullable ExplosionDamageCalculator pContext, double pX, double pY, double pZ, float pSize, boolean pCausesFire, Explosion.BlockInteraction pMode) {
        FireBreathProjectile.FlameBreathExplosion explosion = new FireBreathProjectile.FlameBreathExplosion(this.level, pExploder, pDamageSource, pContext, pX, pY, pZ, pSize, true, pMode);
        if (net.minecraftforge.event.ForgeEventFactory.onExplosionStart(this.level, explosion)) return explosion;
        explosion.explode();
        explosion.finalizeExplosion(true);
        return explosion;
    }
}
