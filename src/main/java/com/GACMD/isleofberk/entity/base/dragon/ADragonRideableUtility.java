package com.GACMD.isleofberk.entity.base.dragon;

import com.GACMD.isleofberk.entity.dragons.speedstinger.SpeedStinger;
import com.GACMD.isleofberk.entity.dragons.terrible_terror.TerribleTerror;
import com.GACMD.isleofberk.gui.DragonContainerMenu;
import com.GACMD.isleofberk.util.Util;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.*;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.extensions.IForgeItem;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.List;
import java.util.function.Predicate;

public class ADragonRideableUtility extends ADragonBase implements ContainerListener, Saddleable, PlayerRideable {
    private static final EntityDataAccessor<Boolean> DATA_ID_CHEST = SynchedEntityData.defineId(ADragonRideableUtility.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_ID_SADDLE = SynchedEntityData.defineId(ADragonRideableUtility.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SEAT_LOCK = SynchedEntityData.defineId(ADragonRideableUtility.class, EntityDataSerializers.BOOLEAN);

    protected static final String DRAGON_NEEDS_SADDLE = "iob.dragonAir.needSaddle";

    private boolean hasChestVarChanged = false;
    public SimpleContainer dragonContainer;
    protected AnimationFactory factory = new AnimationFactory(this);

    protected ADragonRideableUtility(EntityType<? extends ADragonBase> animal, Level world) {
        super(animal, world);
        this.setPersistenceRequired();
        this.maxUpStep = 2.0F;
        createInventory();
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    public void openGUI(Player playerEntity) {
        if (!this.level.isClientSide() && (!this.isVehicle() || playerEntity.getVehicle() != this && this.isTame())) {
            if (playerEntity instanceof ServerPlayer) {
                Component dragonName = this.getName();
                int id = this.getId();
                NetworkHooks.openGui((ServerPlayer) playerEntity, new MenuProvider() {

                    @Override
                    public @NotNull Component getDisplayName() {
                        return dragonName;
                    }

                    @Override
                    public @NotNull AbstractContainerMenu createMenu(int i, @NotNull Inventory playerInventory, @NotNull Player playerEntity) {
                        return new DragonContainerMenu(i, playerInventory, id);
                    }
                }, buf -> buf.writeInt(id));
            }
        }
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    /**
     * For vehicles, the first passenger is generally considered the controller and "drives" the vehicle. For example,
     * Pigs, Horses, and Boats are generally "steered" by the controlling passenger.
     */
    @javax.annotation.Nullable
    public Entity getControllingPassenger() {
        return this.getFirstPassenger();
    }

    @Override
    public boolean canBeControlledByRider() {
        return this.getControllingPassenger() instanceof Player player && isOwnedBy(player);
    }

    /**
     * Separate one is currently used by speed stingers
     * Separated the method for different taming interactions with dragons
     *
     * @param pPlayer
     * @param pHand
     * @param itemstack
     */
    protected void foodTamingInteraction(Player pPlayer, InteractionHand pHand, ItemStack itemstack) {
        if (!itemstack.isEmpty()) {
            IForgeItem forgeItem = itemstack.getItem();
//            int nutrition = Objects.requireNonNull(forgeItem.getFoodProperties(itemstack, this)).getNutrition();
            int nutrition = 6;
            // phase 1 progress limits the player's phase one progress. Dragons don't eat when they are full.
            // thus preventing the quick tame of dragon's
            // dragon that is subject for taming will not fly away
            // taming is reserved for rideable dragons. meanwhile some taming like speed stinger and terrible terror are tamed uniquely
            if (!isTame() && canBeMounted()) {
                if (isItemStackForTaming(itemstack) && getTarget() == null) {
                    if (this.getFoodTameLimiterBar() < this.getFoodTamingPhaseMaximumLevel()) {
                        // food limits how much you can feed currently fills up faster
                        this.modifyFoodTamingLimiterBar(nutrition * 3);
                        // phase 1 max(100), if full, allows you to ride til tamed, grows slower
                        this.modifyPhase1Progress(getDragonProgressSpeed());
                        this.level.playLocalSound(getX(), getY(), getZ(), SoundEvents.DONKEY_EAT, SoundSource.NEUTRAL, 1, getSoundPitch(), true);
                        addParticlesAroundSelf(new ItemParticleOption(ParticleTypes.ITEM, itemstack));
                        if (!pPlayer.getAbilities().instabuild && !pPlayer.getLevel().isClientSide()) {
                            itemstack.shrink(1);
                        }


                    }
                }
            } else {
                // only tamed units can heal when fed, they might accidentally heal to full strength an incapacitated triple stryke
                if (getHealth() < getMaxHealth()) {
                    this.heal(nutrition);
                    if (!pPlayer.getAbilities().instabuild && !pPlayer.getLevel().isClientSide()) {
                        itemstack.shrink(1);
                    }
                }
            }

            // tame easily when baby but, regular taming when adult
            if (!isTame()) {
                if (isFoodEdibleToDragon(itemstack)) {
                    this.level.playLocalSound(getX(), getY(), getZ(), SoundEvents.DONKEY_EAT, SoundSource.NEUTRAL, 1, getSoundPitch(), true);

                    if (isBaby() && isItemStackForTaming(itemstack)) {
                        if (this.random.nextInt(7) == 0 && !ForgeEventFactory.onAnimalTame(this, pPlayer)) {
                            this.tame(pPlayer);
                            this.navigation.stop();
                            this.setTarget((LivingEntity) null);
                            this.level.broadcastEntityEvent(this, (byte) 7);
                            if (!pPlayer.getAbilities().instabuild && !pPlayer.getLevel().isClientSide()) {
                                itemstack.shrink(1);
                            }
                        } else {
                            this.level.broadcastEntityEvent(this, (byte) 6);
                            if (!pPlayer.getAbilities().instabuild && !pPlayer.getLevel().isClientSide()) {
                                itemstack.shrink(1);
                            }
                        }
                    }
                }
            }

            if (isPhaseTwo() && !isTame()) {
                // add regen particles similar to villagers to tell the player visually that hey I'm ready for phase 2 taming
                // also helps for taming dragons that are incapacitated (Tier 3)
                addEffect(new MobEffectInstance(MobEffects.REGENERATION, Util.secondsToTicks(120)));
            }

            // add smoke particles to dragons that are full
            if (isTamingPhaseBarFull() && !isTame()) {
                addSmokeParticles();
            }
        }

    }

    @Override
    public @NotNull InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        Item item = itemstack.getItem();
        boolean ownedByPlayer = this.isOwnedBy(pPlayer);

        setSleepDisturbTicks(Util.secondsToTicks(38));


        if (isBaby()) {
            if (itemstack.isEmpty()) {
                String owned = "iob.baby.owned";
                String not_owned = "iob.baby.not_owned";
                if (isOwnedBy(pPlayer)) {
                    pPlayer.displayClientMessage(new TranslatableComponent(owned, isOwnedBy(pPlayer)), true);
                } else {
                    pPlayer.displayClientMessage(new TranslatableComponent(not_owned, !isOwnedBy(pPlayer)), true);
                }
            }
        }



        if (!level.isClientSide()) {

            if (this.isFoodEdibleToDragon(itemstack) && canEatWithFoodOnHand(true)) {
                foodTamingInteraction(pPlayer, pHand, itemstack);
                this.gameEvent(GameEvent.MOB_INTERACT, this.eyeBlockPosition());
                return InteractionResult.SUCCESS;
            }

            if (pPlayer.isCrouching() && ownedByPlayer && !guiLocked() && !isCommandItems(itemstack)) {
                this.openGUI(pPlayer);
                return InteractionResult.SUCCESS;
            }

            if (this.isTame() && isCommandItems(itemstack)) {
                if (isOwnedBy(pPlayer)) {
                    if (pPlayer.isShiftKeyDown()) {
                        String seatLockOn = "iob.command.seatLock.on";
                        String seatLockOff = "iob.command.seatLock.off";
                        setSeatLocked(!isSeatLocked());
                        if (isSeatLocked()) {
                            pPlayer.displayClientMessage(new TranslatableComponent(seatLockOn, isSeatLocked()), true);
                        } else {
                            pPlayer.displayClientMessage(new TranslatableComponent(seatLockOff, !isSeatLocked()), true);
                        }
                        return InteractionResult.SUCCESS;
                    } else {
                        modifyCommand(2, pPlayer);
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        }

        if (isCommandItems(itemstack)) {
            int i = getPhase1Progress();
            String s = Integer.toString(i);

            if (canBeMounted())
                pPlayer.displayClientMessage(new TranslatableComponent("taming.phase1.25").append(s).append("%"), true);

            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }

        if (!isBreedingFood(itemstack) && !isFoodEdibleToDragon(itemstack)) {
            rideInteract(pPlayer, pHand, itemstack);
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }


        return super.mobInteract(pPlayer, pHand);

    }

    protected void rideInteract(Player pPlayer, InteractionHand pHand, ItemStack itemstack) {
        // can be ridden when phase 2 is reached and is not pissed
        // first passenger seat(controller) is reserved to owner
        if (!isTame() && canBeTameRidden(pPlayer)) {
            this.doPlayerRide(pPlayer);
            if (pPlayer.isCreative())
                this.tameWithName(pPlayer);
        } else if (isTame()) {
            // if tamed and owned by the original owner player, ride normally
            if (pPlayer == this.getOwner()) {
                this.doPlayerRide(pPlayer);

                // non owner players can ride on the rear this dragon if owner is riding it aswell
                // make sure only owners ride the first passenger slot (index 0 which is the pilot)
                // seatLock is disabled using sit
            } else if (pPlayer != getOwner() && !isSeatLocked() && getControllingPassenger() == this.getOwner()) {
                this.doPlayerRide(pPlayer);

            }
        }
    }

    protected void addSmokeParticles() {
        addParticlesAroundSelf(ParticleTypes.SMOKE);
    }

    @Override
    public boolean canEatWithFoodOnHand(boolean pIgnoreHunger) {
        if (isTame()) {
            return true;
        } else {
            return pIgnoreHunger || (!isTamingPhaseBarFull());
        }
    }

    /**
     * higher numbers means higher increase to reach 100, 100 phase progress means phase 2
     * Each dragon should have unique speed on how fast it could be tamed
     *
     * @return
     */
    protected int getDragonProgressSpeed() {
        return 4;
    }

/*    @Override
    public boolean tameWithName(Player pPlayer) {
        if (canBeMounted()) {
            pPlayer.displayClientMessage(new TranslatableComponent(DRAGON_NEEDS_SADDLE), false);
        }
        return super.tameWithName(pPlayer);
    } */

    /**
     * can dragon be ridden for taming attempt purposes
     *
     * @return
     */
    protected boolean canBeTameRidden(Player player) {
        return isPhaseTwo() && !isDragonIncapacitated() || player.isCreative();
    }

    public boolean canBeMounted() {
        return true;
    }

    protected void doPlayerRide(Player pPlayer) {
        if (canBeMounted()) {
            if (!this.level.isClientSide) {
                if (isTame()) {
                    pPlayer.setYRot(this.getYRot());
                    pPlayer.setXRot(this.getXRot());
                }
                if (!isSaddled() && !pPlayer.isCreative() && isTame()) {
                    pPlayer.displayClientMessage(new TranslatableComponent("iob.dragonAir.needSaddle"), false);
                }
                pPlayer.startRiding(this);
            }

            // go to default when rode on while sitting
            if (isDragonSitting())
                this.setIsDragonWandering(true);
            if (isDragonSleeping())
                setIsDragonSleeping(false);
            if (getTarget() != null)
                setTarget(null);
        }
    }

    @Override
    public void ejectPassengers() {
        // memory leak, dragons won't sleep if enabled, left here as warning and possible and link to bugfix in the future
//        this.setSleepDisturbTicks(Util.secondsToTicks(60));
        super.ejectPassengers();
    }

    @Override
    protected boolean canAddPassenger(Entity pPassenger) {
        if (this.isBaby()) {
            return false;
        } else {
            return this.getPassengers().size() < getMaxPassengerCapacity();
        }
    }

    protected int getMaxPassengerCapacity() {
        return 2;
    }

    @Override
    public void dismountTo(double pX, double pY, double pZ) {
        super.dismountTo(pX, pY, pZ);
    }

    /**
     * Spawns particles for the horse entity. par1 tells whether to spawn hearts. If it is false, it spawns smoke."
     */
    protected void spawnTamingParticles(boolean p_30670_) {
        ParticleOptions particleoptions = p_30670_ ? ParticleTypes.HEART : ParticleTypes.SMOKE;

        for (int i = 0; i < 7; ++i) {
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            this.level.addParticle(particleoptions, this.getRandomX(1.0D), this.getRandomY() + getBbHeight() - getBbHeight() * 0.25, this.getRandomZ(1.0D), d0, d1, d2);
        }
    }

    public void handleEntityEvent(byte pId) {
        if (pId == 7) {
            this.spawnTamingParticles(true);
        } else if (pId == 6) {
            this.spawnTamingParticles(false);
        } else {
            super.handleEntityEvent(pId);
        }

    }

    public boolean hasChest() {
        return entityData.get(DATA_ID_CHEST);
    }

    public void setChest(boolean chested) {
        hasChestVarChanged = true;
        entityData.set(DATA_ID_CHEST, chested);
    }

    public boolean isSeatLocked() {
        return entityData.get(SEAT_LOCK);
    }

    public void setSeatLocked(boolean rear_lock) {
        entityData.set(SEAT_LOCK, rear_lock);
    }

    public boolean hasSaddle() {
        return entityData.get(DATA_ID_SADDLE);
    }

    public void setSaddle(boolean saddle) {
        entityData.set(DATA_ID_SADDLE, saddle);
    }


    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel p_146743_, @NotNull AgeableMob p_146744_) {
        return null;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_CHEST, false);
        this.entityData.define(SEAT_LOCK, true);
        this.entityData.define(DATA_ID_SADDLE, false);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("ChestedDragon", this.hasChest());
        pCompound.putBoolean("seat_lock", this.isSeatLocked());
        pCompound.putBoolean("Saddled", this.hasSaddle());
        if (!this.dragonContainer.getItem(0).isEmpty()) {
            pCompound.put("SaddleItem", this.dragonContainer.getItem(0).save(new CompoundTag()));
        }
        if (!this.dragonContainer.getItem(1).isEmpty()) {
            pCompound.put("ChestItem", this.dragonContainer.getItem(1).save(new CompoundTag()));
        }

        if (hasChest()) {
            ListTag listtag = new ListTag();

            for (int i = 2; i < this.dragonContainer.getContainerSize(); ++i) {
                ItemStack itemstack = this.dragonContainer.getItem(i);
                if (!itemstack.isEmpty()) {
                    CompoundTag compoundtag = new CompoundTag();
                    compoundtag.putByte("Slot", (byte) i);
                    itemstack.save(compoundtag);
                    listtag.add(compoundtag);
                }
            }

            pCompound.put("Items", listtag);
        }

    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setChest(pCompound.getBoolean("ChestedDragon"));
        this.setSeatLocked(pCompound.getBoolean("seat_lock"));
        this.setSaddle(pCompound.getBoolean("Saddled"));
        if (pCompound.contains("SaddleItem", 10)) {
            ItemStack itemstack = ItemStack.of(pCompound.getCompound("SaddleItem"));
            if (itemstack.is(Items.SADDLE)) {
                this.dragonContainer.setItem(0, itemstack);
            }
        }
        if (pCompound.contains("ChestItem", 10)) {
            ItemStack itemstack = ItemStack.of(pCompound.getCompound("ChestItem"));
            if (itemstack.is(Items.CHEST)) {
                this.dragonContainer.setItem(1, itemstack);
            }
        }
        this.createInventory();

        if (this.hasChest()) {
            ListTag listtag = pCompound.getList("Items", 10);

            for (int i = 0; i < listtag.size(); ++i) {
                CompoundTag compoundtag = listtag.getCompound(i);
                int j = compoundtag.getByte("Slot") & 255;
                if (j >= 2 && j < this.dragonContainer.getContainerSize()) {
                    this.dragonContainer.setItem(j, ItemStack.of(compoundtag));
                }
            }
        }

        this.updateContainerEquipment();
    }

    @Override
    protected void dropEquipment() {
        if (this.dragonContainer != null) {
            for(int i = 0; i < this.dragonContainer.getContainerSize(); ++i) {
                ItemStack itemstack = this.dragonContainer.getItem(i);
                if (!itemstack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack)) {
                    this.spawnAtLocation(itemstack);
                }
            }
        }
    }

    protected void createInventory() {
        SimpleContainer simplecontainer = this.dragonContainer;
        this.dragonContainer = new SimpleContainer(this.getInventorySize());
        if (simplecontainer != null) {
            simplecontainer.removeListener(this);
            int i = Math.min(simplecontainer.getContainerSize(), this.dragonContainer.getContainerSize());

            for (int j = 0; j < i; ++j) {
                ItemStack itemstack = simplecontainer.getItem(j);
                if (!itemstack.isEmpty()) {
                    this.dragonContainer.setItem(j, itemstack.copy());
                }
            }
        }

        this.dragonContainer.addListener(this);
        this.updateContainerEquipment();
        this.itemHandler = net.minecraftforge.common.util.LazyOptional.of(() -> new net.minecraftforge.items.wrapper.InvWrapper(this.dragonContainer));
    }

    private SlotAccess createEquipmentSlotAccess(final int p_149503_, final Predicate<ItemStack> p_149504_) {
        return new SlotAccess() {
            public @NotNull ItemStack get() {
                return ADragonRideableUtility.this.dragonContainer.getItem(p_149503_);
            }

            public boolean set(@NotNull ItemStack p_149528_) {
                if (!p_149504_.test(p_149528_)) {
                    return false;
                } else {
                    ADragonRideableUtility.this.dragonContainer.setItem(p_149503_, p_149528_);
                    ADragonRideableUtility.this.updateContainerEquipment();
                    return true;
                }
            }
        };
    }

    @Override
    public @NotNull SlotAccess getSlot(int pSlot) {
        return pSlot == 499 ? new SlotAccess() {
            public @NotNull ItemStack get() {
                return ADragonRideableUtility.this.hasChest() ? new ItemStack(Items.CHEST) : ItemStack.EMPTY;
            }

            public boolean set(@NotNull ItemStack p_149485_) {
                if (p_149485_.isEmpty()) {
                    if (ADragonRideableUtility.this.hasChest()) {
                        ADragonRideableUtility.this.setChest(false);
                        ADragonRideableUtility.this.createInventory();
                    }

                    return true;
                } else if (p_149485_.is(Items.CHEST)) {
                    if (!ADragonRideableUtility.this.hasChest()) {
                        ADragonRideableUtility.this.setChest(true);
                        ADragonRideableUtility.this.createInventory();
                    }

                    return true;
                } else {
                    return false;
                }
            }
        } : this.getBasicSlot(pSlot);
    }

    public SlotAccess getBasicSlot(int pSlot) {
        int i = pSlot - 400;
        if (i >= 0 && i < 2 && i < this.dragonContainer.getContainerSize()) {
            if (i == 0) {
                return this.createEquipmentSlotAccess(i, (pStack) -> {
                    return pStack.isEmpty() || pStack.is(Items.SADDLE);
                });
            }

            if (i == 1) {
                if (!this.canWearArmor()) {
                    return SlotAccess.NULL;
                }

                return this.createEquipmentSlotAccess(i, (pStack) -> {
                    return pStack.isEmpty() || pStack.is(Items.CHEST);
                });
            }
        }

        int j = pSlot - 500 + 2;
        return j >= 2 && j < this.dragonContainer.getContainerSize() ? SlotAccess.forContainer(this.dragonContainer, j) : super.getSlot(pSlot);
    }

    protected void updateContainerEquipment() {
        if (!this.level.isClientSide) {
            this.setSaddle(!this.dragonContainer.getItem(0).isEmpty());
            this.setChest(!this.dragonContainer.getItem(1).isEmpty());
        }
    }

    /**
     * Called by InventoryBasic.onInventoryChanged() on a array that is never filled.
     */
    public void containerChanged(Container pInvBasic) {
        boolean flag = this.isSaddled();
        this.updateContainerEquipment();
        if (this.tickCount > 20 && !flag && this.isSaddled()) {
            this.playSound(SoundEvents.HORSE_SADDLE, 0.5F, 1.0F);
        }
        boolean flag_a = this.hasChest();
        if (this.tickCount > 20 && !flag_a && this.hasChest()) {
            this.playSound(SoundEvents.DONKEY_CHEST, 0.5F, 1.0F);
        }
    }

    @Override
    public boolean isSaddleable() {
        return this.isAlive() && !this.isBaby() && this.isTame();
    }

    public boolean isChestable() {
        return true;
    }

    @Override
    public void equipSaddle(@Nullable SoundSource p_21748_) {
        this.dragonContainer.setItem(0, new ItemStack(Items.SADDLE));

    }

    @Override
    public boolean isSaddled() {
        return hasSaddle();
    }

    private net.minecraftforge.common.util.LazyOptional<?> itemHandler = null;

    @Override
    public <T> net.minecraftforge.common.util.@NotNull LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @javax.annotation.Nullable net.minecraft.core.Direction facing) {
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

    public boolean hasInventoryChanged(Container container) {
        return this.dragonContainer != container;
    }

    public int getInventoryColumns() {
        return 5;
    }

    protected int getInventorySize() {
        return 17;
    }

    int noSaddleRideTicks = 0;

    @Override
    public void tick() {
        if(!this.level.isClientSide()) {

            // dismount players who are not using a saddle
            if (!this.hasSaddle() && this.isVehicle() && this.isTame()) {
                noSaddleRideTicks++;
                if (noSaddleRideTicks > 350) {
                    if(this.getControllingPassenger() instanceof Player player && !player.isCreative())
                        this.ejectPassengers();
                    noSaddleRideTicks = 0;
                }
            }
            // reset ticks when the dragon isnt being ridden
            if(noSaddleRideTicks != 0 && this.getControllingPassenger() == null) {
                noSaddleRideTicks = 0;
            }
        }

        if (canCarryCargo()) {
            if (getControllingPassenger() != null && getControllingPassenger() instanceof Player && !this.isSeatLocked() && isTame()) {
                this.checkInsideBlocks();
                List<Entity> list = this.level.getEntities(this, this.getBoundingBox().inflate((double) 0.2F, (double) -0.01F, (double) 0.2F), EntitySelector.pushableBy(this));
                if (!list.isEmpty()) {
                    boolean flag = !this.level.isClientSide;

                    for (Entity entity : list) {
                        if (!entity.hasPassenger(this)) {
                            if (flag && canAddPassenger(entity) && !entity.isPassenger() && entity.getBbWidth() < this.getBbWidth()) {
                                if (!(entity instanceof ADragonBase) && entity instanceof LivingEntity && !(entity instanceof WaterAnimal) && !(entity instanceof Player) && !(entity instanceof Enemy) && !isSeatLocked()) {
                                    entity.startRiding(this);
                                }

                            }
                            if ((entity instanceof ADragonBase dragonBase)) {
                                if (dragonBase.isBaby() || dragonBase instanceof TerribleTerror || (dragonBase instanceof SpeedStinger) && dragonBase.getOwner() == this.getOwner() && dragonBase.isTame()) {
                                    dragonBase.startRiding(this);
                                }
                            }
                            if ((entity instanceof TamableAnimal tame)) {
                                if (tame.getOwner() == this.getOwner() && isTame()) {
                                    tame.startRiding(this);
                                }
                            } else {
                                this.push(entity);
                            }
                        }
                    }
                }
            }
        }

        if (getPassengers().size() > 0) {
            if (getPassengers().stream().iterator().next() instanceof ADragonBase dragonBase) {
                if (!dragonBase.isBaby()) {
                    ejectPassengers();
                }
            }

            if (getControllingPassenger() != this.getOwner() && isTame()) {
                this.ejectPassengers();
            }
        }

        if (hasChestVarChanged && dragonContainer != null && !this.hasChest()) {
            // 0 slot is for saddle and 1 slot is for chest the rest is for item chests
            for (int i = 2; i < 17; i++) {
                if (!dragonContainer.getItem(i).isEmpty()) {
                    if (!level.isClientSide) {
                        this.spawnAtLocation(dragonContainer.getItem(i), 1);
                    }
                    dragonContainer.removeItemNoUpdate(i);
                }
            }
            hasChestVarChanged = false;
        }

        super.tick();
    }

    @Override
    public void positionRider(@NotNull Entity pPassenger) {
        super.positionRider(pPassenger);
        if (pPassenger == this.getPassengers().get(0)) {
            pPassenger.setPos(this.getX() + rider1XOffSet(), this.getY() + rider1YOffSet(), this.getZ() + rider1ZOffSet());
        }

        if (getPassengers().size() == 2) {
            if (pPassenger == this.getPassengers().get(1)) {
                Vec3 pos = new Vec3(0, extraRidersYOffset(), -extraRidersZOffset());
                pos = pos.yRot((float) Math.toRadians(-getYRot()));
                Vec3 pos1 = pos.add(getX(), getY(), getZ());
                pPassenger.setPos(pos1.x, pos1.y, pos1.z);
                setAnimalRotations(pPassenger);
            }

        } else if (getPassengers().size() == 3) {
            if (pPassenger == this.getPassengers().get(1)) {
                Vec3 pos = new Vec3(extraRidersXOffset(), extraRidersYOffset(), -extraRidersZOffset());
                pos = pos.yRot((float) Math.toRadians(-getYRot()));
                Vec3 pos1 = pos.add(getX(), getY(), getZ());
                pPassenger.setPos(pos1.x, pos1.y, pos1.z);
                setAnimalRotations(pPassenger);
            }


            if (pPassenger == this.getPassengers().get(2)) {
                Vec3 pos = new Vec3(-extraRidersXOffset(), extraRidersYOffset(), -extraRidersZOffset());
                pos = pos.yRot((float) Math.toRadians(-getYRot()));
                Vec3 pos1 = pos.add(getX(), getY(), getZ());
                pPassenger.setPos(pos1.x, pos1.y, pos1.z);
                setAnimalRotations(pPassenger);
            }
        }
    }

    protected void setAnimalRotations(Entity pPassenger) {
        if (pPassenger instanceof Animal animal) {
            this.setYBodyRot(this.getYHeadRot());
            animal.setYRot(this.getYRot());
            animal.setYHeadRot(this.getYRot());
            animal.yBodyRot = animal.getYRot();
            animal.yHeadRot = animal.yBodyRot;
            pPassenger.setYBodyRot(((Animal) pPassenger).yBodyRot + 270);
            pPassenger.setYHeadRot(pPassenger.getYHeadRot() + 270);
        }
    }

    protected double rider1XOffSet() {
        return 0;
    }

    protected double rider1YOffSet() {
        return 2D;
    }

    protected double rider1ZOffSet() {
        return 0;
    }

    protected double extraRidersXOffset() {
        return 0.4D;
    }

    protected double extraRidersYOffset() {
        return 2.2D;
    }

    protected double extraRidersZOffset() {
        return 1F;
    }


    /**
     * Some smaller dragons have their guiLocked.
     *
     * @return
     */
    public boolean guiLocked() {
        return false;
    }

    protected boolean canCarryCargo() {
        return false;
    }

}

