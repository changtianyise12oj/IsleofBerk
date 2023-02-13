package com.GACMD.isleofberk.entity.dragons.nightfury;

import com.GACMD.isleofberk.entity.AI.taming.T4DragonPotionRequirement;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideableProjUser;
import com.GACMD.isleofberk.entity.eggs.entity.base.ADragonEggBase;
import com.GACMD.isleofberk.entity.eggs.entity.eggs.NightFuryEgg;
import com.GACMD.isleofberk.entity.projectile.abase.BaseLinearFlightProjectile;
import com.GACMD.isleofberk.entity.projectile.proj_user.furybolt.FuryBolt;
import com.GACMD.isleofberk.registery.ModEntities;
import com.GACMD.isleofberk.registery.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class NightFury extends ADragonBaseFlyingRideableProjUser {

    AnimationFactory factory = new AnimationFactory(this);
    private static final EntityDataAccessor<Integer> GLOW_VARIANT = SynchedEntityData.defineId(NightFury.class, EntityDataSerializers.INT);

    /**
     * 0 is default - value is up and positive value is down
     *
     * @param event
     * @param <E>
     * @return
     */
    private <E extends IAnimatable> PlayState basicMovementController(AnimationEvent<E> event) {

        // flying animations
        if (isFlying()) {
            if (event.isMoving()){

                // mounted flying
                if (this.isVehicle()) {
                    if (this.getXRot() < 8 || isGoingUp() || getPassengers().size() > 2) {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.flap", ILoopType.EDefaultLoopTypes.LOOP));
                        setShouldPlayFlapping(true);
                    }
                    if (this.getXRot() >= 8 && this.getXRot() < 33 && !isGoingUp()) {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.glide", ILoopType.EDefaultLoopTypes.LOOP));
                        setShouldPlayFlapping(false);
                    }
                    if (this.getXRot() >= 33 && !isGoingUp()) {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.dive", ILoopType.EDefaultLoopTypes.LOOP));
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
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.flap", ILoopType.EDefaultLoopTypes.LOOP));
                    } else {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.dive", ILoopType.EDefaultLoopTypes.LOOP));
                    }
                }
                // free flying
                else {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.flap", ILoopType.EDefaultLoopTypes.LOOP));
                    setShouldPlayFlapping(true);
                }
            } else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.hover", ILoopType.EDefaultLoopTypes.LOOP));
                setShouldPlayFlapping(true);
            }

            //ground animations
        } else {
            if (this.isDragonSitting() && !this.isDragonSleeping()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.sit", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
            if (this.isDragonSleeping()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.sleep", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
            if (event.isMoving() && !shouldStopMovingIndependently()) {
                if (getTarget() != null && !getTarget().isDeadOrDying() && distanceTo(getTarget()) < 14 || isVehicle()) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.run", ILoopType.EDefaultLoopTypes.LOOP));
                } else {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.walk", ILoopType.EDefaultLoopTypes.LOOP));
                }
                return PlayState.CONTINUE;
            } else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.idle", ILoopType.EDefaultLoopTypes.LOOP));
            }
        }
        return PlayState.CONTINUE;
    }

    //     Attack animations
    private <E extends IAnimatable> PlayState attackController(AnimationEvent<E> event) {
        if (getTicksSinceLastAttack() >= 0 && getTicksSinceLastAttack() < 12 && getCurrentAttackType() == 0) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.bite", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        if (isMarkFired()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.breath"));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    // Dragon turn animations
    private <E extends IAnimatable> PlayState turnController(AnimationEvent<E> event) {
        int turnState = this.getRotationState();
        event.getController().setAnimationSpeed(4);
        if (turnState != 0) {
            if (isFlying()) {
                if (turnState == 1) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.tailrotleft1f", ILoopType.EDefaultLoopTypes.LOOP));
                } else if (turnState == 2) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.tailrotleft2f", ILoopType.EDefaultLoopTypes.LOOP));
                } else if (turnState == -1) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.tailrotright1f", ILoopType.EDefaultLoopTypes.LOOP));
                } else if (turnState == -2) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.tailrotright2f", ILoopType.EDefaultLoopTypes.LOOP));
                }
            } else {
                if (turnState == 1) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.tailrotleft1", ILoopType.EDefaultLoopTypes.LOOP));
                } else if (turnState == 2) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.tailrotleft2", ILoopType.EDefaultLoopTypes.LOOP));
                } else if (turnState == -1) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.tailrotright1", ILoopType.EDefaultLoopTypes.LOOP));
                } else if (turnState == -2) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.tailrotright2", ILoopType.EDefaultLoopTypes.LOOP));
                }
            }
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.tailrot0", ILoopType.EDefaultLoopTypes.LOOP));
        }
        return PlayState.CONTINUE;
    }

    // Animation
    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<NightFury>(this, "basic_MovementController", 4, this::basicMovementController));
        data.addAnimationController(new AnimationController<NightFury>(this, "attack_Controller", 0, this::attackController));
        data.addAnimationController(new AnimationController<NightFury>(this, "turnController", 35, this::turnController));
    }

    public NightFury(EntityType<? extends NightFury> entityType, Level level) {
        super(entityType, level);

    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @javax.annotation.Nullable SpawnGroupData pSpawnData, @javax.annotation.Nullable CompoundTag pDataTag) {
        pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        if (random.nextInt(1000) == 1) {
            this.setDragonVariant(5);
        } else {
            this.setDragonVariant(this.random.nextInt(getMaxAmountOfVariants()));
        }
        this.setGlowVariant(getMaxAmountOfGlowVariants());
        return pSpawnData;
    }

    @Override
    public int getMaxAmountOfVariants() {
        return 5;
    }

    public int getMaxAmountOfGlowVariants() {
        return 2;
    }

    @Override
    public float getRideCameraDistanceBack() {
        if (!isFlying()) {
            return 4;
        } else {
            return 11F;
        }
    }

    public int getGlowVariants() {
        return this.entityData.get(GLOW_VARIANT);
    }

    public void setGlowVariant(int flying) {
        this.entityData.set(GLOW_VARIANT, flying);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(GLOW_VARIANT, 0);
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("glow_variants", this.getGlowVariants());
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setGlowVariant(pCompound.getInt("glow_variants"));
    }

    @Override
    public float getRideCameraDistanceFront() {
        return 3;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(1, new T4DragonPotionRequirement(this, 1));
    }

    @Override
    public boolean hurt(@NotNull DamageSource pSource, float pAmount) {
        return super.hurt(pSource, pAmount);
    }

    //  Attributes
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 100.0D)
                .add(Attributes.ARMOR, 1)
                .add(Attributes.ARMOR_TOUGHNESS, 16)
                .add(Attributes.MOVEMENT_SPEED, 0.4F)
                .add(Attributes.FLYING_SPEED, 0.20F)
                .add(Attributes.ATTACK_DAMAGE, 8F)
                .add(Attributes.FOLLOW_RANGE, 32F)
                .add(ForgeMod.SWIM_SPEED.get(), 0.8F);

    }

    @Override
    protected boolean isItemStackForTaming(ItemStack stack) {
        return stack.is(Items.SALMON) || stack.is(Items.TROPICAL_FISH) || stack.is(Items.COD);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob parent) {
        NightFury dragon = ModEntities.NIGHT_FURY.get().create(level);
        return dragon;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public boolean isBreedingFood(ItemStack pStack) {
        return pStack.is(Items.HONEYCOMB);
    }

    protected int getInLoveCoolDownInMCDays() {
        return 24;
    }

    @Override
    public Vec3 getThroatPos(ADragonBase entity) {
        Vec3 bodyOrigin = this.position();
        double x = -Math.sin(this.yBodyRot * Math.PI / 180) * 2.4;
        double y = 1.5;
        double z = Math.cos(this.yBodyRot * Math.PI / 180) * 2.4;
        Vec3 throatPos = bodyOrigin.add(new Vec3(x, y, z));
        return throatPos;
    }

    @Override
    protected void playerFireProjectile(Vec3 riderLook, Vec3 throat) {
        if ((tier1() || tier2() || tier3() || tier4()) && !isUsingAbility()) {
            setTicksSinceLastFire(ticksSinceLastProjShootSet());
            FuryBolt bolt = new FuryBolt(this, throat, riderLook, level, getExplosionStrength());
            bolt.setProjectileSize(getProjsSize());
            bolt.shoot(riderLook, 1F);
            bolt.setIsLightFuryTexture(false);
            if (tier4() || tier3()) {
                playProjectileSound();

                if (level.isRaining() && getControllingPassenger() instanceof Player) {
                    LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                    lightningBolt.setPos(getX() - 6, getY() + 4, getZ() + 6);
                    level.addFreshEntity(lightningBolt);

                    LightningBolt lightningBolt1 = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                    lightningBolt1.setPos(getX() - 6, getY() + 4, getZ() + 6);
                    level.addFreshEntity(lightningBolt1);

                    LightningBolt lightningBolt2 = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                    lightningBolt2.setPos(getX() - 6, getY() + 4, getZ() + 6);
                    level.addFreshEntity(lightningBolt2);
                }
            }

            if (tier1() || tier2()) {
                playSound(SoundEvents.LAVA_POP, 18, 0.05F);
            }
            level.addFreshEntity(bolt);

            setPlayerBoltBlastPendingScale(0);
            setPlayerBoltBlastPendingStopThreshold(0);
        }
    }

    @Override
    public void dragonShootProjectile(Vec3 dragonLook, Vec3 throat) {
        if ((tier1() || tier2() || tier3() || tier4()) && !isUsingAbility()) {
            setTicksSinceLastFire(ticksSinceLastProjShootSet());
            FuryBolt bolt = new FuryBolt(this, throat, dragonLook, level, getExplosionStrength());
            bolt.shoot(dragonLook, 1F, 5);
            bolt.setProjectileSize(getProjsSize());
            if (tier4() || tier3()) {
                playProjectileSound();

                if (level.isRaining()) {
                    LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                    lightningBolt.setPos(getX() - 6, getY() + 4, getZ() + 6);
                    level.addFreshEntity(lightningBolt);

                    LightningBolt lightningBolt1 = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                    lightningBolt1.setPos(getX() - 6, getY() + 4, getZ() + 6);
                    level.addFreshEntity(lightningBolt1);

                    LightningBolt lightningBolt2 = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                    lightningBolt2.setPos(getX() - 6, getY() + 4, getZ() + 6);
                    level.addFreshEntity(lightningBolt2);
                }
            }

            if (tier1() || tier2()) {
                playSound(SoundEvents.LAVA_POP, 18, 0.05F);
            }
            level.addFreshEntity(bolt);
            bolt.setIsLightFuryTexture(false);
            setPlayerBoltBlastPendingScale(0);
            setPlayerBoltBlastPendingStopThreshold(0);
        }
    }

    @Override
    protected int ticksSinceLastProjShootSet() {
        return 12;
    }

    protected int getChanceToFire() {
        return 40;
    }

    @org.jetbrains.annotations.Nullable
    public ADragonEggBase getBreedEggResult(ServerLevel level, @NotNull AgeableMob parent) {
        NightFuryEgg dragon = ModEntities.NIGHT_FURY_EGG.get().create(level);
        return dragon;
    }

    // when hit player is target
    @Override
    public void tick() {
        super.tick();

        if (this.tier1()) {
            setProjsSize(0);
            setExplosionStrength(0);
        } else if (this.tier2()) {
            setProjsSize(1);
            setExplosionStrength(3);
        } else if (this.tier3()) {
            setProjsSize(2);
            setExplosionStrength(5);
        } else if (this.tier4()) {
            setProjsSize(3);
            setExplosionStrength(7);
        }

        String s = ChatFormatting.stripFormatting(this.getName().getString());
        if (s != null) {
            if (s.equals("Toothless") || s.equals("toothless") || s.equals("Toothlezz") || s.equals("toothlezz")) {
                this.setDragonVariant(101);
            }
        }

        performInCapacitate();
    }

    /**
     * Add bonus damage to boss mobs with high health
     *
     * @param dragon
     * @param entity
     * @return
     */
    @Override
    public float getProjectileDamage(ADragonBase dragon, Entity entity, BaseLinearFlightProjectile projectile) {
        if (projectile.getDamageTier() == 1) {
            return 22F + (entity instanceof LivingEntity livingEntity ? (float) Math.floor(livingEntity.getMaxHealth() * 0.10F) : 0F);
        } else if (projectile.getDamageTier() == 2) {
            return 23F + (entity instanceof LivingEntity livingEntity ? (float) Math.floor(livingEntity.getMaxHealth() * 0.10F) : 0F);
        } else if (projectile.getDamageTier() == 3) {
            return 24F + (entity instanceof LivingEntity livingEntity ? (float) Math.floor(livingEntity.getMaxHealth() * 0.12F) : 0F);
        } else if (projectile.getDamageTier() == 4) {
            return 25F + (entity instanceof LivingEntity livingEntity ? (float) Math.floor(livingEntity.getMaxHealth() * 0.12F) : 0F);
        }

        return 22;
    }

    @Override
    public int getMaxPlayerBoltBlast() {
        return 70;
    }

    @Override
    protected boolean canCarryCargo() {
        return true;
    }

    protected double rider1XOffSet() {
        return 0;
    }

    protected double rider1YOffSet() {
        return 1.1D;
    }

    protected double rider1ZOffSet() {
        return 0;
    }

    protected double extraRidersXOffset() {
        return 0.4D;
    }

    protected double extraRidersYOffset() {
        return 1D;
    }

    protected double extraRidersZOffset() {
        return 1;
    }

    @Override
    public float getRideCameraDistanceVert() {
        return 0.2F;
    }

    public boolean tier1() {
        return getPlayerBoltBlastPendingScale() >= getMaxPlayerBoltBlast() * 0.10 && getPlayerBoltBlastPendingScale() < getMaxPlayerBoltBlast() * 0.50;
    }

    public boolean tier2() {
        return getPlayerBoltBlastPendingScale() >= getMaxPlayerBoltBlast() * 0.50 && getPlayerBoltBlastPendingScale() < getMaxPlayerBoltBlast() * 0.65;
    }

    public boolean tier3() {
        return getPlayerBoltBlastPendingScale() >= getMaxPlayerBoltBlast() * 0.65 && getPlayerBoltBlastPendingScale() < getMaxPlayerBoltBlast();
    }

    public boolean tier4() {
        return getPlayerBoltBlastPendingScale() >= getMaxPlayerBoltBlast() * 0.92;
    }

    @Override
    protected float getAIProjPowerPercentage() {
        return 0.85F;
    }

    protected SoundEvent getAmbientSound() {
        if (this.isDragonSleeping()) {
            return ModSounds.NIGHT_FURY_SLEEP.get();
        } else {
            return ModSounds.NIGHT_FURY_GROWL.get();
        }
    }

    protected SoundEvent getTameSound() {
        return ModSounds.NIGHT_FURY_TAME.get();
    }

    protected SoundEvent getProjectileSound() {
        return ModSounds.NIGHT_FURY_FIRE.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return ModSounds.NIGHT_FURY_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return ModSounds.NIGHT_FURY_DEATH.get();
    }

    @Override
    protected SoundEvent get1stAttackSound() {
        return ModSounds.NIGHT_FURY_BITE.get();
    }

    protected int getMaxPassengerCapacity() {
        return 2;
    }
}