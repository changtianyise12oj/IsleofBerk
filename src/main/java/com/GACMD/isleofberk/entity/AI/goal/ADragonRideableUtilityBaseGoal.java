package com.GACMD.isleofberk.entity.AI.goal;

import com.GACMD.isleofberk.entity.base.dragon.ADragonRideableUtility;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;


public class ADragonRideableUtilityBaseGoal extends ADragonBaseGoal {

    protected LivingEntity owner;
    protected ADragonRideableUtility dragon;

    public ADragonRideableUtilityBaseGoal(ADragonRideableUtility dragon) {
        super(dragon);
        this.level = dragon.level;
        this.dragon=dragon;
        this.owner = dragon.getOwner();
    }

    @Override
    public boolean canUse() {
        return false;
    }

    protected int getFollowRange() {
        return 70;
    }

    protected boolean tryMoveToBlockPos(Vec3 pos, double speed) {
        return dragon.getNavigation().moveTo(pos.x() + 0.5, pos.y() + 0.5, pos.z() + 0.5, speed);
    }
}
