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
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
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

import static com.GACMD.isleofberk.registery.ModTags.Items.NADDER_BREED_FOOD;
import static com.GACMD.isleofberk.registery.ModTags.Items.NADDER_TAME_FOOD;

public class DeadlyNadder extends ADragonBaseFlyingRideableBreathUser {

    int ticksSinceLastStingAttack = 0;
    int ticksSinceLastStingShootAI = 0;

    protected static final EntityDataAccessor<Integer> TICK_SINCE_LAST_FIRE = SynchedEntityData.defineId(DeadlyNadder.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Boolean> MARK_FIRED = SynchedEntityData.defineId(DeadlyNadder.class, EntityDataSerializers.BOOLEAN);

    private <E extends IAnimatable> PlayState basicMovementController(AnimationEvent<E> event) {

        // flying animations
        if (isFlying()) {
            if (event.isMoving()){

                // mounted flying
                if (this.isVehicle()) {
                    if (this.getXRot() < 8 || isGoingUp() || getPassengers().size() > 2) {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("nadder.flap", ILoopType.EDefaultLoopTypes.LOOP));
                        setShouldPlayFlapping(true);
                    }
                    if (this.getXRot() >= 8 && this.getXRot() < 33 && !isGoingUp()) {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("nadder.glide", ILoopType.EDefaultLoopTypes.LOOP));
                        setShouldPlayFlapping(false);
                    }
                    if (this.getXRot() >= 33 && !isGoingUp()) {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("nadder.dive", ILoopType.EDefaultLoopTypes.LOOP));
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
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("nadder.flap", ILoopType.EDefaultLoopTypes.LOOP));
                    } else {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("nadder.dive", ILoopType.EDefaultLoopTypes.LOOP));
                    }
                }
                // free flying
                else {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("nadder.flap", ILoopType.EDefaultLoopTypes.LOOP));
                    setShouldPlayFlapping(true);
                }
            } else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("nadder.hover", ILoopType.EDefaultLoopTypes.LOOP));
                setShouldPlayFlapping(true);
            }

            //ground animations
        } else {
            if (this.isDragonSitting() && !this.isDragonSleeping()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("nadder.sit", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
            if (this.isDragonSleeping()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("nadder.sleep", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
            if (event.isMoving() && !shouldStopMovingIndependently()) {
                if (getTarget() != null && !getTarget().isDeadOrDying() && distanceTo(getTarget()) < 14 || isVehicle()) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("nadder.run", ILoopType.EDefaultLoopTypes.LOOP));
                } else {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("nadder.walk", ILoopType.EDefaultLoopTypes.LOOP));
                }
                return PlayState.CONTINUE;
            } else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("nadder.idle", ILoopType.EDefaultLoopTypes.LOOP));
            }
        }
        return PlayState.CONTINUE;
    }

    // Attack animations
    private <E extends IAnimatable> PlayState attackController(AnimationEvent<E> event) {
        if (getTicksSinceLastAttack() >= 0 && getTicksSinceLastAttack() < 12) {
            if (getCurrentAttackType() == 0) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("nadder.bite", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
        }
        if (isUsingAbility()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("nadder.breath", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }

        if (isMarkFired()) {
            if (isFlying()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("nadder.dart"));
                return PlayState.CONTINUE;
            } else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("nadder.dart_ground"));
                return PlayState.CONTINUE;
            }
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
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("nadder.tailrotleft1f", ILoopType.EDefaultLoopTypes.LOOP));
                } else if (turnState == 2) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("nadder.tailrotleft2f", ILoopType.EDefaultLoopTypes.LOOP));
                } else if (turnState == -1) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("nadder.tailrotright1f", ILoopType.EDefaultLoopTypes.LOOP));
                } else if (turnState == -2) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("nadder.tailrotright2f", ILoopType.EDefaultLoopTypes.LOOP));
                }
            } else {
                if (turnState == 1) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("nadder.tailrotleft1", ILoopType.EDefaultLoopTypes.LOOP));
                } else if (turnState == 2) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("nadder.tailrotleft2", ILoopType.EDefaultLoopTypes.LOOP));
                } else if (turnState == -1) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("nadder.tailrotright1", ILoopType.EDefaultLoopTypes.LOOP));
                } else if (turnState == -2) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("nadder.tailrotright2", ILoopType.EDefaultLoopTypes.LOOP));
                }
            }
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("nadder.tailrot0", ILoopType.EDefaultLoopTypes.LOOP));
        }
        return PlayState.CONTINUE;
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
                .add(Attributes.ARMOR_TOUGHNESS, 10)
                .add(Attributes.MOVEMENT_SPEED, 0.4F)
                .add(Attributes.FLYING_SPEED, 0.14F)
                .add(Attributes.ATTACK_DAMAGE, 6F)
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
            if (getRemainingSecondFuel() > 0) {
                if (ticksSinceLastStingAttack > threshold && !isUsingSECONDAbility()) {
                    Vec3 riderLook = player.getLookAngle();
                    performRangedAttack(riderLook, 1);
                }
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
                if (getRandom().nextInt(4) == 1) {
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
        spike.shoot(d0, d1 + d3 * (double) 0.2F, d2, 5F, 1F);
        modifySecondaryFuel(-1);
        this.playSound(ModSounds.DEADLY_NADDER_STING.get(), 5.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
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
        modifySecondaryFuel(-1);
        this.playSound(ModSounds.DEADLY_NADDER_STING.get(), 5.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
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

    @Override
    protected boolean isItemStackForTaming(ItemStack stack) {
        return Ingredient.of(NADDER_TAME_FOOD).test(stack);
    }

    @Override
    public boolean isBreedingFood(ItemStack pStack) {
        return Ingredient.of(NADDER_BREED_FOOD).test(pStack);
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

    protected double extraRidersXOffset() {
        return 0.4D;
    }

    protected double extraRidersYOffset() {
        return 1.7D;
    }

    protected double extraRidersZOffset() {
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
        return 2;
    }

    @Override
    protected int getInLoveCoolDownInMCDays() {
        return 14;
    }

    protected SoundEvent getAmbientSound() {
        if (this.isDragonSleeping()) {
            return ModSounds.DEADLY_NADDER_SLEEP.get();
        } else {
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

    protected int getMaxPassengerCapacity() {
        return 2;
    }
}



