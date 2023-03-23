package com.GACMD.isleofberk.entity.projectile.proj_user.skrill_lightning;

import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideableProjUser;
import com.GACMD.isleofberk.entity.base.dragon.ADragonRideableUtility;
import com.GACMD.isleofberk.entity.dragons.nightfury.NightFury;
import com.GACMD.isleofberk.entity.dragons.skrill.Skrill;
import com.GACMD.isleofberk.entity.projectile.abase.BaseLinearBoltProjectile;
import com.GACMD.isleofberk.registery.ModEntities;
import com.GACMD.isleofberk.registery.ModParticles;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class SkrillLightning extends BaseLinearBoltProjectile implements IAnimatable {

    AnimationFactory factory = new AnimationFactory(this);
    public ADragonBaseFlyingRideableProjUser dragon;

    /**
     * registry constructor
     *
     * @param type
     * @param level
     */
    public SkrillLightning(EntityType<? extends SkrillLightning> type, Level level) {
        super(type, level);
    }

    public SkrillLightning(Skrill owner, Vec3 throat, Vec3 end, Level level, int strengthRadius) {
        super(ModEntities.SKRILL_LIGHTNING.get(), owner, throat, end, level, strengthRadius);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
//        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.projectile.spin", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "fury_bolt", 0, this::predicate));
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }


    @Override
    public void tick() {
        super.tick();
    }

    @Override
    protected ParticleOptions getTrailParticle() {
            return ModParticles.SKRILL_LIGHTNING_PARTICLES.get();
    }

    @Override
    protected void callExplosionEffects(boolean flag, ADragonRideableUtility dragon) {
        super.callExplosionEffects(flag, dragon);
    }

    @Override
    protected boolean canHitEntity(@NotNull Entity p_36842_) {
        return super.canHitEntity(p_36842_) && p_36842_ != this.getOwner(); //  && p_36842_ instanceof ADragonBase
    }

    public void updateHeading() {
        double horizontalDistance = this.getDeltaMovement().horizontalDistance();
        this.setYRot((float) (Mth.atan2(this.getDeltaMovement().x(), this.getDeltaMovement().z()) * (180D / Math.PI)));
        this.setXRot((float) (Mth.atan2(this.getDeltaMovement().y(), horizontalDistance) * (180D / Math.PI)));
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();
    }

    @Override
    protected boolean needsTier2ToDamage() {
        return false;
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
