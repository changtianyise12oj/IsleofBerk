package com.GACMD.isleofberk.entity.AI.path.air;

import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideable;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.Vec3;

public class FlyingDragonMoveControl extends MoveControl {

    ADragonBaseFlyingRideable dragon;
    boolean isFlying;
    float speedBase;
    boolean lookAtTarget;

    public FlyingDragonMoveControl(ADragonBaseFlyingRideable dragon, int speedBase, boolean lookAtTarget) {
        super(dragon);
        this.dragon = dragon;
        this.speedBase = speedBase;
        this.lookAtTarget = lookAtTarget;
    }

    @Override
    public void tick() {
        if (this.operation == Operation.MOVE_TO) {
            Vec3 vec3 = new Vec3(this.wantedX - dragon.getX(), this.wantedY - dragon.getY(), this.wantedZ - dragon.getZ());
            double length = vec3.length();
            if (length < dragon.getBoundingBox().getSize()) {
                this.operation = Operation.WAIT;
                dragon.setDeltaMovement(dragon.getDeltaMovement().scale(0.75D)); // 0.5D
            } else {
                dragon.setDeltaMovement(dragon.getDeltaMovement().add(vec3.scale(this.speedModifier * speedBase * 0.05D / length)));
                if (dragon.getTarget() == null || !lookAtTarget) {
                    Vec3 heading = dragon.getDeltaMovement();
                    dragon.setYRot(-((float) Mth.atan2(heading.x, heading.z)) * (180F / (float) Math.PI));
                    dragon.yBodyRot = dragon.getYRot();
                } else {
                    // need to dive and go down quicker
                    dragon.setDeltaMovement(dragon.getDeltaMovement().add(0.0D, 0, 0.0D));

                    double xDist = dragon.getTarget().getX() - dragon.getX();
                    double yDist = dragon.getTarget().getY() - dragon.getY();
                    dragon.setYRot(-((float) Mth.atan2(xDist, yDist)) * (180F / (float) Math.PI));
                    dragon.setXRot(-(float) Mth.atan2(xDist, yDist) * (180f / (float) Math.PI));
                    dragon.yBodyRot = dragon.getYRot();
                    dragon.setYHeadRot(dragon.getYRot());
                }
            }
        } else if (this.operation == Operation.STRAFE) {
            this.operation = Operation.WAIT;
        }
    }
}
    //    public void tick() {
//        if (dragon != null) isFlying = dragon.isFlying();
//        if (isFlying) {
//            if (operation == Operation.MOVE_TO) {
//                if (dragon != null && !dragon.isVehicle() && !dragon.isDragonSitting()) {
//                    if (dragon.getNavigation() instanceof DragonFlyingPathNavigation flyingPath) {
//                        if (flyingPath.getTargetPos() != null) {
//                            double speed = flyingPath.speedModifier;
//
//                            Vec3 targetPos = new Vec3(flyingPath.getTargetPos().getX(), flyingPath.getTargetPos().getY(), flyingPath.getTargetPos().getZ());
//                            float x = (float) (targetPos.x() - dragon.getX());
//                            float y = (float) (targetPos.y() - dragon.getY());
//                            float z = (float) (targetPos.z() - dragon.getZ());
//                            double d0 = (double) Mth.sqrt(x * x + z * z);
//                            if (Math.abs(d0) > (double) 1.0E-5F) {
//                                double d1 = 1.0D - (double) Mth.abs(y * 0.7F) / d0;
//                                x = (float) ((double) x * d1);
//                                z = (float) ((double) z * d1);
//                                d0 = (double) Mth.sqrt(x * x + z * z);
//                                double d2 = (double) Mth.sqrt(x * x + z * z + y * y);
//                                float yaw = dragon.getYRot();
//                                float theta = (float) Mth.atan2((double) z, (double) x);
//                                float wrappedYaw = Mth.wrapDegrees(dragon.getYRot() + 90.0F);
//                                float f6 = Mth.wrapDegrees(theta * (180F / (float) Math.PI));
//                                dragon.setYRot(Mth.approachDegrees(wrappedYaw, f6, 4.0F) - 90.0F);
//                                dragon.yBodyRot = dragon.getYRot();
//
////                            if (Mth.degreesDifferenceAbs(f3, dragon.getYRot()) < 3.0F) {
////                                this.speed = Mth.approach(this.speed, 1.8F, 0.005F * (1.8F / this.speed));
////                            } else {
////                                this.speed = Mth.approach(this.speed, 0.2F, 0.025F);
////                            }
//
//                                float f7 = (float) (-(Mth.atan2((double) (-y), d0) * (double) (180F / (float) Math.PI)));
//                                if (dragon.position().distanceToSqr(targetPos.x(), targetPos.y(), targetPos.z()) > 4) {
//                                    dragon.setXRot(f7);
//                                }
//                                float f8 = dragon.getYRot() + 90.0F;
//                                double d3 = (double) (speed * Mth.cos(f8 * ((float) Math.PI / 180F))) * Math.abs((double) x / d2);
//                                double d4 = (double) (speed * Mth.sin(f8 * ((float) Math.PI / 180F))) * Math.abs((double) z / d2);
//                                double d5 = (double) (speed * Mth.sin(f7 * ((float) Math.PI / 180F))) * Math.abs((double) y / d2);
//                                Vec3 vec3 = dragon.getDeltaMovement();
//                                dragon.setDeltaMovement(vec3.add((new Vec3(d3, d5, d4)).subtract(vec3).scale(0.2D)));
//                            }
//                        }
//                    }
//                }
//            }
//        } else {
//            super.tick();
//        }
//    }
//}