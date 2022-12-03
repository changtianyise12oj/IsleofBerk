package com.GACMD.isleofberk.common.entity.entities.AI.taming;

import com.GACMD.isleofberk.common.entity.entities.base.ADragonBase;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TieredItem;

/**
 * Tier 3:
 * - "Combat tame dragons"
 * - Those dragons have to get their health dropped below 30% to allow taming
 * - Once their health drops <30% message will pop up telling player to stop attacking, dragon will also loose aggro for some time
 * - After that taming progress as normal, including becoming hostile to player that grabs weapon during taming process
 * - T3 dragons are Triple Stryke and Skrill
 *
 * - other methods like interaction are handled by the class
 */
public class T3DragonWeakenAndFeedTamingGoal extends ADragonTamingGoalBase {

    public T3DragonWeakenAndFeedTamingGoal(ADragonBase pDragon, double pSpeedModifier) {
        super(pDragon, pSpeedModifier);
    }

    @Override
    public boolean canUse() {
        return super.canUse();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        Player player = dragon.getLevel().getNearestPlayer(dragon, 8);
        if (player != null && dragon.distanceTo(dragon) < 8 && !player.isCreative() && !dragon.isPhaseTwo() && !dragon.isDragonIncapacitated()) {
            boolean weapon = player.getMainHandItem().getItem() instanceof TieredItem;
            boolean armor = player.getArmorValue() > 0;
            if (weapon || armor) {
                dragon.setTarget(player);
            }
        } else {
            dragon.setTarget(null);
        }
    }

    @Override
    public void stop() {
        super.stop();
    }
}
