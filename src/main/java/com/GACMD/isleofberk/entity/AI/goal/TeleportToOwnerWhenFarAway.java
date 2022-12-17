package com.GACMD.isleofberk.entity.AI.goal;

import com.GACMD.isleofberk.entity.base.dragon.ADragonRideableUtility;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

/**
 * If a player dies or gets too far to a player for some reason. teleport the dragon to the player if he is too far and not sitting
 */
public class TeleportToOwnerWhenFarAway extends Goal {

    ADragonRideableUtility dragon;
    LivingEntity owner;

    public TeleportToOwnerWhenFarAway(ADragonRideableUtility dragon) {
        this.dragon = dragon;
    }

    @Override
    public boolean canUse() {
        this.owner = dragon.getOwner();
        if(!dragon.isDragonFollowing()) {
            return false;
        }
        return dragon.isTame() && !dragon.isDragonSitting() && this.owner != null && !dragon.shouldStopMoving();
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        this.dragon.getLookControl().setLookAt(this.owner, 10.0F, (float) this.dragon.getMaxHeadXRot());
        // use player spawn event
        if (this.dragon.distanceTo(owner) > 45) {
            this.teleportToOwner();
        }
        //  || dragon.position().distanceTo(owner.position()) > 25
    }

    private void teleportToOwner() {
        BlockPos blockpos = this.owner.blockPosition();

        for (int i = 0; i < 10; ++i) {
            int j = this.randomIntInclusive(-3, 3);
            int k = this.randomIntInclusive(-1, 1);
            int l = this.randomIntInclusive(-3, 3);
            boolean flag = this.maybeTeleportTo(blockpos.getX() + j, blockpos.getY() + k, blockpos.getZ() + l);
            if (flag) {
                return;
            }
        }

    }

    private boolean maybeTeleportTo(int pX, int pY, int pZ) {
        if (Math.abs((double) pX - this.owner.getX()) < 2.0D && Math.abs((double) pZ - this.owner.getZ()) < 2.0D) {
            return false;
        } else if (!this.canTeleportTo(new BlockPos(pX, pY, pZ))) {
            return false;
        } else {
            this.dragon.moveTo((double) pX + 0.5D, (double) pY, (double) pZ + 0.5D, this.dragon.getYRot(), this.dragon.getXRot());
            dragon.getNavigation().stop();
            return true;
        }
    }

    private boolean canTeleportTo(BlockPos pPos) {
        BlockPathTypes blockpathtypes = WalkNodeEvaluator.getBlockPathTypeStatic(dragon.level, pPos.mutable());
        if (blockpathtypes != BlockPathTypes.WALKABLE) {
            return false;
        } else {
            BlockPos blockpos = pPos.subtract(this.dragon.blockPosition());
            return this.dragon.level.noCollision(this.dragon, this.dragon.getBoundingBox().move(blockpos));
        }
    }

    private int randomIntInclusive(int pMin, int pMax) {
        return this.dragon.getRandom().nextInt(pMax - pMin + 1) + pMin;
    }
}
