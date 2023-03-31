package com.GACMD.isleofberk.entity.base.dragon;


import com.GACMD.isleofberk.entity.AI.flight.own.AIDragonLand;
import com.GACMD.isleofberk.entity.AI.flight.own.DragonFlyAndAttackAirbourneTargetGoal;
import com.GACMD.isleofberk.entity.AI.flight.own.UntamedDragonCircleFlightGoal;
import com.GACMD.isleofberk.entity.AI.flight.player.AIDragonRide;
import com.GACMD.isleofberk.entity.AI.flight.player.DragonFollowPlayerFlying;
import com.GACMD.isleofberk.entity.AI.path.air.DragonFlyingPathNavigation;
import com.GACMD.isleofberk.entity.AI.path.air.FlyingDragonMoveControl;
import com.GACMD.isleofberk.entity.AI.target.DragonNonTameRandomTargetGoal;
import com.GACMD.isleofberk.entity.AI.water.DragonFloatGoal;
import com.GACMD.isleofberk.entity.dragons.deadlynadder.DeadlyNadder;
import com.GACMD.isleofberk.entity.dragons.gronckle.Gronckle;
import com.GACMD.isleofberk.entity.dragons.nightfury.NightFury;
import com.GACMD.isleofberk.entity.dragons.tryiple_stryke.TripleStryke;
import com.GACMD.isleofberk.network.ControlNetwork;
import com.GACMD.isleofberk.network.message.MessageDragonFlapSounds;
import com.GACMD.isleofberk.registery.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PlayerRideable;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;

