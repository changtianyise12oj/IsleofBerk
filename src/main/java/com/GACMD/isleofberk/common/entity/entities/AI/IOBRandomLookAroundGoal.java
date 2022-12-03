package com.GACMD.isleofberk.common.entity.entities.AI;

import com.GACMD.isleofberk.common.entity.entities.base.ADragonBase;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class IOBRandomLookAroundGoal extends Goal {

    private final ADragonBase dragon;
    private double relX;
    private double relZ;
    private int lookTime;

    public IOBRandomLookAroundGoal(ADragonBase pMob) {
        this.dragon = pMob;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean canUse() {
        return this.dragon.getRandom().nextFloat() < 0.02F && !dragon.shouldStopMoving();
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean canContinueToUse() {
        return this.lookTime >= 0;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void start() {
        double d0 = (Math.PI * 2D) * this.dragon.getRandom().nextDouble();
        this.relX = Math.cos(d0);
        this.relZ = Math.sin(d0);
        this.lookTime = 20 + this.dragon.getRandom().nextInt(20);
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        --this.lookTime;
        this.dragon.getLookControl().setLookAt(this.dragon.getX() + this.relX, this.dragon.getEyeY(), this.dragon.getZ() + this.relZ);
    }
}