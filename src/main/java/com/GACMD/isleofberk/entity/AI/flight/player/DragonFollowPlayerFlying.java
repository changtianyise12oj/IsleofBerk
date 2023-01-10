package com.GACMD.isleofberk.entity.AI.flight.player;

import com.GACMD.isleofberk.entity.AI.flight.ADragonBaseBaseFlyingRideableGoal;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class DragonFollowPlayerFlying extends ADragonBaseBaseFlyingRideableGoal {

    public DragonFollowPlayerFlying(ADragonBaseFlyingRideable dragonBaseFlyingRideable) {
        super(dragonBaseFlyingRideable);
    }

    @Override
    public boolean canUse() {
        LivingEntity owner = dragon.getOwner();
        if (owner != null) {

            if (dragon.isLeashed()) {
                return false;
            }

            // prevent small terrible terrors from flying towards the player and catching it
//            if (!dragon.canBeMounted()) {
//                return false;
//            }


            if (dragon.getControllingPassenger() instanceof Player || dragon.getVehicle() != null) {
                return false;
            }

            // land to ground instead of catch the player

            // if owner is on ground land next to owner
            return dragon.isDragonFollowing();
        }
        return false;
    }

    @Override
    public void tick() {
        // catch owner in flight if possible, only when it is standing do so.
//        if (!dragon.isFlying()) {
        dragon.setIsFlying(true);
//        }
        LivingEntity owner = dragon.getOwner();
        if (owner != null) {
            // don't catch if owner is too far away
            double followRange = 35;

            if (owner.fallDistance > 4 && !owner.isFallFlying()) {
                if (dragon.distanceTo(owner) < followRange) {
                    // mount owner if close enough, otherwise move to owner
                    if (dragon.distanceTo(owner) <= dragon.getBbWidth() * 1.4 || dragon.distanceTo(owner) <= dragon.getBbHeight() * 1.0 && !owner.isShiftKeyDown() && dragon.isFlying()) {
                        owner.startRiding(dragon);
                    } else {
                        // y movement is too slow
                        dragon.getNavigation().moveTo(owner.getX(), owner.getY() - 5, owner.getZ(), 4F);
                    }
                }
            } else {
                Vec3 movePos = new Vec3(owner.getX(), owner.getY() + 4, owner.getZ());
                // now count the index and make them spread, only happens when the entire class AI kicks in
                tailingDragons.put(owner.getUUID(), dragon);
                dragon.getNavigation().moveTo(movePos.x() + (tailingDragons.size() * 3), movePos.y(), movePos.z() + (tailingDragons.size() * 3), 4);
            }
        }
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
}
