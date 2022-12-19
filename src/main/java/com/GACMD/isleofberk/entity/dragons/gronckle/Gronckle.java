package com.GACMD.isleofberk.entity.dragons.gronckle;

import com.GACMD.isleofberk.entity.AI.taming.T2DragonFeedTamingGoal;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideable;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideableProjUser;
import com.GACMD.isleofberk.entity.eggs.entity.eggs.GronkleEgg;
import com.GACMD.isleofberk.entity.eggs.entity.base.ADragonEggBase;
import com.GACMD.isleofberk.util.Util;
import com.GACMD.isleofberk.registery.ModEntities;
import com.GACMD.isleofberk.registery.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
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

public class Gronckle extends ADragonBaseFlyingRideableProjUser implements IAnimatable {

    protected int ticksSinceLastRamAttack = 0;
    protected int ticksSinceLastBiteAttack = 0;

    protected static final EntityDataAccessor<Integer> TICK_SINCE_LAST_RAM = SynchedEntityData.defineId(ADragonBaseFlyingRideableProjUser.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> TICKS_SINCE_STONE_FED = SynchedEntityData.defineId(ADragonBaseFlyingRideableProjUser.class, EntityDataSerializers.INT);

    public Gronckle(EntityType<? extends ADragonBaseFlyingRideable> entityType, Level level) {
        super(entityType, level);
    }

    private <E extends IAnimatable> PlayState basicMovementController(AnimationEvent<E> event) {
        if ((isFlying() && !event.isMoving())) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.Fly", ILoopType.EDefaultLoopTypes.LOOP)); // flyup
            return PlayState.CONTINUE;
        }
        if (isFlying()) {
            if (event.isMoving()) {
                if (getControllingPassenger() instanceof Player) {
                    if (this.getXRot() <= -3 || isGoingUp()) {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.Fly", ILoopType.EDefaultLoopTypes.LOOP)); //flyup
                        return PlayState.CONTINUE;
                    }
                    if (this.getXRot() > -3 && this.getXRot() <= 15 && !isGoingUp()) {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.Glide", ILoopType.EDefaultLoopTypes.LOOP)); // glide
                        return PlayState.CONTINUE;
                    }
                    if (this.getXRot() > 15 && getPassengers().size() < 2 && !isGoingUp()) {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.Dive", ILoopType.EDefaultLoopTypes.LOOP)); // dive
                        return PlayState.CONTINUE;
                    }
                } else if (getOwner() instanceof Player player && isDragonFollowing() && player.isFallFlying()) {
                    float dist = distanceTo(player);
                    double ydist = this.getY() - player.getY();
                    if (dist > 2F && ydist < 5) {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.Fly", ILoopType.EDefaultLoopTypes.LOOP)); //flyup DeadlyNadderFlyup
                        return PlayState.CONTINUE;
                    }
                    if (ydist > 5) {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.Dive", ILoopType.EDefaultLoopTypes.LOOP)); //flyup DeadlyNadderFlyup
                        return PlayState.CONTINUE;
                    }
                }else {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.Fly", ILoopType.EDefaultLoopTypes.LOOP)); //flyup
                    return PlayState.CONTINUE;
                }
            }
        }
        if (event.isMoving() && !shouldStopMovingIndependently()) {
            if (getTarget() != null && !getTarget().isDeadOrDying() && distanceTo(getTarget()) < 14 || isVehicle()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.Run", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            } else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.Walk", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
        }

        if (this.isDragonSitting()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.Sit", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        if (this.isDragonSleeping()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.Sleep", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.Idle", ILoopType.EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState attackController(AnimationEvent<E> event) {
        if (ticksSinceLastAttack >= 0 && ticksSinceLastAttack < 12) {
            if (getCurrentAttackType() == 0) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.Bite", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            } else if (getCurrentAttackType() == 1) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.Ram", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
        }

        if (isMarkFired()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.Breath"));
            return PlayState.CONTINUE;
        }

        return PlayState.STOP;
    }

    private <E extends IAnimatable> PlayState turnController(AnimationEvent<E> event) {
        int turnState = this.getRotationState();
        if (turnState != 0) {
            if (turnState == 1) {
                event.getController().setAnimationSpeed(4);
                event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.tailrotleft1", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            } else if (turnState == 2) {
                event.getController().setAnimationSpeed(4);
                event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.tailrotleft2", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            } else if (turnState == -1) {
                event.getController().setAnimationSpeed(4);
                event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.tailrotright1", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            } else if (turnState == -2) {
                event.getController().setAnimationSpeed(4);
                event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.tailrotright2", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
        } else {
            event.getController().setAnimationSpeed(4);
            event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.TailRot0", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }


    // Animation
    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<Gronckle>(this, "basic_MovementController", 4, this::basicMovementController));
        data.addAnimationController(new AnimationController<Gronckle>(this, "attack_Controller", 0, this::attackController));
        data.addAnimationController(new AnimationController<Gronckle>(this, "turnController", 24, this::turnController));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TICK_SINCE_LAST_RAM, 0);
        this.entityData.define(TICKS_SINCE_STONE_FED, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("ticks_since_ram_attack", getTicksSincePlayerLastRamAttack());
        pCompound.putInt("ticks_since_last_stone_fed", getStoneDigestionTicks());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setTicksSincePlayerLastRamAttack(pCompound.getInt("ticks_since_ram_attack"));
        this.setTicksSincelastStoneFed(pCompound.getInt("ticks_since_last_stone_fed"));
    }

    public int getTicksSincePlayerLastRamAttack() {
        return entityData.get(TICK_SINCE_LAST_RAM);
    }

    public void setTicksSincePlayerLastRamAttack(int ticksSinceLastRamAttack) {
        entityData.set(TICK_SINCE_LAST_RAM, ticksSinceLastRamAttack);
    }

    public int getStoneDigestionTicks() {
        return entityData.get(TICKS_SINCE_STONE_FED);
    }

    public void setTicksSincelastStoneFed(int ticksSinceLastRamAttack) {
        entityData.set(TICKS_SINCE_STONE_FED, ticksSinceLastRamAttack);
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @javax.annotation.Nullable SpawnGroupData pSpawnData, @javax.annotation.Nullable CompoundTag pDataTag) {
        pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        this.setDragonVariant(this.random.nextInt(getMaxAmountOfVariants()));
        return pSpawnData;
    }

    @Override
    public int getMaxAmountOfVariants() {
        return 10;
    }

    @Override
    public void positionRider(Entity pPassenger) {
        super.positionRider(pPassenger);
        pPassenger.setPos(this.getX(), this.getY() + 1, this.getZ());

    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(1, new T2DragonFeedTamingGoal(this, 1));
        this.targetSelector.addGoal(2, new NonTameRandomTargetGoal<>(this, Animal.class, false, PREY_SELECTOR));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
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
    public @NotNull InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack stack = pPlayer.getItemInHand(pHand);
        Item item = stack.getItem();

        if (isTame()) {
            if (!isDragonSleeping() || isDragonSitting()) {
                if (!stack.isEmpty()) {
                    if (getStoneDigestionTicks() <= 0) {
                        if (item == Blocks.BASALT.asItem() ||
                                item == Blocks.DEEPSLATE.asItem() ||
                                item == Blocks.GRANITE.asItem() ||
                                item == Blocks.STONE.asItem() ||
                                item == Blocks.DIORITE.asItem()
                        ) {
                            stack.shrink(1);
                            setTicksSincelastStoneFed(Util.secondsToTicks(3));
                            this.playSound(SoundEvents.DONKEY_EAT, 1, 1);
                        }
                    }
                }
            }
        }
        return super.mobInteract(pPlayer, pHand);
    }

    public ItemEntity shootGronckIron(ItemStack pStack) {
        if (pStack.isEmpty()) {
            return null;
        } else if (this.level.isClientSide) {
            return null;
        } else {
            ItemEntity itementity = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), pStack);
            itementity.setDefaultPickUpDelay();
            double d0 = getThroatPos(this).x() - this.getX();
            double d1 = getThroatPos(this).y() - this.getY();
            double d2 = getThroatPos(this).z() - this.getZ();
            double d3 = Math.sqrt(d0 * d0 + d2 * d2);
            shootItem(itementity, d0, d1 + d3 * (double) 0.2F, d2, 0.5F, (float) (14 - 3));
            if (captureDrops() != null) captureDrops().add(itementity);
            else
                this.level.addFreshEntity(itementity);
            return itementity;
        }
    }

    public void shootItem(ItemEntity itemEntity, double pX, double pY, double pZ, float pVelocity, float pInaccuracy) {
        Vec3 vec3 = (new Vec3(pX, pY, pZ)).normalize().add(this.random.nextGaussian() * (double) 0.0075F * (double) pInaccuracy, this.random.nextGaussian() * (double) 0.0075F * (double) pInaccuracy, this.random.nextGaussian() * (double) 0.0075F * (double) pInaccuracy).scale((double) pVelocity);
        itemEntity.setDeltaMovement(vec3);
        double d0 = vec3.horizontalDistance();
        itemEntity.setYRot((float) (Mth.atan2(vec3.x, vec3.z) * (double) (180F / (float) Math.PI)));
        itemEntity.setXRot((float) (Mth.atan2(vec3.y, d0) * (double) (180F / (float) Math.PI)));
        itemEntity.yRotO = itemEntity.getYRot();
        itemEntity.xRotO = itemEntity.getXRot();
    }


    @Override
    protected void rideInteract(Player pPlayer, InteractionHand pHand, ItemStack itemstack) {
        Item item = itemstack.getItem();
        if (item != Blocks.BASALT.asItem() &&
                item != Blocks.STONE.asItem() &&
                item != Blocks.DIORITE.asItem() &&
                item != Blocks.GRANITE.asItem() &&
                item != Blocks.DEEPSLATE.asItem()) {
            super.rideInteract(pPlayer, pHand, itemstack);
        }
    }

    /**
     * 0 : none; 1: weapon; 2: armor or weapon 3: always, only be obtained via eggs
     */
    @Override
    protected int getAggressionType() {
        return super.getAggressionType();
    }

    //  Attributes
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 120.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3F)
                .add(Attributes.ARMOR, 20)
                .add(Attributes.FLYING_SPEED, 0.06F)
                .add(Attributes.ATTACK_DAMAGE, 17F)
                .add(Attributes.FOLLOW_RANGE, 4.5F)
                .add(ForgeMod.STEP_HEIGHT_ADDITION.get(), 1F);
    }

    @Override
    public void swing(InteractionHand pHand) {
        int random1 = random.nextInt(200);
        if (random1 > 1) {
            ticksSinceLastBiteAttack = Util.secondsToTicks(3);
            ticksSinceLastRamAttack = 0;
        }

        if (random1 > 100) {
            ticksSinceLastRamAttack = Util.secondsToTicks(3);
            ticksSinceLastBiteAttack = 0;
        }
        super.swing(pHand);
    }

    @Override
    public void tick() {
        super.tick();
        ItemStack itemStack = new ItemStack(ModItems.RAW_GRONCKLE_IRON.get(), 1);
        if (getStoneDigestionTicks() == 10) {
            if (random.nextInt(45) == 1) {
                this.playSound(SoundEvents.PLAYER_BURP, 1, 1);
                this.playSound(SoundEvents.GENERIC_BURN, 1, 1);
                this.shootGronckIron(itemStack);
                setTicksSinceLastFire(20);
                addParticlesAroundSelf(ParticleTypes.LAVA);
                addParticlesAroundSelf(ParticleTypes.LARGE_SMOKE);
            } else {
                addParticlesAroundSelf(ParticleTypes.LARGE_SMOKE);
                this.playSound(SoundEvents.LAVA_POP, 1, 1);
            }
        }

        if (getStoneDigestionTicks() > 0) {
            setTicksSincelastStoneFed(getStoneDigestionTicks() - 1);
        }

        if (getTicksSinceLastFire() > 0) {
            setTicksSinceLastFire(getTicksSinceLastFire() - 1);
        }
        if (ticksSinceLastBiteAttack >= 0) {
            ticksSinceLastBiteAttack--;
        }

        if (ticksSinceLastRamAttack >= 0) {
            ticksSinceLastRamAttack--;
        }

        if (getTicksSinceLastFire() < 2) {
            setMarkFired(false);
        } else {
            setMarkFired(true);
        }

        if (getTicksSincePlayerLastRamAttack() >= 0) {
            setTicksSincePlayerLastRamAttack(getTicksSincePlayerLastRamAttack() - 1);
        }

        // 0 ram attack
        // 1 bite attack
        if (ticksSinceLastRamAttack >= 0) {
            setCurrentAttackType(0);
        } else if (ticksSinceLastBiteAttack >= 0) {
            setCurrentAttackType(1);
        }

        if (this.tier1()) {
            setProjsSize(0);
            setExplosionStrength(0);
        } else if (this.tier2()) {
            setProjsSize(1);
            setExplosionStrength(3);
        } else if (this.tier3()) {
            setExplosionStrength(3);
            setProjsSize(2);
        } else if (this.tier4()) {
            setProjsSize(3);
            setExplosionStrength(4);
        }

        String s = ChatFormatting.stripFormatting(this.getName().getString());
        if (s != null) {
            if (s.equals("Meatlug") || s.equals("meatlug")) {
                this.setDragonVariant(0);
            }
        }

    }

    @Override
    protected boolean canCarryCargo() {
        return true;
    }

    protected double rider1XOffSet() {
        return 0;
    }

    protected double rider1YOffSet() {
        return 1.8D;
    }

    protected double rider1ZOffSet() {
        return 0;
    }

    protected double rider2XOffSet() {
        return 1;
    }

    protected double rider2YOffSet() {
        return 1.8D;
    }

    protected double rider2ZOffSet() {
        return 1;
    }

    @org.jetbrains.annotations.Nullable
    public ADragonEggBase getBreedEggResult(ServerLevel level, @NotNull AgeableMob parent) {
        GronkleEgg dragon = ModEntities.GRONCKLE_EGG.get().create(level);
        return dragon;
    }

    @Override
    public float getRideCameraDistanceVert() {
        return 0.2F;
    }

}
