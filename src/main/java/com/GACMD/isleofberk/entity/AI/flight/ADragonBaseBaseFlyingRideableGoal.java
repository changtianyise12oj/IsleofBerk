package com.GACMD.isleofberk.entity.AI.flight;

import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideable;
import com.GACMD.isleofberk.entity.AI.goal.ADragonRideableUtilityBaseGoal;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class ADragonBaseBaseFlyingRideableGoal extends ADragonRideableUtilityBaseGoal {

    protected Map<UUID, ADragonBaseFlyingRideable> tailingDragons = new HashMap<>();
    protected ADragonBaseFlyingRideable dragon;

    public ADragonBaseBaseFlyingRideableGoal(ADragonBaseFlyingRideable dragon) {
        super(dragon);
        this.dragon = dragon;
        this.level=dragon.level;
        this.owner=dragon.getOwner();
    }

    @Override
    public boolean canUse() {
        return true;
    }

}
