package com.GACMD.isleofberk.entity.AI.goal;

import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;

import java.util.Random;

public class ADragonBaseGoal extends Goal {

    protected ADragonBase dragon;
    protected Level level;
    protected Random random = new Random();

    public ADragonBaseGoal(ADragonBase dragon) {
        this.dragon = dragon;
        this.level=dragon.level;
    }

    @Override
    public boolean canUse() {
        return false;
    }
}
