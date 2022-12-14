package com.GACMD.isleofberk.common.entity.entities.AI.target;

import com.GACMD.isleofberk.common.entity.entities.dragons.terrible_terror.TerribleTerror;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class DragonNonTameRandomTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
    private final TamableAnimal tamableMob;

    public DragonNonTameRandomTargetGoal(TamableAnimal pTamableMob, Class<T> pTargetType, boolean pMustSee, @Nullable Predicate<LivingEntity> pTargetPredicate) {
        super(pTamableMob, pTargetType, 10, pMustSee, false, pTargetPredicate);
        this.tamableMob = pTamableMob;
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean canUse() {
        return !this.tamableMob.isTame() && super.canUse() && !tamableMob.isBaby();
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean canContinueToUse() {
        return this.targetConditions != null ? this.targetConditions.test(this.mob, this.target) : super.canContinueToUse();
    }

    protected void findTarget() {
        // if terrible terror check if mob is in water to avoid glitched water movements
        if (tamableMob instanceof TerribleTerror) {
            if (target != null && !target.isInWater() || !target.isUnderWater()) {
                if (this.targetType != Player.class && this.targetType != ServerPlayer.class && tamableMob.getPassengers().getClass().getClasses() != targetType.getClasses()) {
                    this.target = this.mob.level.getNearestEntity(this.mob.level.getEntitiesOfClass(this.targetType, this.getTargetSearchArea(this.getFollowDistance()), (target) -> {
                        return true;
                    }), this.targetConditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
                } else {
                    this.target = this.mob.level.getNearestPlayer(this.targetConditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
                }
            }
        } else {
            if (this.targetType != Player.class && this.targetType != ServerPlayer.class && tamableMob.getPassengers().getClass().getClasses() != targetType.getClasses()) {
                this.target = this.mob.level.getNearestEntity(this.mob.level.getEntitiesOfClass(this.targetType, this.getTargetSearchArea(this.getFollowDistance()), (target) -> {
                    return true;
                }), this.targetConditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
            } else {
                this.target = this.mob.level.getNearestPlayer(this.targetConditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
            }
        }
    }
}