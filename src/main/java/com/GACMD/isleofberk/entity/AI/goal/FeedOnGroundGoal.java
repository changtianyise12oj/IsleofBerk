package com.GACMD.isleofberk.entity.AI.goal;

import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;

public class FeedOnGroundGoal extends Goal {

    ADragonBase dragonBase;
    Level level;

    public FeedOnGroundGoal(ADragonBase dragonBase) {
        this.dragonBase = dragonBase;
        this.level=dragonBase.level;
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    public void start() {
//        dragonBase.getNavigation().moveTo();
    }

//    @Override
//    public void tick() {
//        Pig pig = level.getNearestEntity(level.getEntitiesOfClass(Pig.class, getTargetSearchArea(14)),
//                TargetingConditions.forNonCombat(), this, this.getX(), this.getEyeY(), this.getZ());
//        ItemEntity itemEntity = level.getNearestEntity(level.getEntitiesOfClass(ItemEntity.class, dragonBase.getTargetSearchArea(14)),
//                TargetingConditions.forNonCombat(), this, dragonBase.getX(), dragonBase.getEyeY(), dragonBase.getZ());
//        super.tick();
//    }
}
