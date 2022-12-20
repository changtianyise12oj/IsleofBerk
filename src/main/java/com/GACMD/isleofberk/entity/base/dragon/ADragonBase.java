package com.GACMD.isleofberk.entity.base.dragon;

import com.GACMD.isleofberk.entity.AI.breed.DragonBreedGoal;
import com.GACMD.isleofberk.entity.AI.goal.FollowOwnerNoTPGoal;
import com.GACMD.isleofberk.entity.AI.goal.IOBLookAtPlayerGoal;
import com.GACMD.isleofberk.entity.AI.goal.IOBRandomLookAroundGoal;
import com.GACMD.isleofberk.entity.AI.ground.DragonWaterAvoidingRandomStrollGoal;
import com.GACMD.isleofberk.entity.AI.taming.AggressionToPlayersGoal;
import com.GACMD.isleofberk.entity.AI.target.DragonHurtByTargetGoal;
import com.GACMD.isleofberk.entity.AI.target.DragonMeleeAttackGoal;
import com.GACMD.isleofberk.entity.AI.target.DragonOwnerHurtTargetGoal;
import com.GACMD.isleofberk.entity.AI.water.DragonFloatGoal;
import com.GACMD.isleofberk.entity.dragons.stinger.Stinger;
import com.GACMD.isleofberk.entity.eggs.entity.base.ADragonEggBase;
import com.GACMD.isleofberk.entity.eggs.entity.eggs.StingerEgg;
import com.GACMD.isleofberk.entity.projectile.abase.BaseLinearFlightProjectile;
import com.GACMD.isleofberk.network.ControlNetwork;
import com.GACMD.isleofberk.network.message.ControlMessageAbility;
import com.GACMD.isleofberk.network.message.ControlMessageGoingDown;
import com.GACMD.isleofberk.network.message.ControlMessageJumping;
import com.GACMD.isleofberk.network.message.ControlMessageSECONDAbility;
import com.GACMD.isleofberk.registery.ModEntities;
import com.GACMD.isleofberk.registery.ModKeyBinds;
import com.GACMD.isleofberk.util.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.extensions.IForgeEntity;
import net.minecraftforge.entity.PartEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Predicate;

