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
