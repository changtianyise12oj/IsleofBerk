package com.GACMD.isleofberk.common.entity.entities.AI.pack;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;

public class SpeedStingerPackAI extends TargetGoal {

    public SpeedStingerPackAI(Mob pMob, boolean pMustSee) {
        super(pMob, pMustSee);
    }

    @Override
    public boolean canUse() {
        return false;
    }
}
