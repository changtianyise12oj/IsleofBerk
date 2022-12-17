package com.GACMD.isleofberk.entity.projectile.breath_user.skrill_lightning;

import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideableBreathUser;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class RayTracer {

    public static HitResult rayTrace(Level level, ADragonBaseFlyingRideableBreathUser skrill, Player pilot, int ticks, float distance) {
        Vec3 vec3 = pilot.getEyePosition();
        Vec3 vec31 = pilot.getLookAngle();
        Vec3 vec32 = vec3.add(new Vec3(vec31.x * distance, vec31.y * distance, vec31.z * distance));

        HitResult hitResult = level.clip(new ClipContext(vec3, vec32, ClipContext.Block.VISUAL, ClipContext.Fluid.ANY, skrill));


        return hitResult;
    }

    public static EntityHitResult rayTraceEntities(Level level, ADragonBaseFlyingRideableBreathUser skrill, SkrillLightning lightning, Player pilot, int ticks, float distance) {
        Vec3 vec3 = pilot.getEyePosition();
        Vec3 vec31 = pilot.getLookAngle();
        Vec3 vec32 = vec3.add(new Vec3(vec31.x * distance, vec31.y * distance + 8, vec31.z * distance));

        EntityHitResult hitResult = ProjectileUtil.getEntityHitResult(level, skrill, vec3, vec32, (new AABB(vec3, vec32)).inflate(1.0D), (entity) -> !entity.isSpectator() && entity != skrill, -0F);

        return hitResult;
    }
}

    //    public static MovingObjectPosition getMouseOver(World world, EntityPlayer entityPlayerSP, float maxDistance) {
//        final float PARTIAL_TICK = 1.0F;
//        Vec3 positionEyes = entityPlayerSP.getPositionEyes(PARTIAL_TICK);
//        Vec3 lookDirection = entityPlayerSP.getLook(PARTIAL_TICK);
//        Vec3 endOfLook = positionEyes.addVector(lookDirection.xCoord * maxDistance,
//                lookDirection.yCoord * maxDistance,
//                lookDirection.zCoord * maxDistance);
//        final boolean STOP_ON_LIQUID = true;
//        final boolean IGNORE_BOUNDING_BOX = true;
//        final boolean RETURN_NULL_IF_NO_COLLIDE = true;
//        MovingObjectPosition targetedBlock = world.rayTraceBlocks(positionEyes, endOfLook,
//                STOP_ON_LIQUID, !IGNORE_BOUNDING_BOX,
//                !RETURN_NULL_IF_NO_COLLIDE);
//
//        double collisionDistanceSQ = maxDistance * maxDistance;
//        if (targetedBlock != null) {
//            collisionDistanceSQ = targetedBlock.hitVec.squareDistanceTo(positionEyes);
//            endOfLook = targetedBlock.hitVec;
//        }
//
//        final float EXPAND_SEARCH_BOX_BY = 1.0F;
//        AxisAlignedBB searchBox = entityPlayerSP.getEntityBoundingBox();
//        Vec3 endOfLookDelta = endOfLook.subtract(positionEyes);
//        searchBox = searchBox.addCoord(endOfLookDelta.xCoord, endOfLookDelta.yCoord, endOfLookDelta.zCoord);
//        searchBox = searchBox.expand(EXPAND_SEARCH_BOX_BY, EXPAND_SEARCH_BOX_BY, EXPAND_SEARCH_BOX_BY);
//        List<Entity> nearbyEntities = (List<Entity>) world.getEntitiesWithinAABBExcludingEntity(
//                entityPlayerSP, searchBox);
//        Entity closestEntityHit = null;
//        double closestEntityDistanceSQ = Double.MAX_VALUE;
//        for (Entity entity : nearbyEntities) {
//            if (!entity.canBeCollidedWith() || entity == entityPlayerSP.ridingEntity) {
//                continue;
//            }
//            if (entity instanceof EntityTameable) {
//                EntityTameable tamedEntity = (EntityTameable)entity;
//                if (tamedEntity.isOwner(entityPlayerSP)) {
//                    continue;
//                }
//            }
//
//            float collisionBorderSize = entity.getCollisionBorderSize();
//            AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox()
//                    .expand(collisionBorderSize, collisionBorderSize, collisionBorderSize);
//            MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(positionEyes, endOfLook);
//
//            if (axisalignedbb.isVecInside(endOfLook)) {
//                double distanceSQ = (movingobjectposition == null) ? positionEyes.squareDistanceTo(endOfLook)
//                        : positionEyes.squareDistanceTo(movingobjectposition.hitVec);
//                if (distanceSQ <= closestEntityDistanceSQ) {
//                    closestEntityDistanceSQ = distanceSQ;
//                    closestEntityHit = entity;
//                }
//            } else if (movingobjectposition != null) {
//                double distanceSQ = positionEyes.squareDistanceTo(movingobjectposition.hitVec);
//                if (distanceSQ <= closestEntityDistanceSQ) {
//                    closestEntityDistanceSQ = distanceSQ;
//                    closestEntityHit = entity;
//                }
//            }
//        }
//
//        if (closestEntityDistanceSQ <= collisionDistanceSQ) {
//            assert (closestEntityHit != null);
//            return new MovingObjectPosition(closestEntityHit, closestEntityHit.getPositionVector());
//        }
//        return targetedBlock;
//    }

