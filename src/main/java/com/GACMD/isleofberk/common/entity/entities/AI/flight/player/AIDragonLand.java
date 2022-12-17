package com.GACMD.isleofberk.common.entity.entities.AI.flight.player;

import com.GACMD.isleofberk.common.entity.entities.AI.flight.ADragonBaseBaseFlyingRideableGoal;
import com.GACMD.isleofberk.common.entity.entities.base.dragon.ADragonBaseFlyingRideable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

/**
 * Dragon AI for instant landing, if left unmounted in air.
 */
public class AIDragonLand extends ADragonBaseBaseFlyingRideableGoal {

    private final double speed;
    private BlockPos landingPos;
    Level level;
    Random random;

    public AIDragonLand(ADragonBaseFlyingRideable dragon, double speed) {
        super(dragon);
        this.speed = speed;
        this.level = dragon.level;
        this.random = dragon.getRandom();
    }

    @Override
    public boolean canUse() {
        LivingEntity owner = dragon.getOwner();
        // don't land but catch owner instead
        if (owner != null) {
            if (owner.fallDistance > 4) {
                return false;
            }
            if (!owner.isOnGround() && dragon.isDragonFollowing()) {
                return false;
            }
        }

        // use airbourne melee attack AI
        if (dragon.getTarget() != null && !dragon.getTarget().isOnGround()) {
            return false;
        }

        if (dragon.getTicksFlyWandering() > 3) {
            return false;
        }
        if (dragon.isVehicle() || dragon.getVehicle() != null) {
            return false;
        }

        return dragon.isFlying();
    }

    @Override
    public void start() {
        Vec3 landingPos = getLandingPosition();
        if (landingPos != null)
            this.landingPos = new BlockPos(landingPos);
    }

    @Override
    public boolean canContinueToUse() {
        return dragon.isFlying();
    }

    @Override
    public void stop() {
    }

    public AABB getTargetSearchArea(double distance) {
        return dragon.getBoundingBox().inflate(distance, distance, distance);
    }

    private ADragonBaseFlyingRideable findSameSpecies() {
        ADragonBaseFlyingRideable dragonBaseFlyingRideable = level.getNearestEntity(level.getEntitiesOfClass(ADragonBaseFlyingRideable.class, getTargetSearchArea(750)),
                TargetingConditions.forNonCombat(), dragon, dragon.getX(), dragon.getEyeY(), dragon.getZ());
        if (dragonBaseFlyingRideable != null && dragonBaseFlyingRideable.getMobType() == dragon.getMobType()) {
            return dragonBaseFlyingRideable;
        }

        return null;
    }

    /**
     * Find a dragon and if it is the same species as the dragon land
     * else if tamed and the owner is present land near the owner (dragons will not fly on their own without the owner)
     * simply land anywhere
     */
    @Override
    public void tick() {
        if (landingPos != null) {
            BlockPos landingPos = level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE, this.landingPos);
            if (!dragon.isDragonOnGround()) {
                LivingEntity dragonOwner = dragon.getOwner();

                if (!tryMoveToBlockPos(landingPos, speed)) {
                    if (dragonOwner != null && dragon.distanceTo(dragonOwner) <= getFollowRange() && dragonOwner.isOnGround() && dragon.isDragonFollowing()) {
                        BlockPos ownerPos = new BlockPos(dragonOwner.position());
                        // probably too high, so simply descend vertically
                        tryMoveToBlockPos(new BlockPos(dragon.position().subtract(ownerPos.getX(), ownerPos.getY() - 3, ownerPos.getZ())), speed);
                    } else {
                        // probably too high, so simply descend vertically
                        tryMoveToBlockPos(new BlockPos(dragon.position().subtract(landingPos.getX(), landingPos.getY() - 4, landingPos.getZ())), speed);
                    }
                }
            } else if (dragon.isDragonOnGround() || dragon.isInWater() || dragon.isWaterBelow()) {
                dragon.setIsFlying(false);
            }
        }
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    private Vec3 getLandingPosition() {
        return DefaultRandomPos.getPos(dragon, 7, 40);

    }

    protected boolean tryMoveToBlockPos(BlockPos pos, double speed) {
        return dragon.getNavigation().moveTo(pos.getX(), pos.getY(), pos.getZ(), speed);
    }

}

// } else if (nearbyDragon != null && nearbyDragon.isOnGround() && nearbyDragon.getType() == dragon.getType()) {
//         tryMoveToBlockPos(new BlockPos(nearbyDragon.position()), 0.5F);
//         Wolf wolf = new Wolf(EntityType.WOLF, level);
//         level.addFreshEntity(wolf);
//         wolf.setPos(nearbyDragon.position());
//         level.addParticle(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, nearbyDragon.getX(), nearbyDragon.getY(), nearbyDragon.getZ(), 2, 3, 2);
//         }


