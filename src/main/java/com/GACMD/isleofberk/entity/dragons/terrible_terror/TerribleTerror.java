package com.GACMD.isleofberk.entity.dragons.terrible_terror;

import com.GACMD.isleofberk.entity.AI.goal.FollowOwnerNoTPGoal;
import com.GACMD.isleofberk.entity.AI.goal.IOBLookAtPlayerGoal;
import com.GACMD.isleofberk.entity.AI.ground.DragonWaterAvoidingRandomStrollGoal;
import com.GACMD.isleofberk.entity.AI.target.DragonOwnerHurtTargetGoal;
import com.GACMD.isleofberk.entity.AI.water.DragonFloatGoal;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideableBreathUser;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideableProjUser;
import com.GACMD.isleofberk.entity.eggs.entity.base.ADragonEggBase;
import com.GACMD.isleofberk.entity.eggs.entity.eggs.TerribleTerrorEgg;
import com.GACMD.isleofberk.entity.projectile.abase.BaseLinearFlightProjectile;
import com.GACMD.isleofberk.entity.projectile.breath_user.firebreaths.FireBreathProjectile;
import com.GACMD.isleofberk.network.ControlNetwork;
import com.GACMD.isleofberk.network.message.*;
import com.GACMD.isleofberk.registery.ModEntities;
import com.GACMD.isleofberk.registery.ModKeyBinds;
import com.GACMD.isleofberk.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.extensions.IForgeItem;
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

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public class TerribleTerror extends ADragonBaseFlyingRideableBreathUser implements IAnimatable, NeutralMob {

    private int ticksSinceEaten;

    protected static final EntityDataAccessor<Optional<UUID>> LAST_PLAYER_MOUNTED = SynchedEntityData.defineId(TerribleTerror.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final Ingredient TEMPT_INGREDIENT = Ingredient.of(Items.COD, Items.SALMON, Items.CHICKEN, Items.PORKCHOP, Items.BEEF, Items.MUTTON);
    private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(TerribleTerror.class, EntityDataSerializers.INT);

    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    @javax.annotation.Nullable
    private UUID persistentAngerTarget;

    public static final Predicate<LivingEntity> PREY_SELECTOR = (p_30437_) -> {
        EntityType<?> entitytype = p_30437_.getType();
        return entitytype == EntityType.TROPICAL_FISH || entitytype == EntityType.PUFFERFISH || entitytype == EntityType.COD || entitytype == EntityType.SALMON || entitytype == EntityType.RABBIT || entitytype == EntityType.CHICKEN;
    };


    AnimationFactory factory = new AnimationFactory(this);
    static final Predicate<ItemEntity> ALLOWED_ITEMS = (stack) -> {
        return !stack.hasPickUpDelay() && stack.isAlive();
    };

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving() && !shouldStopMovingIndependently()) {
            if (!isDragonOnGround()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("Flap", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            } else if (isHovering()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("Hover", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            } else if (!isDragonSitting() && !isDragonIncapacitated()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("Walk", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
        }

        Entity vehicle = getVehicle();
        if (vehicle instanceof Player player) {
            if (player.isOnGround() || player.isInWater() || player.isInLava()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("PlayerHeadIdle", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            } else if (player.getVehicle() != null) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("PlayerHeadIdle", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            } else if (player.isFallFlying()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("Flap", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            } else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("PlayerHeadFlying", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
        }
        if (this.isDragonSitting()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("Sit", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        if (this.isDragonSleeping()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("Sleep", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        if (this.getLookControl().isLookingAtTarget()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("HeadCurious", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("Idle", ILoopType.EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    public TerribleTerror(EntityType<? extends TerribleTerror> animal, Level world) {
        super(animal, world);
        this.setCanPickUpLoot(true);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(LAST_PLAYER_MOUNTED, Optional.empty());
        this.entityData.define(DATA_REMAINING_ANGER_TIME, 0);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        UUID uuid;
        if (pCompound.hasUUID("last_mount")) {
            uuid = pCompound.getUUID("last_mount");
            this.setLastMountedPlayerUUID(uuid);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        if (this.getLastMountedPlayerUUID() != null) {
            pCompound.putUUID("last_mount", this.getLastMountedPlayerUUID());
        }
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<TerribleTerror>(this, "terrible_terror_controller", 5, this::predicate));
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @javax.annotation.Nullable SpawnGroupData pSpawnData, @javax.annotation.Nullable CompoundTag pDataTag) {
        pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        this.setDragonVariant(this.getRandom().nextInt(getMaxAmountOfVariants()));
        return pSpawnData;
    }

    @Override
    public int getMaxAmountOfVariants() {
        return 8;
    }

    @javax.annotation.Nullable
    public UUID getLastMountedPlayerUUID() {
        return this.entityData.get(LAST_PLAYER_MOUNTED).orElse((UUID) null);
    }

    /**
     * called when player mounts a terror, used to save the data if the player is unceremoniously dismounted
     *
     * @param uuid
     */
    public void setLastMountedPlayerUUID(@javax.annotation.Nullable UUID uuid) {
        this.entityData.set(LAST_PLAYER_MOUNTED, Optional.ofNullable(uuid));
    }

    /**
     * It rides YOU not the other way around
     *
     * @return
     */
    @Override
    public boolean canBeMounted() {
        return false;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FollowOwnerNoTPGoal(this, 1.1D, 2.0F, 2.0F, false));
        this.goalSelector.addGoal(1, new DragonFloatGoal(this));
        this.goalSelector.addGoal(1, new BreedGoal(this, 1));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(6, new DragonWaterAvoidingRandomStrollGoal(this, 0.7D, 1.0000001E-5F));
        this.goalSelector.addGoal(7, new IOBLookAtPlayerGoal(this, Player.class, 8.0F));
//        this.goalSelector.addGoal(1, new DragonRideTilTamed(this, 1));
        this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
        this.targetSelector.addGoal(2, new NonTameRandomTargetGoal<>(this, LivingEntity.class, false, PREY_SELECTOR));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new DragonOwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(8, new ResetUniversalAngerTargetGoal<>(this, true));
        this.goalSelector.addGoal(11, new TerrorFeedOnGroundGoal(this));
    }

    @Override
    public boolean isItemStackForTaming(ItemStack pStack) {
        return pStack.is(Items.SALMON) || pStack.is(Items.TROPICAL_FISH) || pStack.is(Items.PUFFERFISH);
    }

    @Override
    public boolean isFoodEdibleToDragon(ItemStack pStack) {
        return super.isFoodEdibleToDragon(pStack);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.ARMOR, 0.5)
                .add(Attributes.MOVEMENT_SPEED, 0.3F)
                .add(Attributes.ATTACK_DAMAGE, 2F)
                .add(ForgeMod.SWIM_SPEED.get(), 4F)
                .add(Attributes.ATTACK_DAMAGE, 2F);
    }

    @Override
    public boolean isBreedingFood(ItemStack pStack) {
        return pStack.is(Items.COD);
    }

    protected int getInLoveCoolDownInMCDays() {
        return 4;
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        Item item = itemstack.getItem();

        if (item.isEdible()) {

            ItemStack stack = this.getItemBySlot(EquipmentSlot.MAINHAND);
            if (stack.isEmpty()) {
                this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(item));
            }
            if (!isTame()) {
                if (isItemStackForTaming(itemstack) && random.nextInt(8) == 1) {
                    this.tameWithName(pPlayer);
                    this.level.broadcastEntityEvent(this, (byte) 7);
                } else {
                    this.level.broadcastEntityEvent(this, (byte) 6);
                }
            }
            return InteractionResult.SUCCESS;
            // mount the dragon to player if it is not unceremoniously dismounted
            // plan to temporary hide(despawn?) the terror then unhide(respawn?) if the player appears close by
        } else if (item != Items.STICK && !isBaby() && isTame() && !isItemStackForTaming(itemstack) && !isBreedingFood(itemstack)) { //  && isDragonBelziumHeld(itemstack)
            ridePlayer(pPlayer);
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(pPlayer, pHand);
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
            ControlNetwork.INSTANCE.sendToServer(new ControlMessageTerribleTerrorAbility(true));
        } else {
            ControlNetwork.INSTANCE.sendToServer(new ControlMessageTerribleTerrorAbility(false));
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
    protected void foodTamingInteraction(Player pPlayer, InteractionHand pHand, ItemStack itemstack) {
        if (!pPlayer.getAbilities().instabuild) {
            itemstack.shrink(1);
        }

        IForgeItem forgeItem = itemstack.getItem();
//        int nutrition = Objects.requireNonNull(forgeItem.getFoodProperties(itemstack, this)).getNutrition();
        int nutrition = 6;
        this.heal(nutrition);
    }

    protected void ridePlayer(Player player) {
        if (player.getPassengers().size() < 3) {
            this.startRiding(player, true);
            ControlNetwork.sendMSGToAll(new DragonRideMessage(this.getId(), true));
        }
    }

    @Override
    public boolean startRiding(Entity pEntity, boolean pForce) {
        this.setSleepDisturbTicks(Util.secondsToTicks(20));
        return super.startRiding(pEntity, pForce);
    }

    @Override
    public void rideTick() {
        Entity entity = this.getVehicle();
        if (entity instanceof Player player) {
            if (this.isPassenger() && !player.isAlive()) {
                this.stopRiding();
            } else {
                this.setDeltaMovement(0, 0, 0);
                this.tick();
                if (this.isPassenger()) {
                    this.updateTerrorLatch(player);
                }
            }
        } else {
            super.rideTick();
        }
    }

    public void updateTerrorLatch(Entity vehicle) {
        if (vehicle instanceof Player player) {
            int passengerIndex = vehicle.getPassengers().indexOf(this);
            player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, Util.secondsToTicks(60), passengerIndex, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.JUMP, Util.secondsToTicks(60), passengerIndex, false, false));
            if (passengerIndex > 0)
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, Util.secondsToTicks(60), Mth.clamp(passengerIndex, 0, 1), false, false));
            Vec3 vehiclePosition = player.position();
            double offsetX, offsetY, offsetZ;
            if (passengerIndex == 0) {
                float radius = -0.2F;
                offsetX = (radius * -Math.sin(((Player) player).yBodyRot * Math.PI / 180));
                offsetZ = (radius * Math.cos(((Player) player).yBodyRot * Math.PI / 180));
                offsetY = 1.7D;
                this.setPos(vehiclePosition.x + offsetX, vehiclePosition.y + offsetY, vehiclePosition.z + offsetZ);
            } else if (passengerIndex == 1) {
                float radius = 0.4F;
                float angle = (float) (Math.PI / 180) * ((Player) vehicle).yBodyRot - 90;
                offsetX = radius * Math.sin(Math.PI + angle);
                offsetZ = radius * Math.cos(angle);
                offsetY = 1.2D;
                this.setPos(vehiclePosition.x + offsetX, vehiclePosition.y + offsetY, vehiclePosition.z + offsetZ);
            } else if (passengerIndex == 2) {
                float radius = 0.4F;
                float angle = (float) (Math.PI / 180) * ((Player) vehicle).yBodyRot + 90;
                offsetX = radius * Math.sin(Math.PI + angle);
                offsetZ = radius * Math.cos(angle);
                offsetY = 1.2D;
                this.setPos(vehiclePosition.x + offsetX, vehiclePosition.y + offsetY, vehiclePosition.z + offsetZ);
            }

            if (!player.isFallFlying()) {
                player.setYBodyRot(player.getYHeadRot());
            }
            this.setYRot(player.getYRot());
            this.yBodyRot = ((Player) player).yBodyRot;
            this.yHeadRot = this.yBodyRot;

            // try to dismount
            if (player.isShiftKeyDown() && player.getPassengers().iterator().next() == this && player.isOnGround() && player.getVehicle() == null || this.isDeadOrDying() || this.isRemoved() || player.isDeadOrDying() || player.isRemoved() || player.isUnderWater() || player.isVisuallySwimming() || player.isVisuallyCrawling()) {
                this.setLastMountedPlayerUUID(null);
                player.ejectPassengers();
                player.removeEffect(MobEffects.SLOW_FALLING);
                player.removeEffect(MobEffects.JUMP);
                player.removeEffect(MobEffects.MOVEMENT_SPEED);
                ControlNetwork.INSTANCE.sendToServer(new DragonRideMessage(this.getId(), false));
            }

            // if elytra flying remove potion effects
            if (player.isFallFlying()) {
                player.removeEffect(MobEffects.SLOW_FALLING);
                player.removeEffect(MobEffects.JUMP);
                player.removeEffect(MobEffects.MOVEMENT_SPEED);
            }

//            // remove movement speed if on ground
            // disabled fish lens eyes effects are horrible
//            if (isPlayerOnGround(player))
//                player.removeEffect(MobEffects.MOVEMENT_SPEED);
        }
    }

    /**
     * Check if the ground 4 blocks below is a solid. Replacement for Vanilla onGround
     *
     * @return solidBlockState
     */
    public boolean isPlayerOnGround(Player player) {
        // fix height
        for (int i = 0; i < 1; ++i) {
            BlockPos solidPos = new BlockPos(player.position().x, player.position().y - i, player.position().z);
            if (!level.getBlockState(solidPos).isAir())
                return true;
        }
        return false;
    }

    @Override
    protected boolean canUseBreathNormally() {
        return false;
    }

    @Override
    public Vec3 getThroatPos(ADragonBase entity) {
        Vec3 bodyOrigin = position();

        double x = -Math.sin(this.yBodyRot * Math.PI / 180) * 0.5D;
        double y = 0.5D;
        double z = Math.cos(this.yBodyRot * Math.PI / 180) * 0.5D;
        Vec3 throatPos = bodyOrigin.add(new Vec3(x, y, z));
        return throatPos;
    }

    public Vec3 getTerror0ThroatPosViaPlayer(Player entity) {
        Vec3 vehiclePosition = entity.position();
        double offsetX, offsetY, offsetZ;
        float radius = 0.4F;
        offsetX = (radius * -Math.sin(((Player) entity).yBodyRot * Math.PI / 180));
        offsetZ = (radius * Math.cos(((Player) entity).yBodyRot * Math.PI / 180));
        offsetY = 2.2D;
        Vec3 throatPos = new Vec3(vehiclePosition.x + offsetX, vehiclePosition.y + offsetY, vehiclePosition.z + offsetZ);
        return throatPos;
    }

    public Vec3 getTerror1ThroatPosViaPlayer(Player entity) {
        Vec3 vehiclePosition = entity.position();
        double offsetX, offsetY, offsetZ;
        float radius = 0.4F;
        float angle = (float) (Math.PI / 180) * ((Player) entity).yBodyRot - 95;
        offsetX = (radius * Math.sin(Math.PI + angle));
        offsetZ = (radius * Math.cos(angle));
        offsetY = 1.4D;
        Vec3 throatPos = new Vec3(vehiclePosition.x + offsetX, vehiclePosition.y + offsetY, vehiclePosition.z + offsetZ);
        return throatPos;

    }

    public Vec3 getTerror2ThroatPosViaPlayer(Player entity) {
        Vec3 vehiclePosition = entity.position();
        double offsetX, offsetY, offsetZ;
        float radius = 0.4F;
        float angle = (float) (Math.PI / 180) * ((Player) entity).yBodyRot + 95;
        offsetX = (radius * Math.sin(Math.PI + angle));
        offsetZ = (radius * Math.cos(angle));
        offsetY = 1.4D;
        Vec3 throatPos = new Vec3(vehiclePosition.x + offsetX, vehiclePosition.y + offsetY, vehiclePosition.z + offsetZ);
        return throatPos;

    }

    @Override
    public float getProjectileDamage(ADragonBase dragon, Entity entity, BaseLinearFlightProjectile projectile) {
        return 5;
    }

    @Override
    public int getMaxFuel() {
        return 85;
    }

    @Override
    public void tick() {
        super.tick();
        // attempt to remount to player when he/she disconnects
        if (getLastMountedPlayerUUID() != null) {
            Player player = level.getPlayerByUUID(this.getLastMountedPlayerUUID());

            UUID lastMountedPlayerUUID = this.getLastMountedPlayerUUID();
            if (player != null && lastMountedPlayerUUID != null && this.getLastMountedPlayerUUID() == player.getUUID()) {
                this.ridePlayer(player);
            }
        }

        if (getVehicle() != null && getVehicle() instanceof Player vehicle) {
            Vec3 vehicleLook = vehicle.getViewVector(1);
            if (this == vehicle.getPassengers().get(0)) {
                Vec3 throat0 = getTerror0ThroatPosViaPlayer(vehicle);
                if (isUsingAbility() && canUseBreath())
                    firePrimary(vehicleLook, throat0);
            } else if (this == vehicle.getPassengers().get(1)) {
                Vec3 throat1 = getTerror1ThroatPosViaPlayer(vehicle);
                if (isUsingAbility() && canUseBreath())
                    firePrimary(vehicleLook, throat1);
            } else if (this == vehicle.getPassengers().get(2)) {
                Vec3 throat2 = getTerror2ThroatPosViaPlayer(vehicle);
                if (isUsingAbility() && canUseBreath())
                    firePrimary(vehicleLook, throat2);
            }
        }
    }

    @Override
    protected boolean canUseBreath() {
        if(getVehicle() instanceof Player player) {
            if(player.getVehicle() instanceof ADragonBaseFlyingRideableBreathUser || player.getVehicle() instanceof ADragonBaseFlyingRideableProjUser) {
                return false;
            }
        }
        return super.canUseBreath();
    }

    @Override
    public void firePrimary(Vec3 riderLook, Vec3 throat) {
        FireBreathProjectile fireProj = new FireBreathProjectile(this, throat, riderLook, level);
        fireProj.setProjectileSize(0);
        fireProj.shootNoScaling(riderLook, 1F, 7F);
        level.addFreshEntity(fireProj);
    }

    @org.jetbrains.annotations.Nullable
    public ADragonEggBase getBreedEggResult(ServerLevel level, @NotNull AgeableMob parent) {
        TerribleTerrorEgg dragon = ModEntities.TERRIBLE_TERROR_EGG.get().create(level);
        return dragon;
    }


    @Override
    public boolean canPickUpLoot() {
        return super.canPickUpLoot();
    }

    protected static boolean isBrightEnoughToSpawn(BlockAndTintGetter p_186210_, BlockPos p_186211_) {
        return p_186210_.getRawBrightness(p_186211_, 0) > 8;
    }

    public boolean canHoldItem(ItemStack pStack) {
        Item item = pStack.getItem();
        ItemStack itemstack = this.getItemBySlot(EquipmentSlot.MAINHAND);
        return itemstack.isEmpty() || this.ticksSinceEaten > 0 && item.isEdible() && !itemstack.getItem().isEdible();
    }

    private void spitOutItem(ItemStack pStack) {
        if (!pStack.isEmpty() && !this.level.isClientSide) {
            ItemEntity itementity = new ItemEntity(this.level, this.getX() + this.getLookAngle().x, this.getY() + 1.0D, this.getZ() + this.getLookAngle().z, pStack);
            itementity.setPickUpDelay(40);
            itementity.setThrower(this.getUUID());
            this.playSound(SoundEvents.FOX_SPIT, 1.0F, 1.0F);
            this.level.addFreshEntity(itementity);
        }
    }

    private void dropItemStack(ItemStack pStack) {
        ItemEntity itementity = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), pStack);
        this.level.addFreshEntity(itementity);
    }

    @Override
    protected void pickUpItem(ItemEntity pItemEntity) {
        ItemStack itemstack = pItemEntity.getItem();
        if (this.canHoldItem(itemstack) && isFoodEdibleToDragon(itemstack)) {
            int i = itemstack.getCount();
            if (i > 1) {
                this.dropItemStack(itemstack.split(i - 1));
            }

            this.spitOutItem(this.getItemBySlot(EquipmentSlot.MAINHAND));
            this.onItemPickup(pItemEntity);
            this.setItemSlot(EquipmentSlot.MAINHAND, itemstack.split(1));
            this.handDropChances[EquipmentSlot.MAINHAND.getIndex()] = 2.0F;
            this.take(pItemEntity, itemstack.getCount());
            pItemEntity.discard();
            this.ticksSinceEaten = 0;
        }
    }


    private boolean canEat(ItemStack pItemStack) {
        return pItemStack.getItem().isEdible() && this.getTarget() == null && this.onGround && !this.isSleeping();
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level.isClientSide && this.isAlive() && this.isEffectiveAi()) {
            ++this.ticksSinceEaten;
            ItemStack itemstack = this.getItemBySlot(EquipmentSlot.MAINHAND);
            if (this.canEat(itemstack)) {
                if (this.ticksSinceEaten > 600) {
                    ItemStack itemstack1 = itemstack.finishUsingItem(this.level, this);
                    if (isBreedingFood(itemstack1)) {
                        this.setInLove(null);
                    }
                    if (!itemstack1.isEmpty()) {
                        this.setItemSlot(EquipmentSlot.MAINHAND, itemstack1);
                    }

                    Player player = level.getNearestPlayer(this, 8);
                    if (!isTame()) {
                        if (player != null) {
                            if (isItemStackForTaming(itemstack) && random.nextInt(5) == 1) {
                                this.tameWithName(player);
                                this.level.broadcastEntityEvent(this, (byte) 7);
                            } else {
                                this.level.broadcastEntityEvent(this, (byte) 6);
                            }
                        }
                    }
                    this.ticksSinceEaten = 0;
                } else if (this.ticksSinceEaten > 599 && this.random.nextFloat() < 0.1F) {
                    this.playSound(SoundEvents.GENERIC_BURN, 1.5F, 1.0F);
                    this.level.broadcastEntityEvent(this, (byte) 45);
                }
            }
        }
    }

    @Override
    public boolean guiLocked() {
        return true;
    }

    @javax.annotation.Nullable
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    public void setPersistentAngerTarget(@javax.annotation.Nullable UUID pTarget) {
        this.persistentAngerTarget = pTarget;
    }

    public int getRemainingPersistentAngerTime() {
        return this.entityData.get(DATA_REMAINING_ANGER_TIME);
    }

    public void setRemainingPersistentAngerTime(int pTime) {
        this.entityData.set(DATA_REMAINING_ANGER_TIME, pTime);
    }

    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    public static class TerrorFeedOnGroundGoal extends Goal {

        TerribleTerror dragonBase;
        Level level;


        public TerrorFeedOnGroundGoal(TerribleTerror dragonBase) {
            this.dragonBase = dragonBase;
            this.level = dragonBase.level;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if (!dragonBase.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
                return false;
            } else if (dragonBase.getTarget() == null && dragonBase.getLastHurtByMob() == null) {
                if (dragonBase.shouldStopMoving()) {
                    return false;
                } else {
                    // set the y value to 4 blocks
                    List<ItemEntity> list = dragonBase.level.getEntitiesOfClass(ItemEntity.class, dragonBase.getBoundingBox().inflate(8.0D, 4.0D, 8.0D), TerribleTerror.ALLOWED_ITEMS);
                    if (list != null) {
                        for (ItemEntity itemEntity : list) {
                            if (dragonBase.hasLineOfSight(itemEntity) && dragonBase.canHoldItem(itemEntity.getItem()))
                                return !list.isEmpty() && dragonBase.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty();
                        }
                    }
                }
            } else {
                return true;
            }

            ItemStack itemstack = dragonBase.getItemBySlot(EquipmentSlot.MAINHAND);
            return itemstack.isEmpty() || dragonBase.ticksSinceEaten > 0;

        }

        @Override
        public void start() {
            // set the y value to 4 blocks
            List<ItemEntity> itemEntityList = level.getEntitiesOfClass(ItemEntity.class, dragonBase.getBoundingBox().inflate(8.0D, 4.0D, 8.0D), TerribleTerror.ALLOWED_ITEMS);
            ItemStack itemStack = dragonBase.getItemBySlot(EquipmentSlot.MAINHAND);
            if (!itemEntityList.isEmpty() && itemStack.isEmpty()) {
                ItemEntity itemEntity = itemEntityList.iterator().next();
                if (dragonBase.isFoodEdibleToDragon(itemEntity.getItem())) {
                    dragonBase.getNavigation().moveTo(itemEntity, 1.2F);
                }
            }
        }

        @Override
        public void tick() {
            // set the y value to 4 blockss
            List<ItemEntity> itemEntityList = level.getEntitiesOfClass(ItemEntity.class, dragonBase.getBoundingBox().inflate(8.0D, 4.0D, 8.0D), TerribleTerror.ALLOWED_ITEMS);
            ItemStack itemStack = dragonBase.getItemBySlot(EquipmentSlot.MAINHAND);
            if (!itemEntityList.isEmpty() && itemStack.isEmpty()) {
                ItemEntity itemEntity = itemEntityList.iterator().next();
                if (dragonBase.getSensing().hasLineOfSight(itemEntity)) {
                    if (dragonBase.isFoodEdibleToDragon(itemEntity.getItem())) {
                        dragonBase.getNavigation().moveTo(itemEntity, 1.2F);
                    }
                }
            }
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }
    }

    public class ResetUniversalAngerTargetGoal<T extends Mob & NeutralMob> extends Goal {
        private static final int ALERT_RANGE_Y = 10;
        private final T mob;
        private final boolean alertOthersOfSameType;
        private int lastHurtByPlayerTimestamp;

        public ResetUniversalAngerTargetGoal(T pMob, boolean pAlertOthersOfSameType) {
            this.mob = pMob;
            this.alertOthersOfSameType = pAlertOthersOfSameType;
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return this.mob.level.getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER) && this.wasHurtByPlayer();
        }

        @Override
        public boolean canContinueToUse() {
            // distance should stop the terrors from attacking
            return this.mob.getLastHurtByMob() != null && this.mob.distanceToSqr(mob.getLastHurtByMob()) < 12;
        }

        private boolean wasHurtByPlayer() {
            return this.mob.getLastHurtByMob() != null && this.mob.getLastHurtByMob().getType() == EntityType.PLAYER && this.mob.getLastHurtByMobTimestamp() > this.lastHurtByPlayerTimestamp;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            this.lastHurtByPlayerTimestamp = this.mob.getLastHurtByMobTimestamp();
            this.mob.forgetCurrentTargetAndRefreshUniversalAnger();
            if (this.alertOthersOfSameType) {
                this.getNearbyMobsOfSameType().stream().filter((p_26127_) -> {
                    return p_26127_ != this.mob;
                }).map((p_26125_) -> {
                    return (NeutralMob) p_26125_;
                }).forEach(NeutralMob::forgetCurrentTargetAndRefreshUniversalAnger);
            }

            super.start();
        }

        private List<? extends Mob> getNearbyMobsOfSameType() {
            double d0 = this.mob.getAttributeValue(Attributes.FOLLOW_RANGE);
            AABB aabb = AABB.unitCubeFromLowerCorner(this.mob.position()).inflate(d0, 10.0D, d0);
            return this.mob.level.getEntitiesOfClass(this.mob.getClass(), aabb, EntitySelector.NO_SPECTATORS);
        }
    }

}
