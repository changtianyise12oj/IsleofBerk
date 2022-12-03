package com.GACMD.isleofberk.common.entity.entities.AI.water;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class DragonFloatGoal extends Goal {
    private final Mob mob;

    public DragonFloatGoal(Mob pMob) {
        this.mob = pMob;
        this.setFlags(EnumSet.of(Flag.JUMP));
        pMob.getNavigation().setCanFloat(true);
    }


    public boolean canUse() {
        return this.mob.isInWater() && this.mob.getFluidHeight(FluidTags.WATER) > this.mob.getFluidJumpThreshold() || this.mob.isInLava();
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }

    public void tick() {
        if (this.mob.getRandom().nextFloat() < 0.2F) {
            this.mob.getJumpControl().jump();
        }

    }
}
