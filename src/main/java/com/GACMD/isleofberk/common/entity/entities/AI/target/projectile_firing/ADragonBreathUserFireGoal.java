package com.GACMD.isleofberk.common.entity.entities.AI.target.projectile_firing;

import com.GACMD.isleofberk.common.entity.entities.base.ADragonBaseFlyingRideableBreathUser;

/**
 * Continues breathing projectiles without the need of player input controls
 */
public class ADragonBreathUserFireGoal extends ADragonBaseFire {
    ADragonBaseFlyingRideableBreathUser dragon;


    public ADragonBreathUserFireGoal(ADragonBaseFlyingRideableBreathUser dragon) {
        super(dragon);
        this.dragon = dragon;
    }

    @Override
    public boolean canUse() {
        float dist = dragon.distanceTo(target);
        if(dist > 4 && dist < 8) {
            return true;
        }
        return super.canUse();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
    }
}
