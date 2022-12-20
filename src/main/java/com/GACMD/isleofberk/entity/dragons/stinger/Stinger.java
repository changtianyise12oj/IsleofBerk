package com.GACMD.isleofberk.entity.dragons.stinger;

import com.GACMD.isleofberk.entity.AI.taming.T2DragonFeedTamingGoal;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseGroundRideable;
import com.GACMD.isleofberk.entity.eggs.entity.eggs.StingerEgg;
import com.GACMD.isleofberk.entity.eggs.entity.base.ADragonEggBase;
import com.GACMD.isleofberk.util.math.MathX;
import com.GACMD.isleofberk.registery.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddMobPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.Vec3;
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
import java.util.Objects;

public class Stinger extends ADragonBaseGroundRideable implements IAnimatable {
    DragonPart[] subParts;
    DragonPart stingerRamOffset;

    private <E extends IAnimatable> PlayState predicate1(AnimationEvent<E> event) {

        if (isUsingAbility()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("Ram", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pPose, @NotNull EntityDimensions pSize) {
        return pPose == Pose.SLEEPING ? 0.2F : pSize.height * 1.65F;
    }

    private <E extends IAnimatable> PlayState predicate2(AnimationEvent<E> event) {
        if (isDragonOnGround()) {
            if (this.isDragonSitting()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("Sit", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
            if (this.isDragonSleeping()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("Sleep", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
            if (isVehicle()) {
                if (event.isMoving() && !shouldStopMovingIndependently()) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("Run", ILoopType.EDefaultLoopTypes.LOOP));
                    return PlayState.CONTINUE;
                } else if (isUsingAbility() || (event.isMoving() && isUsingAbility())) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("Ram", ILoopType.EDefaultLoopTypes.LOOP));
                    return PlayState.CONTINUE;
                }
            }
            if (event.isMoving() && !shouldStopMovingIndependently()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("Walk", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
            if (isUsingAbility()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("Ram", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("Jump", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("Idle", ILoopType.EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    public Stinger(EntityType<? extends Stinger> entityType, Level level) {
        super(entityType, level);
        this.stingerRamOffset = new DragonPart(this, "stingerRamOffset", 2F, 2F);
        this.subParts = new DragonPart[]{this.stingerRamOffset};
    }

    @Override
    protected Item tameItem() {
        return Items.MUTTON;
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

        if (isUsingAbility()) {
            this.knockBack(this.level.getEntities(this, this.stingerRamOffset.getBoundingBox().inflate(0.3D, 0.3D, 0.3D).move(0.0D, -0.3D, 0.0D), EntitySelector.NO_CREATIVE_OR_SPECTATOR));

            if (!level.isClientSide()) {
                this.hurt(this.level.getEntities(this, this.stingerRamOffset.getBoundingBox().inflate(1.0D), EntitySelector.NO_CREATIVE_OR_SPECTATOR));
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
        if (isUsingAbility()) {
            this.setXRot(0);
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

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<Stinger>(this, "stinger_controller1", 5, this::predicate1));
        data.addAnimationController(new AnimationController<Stinger>(this, "stinger_controller2", 5, this::predicate2));
    }

    //  Attributes
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 75.0D)
                .add(Attributes.ARMOR, 12)
                .add(Attributes.FLYING_SPEED, 0.14D)
                .add(Attributes.MOVEMENT_SPEED, 0.55F)
                .add(Attributes.ATTACK_DAMAGE, 16F)
                .add(Attributes.JUMP_STRENGTH, 4)
                .add(ForgeMod.STEP_HEIGHT_ADDITION.get(), 1F)
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

    public void positionRider(Entity pPassenger) {
        super.positionRider(pPassenger);
        pPassenger.setPos(this.getX(), this.getY() + 2.2, this.getZ());
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

}
