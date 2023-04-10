package com.GACMD.isleofberk.entity.dragons.gronckle;

import com.GACMD.isleofberk.config.ModConfigs;
import com.GACMD.isleofberk.entity.AI.taming.T2DragonFeedTamingGoal;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideable;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideableProjUser;
import com.GACMD.isleofberk.entity.eggs.entity.base.ADragonEggBase;
import com.GACMD.isleofberk.entity.eggs.entity.eggs.GronkleEgg;
import com.GACMD.isleofberk.registery.ModEntities;
import com.GACMD.isleofberk.registery.ModItems;
import com.GACMD.isleofberk.registery.ModSounds;
import com.GACMD.isleofberk.util.Util;
import com.GACMD.isleofberk.util.math.MathX;
import net.minecraft.ChatFormatting;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddMobPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.entity.PartEntity;
import net.minecraftforge.registries.ForgeRegistries;
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

import java.util.List;
import java.util.Objects;

import static com.GACMD.isleofberk.registery.ModTags.Items.*;

public class Gronckle extends ADragonBaseFlyingRideableProjUser implements IAnimatable {

    protected int ticksSinceLastRamAttack = 0;
    protected int ticksSinceLastBiteAttack = 0;
    protected int ticksSinceLastRamAttackPlayer = 0;

