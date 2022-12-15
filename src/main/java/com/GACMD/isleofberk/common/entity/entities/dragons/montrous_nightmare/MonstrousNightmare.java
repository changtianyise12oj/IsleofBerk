package com.GACMD.isleofberk.common.entity.entities.dragons.montrous_nightmare;

import com.GACMD.isleofberk.common.entity.entities.AI.taming.T4DragonPotionRequirement;
import com.GACMD.isleofberk.common.entity.entities.base.ADragonBase;
import com.GACMD.isleofberk.common.entity.entities.base.ADragonBaseFlyingRideable;
import com.GACMD.isleofberk.common.entity.entities.base.ADragonBaseFlyingRideableBreathUser;
import com.GACMD.isleofberk.common.entity.entities.projectile.abase.BaseLinearFlightProjectile;
import com.GACMD.isleofberk.common.entity.entities.projectile.breath_user.firebreaths.FireBreathProjectile;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.Nullable;

public class MonstrousNightmare extends ADragonBaseFlyingRideableBreathUser {

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
    public int getMaxAmountOfVariants() {
        return 10;
    }

    @Override
    public float getRideCameraDistanceBack() {
        if(isFlying()) {
            return 15;
        } else {
            return 6;
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

    //  Attributes
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 200.0D)
                .add(Attributes.ARMOR, 10)
                .add(Attributes.MOVEMENT_SPEED, 0.4F)
                .add(Attributes.FLYING_SPEED, 0.14F)
                .add(Attributes.ATTACK_DAMAGE, 15F)
                .add(ForgeMod.STEP_HEIGHT_ADDITION.get(), 1F)
                .add(ForgeMod.SWIM_SPEED.get(), 0.8F);
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
}
