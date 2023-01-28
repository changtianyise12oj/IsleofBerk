//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.GACMD.isleofberk.entity.AI.target;

import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

public class DragonHurtByTargetGoal extends TargetGoal {
    private static final TargetingConditions HURT_BY_TARGETING = TargetingConditions.forCombat().ignoreLineOfSight().ignoreInvisibilityTesting();
    private static final int ALERT_RANGE_Y = 10;
    private boolean alertSameType;
    private int timestamp;
    ADragonBase dragonBase;
    private final Class<?>[] toIgnoreDamage;
    @Nullable
    private Class<?>[] toIgnoreAlert;

    public DragonHurtByTargetGoal(ADragonBase pMob, Class<?>... pToIgnoreDamage) {
        super(pMob, true);
        this.toIgnoreDamage = pToIgnoreDamage;
        this.dragonBase = pMob;
        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    public boolean canUse() {
        int $$0 = this.dragonBase.getLastHurtByMobTimestamp();
        LivingEntity $$1 = this.dragonBase.getLastHurtByMob();

        // babies don't attack
        if(this.dragonBase.isBaby()) {
            return false;
        }

        if($$1 instanceof Player player) {
            if(player.isCreative()) {
                return false;
            }
        }

        if($$1 instanceof TamableAnimal tame && tame.getOwner() == dragonBase.getOwner()) {
            return false;
        }

        if ($$0 != this.timestamp && $$1 != null) {

            if ($$1.getType() == EntityType.PLAYER && this.dragonBase.level.getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER)) {
                return false;
            } else {
                Class[] var3 = this.toIgnoreDamage;
                int var4 = var3.length;

                for(int var5 = 0; var5 < var4; ++var5) {
                    Class<?> $$2 = var3[var5];
                    if ($$2.isAssignableFrom($$1.getClass())) {
                        return false;
                    }
                }

                return this.canAttack($$1, HURT_BY_TARGETING);
            }
        } else {
            return false;
        }
    }

    public DragonHurtByTargetGoal setAlertOthers(Class<?>... pReinforcementTypes) {
        this.alertSameType = true;
        this.toIgnoreAlert = pReinforcementTypes;
        return this;
    }

    public void start() {
        this.dragonBase.setTarget(this.dragonBase.getLastHurtByMob());
        this.targetMob = this.dragonBase.getTarget();
        this.timestamp = this.dragonBase.getLastHurtByMobTimestamp();
        this.unseenMemoryTicks = 300;
        if (this.alertSameType) {
            this.alertOthers();
        }

        super.start();
    }

    protected void alertOthers() {
        double $$0 = this.getFollowDistance();
        AABB $$1 = AABB.unitCubeFromLowerCorner(this.dragonBase.position()).inflate($$0, 10.0D, $$0);
        List<? extends Mob> $$2 = this.dragonBase.level.getEntitiesOfClass(this.dragonBase.getClass(), $$1, EntitySelector.NO_SPECTATORS);
        Iterator var5 = $$2.iterator();

        while(true) {
            Mob $$3;
            boolean $$4;
            do {
                do {
                    do {
                        do {
                            do {
                                if (!var5.hasNext()) {
                                    return;
                                }

                                $$3 = (Mob)var5.next();
                            } while(this.dragonBase == $$3);
                        } while($$3.getTarget() != null);
                    } while(this.dragonBase != null && ((TamableAnimal)this.dragonBase).getOwner() != ((TamableAnimal)$$3).getOwner());
                } while($$3.isAlliedTo(this.dragonBase.getLastHurtByMob()));

                if (this.toIgnoreAlert == null) {
                    break;
                }

                $$4 = false;
                Class[] var8 = this.toIgnoreAlert;
                int var9 = var8.length;

                for(int var10 = 0; var10 < var9; ++var10) {
                    Class<?> $$5 = var8[var10];
                    if ($$3.getClass() == $$5) {
                        $$4 = true;
                        break;
                    }
                }
            } while($$4);

            this.alertOther($$3, this.dragonBase.getLastHurtByMob());
        }
    }

    protected void alertOther(Mob pMob, LivingEntity pTarget) {
        pMob.setTarget(pTarget);
    }
}
