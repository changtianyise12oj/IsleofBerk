package com.GACMD.isleofberk.common.entity.entities.AI.flight.player;

import com.GACMD.isleofberk.common.entity.entities.AI.flight.ADragonBaseBaseFlyingRideableGoal;
import com.GACMD.isleofberk.common.entity.entities.base.dragon.ADragonBaseFlyingRideable;
import net.minecraft.world.entity.LivingEntity;

/**
 * Set's the flying parameters, but the flight controls and movement is done by travel().
 */
public class AIDragonRide extends ADragonBaseBaseFlyingRideableGoal {

    LivingEntity owner;

    public AIDragonRide(ADragonBaseFlyingRideable dragonBase) {
        super(dragonBase);
        this.owner = dragonBase.getOwner(); // last ridden player
    }

    @Override
    public boolean canUse() {
        return dragon.isVehicle();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse();
    }

    @Override
    public void tick() {
//        if (!dragon.isDragonOnGround() || dragon.isInWater() || dragon.isWaterBelow()) {
//            dragon.setIsFlying(true);
//        } else {
//            dragon.setIsFlying(false);
//        }

        int threshhold = 6;
        if (dragon.getInAirTicksVehicle() > threshhold || dragon.isGoingUp()) {
            dragon.setIsFlying(true);
        } else {
            dragon.setIsFlying(false);
        }

        if (dragon.isGoingUp()) {
            dragon.setInAirTicksVehicle(threshhold);
        }

        // increases the liftOff amount before flying
        if (dragon.isFlying() || !dragon.isDragonOnGround() || dragon.isInWater() || dragon.isWaterBelow()) {
            dragon.setInAirTicksVehicle(dragon.getInAirTicksVehicle() + 1);
        }

        // reset liftOff amount after landing
        if (dragon.isDragonOnGround() && !dragon.isInWater() && !dragon.isWaterBelow()) {
            dragon.setInAirTicksVehicle(0);
        }
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
}
