package com.GACMD.isleofberk.entity.dragons.montrous_nightmare;

import com.GACMD.isleofberk.entity.AI.taming.T4DragonPotionRequirement;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideable;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideableBreathUser;
import com.GACMD.isleofberk.entity.eggs.entity.base.ADragonEggBase;
import com.GACMD.isleofberk.entity.eggs.entity.eggs.MonstrousNightmareEgg;
import com.GACMD.isleofberk.entity.projectile.abase.BaseLinearFlightProjectile;
import com.GACMD.isleofberk.entity.projectile.breath_user.firebreaths.FireBreathProjectile;
import com.GACMD.isleofberk.registery.ModEntities;
import com.GACMD.isleofberk.util.Util;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MonstrousNightmare extends ADragonBaseFlyingRideableBreathUser {
    private static final EntityDataAccessor<Boolean> IS_ON_FIRE_ABILITY = SynchedEntityData.defineId(MonstrousNightmare.class, EntityDataSerializers.BOOLEAN);

    private int ticksUsingSecondAbility;
    private int ticksUsingActiveSecondAbility;

    public MonstrousNightmare(EntityType<? extends ADragonBaseFlyingRideable> entityType, Level level) {
        super(entityType, level);
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @javax.annotation.Nullable SpawnGroupData pSpawnData, @javax.annotation.Nullable CompoundTag pDataTag) {
        pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        this.setDragonVariant(this.random.nextInt(getMaxAmountOfVariants()));
        return pSpawnData;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_ON_FIRE_ABILITY, false);
    }

    public boolean isOnFireAbility() {
        return this.entityData.get(IS_ON_FIRE_ABILITY);
    }

    public void setOnFireAbility(boolean fire) {
        this.entityData.set(IS_ON_FIRE_ABILITY, fire);
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("is_on_fire_ability", this.isOnFireAbility());
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setOnFireAbility(pCompound.getBoolean("is_on_fire_ability"));
    }


    @Override
    public int getMaxAmountOfVariants() {
        return 10;
    }

    @Override
    public float getRideCameraDistanceBack() {
        if (isFlying()) {
            return 15;
        } else {
            return 6;
        }
    }

    @Override
    public float getRideCameraDistanceFront() {
        if (isFlying()) {
            return 9;
        } else {
            return 7;
        }
    }

    @Override
    protected double rider1YOffSet() {
        return super.rider1YOffSet();
    }

    public Vec3 getThroatPos(ADragonBase entity) {
        Vec3 bodyOrigin = position();

        double x = -Math.sin(this.getYRot() * Math.PI / 180) * 3.2;
        double y = 1.5;
        double z = Math.cos(this.getYRot() * Math.PI / 180) * 3.2;
        float scale = isBaby() ? 0.2F : 1;
        Vec3 throatPos = bodyOrigin.add(new Vec3(x * scale, y * scale, z * scale));
        return throatPos;

    }

    @Override
    public void tick() {
        super.tick();
        boolean hasDamageResist = getEffect(MobEffects.DAMAGE_RESISTANCE) != null;

        if (isUsingSECONDAbility()) {
            if (!hasDamageResist && !isInWater()) {
                ticksUsingSecondAbility++;
                ticksUsingActiveSecondAbility = 0;
            } else {
                ticksUsingActiveSecondAbility++;
                ticksUsingSecondAbility = 0;
            }
        }

        if (ticksUsingSecondAbility > 40) {
            this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, Util.minutesToSeconds(1)));
        }

        if (isOnFireAbility()) {
            Vec3 t = getLWingPos(this);
            Vec3 t1 = getRWingPos(this);
            for (double j = 0; j < 5; j++) {
                ParticleOptions particleOptions = ParticleTypes.LAVA;
                level.addParticle(particleOptions, true,
                        t.x, t.y, t.z,
                        0.1525f * (random.nextFloat() - 0.5f),
                        0.1525f * (random.nextFloat() - 0.5f),
                        0.1525f * (random.nextFloat() - 0.5f));
                level.addParticle(particleOptions, true,
                        t1.x, t1.y, t1.z,
                        0.1525f * (random.nextFloat() - 0.5f),
                        0.1525f * (random.nextFloat() - 0.5f),
                        0.1525f * (random.nextFloat() - 0.5f));
            }
        }

        if (hasDamageResist) {
            this.setOnFireAbility(true);
            this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 5));
        }

        if (!hasDamageResist) {
            this.setOnFireAbility(false);
        }

        if (isInWater() || ticksUsingActiveSecondAbility > 40) {
            if (hasDamageResist) {
                this.removeEffect(MobEffects.DAMAGE_RESISTANCE);
                ticksUsingSecondAbility = 0;
            }
        }

        String s = ChatFormatting.stripFormatting(this.getName().getString());
        if (s != null) {
            if (s.equals("Hookfang") || s.equals("hookfang")) {
                this.setDragonVariant(0);
            }
        }

    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        LivingEntity attacker = getLastHurtByMob();
        if (attacker != null) {
            attacker.setSecondsOnFire(3);
            attacker.hurt(DamageSource.indirectMobAttack(this, attacker), 6);
        }
        if (random.nextInt(24) == 1 && !isInWater()) {
            if (getControllingPassenger() == null) {
                this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, Util.minutesToSeconds(2)));
            }
        }
        return super.hurt(pSource, pAmount);
    }

    public Vec3 getLWingPos(ADragonBase entity) {
        Vec3 bodyOrigin = position();
        float angle = (float) ((float) (Math.PI / 180) * this.yBodyRot + (Math.PI / 180 * 95));
        float angle1 = (float) ((float) (Math.PI / 180) * this.yBodyRot + (Math.PI / 180 * 95));
        double x = -Math.sin(Math.PI + angle) * 4;
        double y = 2.4D;
        double z = Math.cos(Math.PI + angle1) * 4;
        float scale = isBaby() ? 0.2F : 1;
        Vec3 throatPos = bodyOrigin.add(new Vec3(x * scale, y * scale, z * scale));
//        return circleVec(bodyOrigin, 5, 1, true, tickCount, 1, 1);

        return throatPos;

    }


    public Vec3 circleVec(Vec3 target, float radius, float speed, boolean direction, int circleFrame, float offset, float moveSpeedMultiplier) {
        int directionInt = direction ? 1 : -1;
        double t = directionInt * circleFrame * 0.5 * speed / radius + offset;
        return target.add(radius * Math.cos(t), 0, radius * Math.sin(t));

    }

    public Vec3 getRWingPos(ADragonBase entity) {
        Vec3 bodyOrigin = position();

        float angle = (float) ((float) (Math.PI / 180) * this.yBodyRot - (Math.PI / 180 * 95));
        float angle1 = (float) ((float) (Math.PI / 180) * this.yBodyRot - (Math.PI / 180 * 95));
        double x = -Math.sin(Math.PI + angle) * 4;
        double y = 2.4D;
        double z = Math.cos(Math.PI + angle1) * 4;
        float scale = isBaby() ? 0.2F : 1;
        Vec3 throatPos = bodyOrigin.add(new Vec3(x * scale, y * scale, z * scale));
//        return circleVec(bodyOrigin, 5, 1, false, tickCount, 1, 1);

        return throatPos;
    }

    @Override
    public void positionRider(Entity pPassenger) {
        if (isOnFireAbility() && (pPassenger instanceof LivingEntity livingEntity && livingEntity.getEffect(MobEffects.FIRE_RESISTANCE) == null)) {
            pPassenger.setSecondsOnFire(4);
        }
        super.positionRider(pPassenger);
    }

    //  Attributes
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 200.0D)
                .add(Attributes.ARMOR, 10)
                .add(Attributes.MOVEMENT_SPEED, 0.4F)
                .add(Attributes.FLYING_SPEED, 0.14F)
                .add(Attributes.ATTACK_DAMAGE, 15F)
                .add(ForgeMod.STEP_HEIGHT_ADDITION.get(), 1F);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(1, new T4DragonPotionRequirement(this, 1));
    }

    @Override
    public void firePrimary(Vec3 riderLook, Vec3 throat) {
        FireBreathProjectile fireProj = new FireBreathProjectile(this, throat, riderLook, level);
        fireProj.setProjectileSize(2);
        fireProj.shoot(riderLook, 1F, 7F);
        level.addFreshEntity(fireProj);
    }

    @Override
    public int getExplosionStrength() {
        return 4;
    }

    @Override
    public float getProjectileDamage(ADragonBase dragon, Entity entity, BaseLinearFlightProjectile projectile) {
        return 13;
    }

    @Override
    protected int breathBarRegenSpeed() {
        return 75;
    }

    @Override
    protected int breathBarRegenAmount() {
        return 1;
    }


    @Override
    public int getMaxFuel() {
        return 275;
    }

    @Override
    public @Nullable ADragonEggBase getBreedEggResult(ServerLevel level, @NotNull AgeableMob parent) {
        MonstrousNightmareEgg dragon = ModEntities.M_NIGHTMARE_EGG.get().create(level);
        return dragon;
    }
}
