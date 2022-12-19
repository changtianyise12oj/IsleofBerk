package com.GACMD.isleofberk.entity.AI.flight.player;

import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideable;
import com.GACMD.isleofberk.entity.AI.flight.ADragonBaseBaseFlyingRideableGoal;
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
        if (dragon.isFlying() || !dragon.isDragonOnGround()) {
            dragon.setInAirTicksVehicle(dragon.getInAirTicksVehicle() + 1);
        }

        // reset liftOff amount after landing
        if (dragon.isDragonOnGround()) {
            dragon.setInAirTicksVehicle(0);
        }

//        if(dragon.isInWater()) {
//            if(dragon.ticksUnderwater < 52) {
//                dragon.ticksUnderwater++;
//            }
//        } else {
//            dragon.ticksUnderwater=0;
//        }
//
//        if(dragon.isFlying() && dragon.ticksUnderwater > 22) {
//            dragon.setIsFlying(false);
//        }
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
}
