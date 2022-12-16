package com.GACMD.isleofberk.common.entity.entities.dragons.zippleback;

import com.GACMD.isleofberk.common.entity.entities.AI.taming.T4DragonPotionRequirement;
import com.GACMD.isleofberk.common.entity.entities.base.ADragonBase;
import com.GACMD.isleofberk.common.entity.entities.base.ADragonBaseFlyingRideable;
import com.GACMD.isleofberk.common.entity.entities.base.ADragonBaseFlyingRideableBreathUser;
import com.GACMD.isleofberk.common.entity.entities.projectile.breath_user.poison.ZipBreathProjectile;
import com.GACMD.isleofberk.common.entity.entities.projectile.breath_user.poison.ZippleBackAOECloud;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ZippleBack extends ADragonBaseFlyingRideableBreathUser {

    private int ticksUsingSecondAbility;

    public ZippleBack(EntityType<? extends ADragonBaseFlyingRideable> entityType, Level level) {
        super(entityType, level);

    }

    @Override
    protected double rider1YOffSet() {
        return 1.3D;
    }

    @Override
    public float getRideCameraDistanceBack() {
        if (!isFlying()) {
            return 5;
        } else {
            return 12F;
        }
    }

    @Override
    public float getRideCameraDistanceFront() {
        if (isFlying()) {
            return 7;
        } else {
            return 4;
        }
    }

    //  Attributes
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 60.0D)
                .add(Attributes.ARMOR, 10)
                .add(Attributes.MOVEMENT_SPEED, 0.4F)
                .add(Attributes.FLYING_SPEED, 0.14F)
                .add(Attributes.ATTACK_DAMAGE, 15F)
                .add(ForgeMod.STEP_HEIGHT_ADDITION.get(), 1F)
                .add(ForgeMod.SWIM_SPEED.get(), 0.8F);
    }

    public Vec3 getThroatPos(ADragonBase entity) {
        Vec3 bodyOrigin = position();

        float angle = (float) ((float) (Math.PI / 180) * this.yBodyRot + (Math.PI / 180 * 10));
        double x = Math.sin(Math.PI + angle) * 4;
        double y = 3.8D;
        double z = Math.cos(angle) * 4;
        float scale = isBaby() ? 0.2F : 1;
        Vec3 throatPos = bodyOrigin.add(new Vec3(x * scale, y * scale, z * scale));
        return throatPos;

    }

    public Vec3 get2ndHeadThroatPos(ADragonBase entity) {
        Vec3 bodyOrigin = position();

        float angle = (float) ((float) (Math.PI / 180) * this.yBodyRot - (Math.PI / 180 * 10));
        double x = Math.sin(Math.PI + angle) * 4;
        double y = 3.8D;
        double z = Math.cos(angle) * 4;
        float scale = isBaby() ? 0.2F : 1;
        Vec3 throatPos = bodyOrigin.add(new Vec3(x * scale, y * scale, z * scale));
        return throatPos;

    }

//    @Override
//    public boolean isInvulnerableTo(@NotNull DamageSource pSource) {
//        if(pSource == DamageSource.MAGIC)
//        return super.isInvulnerableTo(pSource);
//    }

    @javax.annotation.Nullable
    public <T extends ZippleBackAOECloud> T getNearestGasCloud(List<? extends T> pEntities, TargetingConditions pPredicate, @javax.annotation.Nullable LivingEntity pTarget, double pX, double pY, double pZ) {
        double d0 = -1.0D;
        T t = null;

        for (T t1 : pEntities) {
            if (test(pTarget, t1, pPredicate, 17)) {
                double d1 = t1.distanceToSqr(pX, pY, pZ);
                if (d0 == -1.0D || d1 < d0) {
                    d0 = d1;
                    t = t1;
                }
            }
        }
        return t;
    }

    public boolean test(@javax.annotation.Nullable LivingEntity pAttacker, ZippleBackAOECloud pTarget, TargetingConditions targetCon, int range) {
        if (pAttacker != null) {
            if (range > 0.0D) {
                double d1 = Math.max(range, 2.0D);
                double d2 = pAttacker.distanceToSqr(pTarget.getX(), pTarget.getY(), pTarget.getZ());
                if (d2 > d1 * d1) {
                    return false;
                }
            }

            if (pAttacker instanceof Mob mob) {
                return mob.getSensing().hasLineOfSight(pTarget);
            }
        }
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        Vec3 t = getThroatPos(this);
        Vec3 t1 = get2ndHeadThroatPos(this);

        if (isUsingSECONDAbility()) {
            level.addParticle(ParticleTypes.LAVA, t1.x, t1.y, t1.z, 1, 1, 1);
            ticksUsingSecondAbility++;
        } else {
            ticksUsingSecondAbility=0;
        }

        ZippleBackAOECloud zipCloud = this.getNearestGasCloud(level.getEntitiesOfClass(ZippleBackAOECloud.class, getTargetSearchArea(this.getFollowDistance())),
                TargetingConditions.forNonCombat(), this, this.getX(), this.getEyeY(), this.getZ());
        if (zipCloud != null) {
            if (ticksUsingSecondAbility > 8) {
                zipCloud.hurt(DamageSource.IN_FIRE, 1);
            }
        }

        System.out.println();
        if (this.getEffect(MobEffects.POISON) != null) {
            this.removeEffect(MobEffects.POISON);
        }
    }

    @Override
    public void firePrimary(Vec3 riderLook, Vec3 throat) {
        ZipBreathProjectile fireProj = new ZipBreathProjectile(this, throat, riderLook, level);
        fireProj.shoot(riderLook, 1F, 7F);
        level.addFreshEntity(fireProj);
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType
            pReason, @javax.annotation.Nullable SpawnGroupData pSpawnData, @javax.annotation.Nullable CompoundTag pDataTag) {
        pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        this.setDragonVariant(this.random.nextInt(getMaxAmountOfVariants()));
        return pSpawnData;
    }

    @Override
    public int getMaxFuel() {
        return 85;
    }

    @Override
    public int getMaxAmountOfVariants() {
        return 8;
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
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(1, new T4DragonPotionRequirement(this, 1));
    }
}
