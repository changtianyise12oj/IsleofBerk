package com.GACMD.isleofberk.entity.base.render.model;

import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideable;
import com.GACMD.isleofberk.entity.dragons.deadlynadder.DeadlyNadder;
import com.GACMD.isleofberk.entity.dragons.gronckle.Gronckle;
import com.GACMD.isleofberk.entity.dragons.montrous_nightmare.MonstrousNightmare;
import com.GACMD.isleofberk.entity.dragons.nightfury.NightFury;
import com.GACMD.isleofberk.entity.dragons.tryiple_stryke.TripleStryke;
import com.GACMD.isleofberk.entity.dragons.zippleback.ZippleBack;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.geo.render.built.GeoBone;

public class BaseDragonModelFlying<T extends ADragonBaseFlyingRideable & IAnimatable> extends BaseDragonModel<T> {

    public float changeInYaw;
    protected float currentBodyPitch;
    protected float currentBodyYawForRoll;
    protected float currentBodyYaw;
    protected float boostedBodyPitch;
    public float finalBodyPitch;
    int pitch = 0;

    @Override
    public void setLivingAnimations(T dragon, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(dragon, uniqueID, customPredicate);
        modifyPitch(dragon);
    }

    /**
     * Mojang yaw pitch and roll and Geckolib yaw pitch and roll are inversed, use negative and positive values
     */
    protected void modifyPitch(T dragon) {
        if (!dragon.isRenderedOnGUI()) {
            GeoBone body = getGeoBone(getMainBodyBone());
            if (dragon.getPassengers().size() < 2) {
                if (dragon.isFlying()) {
                    if (dragon.getControllingPassenger() instanceof Player pilot) {
                        // approach to 0 if not boosting
                        // approach to maxRise if boosting
                        if (dragon.isGoingUp() && boostedBodyPitch >= -40) {
                            boostedBodyPitch -= 1;
                        } else if (!dragon.isGoingUp()) {
                            boostedBodyPitch = Mth.approach(boostedBodyPitch, 0, -1);
                        }

                        // +90 dive -90 rise
                        currentBodyPitch = Mth.lerp(0.1F, pilot.xRotO, getMaxRise());
                        finalBodyPitch = currentBodyPitch + boostedBodyPitch;
                        body.setRotationX(toRadians(Mth.clamp(-finalBodyPitch, getMinRise(), getMaxRise())));


                    } else if (dragon.getControllingPassenger() == null) {
                        currentBodyPitch = Mth.lerp(0.1F, dragon.xRotO, getMaxRise());
                    }

                    if (dragon.isDragonFollowing() && dragon.getOwner() instanceof Player player
                            && (dragon instanceof NightFury || dragon instanceof MonstrousNightmare || dragon instanceof ZippleBack || dragon instanceof DeadlyNadder
                            || dragon instanceof TripleStryke || dragon instanceof Gronckle)) {
                        double ydist = dragon.getY() - player.getY();
                        if (ydist > 8.3F && !player.isUnderWater()) {
                            pitch -= 4;
                            pitch -= 4;
                            body.setRotationX(toRadians(Mth.clamp(pitch, -90, 0)));
//                        }
                        } else {
                            pitch = 0;
                        }
                    }
                }

                if (!dragon.isFlying()) {
                    boostedBodyPitch = 0;
                    currentBodyPitch = 0;
                    finalBodyPitch = 0;

                    currentBodyYawForRoll = 0;
                    currentBodyYaw = 0;
                }
            }
        }
    }

    private static final float DEGREES_TO_RADIANS = 0.017453292519943295F;

    public static float toRadians(float angdeg) {
        return angdeg * DEGREES_TO_RADIANS;
    }


    public int getMaxRise() {
        return 40;
    }

    public int getMinRise() {
        return -40;
    }
}
