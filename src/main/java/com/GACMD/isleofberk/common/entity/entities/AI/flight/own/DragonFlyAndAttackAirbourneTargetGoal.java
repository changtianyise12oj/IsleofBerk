package com.GACMD.isleofberk.common.entity.entities.AI.flight.own;

import com.GACMD.isleofberk.common.entity.entities.AI.flight.ADragonBaseBaseFlyingRideableGoal;
import com.GACMD.isleofberk.common.entity.entities.base.ADragonBaseFlyingRideable;
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
