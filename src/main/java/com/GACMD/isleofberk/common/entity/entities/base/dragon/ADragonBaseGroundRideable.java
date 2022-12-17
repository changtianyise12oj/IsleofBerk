package com.GACMD.isleofberk.common.entity.entities.base.dragon;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;

public class ADragonBaseGroundRideable extends ADragonRideableUtility implements PlayerRideableJumping, IAnimatable {

    private static final EntityDataAccessor<Boolean> IS_RUNNING = SynchedEntityData.defineId(ADragonBaseGroundRideable.class, EntityDataSerializers.BOOLEAN);

    protected float playerJumpPendingScale;
    protected boolean isJumping;

    protected ADragonBaseGroundRideable(EntityType<? extends ADragonBase> animal, Level world) {
        super(animal, world);
    }

    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        return false;
    }

    @Override
    public void positionRider(Entity pPassenger) {
        super.positionRider(pPassenger);
        pPassenger.setPos(this.getX(), this.getY() + 1.5, this.getZ());
    }


    @Override
    public void onPlayerJump(int pJumpPower) {
        if (this.isVehicle()) {
            if (pJumpPower < 0) {
                pJumpPower = 0;
            }

            if (pJumpPower >= 90) {
                this.playerJumpPendingScale = 1.0F;
            } else {
                this.playerJumpPendingScale = 0.4F + 0.4F * (float) pJumpPower / 90.0F;
            }

        }
    }

    @Override
    public boolean canJump() {
        return isOnJumpHeight() || !isInWater();
    }

    @Override
    public void handleStartJump(int pJumpPower) {

    }

    @Override
    public void handleStopJump() {

    }

    public double getCustomJump() {
        return this.getAttributeValue(Attributes.JUMP_STRENGTH);
    }

    public boolean isJumping() {
        return this.isJumping;
    }

    public void setIsJumping(boolean pJumping) {
        this.isJumping = pJumping;
    }

    @Override
    protected boolean isAffectedByFluids() {
        return true;
    }

    @Override
    public @NotNull Vec3 getFluidFallingAdjustedMovement(double gravityValue, boolean falling, Vec3 deltaMovement) {
        if (!this.isNoGravity() && !this.isSprinting()) {
            if (falling && Math.abs(deltaMovement.y - 0.005D) >= 0.003D && Math.abs(deltaMovement.y - gravityValue / 16.0D) < 0.003D) {
                gravityValue = -0.003D;
            } else {
                gravityValue = deltaMovement.y - gravityValue / 16.0D;
            }

            return new Vec3(deltaMovement.x, gravityValue, deltaMovement.z);
        } else {
            return deltaMovement;
        }
    }

    public void travel(@NotNull Vec3 pTravelVector) {
        if (this.isAlive()) {
            if (this.isVehicle() && this.canBeControlledByRider()) {
                LivingEntity pilot = (LivingEntity) this.getControllingPassenger();
                assert pilot != null;
                pilot.setYBodyRot(pilot.getYHeadRot());
                this.setYRot(pilot.getYRot());
                this.yRotO = this.getYRot();
                this.setXRot(pilot.getXRot() * 0.5F);
                this.setRot(this.getYRot(), this.getXRot());
                this.yBodyRot = this.getYRot();
                this.yHeadRot = this.yBodyRot;
                float f = pilot.xxa * 0.5F;
                float f1 = pilot.zza;
                if (f1 <= 0.0F) {
                    f1 *= 0.25F;
                }

                if (this.playerJumpPendingScale > 0.0F && !this.isJumping() && this.isDragonOnGround()) {
                    double d0 = this.getCustomJump() * (double) this.playerJumpPendingScale * (double) this.getBlockJumpFactor();
                    double d1 = d0 + this.getJumpBoostPower();
                    Vec3 vec3 = this.getDeltaMovement();
                    this.setDeltaMovement(vec3.x, d1, vec3.z);
                    this.setIsJumping(true);
                    this.hasImpulse = true;
                    ForgeHooks.onLivingJump(this);
                    if (f1 > 0.0F) {
                        float f2 = Mth.sin(this.getYRot() * 0.017453292F);
                        float f3 = Mth.cos(this.getYRot() * 0.017453292F);
                        this.setDeltaMovement(this.getDeltaMovement().add((-1.5F * f2 * this.playerJumpPendingScale), 0.0D, (1.5F * f3 * this.playerJumpPendingScale)));
                    }
                    this.playerJumpPendingScale = 0.0F;
                }

                this.flyingSpeed = this.getSpeed() * 0.1F;
                if (this.isControlledByLocalInstance()) {
                    this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
                    super.travel(new Vec3(f, pTravelVector.y, f1));
                } else if (pilot instanceof Player) {
                    this.setDeltaMovement(Vec3.ZERO);
                }

                if (this.isDragonOnGround()) {
                    this.playerJumpPendingScale = 0.0F;
                    this.setIsJumping(false);
                }

                this.tryCheckInsideBlocks();
            } else {
                this.flyingSpeed = 0.02F;
                super.travel(pTravelVector);
            }
        }
    }
}
