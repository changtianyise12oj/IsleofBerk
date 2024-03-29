package com.GACMD.isleofberk.entity.projectile.abase;

import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideable;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseGroundRideable;
import com.GACMD.isleofberk.entity.base.dragon.ADragonRideableUtility;
import com.GACMD.isleofberk.entity.projectile.breath_user.poison.ZipBreathProjectile;
import com.GACMD.isleofberk.entity.projectile.breath_user.skrill_lightning.SkrillLightning;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.function.Predicate;

public abstract class BaseLinearFlightProjectile extends AbstractHurtingProjectile {
    public ADragonRideableUtility dragon;
    public double ticksExisted;
    protected int strengthRadius;
    protected Vec3 start;
    protected Vec3 end;
    protected int damageTier;

    protected static final EntityDataAccessor<Integer> PROJECTILE_SIZE = SynchedEntityData.defineId(BaseLinearFlightProjectile.class, EntityDataSerializers.INT);

    /**
     * registry constructor
     *
     * @param type
     * @param level
     */
    public BaseLinearFlightProjectile(EntityType<? extends AbstractHurtingProjectile> type, Level level) {
        super(type, level);
    }

    protected BaseLinearFlightProjectile(EntityType<? extends AbstractHurtingProjectile> type, ADragonRideableUtility owner, Vec3 start, Vec3 end, Level level, int strengthRadius) {
        super(type, level);
        this.dragon = owner;
        this.strengthRadius = strengthRadius;
        this.setOwner(owner);
        this.start = start;
        this.end = end;

        this.moveTo(this.start.x(), this.start.y(), this.start.z(), this.getYRot(), this.getXRot());
        this.reapplyPosition();
        double d0 = Math.sqrt(end.x() * end.x() + end.y() * end.y() + end.z() * end.z());
        if (d0 != 0.0D) {
            this.xPower = end.x() / d0 * 0.20D; // 0.30D
            this.yPower = end.y() / d0 * 0.20D; // 0.30D
            this.zPower = end.z() / d0 * 0.20D; // 0.30D
        }
    }

    public BaseLinearFlightProjectile(EntityType<? extends AbstractHurtingProjectile> pEntityType, ADragonRideableUtility owner, double pX, double pY, double pZ, double pOffsetX, double pOffsetY, double pOffsetZ, Level pLevel, int strengthRadius) {
        this(pEntityType, pLevel);
        this.dragon = owner;
        this.strengthRadius = strengthRadius;
        this.setOwner(owner);

        this.moveTo(pX, pY, pZ, this.getYRot(), this.getXRot());
        this.reapplyPosition();
        double d0 = Math.sqrt(pOffsetX * pOffsetX + pOffsetY * pOffsetY + pOffsetZ * pOffsetZ);
        if (d0 != 0.0D) {
            this.xPower = pOffsetX / d0 * 0.1D;
            this.yPower = pOffsetY / d0 * 0.1D;
            this.zPower = pOffsetZ / d0 * 0.1D;
        }

    }

