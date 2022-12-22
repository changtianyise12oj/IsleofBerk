package com.GACMD.isleofberk.entity.dragons.zippleback;

import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import com.GACMD.isleofberk.entity.AI.taming.T4DragonPotionRequirement;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideable;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideableBreathUser;
import com.GACMD.isleofberk.entity.eggs.entity.eggs.ZippleBackEgg;
import com.GACMD.isleofberk.entity.eggs.entity.base.ADragonEggBase;
import com.GACMD.isleofberk.entity.projectile.breath_user.poison.ZipBreathProjectile;
import com.GACMD.isleofberk.entity.projectile.breath_user.poison.ZippleBackAOECloud;
import com.GACMD.isleofberk.registery.ModEntities;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
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
import org.jetbrains.annotations.NotNull;
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
                .add(Attributes.MAX_HEALTH, 160.0D)
                .add(Attributes.ARMOR, 2)
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

        if (isUsingSECONDAbility()) {
            modifySecondaryFuel(-1);
        }

        ZippleBackAOECloud zipCloud = this.getNearestGasCloud(level.getEntitiesOfClass(ZippleBackAOECloud.class, getTargetSearchArea(this.getFollowDistance())),
                TargetingConditions.forNonCombat(), this, this.getX(), this.getEyeY(), this.getZ());
        if (zipCloud != null) {
            if (ticksUsingSecondAbility > 2) {
                zipCloud.hurt(DamageSource.IN_FIRE, 1);
            }
        }

        if (this.getEffect(MobEffects.POISON) != null) {
            this.removeEffect(MobEffects.POISON);
        }

        String s = ChatFormatting.stripFormatting(this.getName().getString());
        if (s != null) {
            if (s.equals("Barf and Belch") || s.equals("barf and belch")|| s.equals("Barf & Belch")|| s.equals("barf & belch")|| s.equals("Barf && Belch")|| s.equals("barf && belch")) {
                this.setDragonVariant(0);
            }
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
        return 120;
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

    @Override
    public @Nullable ADragonEggBase getBreedEggResult(ServerLevel level, @NotNull AgeableMob parent) {
        ZippleBackEgg dragon = ModEntities.ZIPPLEBACK_EGG.get().create(level);
        return dragon;
    }
}