//    public void pick(float pPartialTicks) {
//        Entity entity = this.minecraft.getCameraEntity();
//        if (entity != null) {
//            if (this.minecraft.level != null) {
//                this.minecraft.getProfiler().push("pick");
//                this.minecraft.crosshairPickEntity = null;
//                double d0 = (double)this.minecraft.gameMode.getPickRange();
//                this.minecraft.hitResult = entity.pick(d0, pPartialTicks, false);
//                Vec3 vec3 = entity.getEyePosition(pPartialTicks);
//                boolean flag = false;
//                int i = 3;
//                double d1 = d0;
//                if (this.minecraft.gameMode.hasFarPickRange()) {
//                    d1 = 6.0D;
//                    d0 = d1;
//                } else {
//                    if (d0 > 3.0D) {
//                        flag = true;
//                    }
//
//                    d0 = d0;
//                }
//
//                d1 *= d1;
//                if (this.minecraft.hitResult != null) {
//                    d1 = this.minecraft.hitResult.getLocation().distanceToSqr(vec3);
//                }
//
//                Vec3 vec31 = entity.getViewVector(1.0F);
//                Vec3 vec32 = vec3.add(vec31.x * d0, vec31.y * d0, vec31.z * d0);
//                float f = 1.0F;
//                AABB aabb = entity.getBoundingBox().expandTowards(vec31.scale(d0)).inflate(1.0D, 1.0D, 1.0D);
//                EntityHitResult entityhitresult = ProjectileUtil.getEntityHitResult(entity, vec3, vec32, aabb, (p_172770_) -> {
//                    return !p_172770_.isSpectator() && p_172770_.isPickable();
//                }, d1);
//                if (entityhitresult != null) {
//                    Entity entity1 = entityhitresult.getEntity();
//                    Vec3 vec33 = entityhitresult.getLocation();
//                    double d2 = vec3.distanceToSqr(vec33);
//                    if (flag && d2 > 9.0D) {
//                        this.minecraft.hitResult = BlockHitResult.miss(vec33, Direction.getNearest(vec31.x, vec31.y, vec31.z), new BlockPos(vec33));
//                    } else if (d2 < d1 || this.minecraft.hitResult == null) {
//                        this.minecraft.hitResult = entityhitresult;
//                        if (entity1 instanceof LivingEntity || entity1 instanceof ItemFrame) {
//                            this.minecraft.crosshairPickEntity = entity1;
//                        }
//                    }
//                }
//
//                this.minecraft.getProfiler().pop();
//            }
//        }
//    }

    /**
     * Find what the player is looking at (block or entity), up to a maximum range
     * based on code from EntityRenderer.getMouseOver
     * Will not target entities which are tamed by the player
     * @return the block or entity that the player is looking at / targeting with their cursor.  null if no collision
     */
