package com.GACMD.isleofberk.common.entity.entities.projectile.breath_user.skrill_lightning;

import com.GACMD.isleofberk.common.entity.entities.base.dragon.ADragonBaseFlyingRideableBreathUser;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;

import javax.annotation.Nullable;
import java.util.List;

public class SkrillLightning {

    public ADragonBaseFlyingRideableBreathUser skrill;
    public EntityHitResult hitEntity;
    public HitResult hitResult;

    public void shootLightning(Level level, Vec3 origin, Vec3 direction, ADragonBaseFlyingRideableBreathUser skrill, Player pilot, int ticksInUse) {
        HitResult hitResult = RayTracer.rayTrace(level, skrill, pilot, ticksInUse, 180);

        // they spawn on the nadder, the nadder is blocking it
        EntityHitResult entityHitResult = RayTracer.rayTraceEntities(level, skrill,this, pilot, ticksInUse, 80);
        if (entityHitResult != null && entityHitResult.getType() == HitResult.Type.ENTITY) {
            onEntityHit(level, entityHitResult, entityHitResult.getEntity(), entityHitResult.getLocation(), skrill, skrill.getThroatPos(skrill), ticksInUse);
            this.setHitEntity(entityHitResult);
        }

        // could not trace and damage entities
        if (hitResult.getType() != HitResult.Type.MISS) {
            if (hitResult.getType() == HitResult.Type.BLOCK && hitResult instanceof BlockHitResult blockHitResult) {
                onBlockHit(level, hitResult.getLocation(), blockHitResult.getDirection(), blockHitResult.getLocation(), skrill, origin, ticksInUse);
                this.setHitResult(hitResult);
            }
        }
    }

    public EntityHitResult getHitEntity() {
        return hitEntity;
    }

    public void setHitEntity(EntityHitResult hitEntity) {
        this.hitEntity = hitEntity;
    }

    public HitResult getHitResult() {
        return hitResult;
    }

    public void setHitResult(HitResult hitResult) {
        this.hitResult = hitResult;
    }

    public ADragonBaseFlyingRideableBreathUser getSkrill() {
        return skrill;
    }

    public void setSkrill(ADragonBaseFlyingRideableBreathUser skrill) {
        this.skrill = skrill;
    }

    public double getRange() {
        return 70;
    }

    public void onMiss(Level level, @Nullable ADragonBaseFlyingRideableBreathUser skrill, Vec3 origin, Vec3 direction, int ticksInUse) {

    }

    public static void onEntityHit(Level level, EntityHitResult entityHitResult, Entity target, Vec3 hit, ADragonBaseFlyingRideableBreathUser skrill, Vec3 origin, int ticksInUse) {
        // does not affect moving targets
//        AABB aabb = new AABB(new BlockPos(hit));
//        for (Entity entity1 : level.getEntities(null, aabb)) {
        AABB aabb = new AABB(hit.x(), hit.y(), hit.z(), hit.x(), hit.y() + 5, hit.z());
        List<Entity> entity = level.getEntities(null, aabb);
        for (Entity entity1 : entity) {
            entity1.hurt(DamageSource.mobAttack(skrill), 20);
            entity1.hurt(DamageSource.LIGHTNING_BOLT, 8);
            entity1.setSecondsOnFire(4);
        }
//        level.explode(null, aabb.minX, aabb.minY + 1, aabb.minZ, 1, Explosion.BlockInteraction.NONE);
//        level.addParticle(ParticleTypes.LAVA, true, hit.x, hit.y, hit.z, 1, 1, 1);
        level.addParticle(ParticleTypes.LAVA, true, aabb.minX, aabb.minY, aabb.minZ, 1, 1, 1);
    }

    public static void onBlockHit(Level level, Vec3 vec3, Direction side, Vec3 hit, ADragonBaseFlyingRideableBreathUser skrill, Vec3 origin, int ticksInUse) {
//        level.addParticle(ParticleTypes.LAVA, true, hit.x, hit.y, hit.z, 1, 1, 1);
//        Wolf wolf = new Wolf(EntityType.WOLF, level);
//        level.addFreshEntity(wolf);
//        wolf.moveTo(hit);
        AABB aabb = new AABB(hit.x(), hit.y(), hit.z(), hit.x(), hit.y(), hit.z());
//        level.explode(null, aabb.minX, aabb.minY + 1, aabb.minZ, 3, Explosion.BlockInteraction.NONE);
        level.addParticle(ParticleTypes.LAVA, true, aabb.minX, aabb.minY + 1, aabb.minZ, 1, 1, 1);
    }

}
