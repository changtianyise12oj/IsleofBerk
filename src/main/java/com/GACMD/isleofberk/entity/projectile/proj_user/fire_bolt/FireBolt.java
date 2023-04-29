package com.GACMD.isleofberk.entity.projectile.proj_user.fire_bolt;

import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideableProjUser;
import com.GACMD.isleofberk.registery.ModParticles;
import com.GACMD.isleofberk.entity.projectile.abase.BaseLinearBoltProjectile;
import com.GACMD.isleofberk.registery.ModEntities;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;


public class FireBolt extends BaseLinearBoltProjectile implements IAnimatable {

    AnimationFactory factory = new AnimationFactory(this);

    /**
     * registry constructor
     *
     * @param type
     * @param level
     */
    public FireBolt(EntityType<? extends FireBolt> type, Level level) {
        super(type, level);
    }

    public FireBolt(ADragonBaseFlyingRideableProjUser owner, Vec3 throat, Vec3 end, Level level, int strengthRadius) {
        super(ModEntities.FIRE_BOLT.get(), owner, throat, end, level, strengthRadius);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
//        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.projectile.spin", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "fire_bolt", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }


    @Override
    public void tick() {
        super.tick();
    }

    protected int threshHoldForDeletion() {
        if (this.getDamageTier() > 2) {
            return 30;
        } else {
            return 15;
        }
    }
    @Override
    protected ParticleOptions getTrailParticle() {
        return ModParticles.FLAME_TAIL.get();
    }

    @Override
    protected boolean canHitEntity(@NotNull Entity p_36842_) {
        return super.canHitEntity(p_36842_) && p_36842_ != this.getOwner(); //  && p_36842_ instanceof ADragonBase
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public float getBrightness() {
        return 0;
    }


}
