//package com.GACMD.isleofberk.common.entity.util;
//
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.TamableAnimal;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.phys.AABB;
//import net.minecraft.world.phys.BlockHitResult;
//import net.minecraft.world.phys.Vec3;
//
//import java.util.List;
//
///**
// * Created by TGG on 8/07/2015.
// * Performs a ray trace of the player's line of sight to see what the player is looking at.
// * Similar to the vanilla getMouseOver, which is client side only.
// */
//public class RayTraceServer {
//    /**
//     * Find what the player is looking at (block or entity), up to a maximum range
//     * based on code from EntityRenderer.getMouseOver
//     * Will not target entities which are tamed by the player
//     * @return the block or entity that the player is looking at / targeting with their cursor.  null if no collision
//     */
//    public static BlockHitResult getMouseOver(Level world, Player entityPlayerSP, double range) { // int range
//        final float PARTIAL_TICK = 1.0F;
//        Vec3 positionEyes = entityPlayerSP.getEyePosition(PARTIAL_TICK);
//        Vec3 lookDirection = entityPlayerSP.getViewVector(PARTIAL_TICK);
//        Vec3 endOfLook = positionEyes.add(lookDirection.x * range,
//                lookDirection.y * range,
//                lookDirection.z * range);
//        final boolean STOP_ON_LIQUID = true;
//        final boolean IGNORE_BOUNDING_BOX = true;
//        final boolean RETURN_NULL_IF_NO_COLLIDE = true;
//        BlockHitResult targetedBlock = world.rayTraceBlocks(positionEyes, endOfLook,
//                STOP_ON_LIQUID, !IGNORE_BOUNDING_BOX,
//                !RETURN_NULL_IF_NO_COLLIDE);
//
//        double collisionDistanceSQ = range * range;
//        if (targetedBlock != null) {
//            collisionDistanceSQ = targetedBlock.hitVec.squareDistanceTo(positionEyes);
//            endOfLook = targetedBlock.hitVec;
//        }
//
//        final float EXPAND_SEARCH_BOX_BY = 1.0F;
//        AABB searchBox = entityPlayerSP.getEntityBoundingBox();
//        Vec3 endOfLookDelta = endOfLook.subtract(positionEyes);
//        searchBox = searchBox.expand(endOfLookDelta.x, endOfLookDelta.y, endOfLookDelta.z); //add
//        searchBox = searchBox.grow(EXPAND_SEARCH_BOX_BY);
//        List<Entity> nearbyEntities = world.getEntitiesWithinAABBExcludingEntity(
//                entityPlayerSP, searchBox);
//        Entity closestEntityHit = null;
//        double closestEntityDistanceSQ = Double.MAX_VALUE;
//        for (Entity entity : nearbyEntities) {
//            if (!entity.canBeCollidedWith() || entity == entityPlayerSP.getRidingEntity()) {
//                continue;
//            }
//            if (entity instanceof TamableAnimal) {
//                TamableAnimal tamedEntity = (TamableAnimal)entity;
//                if (tamedEntity.isOwner(entityPlayerSP)) {
//                    continue;
//                }
//            }
//
//            float collisionBorderSize = entity.getCollisionBorderSize();
//            AABB axisalignedbb = entity.getEntityBoundingBox()
//                    .grow(collisionBorderSize);
//            BlockHitResult movingobjectposition = axisalignedbb.calculateIntercept(positionEyes, endOfLook);
//
//            if (axisalignedbb.contains(endOfLook)) {
//                double distanceSQ = (movingobjectposition == null) ? positionEyes.distanceToSqr(endOfLook)
//                        : positionEyes.distanceToSqr(movingobjectposition.hitVec);
//                if (distanceSQ <= closestEntityDistanceSQ) {
//                    closestEntityDistanceSQ = distanceSQ;
//                    closestEntityHit = entity;
//                }
//            } else if (movingobjectposition != null) {
//                double distanceSQ = positionEyes.distanceToSqr(movingobjectposition.hitVec);
//                if (distanceSQ <= closestEntityDistanceSQ) {
//                    closestEntityDistanceSQ = distanceSQ;
//                    closestEntityHit = entity;
//                }
//            }
//        }
//
//        if (closestEntityDistanceSQ <= collisionDistanceSQ) {
//            assert (closestEntityHit != null);
//            return new BlockHitResult(closestEntityHit, closestEntityHit.getPositionVector());
//        }
//        return targetedBlock;
//    }
//}
