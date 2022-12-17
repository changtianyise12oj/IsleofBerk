package com.GACMD.isleofberk.entity.AI.target.projectile_firing;

import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideableProjUser;
import com.GACMD.isleofberk.entity.AI.goal.ADragonBaseGoal;

/**
 * Firing projectiles without the need of player input controls
 */
public class ADragonProjUserGoal extends ADragonBaseGoal {

    ADragonBaseFlyingRideableProjUser dragon;

    public ADragonProjUserGoal(ADragonBaseFlyingRideableProjUser dragon) {
        super(dragon);
        this.dragon = dragon;
    }
}
