package com.GACMD.isleofberk.entity.dragons.lightfury;

import com.GACMD.isleofberk.entity.AI.taming.T4DragonPotionRequirement;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import com.GACMD.isleofberk.entity.dragons.nightfury.NightFury;
import com.GACMD.isleofberk.entity.eggs.entity.base.ADragonEggBase;
import com.GACMD.isleofberk.entity.eggs.entity.eggs.LightFuryEgg;
import com.GACMD.isleofberk.entity.projectile.abase.BaseLinearFlightProjectile;
import com.GACMD.isleofberk.entity.projectile.proj_user.furybolt.FuryBolt;
import com.GACMD.isleofberk.registery.ModEntities;
import com.GACMD.isleofberk.util.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LightFury extends NightFury {

    private int ticksUsingSecondAbility;
    private int ticksSecondAbilityRecharge;

    public LightFury(EntityType<? extends LightFury> entityType, Level level) {
        super(entityType, level);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, @NotNull AgeableMob parent) {
        LightFury dragon = ModEntities.LIGHT_FURY.get().create(level);
        return dragon;
    }


    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @javax.annotation.Nullable SpawnGroupData pSpawnData, @javax.annotation.Nullable CompoundTag pDataTag) {
        pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        if (random.nextInt(20) == 1) {
            this.setDragonVariant(5);
        } else {
            this.setDragonVariant(this.random.nextInt(getMaxAmountOfVariants()));
        }
        this.setGlowVariant(getMaxAmountOfGlowVariants());
        return pSpawnData;
    }

    @Override
    public int getMaxAmountOfVariants() {
        return 5;
    }

    //  Attributes
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 60.0D)
                .add(Attributes.ARMOR, 0.5)
                .add(Attributes.MOVEMENT_SPEED, 0.4F)
                .add(Attributes.FLYING_SPEED, 0.18F)
                .add(Attributes.ATTACK_DAMAGE, 12F)
                .add(ForgeMod.STEP_HEIGHT_ADDITION.get(), 1F);
    }

    @Override
    protected double rider1YOffSet() {
        return 1.1D;
    }

    @Override
    public float getRideCameraDistanceBack() {
        if (!isFlying()) {
            return 4;
        } else {
            return 9F;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (isUsingSECONDAbility() && ticksSecondAbilityRecharge == 0) {
            ticksUsingSecondAbility++;
        }

        if (ticksSecondAbilityRecharge > 0) {
            ticksSecondAbilityRecharge--;
        }

        if (ticksUsingSecondAbility > 40 && !isUsingSECONDAbility() && this.getEffect(MobEffects.INVISIBILITY) == null && ticksSecondAbilityRecharge < 2) {
            ticksSecondAbilityRecharge = Util.secondsToTicks(14);
            ticksUsingSecondAbility = 0;
            this.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, Util.secondsToTicks(10)));
        }

        if (this.getEffect(MobEffects.INVISIBILITY) != null) {
            if (getFirstPassenger() instanceof LivingEntity livingEntity) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 5));
            }

            if (getPassengers().size() > 1 && this.getPassengers().get(1) instanceof LivingEntity livingEntity) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 5));
            }
        }
    }

    @Override
    protected void playerFireProjectile(Vec3 riderLook, Vec3 throat) {
        if ((tier1() || tier2() || tier3() || tier4()) && !isUsingAbility()) {
            setTicksSinceLastFire(12);
            FuryBolt bolt = new FuryBolt(this, throat, riderLook, level, getExplosionStrength());
            bolt.setProjectileSize(getProjsSize());
            bolt.shoot(riderLook, 1F);
            bolt.setIsLightFuryTexture(true);
            level.addFreshEntity(bolt);
            setPlayerBoltBlastPendingScale(0);
            setPlayerBoltBlastPendingStopThreshold(0);
        }
    }

    public void dragonShootProjectile(Vec3 dragonLook, Vec3 throat) {
        if ((tier1() || tier2() || tier3() || tier4()) && !isUsingAbility()) {
            setTicksSinceLastFire(ticksSinceLastProjShootSet());
            FuryBolt bolt = new FuryBolt(this, throat, dragonLook, level, getExplosionStrength());
            bolt.shoot(dragonLook, 1F);
            bolt.setProjectileSize(getProjsSize());
            level.addFreshEntity(bolt);
            bolt.setIsLightFuryTexture(true);
            setPlayerBoltBlastPendingScale(0);
            setPlayerBoltBlastPendingStopThreshold(0);
        }
    }

    @javax.annotation.Nullable
    public Entity getSecondPassenger() {
        if (this.getPassengers().size() < 3) {
            return this.getPassengers().get(1);
        }
        return null;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(1, new T4DragonPotionRequirement(this, 1));
    }

    /**
     * Add bonus damage to boss mobs with high health
     *
     * @param dragon
     * @param entity
     * @return
     */
    @Override
    public float getProjectileDamage(ADragonBase dragon, Entity entity, BaseLinearFlightProjectile projectile) {
        if (projectile.getDamageTier() == 1) {
            return 20F + (entity instanceof LivingEntity livingEntity ? (float) Math.floor(livingEntity.getMaxHealth() * 0.10F) : 0F);
        } else if (projectile.getDamageTier() == 2) {
            return 25F + (entity instanceof LivingEntity livingEntity ? (float) Math.floor(livingEntity.getMaxHealth() * 0.12F) : 0F);
        } else if (projectile.getDamageTier() == 3) {
            return 30F + (entity instanceof LivingEntity livingEntity ? (float) Math.floor(livingEntity.getMaxHealth() * 0.20F) : 0F);
        } else if (projectile.getDamageTier() == 4) {
            return 38F + (entity instanceof LivingEntity livingEntity ? (float) Math.floor(livingEntity.getMaxHealth() * 0.30F) : 0F);
        }

        return 20;
    }

    @Override
    public @Nullable ADragonEggBase getBreedEggResult(ServerLevel level, @NotNull AgeableMob parent) {
        LightFuryEgg dragon = ModEntities.LIGHT_FURY_EGG.get().create(level);
        return dragon;
    }


}
