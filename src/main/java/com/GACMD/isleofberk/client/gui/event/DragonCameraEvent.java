package com.GACMD.isleofberk.client.gui.event;

import com.GACMD.isleofberk.common.entity.entities.base.ADragonBase;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class DragonCameraEvent {

    /**
     * Checks for collision of the third person camera and returns the distance
     */
    private static double getMaxZoom(double pStartingDistance, Vec3 position, Level level, Vector3f forwards, Entity entity) {
        for (int i = 0; i < 8; ++i) {
            float f = (float) ((i & 1) * 2 - 1);
            float f1 = (float) ((i >> 1 & 1) * 2 - 1);
            float f2 = (float) ((i >> 2 & 1) * 2 - 1);
            f *= 0.1F;
            f1 *= 0.1F;
            f2 *= 0.1F;
            Vec3 vec3 = position.add((double) f, (double) f1, (double) f2);
            Vec3 vec31 = new Vec3(position.x - (double) forwards.x() * pStartingDistance + (double) f + (double) f2, position.y - (double) forwards.y() * pStartingDistance + (double) f1, position.z - (double) forwards.z() * pStartingDistance + (double) f2);
            HitResult hitresult = level.clip(new ClipContext(vec3, vec31, ClipContext.Block.VISUAL, ClipContext.Fluid.NONE, entity));
            if (hitresult.getType() != HitResult.Type.MISS) {
                double d0 = hitresult.getLocation().distanceTo(position);
                if (d0 < pStartingDistance) {
                    pStartingDistance = d0;
                }
            }
        }
        return pStartingDistance;
    }

    /**
     * @param cameraSetup
     */
    @SubscribeEvent
    public static void Camera(EntityViewRenderEvent.CameraSetup cameraSetup) {
        if (cameraSetup.getCamera().getEntity() instanceof Player) {
            Player player = (Player) cameraSetup.getCamera().getEntity();
            Minecraft mc = Minecraft.getInstance();
            if (player.getVehicle() instanceof ADragonBase) {
                ADragonBase dragon = (ADragonBase) player.getVehicle();
                if (cameraSetup.getCamera().isDetached()) {
                    cameraSetup.getCamera().move(-getMaxZoom(mc.options.getCameraType().isMirrored() ? dragon.getRideCameraDistanceFront() : dragon.getRideCameraDistanceBack(),
                                    player.position(), player.level,
                                    cameraSetup.getCamera().getLookVector(), player),
                            dragon.getRideCameraDistanceVert(), dragon.getRideCameraDistanceHoriz());
                }
            }
        }
    }
}
