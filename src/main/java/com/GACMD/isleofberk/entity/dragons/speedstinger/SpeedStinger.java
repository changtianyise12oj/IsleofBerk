package com.GACMD.isleofberk.entity.dragons.speedstinger;


import com.GACMD.isleofberk.entity.AI.breed.DragonBreedGoal;
import com.GACMD.isleofberk.entity.AI.goal.FollowOwnerNoTPGoal;
import com.GACMD.isleofberk.entity.AI.goal.IOBLookAtPlayerGoal;
import com.GACMD.isleofberk.entity.AI.goal.IOBRandomLookAroundGoal;
import com.GACMD.isleofberk.entity.AI.ground.DragonWaterAvoidingRandomStrollGoal;
import com.GACMD.isleofberk.entity.AI.taming.AggressionToPlayersGoal;
import com.GACMD.isleofberk.entity.AI.target.DragonOwnerHurtTargetGoal;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import com.GACMD.isleofberk.entity.base.dragon.ADragonRideableUtility;
import com.GACMD.isleofberk.entity.dragons.speedstingerleader.SpeedStingerLeader;
import com.GACMD.isleofberk.entity.eggs.entity.base.ADragonEggBase;
import com.GACMD.isleofberk.entity.eggs.entity.eggs.SpeedStingerEgg;
import com.GACMD.isleofberk.registery.ModEntities;
import com.GACMD.isleofberk.registery.ModItems;
import com.GACMD.isleofberk.registery.ModSounds;
import com.GACMD.isleofberk.registery.ModTags;
import com.GACMD.isleofberk.util.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.*;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.eventbus.api.Event;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static com.GACMD.isleofberk.registery.ModTags.Items.SPEED_STINGER_BREED_FOOD;
import static com.GACMD.isleofberk.registery.ModTags.Items.SPEED_STINGER_TAME_FOOD;

public class SpeedStinger extends ADragonRideableUtility {

