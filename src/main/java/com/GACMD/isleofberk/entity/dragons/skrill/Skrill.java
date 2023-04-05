package com.GACMD.isleofberk.entity.dragons.skrill;

import com.GACMD.isleofberk.entity.AI.taming.T3DragonWeakenAndFeedTamingGoal;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideable;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideableBreathUser;
import com.GACMD.isleofberk.entity.dragons.montrous_nightmare.MonstrousNightmare;
import com.GACMD.isleofberk.entity.eggs.entity.base.ADragonEggBase;
import com.GACMD.isleofberk.entity.eggs.entity.eggs.SkrillEgg;
import com.GACMD.isleofberk.entity.projectile.abase.BaseLinearFlightProjectile;
import com.GACMD.isleofberk.entity.projectile.breath_user.firebreaths.FireBreathProjectile;
import com.GACMD.isleofberk.registery.ModEntities;
import com.GACMD.isleofberk.registery.ModParticles;
import com.GACMD.isleofberk.registery.ModSounds;
import com.GACMD.isleofberk.util.Util;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
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

import static com.GACMD.isleofberk.registery.ModTags.Items.*;

public class Skrill extends ADragonBaseFlyingRideableBreathUser {    private static final EntityDataAccessor<Boolean> IS_ON_FIRE_ABILITY = SynchedEntityData.defineId(MonstrousNightmare.class, EntityDataSerializers.BOOLEAN);

    private int ticksUsingSecondAbility;
    private int ticksUsingActiveSecondAbility;

    public Skrill(EntityType<? extends ADragonBaseFlyingRideable> entityType, Level level) {
        super(entityType, level);
    }


