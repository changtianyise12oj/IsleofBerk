package com.GACMD.isleofberk.entity.AI.path.air;

import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideable;
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
