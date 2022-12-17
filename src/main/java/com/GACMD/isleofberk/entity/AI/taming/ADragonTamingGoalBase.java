package com.GACMD.isleofberk.entity.AI.taming;

import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

/**
 * The basis for how dragons are tamed, some of them are tamed in unique ways, babies are an exception, they can be tamed with food as soon as they hatch
 */
public class ADragonTamingGoalBase extends Goal {
    protected final ADragonBase dragon;
    protected final double speedModifier;
    protected double posX;
    protected double posY;
    protected double posZ;
    protected static final Component AIR_RIDE_SADDLE = new TranslatableComponent("iob.dragonAir.needSaddle");

    public ADragonTamingGoalBase(ADragonBase pDragon, double pSpeedModifier) {
        this.dragon = pDragon;
        this.speedModifier = pSpeedModifier;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return !dragon.isTame() && !dragon.isBaby();
    }
}
