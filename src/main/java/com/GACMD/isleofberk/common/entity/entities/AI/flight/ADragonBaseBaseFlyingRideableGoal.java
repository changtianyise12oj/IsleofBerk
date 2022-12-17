package com.GACMD.isleofberk.common.entity.entities.AI.flight;

import com.GACMD.isleofberk.common.entity.entities.AI.ADragonRideableUtilityBaseGoal;
import com.GACMD.isleofberk.common.entity.entities.base.dragon.ADragonBaseFlyingRideable;

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
