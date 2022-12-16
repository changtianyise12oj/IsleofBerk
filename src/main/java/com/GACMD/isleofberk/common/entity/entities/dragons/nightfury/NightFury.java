package com.GACMD.isleofberk.common.entity.entities.dragons.nightfury;

import com.GACMD.isleofberk.common.entity.entities.AI.taming.T4DragonPotionRequirement;
import com.GACMD.isleofberk.common.entity.entities.base.ADragonBase;
import com.GACMD.isleofberk.common.entity.entities.base.ADragonBaseFlyingRideableProjUser;
import com.GACMD.isleofberk.common.entity.entities.eggs.entity.NightFuryEgg;
import com.GACMD.isleofberk.common.entity.entities.eggs.entity.base.ADragonEggBase;
import com.GACMD.isleofberk.common.entity.entities.projectile.abase.BaseLinearFlightProjectile;
import com.GACMD.isleofberk.common.entity.entities.projectile.proj_user.furybolt.FuryBolt;
import com.GACMD.isleofberk.common.entity.sound.IOBSounds;
import com.GACMD.isleofberk.registery.ModEntities;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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

public class NightFury extends ADragonBaseFlyingRideableProjUser implements IAnimatable {

    AnimationFactory factory = new AnimationFactory(this);

    /**
     * 0 is default - value is up and positive value is down
     *
     * @param event
     * @param <E>
     * @return
     */
    private <E extends IAnimatable> PlayState basicMovementController(AnimationEvent<E> event) {
        if ((isFlying() && !event.isMoving())) {
            // the head looks down during hover which looks awful and distorted so temporary disabled
            event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.flap", ILoopType.EDefaultLoopTypes.LOOP)); // hover
            return PlayState.CONTINUE;
        }
        if (isFlying()) {
            if (event.isMoving()) {
                if (getControllingPassenger() instanceof Player) {
//                if (this.getXRot() < 11 || isGoingUp() || getPassengers().size() > 2 || getFirstPassenger() == null) {
                    if (this.getXRot() < 11 || isGoingUp() || getPassengers().size() > 2) {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.flap", ILoopType.EDefaultLoopTypes.LOOP)); //flyup DeadlyNadderFlyup
                        return PlayState.CONTINUE;
                    }
                    if (this.getXRot() >= 11 && this.getXRot() < 26 && !isGoingUp()) { // < 20
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.glide", ILoopType.EDefaultLoopTypes.LOOP)); // glide
                        return PlayState.CONTINUE;
                    }
                    if (this.getXRot() >= 26 && !isGoingUp()) { // > 30
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.dive", ILoopType.EDefaultLoopTypes.LOOP)); // dive
                        return PlayState.CONTINUE;
                    }
                    // different values for pitch and roll when following elytra flying player
                } else if (getOwner() instanceof Player player && isDragonFollowing() && player.isFallFlying()) {
                    float dist = distanceTo(player);
                    double ydist = this.getY() - player.getY();
//                        System.out.println(ydist);
                    if (dist > 8.3F && ydist < 4) {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.flap", ILoopType.EDefaultLoopTypes.LOOP)); //flyup DeadlyNadderFlyup
                        return PlayState.CONTINUE;
                    }
                    if (dist < 8.3F || ydist > 4) {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.dive", ILoopType.EDefaultLoopTypes.LOOP)); //flyup DeadlyNadderFlyup
                        return PlayState.CONTINUE;
                    } else {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.flap", ILoopType.EDefaultLoopTypes.LOOP)); //flyup
                        return PlayState.CONTINUE;
                    }
                }

            }
        } else {
            if (this.isDragonSitting()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.sit", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
            if (event.isMoving() && !shouldStopMovingIndependently()) {
                if (getTarget() != null && !getTarget().isDeadOrDying() && distanceTo(getTarget()) < 14 || isVehicle()) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.walk", ILoopType.EDefaultLoopTypes.LOOP));
                    return PlayState.CONTINUE;

                } else {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.walk", ILoopType.EDefaultLoopTypes.LOOP));
                    return PlayState.CONTINUE;
                }
            }
            if (this.isDragonSleeping()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.sleep", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }


            event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.idle", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState attackController(AnimationEvent<E> event) {
        if (getTicksSinceLastAttack() >= 0 && getTicksSinceLastAttack() < 12) {
            if (getCurrentAttackType() == 0) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.bite", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
        }
        if (isUsingAbility()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.breath", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    private <E extends IAnimatable> PlayState turnController(AnimationEvent<E> event) {
        int turnState = this.getRotationState();
        if (turnState != 0 && getControllingPassenger() instanceof Player) {
            if (isFlying()) {
                boolean diving = getXRot() >= 32 && event.isMoving();
                if (isGoingUp() || diving) {
                    event.getController().setAnimationSpeed(4);
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.tailrot0", ILoopType.EDefaultLoopTypes.LOOP));
                    return PlayState.CONTINUE;
                } else {
                    if (turnState == 1) {
                        event.getController().setAnimationSpeed(4);
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.tailrotleft1f", ILoopType.EDefaultLoopTypes.LOOP));
                        return PlayState.CONTINUE;
                    } else if (turnState == 2) {
                        event.getController().setAnimationSpeed(4);
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.tailrotleft2f", ILoopType.EDefaultLoopTypes.LOOP));
                        return PlayState.CONTINUE;
                    } else if (turnState == -1) {
                        event.getController().setAnimationSpeed(4);
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.tailrotright1f", ILoopType.EDefaultLoopTypes.LOOP));
                        return PlayState.CONTINUE;
                    } else if (turnState == -2) {
                        event.getController().setAnimationSpeed(4);
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.tailrotright2f", ILoopType.EDefaultLoopTypes.LOOP));
                        return PlayState.CONTINUE;
                    }
                }
            } else {
                if (turnState == 1) {
                    event.getController().setAnimationSpeed(4);
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.tailrotleft1", ILoopType.EDefaultLoopTypes.LOOP));
                    return PlayState.CONTINUE;
                } else if (turnState == 2) {
                    event.getController().setAnimationSpeed(4);
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.tailrotleft2", ILoopType.EDefaultLoopTypes.LOOP));
                    return PlayState.CONTINUE;
                } else if (turnState == -1) {
                    event.getController().setAnimationSpeed(4);
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.tailrotright1", ILoopType.EDefaultLoopTypes.LOOP));
                    return PlayState.CONTINUE;
                } else if (turnState == -2) {
                    event.getController().setAnimationSpeed(4);
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.tailrotright2", ILoopType.EDefaultLoopTypes.LOOP));
                    return PlayState.CONTINUE;
                }
            }
        } else {
            event.getController().setAnimationSpeed(4);
            event.getController().setAnimation(new AnimationBuilder().addAnimation("nightfury.tailrot0", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    // Animation
    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<NightFury>(this, "nightfury_anim_controller", 5, this::basicMovementController));
        data.addAnimationController(new AnimationController<NightFury>(this, "attack_Controller", 0, this::attackController));
        data.addAnimationController(new AnimationController<NightFury>(this, "turnController", 35, this::turnController));
    }

    public NightFury(EntityType<? extends NightFury> entityType, Level level) {
        super(entityType, level);
        createAttributes();

    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @javax.annotation.Nullable SpawnGroupData pSpawnData, @javax.annotation.Nullable CompoundTag pDataTag) {
        pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        this.setDragonVariant(this.random.nextInt(getMaxAmountOfVariants()));
        return pSpawnData;
    }

    @Override
    public int getMaxAmountOfVariants() {
        return 5;
    }

    @Override
    public float getRideCameraDistanceBack() {
        if (!isFlying()) {
            return 4;
        } else {
            return 11F;
        }
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

    //  Attributes
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 120.0D)
                .add(Attributes.ARMOR, 30)
                .add(Attributes.MOVEMENT_SPEED, 0.4F)
                .add(Attributes.FLYING_SPEED, 0.18F)
                .add(Attributes.ATTACK_DAMAGE, 28F)
                .add(Attributes.FOLLOW_RANGE, 4.5F)
                .add(ForgeMod.STEP_HEIGHT_ADDITION.get(), 1F)
                .add(ForgeMod.SWIM_SPEED.get(), 0.55F);

    }

    @Override
    protected Item tameItem() {
        return Items.SALMON;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, @NotNull AgeableMob parent) {
        NightFury dragon = ModEntities.NIGHT_FURY.get().create(level);
        return dragon;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    // cannot breed night fury. they are too powerful
    @Override
    public boolean isBreedingFood(ItemStack pStack) {
        return false;
    }

    /**
     * Check if the ground with x amount of blocks below is a solid. Replacement for Vanilla onGround
     *
     * @return solidBlockState
     */
    @Override
    public boolean isDragonOnGround() {
        BlockPos solidPos = new BlockPos(this.position().x, this.position().y - 1, this.position().z);
        return !level.getBlockState(solidPos).isAir();
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
            setTicksSinceLastFire(20);
            FuryBolt bolt = new FuryBolt(this, throat, riderLook, level, getExplosionStrength());
            bolt.shoot(riderLook, 1F);
            bolt.setIsLightFuryTexture(false);
            level.addFreshEntity(bolt);
            setPlayerBoltBlastPendingScale(0);
            setPlayerBoltBlastPendingStopThreshold(0);
        }
    }

    @org.jetbrains.annotations.Nullable
    public ADragonEggBase getBreedEggResult(ServerLevel level, @NotNull AgeableMob parent) {
        NightFuryEgg dragon = ModEntities.NIGHT_FURY_EGG.get().create(level);
        return dragon;
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        return super.mobInteract(pPlayer, pHand);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.tier1()) {
            setExplosionStrength(1);
        } else if (this.tier2()) {
            setExplosionStrength(3);
        } else if (this.tier3()) {
            setExplosionStrength(5);
        } else if (this.tier4()) {
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
            return 20F + (entity instanceof LivingEntity livingEntity ? (float) Math.floor(livingEntity.getMaxHealth() * 0.10F) : 0F);
        } else if (projectile.getDamageTier() == 2) {
            return 35F + (entity instanceof LivingEntity livingEntity ? (float) Math.floor(livingEntity.getMaxHealth() * 0.12F) : 0F);
        } else if (projectile.getDamageTier() == 3) {
            return 45F + (entity instanceof LivingEntity livingEntity ? (float) Math.floor(livingEntity.getMaxHealth() * 0.20F) : 0F);
        } else if (projectile.getDamageTier() == 4) {
            return 50F + (entity instanceof LivingEntity livingEntity ? (float) Math.floor(livingEntity.getMaxHealth() * 0.30F) : 0F);
        }

        return 20;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return IOBSounds.FURY_GROWL_0.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return IOBSounds.FURY_DEATH_0.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return IOBSounds.FURY_HURT_0.get();
    }

    @Override
    public int getMaxPlayerBoltBlast() {
        return 100;
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

    protected double rider2XOffSet() {
        return 1;
    }

    protected double rider2YOffSet() {
        return 1.3D;
    }

    protected double rider2ZOffSet() {
        return 1;
    }

    @Override
    public float getRideCameraDistanceVert() {
        return 0.2F;
    }

    public boolean tier1() {
        return getPlayerBoltBlastPendingScale() >= getMaxPlayerBoltBlast() * 0.10 && getPlayerBoltBlastPendingScale() < getMaxPlayerBoltBlast() * 0.40;
    }

    public boolean tier2() {
        return getPlayerBoltBlastPendingScale() >= getMaxPlayerBoltBlast() * 0.40 && getPlayerBoltBlastPendingScale() < getMaxPlayerBoltBlast() * 0.65;
    }

    public boolean tier3() {
        return getPlayerBoltBlastPendingScale() >= getMaxPlayerBoltBlast() * 0.65 && getPlayerBoltBlastPendingScale() < getMaxPlayerBoltBlast();
    }

    public boolean tier4() {
        return getPlayerBoltBlastPendingScale() >= getMaxPlayerBoltBlast();
    }

    @Override
    protected float getAIProjPowerPercentage() {
        return 0.35F;
    }
}