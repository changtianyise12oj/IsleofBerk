package com.GACMD.isleofberk.entity.dragons.lightfury;

import com.GACMD.isleofberk.entity.AI.taming.T4DragonPotionRequirement;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import com.GACMD.isleofberk.entity.dragons.nightfury.NightFury;
import com.GACMD.isleofberk.entity.eggs.entity.base.ADragonEggBase;
import com.GACMD.isleofberk.entity.eggs.entity.eggs.LightFuryEgg;
import com.GACMD.isleofberk.entity.projectile.abase.BaseLinearFlightProjectile;
import com.GACMD.isleofberk.entity.projectile.proj_user.furybolt.FuryBolt;
import com.GACMD.isleofberk.registery.ModEntities;
import com.GACMD.isleofberk.registery.ModSounds;
import com.GACMD.isleofberk.util.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

public class LightFury extends NightFury {

    private int ticksUsingSecondAbility;
    private int ticksSecondAbilityRecharge;

    /**
     * 0 is default - value is up and positive value is down
     *
     * @param event
     * @param <E>
     * @return
     */
    private <E extends IAnimatable> PlayState basicMovementController(AnimationEvent<E> event) {
        if ((isFlying() && !event.isMoving())) {
            // the head looks down during hover which looks awful and distorted so temporary disabled
            event.getController().setAnimation(new AnimationBuilder().addAnimation("lightfury.flap", ILoopType.EDefaultLoopTypes.LOOP)); // hover
            setShouldPlayFlapping(true);
            return PlayState.CONTINUE;
        }
        if (isFlying()) {
            if (event.isMoving()) {
                if (getControllingPassenger() instanceof Player) {
//                if (this.getXRot() < 11 || isGoingUp() || getPassengers().size() > 2 || getFirstPassenger() == null) {
                    if (this.getXRot() < 11 || isGoingUp() || getPassengers().size() > 2) {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("lightfury.flap", ILoopType.EDefaultLoopTypes.LOOP)); //flyup
                        setShouldPlayFlapping(true);
                        return PlayState.CONTINUE;
                    }
                    if (this.getXRot() >= 11 && this.getXRot() < 26 && !isGoingUp()) { // < 20
                        setShouldPlayFlapping(false);
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("lightfury.glide", ILoopType.EDefaultLoopTypes.LOOP)); // glide
                        return PlayState.CONTINUE;
                    }
                    if (this.getXRot() >= 26 && !isGoingUp()) { // > 30
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("lightfury.dive", ILoopType.EDefaultLoopTypes.LOOP)); // dive
                        setShouldPlayFlapping(false);
                        return PlayState.CONTINUE;
                    }
                    // different values for pitch and roll when following elytra flying player
                } else if (getOwner() instanceof Player player && isDragonFollowing() && player.isFallFlying()) {
                    float dist = distanceTo(player);
                    double ydist = this.getY() - player.getY();
                    if (dist > 8.3F && ydist < 4 || ydist < -1.8F) {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("lightfury.flap", ILoopType.EDefaultLoopTypes.LOOP)); //flyup
                        setShouldPlayFlapping(true);
                        return PlayState.CONTINUE;
                    }
                    if (dist < 8.3F || ydist > 4) {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("lightfury.dive", ILoopType.EDefaultLoopTypes.LOOP)); //flyup
                        setShouldPlayFlapping(false);
                        return PlayState.CONTINUE;
                    } else {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("lightfury.flap", ILoopType.EDefaultLoopTypes.LOOP)); //flyup
                        setShouldPlayFlapping(true);
                        return PlayState.CONTINUE;
                    }
                } else {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("lightfury.flap", ILoopType.EDefaultLoopTypes.LOOP)); //flyup DeadlyNadderFlyup
                    setShouldPlayFlapping(true);
                    return PlayState.CONTINUE;
                }

            }
        } else {
            if (this.isDragonSitting() && !isDragonSleeping()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("lightfury.sit", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
            if (event.isMoving() && !shouldStopMovingIndependently()) {
                if (getTarget() != null && !getTarget().isDeadOrDying() && distanceTo(getTarget()) < 14 || (isVehicle())) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("lightfury.run", ILoopType.EDefaultLoopTypes.LOOP));
                    return PlayState.CONTINUE;

                } else {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("lightfury.walk", ILoopType.EDefaultLoopTypes.LOOP));
                    return PlayState.CONTINUE;
                }
            }
            if (this.isDragonSleeping()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("lightfury.sleep", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }


            event.getController().setAnimation(new AnimationBuilder().addAnimation("lightfury.idle", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState attackController(AnimationEvent<E> event) {
        if (getTicksSinceLastAttack() >= 0 && getTicksSinceLastAttack() < 12) {
            if (getCurrentAttackType() == 0) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("lightfury.bite", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
        }
        if (isMarkFired()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("lightfury.breath", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    private <E extends IAnimatable> PlayState turnController(AnimationEvent<E> event) {
        int turnState = this.getRotationState();
        if (turnState != 0 && getControllingPassenger() instanceof Player) {
            if (isFlying()) {
                boolean diving = getXRot() >= 32 && event.isMoving();
                if (isGoingUp() || diving) {
                    event.getController().setAnimationSpeed(4);
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("lightfury.tailrot0", ILoopType.EDefaultLoopTypes.LOOP));
                    return PlayState.CONTINUE;
                } else {
                    if (turnState == 1) {
                        event.getController().setAnimationSpeed(4);
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("lightfury.tailrotleft1f", ILoopType.EDefaultLoopTypes.LOOP));
                        return PlayState.CONTINUE;
                    } else if (turnState == 2) {
                        event.getController().setAnimationSpeed(4);
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("lightfury.tailrotleft2f", ILoopType.EDefaultLoopTypes.LOOP));
                        return PlayState.CONTINUE;
                    } else if (turnState == -1) {
                        event.getController().setAnimationSpeed(4);
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("lightfury.tailrotright1f", ILoopType.EDefaultLoopTypes.LOOP));
                        return PlayState.CONTINUE;
                    } else if (turnState == -2) {
                        event.getController().setAnimationSpeed(4);
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("lightfury.tailrotright2f", ILoopType.EDefaultLoopTypes.LOOP));
                        return PlayState.CONTINUE;
                    }
                }
            } else {
                if (turnState == 1) {
                    event.getController().setAnimationSpeed(4);
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("lightfury.tailrotleft1", ILoopType.EDefaultLoopTypes.LOOP));
                    return PlayState.CONTINUE;
                } else if (turnState == 2) {
                    event.getController().setAnimationSpeed(4);
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("lightfury.tailrotleft2", ILoopType.EDefaultLoopTypes.LOOP));
                    return PlayState.CONTINUE;
                } else if (turnState == -1) {
                    event.getController().setAnimationSpeed(4);
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("lightfury.tailrotright1", ILoopType.EDefaultLoopTypes.LOOP));
                    return PlayState.CONTINUE;
                } else if (turnState == -2) {
                    event.getController().setAnimationSpeed(4);
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("lightfury.tailrotright2", ILoopType.EDefaultLoopTypes.LOOP));
                    return PlayState.CONTINUE;
                }
            }
        } else {
            event.getController().setAnimationSpeed(4);
            event.getController().setAnimation(new AnimationBuilder().addAnimation("lightfury.tailrot0", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;

    }

    // Animation
    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<LightFury>(this, "basic_MovementController", 4, this::basicMovementController));
        data.addAnimationController(new AnimationController<LightFury>(this, "attack_Controller", 0, this::attackController));
        data.addAnimationController(new AnimationController<LightFury>(this, "turnController", 35, this::turnController));
    }

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
                .add(Attributes.MAX_HEALTH, 120.0D)
                .add(Attributes.ARMOR, 0.5)
                .add(Attributes.ATTACK_DAMAGE, 20F)
                .add(Attributes.MOVEMENT_SPEED, 0.4F)
                .add(Attributes.FLYING_SPEED, 0.20F)
                .add(Attributes.ATTACK_DAMAGE, 50F)
                .add(ForgeMod.SWIM_SPEED.get(), 0.8F);
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
            if (tier4() || tier3()) {
                playProjectileSound();
            }

            if (tier1() || tier2()) {
                playSound(SoundEvents.LAVA_POP, 18, 0.05F);
            }
            level.addFreshEntity(bolt);
            setPlayerBoltBlastPendingScale(0);
            setPlayerBoltBlastPendingStopThreshold(0);
        }
    }

    public void dragonShootProjectile(Vec3 dragonLook, Vec3 throat) {
        if ((tier1() || tier2() || tier3() || tier4()) && !isUsingAbility()) {
            setTicksSinceLastFire(ticksSinceLastProjShootSet());
            FuryBolt bolt = new FuryBolt(this, throat, dragonLook, level, getExplosionStrength());
            bolt.shoot(dragonLook, 1F, 5);
            bolt.setProjectileSize(getProjsSize());
            if (tier4() || tier3()) {
                playProjectileSound();
            }

            if (tier1() || tier2()) {
                playSound(SoundEvents.LAVA_POP, 18, 0.05F);
            }
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
            return 22F + (entity instanceof LivingEntity livingEntity ? (float) Math.floor(livingEntity.getMaxHealth() * 0.10F) : 0F);
        } else if (projectile.getDamageTier() == 3) {
            return 23F + (entity instanceof LivingEntity livingEntity ? (float) Math.floor(livingEntity.getMaxHealth() * 0.11F) : 0F);
        } else if (projectile.getDamageTier() == 4) {
            return 24F + (entity instanceof LivingEntity livingEntity ? (float) Math.floor(livingEntity.getMaxHealth() * 0.12F) : 0F);
        }

        return 20;
    }

    @Override
    public @Nullable ADragonEggBase getBreedEggResult(ServerLevel level, @NotNull AgeableMob parent) {
        LightFuryEgg dragon = ModEntities.LIGHT_FURY_EGG.get().create(level);
        return dragon;
    }

    protected SoundEvent getAmbientSound() {
        if (this.isDragonSleeping()) {
            return ModSounds.LIGHT_FURY_SLEEP.get();
        } else {
            return ModSounds.LIGHT_FURY_GROWL.get();
        }
    }

    protected SoundEvent getTameSound() {
        return ModSounds.LIGHT_FURY_TAME.get();
    }

    protected SoundEvent getProjectileSound() {
        return ModSounds.LIGHT_FURY_FIRE.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return ModSounds.LIGHT_FURY_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return ModSounds.LIGHT_FURY_DEATH.get();
    }

    @Override
    protected SoundEvent get1stAttackSound() {
        return ModSounds.LIGHT_FURY_BITE.get();
    }
}
