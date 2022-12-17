package com.GACMD.isleofberk.common.entity.entities.AI;

import com.GACMD.isleofberk.common.entity.entities.base.dragon.ADragonBase;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class IOBLookAtPlayerGoal extends Goal {
    public static final float DEFAULT_PROBABILITY = 0.02F;
    protected final ADragonBase dragon;
    @Nullable
    protected Entity lookAt;
    protected final float lookDistance;
    private int lookTime;
    protected final float probability;
    private final boolean onlyHorizontal;
    protected final Class<? extends LivingEntity> lookAtType;
    protected final TargetingConditions lookAtContext;

    public IOBLookAtPlayerGoal(ADragonBase pMob, Class<? extends LivingEntity> pLookAtType, float pLookDistance) {
        this(pMob, pLookAtType, pLookDistance, 0.02F);
    }

    public IOBLookAtPlayerGoal(ADragonBase pMob, Class<? extends LivingEntity> pLookAtType, float pLookDistance, float pProbability) {
        this(pMob, pLookAtType, pLookDistance, pProbability, false);
    }

    public IOBLookAtPlayerGoal(ADragonBase pMob, Class<? extends LivingEntity> pLookAtType, float pLookDistance, float pProbability, boolean pOnlyHorizontal) {
        this.dragon = pMob;
        this.lookAtType = pLookAtType;
        this.lookDistance = pLookDistance;
        this.probability = pProbability;
        this.onlyHorizontal = pOnlyHorizontal;
        this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        if (pLookAtType == Player.class) {
            this.lookAtContext = TargetingConditions.forNonCombat().range((double)pLookDistance).selector((p_25531_) -> {
                return EntitySelector.notRiding(pMob).test(p_25531_);
            });
        } else {
            this.lookAtContext = TargetingConditions.forNonCombat().range((double)pLookDistance);
        }

    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean canUse() {
        if (this.dragon.getRandom().nextFloat() >= this.probability) {
            return false;
        } else {
            if (this.dragon.getTarget() != null) {
                this.lookAt = this.dragon.getTarget();
            }

            if (this.lookAtType == Player.class) {
                this.lookAt = this.dragon.level.getNearestPlayer(this.lookAtContext, this.dragon, this.dragon.getX(), this.dragon.getEyeY(), this.dragon.getZ());
            } else {
                this.lookAt = this.dragon.level.getNearestEntity(this.dragon.level.getEntitiesOfClass(this.lookAtType, this.dragon.getBoundingBox().inflate((double)this.lookDistance, 3.0D, (double)this.lookDistance), (p_148124_) -> {
                    return true;
                }), this.lookAtContext, this.dragon, this.dragon.getX(), this.dragon.getEyeY(), this.dragon.getZ());
            }

            return this.lookAt != null && !dragon.shouldStopMoving();
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean canContinueToUse() {
        if (!this.lookAt.isAlive()) {
            return false;
        } else if (this.dragon.distanceToSqr(this.lookAt) > (double)(this.lookDistance * this.lookDistance)) {
            return false;
        } else {
            return this.lookTime > 0;
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void start() {
        this.lookTime = this.adjustedTickDelay(40 + this.dragon.getRandom().nextInt(40));
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void stop() {
        this.lookAt = null;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        if (this.lookAt.isAlive()) {
            double d0 = this.onlyHorizontal ? this.dragon.getEyeY() : this.lookAt.getEyeY();
            this.dragon.getLookControl().setLookAt(this.lookAt.getX(), d0, this.lookAt.getZ());
            --this.lookTime;
        }
    }
}