//    public static MovingObjectPosition getMouseOver(World world, EntityPlayer entityPlayerSP, float maxDistance) {
//        final float PARTIAL_TICK = 1.0F;
//        Vec3 positionEyes = entityPlayerSP.getPositionEyes(PARTIAL_TICK);
//        Vec3 lookDirection = entityPlayerSP.getLook(PARTIAL_TICK);
//        Vec3 endOfLook = positionEyes.addVector(lookDirection.xCoord * maxDistance,
//                lookDirection.yCoord * maxDistance,
//                lookDirection.zCoord * maxDistance);
//        final boolean STOP_ON_LIQUID = true;
//        final boolean IGNORE_BOUNDING_BOX = true;
//        final boolean RETURN_NULL_IF_NO_COLLIDE = true;
//        MovingObjectPosition targetedBlock = world.rayTraceBlocks(positionEyes, endOfLook,
//                STOP_ON_LIQUID, !IGNORE_BOUNDING_BOX,
//                !RETURN_NULL_IF_NO_COLLIDE);
//
//        double collisionDistanceSQ = maxDistance * maxDistance;
//        if (targetedBlock != null) {
//            collisionDistanceSQ = targetedBlock.hitVec.squareDistanceTo(positionEyes);
//            endOfLook = targetedBlock.hitVec;
//        }
//
//        final float EXPAND_SEARCH_BOX_BY = 1.0F;
//        AxisAlignedBB searchBox = entityPlayerSP.getEntityBoundingBox();
//        Vec3 endOfLookDelta = endOfLook.subtract(positionEyes);
//        searchBox = searchBox.addCoord(endOfLookDelta.xCoord, endOfLookDelta.yCoord, endOfLookDelta.zCoord);
//        searchBox = searchBox.expand(EXPAND_SEARCH_BOX_BY, EXPAND_SEARCH_BOX_BY, EXPAND_SEARCH_BOX_BY);
//        List<Entity> nearbyEntities = (List<Entity>) world.getEntitiesWithinAABBExcludingEntity(
//                entityPlayerSP, searchBox);
//        Entity closestEntityHit = null;
//        double closestEntityDistanceSQ = Double.MAX_VALUE;
//        for (Entity entity : nearbyEntities) {
//            if (!entity.canBeCollidedWith() || entity == entityPlayerSP.ridingEntity) {
//                continue;
//            }
//            if (entity instanceof EntityTameable) {
//                EntityTameable tamedEntity = (EntityTameable)entity;
//                if (tamedEntity.isOwner(entityPlayerSP)) {
//                    continue;
//                }
//            }
//
//            float collisionBorderSize = entity.getCollisionBorderSize();
//            AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox()
//                    .expand(collisionBorderSize, collisionBorderSize, collisionBorderSize);
//            MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(positionEyes, endOfLook);
//
//            if (axisalignedbb.isVecInside(endOfLook)) {
//                double distanceSQ = (movingobjectposition == null) ? positionEyes.squareDistanceTo(endOfLook)
//                        : positionEyes.squareDistanceTo(movingobjectposition.hitVec);
//                if (distanceSQ <= closestEntityDistanceSQ) {
//                    closestEntityDistanceSQ = distanceSQ;
//                    closestEntityHit = entity;
//                }
//            } else if (movingobjectposition != null) {
//                double distanceSQ = positionEyes.squareDistanceTo(movingobjectposition.hitVec);
//                if (distanceSQ <= closestEntityDistanceSQ) {
//                    closestEntityDistanceSQ = distanceSQ;
//                    closestEntityHit = entity;
//                }
//            }
//        }
//
//        if (closestEntityDistanceSQ <= collisionDistanceSQ) {
//            assert (closestEntityHit != null);
//            return new MovingObjectPosition(closestEntityHit, closestEntityHit.getPositionVector());
//        }
//        return targetedBlock;
//    }
//}
