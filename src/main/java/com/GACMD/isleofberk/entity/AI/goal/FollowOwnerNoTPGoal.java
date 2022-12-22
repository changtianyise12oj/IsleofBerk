package com.GACMD.isleofberk.entity.AI.goal;

//edit of vanilla FollowOwnerGoal

import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import com.GACMD.isleofberk.entity.dragons.speedstinger.SpeedStinger;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

import java.util.EnumSet;

public class FollowOwnerNoTPGoal extends Goal {

    private final ADragonBase dragon;
    private LivingEntity owner;
    private final LevelReader level;
    private final double speedModifier;
    private final PathNavigation navigation;
    private int timeToRecalcPath;
    private final float stopDistance;
    private final float startDistance;
    private float oldWaterCost;
    private final boolean canFly;

    public FollowOwnerNoTPGoal(ADragonBase dragon, double pSpeedModifier, float pStartDistance, float pStopDistance, boolean pCanFly) {
        this.dragon = dragon;
        this.level = dragon.level;
        this.speedModifier = pSpeedModifier;
        this.navigation = dragon.getNavigation();
        this.startDistance = pStartDistance;
        this.stopDistance = pStopDistance;
        this.canFly = pCanFly;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean canUse() {
        LivingEntity livingentity = this.dragon.getOwner();
        if (livingentity == null) {
            return false;
        } else if (livingentity.isSpectator()) {
            return false;
        } else if (this.dragon.isDragonSitting()) {
            return false;
        } else if (this.dragon.distanceToSqr(livingentity) < (double) (this.startDistance * this.startDistance)) {
            return false;
        } else if (this.dragon.distanceToSqr(livingentity) > 250.0D) {
            return false;
        } else if (!dragon.isDragonFollowing()) {
            return false;
        } else {
            this.owner = livingentity;
            return dragon.isDragonFollowing();
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean canContinueToUse() {
        if (this.navigation.isDone()) {
            return false;
        } else if (this.dragon.isDragonSitting()) {
            return false;
        } else {
            return !(this.dragon.distanceToSqr(this.owner) <= (double) (this.stopDistance * this.stopDistance));
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void start() {
        this.timeToRecalcPath = 0;
        if (!(dragon instanceof SpeedStinger)) {
            this.oldWaterCost = this.dragon.getPathfindingMalus(BlockPathTypes.WATER);
            this.dragon.setPathfindingMalus(BlockPathTypes.WATER, 1.0F);
        }
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void stop() {
        this.owner = null;
        this.navigation.stop();
        if (!(dragon instanceof SpeedStinger)) {
            this.dragon.setPathfindingMalus(BlockPathTypes.WATER, this.oldWaterCost);
        }
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        this.dragon.getLookControl().setLookAt(this.owner, 10.0F, (float) this.dragon.getMaxHeadXRot());
        if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = this.adjustedTickDelay(10);
            if (!this.dragon.isLeashed() && !this.dragon.isPassenger()) {
                if (this.dragon.distanceToSqr(this.owner) >= 250.0D) {
                    this.navigation.stop();
                } else {
                    this.navigation.moveTo(this.owner, this.speedModifier);
                }
            }
        }
    }
}
