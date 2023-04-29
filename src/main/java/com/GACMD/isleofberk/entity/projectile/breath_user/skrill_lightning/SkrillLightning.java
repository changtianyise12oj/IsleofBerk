package com.GACMD.isleofberk.entity.projectile.breath_user.skrill_lightning;

import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideable;
import com.GACMD.isleofberk.entity.projectile.abase.BaseLinearFlightProjectile;
import com.GACMD.isleofberk.registery.ModEntities;
import com.GACMD.isleofberk.registery.ModMobEffects;
import com.GACMD.isleofberk.registery.ModParticles;
import com.GACMD.isleofberk.util.Util;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.*;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;

public class SkrillLightning extends BaseLinearFlightProjectile {

    public SkrillLightning(EntityType<? extends SkrillLightning> projectile, Level level) {
        super(projectile, level);
    }

    public SkrillLightning(ADragonBaseFlyingRideable dragonOwner, Vec3 throat, Vec3 end, Level level) {
        super(ModEntities.SKRILL_LIGHTNING.get(), dragonOwner, throat, end, level, 1);

    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    @Override
    public void playParticles() {

        Vec3 vec3 = this.getDeltaMovement();
        double deltaX = vec3.x;
        double deltaY = vec3.y;
        double deltaZ = vec3.z;
        double dist = Math.ceil(Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ) * 0.7F);

        double posX = (xo + deltaX) + (this.random.nextDouble() - this.random.nextDouble()) * 2;
        double posY = (yo + deltaY) + (this.random.nextDouble() - this.random.nextDouble()) * 2;
        double posZ = (zo + deltaZ) + (this.random.nextDouble() - this.random.nextDouble()) * 2;

        for (double j = 0; j < dist; j++) {

            double scale = 0.05f;
            double coeff = j / dist;
            ParticleOptions particleOptions = ModParticles.SKRILL_LIGHTNING_PARTICLES.get();
            level.addParticle(particleOptions, true,
                    (xo + deltaX * coeff),
                    (yo + deltaY * coeff) + 0.1,
                    (zo + deltaZ * coeff),
                    scale * (random.nextFloat() - 0.3f),
                    scale * (random.nextFloat() - 0.3f),
                    scale * (random.nextFloat() - 0.3f));
        }

        level.addParticle(ParticleTypes.ELECTRIC_SPARK, true,
                posX,
                posY,
                posZ,
                5f * (random.nextFloat() - 0.5f),
                5f * (random.nextFloat() - 0.5f),
                5f * (random.nextFloat() - 0.5f));

        if (this.tickCount % 5 == 0) {
            level.addParticle(ModParticles.SKRILL_SKILL_PARTICLES.get(), true,
                    posX,
                    posY,
                    posZ,
                    25f * (random.nextFloat() - 0.5f),
                    25f * (random.nextFloat() - 0.5f),
                    25f * (random.nextFloat() - 0.5f));
        }
    }

    @Override
    public void tick() {
        super.tick();
        Entity owner = this.getOwner();
        if (this.level.isClientSide || (owner == null || !owner.isRemoved())) {
            HitResult hitresult = ProjectileUtil.getHitResult(this, this::canHitEntity);

            if (hitresult.getType() != HitResult.Type.MISS) end = hitresult.getLocation();

            if (!this.level.isClientSide() && hitresult.getType() != HitResult.Type.MISS && !ForgeEventFactory.onProjectileImpact(this, hitresult)) {

                HitResult.Type hitresult$type = hitresult.getType();
                if (hitresult$type == HitResult.Type.ENTITY && hitresult instanceof EntityHitResult entityHitResult) {
                    Entity entity = entityHitResult.getEntity();
                    boolean mobGriefing = ForgeEventFactory.getMobGriefingEvent(this.level, this.getOwner());
                    if (entity != dragon) {
                        if (mobGriefing) {
                            entity.hurt(DamageSource.explosion(this.dragon), 14);
                            this.discard();
                            this.gameEvent(GameEvent.PROJECTILE_LAND, this.getOwner());

                        } else if (!mobGriefing && !entity.hasCustomName()) {
                            if (entity instanceof TamableAnimal tamableAnimal && !tamableAnimal.isTame()) {
                                entity.hurt(DamageSource.explosion(this.dragon), 8);
                                if (entity.isOnFire()) {
                                    this.explode(getOwner(), entity.getX(), entity.getY(), entity.getZ(), 4, true, Explosion.BlockInteraction.NONE);
                                }
                                this.discard();
                                this.gameEvent(GameEvent.PROJECTILE_LAND, this.getOwner());
                            }
                        }
                    }
                    return;
                } else if (hitresult$type == HitResult.Type.BLOCK && hitresult instanceof BlockHitResult blockHitResult) {
                    // bypass through foliage blocks. flowers, grass, tall grass
                    if (!(level.getBlockState(blockHitResult.getBlockPos()).getBlock() instanceof BushBlock)) {
                        boolean flag = ForgeEventFactory.getMobGriefingEvent(this.level, this.getOwner());

                        // reduce amount of gas spawned
                        this.makeAreaOfEffectCloud();
                        this.discard();
                        this.gameEvent(GameEvent.PROJECTILE_LAND, this.getOwner());
                    }
                }
            }

            this.checkInsideBlocks();
            Vec3 deltaMovement = this.getDeltaMovement();
            double x = this.getX() + deltaMovement.x;
            double y = this.getY() + deltaMovement.y;
            double z = this.getZ() + deltaMovement.z;
            float inertia = this.getInertia();
            if (this.isInWater()) {
                for (int i = 0; i < 4; ++i) {
                    this.level.addParticle(ParticleTypes.BUBBLE, x - deltaMovement.x * 0.25D, y - deltaMovement.y * 0.25D, z - deltaMovement.z * 0.25D, deltaMovement.x, deltaMovement.y, deltaMovement.z);
                }
                inertia = 0.8F;
            }

            this.setDeltaMovement(deltaMovement.add(this.xPower, this.yPower, this.zPower).scale(inertia));
            this.setPos(x, y, z);
        }

        playParticles();

    }

    private void makeAreaOfEffectCloud() {
        AreaEffectCloud areaeffectcloud = new AreaEffectCloud(this.level, this.getX(), this.getY(), this.getZ());
        LivingEntity entity = this.getOwner();
        if (entity != null) {
            areaeffectcloud.setOwner(entity);
        }
        areaeffectcloud.setRadius(2.0F);
        areaeffectcloud.setRadiusOnUse(-0.5F);
        areaeffectcloud.setWaitTime(5);
        areaeffectcloud.setDuration(40);
        areaeffectcloud.setRadiusPerTick(-areaeffectcloud.getRadius() / (float) areaeffectcloud.getDuration());
        areaeffectcloud.setParticle(ModParticles.LIGHTNING_AOE_EMITTER.get());
        areaeffectcloud.addEffect(new MobEffectInstance(ModMobEffects.SHOCK.get(), Util.secondsToTicks(5)));

        this.level.addFreshEntity(areaeffectcloud);
    }

    @Override
    protected boolean canHitEntity(Entity hitEntity) {
        return super.canHitEntity(hitEntity) && hitEntity != dragon;
    }

    @Override
    protected @NotNull ParticleOptions getTrailParticle() {
        return ModParticles.SKRILL_LIGHTNING_PARTICLES.get();
    }

    @Override
    protected int threshHoldForDeletion() {
        return 20;
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

    @Override
    protected Explosion explode(ADragonBase dragon, double x, double y, double z, float explosionStrength, boolean flag, Explosion.BlockInteraction blockInteraction) {
        return null;
    }
}
