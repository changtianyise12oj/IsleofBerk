package com.GACMD.isleofberk.entity.dragons.deadlynadder;

import com.GACMD.isleofberk.entity.AI.taming.T2DragonFeedTamingGoal;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideable;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideableBreathUser;
import com.GACMD.isleofberk.entity.eggs.entity.base.ADragonEggBase;
import com.GACMD.isleofberk.entity.eggs.entity.eggs.DeadlyNadderEgg;
import com.GACMD.isleofberk.entity.projectile.abase.BaseLinearFlightProjectile;
import com.GACMD.isleofberk.entity.projectile.other.nadder_spike.DeadlyNadderSpike;
import com.GACMD.isleofberk.registery.ModEntities;
import com.GACMD.isleofberk.registery.ModSounds;
import com.GACMD.isleofberk.util.Util;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.Nullable;

public class DeadlyNadder extends ADragonBaseFlyingRideableBreathUser {

    int ticksSinceLastStingAttack = 0;
    int ticksSinceLastStingShootAI = 0;

    protected static final EntityDataAccessor<Integer> TICK_SINCE_LAST_FIRE = SynchedEntityData.defineId(DeadlyNadder.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Boolean> MARK_FIRED = SynchedEntityData.defineId(DeadlyNadder.class, EntityDataSerializers.BOOLEAN);

    private <E extends IAnimatable> PlayState basicMovementController(AnimationEvent<E> event) {
        if ((isFlying() && !event.isMoving())) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("DeadlyNadderFlap", ILoopType.EDefaultLoopTypes.LOOP)); // hover
            setShouldPlayFlapping(true);
            return PlayState.CONTINUE;
        }
        if (isFlying()) {
            if (event.isMoving()) {
                if (getControllingPassenger() instanceof Player) {
                    if (this.getXRot() < 11 || isGoingUp() || getPassengers().size() > 2 || getFirstPassenger() == null) {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("DeadlyNadderFlap", ILoopType.EDefaultLoopTypes.LOOP)); //flyup DeadlyNadderFlyup
                        setShouldPlayFlapping(true);
                        return PlayState.CONTINUE;
                    }
                    if (this.getXRot() >= 11 && this.getXRot() < 26 && !isGoingUp()) { // < 20
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("DeadlyNadderGlide", ILoopType.EDefaultLoopTypes.LOOP)); // glide
                        setShouldPlayFlapping(false);
                        return PlayState.CONTINUE;
                    }
                    if (this.getXRot() >= 26 && !isGoingUp()) { // > 30
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("DeadlyNadderDive", ILoopType.EDefaultLoopTypes.LOOP)); // dive
                        setShouldPlayFlapping(false);
                        return PlayState.CONTINUE;
                    }
                } else if (getOwner() instanceof Player player && isDragonFollowing() && player.isFallFlying()) {
                    float dist = distanceTo(player);
                    double ydist = this.getY() - player.getY();
                    if (dist > 4.3F && ydist < 5F) {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("DeadlyNadderFlap", ILoopType.EDefaultLoopTypes.LOOP)); //flyup DeadlyNadderFlyup
                        setShouldPlayFlapping(true);
                        return PlayState.CONTINUE;
                    }
                    if (dist < 4.3F && ydist < 5F) {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("DeadlyNadderGlide", ILoopType.EDefaultLoopTypes.LOOP)); //flyup
                        setShouldPlayFlapping(false);
                        return PlayState.CONTINUE;
                    }
                    if (ydist > 5F && dist > 7.8F) {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("DeadlyNadderDive", ILoopType.EDefaultLoopTypes.LOOP)); // dive
                        setShouldPlayFlapping(false);
                        return PlayState.CONTINUE;
                    }
                } else {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("DeadlyNadderFlap", ILoopType.EDefaultLoopTypes.LOOP)); //flyup
                    setShouldPlayFlapping(true);
                    return PlayState.CONTINUE;
                }
            }
        } else {
            if (this.isDragonSitting() && !isDragonSleeping()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("DeadlyNadderSit", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
            if (event.isMoving() && !shouldStopMovingIndependently()) {
                if (getTarget() != null && !getTarget().isDeadOrDying() && distanceTo(getTarget()) < 14 || isVehicle()) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("DeadlyNadderRun", ILoopType.EDefaultLoopTypes.LOOP));
                    return PlayState.CONTINUE;

                } else {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("DeadlyNadderWalk", ILoopType.EDefaultLoopTypes.LOOP));
                    return PlayState.CONTINUE;
                }
            }
            if (this.isDragonSleeping()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("DeadlyNadderSleep", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }


            event.getController().setAnimation(new AnimationBuilder().addAnimation("DeadlyNadderIdle", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState attackController(AnimationEvent<E> event) {
        if (getTicksSinceLastAttack() >= 0 && getTicksSinceLastAttack() < 12) {
            if (getCurrentAttackType() == 0) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("DeadlyNadderBite", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
        }
        if (isUsingAbility()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("DeadlyNadderBreath", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }

        if (isMarkFired()) {
            if (isFlying()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("DeadlyNadderTailWhipDartAir"));
                return PlayState.CONTINUE;
            } else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("DeadlyNadderTailWhipDartGround"));
                return PlayState.CONTINUE;
            }
        }
        return PlayState.STOP;
    }

    private <E extends IAnimatable> PlayState turnController(AnimationEvent<E> event) {
        int turnState = this.getRotationState();
        if (turnState != 0) {
            if (isFlying()) {
                boolean diving = getXRot() >= 32 && event.isMoving();
                if (isGoingUp() || diving) {
                    event.getController().setAnimationSpeed(4);
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("nadder.tailrot0", ILoopType.EDefaultLoopTypes.LOOP));
                    return PlayState.CONTINUE;
                } else {
                    if (turnState == 1) {
                        event.getController().setAnimationSpeed(4);
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("nadder.tailrotleft1f", ILoopType.EDefaultLoopTypes.LOOP));
                        return PlayState.CONTINUE;
                    } else if (turnState == 2) {
                        event.getController().setAnimationSpeed(4);
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("nadder.tailrotleft2f", ILoopType.EDefaultLoopTypes.LOOP));
                        return PlayState.CONTINUE;
                    } else if (turnState == -1) {
                        event.getController().setAnimationSpeed(4);
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("nadder.tailrotright1f", ILoopType.EDefaultLoopTypes.LOOP));
                        return PlayState.CONTINUE;
                    } else if (turnState == -2) {
                        event.getController().setAnimationSpeed(4);
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("nadder.tailrotright2f", ILoopType.EDefaultLoopTypes.LOOP));
                        return PlayState.CONTINUE;
                    }
                }
            } else {
                if (turnState == 1) {
                    event.getController().setAnimationSpeed(4);
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("nadder.tailrotleft1", ILoopType.EDefaultLoopTypes.LOOP));
                    return PlayState.CONTINUE;
                } else if (turnState == 2) {
                    event.getController().setAnimationSpeed(4);
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("nadder.tailrotleft2", ILoopType.EDefaultLoopTypes.LOOP));
                    return PlayState.CONTINUE;
                } else if (turnState == -1) {
                    event.getController().setAnimationSpeed(4);
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("nadder.tailrotright1", ILoopType.EDefaultLoopTypes.LOOP));
                    return PlayState.CONTINUE;
                } else if (turnState == -2) {
                    event.getController().setAnimationSpeed(4);
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("nadder.tailrotright2", ILoopType.EDefaultLoopTypes.LOOP));
                    return PlayState.CONTINUE;
                }
            }
        } else {
            event.getController().setAnimationSpeed(4);
            event.getController().setAnimation(new AnimationBuilder().addAnimation("nadder.tailrot0", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }

        if (isGoingUp()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("rot0", ILoopType.EDefaultLoopTypes.LOOP)); //flyup
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }


    // Animation
    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<DeadlyNadder>(this, "basic_MovementController", 4, this::basicMovementController));
        data.addAnimationController(new AnimationController<DeadlyNadder>(this, "attack_Controller", 0, this::attackController));
        data.addAnimationController(new AnimationController<DeadlyNadder>(this, "turnController", 35, this::turnController));
    }

    public DeadlyNadder(EntityType<? extends ADragonBaseFlyingRideable> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TICK_SINCE_LAST_FIRE, 0);
        this.entityData.define(MARK_FIRED, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("markFired", isMarkFired());
        pCompound.putInt("ticksFire", getTicksSinceLastFire());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setTicksSinceLastFire(pCompound.getInt("ticksFire"));
        this.setMarkFired(pCompound.getBoolean("markFired"));
    }

    public int getTicksSinceLastFire() {
        return this.entityData.get(TICK_SINCE_LAST_FIRE);
    }

    public void setTicksSinceLastFire(int pType) {
        this.entityData.set(TICK_SINCE_LAST_FIRE, pType);
    }

    public boolean isMarkFired() {
        return this.entityData.get(MARK_FIRED);
    }

    public void setMarkFired(boolean fired) {
        this.entityData.set(MARK_FIRED, fired);
    }

    //  Attributes
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 80.0D)
                .add(Attributes.ARMOR, 2)
                .add(Attributes.MOVEMENT_SPEED, 0.4F)
                .add(Attributes.FLYING_SPEED, 0.14F)
                .add(Attributes.ATTACK_DAMAGE, 12F)
                .add(ForgeMod.STEP_HEIGHT_ADDITION.get(), 1F)
                .add(ForgeMod.SWIM_SPEED.get(), 0.8F);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(1, new T2DragonFeedTamingGoal(this, 1));
        this.targetSelector.addGoal(2, new NonTameRandomTargetGoal<>(this, Animal.class, false, PREY_SELECTOR));
    }


    public Vec3 getThroatPos(ADragonBase entity) {
        Vec3 bodyOrigin = position();

        double x = -Math.sin(this.yBodyRot * Math.PI / 180) * 1.4D;
        double y = getLookAngle().y * 2 + 1;
        double z = Math.cos(this.yBodyRot * Math.PI / 180) * 1.4D;
        Vec3 throatPos = bodyOrigin.add(new Vec3(x, y, z));
        return throatPos;

    }

    @Override
    public Vec3 getTailPos(ADragonBase entity) {
        Vec3 bodyOrigin = position();

        double x = Math.cos(Math.toRadians(getYRot() - 90)) * 1.2;
        double y = 1;
        double z = Math.sin(Math.toRadians(getYRot() - 90)) * 1.2;
        Vec3 tailPos = bodyOrigin.add(new Vec3(x, y, z));
        return tailPos;
    }

    @Override
    public boolean hurt(@NotNull DamageSource pSource, float pAmount) {
        return super.hurt(pSource, pAmount);
    }

    @Override
    public void tick() {
        super.tick();
        if (ticksSinceLastStingAttack >= 0 && !isUsingSECONDAbility())
            ticksSinceLastStingAttack--;

        int threshold = 3;
        if (isUsingSECONDAbility() && ticksSinceLastStingAttack < threshold + 6) {
            ticksSinceLastStingAttack++;
        }

        if (getTicksSinceLastFire() > 0) {
            setTicksSinceLastFire(getTicksSinceLastFire() - 1);
        }

        if (getTicksSinceLastFire() < 2) {
            setMarkFired(false);
        } else {
            setMarkFired(true);
        }

        if (getControllingPassenger() instanceof Player player) {
            if (ticksSinceLastStingAttack > threshold && !isUsingSECONDAbility()) {
                Vec3 riderLook = player.getLookAngle();
                performRangedAttack(riderLook, 1);
            }
        }

        String s = ChatFormatting.stripFormatting(this.getName().getString());
        if (s != null) {
            if (s.equals("Stormfly") || s.equals("stormfly")) {
                this.setDragonVariant(0);
            }
        }

        // probably scale the hitbox too
        // use in melee AI
        if (getTarget() != null && !(getTarget() instanceof Animal) && !(getTarget() instanceof WaterAnimal) && (getTarget() instanceof Player player && !player.isCreative())) {
            if (!(getControllingPassenger() instanceof Player)) {
                if (getRandom().nextInt(25) == 1) {
                    performRangedAttackAI(getViewVector(1F), 1);
                    ticksSinceLastStingShootAI = Util.secondsToTicks(1);
                }

                if (ticksSinceLastStingShootAI > 0) {
                    ticksSinceLastStingShootAI--;
                }
            }
        }
    }

    @Override
    public void fireSecondary(Vec3 riderLook, Vec3 throat) {

    }

    /**
     * Attack the specified entity using a ranged attack.
     */
    public void performRangedAttack(Vec3 riderLook, float pDistanceFactor) {
        setTicksSinceLastFire(10);
        DeadlyNadderSpike spike = new DeadlyNadderSpike(level, this);
        spike.setOwner(this);
        double d0 = riderLook.x();
        double d1 = riderLook.y();
        double d2 = riderLook.z();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        spike.shoot(d0, d1 + d3 * (double) 0.2F, d2, 4F, 1F);
        modifySecondaryFuel(-4);
        this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level.addFreshEntity(spike);
    }

    /**
     * Attack the specified entity using a ranged attack.
     */
    public void performRangedAttackAI(Vec3 dragonLook, float pDistanceFactor) {
        setTicksSinceLastFire(10);
        DeadlyNadderSpike spike = new DeadlyNadderSpike(level, this);
        spike.setOwner(this);
        double d0 = dragonLook.x();
        double d1 = dragonLook.y();
        double d2 = dragonLook.z();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        spike.shoot(d0, d1 + d3 * (double) 0.2F, d2, 4F, 1.2F);
        modifySecondaryFuel(-4);
        this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level.addFreshEntity(spike);
    }

    /**
     * Fires an arrow
     */
    protected AbstractArrow getArrow(ItemStack pArrowStack, float pDistanceFactor) {
        return ProjectileUtil.getMobArrow(this, pArrowStack, pDistanceFactor);
    }

    // Variant pt2
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @javax.annotation.Nullable SpawnGroupData pSpawnData, @javax.annotation.Nullable CompoundTag pDataTag) {
        pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        this.setDragonVariant(this.random.nextInt(getMaxAmountOfVariants()));
        return pSpawnData;
    }

    public int getMaxAmountOfVariants() {
        return 11;
    }

    @Override
    public void swing(InteractionHand pHand) {
        super.swing(pHand);
    }

    /**
     * Check if the ground 2 blocks below is a solid. Replacement for Vanilla onGround
     *
     * @return solidBlockState
     */
    public boolean isOnGround() {
        BlockPos solidPos = new BlockPos(this.position().x, this.position().y - 1, this.position().z);
        return !level.getBlockState(solidPos).isAir();
    }

    @Override
    protected Item tameItem() {
        return Items.CHICKEN;
    }

    @Override
    public float getRideCameraDistanceBack() {
        return 8;
    }

    @Override
    public float getRideCameraDistanceFront() {
        return 5;
    }

    @Override
    public int getExplosionStrength() {
        return 1;
    }

    @Override
    protected boolean canCarryCargo() {
        return true;
    }

    protected double rider1XOffSet() {
        return 0;
    }

    protected double rider1YOffSet() {
        return 1.7D;
    }

    protected double rider1ZOffSet() {
        return 0;
    }

    protected double rider2XOffSet() {
        return 1;
    }

    protected double rider2YOffSet() {
        return 1.5D;
    }

    protected double rider2ZOffSet() {
        return 1;
    }

    @org.jetbrains.annotations.Nullable
    public ADragonEggBase getBreedEggResult(ServerLevel level, @NotNull AgeableMob parent) {
        DeadlyNadderEgg dragon = ModEntities.NADDER_EGG.get().create(level);
        return dragon;
    }

    /**
     * default value is used by deadly nadder breath, reduced damage to players
     *
     * @param dragon
     * @param entity
     * @param projectile
     * @return
     */
    public float getProjectileDamage(ADragonBase dragon, Entity entity, BaseLinearFlightProjectile projectile) {
        return 9;
    }

    @Override
    protected int getInLoveCoolDownInMCDays() {
        return 14;
    }

    protected SoundEvent getAmbientSound() {
        if (this.isDragonSleeping()) {
            return ModSounds.DEADLY_NADDER_SLEEP.get();
        }
        else {
            return ModSounds.DEADLY_NADDER_GROWL.get();
        }
    }

    protected SoundEvent getTameSound() {
        return ModSounds.DEADLY_NADDER_TAME.get();
    }

    protected SoundEvent getProjectileSound() {
        return ModSounds.DEADLY_NADDER_FIRE.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return ModSounds.DEADLY_NADDER_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return ModSounds.DEADLY_NADDER_DEATH.get();
    }

    @Override
    protected SoundEvent get1stAttackSound() {
        return ModSounds.DEADLY_NADDER_BITE.get();
    }
}



