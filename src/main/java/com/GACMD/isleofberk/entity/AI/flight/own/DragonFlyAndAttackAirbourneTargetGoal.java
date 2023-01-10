package com.GACMD.isleofberk.entity.AI.flight.own;

import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideable;
import com.GACMD.isleofberk.entity.AI.flight.ADragonBaseBaseFlyingRideableGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;

public class DragonFlyAndAttackAirbourneTargetGoal extends ADragonBaseBaseFlyingRideableGoal {

    public DragonFlyAndAttackAirbourneTargetGoal(ADragonBaseFlyingRideable dragon, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        super(dragon);
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public boolean canUse() {
        LivingEntity target = dragon.getTarget();
        if (target != null) {
            BlockPos solidPos = new BlockPos(target.getX(), target.getY() - 1, target.getZ());
            if (!level.getBlockState(solidPos).isSolidRender(level, solidPos)) {
                return false;
            }
        }


        // disabled
//        return target != null && dragon.distanceTo(target) < 6;
        return false;
    }

    @Override
    public void tick() {
        LivingEntity target = dragon.getTarget();
        if (target != null) {
            if (!dragon.isFlying()) {
                dragon.setIsFlying(true);
            }

            if (dragon.isFlying())
                dragon.getNavigation().moveTo(target, 1);
        }
    }
}
