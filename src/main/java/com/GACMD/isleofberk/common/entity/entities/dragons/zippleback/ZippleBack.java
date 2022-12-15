package com.GACMD.isleofberk.common.entity.entities.dragons.zippleback;

import com.GACMD.isleofberk.common.entity.entities.AI.taming.T4DragonPotionRequirement;
import com.GACMD.isleofberk.common.entity.entities.base.ADragonBaseFlyingRideable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.Nullable;

public class ZippleBack extends ADragonBaseFlyingRideable {

    public ZippleBack(EntityType<? extends ADragonBaseFlyingRideable> entityType, Level level) {
        super(entityType, level);

    }

    @Override
    protected double rider1YOffSet() {
        return 1.3D;
    }

    @Override
    public float getRideCameraDistanceBack() {
        if(!isFlying()) {
            return 5;
        } else {
            return 12F;
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

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @javax.annotation.Nullable SpawnGroupData pSpawnData, @javax.annotation.Nullable CompoundTag pDataTag) {
        pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        this.setDragonVariant(this.random.nextInt(getMaxAmountOfVariants()));
        return pSpawnData;
    }

    @Override
    public int getMaxAmountOfVariants() {
        return 8;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(1, new T4DragonPotionRequirement(this, 1));
    }
}
