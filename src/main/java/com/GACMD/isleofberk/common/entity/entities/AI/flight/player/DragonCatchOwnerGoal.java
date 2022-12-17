package com.GACMD.isleofberk.common.entity.entities.AI.flight.player;

import com.GACMD.isleofberk.common.entity.entities.AI.flight.ADragonBaseBaseFlyingRideableGoal;
import com.GACMD.isleofberk.common.entity.entities.base.dragon.ADragonBaseFlyingRideable;
import net.minecraft.world.entity.LivingEntity;

public class DragonCatchOwnerGoal extends ADragonBaseBaseFlyingRideableGoal {

    public DragonCatchOwnerGoal(ADragonBaseFlyingRideable dragonBaseFlyingRideable) {
        super(dragonBaseFlyingRideable);
    }

    @Override
    public boolean canUse() {
        LivingEntity owner = dragon.getOwner();
        if (owner != null) {

            if (dragon.isLeashed()) {
                return false;
            }

            if (dragon.getControllingPassenger() != null) {
                return false;
            }

            // prevent small terrible terrors from flying towards the player and catching it
            if(!dragon.canBeMounted()) {
                return false;
            }

            // don't catch if owner has a working Elytra equipped
            if (owner.isFallFlying()) {
                return false;
            }

            if (dragon.isVehicle() || dragon.getVehicle() != null) {
                return false;
            }

            if(!dragon.isDragonFollowing()) {
                return false;
            }

            // land to ground instead of catch the player
            // if owner is on ground land next to owner
            return owner.fallDistance > 4;
        }
        return false;
    }

    @Override
    public void tick() {
        // catch owner in flight if possible, only when it is standing do so.
        if (!dragon.isFlying()) {
            dragon.setIsFlying(true);
        }
        LivingEntity owner = dragon.getOwner();
        if (owner != null) {
            // don't catch if owner is too far away
            double followRange = 35;

            if (dragon.distanceTo(owner) < followRange) {
                // mount owner if close enough, otherwise move to owner
                if (dragon.distanceTo(owner) <= dragon.getBbWidth() * 1.5 || dragon.distanceTo(owner) <= dragon.getBbHeight() * 1.5 && !owner.isShiftKeyDown() && dragon.isFlying()) {
                    owner.startRiding(dragon);
                } else {
                    // y movement is too slow
                    dragon.getNavigation().moveTo(owner.getX(), owner.getY() - 5, owner.getZ(), 8);
                }
            }
        }
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
}
