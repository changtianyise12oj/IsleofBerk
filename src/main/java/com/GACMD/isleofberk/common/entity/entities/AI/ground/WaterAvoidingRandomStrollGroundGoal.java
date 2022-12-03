package com.GACMD.isleofberk.common.entity.entities.AI.ground;

import com.GACMD.isleofberk.common.entity.entities.base.ADragonBaseFlyingRideable;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;

public class WaterAvoidingRandomStrollGroundGoal extends WaterAvoidingRandomStrollGoal {
    protected final ADragonBaseFlyingRideable dragon;

    public WaterAvoidingRandomStrollGroundGoal(ADragonBaseFlyingRideable pMob, double pSpeedModifier, float probability) {
        super(pMob, pSpeedModifier, probability);
        this.dragon=pMob;
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    @Override
    public boolean canUse() {
        if (this.dragon.isVehicle() && dragon.isFlying() && !this.dragon.isOnGround() && !(dragon.getNavigation() instanceof GroundPathNavigation)) {
            return false;
        } else {
            return super.canUse();
        }
    }
}