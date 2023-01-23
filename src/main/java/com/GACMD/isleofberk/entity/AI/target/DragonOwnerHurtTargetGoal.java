//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.GACMD.isleofberk.entity.AI.target;

import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import com.GACMD.isleofberk.util.Util;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

import java.util.EnumSet;

public class DragonOwnerHurtTargetGoal extends TargetGoal {
    private final ADragonBase dragon;
    private LivingEntity ownerLastHurt;
    private int timestamp;

    public DragonOwnerHurtTargetGoal(ADragonBase pTameAnimal) {
        super(pTameAnimal, false);
        this.dragon = pTameAnimal;
        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    public boolean canUse() {
        if (this.dragon.isTame() && !dragon.isDragonSitting()) {
            LivingEntity $$0 = this.dragon.getOwner();
            if ($$0 == null) {
                return false;
            } else {
                this.ownerLastHurt = $$0.getLastHurtMob();
                int $$1 = $$0.getLastHurtMobTimestamp();

                // do not attack babies of same kind, eggs may hatch and the owner might accidentally hit the baby causing the parents to attack their own baby,
                // kind of immersion breaking
                if (ownerLastHurt instanceof ADragonBase aDragonBase) {
                    if (aDragonBase.getType() == dragon.getType()) {
                        if (aDragonBase.isBaby())
                            return false;
                    }
                }

                // do not attack pets that you own
                if(ownerLastHurt instanceof TamableAnimal tamableAnimal) {
                    if(tamableAnimal.getOwner() == dragon.getOwner() && tamableAnimal.isTame()) {
                        return false;
                    }

                    // configurable
                    // do not attack any animal pets that is owned by you or other people, may be used in SMP Servers
//                    if (tamableAnimal.isTame()) {
//                        return false;
//                    }
                }

                if(dragon.shouldStopMovingIndependently()) {
                    return false;
                }


                return $$1 != this.timestamp && this.canAttack(this.ownerLastHurt, TargetingConditions.DEFAULT) && this.dragon.wantsToAttack(this.ownerLastHurt, $$0);
            }
        } else {
            return false;
        }
    }

    public void start() {
        this.mob.setTarget(this.ownerLastHurt);
        dragon.setSleepDisturbTicks(Util.secondsToTicks(120));
        LivingEntity $$0 = this.dragon.getOwner();
        if ($$0 != null) {
            this.timestamp = $$0.getLastHurtMobTimestamp();
        }

        super.start();
    }
}
