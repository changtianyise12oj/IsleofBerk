package com.GACMD.isleofberk.entity.AI.flight.player;

import com.GACMD.isleofberk.entity.AI.flight.ADragonBaseBaseFlyingRideableGoal;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideable;
import com.GACMD.isleofberk.util.Util;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class DragonFollowPlayerFlying extends ADragonBaseBaseFlyingRideableGoal {

    private int xDist;
    private int yDist;
    private int zDist;

    public DragonFollowPlayerFlying(ADragonBaseFlyingRideable dragonBaseFlyingRideable, int xDist, int yDist, int zDist) {
        super(dragonBaseFlyingRideable);
        this.xDist=xDist;
        this.yDist=yDist;
        this.zDist=zDist;
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
        LivingEntity target = dragon.getTarget();
        dragon.setIsFlying(true);
//        }
        LivingEntity owner = dragon.getOwner();
        if (target != null && dragon.distanceTo(target) < 8) {
            dragon.getNavigation().moveTo(target.getX(), target.getY(), target.getZ(), 4);
        } else {
            if (owner != null) {
                // don't catch if owner is too far away
                double followRange = 35;

                if (owner.fallDistance > 4 && !owner.isFallFlying() && dragon.canBeMounted()) {
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
                    Vec3 movePos = new Vec3(owner.getX(), owner.getY() + yDist, owner.getZ());
                    // now count the index and make them spread, only happens when the entire class AI kicks in
                    tailingDragons.put(owner.getUUID(), dragon);
                    dragon.getNavigation().moveTo(movePos.x() + (tailingDragons.size() * xDist), movePos.y(), movePos.z() + (tailingDragons.size() * zDist), 4);
                }

                dragon.setIsDragonDisabled(false);
            } else {
                dragon.getNavigation().moveTo(dragon.getX(), dragon.getY(), dragon.getZ(), 4);
                dragon.setIsDragonDisabled(true);
                dragon.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, Util.secondsToTicks(1)));
            }
        }
    }

    @Override
    public void stop() {
        dragon.setIsDragonDisabled(false);
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
}