public abstract class ADragonBase extends TamableAnimal implements IAnimatable, IForgeEntity { //  /kill @e[type=! player]
    // 4071188624692119146
    protected static final EntityDataAccessor<Integer> DRAGON_VARIANT = SynchedEntityData.defineId(ADragonBase.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Float> CHANGE_IN_YAW = SynchedEntityData.defineId(ADragonBase.class, EntityDataSerializers.FLOAT);
    protected static final EntityDataAccessor<Integer> DISTURB_TICKS = SynchedEntityData.defineId(ADragonBase.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> DISTURB_TICKS_ABILITY = SynchedEntityData.defineId(ADragonBase.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> DRAGON_OVERLAY = SynchedEntityData.defineId(ADragonBase.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> FOOD_TAMING_LIMITER_BAR = SynchedEntityData.defineId(ADragonBase.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> PHASE_ONE_PROGRESS = SynchedEntityData.defineId(ADragonBase.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Boolean> TITAN_WING = SynchedEntityData.defineId(ADragonBase.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> IS_INCAPACITATED = SynchedEntityData.defineId(ADragonBase.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> ABILITY = SynchedEntityData.defineId(ADragonBase.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> SECOND_ABILITY = SynchedEntityData.defineId(ADragonBase.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> IS_MALE = SynchedEntityData.defineId(ADragonBase.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> SLEEPING = SynchedEntityData.defineId(ADragonBase.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> ON_GROUND = SynchedEntityData.defineId(ADragonBase.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> SITTING = SynchedEntityData.defineId(ADragonBase.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Integer> COMMANDS = SynchedEntityData.defineId(ADragonBase.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> CURRENT_ATTACK = SynchedEntityData.defineId(ADragonBase.class, EntityDataSerializers.INT);

    public static final Predicate<LivingEntity> PREY_SELECTOR = (p_30437_) -> {
        EntityType<?> entitytype = p_30437_.getType();
        return entitytype == EntityType.SHEEP || entitytype == EntityType.RABBIT || entitytype == EntityType.PIG || entitytype == EntityType.COW || entitytype == EntityType.CHICKEN || entitytype == EntityType.CREEPER;
    };

    public boolean renderedOnGUI;
    protected int baseDragonOnGroundHeight;
    protected int ticksSinceLastAttack;
    public float changeInYaw;
    BlockPos homePos;
    boolean hasHomePosition;

    protected ADragonBase(EntityType<? extends ADragonBase> animal, Level world) {
        super(animal, world);
        this.maxUpStep = 1;
        this.baseDragonOnGroundHeight = 4;
    }

    @Override
    public boolean isInvulnerableTo(@NotNull DamageSource pSource) {
        if (pSource == DamageSource.IN_FIRE || pSource == DamageSource.ON_FIRE || pSource == DamageSource.FALL || pSource == DamageSource.LAVA || pSource == DamageSource.IN_WALL || pSource == DamageSource.FLY_INTO_WALL || pSource == DamageSource.CACTUS) {
            return true;
        } else {
            return super.isInvulnerableTo(pSource);
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DRAGON_VARIANT, 0);
        this.entityData.define(CHANGE_IN_YAW, 0F);
        this.entityData.define(DISTURB_TICKS, 0);
        this.entityData.define(DISTURB_TICKS_ABILITY, 0);
        this.entityData.define(DRAGON_OVERLAY, 0);
        this.entityData.define(COMMANDS, 0);
        this.entityData.define(FOOD_TAMING_LIMITER_BAR, 0);
        this.entityData.define(PHASE_ONE_PROGRESS, 0);
        this.entityData.define(TITAN_WING, false);
        this.entityData.define(IS_INCAPACITATED, false);
        this.entityData.define(ABILITY, false);
        this.entityData.define(SECOND_ABILITY, false);
        this.entityData.define(SLEEPING, false);
        this.entityData.define(ON_GROUND, false);
        this.entityData.define(SITTING, false);
        this.entityData.define(IS_MALE, random.nextBoolean());
        this.entityData.define(CURRENT_ATTACK, 0);
    }

    @Override
    public void checkDespawn() {
        super.checkDespawn();
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("food_taming_threshold", this.getFoodTameLimiterBar());
        pCompound.putInt("phase_1_progress", this.getPhase1Progress());
        pCompound.putInt("disturb_ticks", this.getSleepDisturbTicks());
        pCompound.putInt("disturb_ticks_ability", this.getAbilityDisturbTicks());
        pCompound.putInt("variant", this.getDragonVariant());
        pCompound.putInt("overlay", this.getDragonOverlay());
        pCompound.putInt("commands", this.getCommand());
        pCompound.putInt("current_attack", this.getCurrentAttackType());
        pCompound.putBoolean("ability", this.isUsingAbility());
        pCompound.putBoolean("s_ability", this.isUsingSECONDAbility());
        pCompound.putBoolean("sleeping", this.isDragonSleeping());
        pCompound.putBoolean("dragonOnGround", this.isDragonOnGround());
        pCompound.putBoolean("sitting", this.isDragonSitting());
        pCompound.putBoolean("incapacitated", this.isDragonIncapacitated());
        pCompound.putBoolean("wandering", this.isDragonWandering());
        pCompound.putBoolean("is_male", this.isDragonMale());
        pCompound.putBoolean("following", this.isDragonFollowing());
        pCompound.putFloat("change_in_yaw", this.getChangeInYaw());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setFoodTameLimiterBar(pCompound.getInt("food_taming_threshold"));
        this.setPhase1Progress(pCompound.getInt("phase_1_progress"));
        this.setSleepDisturbTicks(pCompound.getInt("disturb_ticks"));
        this.setAbilityDisturbTicksAbility(pCompound.getInt("disturb_ticks_ability"));
        this.setDragonVariant(pCompound.getInt("variant"));
        this.setDragonOverlay(pCompound.getInt("overlay"));
        this.setCommands(pCompound.getInt("commands"));
        this.setIsTitanWing(pCompound.getBoolean("titan_wing"));
        this.setIsUsingAbility(pCompound.getBoolean("ability"));
        this.setIsUsingSECONDAbility(pCompound.getBoolean("s_ability"));
        this.setIsDragonIncapacitated(pCompound.getBoolean("incapacitated"));
        this.setIsDragonSitting(pCompound.getBoolean("sitting"));
        this.setIsDragonSleeping(pCompound.getBoolean("sleeping"));
        this.setIsDragonOnGround(pCompound.getBoolean("dragonOnGround"));
        this.setIsDragonWandering(pCompound.getBoolean("wandering"));
        this.setIsDragonFollowing(pCompound.getBoolean("following"));
        this.setCurrentAttackType(pCompound.getInt("current_attack"));
        this.setIsDragonMale(pCompound.getBoolean("is_male"));
        this.setChangeInYaw(pCompound.getFloat("change_in_yaw"));
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_146746_, DifficultyInstance p_146747_, MobSpawnType p_146748_, @Nullable SpawnGroupData p_146749_, @Nullable CompoundTag p_146750_) {
        System.out.println("Dragon spawned here: " + this.position() + "" + this.getClass());
        return super.finalizeSpawn(p_146746_, p_146747_, p_146748_, p_146749_, p_146750_);
    }

    @Override
    public void registerControllers(AnimationData data) {

    }

    /**
     * Static predicate for determining whether or not an animal can spawn at the provided location.
     *
     * @param pAnimal The animal entity to be spawned
     */
    public static boolean checkAnimalSpawnRules(EntityType<? extends Animal> pAnimal, LevelAccessor pLevel, MobSpawnType pReason, BlockPos pPos, Random pRandom) {
        return pLevel.getBlockState(pPos.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON) || pLevel.getBlockState(pPos.below()).is(BlockTags.SAND) ||
                pLevel.getBlockState(pPos.below()).is(BlockTags.LEAVES) || pLevel.getBlockState(pPos.below()).is(BlockTags.REPLACEABLE_PLANTS) ||
                pLevel.getBlockState(pPos.below()).is(BlockTags.MINEABLE_WITH_PICKAXE) || pLevel.getBlockState(pPos.below()).is(BlockTags.MINEABLE_WITH_SHOVEL) && pLevel.canSeeSky(pPos);
    }

    protected static boolean isBrightEnoughToSpawn(BlockAndTintGetter p_186210_, BlockPos p_186211_) {
        return p_186210_.getRawBrightness(p_186211_, 0) > 4;

    }

    @Override
    public AnimationFactory getFactory() {
        return null;
    }

    public int getCommand() {
        return this.entityData.get(COMMANDS);
    }

    public void setCommands(int commands) {
        this.entityData.set(COMMANDS, commands);
    }

    public void modifyCommand(int limit, Player player) {
        if (getCommand() >= limit) {
            this.setCommands(0);
        } else {
            this.setCommands(getCommand() + 1);
        }

        String wandering = "iob.command.wandering";
        String sitting = "iob.command.sitting";
        String following = "iob.command.following";
        switch (getCommand()) {
            default:
            case 0:
                player.displayClientMessage(new TranslatableComponent(wandering, Integer.toString(getCommand())), true);
                break;
            case 1:
                player.displayClientMessage(new TranslatableComponent(sitting, Integer.toString(getCommand())), true);
                break;
            case 2:
                player.displayClientMessage(new TranslatableComponent(following, Integer.toString(getCommand())), true);
                break;
        }
    }

    /**
     * Set value is 0
     *
     * @return
     */
    public boolean isDragonWandering() {
        return (this.entityData.get(COMMANDS)) == 0;
    }

    /**
     * Set value is 1
     *
     * @return
     */
    public boolean isDragonSitting() {
        return (this.entityData.get(COMMANDS)) == 1;
    }

    /**
     * Set value is 2
     *
     * @return
     */
    public boolean isDragonFollowing() {
        return (this.entityData.get(COMMANDS)) == 2;
    }

    /**
     * Called is wandering but really results in the default vanilla commands
     *
     * @param wandering
     */
    public void setIsDragonWandering(boolean wandering) {
        if (wandering) this.entityData.set(COMMANDS, 0);
    }

    public void setIsDragonSitting(boolean sitting) {
        if (sitting) {
            this.setOrderedToSit(isDragonSitting());
            this.entityData.set(COMMANDS, 1);
        }
    }

    public void setIsDragonFollowing(boolean following) {
        if (following) this.entityData.set(COMMANDS, 2);
    }

    public boolean isDragonSleeping() {
        return this.entityData.get(SLEEPING);
    }

    public void setIsDragonSleeping(boolean sleeping) {
        this.entityData.set(SLEEPING, sleeping);
    }

    public boolean isDragonOnGround() {
        return this.entityData.get(ON_GROUND);
    }

    public void setIsDragonOnGround(boolean sleeping) {
        this.entityData.set(ON_GROUND, sleeping);
    }

    public boolean isDragonIncapacitated() {
        return this.entityData.get(IS_INCAPACITATED);
    }

    public void setIsDragonIncapacitated(boolean incapacitated) {
        this.entityData.set(IS_INCAPACITATED, incapacitated);
    }

    public void setReady() {
        this.setIsDragonSleeping(false);
        this.setIsDragonSitting(false);
    }

    // Variant
    public int getDragonVariant() {
        return this.entityData.get(DRAGON_VARIANT);
    }

    public void setDragonVariant(int pType) {
        this.entityData.set(DRAGON_VARIANT, pType);
    }

    public int getDragonOverlay() {
        return this.entityData.get(DRAGON_OVERLAY);
    }

    public void setDragonOverlay(int pType) {
        this.entityData.set(DRAGON_OVERLAY, pType);
    }

    public boolean isTitanWing() {
        return this.entityData.get(TITAN_WING);
    }

    public void setIsTitanWing(boolean titan_wing) {
        this.entityData.set(TITAN_WING, titan_wing);
    }

    public boolean isDragonMale() {
        return this.entityData.get(IS_MALE);
    }

    public void setIsDragonMale(boolean male) {
        this.entityData.set(IS_MALE, male);
    }

    /**
     * there is an extra food taming phase, foodTamingPhaseProogress() is subtracted on ticks(),
     *
     * @param pIgnoreHunger
     * @return
     */
    public boolean canEatWithFoodOnHand(boolean pIgnoreHunger) {
        if (!isTame()) {
            return this.isInvulnerable() || pIgnoreHunger && getTarget() != null && this.getFoodTameLimiterBar() < 100;
        } else {
            return this.isInvulnerable() || pIgnoreHunger && getTarget() != null;

        }
    }

    /**
     * foodTamingPhaseProgress() is subtracted on ticks() if not Tamed, it limits how much food you can feed the dragon at food taming phase, then if it reaches max, health potion particles appear
     * if it does, then you can ride/dragon dismounts you then you ride again, repeatedly until you tame it.
     *
     * @return
     */
    public int getFoodTameLimiterBar() {
        return this.entityData.get(FOOD_TAMING_LIMITER_BAR);
    }

    public void setFoodTameLimiterBar(int food) {
        this.entityData.set(FOOD_TAMING_LIMITER_BAR, food);
    }

    /**
     * Progress on phase one to phase 2, reaching 100 is phase 2
     *
     * @return
     */
    public int getPhase1Progress() {
        return this.entityData.get(PHASE_ONE_PROGRESS);
    }

    public void setPhase1Progress(int phase_progress) {
        this.entityData.set(PHASE_ONE_PROGRESS, phase_progress);
    }

    public int getSleepDisturbTicks() {
        return this.entityData.get(DISTURB_TICKS);
    }

    public void setSleepDisturbTicks(int disturbTicks) {
        this.entityData.set(DISTURB_TICKS, disturbTicks);
    }

    /**
     * give ticks to enable special abilities. i.e. sting ready anims for triple_stryke or monstrous night mare on fire ticks
     *
     * @return
     */
    public int getAbilityDisturbTicks() {
        return this.entityData.get(DISTURB_TICKS_ABILITY);
    }

    public void setAbilityDisturbTicksAbility(int disturbTicks) {
        this.entityData.set(DISTURB_TICKS_ABILITY, disturbTicks);
    }

    public boolean isPhaseTwo() {
        return this.getPhase1Progress() >= 100;
    }

    public int getFoodTamingPhaseMaximumLevel() {
        return 100;
    }

    public boolean isUsingAbility() {
        return this.entityData.get(ABILITY);
    }

    public void setIsUsingAbility(boolean ability_pressed) {
        this.entityData.set(ABILITY, ability_pressed);
    }

    public boolean isUsingSECONDAbility() {
        return this.entityData.get(SECOND_ABILITY);
    }

    public void setIsUsingSECONDAbility(boolean ability_pressed) {
        this.entityData.set(SECOND_ABILITY, ability_pressed);
    }

    public boolean isRenderedOnGUI() {
        return renderedOnGUI;
    }

    public void setIsRenderedOnGUI(boolean renderedOnGUI) {
        this.renderedOnGUI = renderedOnGUI;
    }

    protected boolean isImmobile() {
        return false;
    }

    public float getRideCameraDistanceBack() {
        return 4;
    }

    public float getRideCameraDistanceVert() {
        return 1.2F;
    }

    public float getRideCameraDistanceHoriz() {
        return 0.0F;
    }

    public float getRideCameraDistanceFront() {
        return getRideCameraDistanceBack();
    }

    protected double getFollowDistance() {
        return this.getAttributeValue(Attributes.FOLLOW_RANGE);
    }

    public int getTicksSinceLastAttack() {
        return ticksSinceLastAttack;
    }


    /**
     * Check if the ground 2 blocks below is a solid. Replacement for Vanilla onGround
     *
     * @return solidBlockState
     */
    public boolean isWaterBelow() {
        BlockPos waterPos = new BlockPos(this.position().x, this.position().y - 3, this.position().z);
        return level.getBlockState(waterPos).getBlock() instanceof LiquidBlock;
    }

    /**
     * Check if the ground 5 blocks below is a solid. Replacement for Vanilla onGround
     *
     * @return solidBlockState
     */
    public boolean isOnJumpHeight() {
        BlockPos solidPos = new BlockPos(this.position().x, this.position().y - 6, this.position().z);
        return !level.getBlockState(solidPos).isAir();
    }

    //  Attributes
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 120.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.4F)
                .add(Attributes.ATTACK_DAMAGE, 4.5F)
                .add(Attributes.FOLLOW_RANGE, 7F)
                .add(ForgeMod.SWIM_SPEED.get(), 1);

    }

    public AABB getTargetSearchArea(double distance) {
        return this.getBoundingBox().inflate(distance, distance, distance);
    }

    @Override
    public double getFluidJumpThreshold() {
        return (double) this.getEyeHeight() < 0.4D ? 0.0D : 0.2D;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new DragonFloatGoal(this));
        this.goalSelector.addGoal(3, new DragonBreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, getMeleeAttackGoal());
        this.goalSelector.addGoal(1, new FollowOwnerNoTPGoal(this, 1.1D, 1.0F, 1.0F, false));
        this.goalSelector.addGoal(6, new DragonWaterAvoidingRandomStrollGoal(this, 0.7D, 1.0000001E-5F));
        this.goalSelector.addGoal(7, new IOBLookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new IOBRandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new DragonHurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new AggressionToPlayersGoal<>(this, Player.class, true, getAggressionType(), null));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new DragonOwnerHurtTargetGoal(this));
    }

    protected DragonMeleeAttackGoal getMeleeAttackGoal() {
        return new DragonMeleeAttackGoal(this, 1, false);
    }


    protected Item tameItem() {
        return Items.BEEF;
    }

    protected boolean isItemStackForTaming(ItemStack stack) {
        return stack.is(tameItem());
    }

    /**
     * This filter is applied to the Entity search. Only matching entities will be targeted.
     * 0 : none; 1: weapon; 2: armor or weapon 3: always, only be obtained via eggs
     */
    protected int getAggressionType() {
        return 0;
    }

    @Override
    public void setInLove(@javax.annotation.Nullable Player pPlayer) {
        this.setInLoveTime(Util.mcDaysToMinutes(10));
        super.setInLove(pPlayer);
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        Item item = itemstack.getItem();
        if (item == Items.STICK && isOwnedBy(pPlayer)) {
            modifyCommand(2, pPlayer);
            return InteractionResult.SUCCESS;
        }

        if (this.isBreedingFood(itemstack) && !shouldStopMovingIndependently()) {
            if (isOwnedBy(pPlayer)) {
                int i = this.getAge();
                if (!this.level.isClientSide && i == 0 && this.canFallInLove()) {
                    this.usePlayerItem(pPlayer, pHand, itemstack);
                    this.setInLove(pPlayer);
                    this.gameEvent(GameEvent.MOB_INTERACT, this.eyeBlockPosition());
                    return InteractionResult.SUCCESS;
                }
            } else {
                String string = "iob.only.owner.breed";
                pPlayer.displayClientMessage(new TranslatableComponent(string), false);
                return InteractionResult.FAIL;
            }
        }
        return super.mobInteract(pPlayer, pHand);
    }

    public boolean shouldStopMoving() {
        return this.isDragonSleeping() || this.isDragonSitting() && !isVehicle() || isDragonIncapacitated();
    }

    public boolean shouldStopMovingIndependently() {
        return this.isDragonSleeping() || this.isDragonSitting() && !isVehicle() || isDragonIncapacitated();
    }

    @Override
    public void swing(InteractionHand pHand) {
        this.swing(pHand, true);

        ticksSinceLastAttack = 0;
    }

    @Override
    public boolean doHurtTarget(Entity pEntity) {
        return super.doHurtTarget(pEntity);
    }

    @Override
    public void setTarget(@Nullable LivingEntity pLivingEntity) {
        if (!this.isBaby()) {
            super.setTarget(pLivingEntity);
        } else if (pLivingEntity instanceof Player playerTarget) {
            if (!playerTarget.isCreative()) {
                super.setTarget(playerTarget);
            }
        }
    }

    @Override
    public boolean hurt(@NotNull DamageSource pSource, float pAmount) {
        setSleepDisturbTicks(500);
        setAbilityDisturbTicksAbility(500);
        if (pAmount > 0) {
            if (!isTame()) {
                if (pSource.getEntity() instanceof Player player) {
                    setTarget(player);
                }
            }
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    public void swing(InteractionHand pHand, boolean pUpdateSelf) {
        super.swing(pHand, pUpdateSelf);
    }

    @Override
    public void tame(Player pPlayer) {
        this.setTarget(null);
        this.setPersistenceRequired();
        super.tame(pPlayer);
    }

    public boolean isTamingPhaseBarFull() {
        return getFoodTameLimiterBar() >= getFoodTamingPhaseMaximumLevel();
    }

    public boolean isNocturnal() {
        return false;
    }

    // TODO: ~~General~~
    // TODO: may use right click for ranged breath ability
    // TODO: Double Check sleeping AI for bugs
    // TODO: better hatching AI, ss are golems when tamed. Work on eggs
    // TODO: fly above attacking players and use breath
    // TODO: Breath on attacking entities once projectile assets and configs are laid out
    // TODO: overall, spawn will be revamped if all dragons are added, take in consideration proximity
    // TODO: flying on it's own is troublesome, no navigator switching
    // TODO: AI stuck on edge of block
    // TODO: Flight stuck when facing down a certain angle
    // TODO: ~~close dragon's eyes when sleeping when all models are released to get consistent eye names.~~ System is fine as it is
    // TODO:

    // TODO: ~~~Terrible Terror~~~
    // TODO: ocelot taming for terrors (discuss), they spawn near the water and might clog thanks to horrid navgiator like vanilla cats
    // TODO: fix spawning, terrors spawning on the water

    // TODO: ~~~Speed Stinger~~~
    // TODO: check SS spawning
    // TODO: Speed stinger meat
    // TODO: better hatching AI, ss are golems when tamed

    // TODO: ~~~Night Fury~~~
    // TODO: night fury special spawning
    // TODO: either disappear during the day or rarely spawn in biomes

    // TODO: ~~~Monstrous Nightmare~~~
    // TODO: Fire textures layer
    // TODO: breath
    // TODO:

    // TODO: ~~~ZippleBack~~~
    // TODO: exploding breath
    // TODO: breath are AOE and give potion effects
    // TODO: make sure block damage is not too much but cause hella fire
    // TODO: fuel very limited
    // TODO:

    // TODO: ~~~Stinger~~~
    // TODO: may use tail spin attack
    // TODO:

    // TODO: ~~~Deadly Nadder~~~
    // TODO: spike attacks
    // TODO: use arrows for place holder
    // TODO: getTailPos for tail projectile
    // TODO:

    // TODO: ~~~Gronckle~~~
    // TODO: Projectile Attack
    // TODO: slow but tanky, gives rider resistance effect. riders die faster than the dragon itself
    // TODO:

    // TODO: ~~~Triple Stryke~~~
    // TODO: Projectile attack
    // TODO: TS sting attack create clouds of AOE potion
    // TODO: TS use tail sting attack for long range, projectile
    // TODO:
    @Override
    public void tick() {
        super.tick();
        if (this.getCommand() == 1) {
            this.setOrderedToSit(isDragonSitting());
        }
        if (shouldStopMoving()) {
            this.getNavigation().stop();
            this.getNavigation().timeoutPath();
            this.setRot(this.getYRot(), this.getXRot());
        }

//        if (getControllingPassenger() instanceof Player) {
////            ArrayList<BlockPos> pos = new ArrayList<>();
////            pos.add(new BlockPos(position().add(0,0,0)));
////            pos.add(new BlockPos(position().add(0,-1,0)));
////            pos.add(new BlockPos(position().add(0,-2,0)));
//            List<BlockPos> solidPosArray = Arrays.asList(new BlockPos(position().add(0, -1, 0)), new BlockPos(position().add(0, -2, 0)), new BlockPos(position().add(0, -3, 0)));
////            List<BlockPos> solidPosArray = Arrays.asList(new BlockPos(position().add(0, -1, 0)), new BlockPos(position().add(0, -2, 0)), new BlockPos(position().add(0, -3, 0)));
//            for (int i = 0; i < solidPosArray.size(); ++i) {
//                // Printing and display the elements in ArrayList
////                System.out.println(solidPosArray.get(i));
//                if (level.getBlockState(solidPosArray.get(i)).isSolidRender(level, solidPosArray.get(i))) {
//                    setIsDragonOnGround(true);
//                    System.out.println(solidPosArray.get(i));
//                    System.out.println("solid? " + level.getBlockState(solidPosArray.get(i)).isSolidRender(level, solidPosArray.get(i)));
//                } else {
//                    setIsDragonOnGround(false);
//                }
//            }
//        }

//        if (getControllingPassenger() instanceof Player) {
//            ArrayList<BlockPos> pos = new ArrayList<>();
//            pos.add(new BlockPos(position().add(0,0,0)));
//            pos.add(new BlockPos(position().add(0,-1,0)));
//            pos.add(new BlockPos(position().add(0,-2,0)));

            BlockPos pos1 = new BlockPos(position().add(0, -1, 0));
            BlockPos pos2 = new BlockPos(position().add(0, -2, 0));
            BlockPos pos3 = new BlockPos(position().add(0, -3, 0));
//            List<BlockPos> solidPosArray = Arrays.asList(new BlockPos(position().add(0, -1, 0)), new BlockPos(position().add(0, -2, 0)), new BlockPos(position().add(0, -3, 0)));
//            List<BlockPos> solidPosArray = Arrays.asList(new BlockPos(position().add(0, -1, 0)), new BlockPos(position().add(0, -2, 0)), new BlockPos(position().add(0, -3, 0)));
//            for (int i = 0; i < solidPosArray.size(); ++i) {
            // Printing and display the elements in ArrayList
//                System.out.println(solidPosArray.get(i));
            if (level.getBlockState(pos1).getMaterial().isSolid()) {
                setIsDragonOnGround(true);
            } else {
                setIsDragonOnGround(false);
            }
//        }

        if (getSleepDisturbTicks() > 0)
            setSleepDisturbTicks(getSleepDisturbTicks() - 1);

        if (getAbilityDisturbTicks() > 0)
            setAbilityDisturbTicksAbility(getAbilityDisturbTicks() - 1);

        if (ticksSinceLastAttack >= 0) { // used for jaw animation
            ++ticksSinceLastAttack;
            if (ticksSinceLastAttack > 1000) {
                ticksSinceLastAttack = -1; // reset at arbitrary large value
            }
        }

        if (level.isClientSide()) {
            updateClientControls();
        }

        if (!isTame()) {
            // used in tamingFood levels
            if (level.random.nextInt(85) == 1) {
                this.modifyFoodTamingThreshold(-1);
            }

            // gradually reduce if player isn't consistent about their taming
            if (level.random.nextInt(600) == 1 && getPhase1Progress() < 95) {
                this.modifyPhase1Progress(-1);
            }
        }
        if (!isDragonIncapacitated() && random.nextInt(150) == 1 && getHealth() < getMaxHealth()) {
            this.heal(5);
        }

        sleepMechanics();
    }

    protected void sleepMechanics() {
        if (!level.isClientSide()) {
            if (canSleep()) {
                // speed stingers are nocturnal
                if (!isNocturnal()) {
                    if (!level.isDay()) {
                        setIsDragonSleeping(true);
                    } else {
                        setIsDragonSleeping(false);
                    }
                } else {
                    if (level.isDay()) {
                        setIsDragonSleeping(true);
                    } else {
                        setIsDragonSleeping(false);
                    }
                }
            } else {
                setIsDragonSleeping(false);
            }
        }
    }

    /**
     * put the sleep AI here since tick is the only one kicking them to start
     *
     * @return
     */
    public boolean canSleep() {
        LivingEntity lastHurt = this.getLastHurtByMob();
        LivingEntity target = this.getTarget();
        int i = this.getLastHurtByMobTimestamp();
        boolean canSleep = lastHurt == null && i < 2500 && target == null;
        if (!canSleep) {
            return false;
        }

        if (this.isVehicle()) {
            return false;
        }

        if (this.getSleepDisturbTicks() > 3) {
            return false;
        }

        // only sleep when sitting or wandering
        if (this.isDragonFollowing()) {
            return false;
        }

        return this.isDragonOnGround() && !this.isInWater() && !this.isWaterBelow();
    }

    /**
     * Check if the ground 4 blocks below is a solid. Replacement for Vanilla onGround
     *
     * @return solidBlockState
     */
    public void isDragonOnGroundTickLoop() {
        for (int i = 0; getControllingPassenger() != null ? i < 3 : i < baseDragonOnGroundHeight; ++i) {
            BlockPos solidPos = new BlockPos(this.position().x, this.position().y - i, this.position().z);
            if (level.getBlockState(solidPos).isSolidRender(level, solidPos)) {
                setIsDragonOnGround(false);
                System.out.println(solidPos);
                System.out.println("solid? " + level.getBlockState(solidPos).isSolidRender(level, solidPos));
            } else {
                setIsDragonOnGround(true);
            }
        }
    }

    public static boolean isDarkEnoughToSleep(Level pLevel, BlockPos pPos, Random pRandom) {
        return pLevel.getBrightness(LightLayer.BLOCK, pPos) > 0 || pLevel.getBrightness(LightLayer.SKY, pPos) > pRandom.nextInt(32);
    }


    @Override
    public void setYRot(float pYRot) {
        if (!shouldStopMoving())
            super.setYRot(pYRot);
    }

    public void ItemOnMouth(ItemStack newItemOnMouth) {
        ItemStack stack = this.getItemBySlot(EquipmentSlot.MAINHAND);
        if (stack.isEmpty()) {
            this.setItemSlot(EquipmentSlot.MAINHAND, newItemOnMouth);
        }
    }

    public boolean canWearArmor() {
        return true;
    }

    public Vec3 getThroatPos(ADragonBase entity) {
        Vec3 bodyOrigin = position();

        double x = -Math.sin(this.yBodyRot * Math.PI / 180) * 2.4;
        double y = 1.5;
        double z = Math.cos(this.yBodyRot * Math.PI / 180) * 2.4;
        float scale = isBaby() ? 0.2F : 1;
        Vec3 throatPos = bodyOrigin.add(new Vec3(x * scale, y * scale, z * scale));
        return throatPos;

    }

    public Vec3 getTailPos(ADragonBase entity) {
        Vec3 bodyOrigin = position();

        double x = -Math.sin(this.getYRot() * Math.PI / 180) * 2.4;
        double y = 1.5;
        double z = Math.cos(this.getYRot() * Math.PI / 180) * 2.4;
        Vec3 throatPos = bodyOrigin.add(new Vec3(x, y, z));
        return throatPos;

    }

    /**
     * Applies this boat's yaw to the given entity. Used to update the orientation of its passenger.
     */
    protected void clampRotation(Entity pEntityToUpdate) {
        pEntityToUpdate.setYBodyRot(this.getYRot());
        float f = Mth.wrapDegrees(pEntityToUpdate.getYRot() - this.getYRot());
        float f1 = Mth.clamp(f, -105.0F, 105.0F);
        pEntityToUpdate.yRotO += f1 - f;
        pEntityToUpdate.setYRot(pEntityToUpdate.getYRot() + f1 - f);
        pEntityToUpdate.setYHeadRot(pEntityToUpdate.getYRot());
    }


    private net.minecraftforge.common.util.LazyOptional<?> itemHandler = null;

    @Override
    public <
            T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @javax.annotation.Nullable net.minecraft.core.Direction facing) {
        if (this.isAlive() && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && itemHandler != null)
            return itemHandler.cast();
        return super.getCapability(capability, facing);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        if (itemHandler != null) {
            net.minecraftforge.common.util.LazyOptional<?> oldHandler = itemHandler;
            itemHandler = null;
            oldHandler.invalidate();
        }
    }

    /**
     * IOB version of taming food
     *
     * @param pStack
     * @return
     */
    public boolean isBreedingFood(ItemStack pStack) {
        return pStack.is(Items.SUSPICIOUS_STEW);
    }

    /**
     * Vanilla method for breeding item, disabled isFood() is also used for taming, causes insta tame
     *
     * @param pStack
     * @return
     */
    @Override
    public boolean isFood(ItemStack pStack) {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public void updateClientControls() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.options.keyJump.isDown()) {
            ControlNetwork.INSTANCE.sendToServer(new ControlMessageJumping(true));
        } else {
            ControlNetwork.INSTANCE.sendToServer(new ControlMessageJumping(false));
        }
        if (ModKeyBinds.keyAbilty.isDown()) {
            ControlNetwork.INSTANCE.sendToServer(new ControlMessageAbility(true));
        } else {
            ControlNetwork.INSTANCE.sendToServer(new ControlMessageAbility(false));
        }
        if (ModKeyBinds.keySecondAbilty.isDown()) {
            ControlNetwork.INSTANCE.sendToServer(new ControlMessageSECONDAbility(true));
        } else {
            ControlNetwork.INSTANCE.sendToServer(new ControlMessageSECONDAbility(false));
        }
        if (ModKeyBinds.keyDown.isDown()) {
            ControlNetwork.INSTANCE.sendToServer(new ControlMessageGoingDown(true));
        } else {
            ControlNetwork.INSTANCE.sendToServer(new ControlMessageGoingDown(false));
        }

    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    /**
     * Checks if this can be fed to dragons
     *
     * @param pStack
     * @return
     */
    public boolean isFoodEdibleToDragon(ItemStack pStack) {
        Item item = pStack.getItem();
        return pStack.is(Items.SALMON) || pStack.is(Items.COD) || pStack.is(Items.ROTTEN_FLESH) ||
                pStack.is(Items.TROPICAL_FISH) || pStack.is(Items.PUFFERFISH) || (item.isEdible() && item.getFoodProperties().isMeat() && item.getFoodProperties() != null) && !pStack.isEmpty();
    }

    public int getMaxTemper() {
        return 100;
    }

    public BlockPos getHomePos() {
        return homePos;
    }

    public void setHomePos(BlockPos homePos) {
        this.homePos = homePos;
    }

    public void modifyFoodTamingThreshold(int x) {
        int i = Mth.clamp(this.getFoodTameLimiterBar() + x, 0, this.getFoodTamingPhaseMaximumLevel());
        this.setFoodTameLimiterBar(i);
    }

    public void modifyPhase1Progress(int x) {
        int i = Mth.clamp(this.getPhase1Progress() + x, 0, 100);
        this.setPhase1Progress(i);
    }

    public void makeMad() {

    }

    @Override
    public boolean isPersistenceRequired() {
        return false;
    }

    public int getCurrentAttackType() {
        return this.entityData.get(CURRENT_ATTACK);
    }

    public void setCurrentAttackType(int currentAttack) {
        this.entityData.set(CURRENT_ATTACK, currentAttack);
    }

    protected void performInCapacitate() {
        if (getHealth() < getMaxHealth() * 0.30) {
            setIsDragonIncapacitated(true);
        } else {
            setIsDragonIncapacitated(false);
        }
    }

    @Nullable
    public ADragonEggBase getBreedEggResult(ServerLevel level, @NotNull AgeableMob parent) {
        StingerEgg dragon = ModEntities.STINGER_EGG.get().create(level);
        return dragon;
    }

    public int getMaxAmountOfVariants() {
        return 0;
    }

    @Override
    public void resetLove() {
        super.resetLove();
    }

    @Override
    public void spawnChildFromBreeding(ServerLevel serverLevel, Animal partner) {
        super.spawnChildFromBreeding(serverLevel, partner);
        if (partner instanceof ADragonBase dragonPartner) {
            ADragonEggBase egg = this.getBreedEggResult(serverLevel, dragonPartner);
            //Reset the "inLove" state for the dragonPartners
            this.setAge(6000);
            dragonPartner.setAge(6000);
            this.resetLove();
            dragonPartner.resetLove();

            if (egg != null) {
                this.setAge(6000);
                dragonPartner.setAge(6000);
                this.resetLove();
                dragonPartner.resetLove();
                egg.moveTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
                serverLevel.addFreshEntityWithPassengers(egg);
                serverLevel.broadcastEntityEvent(this, (byte) 18);
                egg.setDragonVariant(random.nextInt(getMaxAmountOfVariants()));
            }
            if (serverLevel.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
                serverLevel.addFreshEntity(new ExperienceOrb(serverLevel, this.getX(), this.getY(), this.getZ(), this.getRandom().nextInt(7) + 1));
            }
        }
    }

    public int getExplosionStrength() {
        return 0;
    }

    /**
     * @param dragon
     * @param entity
     * @param projectile
     * @return
     */
    public float getProjectileDamage(ADragonBase dragon, Entity entity, BaseLinearFlightProjectile projectile) {
//        return 22;
        return 15;
    }

    public boolean tameWithName(Player pPlayer) {
        this.setOwnerUUID(pPlayer.getUUID());
        this.setTame(true);
        if (pPlayer instanceof ServerPlayer) {
            CriteriaTriggers.TAME_ANIMAL.trigger((ServerPlayer) pPlayer, this);
        }

        modifyPhase1Progress(100);

        this.level.broadcastEntityEvent(this, (byte) 7);
        return true;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return null;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return null;
    }

    protected void addParticlesAroundSelf(ParticleOptions p_36209_) {
        for (int i = 0; i < 4; ++i) {
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            this.level.addParticle(p_36209_, this.getRandomThroatX(!isBaby() ? 0.4D : 0.1), getThroatRandomY(0.4D), this.getThroatRandomZ(0.4D), d0, d1, d2);
        }

    }

    public double getX(double pScale) {
        return this.getThroatPos(this).x + (double) this.getBbWidth() * pScale;
    }

    public double getRandomThroatX(double pScale) {
        return this.getThroatPos(this).x + (2.0D * this.random.nextDouble() - 1.0D) * pScale;
    }

    public double getY(double pScale) {
        return this.getThroatPos(this).y + (double) this.getBbHeight() * pScale;
    }

    public double getThroatRandomY(double pScale) {
        return this.getThroatPos(this).y + (double) this.getBbHeight() * pScale;
    }

    public double getZ(double pScale) {
        return this.getThroatPos(this).z + (double) this.getBbWidth() * pScale;
    }

    public double getThroatRandomZ(double pScale) {
        return this.getThroatPos(this).z + ((2.0D * this.random.nextDouble() - 1.0D) * pScale);
    }

    public float getChangeInYaw() {
        return entityData.get(CHANGE_IN_YAW);
    }

    public void setChangeInYaw(float changeInYaw) {
        this.entityData.set(CHANGE_IN_YAW, changeInYaw);
    }

    public float getSoundPitch() {
        return isBaby() ? 1.4F : 1F;
    }

    public static class DragonPart extends PartEntity<ADragonBase> {

        ADragonBase parent;
        EntityDimensions size;
        public final String name;

        public DragonPart(ADragonBase parent, String name, float sizeX, float sizeY) {
            super(parent);
            this.size = EntityDimensions.scalable(sizeX, sizeY);
            this.parent = parent;
            this.name = name;
            this.refreshDimensions();
        }

        @Override
        protected void defineSynchedData() {

        }

        @Override
        protected void readAdditionalSaveData(CompoundTag pCompound) {

        }

        @Override
        protected void addAdditionalSaveData(CompoundTag pCompound) {

        }

        @Override
        public boolean isPickable() {
            return true;
        }

        @Override
        public boolean hurt(DamageSource pSource, float pAmount) { //  && pSource.getEntity().getVehicle() != parent
            Entity entity = pSource.getEntity();
            if (entity instanceof LivingEntity rider) {
                if (pSource.equals(DamageSource.mobAttack(rider)) && rider.getVehicle() == parent) {
                    return false;
                }
            }
            return (!isInvulnerableTo(pSource) || Objects.requireNonNull(entity).getVehicle() == parent) && parent.hurt(pSource, pAmount);
        }

        public boolean is(@NotNull Entity pEntity) {
            return this == pEntity || this.parent == pEntity;
        }

        public Packet<?> getAddEntityPacket() {
            throw new UnsupportedOperationException();
        }

        public @NotNull EntityDimensions getDimensions(@NotNull Pose pPose) {
            return this.size;
        }

        public boolean shouldBeSaved() {
            return false;
        }
    }
}
