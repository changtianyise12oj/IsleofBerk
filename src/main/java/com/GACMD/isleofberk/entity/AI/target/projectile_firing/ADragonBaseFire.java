package com.GACMD.isleofberk.entity.AI.target.projectile_firing;

import com.GACMD.isleofberk.entity.base.dragon.ADragonRideableUtility;
import com.GACMD.isleofberk.entity.AI.goal.ADragonBaseGoal;
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
