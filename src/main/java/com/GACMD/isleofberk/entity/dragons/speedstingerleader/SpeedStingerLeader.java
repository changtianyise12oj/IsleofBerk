package com.GACMD.isleofberk.entity.dragons.speedstingerleader;

import com.GACMD.isleofberk.entity.dragons.speedstinger.SpeedStinger;
import com.GACMD.isleofberk.registery.ModEntities;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraftforge.common.ForgeMod;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class SpeedStingerLeader extends SpeedStinger {

    AnimationFactory factory = new AnimationFactory(this);
    protected final List<SpeedStinger> groupMembers = new ArrayList<>();

    private <E extends IAnimatable> PlayState basicMovementController(AnimationEvent<E> event) {
        if (event.isMoving() && !shouldStopMovingIndependently()) {
            if (getTarget() != null && !getTarget().isDeadOrDying() && distanceTo(getTarget()) < 8) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("SpeedStingerRun",  ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;

            } else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("SpeedStingerWalk",  ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
        }

        if (this.isDragonSitting()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("SpeedStingerSit",  ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        if (this.isDragonSleeping()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("SpeedStingerSleep",  ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        if (this.getLookControl().isLookingAtTarget()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("SpeedStingerCurious",  ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("SpeedStingerIdle",  ILoopType.EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState attackController(AnimationEvent<E> event) {
        if (getTicksSinceLastAttack() >= 0 && getTicksSinceLastAttack() < 12) {
            if (getCurrentAttackType() == 0) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("SpeedStingerBite",  ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            } else if (getCurrentAttackType() == 1) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("SpeedStingerSting",  ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
        }
        if (getTarget() != null) {
            if (!isOnGround() && event.isMoving() && !getTarget().isDeadOrDying()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("SpeedStingerPounce",  ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
        }

        return PlayState.STOP;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<SpeedStingerLeader>(this, "speed_stinger_leader_controller", 5, this::basicMovementController));
        data.addAnimationController(new AnimationController<SpeedStingerLeader>(this, "speed_stinger_leader_controller_attacks", 5, this::attackController));
    }

    @Override
    protected boolean isItemStackForTaming(ItemStack stack) {
        return stack.is(Items.RABBIT);
    }

    public SpeedStingerLeader(EntityType<? extends SpeedStingerLeader> animal, Level world) {
        super(animal, world);
        this.xpReward = 1000;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
    }

    @Override
    protected int getAggressionType() {
        return 3;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @javax.annotation.Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);

        if (pReason == MobSpawnType.SPAWN_EGG) {
            this.setDragonVariant(this.random.nextInt(getMaxAmountOfVariants()));
        } else {
            this.setDragonVariant(getTypeForBiome(pLevel));
        }

        int size = random.nextInt(2) + 3;
        float theta = (2 * (float) Math.PI / size);
        for (int i = 0; i <= 6; i++) {
            SpeedStinger speedStinger = ModEntities.SPEED_STINGER.get().create(this.level);
            assert speedStinger != null;
            speedStinger.moveTo(this.getX() + 0.1 * Math.cos(theta * i), this.getY(), this.getZ() + 0.1 * Math.sin(theta * i));
            speedStinger.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
            speedStinger.setDragonVariant(this.getDragonVariant());
            pLevel.addFreshEntity(speedStinger);
        }
        return pSpawnData;
    }

    //  Attributes
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 120.0D)
                .add(Attributes.ARMOR, 20)
                .add(Attributes.MOVEMENT_SPEED, 0.4F)
                .add(Attributes.ATTACK_DAMAGE, 4.5F)
                .add(Attributes.FOLLOW_RANGE, 7F)
                .add(ForgeMod.STEP_HEIGHT_ADDITION.get(), 1F)
                .add(ForgeMod.SWIM_SPEED.get(), 10);

    }

    @Override
    public void tick() {
        super.tick();
    }

    public void addMember(SpeedStinger speedStinger) {
        if (speedStinger.getLeaderUUID() == this.getUUID()) {
            groupMembers.add(speedStinger);
        }
    }

    @Override
    public void die(DamageSource pCause) {
        this.groupMembers.forEach(SpeedStinger::removeLeader);
        super.die(pCause);
    }

    @Override
    public void remove(Entity.RemovalReason pReason) {
        this.groupMembers.forEach(SpeedStinger::removeLeader);
        super.remove(pReason);
    }

    public List<SpeedStinger> getGroupMembers() {
        return groupMembers;
    }

    public boolean isGroupFull() {
        return getGroupMembers().size() > 15;
    }

    @Override
    protected int getExperienceReward(Player pPlayer) {
        return 1000;
    }

    @Override
    protected boolean isSpeedStingerLeaderClass() {
        return true;
    }

    @Override
    public void ageUp(int p_146759_) {
        super.ageUp(p_146759_);
    }
}