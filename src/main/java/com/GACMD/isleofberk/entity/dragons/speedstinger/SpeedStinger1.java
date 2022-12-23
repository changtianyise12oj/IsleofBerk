//package com.GACMD.isleofberk.entity.dragons.speedstinger;
//
//import com.GACMD.isleofberk.entity.AI.goal.FollowOwnerNoTPGoal;
//import com.GACMD.isleofberk.entity.AI.goal.IOBLookAtPlayerGoal;
//import com.GACMD.isleofberk.entity.AI.goal.IOBRandomLookAroundGoal;
//import com.GACMD.isleofberk.entity.AI.ground.DragonWaterAvoidingRandomStrollGoal;
//import com.GACMD.isleofberk.entity.AI.path.air.FlyingDragonMoveControl;
//import com.GACMD.isleofberk.entity.AI.taming.AggressionToPlayersGoal;
//import com.GACMD.isleofberk.entity.AI.target.DragonOwnerHurtTargetGoal;
//import com.GACMD.isleofberk.entity.AI.water.DragonFloatGoal;
//import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
//import com.GACMD.isleofberk.entity.dragons.speedstingerleader.SpeedStingerLeader;
//import com.GACMD.isleofberk.entity.eggs.entity.base.ADragonEggBase;
//import com.GACMD.isleofberk.entity.eggs.entity.eggs.SpeedStingerEgg;
//import com.GACMD.isleofberk.registery.ModEntities;
//import com.GACMD.isleofberk.registery.ModItems;
//import com.GACMD.isleofberk.util.Util;
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.Holder;
//import net.minecraft.core.particles.ItemParticleOption;
//import net.minecraft.core.particles.ParticleTypes;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.network.chat.TranslatableComponent;
//import net.minecraft.network.syncher.EntityDataAccessor;
//import net.minecraft.network.syncher.EntityDataSerializers;
//import net.minecraft.network.syncher.SynchedEntityData;
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.sounds.SoundEvents;
//import net.minecraft.sounds.SoundSource;
//import net.minecraft.tags.BiomeTags;
//import net.minecraft.tags.BlockTags;
//import net.minecraft.tags.FluidTags;
//import net.minecraft.world.Difficulty;
//import net.minecraft.world.DifficultyInstance;
//import net.minecraft.world.InteractionHand;
//import net.minecraft.world.InteractionResult;
//import net.minecraft.world.damagesource.DamageSource;
//import net.minecraft.world.effect.MobEffectInstance;
//import net.minecraft.world.effect.MobEffects;
//import net.minecraft.world.entity.*;
//import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
//import net.minecraft.world.entity.ai.attributes.Attributes;
//import net.minecraft.world.entity.ai.control.MoveControl;
//import net.minecraft.world.entity.ai.goal.FloatGoal;
//import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
//import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
//import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
//import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
//import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
//import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
//import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
//import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
//import net.minecraft.world.entity.ai.navigation.PathNavigation;
//import net.minecraft.world.entity.ai.targeting.TargetingConditions;
//import net.minecraft.world.entity.animal.Animal;
//import net.minecraft.world.entity.animal.Pig;
//import net.minecraft.world.entity.monster.*;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.Items;
//import net.minecraft.world.level.*;
//import net.minecraft.world.level.biome.Biome;
//import net.minecraft.world.level.biome.Biomes;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.level.block.LiquidBlock;
//import net.minecraft.world.level.material.FluidState;
//import net.minecraft.world.level.pathfinder.BlockPathTypes;
//import net.minecraft.world.level.pathfinder.PathComputationType;
//import net.minecraft.world.level.pathfinder.PathFinder;
//import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
//import net.minecraft.world.phys.Vec3;
//import net.minecraft.world.phys.shapes.CollisionContext;
//import net.minecraftforge.common.ForgeMod;
//import net.minecraftforge.common.extensions.IForgeItem;
//import net.minecraftforge.event.ForgeEventFactory;
//import net.minecraftforge.eventbus.api.Event;
//import org.jetbrains.annotations.NotNull;
//import software.bernie.geckolib3.core.IAnimatable;
//import software.bernie.geckolib3.core.PlayState;
//import software.bernie.geckolib3.core.builder.AnimationBuilder;
//import software.bernie.geckolib3.core.builder.ILoopType;
//import software.bernie.geckolib3.core.controller.AnimationController;
//import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
//import software.bernie.geckolib3.core.manager.AnimationData;
//import software.bernie.geckolib3.core.manager.AnimationFactory;
//
//import javax.annotation.Nullable;
//import java.time.LocalDate;
//import java.time.temporal.ChronoField;
//import java.util.Objects;
//import java.util.Optional;
//import java.util.Random;
//import java.util.UUID;
//
//public class SpeedStinger extends ADragonBase {
//
//    /**
//     * 1. Find the leader closest to the speed stinger,
//     * 2. if entity has no leader set the leader, then add the leader to the pack data
//     * 3. if leader dies,or if leader is gone or too far away set leader to null,
//     * <p>
//     * 1. move position as close to the leader
//     * 2. strike at entities who come close
//     * <p>
//     * 1. add advancements like unit type such as. 0:FRONT, 1:FLANKER, 2:GUARD,
//     */
//    private static final EntityDataAccessor<Optional<UUID>> LEADER_UUID = SynchedEntityData.defineId(SpeedStinger.class, EntityDataSerializers.OPTIONAL_UUID);
//    private static final Optional<Object> ABSENT_LEADER = Optional.empty();
//    private static final EntityDataAccessor<Byte> UNIT_TYPE = SynchedEntityData.defineId(SpeedStinger.class, EntityDataSerializers.BYTE);
//
//    AnimationFactory factory = new AnimationFactory(this);
//
//    protected int ticksSinceLastStingAttack = 0;
//    protected int jumpTicks = 0;
//
//    private <E extends IAnimatable> PlayState basicMovementController(AnimationEvent<E> event) {
//        if (event.isMoving() && !shouldStopMovingIndependently()) {
//            if (getTarget() != null && !getTarget().isDeadOrDying() && distanceTo(getTarget()) < 14) {
//                event.getController().setAnimation(new AnimationBuilder().addAnimation("SpeedStingerRun", ILoopType.EDefaultLoopTypes.LOOP));
//                return PlayState.CONTINUE;
//
//            } else {
//                event.getController().setAnimation(new AnimationBuilder().addAnimation("SpeedStingerWalk", ILoopType.EDefaultLoopTypes.LOOP));
//                return PlayState.CONTINUE;
//            }
//        }
//
//        if (this.isDragonSitting()) {
//            event.getController().setAnimation(new AnimationBuilder().addAnimation("SpeedStingerSit", ILoopType.EDefaultLoopTypes.LOOP));
//            return PlayState.CONTINUE;
//        }
//        if (this.isDragonSleeping()) {
//            event.getController().setAnimation(new AnimationBuilder().addAnimation("SpeedStingerSleep", ILoopType.EDefaultLoopTypes.LOOP));
//            return PlayState.CONTINUE;
//        }
//        if (this.getLookControl().isLookingAtTarget()) {
//            event.getController().setAnimation(new AnimationBuilder().addAnimation("SpeedStingerCurious", ILoopType.EDefaultLoopTypes.LOOP));
//            return PlayState.CONTINUE;
//        }
//        event.getController().setAnimation(new AnimationBuilder().addAnimation("SpeedStingerIdle", ILoopType.EDefaultLoopTypes.LOOP));
//        return PlayState.CONTINUE;
//    }
//
//    private <E extends IAnimatable> PlayState attackController(AnimationEvent<E> event) {
//        if (ticksSinceLastAttack >= 0 && ticksSinceLastAttack < 12) {
//            if (getCurrentAttackType() == 0) {
//                event.getController().setAnimation(new AnimationBuilder().addAnimation("SpeedStingerBite", ILoopType.EDefaultLoopTypes.LOOP));
//                return PlayState.CONTINUE;
//            } else if (getCurrentAttackType() == 1) {
//                event.getController().setAnimation(new AnimationBuilder().addAnimation("SpeedStingerSting", ILoopType.EDefaultLoopTypes.LOOP));
//                return PlayState.CONTINUE;
//            }
//        }
//
//        LivingEntity target = getTarget();
//        if (target != null) {
//            if (!isSpeedStingerOnGround() && event.isMoving() && !target.isDeadOrDying()) {
//                event.getController().setAnimation(new AnimationBuilder().addAnimation("SpeedStingerPounce", ILoopType.EDefaultLoopTypes.LOOP));
//                return PlayState.CONTINUE;
//            }
//        }
//
//        return PlayState.STOP;
//    }
//
//
//    @Override
//    public void registerControllers(AnimationData data) {
//        data.addAnimationController(new AnimationController<SpeedStinger>(this, "speed_stinger_controller", 5, this::basicMovementController));
//        data.addAnimationController(new AnimationController<SpeedStinger>(this, "speed_stinger_controller_attacks", 0, this::attackController));
//    }
//
//    public boolean isSpeedStingerOnGround() {
//        BlockPos solidPos = new BlockPos(this.position().x, this.position().y - 1, this.position().z);
//        return !level.getBlockState(solidPos).isAir();
//    }
//
//    public SpeedStinger(EntityType<? extends SpeedStinger> animal, Level world) {
//        super(animal, world);
//        this.xpReward = 25;
//        this.setPathfindingMalus(BlockPathTypes.WATER, 1.0F);
//    }
//
//    private void floatStinger() {
//        if (this.isInWater()) {
//            CollisionContext collisioncontext = CollisionContext.of(this);
//            if (collisioncontext.isAbove(LiquidBlock.STABLE_SHAPE, this.blockPosition(), true) && !this.level.getFluidState(this.blockPosition().above()).is(FluidTags.LAVA)) {
//                this.onGround = true;
//            } else {
//                this.setDeltaMovement(this.getDeltaMovement().scale(0.5D).add(0.0D, 0.05D, 0.0D));
//            }
//        }
//
//    }
//
//    public boolean canStandOnFluid(FluidState p_204067_) {
//        return p_204067_.is(FluidTags.WATER);
//    }
//
//    /**
//     * Static predicate for determining whether or not an animal can spawn at the provided location.
//     *
//     * @param pAnimal The animal entity to be spawned
//     */
//    public static boolean checkSpeedStingerSpawnRules(EntityType<? extends Animal> pAnimal, LevelAccessor pLevel, MobSpawnType pReason, BlockPos pPos, Random pRandom) {
//        BlockPos blockpos = pPos.below();
//        return pLevel.getBlockState(pPos.below()).is(BlockTags.SAND) ||
//                pLevel.getBlockState(pPos.below()).is(BlockTags.LEAVES) || pLevel.getBlockState(pPos.below()).is(BlockTags.REPLACEABLE_PLANTS) ||
//                pLevel.getBlockState(pPos.below()).is(BlockTags.MINEABLE_WITH_PICKAXE) || pLevel.getBlockState(pPos.below()).is(BlockTags.MINEABLE_WITH_SHOVEL)
//                || isDarkEnoughToSpawn((ServerLevelAccessor) pLevel, pPos, pRandom) || pLevel.getBlockState(blockpos).isValidSpawn(pLevel, blockpos, pAnimal);
//    }
//
//    public static boolean isDarkEnoughToSpawn(ServerLevelAccessor pLevel, BlockPos pPos, Random pRandom) {
//        if (pLevel.getBrightness(LightLayer.SKY, pPos) > pRandom.nextInt(32)) {
//            return false;
//        } else if (pLevel.getBrightness(LightLayer.BLOCK, pPos) > 0) {
//            return false;
//        } else {
//            int i = pLevel.getLevel().isThundering() ? pLevel.getMaxLocalRawBrightness(pPos, 10) : pLevel.getMaxLocalRawBrightness(pPos);
//            return i <= pRandom.nextInt(8);
//        }
//    }
//
//
//    public static boolean checkAnyLightMonsterSpawnRules(EntityType<? extends SpeedStinger> pType, LevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, Random pRandom) {
//        return checkMobSpawnRules(pType, pLevel, pSpawnType, pPos, pRandom);
//    }
//
//    protected boolean shouldDespawnInPeaceful() {
//        return !isTame();
//    }
//
//    @Override
//    public boolean requiresCustomPersistence() {
//        return !isTame() && !isBaby();
//    }
//
//    public void checkDespawn() {
//        if (this.level.getDifficulty() == Difficulty.PEACEFUL) {
//            this.discard();
//        } else if (!this.isPersistenceRequired() && !this.requiresCustomPersistence()) {
//            Entity entity = this.level.getNearestPlayer(this, -1.0D);
//            Event.Result result = ForgeEventFactory.canEntityDespawn(this);
//            if (result == Event.Result.DENY) {
//                this.noActionTime = 0;
//                entity = null;
//            } else if (result == Event.Result.ALLOW) {
//                this.discard();
//                entity = null;
//            }
//
//            if (entity != null) {
//                double d0 = entity.distanceToSqr(this);
//                int i = this.getType().getCategory().getDespawnDistance();
//                int j = i * i;
//                if (d0 > (double) j && this.removeWhenFarAway(d0)) {
//                    this.discard();
//                }
//
//                int k = this.getType().getCategory().getNoDespawnDistance();
//                int l = k * k;
//                if (this.noActionTime > 600 && this.random.nextInt(800) == 0 && d0 > (double) l && this.removeWhenFarAway(d0)) {
//                    this.discard();
//                } else if (d0 < (double) l) {
//                    this.noActionTime = 0;
//                }
//            }
//        } else {
//            this.noActionTime = 0;
//        }
//
//    }
//
//    @Override
//    public boolean isNocturnal() {
//        return true;
//    }
//
//    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
//        return true;
//    }
//
//    @Override
//    protected boolean isItemStackForTaming(ItemStack stack) {
//        return stack.is(Items.RABBIT);
//    }
//
//    @Override
//    protected int getAggressionType() {
//        return 3;
//    }
//
//    @Override
//    protected void registerGoals() {
//        this.goalSelector.addGoal(1, new FollowOwnerNoTPGoal(this, 1.1D, 3.0F, 3.0F, false));
//        this.goalSelector.addGoal(6, new DragonWaterAvoidingRandomStrollGoal(this, getAttributeValue(Attributes.MOVEMENT_SPEED), 1.0000001E-5F));
//        this.goalSelector.addGoal(7, new IOBLookAtPlayerGoal(this, Player.class, 8.0F));
//        this.goalSelector.addGoal(7, new IOBRandomLookAroundGoal(this));
//        this.goalSelector.addGoal(2, new SpeedStingerCustomLeapAttackGoal(this, 0.6F));
//        this.targetSelector.addGoal(1, new AggressionToPlayersGoal<>(this, Player.class, true, getAggressionType(), null));
//        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
//        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
//        this.targetSelector.addGoal(2, new DragonOwnerHurtTargetGoal(this));
//        this.targetSelector.addGoal(2, new NonTameRandomTargetGoal<>(this, Animal.class, false, PREY_SELECTOR));
//        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractSkeleton.class, true));
//        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Zombie.class, true));
//        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, ZombifiedPiglin.class, true));
//        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Spider.class, true));
//        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, EnderMan.class, true));
//        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Witch.class, true));
//        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Slime.class, true));
//        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Monster.class, true));
//    }
//
//    @Override
//    public void setTarget(@org.jetbrains.annotations.Nullable LivingEntity pLivingEntity) {
//        super.setTarget(pLivingEntity);
//        // disturb the other alerted creatures
//        setSleepDisturbTicks(250);
//        setAbilityDisturbTicksAbility(250);
//    }
//
//    @Override
//    protected void defineSynchedData() {
//        super.defineSynchedData();
//        this.entityData.define(LEADER_UUID, Optional.empty());
//    }
//
//    @Override
//    public void addAdditionalSaveData(CompoundTag pCompound) {
//        super.addAdditionalSaveData(pCompound);
//        if (getLeaderUUID() != null) pCompound.putUUID("dragon_flock_leader", this.getLeaderUUID());
//    }
//
//    @Override
//    public void readAdditionalSaveData(CompoundTag pCompound) {
//        super.readAdditionalSaveData(pCompound);
//        UUID uuid;
//        if (pCompound.hasUUID("dragon_flock_leader")) {
//            uuid = pCompound.getUUID("dragon_flock_leader");
//            this.setLeaderViaUUID(uuid);
//        }
//    }
//
//    // Variant pt2
//    @org.jetbrains.annotations.Nullable
//    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @javax.annotation.Nullable SpawnGroupData pSpawnData, @javax.annotation.Nullable CompoundTag pDataTag) {
//        pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
//        if (pReason == MobSpawnType.SPAWN_EGG) {
//            this.setDragonVariant(this.random.nextInt(getMaxAmountOfVariants()));
//        } else {
//            this.setDragonVariant(getTypeForBiome(pLevel));
//        }
//        return pSpawnData;
//    }
//
//    @Override
//    public int getMaxAmountOfVariants() {
//        return 4;
//    }
//
//    @org.jetbrains.annotations.Nullable
//    @Override
//    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
//        return null;
//    }
//
//    /**
//     * @param pLevel - The server world, this can be obtained by checking a world with [!world.isRemote()]
//     * @return the Entity variant depending on the Biome
//     * @author andrew0030
//     */
//    protected int getTypeForBiome(ServerLevelAccessor pLevel) {
//        Holder<Biome> biome = pLevel.getBiome(new BlockPos(this.position()));
//        // if it spawns below ground probably less than y 70 set to black, regardless of the position of the biome it spawned in
//        // surface biomes on biomeloading event is still considered lush caves
//        if (this.getY() < 0) {
//            return 1;
//        } else {
//            if (biome.is(Biomes.TAIGA) || biome.is(Biomes.SNOWY_TAIGA) || biome.is(Biomes.OLD_GROWTH_PINE_TAIGA) || biome.is(Biomes.OLD_GROWTH_SPRUCE_TAIGA) || biome.is(Biomes.STONY_SHORE)) {
//                return 0;
//            } else if (biome.is(Biomes.DEEP_COLD_OCEAN) || biome.is(Biomes.COLD_OCEAN) || biome.is(Biomes.ICE_SPIKES) || biome.is(Biomes.DEEP_FROZEN_OCEAN)
//                    || biome.is(Biomes.FROZEN_PEAKS)
//                    || biome.is(Biomes.FROZEN_RIVER)
//                    || biome.is(Biomes.SNOWY_BEACH) || biome.is(Biomes.SNOWY_PLAINS) || biome.is(Biomes.SNOWY_TAIGA) || biome.is(Biomes.SNOWY_SLOPES)) {
//                return 2;
//            } else if (biome.is(BiomeTags.IS_JUNGLE)) {
//                return 3;
//            }
//        }
//        return 0;
//    }
//
//    //  Attributes
//    public static AttributeSupplier.Builder createAttributes() {
//        return Mob.createMobAttributes()
//                .add(Attributes.MAX_HEALTH, 30.0D)
//                .add(Attributes.ARMOR, 2)
//                .add(Attributes.MOVEMENT_SPEED, 0.8F)
//                .add(Attributes.ATTACK_DAMAGE, 8F)
//                .add(Attributes.FOLLOW_RANGE, 5F)
//                .add(ForgeMod.STEP_HEIGHT_ADDITION.get(), 1F)
//                .add(ForgeMod.SWIM_SPEED.get(), 10);
//
//    }
//
//    protected boolean isSpeedStingerLeaderClass() {
//        return false;
//    }
//
//    @Nullable
//    public UUID getLeaderUUID() {
//        return this.entityData.get(LEADER_UUID).orElse((UUID) null);
//    }
//
//    public void setLeaderViaUUID(UUID uuid) {
//        setLeaderViaUUID(Optional.of(uuid));
//    }
//
//    public void setLeaderViaUUID(Optional<UUID> leader) {
//        this.entityData.set(LEADER_UUID, leader);
//    }
//
//    @Override
//    public AnimationFactory getFactory() {
//        return this.factory;
//    }
//
//    @Override
//    public float getRideCameraDistanceBack() {
//        return 6;
//    }
//
//    private void findPotentialLeader() {
//        SpeedStingerLeader speedStingerLeaderEntity = level.getNearestEntity(level.getEntitiesOfClass(SpeedStingerLeader.class, getTargetSearchArea(this.getFollowDistance())),
//                TargetingConditions.forNonCombat(), this, this.getX(), this.getEyeY(), this.getZ());
//        if (speedStingerLeaderEntity != null) {
//            if (!isSpeedStingerLeaderClass()) setLeaderViaUUID(speedStingerLeaderEntity.getUUID());
//            speedStingerLeaderEntity.addMember(this);
//        }
//    }
//
//    public void removeLeader() {
//        this.setLeaderViaUUID(Optional.empty());
//    }
//
//    @Override
//    protected int getExperienceReward(Player pPlayer) {
//        return 150;
//    }
//
//    @Override
//    public void setBaby(boolean baby) {
//        super.setBaby(baby);
//        if (baby) {
//            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(14.0D);
//            this.setHealth(14.0F);
//        } else {
//            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(this.getMaxHealth());
//        }
//    }
//
//    @Override
//    public boolean hurt(@NotNull DamageSource pSource, float pAmount) {
//        return super.hurt(pSource, pAmount);
//    }
//
//    @Override
//    public @NotNull InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
//        ItemStack itemstack = pPlayer.getItemInHand(pHand);
//        Item item = itemstack.getItem();
//        if (!itemstack.isEmpty()) {
//            IForgeItem forgeItem = itemstack.getItem();
////            int nutrition = Objects.requireNonNull(forgeItem.getFoodProperties(itemstack, this)).getNutrition();
//            int nutrition = 6;
//            // hunger limits the player's phase one progress. Dragons don't eat when they are full.
//            // thus preventing the quick tame of dragon's
//            if (!isTame()) {
//                if (isBaby() && isFoodEdibleToDragon(itemstack)) {
//                    if (!pPlayer.getAbilities().instabuild) {
//                        itemstack.shrink(1);
//                    }
//                    if (this.random.nextInt(5) == 0 && !ForgeEventFactory.onAnimalTame(this, pPlayer)) {
//                        this.tame(pPlayer);
//                        this.navigation.stop();
//                        this.setTarget((LivingEntity) null);
//                        this.level.playLocalSound(getX(), getY(), getZ(), SoundEvents.DONKEY_EAT, SoundSource.NEUTRAL, 1, getSoundPitch(), true);
//                        this.addParticlesAroundSelf(new ItemParticleOption(ParticleTypes.ITEM, itemstack));
//                        this.level.broadcastEntityEvent(this, (byte) 7);
//                    } else {
//                        this.level.broadcastEntityEvent(this, (byte) 6);
//                    }
//                }
//            } else {
//                if (this.isFoodEdibleToDragon(itemstack) && canEatWithFoodOnHand(true)) {
//                    if (this.getHealth() < getMaxHealth()) {
//                        this.heal(5);
//                        this.level.playLocalSound(getX(), getY(), getZ(), SoundEvents.DONKEY_EAT, SoundSource.NEUTRAL, 1, getSoundPitch(), true);
//                        this.addParticlesAroundSelf(new ItemParticleOption(ParticleTypes.ITEM, itemstack));
//                        if (!pPlayer.getAbilities().instabuild) {
//                            itemstack.shrink(1);
//                        }
//                    }
//                    // only tamed units can heal when fed, they might accidentally heal to full strength an incapacitated triple stryke
//                    if (getHealth() < getMaxHealth()) {
//                        this.heal(5);
//                        if (!pPlayer.getAbilities().instabuild) {
//                            itemstack.shrink(1);
//                        }
//                    }
//                }
//            }
//            // tame easily when baby but not when taming adult
//        } else {
//            if (itemstack.isEmpty()) {
//                String owned = "iob.speed_stinger.owned";
//                String not_owned = "iob.speed_stinger.not_owned";
//                if (isOwnedBy(pPlayer)) {
//                    pPlayer.displayClientMessage(new TranslatableComponent(owned, isOwnedBy(pPlayer)), true);
//                } else {
//                    pPlayer.displayClientMessage(new TranslatableComponent(not_owned, !isOwnedBy(pPlayer)), true);
//                }
//                if (!isTame()) {
//                    if (!isBreedingFood(itemstack) && !isFoodEdibleToDragon(itemstack)) {//  && !isDragonBelziumHeld(itemstack)
//                        if (pPlayer.isCreative() && !isSpeedStingerLeaderClass()) {
//                            this.tameWithName(pPlayer);
//                            return InteractionResult.sidedSuccess(this.level.isClientSide);
//                        }
//                    }
//                }
//                return InteractionResult.SUCCESS;
//            }
//        }
//        return super.mobInteract(pPlayer, pHand);
//    }
//
//    @Override
//    public void setTame(boolean pTamed) {
//        if (pTamed) setHealth(getMaxHealth() + (getMaxHealth() * 0.20F));
//        super.setTame(pTamed);
//    }
//
//    @Override
//    public void setHealth(float pHealth) {
//        super.setHealth(pHealth);
//    }
//
//
//    @Override
//    protected void dropCustomDeathLoot(DamageSource pSource, int pLooting, boolean pRecentlyHit) {
//        super.dropCustomDeathLoot(pSource, pLooting, pRecentlyHit);
//        ItemStack itemstack0 = new ItemStack(ModItems.SPEED_STINGER_EGG.get(), 1);
//        ItemStack itemstack1 = new ItemStack(ModItems.SPEED_STINGER_EGG_FLOUTSCOOUT.get(), 1);
//        ItemStack itemstack2 = new ItemStack(ModItems.SPEED_STINGER_EGG_ICE_BREAKER.get(), 1);
//        ItemStack itemstack3 = new ItemStack(ModItems.SPEED_STINGER_EGG_SWEET_STING.get(), 1);
//
//        if (random.nextBoolean()) {
//            if (getDragonVariant() == 0) {
//                if (!itemstack0.isEmpty()) { //  && random.nextInt(10) == 1
//                    this.spawnAtLocation(itemstack0);
//                }
//            } else if (getDragonVariant() == 1) {
//                if (!itemstack1.isEmpty()) { //  && random.nextInt(10) == 1
//                    this.spawnAtLocation(itemstack1);
//                }
//            } else if (getDragonVariant() == 2) {
//                if (!itemstack2.isEmpty()) { //  && random.nextInt(10) == 1
//                    this.spawnAtLocation(itemstack2);
//                }
//            } else if (getDragonVariant() == 3) {
//                if (!itemstack3.isEmpty()) { //  && random.nextInt(10) == 1
//                    this.spawnAtLocation(itemstack3);
//                }
//            }
//        }
//    }
//
//    @Override
//    public void swing(InteractionHand pHand, boolean pUpdateSelf) {
//        if (random.nextInt(7) == 0) ticksSinceLastStingAttack = Util.secondsToTicks(3);
//        super.swing(pHand, pUpdateSelf);
//    }
//
//    @Override
//    public boolean doHurtTarget(Entity pEntity) {
//        initAttackEffects(pEntity);
//        return super.doHurtTarget(pEntity);
//    }
//
//    protected void initAttackEffects(Entity pEntity) {
//        if (pEntity instanceof LivingEntity) {
//            if (getCurrentAttackType() == 1) {
//                LivingEntity target = (LivingEntity) pEntity;
//                target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 250, 2));
//            }
//        }
//    }
//
//    @org.jetbrains.annotations.Nullable
//    public ADragonEggBase getBreedEggResult(ServerLevel level, @NotNull AgeableMob parent) {
//        SpeedStingerEgg dragon = ModEntities.SPEED_STINGER_EGG.get().create(level);
//        return dragon;
//    }
//
//    @Override
//    public void tick() {
//        super.tick();
//        if (getLeaderUUID() == null)
//            findPotentialLeader();
//
//        Pig pig = level.getNearestEntity(level.getEntitiesOfClass(Pig.class, getTargetSearchArea(14)),
//                TargetingConditions.forNonCombat(), this, this.getX(), this.getEyeY(), this.getZ());
//        if (pig != null) {
//            this.setTarget(pig);
//        }
//        if (ticksSinceLastStingAttack >= 0)
//            ticksSinceLastStingAttack--;
//
//        if (ticksSinceLastStingAttack >= 0) {
//            setCurrentAttackType(1);
//        } else {
//            setCurrentAttackType(0);
//        }
//        if (!onGround) {
//            jumpTicks++;
//        } else {
//            jumpTicks = 0;
//        }
//
//        checkInsideBlocks();
//        floatStinger();
//    }
//
//    protected float nextStep() {
//        return this.moveDist + 0.6F;
//    }
//
//    protected boolean shouldPassengersInheritMalus() {
//        return true;
//    }
//
//    @Override
//    protected @NotNull PathNavigation createNavigation(@NotNull Level pLevel) {
////        return new SpeedStingerPathNavigation(this , pLevel);
//        return super.createNavigation(pLevel);
//    }
//
//    public float getWalkTargetValue(BlockPos pPos, LevelReader pLevel) {
//        if (pLevel.getBlockState(pPos).getFluidState().is(FluidTags.WATER)) {
//            return 10.0F;
//        } else {
//            return this.isInWater() || this.isWaterBelow() ? Float.NEGATIVE_INFINITY : 0.0F;
//        }
//    }
//
//    // lava walking seems seemless
//
//    static class SpeedStingerPathNavigation extends GroundPathNavigation {
//
//        SpeedStingerPathNavigation(SpeedStinger pSpeedStinger, Level pLevel) {
//            super(pSpeedStinger, pLevel);
//        }
//
//        @Override
//        protected @NotNull PathFinder createPathFinder(int pMaxVisitedNodes) {
//            this.nodeEvaluator = new WalkNodeEvaluator();
//            return new PathFinder(this.nodeEvaluator, pMaxVisitedNodes);
//        }
//
//        @Override
//        protected boolean hasValidPathType(@NotNull BlockPathTypes pPathType) {
//            return pPathType == BlockPathTypes.WATER || pPathType == BlockPathTypes.DAMAGE_FIRE || pPathType == BlockPathTypes.DANGER_FIRE || super.hasValidPathType(pPathType);
//        }
//
//        @Override
//        public boolean isStableDestination(@NotNull BlockPos pPos) {
//            return this.level.getBlockState(pPos).is(Blocks.WATER) || super.isStableDestination(pPos);
//        }
//    }
//
//    protected static class SpeedStingerCustomLeapAttackGoal extends LeapAtTargetGoal {
//
//        SpeedStinger speedStingerEntity;
//
//        public SpeedStingerCustomLeapAttackGoal(SpeedStinger speedStingerEntity, float pYd) {
//            super(speedStingerEntity, pYd);
//            this.speedStingerEntity = speedStingerEntity;
//        }
//
//
//        /**
//         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
//         * method as well.
//         */
//        @Override
//        public boolean canUse() {
//            if (this.speedStingerEntity.isVehicle()) {
//                return false;
//            } else {
//                this.target = this.speedStingerEntity.getTarget();
//                if (this.target == null) {
//                    return false;
//                } else {
//                    double d0 = Math.sqrt(this.speedStingerEntity.distanceToSqr(this.target.getX(), 0, this.target.getZ()));
//                    if (d0 > 6) {
//                        if (!this.speedStingerEntity.isSpeedStingerOnGround()) {
//                            return false;
//                        } else {
//                            return this.speedStingerEntity.getRandom().nextInt(reducedTickDelay(75)) == 0;
//                        }
//                    } else {
//                        return false;
//                    }
//                }
//            }
//        }
//
//
//        /**
//         * Execute a one shot task or start executing a continuous task
//         */
//        public void start() {
//            Vec3 vec3 = this.speedStingerEntity.getDeltaMovement();
//            Vec3 vec31 = new Vec3(this.target.getX() - this.speedStingerEntity.getX(), 0.0D, this.target.getZ() - this.speedStingerEntity.getZ());
//            if (vec31.lengthSqr() > 1.0E-7D) {
//                vec31 = vec31.normalize().scale(0.8D).add(vec3.scale(0.5D));
//            }
//
//            this.speedStingerEntity.setDeltaMovement(vec31.x, (double) this.yd, vec31.z);
//        }
//    }
//}
//
