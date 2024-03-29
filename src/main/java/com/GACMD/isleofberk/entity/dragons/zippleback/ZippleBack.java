package com.GACMD.isleofberk.entity.dragons.zippleback;

import com.GACMD.isleofberk.entity.AI.taming.T4DragonPotionRequirement;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideable;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideableBreathUser;
import com.GACMD.isleofberk.entity.eggs.entity.base.ADragonEggBase;
import com.GACMD.isleofberk.entity.eggs.entity.eggs.ZippleBackEgg;
import com.GACMD.isleofberk.entity.projectile.breath_user.poison.ZipBreathProjectile;
import com.GACMD.isleofberk.entity.projectile.breath_user.poison.ZippleBackAOECloud;
import com.GACMD.isleofberk.registery.ModEntities;
import com.GACMD.isleofberk.registery.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import java.util.List;

import static com.GACMD.isleofberk.registery.ModTags.Items.ZIPPLEBACK_BREED_FOOD;
import static com.GACMD.isleofberk.registery.ModTags.Items.ZIPPLEBACK_TAME_FOOD;

public class ZippleBack extends ADragonBaseFlyingRideableBreathUser {

    private int ticksUsingSecondAbility;

    private <E extends IAnimatable> PlayState basicMovementController(AnimationEvent<E> event) {

        // flying animations
        if (isFlying()) {
            if (isDragonMoving()){

                // mounted flying
                if (this.isVehicle()) {
                    if (this.getXRot() < 8 || isGoingUp() || getPassengers().size() > 2) {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("zippleback.fly", ILoopType.EDefaultLoopTypes.LOOP));
                        setShouldPlayFlapping(true);
                    }
                    if (this.getXRot() >= 8 && this.getXRot() < 33 && !isGoingUp()) {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("zippleback.glide", ILoopType.EDefaultLoopTypes.LOOP));
                        setShouldPlayFlapping(false);
                    }
                    if (this.getXRot() >= 33 && !isGoingUp()) {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("zippleback.dive", ILoopType.EDefaultLoopTypes.LOOP));
                        setShouldPlayFlapping(false);
                    }
                    return PlayState.CONTINUE;
                }
                // follow player on elytra
                if (isDragonFollowing() && getOwner() != null && getOwner().isFallFlying()) {
                    LivingEntity owner = getOwner();
                    float dist = distanceTo(owner);
                    double ydist = this.getY() - owner.getY();

                    if (ydist < 10 || dist > 15) {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("zippleback.fly", ILoopType.EDefaultLoopTypes.LOOP));
                    } else {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("zippleback.dive", ILoopType.EDefaultLoopTypes.LOOP));
                    }
                }
                // free flying
                else {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("zippleback.fly", ILoopType.EDefaultLoopTypes.LOOP));
                    setShouldPlayFlapping(true);
                }
            } else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("zippleback.hover", ILoopType.EDefaultLoopTypes.LOOP));
                setShouldPlayFlapping(true);
            }

            //ground animations
        } else {
            if (this.isDragonSitting() && !this.isDragonSleeping()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("zippleback.sit", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
            if (this.isDragonSleeping()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("zippleback.sleep", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
            if (isDragonMoving() && !shouldStopMovingIndependently()) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("zippleback.walk", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            } else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("zippleback.idle", ILoopType.EDefaultLoopTypes.LOOP));
            }
        }
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState attackController(AnimationEvent<E> event) {
        if (getTicksSinceLastAttack() >= 0 && getTicksSinceLastAttack() < 12) {
            if (getCurrentAttackType() == 0) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("zippleback.bite_right", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
        }
        if (isUsingAbility()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("zippleback.breath", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    private <E extends IAnimatable> PlayState attackControllerLeft(AnimationEvent<E> event) {
        if (getTicksSinceLastAttack() >= 0 && getTicksSinceLastAttack() < 12) {
            if (getCurrentAttackType() == 0) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("zippleback.bite_left", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
        }
        if (isUsingSECONDAbility()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("zippleback.breath_ignite"));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    private <E extends IAnimatable> PlayState turnController(AnimationEvent<E> event) {
        int turnState = this.getRotationState();
        if (turnState != 0 && getControllingPassenger() instanceof Player) {
            if (isFlying()) {
                boolean diving = getXRot() >= 32 && isDragonMoving();
                if (isGoingUp() || diving) {
                    event.getController().setAnimationSpeed(4);
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("zippleback.tailrot0", ILoopType.EDefaultLoopTypes.LOOP));
                    return PlayState.CONTINUE;
                } else {
                    if (turnState == 1) {
                        event.getController().setAnimationSpeed(4);
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("zippleback.lefttailrot1f", ILoopType.EDefaultLoopTypes.LOOP));
                        return PlayState.CONTINUE;
                    } else if (turnState == 2) {
                        event.getController().setAnimationSpeed(4);
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("zippleback.lefttailrot2f", ILoopType.EDefaultLoopTypes.LOOP));
                        return PlayState.CONTINUE;
                    } else if (turnState == -1) {
                        event.getController().setAnimationSpeed(4);
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("zippleback.righttailrot1f", ILoopType.EDefaultLoopTypes.LOOP));
                        return PlayState.CONTINUE;
                    } else if (turnState == -2) {
                        event.getController().setAnimationSpeed(4);
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("zippleback.righttailrot2f", ILoopType.EDefaultLoopTypes.LOOP));
                        return PlayState.CONTINUE;
                    }
                }
            } else {
                if (turnState == 1) {
                    event.getController().setAnimationSpeed(4);
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("zippleback.lefttailrot1", ILoopType.EDefaultLoopTypes.LOOP));
                    return PlayState.CONTINUE;
                } else if (turnState == 2) {
                    event.getController().setAnimationSpeed(4);
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("zippleback.lefttailrot2", ILoopType.EDefaultLoopTypes.LOOP));
                    return PlayState.CONTINUE;
                } else if (turnState == -1) {
                    event.getController().setAnimationSpeed(4);
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("zippleback.righttailrot1", ILoopType.EDefaultLoopTypes.LOOP));
                    return PlayState.CONTINUE;
                } else if (turnState == -2) {
                    event.getController().setAnimationSpeed(4);
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("zippleback.righttailrot2", ILoopType.EDefaultLoopTypes.LOOP));
                    return PlayState.CONTINUE;
                }
            }
        } else {
            event.getController().setAnimationSpeed(4);
            event.getController().setAnimation(new AnimationBuilder().addAnimation("zippleback.tailrot0", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;

    }

    // Animation
    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<ZippleBack>(this, "basic_MovementController", getTransitionTicks(), this::basicMovementController));
        data.addAnimationController(new AnimationController<ZippleBack>(this, "attack_Controller", 0, this::attackController));
        data.addAnimationController(new AnimationController<ZippleBack>(this, "attack_Controller_Left", 0, this::attackControllerLeft));
        data.addAnimationController(new AnimationController<ZippleBack>(this, "turnController", 35, this::turnController));
    }

    public ZippleBack(EntityType<? extends ADragonBaseFlyingRideable> entityType, Level level) {
        super(entityType, level);

    }

    @Override
    protected boolean canCarryCargo() {
        return true;
    }

    @Override
    protected double extraRidersYOffset() {
        return 1.3D;
    }

    protected int getMaxPassengerCapacity() {
        return 3;
    }

    @Override
    protected double rider1YOffSet() {
        return 1.3D;
    }

    @Override
    public float getRideCameraDistanceBack() {
        if (!isFlying()) {
            return 5;
        } else {
            return 12F;
        }
    }

    @Override
    public float getRideCameraDistanceFront() {
        if (isFlying()) {
            return 7;
        } else {
            return 4;
        }
    }

    //  Attributes
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 100)
                .add(Attributes.ARMOR, 0)
                .add(Attributes.MOVEMENT_SPEED, 0.4F)
                .add(Attributes.FLYING_SPEED, 0.14F)
                .add(Attributes.FOLLOW_RANGE, 32F)
                .add(Attributes.ATTACK_DAMAGE, 6)
                .add(ForgeMod.SWIM_SPEED.get(), 0.8F);
    }

    public Vec3 getThroatPos(ADragonBase entity) {
        Vec3 bodyOrigin = position();

        float angle = (float) ((float) (Math.PI / 180) * this.yBodyRot + (Math.PI / 180 * 10));
        double x = Math.sin(Math.PI + angle) * 6;
        double y = 2D;
        double z = Math.cos(angle) * 6;
        float scale = isBaby() ? 0.2F : 1;
        Vec3 throatPos = bodyOrigin.add(new Vec3(x * scale, y * scale, z * scale));
        return throatPos;

    }

    public Vec3 get2ndHeadThroatPos(ADragonBase entity) {
        Vec3 bodyOrigin = position();

        float angle = (float) ((float) (Math.PI / 180) * this.yBodyRot - (Math.PI / 180 * 10));
        double x = Math.sin(Math.PI + angle) * 4;
        double y = 3.8D;
        double z = Math.cos(angle) * 4;
        float scale = isBaby() ? 0.2F : 1;
        Vec3 throatPos = bodyOrigin.add(new Vec3(x * scale, y * scale, z * scale));
        return throatPos;

    }

    @javax.annotation.Nullable
    public <T extends ZippleBackAOECloud> T getNearestGasCloud(List<? extends T> pEntities, @javax.annotation.Nullable LivingEntity attacker, double pX, double pY, double pZ) {
        double d0 = -1.0D;
        T t = null;

        for (T t1 : pEntities) {
            if (test(attacker, t1, 17, 3D, 3D)) {
                double d1 = t1.distanceToSqr(pX, pY, pZ);
                if (d0 == -1.0D || d1 < d0) {
                    d0 = d1;
                    t = t1;
                }
            }
        }
        return t;
    }

    public boolean test(@javax.annotation.Nullable LivingEntity pAttacker, ZippleBackAOECloud pTarget, double yRange, double xRange, double zRange) {
        if (pAttacker != null) {
            if (yRange > 0.0D || xRange > 0.0D || zRange > 0.0D) {
                double d1 = Math.max(yRange, 1.0D);
                double d2 = Math.max(xRange, 1.5D);
                double d3 = Math.max(zRange, 1.5D);
                double x = getX() - pTarget.getX();
                double y = getY() - pTarget.getY();
                double z = getZ() - pTarget.getZ();

                if (y > d1 * d1 || y < -2) {
                    return false;
                }
                if (x > d2 * d2) {
                    return false;
                }
                if (z > d3 * d3) {
                    return false;
                }
            }

            return this.getSensing().hasLineOfSight(pTarget);
        }
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        Vec3 t = getThroatPos(this);
        Vec3 t1 = get2ndHeadThroatPos(this);

        if (isUsingSECONDAbility()) {
            level.addParticle(ParticleTypes.LAVA, t1.x, t1.y, t1.z, 1, 1, 1);
            ticksUsingSecondAbility++;
        } else {
            ticksUsingSecondAbility = 0;
        }

        if (isUsingSECONDAbility()) {
            modifySecondaryFuel(-1);
        }

        ZippleBackAOECloud zipCloud = this.getNearestGasCloud(level.getEntitiesOfClass(ZippleBackAOECloud.class, getTargetSearchArea(this.getFollowDistance())),
                this, this.getX(), this.getEyeY(), this.getZ());

        if (isUsingSECONDAbility() && random.nextInt(7) == 1) {
            playSound(SoundEvents.FLINTANDSTEEL_USE, 15, 1);
        }

        if (zipCloud != null) {
            if (ticksUsingSecondAbility > 2) {
//                playSound(ModSounds.HIDEOUS_ZIPPLEBACK_LIGHTER.get(), 15,1);
                zipCloud.hurt(DamageSource.IN_FIRE, 1);
            }

            if (getControllingPassenger() == null && !isTame()) {
                if (random.nextInt(50) == 1) {
                    ticksUsingSecondAbility = 4;
                    level.addParticle(ParticleTypes.LAVA, t1.x, t1.y, t1.z, 1, 1, 1);
                } else {
                    ticksUsingSecondAbility = 0;
                }
            }
        }

        if (this.getEffect(MobEffects.POISON) != null) {
            this.removeEffect(MobEffects.POISON);
        }

        String s = ChatFormatting.stripFormatting(this.getName().getString());
        if (s != null) {
            if (s.equals("Barf and Belch") || s.equals("barf and belch") || s.equals("Barf & Belch") || s.equals("barf & belch") || s.equals("Barf && Belch") || s.equals("barf && belch")) {
                this.setDragonVariant(0);
            }
        }
    }

    @Override
    public void firePrimary(Vec3 riderLook, Vec3 throat) {
        if (random.nextInt(3) == 1) {
            ZipBreathProjectile fireProj = new ZipBreathProjectile(this, throat, riderLook, level);
            fireProj.shoot(riderLook, 1F, 5F);
            level.addFreshEntity(fireProj);
        }
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @javax.annotation.Nullable SpawnGroupData pSpawnData, @javax.annotation.Nullable CompoundTag pDataTag) {
        pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        if (random.nextInt(10) == 1) {
            this.setDragonVariant(7);
        } else {
            this.setDragonVariant(this.random.nextInt(getMaxAmountOfVariants()));
        }

        return pSpawnData;
    }

    @Override
    public int getMaxFuel() {
        return 60;
    }

    @Override
    public int getMaxAmountOfVariants() {
        return 7;
    }

    @Override
    protected int breathBarRegenSpeed() {
        return 20;
    }

    @Override
    protected int breathBarRegenAmount() {
        return 2;
    }


    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(1, new T4DragonPotionRequirement(this, 1));
    }

    @Override
    public @Nullable ADragonEggBase getBreedEggResult(ServerLevel level, @NotNull AgeableMob parent) {
        ZippleBackEgg dragon = ModEntities.ZIPPLEBACK_EGG.get().create(level);
        return dragon;
    }

    @Override
    protected int getAggressionType() {
        return 2;
    }

    @Override
    protected int getInLoveCoolDownInMCDays() {
        return 4;
    }

    protected SoundEvent getAmbientSound() {
        if (this.isDragonSleeping()) {
            return ModSounds.HIDEOUS_ZIPPLEBACK_SLEEP.get();
        } else {
            return ModSounds.HIDEOUS_ZIPPLEBACK_GROWL.get();
        }
    }

    protected SoundEvent getTameSound() {
        return ModSounds.HIDEOUS_ZIPPLEBACK_TAME.get();
    }

    protected SoundEvent getProjectileSound() {
        return ModSounds.HIDEOUS_ZIPPLEBACK_POISON_BREATH.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return ModSounds.HIDEOUS_ZIPPLEBACK_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return ModSounds.HIDEOUS_ZIPPLEBACK_DEATH.get();
    }

    @Override
    protected SoundEvent get1stAttackSound() {
        return ModSounds.HIDEOUS_ZIPPLEBACK_BITE.get();
    }

    @Override
    protected boolean isItemStackForTaming(ItemStack stack) {
        return Ingredient.of(ZIPPLEBACK_TAME_FOOD).test(stack);
    }
    @Override
    public boolean isBreedingFood(ItemStack pStack) {
        return Ingredient.of(ZIPPLEBACK_BREED_FOOD).test(pStack);
    }
}
