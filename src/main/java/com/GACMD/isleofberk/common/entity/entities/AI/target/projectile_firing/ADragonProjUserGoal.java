package com.GACMD.isleofberk.common.entity.entities.AI.target.projectile_firing;

import com.GACMD.isleofberk.common.entity.entities.AI.ADragonBaseGoal;
import com.GACMD.isleofberk.common.entity.entities.base.dragon.ADragonBaseFlyingRideableProjUser;

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