    protected void defineSynchedData() {
        this.entityData.define(PROJECTILE_SIZE, 1);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setProjectileSize(pCompound.getInt("projectile_size"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("projectile_size", this.getProjectileSize());
    }

    public int getProjectileSize() {
        return this.entityData.get(PROJECTILE_SIZE);
    }

    public void setProjectileSize(int sizeTier) {
        this.entityData.set(PROJECTILE_SIZE, sizeTier);

    }


    /**
     * Entity and block collisions have different sizes of inflated hitboxes (bounding boxes), so the proejctile won't explode early when hitting a block
     *
     * @param pProjectile
     * @param pFilter
     * @return
     */
    public static HitResult getLargerHitResultForEntityCollisions(Entity pProjectile, Predicate<Entity> pFilter) {
        Vec3 vec3 = pProjectile.getDeltaMovement();
        Level level = pProjectile.level;
        Vec3 vec31 = pProjectile.position();
        Vec3 vec32 = vec31.add(vec3);
        HitResult hitresult = level.clip(new ClipContext(vec31, vec32, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, pProjectile));
        if ((hitresult).getType() != HitResult.Type.MISS) {
            vec32 = (hitresult).getLocation();
        }

        HitResult hitresult1 = ProjectileUtil.getEntityHitResult(level, pProjectile, vec31, vec32, pProjectile.getBoundingBox().expandTowards(pProjectile.getDeltaMovement()).inflate(6.0D).expandTowards(1, 3, 1), pFilter);
        if (hitresult1 != null) {
            hitresult = hitresult1;
        }

        return hitresult;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public ADragonBase getOwner() {
        return dragon;
    }

    @Override
    public void tick() {
        // super.tick() causes fireball textures
        this.baseTick();
        ticksExisted++;

        if (this.level.isClientSide || (dragon == null || !dragon.isRemoved())) {
            this.checkInsideBlocks();
            Vec3 vec3 = this.getDeltaMovement();
            double d0 = this.getX() + vec3.x;
            double d1 = this.getY() + vec3.y;
            double d2 = this.getZ() + vec3.z;
            float f = this.getInertia();
            if (this.isInWater()) {
                for (int i = 0; i < 4; ++i) {
                    this.level.addParticle(ParticleTypes.BUBBLE, d0 - vec3.x * 0.25D, d1 - vec3.y * 0.25D, d2 - vec3.z * 0.25D, vec3.x, vec3.y, vec3.z);
                }

                f = 0.8F;

            }
            this.setDeltaMovement(vec3.add(this.xPower, this.yPower, this.zPower).scale(f));
            this.setPos(d0, d1, d2);
        }

        Entity owner = this.getOwner();
        HitResult hitresult = ProjectileUtil.getHitResult(this, this::canHitEntity);
        HitResult largerEntityHitResult = getLargerHitResultForEntityCollisions(this, this::canHitEntity);

        if (!this.level.isClientSide || owner == null || !owner.isRemoved()) {
            if ((hitresult.getType() != HitResult.Type.MISS && hitresult.getType() != HitResult.Type.ENTITY)) {
                end = hitresult.getLocation();
                if (dragon != null) {

                    HitResult.Type hitresult$type = hitresult.getType();
                    if (hitresult$type == HitResult.Type.BLOCK && hitresult instanceof BlockHitResult blockHitResult) {
                        boolean flag = ForgeEventFactory.getMobGriefingEvent(this.level, this.getOwner());
                        if (flag) {
                            this.explode(dragon, this.getX(), this.getY(), this.getZ(), dragon.getExplosionStrength(), flag, flag ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE);
                            callExplosionEffects(flag, dragon);
                        }
                        this.discard();
                        this.gameEvent(GameEvent.PROJECTILE_LAND, this.getOwner());
                    }
                }
            }

            HitResult.Type hitresult$typeEntity = largerEntityHitResult.getType();
            if (hitresult$typeEntity == HitResult.Type.ENTITY && largerEntityHitResult instanceof EntityHitResult entityHitResult) {
                if (largerEntityHitResult.getType() != HitResult.Type.MISS && largerEntityHitResult.getType() != HitResult.Type.BLOCK) {
                    end = largerEntityHitResult.getLocation();

                    Entity entity = entityHitResult.getEntity();

                    // if not a dragon or an instance of a projectile, hit it
                    if (dragon != null && entity != dragon && entity != owner && !(entity instanceof BaseLinearFlightProjectile)) {
               /*        if ((entity instanceof LivingEntity livingEntity && livingEntity.getEffect(MobEffects.FIRE_RESISTANCE) != null)) {
                            return;
                        }

                */

                        boolean mobGriefing = ForgeEventFactory.getMobGriefingEvent(this.level, this.getOwner());
                        float damage = dragon.getProjectileDamage(dragon, entity, this);
                        // nerf damage to players
                        float damage1 = entity instanceof Player || entity instanceof ADragonBaseFlyingRideable || entity instanceof ADragonBaseGroundRideable ? damage / 3 : damage;
                        if (mobGriefing) {
                            entity.hurt(DamageSource.mobAttack(dragon), damage1);
                            if(!(this instanceof ZipBreathProjectile || this instanceof SkrillLightning))
                                entity.setSecondsOnFire(7);
                            this.discard();
                            this.gameEvent(GameEvent.PROJECTILE_LAND, this.getOwner());

                            // add exception if mobGriefing is false,
                            // allow damage only to tamable mobs that aren't tamed
                            // and especially hostile mobs such as bosses to make dragons useful in combat.
                            // no damage to pets (tamed mobs and mobs that have name nametags are considered as pets)
                            // also add exceptions to mobs with large health since players would put nametags to  bosses or dragons to avoid them getting damage
                        } else if (!mobGriefing) {
                            if (entity instanceof LivingEntity livingEntity && livingEntity.getMaxHealth() < 20 && livingEntity.hasCustomName()) {
                                return;
                            }
                            if (entity instanceof TamableAnimal tamableAnimal && tamableAnimal.isTame()) {
                                return;
                            } else {
                                entity.hurt(DamageSource.mobAttack(dragon), damage1);
                                if(!(this instanceof ZipBreathProjectile || this instanceof SkrillLightning))
                                    entity.setSecondsOnFire(7);
                                this.discard();
                                this.gameEvent(GameEvent.PROJECTILE_LAND, this.getOwner());
                            }
                        }
                        boolean flag = ForgeEventFactory.getMobGriefingEvent(this.level, this.getOwner());
                        this.explode(dragon, this.getX(), this.getY(), this.getZ(), dragon.getExplosionStrength(), flag, flag ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE);
                        callExplosionEffects(flag, dragon);
                    }
                }
            }
        }

        playParticles();
//         kill entity when ticks exceed 250 to remove lag
        if (ticksExisted > threshHoldForDeletion()) {
            this.discard();
            ticksExisted = 0;
        }
    }

    /**
     * Some dragons don't need cool explosions unless they reach teh most powerful size
     *
     * @param flag
     */
    protected void callExplosionEffects(boolean flag, ADragonRideableUtility dragon) {
        if (this instanceof BaseLinearBoltProjectile) {
            if (getProjectileSize() == 3 || getProjectileSize() == 2 || getProjectileSize() == 1) {
                level.explode(dragon, this.getX(), this.getY(), this.getZ(), dragon.getExplosionStrength(), flag, flag ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE);
            }

            if (getProjectileSize() == 0) {
                this.level.addParticle(ParticleTypes.EXPLOSION, getX(), getY(), getZ(), 1, 0, 0);
                playSound(SoundEvents.FIREWORK_ROCKET_BLAST_FAR, 4F, 1f);
            }
        }
    }

    /**
     * Similar to setArrowHeading, it's point the throwable entity to a x, y, z direction.
     */
    public void shoot(Vec3 end, float partialTicks, float pInaccuracy, double pVelocity) {
        Vec3 endVec = (new Vec3(end.x() * pVelocity, end.y() * pVelocity, end.z() * pVelocity));

        // plays particles
        // use rotation and endpoint
        Vec3 vec3 = (endVec).normalize().add(this.random.nextGaussian() * 0.007499999832361937D * (double) pInaccuracy, this.random.nextGaussian() * 0.007499999832361937D * (double) pInaccuracy,
                this.random.nextGaussian() * 0.007499999832361937D * (double) pInaccuracy).scale(pVelocity);
        this.setDeltaMovement(vec3);

        double d0 = end.horizontalDistance();
        this.setYRot((float) (Mth.atan2(end.x, end.z) * (double) (180F / (float) Math.PI)));
        this.setXRot((float) (Mth.atan2(end.y, d0) * (double) (180F / (float) Math.PI)));
        this.yRotO = this.getYRot();
        this.xRotO = this.getYRot();
    }

    /**
     * Similar to setArrowHeading, it's point the throwable entity to a x, y, z direction.
     */
    public void shootNoScaling(Vec3 end, float partialTicks, float pInaccuracy) {
        double pVelocity = 2;
        Vec3 endVec = (new Vec3(end.x() * pVelocity, end.y() * pVelocity, end.z() * pVelocity));

        // plays particles
        // use rotation and endpoint
        Vec3 vec3 = (endVec).normalize().add(this.random.nextGaussian() * 0.007499999832361937D * (double) pInaccuracy,
                this.random.nextGaussian() * 0.007499999832361937D * (double) pInaccuracy, this.random.nextGaussian() * 0.007499999832361937D * pInaccuracy).scale(pVelocity);
        this.setDeltaMovement(vec3);

        double d0 = end.horizontalDistance();
        this.setYRot((float) (Mth.atan2(end.x, end.z) * (double) (180F / (float) Math.PI)));
        this.setXRot((float) (Mth.atan2(end.y, d0) * (double) (180F / (float) Math.PI)));
        this.yRotO = this.getYRot();
        this.xRotO = this.getYRot();
    }

    public void shoot(Vec3 end, float partialTicks) {
        shoot(end, partialTicks, 1, 5);
    }

    public void shoot(Vec3 end, float partialTicks, double velocity) {
        shoot(end, partialTicks, 1,  velocity);
    }

    /**
     * Sets how long before the entity is deleted, meaning the range of the projectile
     *
     * @return
     */
    protected int threshHoldForDeletion() {
        return 300;
    }

    protected abstract Explosion explode(ADragonBase dragon, double x, double y, double z, float explosionStrength, boolean flag, Explosion.BlockInteraction blockInteraction);

    public void playParticles() {

        float scale = (float) ticksExisted * 2 + 8;
        float scaleMultiplier;

        switch (getProjectileSize()) {
            default -> scaleMultiplier = 0.3F;
            case 1 -> scaleMultiplier = 0.4F;
            case 2 -> scaleMultiplier = 0.5F;
            case 3 -> scaleMultiplier = 0.6F;
        }

        double posX = this.xo + (this.random.nextDouble() - this.random.nextDouble()) * (ticksExisted / 15);
        double posY = this.yo + (this.random.nextDouble() - this.random.nextDouble()) * (ticksExisted / 15);
        double posZ = this.zo + (this.random.nextDouble() - this.random.nextDouble()) * (ticksExisted / 15);

        ParticleOptions particleOptions = getTrailParticle();
        level.addParticle(particleOptions, true,
                posX,
                posY,
                posZ,
                (scale * scaleMultiplier) + 0.1 * (random.nextFloat() - 0.5f),
                (scale * scaleMultiplier) * 0.1 * (random.nextFloat() - 0.5f),
                (scale * scaleMultiplier) + 0.1 * (random.nextFloat() - 0.5f));

    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    public int getDamageTier() {
        return damageTier;
    }

    public void setDamageTier(int damageTier) {
        this.damageTier = damageTier;
    }
}
