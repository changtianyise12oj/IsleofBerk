package com.GACMD.isleofberk.common.entity.entities.AI.flight.own;

import com.GACMD.isleofberk.common.entity.entities.AI.flight.ADragonBaseBaseFlyingRideableGoal;
import com.GACMD.isleofberk.common.entity.entities.base.dragon.ADragonBaseFlyingRideable;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

/**
 * Randomly start flying then trigger flight AI by increasing it's ticks wandering
 */
public class UntamedDragonCircleFlightGoal extends ADragonBaseBaseFlyingRideableGoal {

    Vec3 posToCircle;

    public UntamedDragonCircleFlightGoal(ADragonBaseFlyingRideable dragon) {
        super(dragon);
    }

    @Override
    public boolean canUse() {
        if (dragon.isTame()) {
            return false;
        }
        if (dragon.getControllingPassenger() != null) {
            return false;
        }
        if (dragon.getVehicle() != null || dragon.isVehicle()) {
            return false;
        }

        if(dragon.isDragonIncapacitated()) {
            return false;
        }

        if(dragon.isDragonSleeping()) {
            return false;
        }

        // figure a fly on their own flight attack
        if(dragon.getTarget() != null) {
            return false;
        }

        if(!dragon.canBeMounted()) {
            return false;
        }

        if(dragon.shouldStopMoving()) {
            return false;
        }

        // make taming easier
        if(dragon.getFoodTameLimiterBar() > 0) {
            return false;
        }

        // make taming easier
        if(dragon.getPhase1Progress() > 0) {
            return false;
        }

        return true;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    public Vec3 getPosToCircle() {
        return posToCircle;
    }

    public void setPosToCircle(Vec3 posToCircle) {
        this.posToCircle = posToCircle;
    }

    @Override
    public void tick() {
        if (!dragon.isFlying() && dragon.getRandom().nextInt(3000) == 1) {
            dragon.setTicksFlyWandering(500);
        }

        Vec3 targetPos = getPosToCircle();
        if (dragon.isDragonOnGround())
            setPosToCircle(DefaultRandomPos.getPos(dragon, 7, 2));

        if (!dragon.isFlying() && dragon.getTicksFlyWandering() > 1) {
            dragon.setIsFlying(true);
        }
        if (dragon.getTicksFlyWandering() > 3 && targetPos != null) {
            // that's why it's facing the ground
            // another tweak to the path direction

            // make varying methods per dragon, gronckles and terrible terrors don't need to fly that high and wide
            dragon.circleEntity(new Vec3(targetPos.x(), targetPos.y() + 40, targetPos.z()), 30, 1, true, dragon.tickCount, 1, 1);
        }
    }
}
