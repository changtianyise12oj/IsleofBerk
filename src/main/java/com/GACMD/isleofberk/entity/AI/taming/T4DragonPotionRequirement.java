package com.GACMD.isleofberk.entity.AI.taming;

import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import com.GACMD.isleofberk.entity.dragons.lightfury.LightFury;
import com.GACMD.isleofberk.entity.dragons.montrous_nightmare.MonstrousNightmare;
import com.GACMD.isleofberk.entity.dragons.nightfury.NightFury;
import com.GACMD.isleofberk.entity.dragons.nightlight.NightLight;
import com.GACMD.isleofberk.entity.dragons.skrill.Skrill;
import com.GACMD.isleofberk.entity.dragons.zippleback.ZippleBack;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TieredItem;

/**
 * Tier 4:
 * - "Buff tame dragons"
 * - T4 dragons are aggressive (even if player is holding their fav food) unless player is under certain potion effect that allows taming to take place (effectively locking them to nether phase of the game)
 * - As long as player have said buff taming progress as normal
 * - T4 dragons are: Monstrous Nightmare, Hideous Zippleback, Night Fury and Light Fury
 *
 * - controls agression
 */
public class T4DragonPotionRequirement extends ADragonTamingGoalBase {

    public T4DragonPotionRequirement(ADragonBase pDragon, double pSpeedModifier) {
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
        Player player = dragon.getLevel().getNearestPlayer(dragon, 32);
        if (player != null && dragon.distanceTo(player) < 8 && !player.isCreative()) {
            boolean weapon = player.getMainHandItem().getItem() instanceof TieredItem;
            boolean armor = player.getArmorValue() > 0;
            if (aggroUnlessHasPotionEffect(player, dragon) && !dragon.isPhaseTwo() && !dragon.isDragonIncapacitated() || weapon || armor) {
                dragon.setTarget(player);
            } else {
                dragon.setTarget(null);
            }
        }
    }

    private boolean aggroUnlessHasPotionEffect(Player player, ADragonBase dragon) {
        if (dragon instanceof MonstrousNightmare && player.hasEffect(MobEffects.FIRE_RESISTANCE)) {
            return false;
        }
        if (dragon instanceof NightFury && player.hasEffect(MobEffects.NIGHT_VISION)) {
            return false;
        }
        if (dragon instanceof LightFury && player.hasEffect(MobEffects.INVISIBILITY)) {
            return false;
        }
        if (dragon instanceof ZippleBack && player.hasEffect(MobEffects.DAMAGE_BOOST)) {
            return false;
        }
        if (dragon instanceof NightLight && player.hasEffect(MobEffects.INVISIBILITY)) {
            return false;
        }

        return true;
    }

    @Override
    public void stop() {
        super.stop();
    }
}
