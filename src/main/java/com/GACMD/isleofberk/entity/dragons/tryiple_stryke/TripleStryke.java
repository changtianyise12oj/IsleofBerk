package com.GACMD.isleofberk.entity.dragons.tryiple_stryke;

import com.GACMD.isleofberk.config.CommonConfig;
import com.GACMD.isleofberk.entity.AI.target.DragonMeleeAttackGoal;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideableProjUser;
import com.GACMD.isleofberk.entity.dragons.deadlynadder.DeadlyNadder;
import com.GACMD.isleofberk.entity.eggs.entity.base.ADragonEggBase;
import com.GACMD.isleofberk.entity.eggs.entity.eggs.TripleStrykeEgg;
import com.GACMD.isleofberk.entity.projectile.ScalableParticleType;
import com.GACMD.isleofberk.entity.projectile.abase.BaseLinearFlightProjectile;
import com.GACMD.isleofberk.registery.ModEntities;
import com.GACMD.isleofberk.registery.ModSounds;
import com.GACMD.isleofberk.util.Util;
import com.GACMD.isleofberk.util.math.MathX;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddMobPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
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
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class TripleStryke extends ADragonBaseFlyingRideableProjUser {

    AnimationFactory factory = new AnimationFactory(this);
    protected int ticksSinceLastBiteAttack = 0;
    protected int ticksSinceLastClawAttack = 0;
    protected int ticksSinceLastStingAttackAI = 0;
    protected int ticksSinceLastStingAttackPlayer = 0;
    protected static final EntityDataAccessor<Integer> TICKS_SINCE_LAST_STING = SynchedEntityData.defineId(TripleStryke.class, EntityDataSerializers.INT);

    DragonPart[] subParts;
    DragonPart TSStingArea;

    boolean stingAttack;

    private <E extends IAnimatable> PlayState basicMovementController(AnimationEvent<E> event) {
        if ((isFlying() && !event.isMoving())) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("TripleStrykeFlap", ILoopType.EDefaultLoopTypes.LOOP)); // flyup
            setShouldPlayFlapping(true);
            return PlayState.CONTINUE;
        }
        if (isFlying()) {
            if (event.isMoving()) {
                if (getControllingPassenger() instanceof Player) {
                    if (this.getXRot() < 4 || isGoingUp()) {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("TripleStrykeFlap", ILoopType.EDefaultLoopTypes.LOOP)); //flyup
                        setShouldPlayFlapping(true);
                        return PlayState.CONTINUE;
                    }
                    if (this.getXRot() >= 4 && this.getXRot() < 15 && !isGoingUp()) {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("TripleStrykeGlide", ILoopType.EDefaultLoopTypes.LOOP)); // glide
                        setShouldPlayFlapping(false);
                        return PlayState.CONTINUE;
                    }
                    if (this.getXRot() >= 15 && getPassengers().size() < 2 && !isGoingUp()) {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("TripleStrykeDive", ILoopType.EDefaultLoopTypes.LOOP)); // dive
                        setShouldPlayFlapping(false);
                        return PlayState.CONTINUE;
                    }
                } else if (getOwner() instanceof Player player && isDragonFollowing() && player.isFallFlying()) {
                    float dist = distanceTo(player);
                    double ydist = this.getY() - player.getY();
                    if (dist > 4.3F && ydist < 5F) {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("TripleStrykeFlap", ILoopType.EDefaultLoopTypes.LOOP)); //flyup
                        setShouldPlayFlapping(true);
                        return PlayState.CONTINUE;
                    }
                    if (dist < 4.3F && ydist < 5F) {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("TripleStrykeGlide", ILoopType.EDefaultLoopTypes.LOOP)); //flyup
                        setShouldPlayFlapping(false);
                        return PlayState.CONTINUE;
                    }
                    if (ydist > 5F && dist > 7.8F) {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("TripleStrykeDive", ILoopType.EDefaultLoopTypes.LOOP)); // dive
                        setShouldPlayFlapping(false);
                        return PlayState.CONTINUE;
                    }
                } else {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("TripleStrykeFlap", ILoopType.EDefaultLoopTypes.LOOP)); //flyup
                    setShouldPlayFlapping(true);
                    return PlayState.CONTINUE;
                }
            }
        }
        if (event.isMoving() && !shouldStopMovingIndependently()) {
            if (getTarget() != null && !getTarget().isDeadOrDying() && distanceTo(getTarget()) < 14 || isVehicle()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("TripleStrykeRun", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            } else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("TripleStrykeWalk", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
        }

        if (this.isDragonSitting()&&!isDragonSleeping()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("TripleStrykeSit", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        if (this.isDragonSleeping()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("TripleStrykeSleep", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        if (!isVehicle() && isDragonIncapacitated()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("TripleStrykeSurrender", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;

        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("TripleStrykeIdle", ILoopType.EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState attackController(AnimationEvent<E> event) {
        if (ticksSinceLastAttack >= 0 && ticksSinceLastAttack < 12) {
            if (getCurrentAttackType() == 0) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("TripleStrykeClaw", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            } else if (getCurrentAttackType() == 1) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("TripleStrykeBite", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
        }

        if (isMarkFired()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("TripleStrykeBreath"));
            return PlayState.CONTINUE;
        }


        return PlayState.STOP;
    }

    private <E extends IAnimatable> PlayState stingAttackController(AnimationEvent<E> event) {
        if (getAbilityDisturbTicks() > 1 && isDragonOnGround() && getCurrentAttackType() != 2 && ticksSinceLastStingAttackPlayer == 0) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("TripleStrykeStingReady", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        } else if (getCurrentAttackType() == 2 && getTarget() != null && distanceTo(getTarget()) < 10 || ticksSinceLastStingAttackPlayer > 0) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("TripleStrykeSting", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;

    }
    private <E extends IAnimatable> PlayState rotUpController(AnimationEvent<E> event) {
        if(isGoingUp()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("rot0", ILoopType.EDefaultLoopTypes.LOOP)); //flyup
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    // Animation
    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<TripleStryke>(this, "basic_MovementController", 4, this::basicMovementController));
        data.addAnimationController(new AnimationController<TripleStryke>(this, "attack_Controller", 0, this::attackController));
        data.addAnimationController(new AnimationController<TripleStryke>(this, "stingAttackController", 0, this::stingAttackController));
    }

    public TripleStryke(EntityType<? extends TripleStryke> entityType, Level level) {
        super(entityType, level);
        this.TSStingArea = new DragonPart(this, "TSStingArea", 2F, 2F);
        this.subParts = new DragonPart[]{this.TSStingArea};
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

        this.tickPart(this.TSStingArea, 3 * -sinY * 1, 0.4D, 3 * cosY * 1);

        if (isUsingSECONDAbility() && getTicksSinceLastSting() == 0) {
            ticksSinceLastStingAttackPlayer = 46;
        } else {
            if (ticksSinceLastStingAttackPlayer > 0) {
                ticksSinceLastStingAttackPlayer -= 1;
            } else {
                ticksSinceLastStingAttackPlayer = 0;
            }
        }

        if (ticksSinceLastStingAttackPlayer == 40) {
            this.setTicksSinceLastSting(Util.secondsToTicks(2));
            this.knockBack(this.level.getEntities(this, this.TSStingArea.getBoundingBox().inflate(0.4D, 0.4D, 0.4D).move(0.0D, -0.3D, 0.0D), EntitySelector.NO_CREATIVE_OR_SPECTATOR));

            if (!level.isClientSide()) {
                this.hurt(this.level.getEntities(this, this.TSStingArea.getBoundingBox().inflate(1.0D), EntitySelector.NO_CREATIVE_OR_SPECTATOR));
            }
        }

        if(ticksSinceLastStingAttackPlayer == 44) {
            playSound(ModSounds.TRIPLE_STRYKE_STING.get(), 1, 1);
        }
    }

    /**
     * Pushes all entities inside the list away from the enderdragon.
     */
    private void knockBack(List<Entity> pEntities) {
        double d0 = (this.TSStingArea.getBoundingBox().minX + this.TSStingArea.getBoundingBox().maxX) / 2.0D;
        double d1 = (this.TSStingArea.getBoundingBox().minZ + this.TSStingArea.getBoundingBox().maxZ) / 2.0D;

        for (Entity entity : pEntities) {
            if (entity instanceof LivingEntity && entity != this.getPassengers() && (entity.xOld == entity.getX() || entity.zOld == entity.getZ())) {
                double d2 = entity.getX() - d0;
                double d3 = entity.getZ() - d1;
                double d4 = Math.max(d2 * d2 + d3 * d3, 0.1D);
                entity.push(d2 / d4 * 0.50D, (double) 0.2F, d3 / d4 * 2.0D);
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
                livingEntity.hurt(DamageSource.mobAttack(this), 36.0F);
                this.doEnchantDamageEffects(this, livingEntity);
                livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, Util.secondsToTicks(20)));
                livingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, Util.secondsToTicks(4)));
            }
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TICKS_SINCE_LAST_STING, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("ticksSting", getTicksSinceLastSting());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setTicksSinceLastSting(pCompound.getInt("ticksSting"));
    }

    public int getTicksSinceLastSting() {
        return this.entityData.get(TICKS_SINCE_LAST_STING);
    }

    public void setTicksSinceLastSting(int pType) {
        this.entityData.set(TICKS_SINCE_LAST_STING, pType);
    }


    @Override
    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
        return pPose == Pose.SLEEPING ? 0.2F : pSize.height * 1.2F;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 100.0D)
                .add(Attributes.ARMOR, 4)
                .add(Attributes.MOVEMENT_SPEED, 0.4F)
                .add(Attributes.FLYING_SPEED, 0.10F)
                .add(Attributes.ATTACK_DAMAGE, 12F)
                .add(ForgeMod.STEP_HEIGHT_ADDITION.get(), 1F)
                .add(ForgeMod.SWIM_SPEED.get(), 0.55F);
    }

    /**
     * Override method to reduce stinger damage on armored players so taming won't be as hard
     *
     * @param pEntity
     * @return
     */
    public boolean doHurtTarget(Entity pEntity) {
        float f = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float f1 = (float) this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
        if (pEntity instanceof LivingEntity) {
            f += EnchantmentHelper.getDamageBonus(this.getMainHandItem(), ((LivingEntity) pEntity).getMobType());
            f1 += (float) EnchantmentHelper.getKnockbackBonus(this);
        }

        int i = EnchantmentHelper.getFireAspect(this);
        if (i > 0) {
            pEntity.setSecondsOnFire(i * 4);
        }

        boolean flag = pEntity.hurt(DamageSource.mobAttack(this), pEntity instanceof Player player && player.getArmorValue() > 0 ? f / 2 : f);
        if (flag) {
            if (f1 > 0.0F && pEntity instanceof LivingEntity) {
                ((LivingEntity) pEntity).knockback((double) (f1 * 0.5F), (double) Mth.sin(this.getYRot() * ((float) Math.PI / 180F)), (double) (-Mth.cos(this.getYRot() * ((float) Math.PI / 180F))));
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
            }

            if (pEntity instanceof Player) {
                Player player = (Player) pEntity;
                this.maybeDisableShield(player, this.getMainHandItem(), player.isUsingItem() ? player.getUseItem() : ItemStack.EMPTY);
            }

            this.doEnchantDamageEffects(this, pEntity);
            this.setLastHurtMob(pEntity);
        }

        return flag;
    }

    private void maybeDisableShield(Player pPlayer, ItemStack pMobItemStack, ItemStack pPlayerItemStack) {
        if (!pMobItemStack.isEmpty() && !pPlayerItemStack.isEmpty() && pMobItemStack.getItem() instanceof AxeItem && pPlayerItemStack.is(Items.SHIELD)) {
            float f = 0.25F + (float) EnchantmentHelper.getBlockEfficiency(this) * 0.05F;
            if (this.random.nextFloat() < f) {
                pPlayer.getCooldowns().addCooldown(Items.SHIELD, 100);
                this.level.broadcastEntityEvent(pPlayer, (byte) 30);
            }
        }
    }

    @Override
    public DragonMeleeAttackGoal getMeleeAttackGoal() {
        return new TripleStrykeCustomMeleeAttackGoal(this, 1, false);
    }

    @Override
    public float getRideCameraDistanceBack() {
        return CommonConfig.USE_LARGER_SCALING.get() ? 11 : 9;
    }

    @Override
    public float getRideCameraDistanceFront() {
        return 6;
    }

    @Override
    public float getRideCameraDistanceVert() {
        return 1.30F;
    }

    // Variant pt2
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @javax.annotation.Nullable SpawnGroupData pSpawnData, @javax.annotation.Nullable CompoundTag pDataTag) {
        pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        if (random.nextInt(20) == 1) {
            this.setDragonVariant(6);
        }
        else if (random.nextInt(20) == 1) {
            this.setDragonVariant(3);
        } else {
            this.setDragonVariant(this.random.nextInt(getMaxAmountOfVariants()));
        }

        return pSpawnData;
    }

    @Override
    public int getMaxAmountOfVariants() {
        return 6;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    protected int getAggressionType() {
        return 3;
    }

    @Override
    public void swing(InteractionHand pHand) {
        Random random = new Random();
        int random1 = random.nextInt(300);
        if (random1 > 1) {
            ticksSinceLastBiteAttack = Util.secondsToTicks(3);
            ticksSinceLastStingAttackAI = 0;
            ticksSinceLastClawAttack = 0;
        }

        if (random1 > 200) {
            ticksSinceLastStingAttackAI = Util.secondsToTicks(3);
            ticksSinceLastClawAttack = 0;
            ticksSinceLastBiteAttack = 0;
        }

        if (random1 > 250) {
            ticksSinceLastClawAttack = Util.secondsToTicks(3);
            ticksSinceLastStingAttackAI = 0;
            ticksSinceLastBiteAttack = 0;
        }

        super.swing(pHand);
    }

    private void playerStingAttack() {

    }

    @Override
    public void tick() {
        super.tick();
        if (ticksSinceLastBiteAttack >= 0) {
            ticksSinceLastBiteAttack--;
        }

        if (ticksSinceLastClawAttack >= 0) {
            ticksSinceLastClawAttack--;
        }

        if (ticksSinceLastStingAttackAI >= 0) {
            ticksSinceLastStingAttackAI--;
        }

        if (ticksSinceLastStingAttackPlayer > 0) {
            ticksSinceLastStingAttackPlayer--;
        }

        if(ticksSinceLastStingAttackAI == 55) {
            playSound(ModSounds.TRIPLE_STRYKE_STING.get(), 1, 1);
        }

        if (getTicksSinceLastSting() > 0) {
            setTicksSinceLastSting(getTicksSinceLastSting() - 1);
        }

//        if (getOwner() != null && getOwner() instanceof Player player) {
//            player.displayClientMessage(new TextComponent("current Attack type: " + getCurrentAttackType()), false);
//        }

        // 0 claw attack
        // 1 bite attack
        // 2 sting attack
        if (ticksSinceLastClawAttack >= 0) {
            setCurrentAttackType(0);
        } else if (ticksSinceLastBiteAttack >= 0) {
            setCurrentAttackType(1);
        } else if (ticksSinceLastStingAttackAI >= 0) {
            setCurrentAttackType(2);
        }
        if (hasEffect(MobEffects.POISON)) {
            removeEffect(MobEffects.POISON);
        }

        if (this.tier1()) {
            setProjsSize(0);
            setExplosionStrength(0);
        } else if (this.tier2()) {
            setProjsSize(1);
            setExplosionStrength(1);
        } else if (this.tier3()) {
            setProjsSize(2);
            setExplosionStrength(2);
        } else if (this.tier4()) {
            setProjsSize(3);
            setExplosionStrength(3);
        }

        performInCapacitate();

        // TODO: redo triple stryke attacks, use hitbox in the front of the dragon for it's sting and poison damage,
        //  the sting should instead shoot projectiles
        //  use triple stryke sting ready animation when on ground
        //  custom nadder arrows,
    }

    /**
     * Add bonus damage to boss mobs with high health
     * damage is set by the dragon itself, some dragons have lesser damage
     *
     * @param dragon
     * @param entity
     * @return
     */
    public float getProjectileDamage(ADragonBase dragon, Entity entity, BaseLinearFlightProjectile projectile) {
        if (projectile.getDamageTier() == 1) {
            return 20F + (entity instanceof LivingEntity livingEntity ? (float) Math.floor(livingEntity.getMaxHealth() * 0.05F) : 0F);
        } else if (projectile.getDamageTier() == 2) {
            return 24F + (entity instanceof LivingEntity livingEntity ? (float) Math.floor(livingEntity.getMaxHealth() * 0.10F) : 0F);
        } else if (projectile.getDamageTier() == 3) {
            return 28F + (entity instanceof LivingEntity livingEntity ? (float) Math.floor(livingEntity.getMaxHealth() * 0.20F) : 0F);
        } else if (projectile.getDamageTier() == 4) {
            return 35F + (entity instanceof LivingEntity livingEntity ? (float) Math.floor(livingEntity.getMaxHealth() * 0.30F) : 0F);
        }

        return 20;
    }

    public boolean tier1() {
        return getPlayerBoltBlastPendingScale() >= getMaxPlayerBoltBlast() * 0.27 && getPlayerBoltBlastPendingScale() < getMaxPlayerBoltBlast() * 0.65;
    }

    public boolean tier2() {
        return getPlayerBoltBlastPendingScale() >= getMaxPlayerBoltBlast() * 0.65 && getPlayerBoltBlastPendingScale() < getMaxPlayerBoltBlast() * 0.85;
    }

    public boolean tier3() {
        return getPlayerBoltBlastPendingScale() >= getMaxPlayerBoltBlast() * 0.85 && getPlayerBoltBlastPendingScale() < getMaxPlayerBoltBlast();
    }

    public boolean tier4() {
        return getPlayerBoltBlastPendingScale() >= getMaxPlayerBoltBlast();
    }

    @Override
    protected boolean isItemStackForTaming(ItemStack stack) {
        return super.isItemStackForTaming(stack);
    }

    public static class TripleStrykeCustomMeleeAttackGoal extends DragonMeleeAttackGoal {

        TripleStryke dragon;

        public TripleStrykeCustomMeleeAttackGoal(TripleStryke pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
            super(pMob, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
            this.dragon = pMob;
        }

        @Override
        protected void checkAndPerformAttack(LivingEntity pEnemy, double pDistToEnemySqr) {
            super.checkAndPerformAttack(pEnemy, pDistToEnemySqr);
            if (this.dragon.getCurrentAttackType() == 2 && dragon.getTarget() != null && dragon.getTarget().isOnGround() && dragon.distanceTo(dragon.getTarget()) < 5) {
                playParticles();

                AreaEffectCloud areaEffectCloud = new AreaEffectCloud(dragon.level, pEnemy.getX(), pEnemy.getY(), pEnemy.getZ());
                dragon.level.addFreshEntity(areaEffectCloud);

                if (dragon != null) {
                    areaEffectCloud.setOwner(dragon);
                }

                areaEffectCloud.setPotion(Potions.POISON);
                areaEffectCloud.addEffect(new MobEffectInstance(MobEffects.POISON, Util.secondsToTicks(4)));
                areaEffectCloud.setRadius(1.5F);
                areaEffectCloud.setRadiusOnUse(-0.5F);
                areaEffectCloud.setWaitTime(10);
                areaEffectCloud.setRadiusPerTick(-areaEffectCloud.getRadius() / (float) areaEffectCloud.getDuration());


            }
        }

        public float getColorR(int color) {
            return ((color >> 16) & 0xFF) / 255f;
        }

        public float getColorG(int color) {
            return ((color >> 8) & 0xFF) / 255f;
        }

        public float getColorB(int color) {
            return (color & 0xFF) / 255f;
        }

        public void playParticles() {
            int colorGreen = 0x4e8b2e;
            for (int i = 0; i < 1; i++) {
                Vec3 vec3 = dragon.getDeltaMovement();
                double deltaX = vec3.x;
                double deltaY = vec3.y;
                double deltaZ = vec3.z;
                double dist = Math.ceil(Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ) * 6);
                for (double j = 0; j < dist; j++) {
                    double coeff = j / dist;

                    if (dragon.level.isClientSide()) {
                        ParticleOptions particleOptions = ParticleTypes.INSTANT_EFFECT;
                        LevelRenderer levelRenderer = Minecraft.getInstance().levelRenderer;
                        ;
                        Particle particle = levelRenderer.addParticleInternal(particleOptions, particleOptions.getType().getOverrideLimiter(),
                                (double) (dragon.xo + deltaX * coeff),
                                (double) (dragon.yo + deltaY * coeff) + 3,
                                (double) (dragon.zo + deltaZ * coeff),
                                0.0525f * (dragon.random.nextFloat() - 0.5f),
                                0.0525f * (dragon.random.nextFloat() - 0.5f),
                                0.0525f * (dragon.random.nextFloat() - 0.5f));
                        if (particle != null) {
                            double d23 = dragon.random.nextDouble() * 4.0D;
                            particle.setColor(getColorR(colorGreen), getColorG(colorGreen), getColorB(colorGreen));
                            particle.setPower((float) d23);
                        }
                    }
                }
            }
        }
    }

    @org.jetbrains.annotations.Nullable
    public ADragonEggBase getBreedEggResult(ServerLevel level, @NotNull AgeableMob parent) {
        TripleStrykeEgg dragon = ModEntities.TRIPLE_STRYKE_EGG.get().create(level);
        return dragon;
    }

    @Override
    protected boolean canCarryCargo() {
        return true;
    }

    protected double rider1XOffSet() {
        return 0;
    }

    protected double rider1YOffSet() {
        return CommonConfig.USE_LARGER_SCALING.get() ? 1.6D : 1.2D;
    }

    protected double rider1ZOffSet() {
        return 0;
    }

    protected double rider2XOffSet() {
        return 1;
    }

    protected double rider2YOffSet() {
        return CommonConfig.USE_LARGER_SCALING.get() ? 1.6D : 1.2D;
    }

    protected double rider2ZOffSet() {
        return 1;
    }

    /**
     * biggest without looking weird is 1.25F
     *
     * @param scalableParticleType
     */
    public void scaleParticleSize(ScalableParticleType scalableParticleType, BaseLinearFlightProjectile projectile) {
        int scale = 0;
        if (projectile.getDamageTier() == 1) {
            scale += 0.55f;
        } else if (projectile.getDamageTier() == 2) {
            scale += 0.65f;
        } else if (projectile.getDamageTier() == 3) {
            scale += 0.75f;
        } else if (projectile.getDamageTier() == 4) {
            scale += 0.95f;
        }

        scalableParticleType.setScale(scale);
    }

    @Override
    public int getMaxPlayerBoltBlast() {
        return 82;
    }

    @Override
    protected int getInLoveCoolDownInMCDays() {
        return 22;
    }

    protected SoundEvent getAmbientSound() {
        if (this.isDragonSleeping()) {
            return ModSounds.TRIPLE_STRYKE_SLEEP.get();
        }
        else {
            return ModSounds.TRIPLE_STRYKE_GROWL.get();
        }
    }

    protected SoundEvent getTameSound() {
        return ModSounds.TRIPLE_STRYKE_TAME.get();
    }

    protected SoundEvent getProjectileSound() {
        return ModSounds.TRIPLE_STRYKE_FIRE.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return ModSounds.TRIPLE_STRYKE_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return ModSounds.TRIPLE_STRYKE_DEATH.get();
    }
}
