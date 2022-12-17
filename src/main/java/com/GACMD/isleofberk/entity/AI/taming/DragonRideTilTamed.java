package com.GACMD.isleofberk.entity.AI.taming;

import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

/**
 * Two step taming process, cancelled if player grabs weapon in phase 1 or hit dragon on phase 2:
 * <p>
 * Phase 1:
 * - Based on ocelot taming
 * - Dragon can be SLOWLY approached by player holding taming food
 * - If spooked dragon will attack player
 * - Feeding dragon is on cooldown to prevent quick tame
 * - Once dragon is feed sufficent amounts of food it enters Phase 2
 * <p>
 * Phase 2:
 * - Dragon will no longer aggro to player unless hit
 * - In this phase player can attempt to mount dragon
 * - After 2-3 mount fails dragon will become "upset" and request taming food and mount cooldown to calm down
 * - Dragon is tamed only by mounting, food dont increase chance of taming
 */
public class DragonRideTilTamed extends ADragonTamingGoalBase {

    public DragonRideTilTamed(ADragonBase pDragon, double pSpeedModifier) {
        super(pDragon, pSpeedModifier);
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     * <p>
     * Enabling if the player can ride the dragon is done on mobInteract, would not activate if dragon is ridden, which in turn (isVehicle) boolean won't be activated if dragon could not be ridden
     */
    public boolean canUse() {
        super.canUse();
        if (!this.dragon.isTame() && this.dragon.isVehicle()) {
            Vec3 vec3 = DefaultRandomPos.getPos(this.dragon, 5, 4);
            if (vec3 == null) {
                return false;
            } else {
                this.posX = vec3.x;
                this.posY = vec3.y;
                this.posZ = vec3.z;
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void start() {
        this.dragon.getNavigation().moveTo(this.posX, this.posY, this.posZ, this.speedModifier);
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean canContinueToUse() {
        return !this.dragon.isTame() && !this.dragon.getNavigation().isDone() && this.dragon.isVehicle();
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        if (!this.dragon.isTame() && this.dragon.getRandom().nextInt(this.adjustedTickDelay(50)) == 0) {
            Entity entity = dragon.getControllingPassenger();
            if (entity == null) {
                return;
            }

            if (entity instanceof Player) {
                if (this.dragon.getRandom().nextInt(100) < 30 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(dragon, (Player) entity)) {
                    dragon.tameWithName((Player) entity);
                    return;
                }
            }

            this.dragon.ejectPassengers();
            this.dragon.makeMad();
            this.dragon.level.broadcastEntityEvent(this.dragon, (byte) 6);
        }
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

}
