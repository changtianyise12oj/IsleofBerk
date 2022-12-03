package com.GACMD.isleofberk.common.entity.entities.AI;

import com.GACMD.isleofberk.common.entity.entities.base.ADragonBase;
import net.minecraft.world.entity.LivingEntity;

public class DragonSleepGoal extends ADragonBaseGoal {

    public DragonSleepGoal(ADragonBase dragon) {
        super(dragon);
    }

    @Override
    public boolean canUse() {
        LivingEntity lastHurt = dragon.getLastHurtByMob();
        LivingEntity target = dragon.getTarget();
        int i = dragon.getLastHurtByMobTimestamp();
        boolean canSleep = lastHurt == null && i < 2500 && target == null;
//        if (!canSleep) {
//            return false;
//        }

        if (dragon.isVehicle()) {
            return false;
        }

        if (dragon.getVehicle() == null) {
            return false;
        }

        if (dragon.getSleepDisturbTicks() > 3) {
            return false;
        }

        // only sleep when sitting or wandering
        if (dragon.isDragonFollowing()) {
            return false;
        }

        return dragon.isOnGround() && !dragon.isInWater() && !dragon.isWaterBelow();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        // level is night is not working
        dragon.setIsDragonSleeping(false);
//        if (!dragon.isNocturnal()) {
//            // get light level instead of daytime
//            // nocturnal dragons sleep on bright areas
//            if (level.isNight()) {
////                    if (random.nextInt(50) == 1)
//                dragon.setIsDragonSleeping(true);
//            } else {
//                dragon.setIsDragonSleeping(false);
//            }
//        } else {
//            if (level.isDay()) {
////                    if (random.nextInt(50) == 1)
//                dragon.setIsDragonSleeping(true);
//            } else {
//                dragon.setIsDragonSleeping(false);
//            }
//        }
    }

//    @Override
//    public void stop() {
//        dragon.setIsDragonSleeping(false);
//    }
}


