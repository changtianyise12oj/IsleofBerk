package com.GACMD.isleofberk.entity.dragons.stinger;

import com.GACMD.isleofberk.entity.AI.taming.T2DragonFeedTamingGoal;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseGroundRideable;
import com.GACMD.isleofberk.entity.eggs.entity.base.ADragonEggBase;
import com.GACMD.isleofberk.network.ControlNetwork;
import com.GACMD.isleofberk.network.message.MessageStingerMovingForRam;
import com.GACMD.isleofberk.registery.ModEntities;
import com.GACMD.isleofberk.registery.ModSounds;
import com.GACMD.isleofberk.util.math.MathX;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddMobPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.entity.PartEntity;
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

public class Stinger extends ADragonBaseGroundRideable implements IAnimatable {
    DragonPart[] subParts;
    DragonPart stingerRamOffset;


    private static final EntityDataAccessor<Boolean> RAMMING_DAMAGE = SynchedEntityData.defineId(Stinger.class, EntityDataSerializers.BOOLEAN);


    @Override
    protected float getStandingEyeHeight(@NotNull Pose pPose, @NotNull EntityDimensions pSize) {
        return pPose == Pose.SLEEPING ? 0.2F : pSize.height * 1.65F;
    }

    private <E extends IAnimatable> PlayState predicate1(AnimationEvent<E> event) {

        if (isUsingAbility()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("stinger.ram", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    private <E extends IAnimatable> PlayState predicate2(AnimationEvent<E> event) {
        if (isDragonOnGround()) {
            if (this.isDragonSitting() && !isDragonSleeping()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("stinger.sit", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
            if (this.isDragonSleeping()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("stinger.sleep", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
            if (isVehicle()) {
                if (event.isMoving() && !shouldStopMovingIndependently()) {
                    setIsRammingDamageTrue(true);
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("stinger.run", ILoopType.EDefaultLoopTypes.LOOP));
                    return PlayState.CONTINUE;
                }
            }
            if (event.isMoving() && !shouldStopMovingIndependently()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("stinger.walk", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("stinger.jump", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }

        setIsRammingDamageTrue(false);
        event.getController().setAnimation(new AnimationBuilder().addAnimation("stinger.idle", ILoopType.EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState attackController(AnimationEvent<E> event) {
        if (getTicksSinceLastAttack() >= 0 && getTicksSinceLastAttack() < 12) {
            if (getCurrentAttackType() == 0) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("stinger.bite", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
        }
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<Stinger>(this, "stinger_controller1", 5, this::predicate1));
        data.addAnimationController(new AnimationController<Stinger>(this, "stinger_controller3", 0, this::attackController));
        data.addAnimationController(new AnimationController<Stinger>(this, "stinger_controller2", 5, this::predicate2));
    }

    public Stinger(EntityType<? extends Stinger> entityType, Level level) {
        super(entityType, level);
        this.stingerRamOffset = new DragonPart(this, "stingerRamOffset", 1.5F, 1.5F);
        this.subParts = new DragonPart[]{this.stingerRamOffset};
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(RAMMING_DAMAGE, false);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setIsRammingDamageTrue(pCompound.getBoolean("ramming_damage"));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("ramming_damage", this.IsRammingDamageTrue());
    }

    public boolean IsRammingDamageTrue() {
        return this.entityData.get(RAMMING_DAMAGE);
    }

    public void setIsRammingDamageTrue(boolean ram) {
        this.entityData.set(RAMMING_DAMAGE, ram);
    }

    @Override
    protected boolean isItemStackForTaming(ItemStack stack) {
        return stack.is(Items.MUTTON);
    }

    @Override
    public PartEntity<?>[] getParts() {
        return this.subParts;
    }

    @Override
    public void recreateFromPacket(@NotNull ClientboundAddMobPacket mobPacket) {
        super.recreateFromPacket(mobPacket);
        PartEntity<?>[] stingerPart = this.getParts();

        for (int i = 0; i < stingerPart.length; ++i) {
            stingerPart[i].setId(i + mobPacket.getId());
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

        this.tickPart(this.stingerRamOffset, 3 * -sinY * 1, isUsingAbility() ? 0.4D : 2D, 3 * cosY * 1);
        Vec3 vec3 = this.getDeltaMovement();
        boolean isMoving = vec3.x > 0 || vec3.y > 0 || vec3.z > 0;
        if (getControllingPassenger() instanceof Player player) {
            if (isUsingAbility() && IsRammingDamageTrue()) {
                this.knockBack(this.level.getEntities(this, this.stingerRamOffset.getBoundingBox().inflate(0.3D, 0.3D, 0.3D).move(0.0D, -0.3D, 0.0D), EntitySelector.NO_CREATIVE_OR_SPECTATOR));

                if (!level.isClientSide()) {
                    this.hurt(this.level.getEntities(this, this.stingerRamOffset.getBoundingBox().inflate(1.0D), EntitySelector.NO_CREATIVE_OR_SPECTATOR));
                }
            }
        }
    }

    /**
     * Pushes all entities inside the list away from the enderdragon.
     */
    private void knockBack(List<Entity> pEntities) {
        double d0 = (this.stingerRamOffset.getBoundingBox().minX + this.stingerRamOffset.getBoundingBox().maxX) / 2.0D;
        double d1 = (this.stingerRamOffset.getBoundingBox().minZ + this.stingerRamOffset.getBoundingBox().maxZ) / 2.0D;

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
            if (entity instanceof LivingEntity) {
                entity.hurt(DamageSource.mobAttack(this), 8.0F);
                this.doEnchantDamageEffects(this, entity);
            }
        }

    }

    @Override
    public void tick() {
        super.tick();

        if (level.isClientSide()) {
            ControlNetwork.INSTANCE.sendToServer(new MessageStingerMovingForRam(IsRammingDamageTrue(), this.getId()));
        }

        if (isUsingAbility()) {
            this.setXRot(0);
        }
    }

    @Override
    public void travel(@NotNull Vec3 pTravelVector) {
        if (this.isAlive()) {
            if (this.isVehicle() && this.canBeControlledByRider()) {
                LivingEntity pilot = (LivingEntity) this.getControllingPassenger();
                assert pilot != null;
                pilot.setYBodyRot(pilot.getYHeadRot());
                this.setYRot(pilot.getYRot());
                this.yRotO = this.getYRot();
                this.setXRot(pilot.getXRot() * 0.5F);
                this.setRot(this.getYRot(), this.getXRot());
                this.yBodyRot = this.getYRot();
                this.yHeadRot = this.yBodyRot;
                float f = pilot.xxa * 0.5F;
                float f1 = pilot.zza;
                if (f1 <= 0.0F) {
                    f1 *= 0.25F;
                }

                if (this.playerJumpPendingScale > 0.0F && !this.isJumping() && this.isDragonOnGround()) {
                    double d0 = this.getCustomJump() * (double) this.playerJumpPendingScale * (double) this.getBlockJumpFactor();
                    double d1 = d0 + this.getJumpBoostPower();
                    Vec3 vec3 = this.getDeltaMovement();
                    this.setDeltaMovement(vec3.x, d1, vec3.z);
                    this.setIsJumping(true);
                    this.hasImpulse = true;
                    ForgeHooks.onLivingJump(this);
                    if (f1 > 0.0F) {
                        float f2 = Mth.sin(this.getYRot() * 0.017453292F);
                        float f3 = Mth.cos(this.getYRot() * 0.017453292F);
                        this.setDeltaMovement(this.getDeltaMovement().add((-1.5F * f2 * this.playerJumpPendingScale), 0.0D, (1.5F * f3 * this.playerJumpPendingScale)));
                    }
                    this.playerJumpPendingScale = 0.0F;
                }

                this.flyingSpeed = this.getSpeed() * 0.1F;
                if (this.isControlledByLocalInstance()) {
                    this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
                    super.travel(new Vec3(f, pTravelVector.y, f1));
                } else if (pilot instanceof Player) {
                    this.setDeltaMovement(Vec3.ZERO);
                }

                if (this.isDragonOnGround()) {
                    this.playerJumpPendingScale = 0.0F;
                    this.setIsJumping(false);
                }

                this.tryCheckInsideBlocks();
            } else {
                this.flyingSpeed = 0.02F;
                super.travel(pTravelVector);
            }
        }
    }

    @Nullable
    public ADragonEggBase getBreedEggResult(ServerLevel level, @NotNull AgeableMob parent) {
        return ModEntities.STINGER_EGG.get().create(level);
    }

    @Override
    public void resetLove() {
        super.resetLove();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(1, new T2DragonFeedTamingGoal(this, 1));
    }

    //  Attributes
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 140.0D)
                .add(Attributes.ARMOR, 15)
                .add(Attributes.ARMOR_TOUGHNESS, 14)
                .add(Attributes.FLYING_SPEED, 0.14D)
                .add(Attributes.MOVEMENT_SPEED, 0.55F)
                .add(Attributes.ATTACK_DAMAGE, 10F)
                .add(Attributes.JUMP_STRENGTH, 4)
                .add(Attributes.FOLLOW_RANGE, 32F)
                .add(ForgeMod.SWIM_SPEED.get(), 10);
    }

    @Override
    public float getRideCameraDistanceBack() {
        return 7;
    }

    @Override
    public float getRideCameraDistanceFront() {
        return 3;
    }

    // Variant pt2
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        if (pReason == MobSpawnType.SPAWN_EGG) {
            this.setDragonVariant(this.getRandom().nextInt(getMaxAmountOfVariants()));
        } else {
            this.setDragonVariant(getTypeForBiome(pLevel));
        }
        return pSpawnData;
    }

    @Override
    public int getMaxAmountOfVariants() {
        return 2;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob pMob) {
        return null;
    }

    /**
     * @param pLevel - The server world, this can be obtained by checking a world with [!world.isRemote()]
     * @return the Entity variant depending on the Biome
     * @author andrew0030
     */
    private int getTypeForBiome(ServerLevelAccessor pLevel) {
        Holder<Biome> biome = pLevel.getBiome(new BlockPos(this.position()));
        if (biome.is(BiomeTags.HAS_VILLAGE_PLAINS)) {
            return 1;
        } else if (biome.is(BiomeTags.HAS_VILLAGE_SAVANNA) || biome.is(BiomeTags.HAS_VILLAGE_DESERT)) {
            return 0;
        }

        return 1;
    }

    @Override
    public void positionRider(@NotNull Entity pPassenger) {
        super.positionRider(pPassenger);
    }

    /**
     * 0 : none; 1: weapon; 2: armor or weapon 3: always, only be obtained via eggs
     */
    protected int getAggressionType() {
        return 1;
    }

    @Override
    public float getRideCameraDistanceVert() {
        return 0.2F;
    }

    protected SoundEvent getAmbientSound() {
        if (this.isDragonSleeping()) {
            return ModSounds.STINGER_SLEEP.get();
        } else {
            return ModSounds.STINGER_GROWL.get();
        }
    }

    protected SoundEvent getTameSound() {
        return ModSounds.STINGER_TAME.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return ModSounds.STINGER_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return ModSounds.STINGER_DEATH.get();
    }

    @Override
    protected SoundEvent get1stAttackSound() {
        return ModSounds.STINGER_BITE.get();
    }

    @Override
    protected boolean canCarryCargo() {
        return true;
    }

    @Override
    protected double extraRidersXOffset() {
        return 0.4D;
    }

    @Override
    protected double extraRidersYOffset() {
        return 2.2D;
    }

    @Override
    protected double extraRidersZOffset() {
        return 0.8D;
    }

    @Override
    protected double rider1YOffSet() {
        return 2.2D;
    }

    @Override
    protected int getMaxPassengerCapacity() {
        return 3;
    }

}