public class ADragonBaseFlyingRideable extends ADragonRideableUtility implements IAnimatable, PlayerRideable {
    private static final EntityDataAccessor<Boolean> IS_FLYING = SynchedEntityData.defineId(ADragonBaseFlyingRideable.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> TICKS_FLY_WANDERING = SynchedEntityData.defineId(ADragonBaseFlyingRideable.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> IN_AIR_TICKS_VEHICLE = SynchedEntityData.defineId(ADragonBaseFlyingRideable.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ROTATION_STATE = SynchedEntityData.defineId(ADragonBaseFlyingRideable.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> IS_LANDING = SynchedEntityData.defineId(ADragonBaseFlyingRideable.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_GOINGUP = SynchedEntityData.defineId(ADragonBaseFlyingRideable.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_GOINDOWN = SynchedEntityData.defineId(ADragonBaseFlyingRideable.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_FLAPPING = SynchedEntityData.defineId(ADragonBaseFlyingRideable.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> USING_SECOND_NAVIGATOR = SynchedEntityData.defineId(ADragonBaseFlyingRideable.class, EntityDataSerializers.BOOLEAN);

    public boolean isLandNavigator;
    public int ticksUnderwater;
    public float oFlapTime;
    public float flapTime;

    public ADragonBaseFlyingRideable(EntityType<? extends ADragonBaseFlyingRideable> entityType, Level level) {
        super(entityType, level);
        switchNavigator(true);
    }

    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        return false;
    }

    public boolean isLanding() {
        return this.entityData.get(IS_LANDING);
    }

    public void setIsLanding(boolean isLanding) {
        this.entityData.set(IS_LANDING, isLanding);
    }

    public boolean isFlying() {
        return this.entityData.get(IS_FLYING);
    }

    public void setIsFlying(boolean flying) {
        this.entityData.set(IS_FLYING, flying);
    }

    public boolean IsUsingSecondNavigator() {
        return this.entityData.get(USING_SECOND_NAVIGATOR);
    }

    public void setIsUsingSecondNavigator(boolean usingSecondNavigator) {
        this.entityData.set(USING_SECOND_NAVIGATOR, usingSecondNavigator);
    }

    public int getTicksFlyWandering() {
        return this.entityData.get(TICKS_FLY_WANDERING);
    }

    public void setTicksFlyWandering(int flying) {
        this.entityData.set(TICKS_FLY_WANDERING, flying);
    }

    public int getRotationState() {
        return this.entityData.get(ROTATION_STATE);
    }

    public void setRotationState(int rotation_state) {
        this.entityData.set(ROTATION_STATE, rotation_state);
    }

    public int getInAirTicksVehicle() {
        return this.entityData.get(IN_AIR_TICKS_VEHICLE);
    }

    public void setInAirTicksVehicle(int inAirTicks) {
        this.entityData.set(IN_AIR_TICKS_VEHICLE, inAirTicks);
    }

    public boolean isGoingUp() {
        return this.entityData.get(IS_GOINGUP);
    }

    public void setIsGoingUp(boolean jumping) {
        this.entityData.set(IS_GOINGUP, jumping);
    }

    public boolean isGoingDown() {
        return this.entityData.get(IS_GOINDOWN);
    }

    public void setIsGoingDown(boolean goingdown) {
        this.entityData.set(IS_GOINDOWN, goingdown);
    }

    public boolean shouldPlayFlapping() {
        return this.entityData.get(IS_FLAPPING);
    }

    public void setShouldPlayFlapping(boolean hovering) {
        this.entityData.set(IS_FLAPPING, hovering);
    }

    public void switchNavigator(boolean useLandNavigationController) {
        if (useLandNavigationController) {
            this.moveControl = new MoveControl(this);
            this.navigation = new GroundPathNavigation(this, level);
            this.isLandNavigator = true;
        } else {
            this.moveControl = new FlyingDragonMoveControl(this, 1, true);
            this.navigation = new DragonFlyingPathNavigation(this, level);
            this.isLandNavigator = false;
        }
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_FLYING, false);
        this.entityData.define(TICKS_FLY_WANDERING, 0);
        this.entityData.define(ROTATION_STATE, 0);
        this.entityData.define(IN_AIR_TICKS_VEHICLE, 0);
        this.entityData.define(IS_LANDING, false);
        this.entityData.define(IS_GOINGUP, false);
        this.entityData.define(IS_GOINDOWN, false);
        this.entityData.define(IS_FLAPPING, false);
        this.entityData.define(USING_SECOND_NAVIGATOR, false);
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("is_flying", this.isFlying());
        pCompound.putBoolean("is_landing", this.isLanding());
        pCompound.putInt("ticks_fly_wandering", this.getTicksFlyWandering());
        pCompound.putInt("in_air_ticks_vehicle", this.getInAirTicksVehicle());
        pCompound.putInt("rotation_state", this.getRotationState());
        pCompound.putBoolean("is_goingup", this.isGoingUp());
        pCompound.putBoolean("is_goingdown", this.isGoingDown());
        pCompound.putBoolean("play_flapping", this.shouldPlayFlapping());
        pCompound.putBoolean("is_using_second_navigator", this.IsUsingSecondNavigator());
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setIsFlying(pCompound.getBoolean("is_flying"));
        this.setIsLanding(pCompound.getBoolean("is_landing"));
        this.setTicksFlyWandering(pCompound.getInt("ticks_fly_wandering"));
        this.setInAirTicksVehicle(pCompound.getInt("in_air_ticks_vehicle"));
        this.setRotationState(pCompound.getInt("rotation_state"));
        this.setIsGoingUp(pCompound.getBoolean("is_goingup"));
        this.setIsGoingDown(pCompound.getBoolean("is_goingdown"));
        this.setShouldPlayFlapping(pCompound.getBoolean("play_flapping"));
        this.setIsUsingSecondNavigator(pCompound.getBoolean("is_using_second_navigator"));
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new DragonFloatGoal(this));
        this.goalSelector.addGoal(0, new DragonFollowPlayerFlying(this, 3, 4, 3));
//        this.goalSelector.addGoal(0, new FollowOwnerNoTPGoal(this, 1.1D, 4, 4, true));
        this.goalSelector.addGoal(0, new DragonFlyAndAttackAirbourneTargetGoal(this, 1, true));
        this.goalSelector.addGoal(1, new UntamedDragonCircleFlightGoal(this));
        this.goalSelector.addGoal(1, new AIDragonLand(this, 1));
        this.goalSelector.addGoal(2, new AIDragonRide(this));
        this.targetSelector.addGoal(2, new DragonNonTameRandomTargetGoal<>(this, Animal.class, false, PREY_SELECTOR));
    }

    @Override
    public boolean hurt(@NotNull DamageSource pSource, float pAmount) {
        setTicksFlyWandering(200);
        return super.hurt(pSource, pAmount);
    }

    @Override
    protected float getWaterSlowDown() {
        return 0.9F;
    }

    // facing up: xHeadRot = -12.5;
    // facing down: xHeadRot = 22.5;
    // facing forward: xHeadRot = 0;
    private double calculateFlightHeight(double xHeadRot) {
        if (this.getY() < 250) {
            double y = isFlying() && (isGoingUp() || isGoingDown()) ? 0 : xHeadRot * -0.005;
            return y;
        } else {
            return 0;
        }
    }

    /**
     * travel is called every movement click
     */
    // facing up: xHeadRot = -12.5;
    // facing down: xHeadRot = 22.5;
    // facing forward: xHeadRot = 0;
    @Override
    public void travel(@NotNull Vec3 pTravelVector) {
        if (isAlive()) {
            if (this.isVehicle() && this.canBeControlledByRider()) {
                // set rotations and tie it to pilot rotations
                LivingEntity pilot = (LivingEntity) this.getControllingPassenger();
                assert pilot != null;
                pilot.setYBodyRot(pilot.getYHeadRot());
                this.setYRot(pilot.getYRot());
//                disabled for animation purposes
//                this.yRotO = this.getYRot();
                if (isFlying()) {
                    this.setXRot(pilot.getXRot() * 0.5F);
                } else {
                    this.setXRot(0);
                }
                this.setRot(this.getYRot(), this.getXRot());
                this.yBodyRot = this.getYRot();
                this.yHeadRot = this.yBodyRot;

                // xxa and zza half the xxa, zza is forward
                float f = pilot.xxa * 0.5F;
                float f1 = pilot.zza;
                if (f1 <= 0.0F) {
                    f1 *= 0.25F;
                }

                if (getControllingPassenger() != null) {
                    byte turnState = 0;
                    float rotationSpeed = 6;
                    float yawDiff = yRotO - this.getYRot();
                    // currently, 1 is left 2 is right
                    // make it so 1 is left 2 is left2, -1 is right1 and -2 is right2
                    // greater than zero > 0 means right
                    turnState = (Math.abs(yawDiff)) > rotationSpeed && yawDiff > 0 ? 1 : turnState;
                    turnState = (Math.abs(yawDiff)) > rotationSpeed && yawDiff > 4 ? 2 : turnState;
                    // less than 0 < 0 meaning right
                    turnState = (Math.abs(yawDiff)) > rotationSpeed && yawDiff < 0 ? -1 : turnState;
                    turnState = (Math.abs(yawDiff)) > rotationSpeed && yawDiff < -4 ? -2 : turnState;
                    this.setRotationState(turnState);
                } else {
                    setRotationState(0);
                }

                this.setYya(pilot.yya);
                float xxa = pilot.xxa * 0.5F;
                float zza = pilot.zza;

                // facing straight up is -10 xRot, up is - and + is down
                double xHeadRot;
                if (this.getXRot() > 0) {
                    xHeadRot = this.getXRot() / 2;
                } else {
                    xHeadRot = this.getXRot() / 3.6;
                }

                double xHeadRotABS = Math.abs(this.getXRot()) / 450;
                double y = xHeadRot * -0.005;
                if (zza <= 0.0F) {
                    zza *= 0.25F;
                }

                // slow down movement by half
                Vec3 delta = this.getDeltaMovement();
                this.setDeltaMovement(delta.x / 2, delta.y / 2, delta.z / 2);

                if (zza > 0.0F) {
                    float f2 = Mth.sin(this.getYRot() * 0.017453292F);
                    float f3 = Mth.cos(this.getYRot() * 0.017453292F);

                    boolean isFlying = isFlying() && !isInWater();
                    double speed = this.getAttributeValue(Attributes.FLYING_SPEED) * (isInWater() ? 0.2F : 1.02F);
                    double groundSpeed = this.getAttributeValue(Attributes.MOVEMENT_SPEED) * (isInWater() ? 0.2F : 1F) / 8;
                    this.setDeltaMovement(delta.add(
                            (isFlying ? -speed : -groundSpeed / 2 + xHeadRotABS) * f2,
                            y, (isFlying ? speed : groundSpeed / 2 - xHeadRotABS) * f3));
                }

                if (this.isGoingUp()) {
                    this.setDeltaMovement(this.getDeltaMovement().add(0, zza > 0 ? 0.1 : 0.3, 0));
                } else if (this.isGoingDown()) {
                    this.setDeltaMovement(this.getDeltaMovement().add(0, zza > 0 ? -0.2 : -0.4, 0));
                }

                if (this.isControlledByLocalInstance()) {
                    this.setSpeed((float) this.getAttributeValue(Attributes.FLYING_SPEED));
                    super.travel(new Vec3(xxa, y, zza)); // pTravelVector.y
                } else if (pilot instanceof Player) {
                    this.setDeltaMovement(Vec3.ZERO);
                }
                this.tryCheckInsideBlocks();
            } else {
                this.setRotationState(0);
                super.travel(pTravelVector);
            }
        }
    }

    public boolean isFlapping() {
        float f = Mth.cos(this.flapTime * ((float) Math.PI * 2F));
        float f1 = Mth.cos(this.oFlapTime * ((float) Math.PI * 2F));
        return f1 <= -0.3F && f >= -0.3F;
    }

    public void onFlap() {
        if (!this.isSilent() && !isInWater() && !isInLava() && isFlying()) {
            if (shouldPlayFlapping()) {
                this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), getFlapSound(), this.getSoundSource(), getFlapVol(), getFlapPitch() + this.random.nextFloat() * 0.3F, false);
            }
        }
    }

    protected float getFlapVol() {
        return 6F;
    }

    protected float getFlapPitch() {
        return 0.4F;
    }

    protected SoundEvent getFlapSound() {
        return ModSounds.DEADLY_NADDER_FLAP.get();
    }


    /**
     * Plays living's sound at its position
     */
    @Override
    public void playAmbientSound() {
        if (!isFlying()) {
            SoundEvent soundevent = this.getAmbientSound();
            if (soundevent != null) {
                this.playSound(soundevent, this.getSoundVolume(), this.getVoicePitch());
            }
        }

    }

    protected SoundEvent getRoarSound() {
        return null;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.oFlapTime = this.flapTime;
        this.flapTime += getFlapSpeedPerFlyingDragon();
    }

    private float getFlapSpeedPerFlyingDragon() {
        if (this instanceof Gronckle) {
            return 0.09F;
        }

        if (this instanceof DeadlyNadder) {
            return 0.046F;
        }

        if (this instanceof NightFury) {
            return 0.042F;
        }

        if (this instanceof TripleStryke) {
            return 0.042F;
        }

        return 0.042F;
    }

    @Override
    public void tick() {
        super.tick();

        if (isDragonIncapacitated()) {
            this.setIsFlying(false);
        }
        if (isFlying() && isLandNavigator) {
            switchNavigator(false);
        }
        if (!isFlying() && !isLandNavigator) {
            switchNavigator(true);
        }
        if (isFlying()) {
            this.setNoGravity(true);
        } else {
            this.setNoGravity(false);
        }

        if (level.isClientSide()) {
            boolean playFlap = shouldPlayFlapping();
            int id = getId();
            ControlNetwork.INSTANCE.sendToServer(new MessageDragonFlapSounds(playFlap, id));
        }

        // decrement per tick
        if (getTicksFlyWandering() > -10) {
            setTicksFlyWandering(getTicksFlyWandering() - 1);
        }
    }

    protected void onGroundMechanics() {
        int start = 4;
        Vec3 pos = position();
        for (int xz1 = -start + 1; xz1 < start; xz1++) {
            for (int xz4 = -start + 1; xz4 < start; xz4++) {
                BlockPos pos1 = new BlockPos(pos.add(xz1, -1, xz1));
                BlockPos pos2 = new BlockPos(pos.add(-xz4, -1, xz4));
                BlockPos pos3 = new BlockPos(pos.add(xz1, -2, xz1));
                BlockPos pos4 = new BlockPos(pos.add(-xz4, -2, xz4));
                if (level.getBlockState(pos1).getMaterial().blocksMotion() || level.getBlockState(pos2).getMaterial().blocksMotion()
                        || level.getBlockState(pos3).getMaterial().blocksMotion() || level.getBlockState(pos4).getMaterial().blocksMotion()
                ) {
                    setIsDragonOnGround(true);
                } else {
                    setIsDragonOnGround(false);
                }
            }
        }
    }

    @Override
    public boolean rideableUnderWater() {
        return true;
    }

    public void circleEntity(Vec3 target, float radius, float speed, boolean direction, int circleFrame,
                             float offset, float moveSpeedMultiplier) {
        int directionInt = direction ? 1 : -1;
        double t = directionInt * circleFrame * 0.5 * speed / radius + offset;
        Vec3 movePos = target.add(radius * Math.cos(t), 0, radius * Math.sin(t));
        this.getNavigation().moveTo(movePos.x(), movePos.y(), movePos.z(), speed * moveSpeedMultiplier);
    }

    @Override
    public boolean canBeRiddenInWater(Entity rider) {
        return true;
    }

    /**
     * Will the dragon flap if there is water below
     *
     * @return
     */
    protected boolean groundDragon() {
        return false;
    }

}
