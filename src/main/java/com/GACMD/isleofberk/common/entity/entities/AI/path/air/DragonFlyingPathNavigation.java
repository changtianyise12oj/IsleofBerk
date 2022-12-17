package com.GACMD.isleofberk.common.entity.entities.AI.path.air;

import com.GACMD.isleofberk.common.entity.entities.base.dragon.ADragonBaseFlyingRideable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.level.Level;

public class DragonFlyingPathNavigation extends FlyingPathNavigation {

    ADragonBaseFlyingRideable dragon;

    public DragonFlyingPathNavigation(ADragonBaseFlyingRideable dragon, Level level) {
        super(dragon, level);
        this.dragon = dragon;
    }

    public boolean isStableDestination(BlockPos pos) {
        return true;
    }

    protected boolean canUpdatePath() {
        return !dragon.isPassenger() && !dragon.isVehicle() && !dragon.isDragonSitting();

    }

//    @Override
//    public void tick() {
//        if (!isDone() && canUpdatePath()) {
//            BlockPos target = getTargetPos();
//            if (target != null) {
//                dragon.getMoveControl().setWantedPosition(target.getX(), target.getY(), target.getZ(), speedModifier);
//                maxDistanceToWaypoint = dragon.getBbWidth() * dragon.getBbWidth() * 75 * 75;
//                Vec3 entityPos = getTempMobPos();
//                if (target.distSqr(entityPos.x, entityPos.y, entityPos.z, true) <= maxDistanceToWaypoint)
//                    path = null;
//            }
//        }
//    }


    @Override
    public void tick() {
        ++tick;
    }

    @Override
    public boolean moveTo(double pX, double pY, double pZ, double pSpeed) {
        mob.getMoveControl().setWantedPosition(pX,pY,pZ,pSpeed);
        return true;
    }

    @Override
    public boolean moveTo(Entity pEntity, double pSpeed) {
        mob.getMoveControl().setWantedPosition(pEntity.getX(), pEntity.getY() + 5, pEntity.getZ(), pSpeed);
        return true;
    }
}
