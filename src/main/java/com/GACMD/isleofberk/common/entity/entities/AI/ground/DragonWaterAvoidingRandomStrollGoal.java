//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.GACMD.isleofberk.common.entity.entities.AI.ground;

import com.GACMD.isleofberk.common.entity.entities.base.ADragonBase;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class DragonWaterAvoidingRandomStrollGoal extends RandomStrollGoal {
    ADragonBase dragon;
    public static final float PROBABILITY = 0.001F;
    protected final float probability;

    public DragonWaterAvoidingRandomStrollGoal(ADragonBase dragon, double pSpeedModifier) {
        this(dragon, pSpeedModifier, 0.001F);
        this.dragon = dragon;
    }

    public DragonWaterAvoidingRandomStrollGoal(ADragonBase dragon, double pSpeedModifier, float pProbability) {
        super(dragon, pSpeedModifier);
        this.probability = pProbability;
        this.dragon = dragon;
    }

    @Override
    public boolean canUse() {
        if (dragon.shouldStopMovingIndependently()) {
            return false;
        }
        return super.canUse();
    }

    @Nullable
    protected Vec3 getPosition() {
        if (this.mob.isInWaterOrBubble()) {
            Vec3 $$0 = LandRandomPos.getPos(this.mob, 15, 7);
            return $$0 == null ? super.getPosition() : $$0;
        } else {
            return this.mob.getRandom().nextFloat() >= this.probability ? LandRandomPos.getPos(this.mob, 10, 7) : super.getPosition();
        }
    }
}