    protected static final EntityDataAccessor<Integer> TICK_SINCE_LAST_RAM = SynchedEntityData.defineId(Gronckle.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> TICKS_SINCE_STONE_FED = SynchedEntityData.defineId(Gronckle.class, EntityDataSerializers.INT);
    DragonPart[] subParts;
    DragonPart GronckleRamArea;
    private ItemStack pStack;

    public Gronckle(EntityType<? extends ADragonBaseFlyingRideable> entityType, Level level) {
        super(entityType, level);
        this.GronckleRamArea = new DragonPart(this, "GronckleRamArea", 1.6F, 1.6F);
        this.subParts = new DragonPart[]{this.GronckleRamArea};
    }

    private <E extends IAnimatable> PlayState basicMovementController(AnimationEvent<E> event) {

        // flying animations
        if (isFlying()) {
            if (isDragonMoving()){

                // mounted flying
                if (this.isVehicle()) {
                    if (this.getXRot() < 8 || isGoingUp() || getPassengers().size() > 2) {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.Fly", ILoopType.EDefaultLoopTypes.LOOP));
                        setShouldPlayFlapping(true);
                    }
                    if (this.getXRot() >= 8 && this.getXRot() < 33 && !isGoingUp()) {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.Glide", ILoopType.EDefaultLoopTypes.LOOP));
                        setShouldPlayFlapping(false);
                    }
                    if (this.getXRot() >= 33 && !isGoingUp()) {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.Dive", ILoopType.EDefaultLoopTypes.LOOP));
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
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.Fly", ILoopType.EDefaultLoopTypes.LOOP));
                    } else {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.Dive", ILoopType.EDefaultLoopTypes.LOOP));
                    }
                }
                // free flying
                else {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.Fly", ILoopType.EDefaultLoopTypes.LOOP));
                    setShouldPlayFlapping(true);
                }
            } else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.Hover", ILoopType.EDefaultLoopTypes.LOOP));
                setShouldPlayFlapping(true);
            }

            //ground animations
        } else {
            if (this.isDragonSitting() && !this.isDragonSleeping()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.Sit", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
            if (this.isDragonSleeping()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.Sleep", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
            if (isDragonMoving() && !shouldStopMovingIndependently()) {
                if (getTarget() != null && !getTarget().isDeadOrDying() && distanceTo(getTarget()) < 14 || isVehicle()) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.Run", ILoopType.EDefaultLoopTypes.LOOP));
                } else {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.Walk", ILoopType.EDefaultLoopTypes.LOOP));
                }
                return PlayState.CONTINUE;
            } else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.Idle", ILoopType.EDefaultLoopTypes.LOOP));
            }
        }
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState attackController(AnimationEvent<E> event) {
        if (getTicksSinceLastAttack() >= 0 && getTicksSinceLastAttack() < 12) {
            if (getCurrentAttackType() == 0) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.Bite", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            } else if (getCurrentAttackType() == 1) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.Ram", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
        }

        if (ticksSinceLastRamAttackPlayer > 0) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.Ram", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }

        if (isMarkFired()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.Breath"));
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
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.tailrotleft1f", ILoopType.EDefaultLoopTypes.LOOP));
                } else if (turnState == 2) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.tailrotleft2f", ILoopType.EDefaultLoopTypes.LOOP));
                } else if (turnState == -1) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.tailrotright1f", ILoopType.EDefaultLoopTypes.LOOP));
                } else if (turnState == -2) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.tailrotright2f", ILoopType.EDefaultLoopTypes.LOOP));
                }
            } else {
                if (turnState == 1) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.tailrotleft1", ILoopType.EDefaultLoopTypes.LOOP));
                } else if (turnState == 2) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.tailrotleft2", ILoopType.EDefaultLoopTypes.LOOP));
                } else if (turnState == -1) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.tailrotright1", ILoopType.EDefaultLoopTypes.LOOP));
                } else if (turnState == -2) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.tailrotright2", ILoopType.EDefaultLoopTypes.LOOP));
                }
            }
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("Gronckle.TailRot0", ILoopType.EDefaultLoopTypes.LOOP));
        }
        return PlayState.CONTINUE;
    }

    // Animation
    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<Gronckle>(this, "basic_MovementController", getTransitionTicks(), this::basicMovementController));
        data.addAnimationController(new AnimationController<Gronckle>(this, "attack_Controller", 0, this::attackController));
        data.addAnimationController(new AnimationController<Gronckle>(this, "turnController", 35, this::turnController));
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

    @Override
    public PartEntity<?>[] getParts() {
        return this.subParts;
    }

    @Override
    public void recreateFromPacket(@NotNull ClientboundAddMobPacket mobPacket) {
        super.recreateFromPacket(mobPacket);
        PartEntity<?>[] part = this.getParts();

        for (int i = 0; i < Objects.requireNonNull(part).length; ++i) {
            part[i].setId(i + mobPacket.getId());
        }

    }

    @Override
    public @NotNull Packet<?> getAddEntityPacket() {
        return new ClientboundAddMobPacket(this);
    }

    private void tickPart(DragonPart pPart, double pOffsetX, double pOffsetY, double pOffsetZ) {
        Vec3 lastPos = new Vec3(pPart.getX(), pPart.getY(), pPart.getZ());
        pPart.setPos(this.getX() + pOffsetX, this.getY() + pOffsetY, this.getZ() + pOffsetZ);

        pPart.xo = lastPos.x;
        pPart.yo = lastPos.y;
        pPart.zo = lastPos.z;
        pPart.xOld = lastPos.x;
        pPart.yOld = lastPos.y;
        pPart.zOld = lastPos.z;

    }

    @Override
    public boolean isMultipartEntity() {
        return !isBaby();
    }

    @Override
    public void aiStep() {
        super.aiStep();
        float yRotRadians = MathX.toRadians(this.getYRot());
        float sinY = Mth.sin(yRotRadians);
        float cosY = Mth.cos(yRotRadians);

        this.tickPart(this.GronckleRamArea, 2.2 * -sinY * 1, 0.4D, 2.2 * cosY * 1);

        if (isUsingSECONDAbility() && getTicksSincePlayerLastRamAttack() == 0) {
            ticksSinceLastRamAttackPlayer = 20;
        } else {
            if (ticksSinceLastRamAttackPlayer > 0) {
                ticksSinceLastRamAttackPlayer -= 1;
            } else {
                ticksSinceLastRamAttackPlayer = 0;
            }
        }

        if (ticksSinceLastRamAttackPlayer == 18) {
            this.setTicksSincePlayerLastRamAttack(Util.secondsToTicks(1));
            this.knockBack(this.level.getEntities(this, this.GronckleRamArea.getBoundingBox().inflate(0.4D, 0.4D, 0.4D).move(0.0D, -0.3D, 0.0D), EntitySelector.NO_CREATIVE_OR_SPECTATOR));

            if (!level.isClientSide()) {
                this.hurt(this.level.getEntities(this, this.GronckleRamArea.getBoundingBox().inflate(1.0D), EntitySelector.NO_CREATIVE_OR_SPECTATOR));
            }
        }
    }

    /**
     * Pushes all entities inside the list away from the enderdragon.
     */
    private void knockBack(List<Entity> pEntities) {
        double d0 = (this.GronckleRamArea.getBoundingBox().minX + this.GronckleRamArea.getBoundingBox().maxX) / 2.0D;
        double d1 = (this.GronckleRamArea.getBoundingBox().minZ + this.GronckleRamArea.getBoundingBox().maxZ) / 2.0D;

        for (Entity entity : pEntities) {
            if (entity instanceof LivingEntity && entity != this.getPassengers() && (entity.xOld == entity.getX() || entity.zOld == entity.getZ())) {
                double d2 = entity.getX() - d0;
                double d3 = entity.getZ() - d1;
                double d4 = Math.max(d2 * d2 + d3 * d3, 0.1D);
                entity.push(d2 / d4 * 0.50D, (double) 0.2F, d3 / d4 * 4.0D);
                entity.hurt(DamageSource.mobAttack(this), 5.0F);
                this.doEnchantDamageEffects(this, entity);
            }
        }
    }

    /**
     * Attacks all entities inside this list, dealing 5 hearts of damage.
     */
    private void hurt(List<Entity> pEntities) {
        for (Entity entity : pEntities) {
            if (entity instanceof LivingEntity livingEntity) {
                if(!this.getPassengers().contains(livingEntity)) {
                    playSound(SoundEvents.GENERIC_BIG_FALL, 3, 1);
                    livingEntity.hurt(DamageSource.mobAttack(this), 22F);
                    this.doEnchantDamageEffects(this, livingEntity);
                }
            }
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TICK_SINCE_LAST_RAM, 0);
        this.entityData.define(TICKS_SINCE_STONE_FED, 0);
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
        if (isGronckleIngredient(stack)) {
            if (getStoneDigestionTicks() <= 0) {
                if (isTame()) {
                    if (!isDragonSleeping() || isDragonSitting()) {
                        if (!stack.isEmpty()) {
                            setSleepDisturbTicks(Util.secondsToTicks(30));
                            stack.shrink(1);
                            setTicksSincelastStoneFed(Util.secondsToTicks(3));
                            this.playSound(SoundEvents.DONKEY_EAT, 1, 1);
                            return InteractionResult.SUCCESS;
                        }
                    }
                }
            }
            return InteractionResult.SUCCESS;
        } else {

            return super.mobInteract(pPlayer, pHand);
        }

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
        if (!isGronckleIngredient(itemstack)) {
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
                .add(Attributes.MAX_HEALTH, ModConfigs.statsConfig.groncleHealth.get())
                .add(Attributes.ARMOR, ModConfigs.statsConfig.groncleArmor.get())
                .add(Attributes.ATTACK_DAMAGE, ModConfigs.statsConfig.groncleBite.get())
                .add(Attributes.FLYING_SPEED, 0.08F)
                .add(Attributes.MOVEMENT_SPEED, 0.37F)
                .add(Attributes.FOLLOW_RANGE, 32F)
                .add(ForgeMod.SWIM_SPEED.get(), 0.25F);
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
            if (random.nextInt(35) == 1) {
                this.playSound(ModSounds.GRONCKLE_SPIT_IRON.get(), 4, 1);
                this.playSound(SoundEvents.GENERIC_BURN, 4, 1);
                this.shootGronckIron(itemStack);
                setTicksSinceLastFire(20);
                addParticlesAroundSelf(ParticleTypes.LAVA);
                addParticlesAroundSelf(ParticleTypes.LARGE_SMOKE);
            } else {
                addParticlesAroundSelf(ParticleTypes.LARGE_SMOKE);
                this.playSound(SoundEvents.LAVA_POP, 4, 1);
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

        if (ticksSinceLastRamAttackPlayer > 0) {
            ticksSinceLastRamAttackPlayer--;
        }

        if (getTicksSinceLastFire() < 2) {
            setMarkFired(false);
        } else {
            setMarkFired(true);
        }

        if (getTicksSincePlayerLastRamAttack() > 0) {
            setTicksSincePlayerLastRamAttack(getTicksSincePlayerLastRamAttack() - 1);
        }

        // 0 ram attack
        // 1 bite attack
        if (ticksSinceLastRamAttack >= 54) {
            setCurrentAttackType(0);
        } else if (ticksSinceLastBiteAttack >= 70) {
            setCurrentAttackType(1);
        }

        if (this.tier1()) {
            setProjsSize(0);
            setExplosionStrength(0);
        } else if (this.tier2()) {
            setProjsSize(1);
            setExplosionStrength(1);
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
    public float getSoundPitch() {
        return 1F;
    }

    @Override
    protected float getSoundVolume() {
        return 0.7F;
    }

    @Override
    protected boolean canCarryCargo() {
        return true;
    }

    protected double rider1XOffSet() {
        return 0;
    }

    protected double rider1YOffSet() {
        return 1.2D;
    }

    protected double rider1ZOffSet() {
        return 0;
    }

    protected double extraRidersXOffset() {
        return 0.4D;
    }

    protected double extraRidersYOffset() {
        return 1.2D;
    }

    protected double extraRidersZOffset() {
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

    @Override
    protected int getInLoveCoolDownInMCDays() {
        return 14;
    }

    protected SoundEvent getAmbientSound() {
        if (this.isDragonSleeping()) {
            return ModSounds.GRONCKLE_SLEEP.get();
        } else {
            return ModSounds.GRONCKLE_GROWL.get();
        }
    }

    @Override
    protected SoundEvent getFlapSound() {
        return ModSounds.GRONCKLE_FLAP.get();
    }

    protected SoundEvent getTameSound() {
        return ModSounds.GRONCKLE_TAME.get();
    }

    protected float getFlapPitch() {
        return 1F;
    }

    protected SoundEvent getProjectileSound() {
        return ModSounds.GRONCKLE_FIRE.get();
    }

    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return ModSounds.GRONCKLE_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return ModSounds.GRONCKLE_DEATH.get();
    }

    @Override
    protected SoundEvent get1stAttackSound() {
        return ModSounds.DEADLY_NADDER_BITE.get();
    }

    protected int getMaxPassengerCapacity() {
        return 3;
    }

    @Override
    protected boolean isItemStackForTaming(ItemStack stack) {
        return Ingredient.of(GRONCKLE_TAME_FOOD).test(stack);
    }

    @Override
    public boolean isBreedingFood(ItemStack pStack) {
        return Ingredient.of(GRONCKLE_BREED_FOOD).test(pStack);
    }

    protected boolean isGronckleIngredient(ItemStack stack) {
        return Ingredient.of(GRONCKLE_IRON_INGREDIENTS).test(stack);
    }
}