    private <E extends IAnimatable> PlayState basicMovementController(AnimationEvent<E> event) {

        // flying animations
        if (isFlying()) {
            if (event.isMoving()) {

                // mounted flying
                if (this.isVehicle()) {
                    if (this.getXRot() < 8 || isGoingUp() || getPassengers().size() > 2) {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("Skrill.Fly", ILoopType.EDefaultLoopTypes.LOOP));
                        setShouldPlayFlapping(true);
                    }
                    if (this.getXRot() >= 8 && this.getXRot() < 33 && !isGoingUp()) {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("Skrill.Glide", ILoopType.EDefaultLoopTypes.LOOP));
                        setShouldPlayFlapping(false);
                    }
                    if (this.getXRot() >= 33 && !isGoingUp()) {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("Skrill.dive", ILoopType.EDefaultLoopTypes.LOOP));
                        setShouldPlayFlapping(false);
                    }
                    return PlayState.CONTINUE;
                }
                // follow player on elytra
                if (isDragonFollowing() && getOwner() != null && getOwner().isFallFlying()) {
                    LivingEntity owner = getOwner();
                    float dist = distanceTo(owner);
                    double ydist = this.getY() - owner.getY();

                    if (ydist < 10 || dist > 15) {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("Skrill.Fly", ILoopType.EDefaultLoopTypes.LOOP));
                    } else {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("Skrill.dive", ILoopType.EDefaultLoopTypes.LOOP));
                    }
                }
                // free flying
                else {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("Skrill.Fly", ILoopType.EDefaultLoopTypes.LOOP));
                    setShouldPlayFlapping(true);
                }
            } else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("Skrill.Hover", ILoopType.EDefaultLoopTypes.LOOP));
                setShouldPlayFlapping(true);
            }

            //ground animations
        } else {
            if (this.isDragonSitting() && !this.isDragonSleeping()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("Skrill.Sit", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
            if (this.isDragonSleeping()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("Skrill.Sleep", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
            if (event.isMoving() && !shouldStopMovingIndependently()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("Skrill.Walk", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
            if (isDragonIncapacitated()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("Skrill.Surrender", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            } else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("Skrill.Idle", ILoopType.EDefaultLoopTypes.LOOP));
            }
        }
        return PlayState.CONTINUE;
    }

    //     Attack animations
    private <E extends IAnimatable> PlayState attackController(AnimationEvent<E> event) {
        if (getTicksSinceLastAttack() >= 0 && getTicksSinceLastAttack() < 12 && getCurrentAttackType() == 0) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("Skrill.Bite", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        if (isUsingAbility()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("Skrill.Breath"));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    // Dragon turn animations
    private <E extends IAnimatable> PlayState turnController(AnimationEvent<E> event) {
        int turnState = this.getRotationState();
        event.getController().setAnimationSpeed(4);
        if (turnState != 0) {
            if (isFlying()) {
                if (turnState == 1) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("Skrill.rotLeft1F", ILoopType.EDefaultLoopTypes.LOOP));
                } else if (turnState == 2) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("Skrill.rotLeft2F", ILoopType.EDefaultLoopTypes.LOOP));
                } else if (turnState == -1) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("Skrill.rotRight1F", ILoopType.EDefaultLoopTypes.LOOP));
                } else if (turnState == -2) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("Skrill.rotRight2F", ILoopType.EDefaultLoopTypes.LOOP));
                }
            } else {
                if (turnState == 1) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("Skrill.rotLeft1", ILoopType.EDefaultLoopTypes.LOOP));
                } else if (turnState == 2) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("Skrill.rotLeft2", ILoopType.EDefaultLoopTypes.LOOP));
                } else if (turnState == -1) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("Skrill.rotRight1", ILoopType.EDefaultLoopTypes.LOOP));
                } else if (turnState == -2) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("Skrill.rotRight2", ILoopType.EDefaultLoopTypes.LOOP));
                }
            }
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("Skrill.rot0", ILoopType.EDefaultLoopTypes.LOOP));
        }
        return PlayState.CONTINUE;
    }

    // Animation
    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<Skrill>(this, "basic_MovementController", getTransitionTicks(), this::basicMovementController));
        data.addAnimationController(new AnimationController<Skrill>(this, "attack_Controller", 0, this::attackController));
        data.addAnimationController(new AnimationController<Skrill>(this, "turnController", 35, this::turnController));
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

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @javax.annotation.Nullable SpawnGroupData pSpawnData, @javax.annotation.Nullable CompoundTag pDataTag) {
        pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        if (random.nextInt(1000) == 1) {
            this.setDragonVariant(10);
        } else {
            this.setDragonVariant(this.random.nextInt(getMaxAmountOfVariants()));
        }
        return pSpawnData;
    }

    @Override
    protected int getAggressionType() {
        return 3;
    }
    @Override
    public int getMaxAmountOfVariants() {
        return 10;
    }

    @Override
    public float getRideCameraDistanceBack() {
        if (isFlying()) {
            return 10;
        } else {
            return 5;
        }
    }

    @Override
    public float getRideCameraDistanceFront() {
        if (isFlying()) {
            return 4;
        } else {
            return 9;
        }
    }

    @Override
    public Vec3 getThroatPos(ADragonBase entity) {
        Vec3 bodyOrigin = this.position();
        double x = -Math.sin(this.yBodyRot * Math.PI / 180) * 2.4;
        double y = 1.5;
        double z = Math.cos(this.yBodyRot * Math.PI / 180) * 2.4;
        Vec3 throatPos = bodyOrigin.add(new Vec3(x, y, z));
        return throatPos;
    }

    @Override
    public void tick() {
        super.tick();
        boolean hasDamageResist = getEffect(MobEffects.DAMAGE_RESISTANCE) != null;

        if (isUsingSECONDAbility()) {
            if (!hasDamageResist && !isInWater() && !isInWaterRainOrBubble()) {
                ticksUsingSecondAbility++;
                ticksUsingActiveSecondAbility = 0;
            } else {
                ticksUsingActiveSecondAbility++;
                ticksUsingSecondAbility = 0;
            }
        }

        if (ticksUsingSecondAbility > 40) {
            this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, Util.minutesToSeconds(1), 0, false, false));
        }

        if (isOnFireAbility()) {
            Vec3 t = getBodyPos(this);



            double d0 = t.x + (this.random.nextDouble() - this.random.nextDouble()) * 3.0D;
            double d1 = t.y + (this.random.nextDouble() - this.random.nextDouble()) * 3.0D;
            double d2 = t.z + (this.random.nextDouble() - this.random.nextDouble()) * 3.0D;

                ParticleOptions particleOptions = ModParticles.SKRILL_SKILL_PARTICLES.get();

           if (this.tickCount % 4 == 0) {
                level.addParticle(particleOptions, false,
                        d0, d1, d2,
                        0.1525f * (random.nextFloat() - 0.5f),
                        0.1525f * (random.nextFloat() - 0.5f),
                        0.1525f * (random.nextFloat() - 0.5f));
            }

            level.addParticle(ParticleTypes.ELECTRIC_SPARK, false,
                    d0, d1, d2,
                    0.1525f * (random.nextFloat() - 0.5f),
                    0.1525f * (random.nextFloat() - 0.5f),
                    0.1525f * (random.nextFloat() - 0.5f));


            if(this.tickCount % 10 == 0) {
                level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(4)).forEach(livingEntity -> {
                    if(livingEntity.getEffect(MobEffects.FIRE_RESISTANCE) == null && !(livingEntity instanceof Player player && player.isCreative()))
                        if(!(this.isSaddled() && this.getPassengers().contains(livingEntity)))
                            livingEntity.setSecondsOnFire(4);
                });
            }
        }

        if (hasDamageResist) {
            this.setOnFireAbility(true);
            this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 5, 0, false, false));
        }

        if (!hasDamageResist) {
            this.setOnFireAbility(false);
        }

        if (isInWater() || isInWaterRainOrBubble() || ticksUsingActiveSecondAbility > 40) {
            if (hasDamageResist) {
                this.removeEffect(MobEffects.DAMAGE_RESISTANCE);
                ticksUsingSecondAbility = 0;
            }
        }

        performInCapacitate();
    }

    @Override
    public boolean hurt(@NotNull DamageSource pSource, float pAmount) {
        if (pSource.getDirectEntity() instanceof LivingEntity attacker && attacker.distanceTo(this) < 3 && getLastHurtByMobTimestamp() < 12 && attacker.getLastHurtByMobTimestamp() < 12) {
            attacker.setSecondsOnFire(5);
            attacker.hurt(DamageSource.thorns(this), 4.0F);
        }
        if (random.nextInt(24) == 1 && !isInWater()) {
            if (getControllingPassenger() == null) {
                this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, Util.minutesToSeconds(2), 0, false, false));
            }
        }
        return super.hurt(pSource, pAmount);
    }

    public Vec3 getBodyPos(ADragonBase entity) {
        Vec3 bodyOrigin = position();
        double x = 0;
        double y = 0;
        double z = 0;
        float scale = isBaby() ? 0.2F : 1;
        Vec3 throatPos = bodyOrigin.add(new Vec3(x * scale, y * scale, z * scale));

        return throatPos;

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

        return throatPos;
    }

    protected double rider1YOffSet() {
        return 0.6D;
    }

    protected double extraRidersXOffset() {
        return 0.4D;
    }

    protected double extraRidersYOffset() {
        return 0.6D;
    }

    protected double extraRidersZOffset() {
        return 1;
    }

    //  Attributes
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 100.0D)
                .add(Attributes.ARMOR, 6)
                .add(Attributes.ARMOR_TOUGHNESS, 16)
                .add(Attributes.MOVEMENT_SPEED, 0.2F)
                .add(Attributes.FLYING_SPEED, 0.17F)
                .add(Attributes.ATTACK_DAMAGE, 6F)
                .add(Attributes.FOLLOW_RANGE, 32F)
                .add(ForgeMod.SWIM_SPEED.get(), 0.8F);

    }


    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(1, new T3DragonWeakenAndFeedTamingGoal(this, 1));
    }

    @Override
    public void firePrimary(Vec3 riderLook, Vec3 throat) {
        if (random.nextInt(3) == 1) {
            FireBreathProjectile fireProj = new FireBreathProjectile(this, throat, riderLook, level);
            fireProj.setProjectileSize(2);
            fireProj.shoot(riderLook, 1F, 4F);
            playProjectileSound();
            level.addFreshEntity(fireProj);
        }
    }

    @Override
    public int getExplosionStrength() {
        return 2;
    }

    @Override
    public float getProjectileDamage(ADragonBase dragon, Entity entity, BaseLinearFlightProjectile projectile) {
        return 3;
    }

    @Override
    protected int breathBarRegenSpeed() {
        return 30;
    }

    @Override
    protected int breathBarRegenAmount() {
        return 4;
    }


    @Override
    public int getMaxFuel() {
        return 350;
    }

    @org.jetbrains.annotations.Nullable
    public ADragonEggBase getBreedEggResult(ServerLevel level, @NotNull AgeableMob parent) {
        SkrillEgg dragon = ModEntities.SKRILL_EGG.get().create(level);
        return dragon;
    }

    @Override
    protected int getInLoveCoolDownInMCDays() {
        return 10;
    }

    protected SoundEvent getAmbientSound() {
        if (this.isDragonSleeping()) {
            return ModSounds.SKRILL_SLEEP.get();
        } else {
            return ModSounds.SKRILL_GROWL.get();
        }
    }

    protected SoundEvent getTameSound() {
        return ModSounds.SKRILL_TAME.get();
    }

    protected SoundEvent getProjectileSound() {
        return ModSounds.SKRILL_FIRE.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return ModSounds.SKRILL_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return ModSounds.SKRILL_DEATH.get();
    }

    @Override
    protected SoundEvent get1stAttackSound() {
        return ModSounds.SKRILL_BITE.get();
    }

    protected int getMaxPassengerCapacity() {
        return 2;
    }

    @Override
    protected boolean canCarryCargo() {
        return true;
    }
    @Override
    protected boolean isItemStackForTaming(ItemStack stack) {
        return Ingredient.of(SKRILL_TAME_FOOD).test(stack);    }

    @Override
    public boolean isBreedingFood(ItemStack pStack) {
        return Ingredient.of(SKRILL_BREED_FOOD).test(pStack);
    }

}
