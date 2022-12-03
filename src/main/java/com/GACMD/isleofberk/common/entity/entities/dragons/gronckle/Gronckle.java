package com.GACMD.isleofberk.common.entity.entities.dragons.gronckle;

import com.GACMD.isleofberk.common.entity.entities.AI.taming.T2DragonFeedTamingGoal;
import com.GACMD.isleofberk.common.entity.entities.base.ADragonBase;
import com.GACMD.isleofberk.common.entity.entities.base.ADragonBaseFlyingRideable;
import com.GACMD.isleofberk.common.entity.entities.base.ADragonBaseFlyingRideableProjUser;
import com.GACMD.isleofberk.common.entity.entities.eggs.entity.GronkleEgg;
import com.GACMD.isleofberk.common.entity.entities.eggs.entity.base.ADragonEggBase;
import com.GACMD.isleofberk.common.entity.entities.projectile.proj_user.fire_bolt.FireBolt;
import com.GACMD.isleofberk.common.entity.util.Util;
import com.GACMD.isleofberk.registery.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.animal.Animal;
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
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class Gronckle extends ADragonBaseFlyingRideableProjUser implements IAnimatable {

    protected int ticksSinceLastRamAttack = 0;
    protected int ticksSinceLastBiteAttack = 0;

    protected static final EntityDataAccessor<Integer> TICK_SINCE_LAST_RAM = SynchedEntityData.defineId(ADragonBaseFlyingRideableProjUser.class, EntityDataSerializers.INT);

    public Gronckle(EntityType<? extends ADragonBaseFlyingRideable> entityType, Level level) {
        super(entityType, level);
    }

    private <E extends IAnimatable> PlayState basicMovementController(AnimationEvent<E> event) {
        if ((isFlying() && !event.isMoving())) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.Fly", ILoopType.EDefaultLoopTypes.LOOP)); // flyup
            return PlayState.CONTINUE;
        }
        if (isFlying()) {
            if (getControllingPassenger() != null) {
                if (this.getXRot() <= -3 || isGoingUp()) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.Fly", ILoopType.EDefaultLoopTypes.LOOP)); //flyup
                    return PlayState.CONTINUE;
                }
                if (this.getXRot() > -3 && this.getXRot() <= 15 && !isGoingUp()) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.Glide", ILoopType.EDefaultLoopTypes.LOOP)); // glide
                    return PlayState.CONTINUE;
                }
                if (this.getXRot() > 15 && getPassengers().size() < 2 && !isGoingUp()) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.Dive", ILoopType.EDefaultLoopTypes.LOOP)); // dive
                    return PlayState.CONTINUE;
                }
            } else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.Fly", ILoopType.EDefaultLoopTypes.LOOP)); //flyup
                return PlayState.CONTINUE;
            }
        }
        if (event.isMoving() && !shouldStopMovingIndependently()) {
            if (getTarget() != null && !getTarget().isDeadOrDying() && distanceTo(getTarget()) < 14 || isVehicle()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.Run", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            } else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.Walk", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
        }

        if (this.isDragonSitting()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.Sit", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        if (this.isDragonSleeping()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.Sleep", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.Idle", ILoopType.EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState attackController(AnimationEvent<E> event) {
        if (ticksSinceLastAttack >= 0 && ticksSinceLastAttack < 12) {
            if (getCurrentAttackType() == 0) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.Bite", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            } else if (getCurrentAttackType() == 1) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.Ram", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
        }

        if (isMarkFired()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.Breath"));
            return PlayState.CONTINUE;
        }

        return PlayState.STOP;
    }

    private <E extends IAnimatable> PlayState turnController(AnimationEvent<E> event) {
        int turnState = this.getRotationState();
        if (turnState != 0) {
            if (turnState == 1) {
                event.getController().setAnimationSpeed(4);
                event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.tailrotleft1", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            } else if (turnState == 2) {
                event.getController().setAnimationSpeed(4);
                event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.tailrotleft2", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            } else if (turnState == -1) {
                event.getController().setAnimationSpeed(4);
                event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.tailrotright1", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            } else if (turnState == -2) {
                event.getController().setAnimationSpeed(4);
                event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.tailrotright2", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
        } else {
            event.getController().setAnimationSpeed(4);
            event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.TailRot0", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }


    // Animation
    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<Gronckle>(this, "basic_MovementController", 4, this::basicMovementController));
        data.addAnimationController(new AnimationController<Gronckle>(this, "attack_Controller", 0, this::attackController));
        data.addAnimationController(new AnimationController<Gronckle>(this, "turnController", 24, this::turnController));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TICK_SINCE_LAST_RAM, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("ticks_since_ram_attack", getTicksSincePlayerLastRamAttack());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setTicksSincePlayerLastRamAttack(pCompound.getInt("ticks_since_ram_attack"));
    }


    public int getTicksSincePlayerLastRamAttack() {
        return entityData.get(TICK_SINCE_LAST_RAM);
    }

    public void setTicksSincePlayerLastRamAttack(int ticksSinceLastRamAttack) {
        entityData.set(TICK_SINCE_LAST_RAM, ticksSinceLastRamAttack);
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
    public void positionRider(Entity pPassenger) {
        super.positionRider(pPassenger);
        pPassenger.setPos(this.getX(), this.getY() + 1, this.getZ());

    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(1, new T2DragonFeedTamingGoal(this, 1));
        this.targetSelector.addGoal(2, new NonTameRandomTargetGoal<>(this, Animal.class, false, PREY_SELECTOR));
    }

    @Override
    public boolean isDragonOnGround() {        // fix height
        for (int i = 0; getControllingPassenger() != null ? i < 3 : i < baseDragonOnGroundHeight; ++i) {
            BlockPos solidPos = new BlockPos(this.position().x, this.position().y - i, this.position().z);
            if (!level.getBlockState(solidPos).isAir())
                return true;
        }
        return false;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public Vec3 getThroatPos(ADragonBase entity) {
        Vec3 bodyOrigin = this.position();
        double x = -Math.sin(this.getYRot() * Math.PI / 180) * 2.4;
        double y = 1.5;
        double z = Math.cos(this.getYRot() * Math.PI / 180) * 2.4;
        Vec3 throatPos = bodyOrigin.add(new Vec3(x, y, z));
        return throatPos;


    }

    @Override
    protected void fireProjectile(Vec3 riderLook, Vec3 throat) {
        if ((tier1() || tier2() || tier3() || tier4()) && !isUsingAbility()) {
            setTicksSinceLastFire(20);
            FireBolt bolt = new FireBolt(this, throat, riderLook, level, getExplosionStrength());
            bolt.shoot(riderLook, 1F);
            level.addFreshEntity(bolt);
            playerBoltBlastPendingScale = 0;
        }
    }

    /**
     * 0 : none; 1: weapon; 2: armor or weapon 3: always, only be obtained via eggs
     */
    @Override
    protected int getAggressionType() {
        return super.getAggressionType();
    }

    //  Attributes
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 120.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3F)
                .add(Attributes.ARMOR, 20)
                .add(Attributes.FLYING_SPEED, 0.06F)
                .add(Attributes.ATTACK_DAMAGE, 17F)
                .add(Attributes.FOLLOW_RANGE, 4.5F)
                .add(ForgeMod.STEP_HEIGHT_ADDITION.get(), 1F)
                .add(ForgeMod.SWIM_SPEED.get(), 0.8F);
    }

    @Override
    public void swing(InteractionHand pHand) {
        int random1 = random.nextInt(200);
        if (random1 > 1) {
            ticksSinceLastBiteAttack = Util.secondsToTicks(3);
            ticksSinceLastRamAttack = 0;
        }

        if (random1 > 100) {
            ticksSinceLastRamAttack = Util.secondsToTicks(3);
            ticksSinceLastBiteAttack = 0;
        }
        super.swing(pHand);
    }

    @Override
    public void tick() {
        super.tick();
        if (ticksSinceLastBiteAttack >= 0) {
            ticksSinceLastBiteAttack--;
        }

        if (ticksSinceLastRamAttack >= 0) {
            ticksSinceLastRamAttack--;
        }

        if (getTicksSincePlayerLastRamAttack() >= 0) {
            setTicksSincePlayerLastRamAttack(getTicksSincePlayerLastRamAttack() - 1);
        }

        // 0 ram attack
        // 1 bite attack
        if (ticksSinceLastRamAttack >= 0) {
            setCurrentAttackType(0);
        } else if (ticksSinceLastBiteAttack >= 0) {
            setCurrentAttackType(1);
        }

        if (this.tier1()) {
            setExplosionStrength(0);
        } else if (this.tier2()) {
            setExplosionStrength(1);
        } else if (this.tier3()) {
            setExplosionStrength(3);
        } else if (this.tier4()) {
            setExplosionStrength(4);
        }
    }

    @Override
    protected boolean canCarryCargo() {
        return true;
    }

    protected double rider1XOffSet() {
        return 0;
    }

    protected double rider1YOffSet() {
        return 1.8D;
    }

    protected double rider1ZOffSet() {
        return 0;
    }

    protected double rider2XOffSet() {
        return 1;
    }

    protected double rider2YOffSet() {
        return 1.8D;
    }

    protected double rider2ZOffSet() {
        return 1;
    }

    @org.jetbrains.annotations.Nullable
    public ADragonEggBase getBreedEggResult(ServerLevel level, @NotNull AgeableMob parent) {
        GronkleEgg dragon = ModEntities.GRONCKLE_EGG.get().create(level);
        return dragon;
    }

    @Override
    public float getRideCameraDistanceVert() {
        return 0.2F;
    }

}
