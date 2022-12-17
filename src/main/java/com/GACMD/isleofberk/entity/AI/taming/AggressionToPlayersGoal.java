package com.GACMD.isleofberk.entity.AI.taming;

import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.SwordItem;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class AggressionToPlayersGoal<T extends LivingEntity> extends TargetGoal {

    /**
     * 0 : none; 1: weapon; 2: armor or weapon 3: always
     */
    int agressionType = 0;
    ADragonBase dragonBase;
    protected final Class<T> targetType;
    @Nullable
    protected LivingEntity target;
    protected final int randomInterval;

    /**
     * This filter is applied to the Entity search. Only matching entities will be targeted.
     */
    protected TargetingConditions targetConditions;

    public AggressionToPlayersGoal(ADragonBase dragonBase, Class<T> pTargetType, boolean pMustSee, int agressionType, @Nullable Predicate<LivingEntity> pTargetPredicate) {
        super(dragonBase, pMustSee);
        this.targetType = pTargetType;
        this.dragonBase = dragonBase;
        this.agressionType = agressionType;
        this.targetConditions = TargetingConditions.forCombat().range(this.getFollowDistance()).selector(pTargetPredicate);
        this.randomInterval = reducedTickDelay(10);
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    @Override
    public boolean canUse() {
        return dragonBase.isAlive() && !dragonBase.isTame() && !dragonBase.isBaby();
    }

    @Override
    public boolean canContinueToUse() {
        return canUse();
    }

    @Override
    public void tick() {
        Player player = dragonBase.getLevel().getNearestPlayer(dragonBase, 8);
        if (this.randomInterval > 0 && this.mob.getRandom().nextInt(this.randomInterval) != 0) {
            return;
        }

        if (player != null && dragonBase.distanceTo(player) < 8) {
            boolean weapon = player.getMainHandItem().getItem() instanceof SwordItem || player.getMainHandItem().getItem() instanceof AxeItem;
            boolean armor = player.getArmorValue() > 0;
            if (agressionType == 1) {
                if (weapon) {
                    dragonBase.setTarget(player);
                } else {
                    this.dragonBase.setTarget(null);
                }
            } else if (agressionType == 2) {
                if (armor || weapon) {
                    dragonBase.setTarget(player);
                } else {
                    this.dragonBase.setTarget(null);
                }
            } else if (agressionType == 3 && !player.isCreative()) {
                dragonBase.setTarget(player);
            }
        }
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void stop() {
        this.dragonBase.setTarget(null);
    }

    public void setTarget(@Nullable LivingEntity pTarget) {
        this.target = pTarget;
    }

}
