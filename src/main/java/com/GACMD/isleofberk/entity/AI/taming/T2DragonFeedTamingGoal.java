package com.GACMD.isleofberk.entity.AI.taming;

import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TieredItem;

/**
 * Tier 2:
 * - Like T1 those dragons can be tamed as soon as player obtain taming food
 * - Those dragons will attack armed (but not armored) players (also worth mentioning that some TOOLS also counts as weapons to dragons)
 * - T2 dragons are Gronckles,Stingers, and Deadly Nadders
 * <p>
 * - will attack the player if you are sprinting
 */
public class T2DragonFeedTamingGoal extends ADragonTamingGoalBase {

    public T2DragonFeedTamingGoal(ADragonBase pDragon, double pSpeedModifier) {
        super(pDragon, pSpeedModifier);
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        Player player = dragon.getLevel().getNearestPlayer(dragon, 8);
        if (player != null && dragon.distanceTo(dragon) < 8 && !dragon.isPhaseTwo()) {
            boolean weapon = player.getMainHandItem().getItem() instanceof TieredItem;
            if (!player.isCreative() && (weapon)) {
                dragon.setTarget(player);
            }
        } else {
            dragon.setTarget(null);
        }
    }
}