    /**
     * 1. Find the leader closest to the speed stinger,
     * 2. if entity has no leader set the leader, then add the leader to the pack data
     * 3. if leader dies,or if leader is gone or too far away set leader to null,
     * <p>
     * 1. move position as close to the leader
     * 2. strike at entities who come close
     * <p>
     * 1. add advancements like unit type such as. 0:FRONT, 1:FLANKER, 2:GUARD,
     */
    private static final EntityDataAccessor<Optional<UUID>> LEADER_UUID = SynchedEntityData.defineId(SpeedStinger.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final Optional<Boolean> DO_NOT_DESPAWN = Optional.empty();
    private static final EntityDataAccessor<Byte> UNIT_TYPE = SynchedEntityData.defineId(SpeedStinger.class, EntityDataSerializers.BYTE);

    AnimationFactory factory = new AnimationFactory(this);

    protected int ticksSinceLastStingAttack = 0;
    protected int jumpTicks = 0;

    private <E extends IAnimatable> PlayState basicMovementController(AnimationEvent<E> event) {
        if (event.isMoving() && !shouldStopMovingIndependently()) {
            if (getTarget() != null && !getTarget().isDeadOrDying() && distanceTo(getTarget()) < 14) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("SpeedStingerRun", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;

            } else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("SpeedStingerWalk", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
        }

        if (this.isDragonSitting() && !isDragonSleeping()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("SpeedStingerSit", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        if (this.isDragonSleeping()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("SpeedStingerSleep", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        if (this.getLookControl().isLookingAtTarget()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("SpeedStingerCurious", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("SpeedStingerIdle", ILoopType.EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState attackController(AnimationEvent<E> event) {
        if (getTicksSinceLastAttack()  >= 0 && getTicksSinceLastAttack()  < 12) {
            if (getCurrentAttackType() == 0) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("SpeedStingerBite", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            } else if (getCurrentAttackType() == 1) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("SpeedStingerSting", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
        }

        LivingEntity target = getTarget();
        if (target != null) {
            if (!isSSOnGround() && event.isMoving() && !target.isDeadOrDying()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("SpeedStingerPounce", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
        }

        return PlayState.STOP;
    }

    @Override
    public boolean isInvulnerableTo(@NotNull DamageSource pSource) {
        if (pSource == DamageSource.IN_FIRE || pSource == DamageSource.ON_FIRE || pSource == DamageSource.FALL || pSource == DamageSource.IN_WALL
                || pSource == DamageSource.FLY_INTO_WALL || pSource == DamageSource.CACTUS || pSource == DamageSource.HOT_FLOOR) {
            return true;
        } else if (isDragonDisabled() && !(pSource.getEntity() instanceof Player)) {
            return false;
        } else {
            return super.isInvulnerableTo(pSource);
        }
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<SpeedStinger>(this, "speed_stinger_controller", 5, this::basicMovementController));
        data.addAnimationController(new AnimationController<SpeedStinger>(this, "speed_stinger_controller_attacks", 0, this::attackController));
    }

    public boolean isSSOnGround() {
        BlockPos solidPos = new BlockPos(this.position().x, this.position().y - 1, this.position().z);
        return !level.getBlockState(solidPos).isAir();
    }

    public SpeedStinger(EntityType<? extends SpeedStinger> animal, Level world) {
        super(animal, world);
        this.xpReward = 3;
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
    }

    public static boolean checkSpeedStingerSpawnRules(EntityType<? extends Animal> pAnimal, ServerLevelAccessor pLevel, MobSpawnType pReason, BlockPos pPos, Random pRandom) {
        return pReason == MobSpawnType.SPAWNER || pReason == MobSpawnType.STRUCTURE || !pLevel.canSeeSky(pPos) || pLevel.canSeeSky(pPos) && checkMonsterSpawnRules(pAnimal, pLevel, pReason, pPos, pRandom);
    }

    public static boolean isDarkEnoughToSpawn(ServerLevelAccessor pLevel, BlockPos pPos, Random pRandom) {
        if (pLevel.getBrightness(LightLayer.SKY, pPos) > pRandom.nextInt(32)) {
            return false;
        } else if (pLevel.getBrightness(LightLayer.BLOCK, pPos) > 0) {
            return false;
        } else {
            int i = pLevel.getLevel().isThundering() ? pLevel.getMaxLocalRawBrightness(pPos, 10) : pLevel.getMaxLocalRawBrightness(pPos);
            return i <= pRandom.nextInt(8);
        }
    }

    public static boolean checkMonsterSpawnRules(EntityType<? extends Animal> pType, ServerLevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, Random pRandom) {
        return pLevel.getDifficulty() != Difficulty.PEACEFUL && isDarkEnoughToSpawn(pLevel, pPos, pRandom) && checkMobSpawnRules(pType, pLevel, pSpawnType, pPos, pRandom);
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return !isTame() && !isBaby();
    }

    @Override
    public boolean requiresCustomPersistence() {
        return !isTame() && !isBaby();
    }

    @Override
    public void checkDespawn() {
        if (shouldDespawnInPeaceful() && this.level.getDifficulty() == Difficulty.PEACEFUL) {
            this.discard();
        } else if (!isTame() && !this.isPersistenceRequired() && !this.requiresCustomPersistence()) {
            Entity entity = this.level.getNearestPlayer(this, -1.0D);
            Event.Result result = ForgeEventFactory.canEntityDespawn(this);
            if (result == Event.Result.DENY) {
                this.noActionTime = 0;
                entity = null;
            } else if (result == Event.Result.ALLOW) {
                this.discard();
                entity = null;
            }

            if (!isTame() && entity != null) {
                double d0 = entity.distanceToSqr(this);
                int i = this.getType().getCategory().getDespawnDistance();
                int j = i * i;
                if (d0 > (double) j && this.removeWhenFarAway(d0)) {
                    this.discard();
                }

                int k = this.getType().getCategory().getNoDespawnDistance();
                int l = k * k;
                if (this.noActionTime > 600 && this.random.nextInt(800) == 0 && d0 > (double) l && this.removeWhenFarAway(d0)) {
                    this.discard();
                } else if (d0 < (double) l) {
                    this.noActionTime = 0;
                }
            }
        } else {
            this.noActionTime = 0;
        }

    }

    @Override
    public boolean isNocturnal() {
        return true;
    }

    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
        return true;
    }

    @Override
    protected boolean isItemStackForTaming(ItemStack stack) {
        return Ingredient.of(SPEED_STINGER_TAME_FOOD).test(stack);
    }
    @Override
    public boolean isBreedingFood(ItemStack pStack) {
        return Ingredient.of(SPEED_STINGER_BREED_FOOD).test(pStack);
    }


    @Override
    protected int getAggressionType() {
        return 3;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(3, new SpeedStingerCustomMeleeAttackGoal(this, 1.4D, true));
        this.goalSelector.addGoal(3, new DragonBreedGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new FollowOwnerNoTPGoal(this, 1.1D, 3.0F, 3.0F, false));
        this.goalSelector.addGoal(6, new DragonWaterAvoidingRandomStrollGoal(this, getAttributeValue(Attributes.MOVEMENT_SPEED), 1.0000001E-5F));
        this.goalSelector.addGoal(7, new IOBLookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new IOBRandomLookAroundGoal(this));
        this.goalSelector.addGoal(2, new SpeedStingerCustomLeapAttackGoal(this, 0.6F));
        this.targetSelector.addGoal(1, new AggressionToPlayersGoal<>(this, Player.class, true, getAggressionType(), null));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(2, new DragonOwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(2, new NonTameRandomTargetGoal<>(this, Animal.class, false, PREY_SELECTOR));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractSkeleton.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Zombie.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, ZombifiedPiglin.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Spider.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, EnderMan.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Witch.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Slime.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Monster.class, true));
    }

    @Override
    public void setTarget(@org.jetbrains.annotations.Nullable LivingEntity pLivingEntity) {
        super.setTarget(pLivingEntity);
        // disturb the other alerted creatures
        setSleepDisturbTicks(250);
        setAbilityDisturbTicksAbility(250);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(LEADER_UUID, Optional.empty());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        if (getLeaderUUID() != null) pCompound.putUUID("dragon_flock_leader", this.getLeaderUUID());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        UUID uuid;
        if (pCompound.hasUUID("dragon_flock_leader")) {
            uuid = pCompound.getUUID("dragon_flock_leader");
            this.setLeaderViaUUID(uuid);
        }
    }

    // Variant pt2
    @org.jetbrains.annotations.Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @javax.annotation.Nullable SpawnGroupData pSpawnData, @javax.annotation.Nullable CompoundTag pDataTag) {
        pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        if (pReason == MobSpawnType.SPAWN_EGG) {
            this.setDragonVariant(getTypeForBiome(pLevel));
        } else {
            this.setDragonVariant(getTypeForBiome(pLevel));
        }
        return pSpawnData;
    }

    @Override
    public int getMaxAmountOfVariants() {
        return 4;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }

    /**
     * @param pLevel - The server world, this can be obtained by checking a world with [!world.isRemote()]
     * @return the Entity variant depending on the Biome
     * @author andrew0030
     */
    protected int getTypeForBiome(ServerLevelAccessor pLevel) {
        Holder<Biome> biome = pLevel.getBiome(new BlockPos(this.position()));
        // if it spawns below ground probably less than y 70 set to black, regardless of the position of the biome it spawned in
        // surface biomes on biomeloading event is still considered lush caves
        // spawning is still set on caves though
        if (this.getY() < 0) {
            return 1;
        } else {
            if (biome.is(ModTags.Biomes.SPEED_STINGER_BIOMES)) {
                return 0;
            } else if (biome.is(ModTags.Biomes.ICE_BREAKER_BIOMES)) {
                return 2;
            } else if (biome.is(ModTags.Biomes.FLOUTSCOUT_BIOMES)) {
                return 1;
            } else if (biome.is(ModTags.Biomes.SWEET_STING_BIOMES)) {
                return 3;
            }
        }
        return 0;
    }

    //  Attributes
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.ARMOR, 2)
                .add(Attributes.MOVEMENT_SPEED, 0.4F)
                .add(Attributes.ATTACK_DAMAGE, 8F)
                .add(Attributes.FOLLOW_RANGE, 5F)
                .add(ForgeMod.SWIM_SPEED.get(), 10);

    }

    protected boolean isSpeedStingerLeaderClass() {
        return false;
    }

    @Nullable
    public UUID getLeaderUUID() {
        return this.entityData.get(LEADER_UUID).orElse((UUID) null);
    }

    public void setLeaderViaUUID(UUID uuid) {
        setLeaderViaUUID(Optional.of(uuid));
    }

    public void setLeaderViaUUID(Optional<UUID> leader) {
        this.entityData.set(LEADER_UUID, leader);
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public float getRideCameraDistanceBack() {
        return 6;
    }


    private void findPotentialLeader() {
        SpeedStingerLeader speedStingerLeaderEntity = level.getNearestEntity(level.getEntitiesOfClass(SpeedStingerLeader.class, getTargetSearchArea(this.getFollowDistance())),
                TargetingConditions.forNonCombat(), this, this.getX(), this.getEyeY(), this.getZ());
        if (speedStingerLeaderEntity != null) {
            if (!isSpeedStingerLeaderClass()) setLeaderViaUUID(speedStingerLeaderEntity.getUUID());
            speedStingerLeaderEntity.addMember(this);
        }
    }

    public void removeLeader() {
        this.setLeaderViaUUID(Optional.empty());
    }

    private SpeedStingerLeader nearestLeader() {
        return level.getNearestEntity(level.getEntitiesOfClass(SpeedStingerLeader.class, getTargetSearchArea(this.getFollowDistance())),
                TargetingConditions.forNonCombat(), this, this.getX(), this.getEyeY(), this.getZ());
    }

    @Override
    protected int getExperienceReward(Player pPlayer) {
        return 150;
    }

    @Override
    public void setBaby(boolean baby) {
        super.setBaby(baby);
        if (baby) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(14.0D);
            this.setHealth(14.0F);
        } else {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(this.getMaxHealth());
        }
    }

    @Override
    public boolean hurt(@NotNull DamageSource pSource, float pAmount) {
        return super.hurt(pSource, pAmount);
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        Item item = itemstack.getItem();
        boolean ownedByPlayer = this.isOwnedBy(pPlayer);

        if (pPlayer.isCrouching() && ownedByPlayer && !guiLocked() && !isCommandItems(itemstack)) {
            this.openGUI(pPlayer);
            return InteractionResult.SUCCESS;
        }

        if (!itemstack.isEmpty()) {
//            int nutrition = Objects.requireNonNull(forgeItem.getFoodProperties(itemstack, this)).getNutrition();
            int nutrition = 6;
            // hunger limits the player's phase one progress. Dragons don't eat when they are full.
            // thus preventing the quick tame of dragon's
            if (!isTame()) {
                if (isBaby() && isFoodEdibleToDragon(itemstack)) {
                    if (!pPlayer.getAbilities().instabuild) {
                        itemstack.shrink(1);
                    }
                    if (this.random.nextInt(5) == 0 && !ForgeEventFactory.onAnimalTame(this, pPlayer)) {
                        this.tame(pPlayer);
                        this.navigation.stop();
                        this.setTarget((LivingEntity) null);
                        this.level.playLocalSound(getX(), getY(), getZ(), SoundEvents.DONKEY_EAT, SoundSource.NEUTRAL, 1, getSoundPitch(), true);
                        this.addParticlesAroundSelf(new ItemParticleOption(ParticleTypes.ITEM, itemstack));
                        this.level.broadcastEntityEvent(this, (byte) 7);
                    } else {
                        this.level.broadcastEntityEvent(this, (byte) 6);
                    }
                }
            } else {
                if (this.isFoodEdibleToDragon(itemstack) && canEatWithFoodOnHand(true)) {
                    if (this.getHealth() < getMaxHealth()) {
                        this.heal(5);
                        this.level.playLocalSound(getX(), getY(), getZ(), SoundEvents.DONKEY_EAT, SoundSource.NEUTRAL, 1, getSoundPitch(), true);
                        this.addParticlesAroundSelf(new ItemParticleOption(ParticleTypes.ITEM, itemstack));
                        if (!pPlayer.getAbilities().instabuild) {
                            itemstack.shrink(1);
                        }
                    }
                    // only tamed units can heal when fed, they might accidentally heal to full strength an incapacitated triple stryke
                    if (getHealth() < getMaxHealth()) {
                        this.heal(5);
                        if (!pPlayer.getAbilities().instabuild) {
                            itemstack.shrink(1);
                        }
                    }
                }
            }
            // tame easily when baby but not when taming adult
        } else {
            if (itemstack.isEmpty()) {
                String owned = "iob.speed_stinger.owned";
                String not_owned = "iob.speed_stinger.not_owned";
                if (isOwnedBy(pPlayer)) {
                    pPlayer.displayClientMessage(new TranslatableComponent(owned, isOwnedBy(pPlayer)), true);
                } else {
                    pPlayer.displayClientMessage(new TranslatableComponent(not_owned, !isOwnedBy(pPlayer)), true);
                }
                if (!isTame()) {
                    if (!isBreedingFood(itemstack) && !isFoodEdibleToDragon(itemstack)) {//  && !isDragonBelziumHeld(itemstack)
                        if (pPlayer.isCreative() && !isSpeedStingerLeaderClass()) {
                            this.tameWithName(pPlayer);
                            return InteractionResult.sidedSuccess(this.level.isClientSide);
                        }
                    }
                }
                return InteractionResult.SUCCESS;
            }
        }
        return super.mobInteract(pPlayer, pHand);
    }

    protected int getInLoveCoolDownInMCDays() {
        return 5;
    }

    public void circleEntity(Entity target, float radius, float speed, boolean direction, int circleFrame, float offset, float moveSpeedMultiplier) {
        int directionInt = direction ? 1 : -1;
        double t = 1 * circleFrame * 0.5 * speed / radius + offset;
        Vec3 movePos = target.position().add(radius * Math.cos(t), 0, radius * Math.sin(t));
        this.navigation.moveTo(movePos.x(), movePos.y(), movePos.z(), speed * moveSpeedMultiplier);
    }

//    public void circleTarget(Entity target, float speed, float moveSpeedMultiplier, float radius, boolean direction) {
//        int directionInt = direction ? 1 : -1;
//        Vec2 rotation = target.getRotationVector();
//        double t = 1 * tickCount * 1 * speed / radius;
//        Vec3 movePos = target.position().add(radius * Math.cos(t), 0, radius * Math.sin(t));
//        Vec3 movePos = target.position().add(
//                radius * (target.getRotationVector().x - 45 * Math.PI / 180),
//                0,
//                radius * (target.getRotationVector().x - 45 * Math.PI / 180));
//        this.navigation.moveTo(movePos.x(), movePos.y(), movePos.z(), speed * moveSpeedMultiplier);
//    }

    @Override
    public void setTame(boolean pTamed) {
        if (pTamed) setHealth(getMaxHealth() + (getMaxHealth() * 0.20F));
        super.setTame(pTamed);
    }

    @Override
    public void setHealth(float pHealth) {
        super.setHealth(pHealth);
    }


    @Override
    protected void dropCustomDeathLoot(DamageSource pSource, int pLooting, boolean pRecentlyHit) {
        super.dropCustomDeathLoot(pSource, pLooting, pRecentlyHit);
        ItemStack itemstack0 = new ItemStack(ModItems.SPEED_STINGER_EGG.get(), 1);
        ItemStack itemstack1 = new ItemStack(ModItems.SPEED_STINGER_EGG_FLOUTSCOOUT.get(), 1);
        ItemStack itemstack2 = new ItemStack(ModItems.SPEED_STINGER_EGG_ICE_BREAKER.get(), 1);
        ItemStack itemstack3 = new ItemStack(ModItems.SPEED_STINGER_EGG_SWEET_STING.get(), 1);

        if (random.nextInt(8) == 1) {
            if (getDragonVariant() == 0) {
                if (!itemstack0.isEmpty()) { //  && random.nextInt(10) == 1
                    this.spawnAtLocation(itemstack0);
                }
            } else if (getDragonVariant() == 1) {
                if (!itemstack1.isEmpty()) { //  && random.nextInt(10) == 1
                    this.spawnAtLocation(itemstack1);
                }
            } else if (getDragonVariant() == 2) {
                if (!itemstack2.isEmpty()) { //  && random.nextInt(10) == 1
                    this.spawnAtLocation(itemstack2);
                }
            } else if (getDragonVariant() == 3) {
                if (!itemstack3.isEmpty()) { //  && random.nextInt(10) == 1
                    this.spawnAtLocation(itemstack3);
                }
            }
        }
    }

    @Override
    public void swing(InteractionHand pHand, boolean pUpdateSelf) {
        if (random.nextInt(7) == 0) ticksSinceLastStingAttack = Util.secondsToTicks(3);
        super.swing(pHand, pUpdateSelf);
    }

    @Override
    public boolean doHurtTarget(Entity pEntity) {
        initAttackEffects(pEntity);
        return super.doHurtTarget(pEntity);
    }

    protected void initAttackEffects(Entity pEntity) {
        if (pEntity instanceof LivingEntity) {
            if (getCurrentAttackType() == 1) {
                LivingEntity target = (LivingEntity) pEntity;
                target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 250, 2));
            }
        }
    }

    @org.jetbrains.annotations.Nullable
    public ADragonEggBase getBreedEggResult(ServerLevel level, @NotNull AgeableMob parent) {
        SpeedStingerEgg dragon = ModEntities.SPEED_STINGER_EGG.get().create(level);
        return dragon;
    }

    @Override
    public void spawnChildFromBreeding(ServerLevel serverLevel, Animal partner) {
        if (partner instanceof ADragonBase dragonPartner) {
            ADragonEggBase egg = this.getBreedEggResult(serverLevel, dragonPartner);
            final net.minecraftforge.event.entity.living.BabyEntitySpawnEvent event = new net.minecraftforge.event.entity.living.BabyEntitySpawnEvent(this, partner, egg);
            final boolean cancelled = net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);
            if (cancelled) {
                //Reset the "inLove" state for the dragons
                this.setAge(Util.mcDaysToMinutes(getInLoveCoolDownInMCDays()));
                dragonPartner.setAge(Util.mcDaysToMinutes(getInLoveCoolDownInMCDays()));
//                this.setAge(Util.secondsToTicks(10));
//                dragonPartner.setAge(Util.secondsToTicks(10));
                this.resetLove();
                partner.resetLove();
                return;
            }
            if (egg != null) {
                ServerPlayer serverplayer = this.getLoveCause();
                if (serverplayer == null && partner.getLoveCause() != null) {
                    serverplayer = partner.getLoveCause();
                }

                if (serverplayer != null) {
                    serverplayer.awardStat(Stats.ANIMALS_BRED);
                    CriteriaTriggers.BRED_ANIMALS.trigger(serverplayer, this, partner, egg);
                }

                this.setAge(Util.mcDaysToMinutes(getInLoveCoolDownInMCDays()));
                dragonPartner.setAge(Util.mcDaysToMinutes(getInLoveCoolDownInMCDays()));
//                this.setAge(Util.secondsToTicks(10));
//                dragonPartner.setAge(Util.secondsToTicks(10));
                this.resetLove();
                partner.resetLove();
                if (dragonPartner.getDragonVariant() == getDragonVariant()) {
                    egg.setDragonVariant(getDragonVariant());
                } else {
                    egg.setDragonVariant(random.nextInt(getMaxAmountOfVariants()));
                }
                egg.setBaby(true);
                egg.moveTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
                level.addFreshEntity(egg);
                level.broadcastEntityEvent(this, (byte) 18);
                if (level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
                    level.addFreshEntity(new ExperienceOrb(level, this.getX(), this.getY(), this.getZ(), this.getRandom().nextInt(7) + 1));
                }
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (getLeaderUUID() == null)
            findPotentialLeader();

        Pig pig = level.getNearestEntity(level.getEntitiesOfClass(Pig.class, getTargetSearchArea(14)),
                TargetingConditions.forNonCombat(), this, this.getX(), this.getEyeY(), this.getZ());
        if (pig != null) {
            this.setTarget(pig);
        }
        if (ticksSinceLastStingAttack >= 0)
            ticksSinceLastStingAttack--;

        if (ticksSinceLastStingAttack >= 0) {
            setCurrentAttackType(1);
        } else {
            setCurrentAttackType(0);
        }
        if (!onGround) {
            jumpTicks++;
        } else {
            jumpTicks = 0;
        }

        checkInsideBlocks();
        floatStinger();
    }

    protected void onGroundMechanics() {
        BlockPos pos1 = new BlockPos(position().add(0, -1, 0));
        if (level.getBlockState(pos1).getMaterial().isSolid()) {
            setIsDragonOnGround(true);
        } else {
            setIsDragonOnGround(false);
        }
    }

    @Override
    public boolean canBeMounted() {
        return false;
    }

    @Override
    public boolean isSaddleable() {
        return false;
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level pLevel) {
        return new SpeedStingerPathNavigation(this, pLevel);
//        return super.createNavigation(pLevel);
    }

    private void floatStinger() {
        if (this.isInWater()) {
            CollisionContext collisioncontext = CollisionContext.of(this);
            if (collisioncontext.isAbove(LiquidBlock.STABLE_SHAPE, this.blockPosition(), true) && !this.level.getFluidState(this.blockPosition().above()).is(FluidTags.WATER)) {
                this.onGround = true;
            } else {
                this.setDeltaMovement(this.getDeltaMovement().scale(0.5D).add(0.0D, 0.05D, 0.0D));
            }
        }
    }

    public boolean canStandOnFluid(FluidState fluidstate) {
        return fluidstate.is(FluidTags.WATER);
    }

    public float getWalkTargetValue(@NotNull BlockPos pPos, LevelReader pLevel) {
        if (pLevel.getBlockState(pPos).getFluidState().is(FluidTags.WATER)) {
            return 10.0F;
        } else {
            return this.isInWater() || this.isWaterBelow() ? Float.NEGATIVE_INFINITY : 0.0F;
        }
    }

    static class SpeedStingerPathNavigation extends GroundPathNavigation {

        SpeedStingerPathNavigation(SpeedStinger pSpeedStinger, Level pLevel) {
            super(pSpeedStinger, pLevel);
        }

        @Override
        protected @NotNull PathFinder createPathFinder(int pMaxVisitedNodes) {
            this.nodeEvaluator = new WalkNodeEvaluator();
            return new PathFinder(this.nodeEvaluator, pMaxVisitedNodes);
        }

        @Override
        protected boolean hasValidPathType(@NotNull BlockPathTypes pPathType) {
            return pPathType == BlockPathTypes.WATER || pPathType == BlockPathTypes.DAMAGE_FIRE || pPathType == BlockPathTypes.DANGER_FIRE || super.hasValidPathType(pPathType);
        }

        @Override
        public boolean isStableDestination(@NotNull BlockPos pPos) {
            return this.level.getBlockState(pPos).is(Blocks.WATER) || super.isStableDestination(pPos);
        }
    }

//    protected PathNavigation createNavigation(Level pLevel) {
//        return new SpeedStingerPathNavigation(this, level);
//    }

    protected class SpeedStingerCustomMeleeAttackGoal extends MeleeAttackGoal {

        SpeedStinger speedStingerEntity;

        public SpeedStingerCustomMeleeAttackGoal(PathfinderMob pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
            super(pMob, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
            this.speedStingerEntity = (SpeedStinger) pMob;
        }

        @Override
        public boolean canUse()
        {
            if(this.speedStingerEntity.isDragonSitting())
                return false;
            return super.canUse();
        }

        @Override
        public boolean canContinueToUse()
        {
            if(this.speedStingerEntity.isDragonSitting())
                return false;
            return super.canContinueToUse();
        }
    }

    protected class SpeedStingerCustomLeapAttackGoal extends LeapAtTargetGoal {

        SpeedStinger speedStingerEntity;

        public SpeedStingerCustomLeapAttackGoal(SpeedStinger speedStingerEntity, float pYd) {
            super(speedStingerEntity, pYd);
            this.speedStingerEntity = speedStingerEntity;
        }


        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        @Override
        public boolean canUse() {
            if (this.speedStingerEntity.isVehicle() || this.speedStingerEntity.isDragonSitting()) {
                return false;
            } else {
                this.target = this.speedStingerEntity.getTarget();
                if (this.target == null) {
                    return false;
                } else {
                    double d0 = Math.sqrt(this.speedStingerEntity.distanceToSqr(this.target.getX(), 0, this.target.getZ()));
//                    if (!(d0 < 4.0D) && !(d0 > 16.0D)) {
                    if (d0 > 6) {
                        if (!this.speedStingerEntity.isOnGround()) {
                            return false;
                        } else {
                            return this.speedStingerEntity.getRandom().nextInt(reducedTickDelay(75)) == 0;
                        }
                    } else {
                        return false;
                    }
                }
            }
        }


        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            Vec3 vec3 = this.speedStingerEntity.getDeltaMovement();
            Vec3 vec31 = new Vec3(this.target.getX() - this.speedStingerEntity.getX(), 0.0D, this.target.getZ() - this.speedStingerEntity.getZ());
            if (vec31.lengthSqr() > 1.0E-7D) {
                vec31 = vec31.normalize().scale(0.8D).add(vec3.scale(0.5D));
            }

            this.speedStingerEntity.setDeltaMovement(vec31.x, (double) this.yd, vec31.z);
        }

        @Override
        public boolean canContinueToUse() {
            if(this.speedStingerEntity.isDragonSitting())
                return false;
            return super.canContinueToUse();
        }
    }

    private class SpeedStingerPlayerSupport extends Goal {

        SpeedStinger speedStinger;
        Player player;

        public SpeedStingerPlayerSupport(SpeedStinger speedStinger) {
            this.speedStinger = speedStinger;
            this.player = (Player) speedStinger.getOwner();
        }

        @Override
        public boolean canUse() {
            return speedStinger.isTame() && player != null && !isDragonSitting() && isDragonFollowing();
        }

        @Override
        public void tick() {
            Vec3 bodyOrigin = player.position();
            double x = -Math.sin(player.getYRot() * Math.PI / 180) * 2.4;
            double y = player.getY();
            double z = Math.cos(player.getYRot() * Math.PI / 180) * 2.4;
            Vec3 pos = bodyOrigin.add(new Vec3(x, y, z));
            speedStinger.navigation.moveTo(pos.x() - 40, pos.y(), pos.z() - 40, 1.4F);
        }
    }

    protected SoundEvent getAmbientSound() {
        if (this.isDragonSleeping()) {
            return ModSounds.SPEED_STINGER_SLEEP.get();
        } else {
            return ModSounds.SPEED_STINGER_GROWL.get();
        }
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return ModSounds.SPEED_STINGER_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return ModSounds.SPEED_STINGER_DEATH.get();
    }

    @Override
    protected SoundEvent get1stAttackSound() {
        return ModSounds.SPEED_STINGER_BITE.get();
    }

    @Override
    protected SoundEvent get2ndAttackSound() {
        return ModSounds.SPEED_STINGER_STING.get();
    }

    @Override
    protected void playAttackSound() {
        if (getCurrentAttackType() == 0) {
            playSound(get1stAttackSound(), 4, 1);
        }

        if (getCurrentAttackType() == 1) {
            playSound(get2ndAttackSound(), 4, 1);

        }
    }
}

