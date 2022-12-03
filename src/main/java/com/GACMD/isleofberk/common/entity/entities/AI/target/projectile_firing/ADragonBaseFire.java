package com.GACMD.isleofberk.common.entity.entities.AI.target.projectile_firing;

import com.GACMD.isleofberk.common.entity.entities.AI.ADragonBaseGoal;
import com.GACMD.isleofberk.common.entity.entities.base.ADragonRideableUtility;
import net.minecraft.world.entity.LivingEntity;

public class ADragonBaseFire extends ADragonBaseGoal {

    LivingEntity target;

    public ADragonBaseFire(ADragonRideableUtility dragon) {
        super(dragon);
        target=dragon.getTarget();
    }

    @Override
    public boolean canUse() {
        if(target == null) {
            return false;
        }

        return true;
    }
}
