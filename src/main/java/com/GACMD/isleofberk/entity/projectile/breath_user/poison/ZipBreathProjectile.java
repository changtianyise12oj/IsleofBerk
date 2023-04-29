package com.GACMD.isleofberk.entity.projectile.breath_user.poison;

import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideable;
import com.GACMD.isleofberk.entity.projectile.abase.BaseLinearFlightProjectile;
import com.GACMD.isleofberk.registery.ModEntities;
import com.GACMD.isleofberk.registery.ModParticles;
import com.GACMD.isleofberk.util.Util;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;

public class ZipBreathProjectile extends BaseLinearFlightProjectile {

    ADragonBase dragon;
    public double ticksExisted;
    Vec3 end;

    public ZipBreathProjectile(EntityType<? extends ZipBreathProjectile> projectile, Level level) {
        super(projectile, level);
    }

    public ZipBreathProjectile(ADragonBaseFlyingRideable dragonOwner, Vec3 throat, Vec3 end, Level level) {
        super(ModEntities.ZIP_POISON.get(), dragonOwner, throat, end, level, 1);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    @Override
    public void playParticles() {

        // size increasing with projectile age
        float scale = (float)ticksExisted * 2 + 5;

        // position randomness increasing with projectile age
        double posX = this.xo + (this.random.nextDouble() - this.random.nextDouble()) * ticksExisted;
        double posY = this.yo + (this.random.nextDouble() - this.random.nextDouble()) * ticksExisted;
        double posZ = this.zo + (this.random.nextDouble() - this.random.nextDouble()) * ticksExisted;

        ParticleOptions particleOptions = ModParticles.GAS.get();
        level.addParticle(particleOptions, true,
                posX,
                posY,
                posZ,
                scale + 0.1 * (random.nextFloat() - 0.5f),
                scale + 0.1 * (random.nextFloat() - 0.5f),
                scale + 0.1 * (random.nextFloat() - 0.5f));
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

                            // add exception if mobGriefing is false,
                            // allow damage only to tamable mobs that aren't tamed
                            // and other hostile mobs such as bosses to make dragons useful in combat.
                            // no damage to pets(tamed mobs and mobs that have name tags)
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

        // kill entity when ticks exceed 250 to remove lag
        ticksExisted++;
        if (ticksExisted > 7) {
            this.discard();
            ticksExisted = 0;
        }

    }
    @Override
    protected boolean canHitEntity(Entity hitEntity) {
        return super.canHitEntity(hitEntity) && hitEntity != dragon;
    }

    @Override
    protected @NotNull ParticleOptions getTrailParticle() {
        return ParticleTypes.EFFECT;
    }

    private void makeAreaOfEffectCloud() {
        ZippleBackAOECloud areaeffectcloud = new ZippleBackAOECloud(this.level, this.getX(), this.getY(), this.getZ());
        LivingEntity entity = this.getOwner();
        if (entity != null) {
            areaeffectcloud.setOwner(entity);
        }
        areaeffectcloud.setRadius(2.0F);
        areaeffectcloud.setRadiusOnUse(-0.5F);
        areaeffectcloud.setWaitTime(10);
        areaeffectcloud.setRadiusPerTick(-areaeffectcloud.getRadius() / (float) areaeffectcloud.getDuration());
        areaeffectcloud.setPotion(Potions.POISON);
        areaeffectcloud.setParticle(ModParticles.GAS_AOE_EMITTER.get());
        areaeffectcloud.addEffect(new MobEffectInstance(MobEffects.POISON, Util.secondsToTicks(25)));

        this.level.addFreshEntity(areaeffectcloud);
    }

    /**
     * Similar to setArrowHeading, it's point the throwable entity to a x, y, z direction.
     */
    public void shoot(Vec3 end, float partialTicks) {
        if (partialTicks == 1) {
            Vec3 endVec = (new Vec3(end.x() * 4.8, end.y() * 4.8, end.z() * 4.8));
            this.setDeltaMovement(endVec);
            double d0 = end.horizontalDistance();
            this.setYRot((float) (Mth.atan2(end.x, end.z) * (double) (180F / (float) Math.PI)));
            this.setXRot((float) (Mth.atan2(end.y, d0) * (double) (180F / (float) Math.PI)));
            this.yRotO = this.getYRot();
            this.xRotO = this.getYRot();
        }
    }

    @Override
    protected Explosion explode(ADragonBase dragon, double x, double y, double z, float explosionStrength, boolean flag, Explosion.BlockInteraction blockInteraction) {
        return null;
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public float getBrightness() {
        return 0;
    }

    public static class ZipBreathProjectileRenderer extends EntityRenderer<ZipBreathProjectile> {

        public ZipBreathProjectileRenderer(EntityRendererProvider.Context p_174008_) {
            super(p_174008_);
        }

        @Override
        public ResourceLocation getTextureLocation(ZipBreathProjectile pEntity) {
            return null;
        }
    }
}

