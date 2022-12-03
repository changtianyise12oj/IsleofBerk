package com.GACMD.isleofberk.common.entity.entities.projectile.breath_user.poison;

import com.GACMD.isleofberk.common.entity.entities.base.ADragonBase;
import com.GACMD.isleofberk.common.entity.entities.base.ADragonBaseFlyingRideable;
import com.GACMD.isleofberk.common.entity.entities.projectile.abase.BaseLinearFlightProjectile;
import com.GACMD.isleofberk.common.entity.util.Util;
import com.GACMD.isleofberk.registery.ModEntities;
import com.google.common.collect.Sets;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.*;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ZipBreathProjectile extends BaseLinearFlightProjectile {

    ADragonBase dragon;
    public double ticksExisted;
    Vec3 throat;
    Vec3 end;

    public ZipBreathProjectile(EntityType<? extends ZipBreathProjectile> projectile, Level level) {
        super(projectile, level);
    }

    public ZipBreathProjectile(ADragonBaseFlyingRideable dragonOwner, Vec3 throat, Vec3 end, Level level) {
        super(ModEntities.ZIP_POISON.get(), dragonOwner, throat, end, level, 1);
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected boolean shouldBurn() {
        return false;
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
        for (int i = 0; i < 1; i++) {
            Vec3 vec3 = this.getDeltaMovement();
            double deltaX = vec3.x;
            double deltaY = vec3.y;
            double deltaZ = vec3.z;
            double dist = Math.ceil(Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ) * 6);
            for (double j = 0; j < dist; j++) {
                double coeff = j / dist;
                int colorGreen = 0x4e8b2e;

                if (level.isClientSide()) {
                    ParticleOptions particleOptions = ParticleTypes.INSTANT_EFFECT;
                    LevelRenderer levelRenderer = Minecraft.getInstance().levelRenderer;
                    ;
                    Particle particle = levelRenderer.addParticleInternal(particleOptions, true,
                            (double) (xo + deltaX * coeff),
                            (double) (yo + deltaY * coeff) + 0.1,
                            (double) (zo + deltaZ * coeff),
                            0.0525f * (random.nextFloat() - 0.5f),
                            0.0525f * (random.nextFloat() - 0.5f),
                            0.0525f * (random.nextFloat() - 0.5f));
                    if (particle != null) {
                        double d23 = random.nextDouble() * 4.0D;
                        particle.setColor(getColorR(colorGreen), getColorG(colorGreen), getColorB(colorGreen));
//                        particle.setPower((float) d23);
                    }
                }
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        Entity owner = this.getOwner();
        if (this.level.isClientSide || (owner == null || !owner.isRemoved())) {
            HitResult hitresult = ProjectileUtil.getHitResult(this, this::canHitEntity);

            if (hitresult.getType() != HitResult.Type.MISS) end = hitresult.getLocation();

            if (!this.level.isClientSide() && hitresult.getType() != HitResult.Type.MISS && !ForgeEventFactory.onProjectileImpact(this, hitresult)) {

                HitResult.Type hitresult$type = hitresult.getType();
                if (hitresult$type == HitResult.Type.ENTITY && hitresult instanceof EntityHitResult entityHitResult) {
//                    Entity entity = entityHitResult.getEntity();
//                    boolean mobGriefing = ForgeEventFactory.getMobGriefingEvent(this.level, this.getOwner());
//                    if (entity != dragon) {
//                        if (mobGriefing) {
//                            entity.hurt(DamageSource.explosion(this.dragon), 14);
//                            entity.setSecondsOnFire(7);
//                            this.discard();
//                            this.gameEvent(GameEvent.PROJECTILE_LAND, this.getOwner());
//
//                            // add exception if mobGriefing is false,
//                            // allow damage only to tamable mobs that aren't tamed
//                            // and other hostile mobs such as bosses to make dragons useful in combat.
//                            // no damage to pets(tamed mobs and mobs that have name tags)
//                        } else if (!mobGriefing && !entity.hasCustomName()) {
//                            if (entity instanceof TamableAnimal tamableAnimal && !tamableAnimal.isTame()) {
//                                entity.hurt(DamageSource.explosion(this.dragon), 8);
//                                entity.setSecondsOnFire(7);
//                                this.discard();
//                                this.gameEvent(GameEvent.PROJECTILE_LAND, this.getOwner());
//                            }
//                        }
//                    }
                } else if (hitresult$type == HitResult.Type.BLOCK && hitresult instanceof BlockHitResult blockHitResult) {
                    // bypass through foliage blocks. flowers, grass, tall grass
                    if (!(level.getBlockState(blockHitResult.getBlockPos()).getBlock() instanceof BushBlock)) {
                        boolean flag = ForgeEventFactory.getMobGriefingEvent(this.level, this.getOwner());

                        // reduce amount of gas spawned
                        this.makeAreaOfEffectCloud();
                        this.discard();
                        this.gameEvent(GameEvent.PROJECTILE_LAND, this.getOwner());
                    }
                }
            }

            this.checkInsideBlocks();
            Vec3 deltaMovement = this.getDeltaMovement();
            double x = this.getX() + deltaMovement.x;
            double y = this.getY() + deltaMovement.y;
            double z = this.getZ() + deltaMovement.z;
            float inertia = this.getInertia();
            if (this.isInWater()) {
                for (int i = 0; i < 4; ++i) {
                    this.level.addParticle(ParticleTypes.BUBBLE, x - deltaMovement.x * 0.25D, y - deltaMovement.y * 0.25D, z - deltaMovement.z * 0.25D, deltaMovement.x, deltaMovement.y, deltaMovement.z);
                }
                inertia = 0.8F;
            }

            double d3 = x * 1.8;
            double d4 = y * 1.8;
            double d5 = z * 1.8;
            this.setDeltaMovement(deltaMovement.add(this.xPower, this.yPower, this.zPower).scale(inertia));
//            this.level.addParticle(this.getTrailParticle(), d3, d4 + 0.5D, d5, 1.1D, 1.1D, 1.0D);
            this.setPos(x, y, z);
        }

        playParticles();

        // kill entity when ticks exceed 250 to remove lag
        ticksExisted++;
        if (ticksExisted > 75) {
            this.discard();
            ticksExisted = 0;
        }

    }

    @Override
    protected boolean canHitEntity(Entity hitEntity) {
        return super.canHitEntity(hitEntity) && hitEntity != dragon;
    }

    @Override
    protected @NotNull ParticleOptions getTrailParticle() {
        return ParticleTypes.EFFECT;
    }

    private void makeAreaOfEffectCloud() {
        ZippleBackAOECloud areaeffectcloud = new ZippleBackAOECloud(this.level, this.getX(), this.getY(), this.getZ());
        LivingEntity entity = this.getOwner();
        if (entity != null) {
            areaeffectcloud.setOwner(entity);
        }
        areaeffectcloud.setRadius(3.0F);
        areaeffectcloud.setRadiusOnUse(-0.5F);
        areaeffectcloud.setWaitTime(10);
        areaeffectcloud.setRadiusPerTick(-areaeffectcloud.getRadius() / (float) areaeffectcloud.getDuration());
        areaeffectcloud.setPotion(Potions.POISON);
        areaeffectcloud.addEffect(new MobEffectInstance(MobEffects.POISON, Util.secondsToTicks(25)));

        this.level.addFreshEntity(areaeffectcloud);
    }

    /**
     * Similar to setArrowHeading, it's point the throwable entity to a x, y, z direction.
     */
    public void shoot(Vec3 end, float partialTicks) {
        if (partialTicks == 1) {
            Vec3 endVec = (new Vec3(end.x() * 4.8, end.y() * 4.8, end.z() * 4.8));
            this.setDeltaMovement(endVec);
            double d0 = end.horizontalDistance();
            this.setYRot((float) (Mth.atan2(end.x, end.z) * (double) (180F / (float) Math.PI)));
            this.setXRot((float) (Mth.atan2(end.y, d0) * (double) (180F / (float) Math.PI)));
            this.yRotO = this.getYRot();
            this.xRotO = this.getYRot();
        }
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public float getBrightness() {
        return 0;
    }

    /**
     * Custom Explosion method used for making explosions with DragonSoulFire.
     *
     * @return The Explosion Object
     * @see com.GACMD.isleofberk.init.blocks.DragonSoulFire
     * @see Explosion
     * @see Explosion#explode()
     */
    public FlameBreathExplosion explode(@Nullable ADragonBase pEntity, double pX, double pY, double pZ, float pExplosionRadius, boolean pCausesFire, Explosion.BlockInteraction pMode) {
        return this.explode(pEntity, null, null, pX, pY, pZ, pExplosionRadius, pCausesFire, pMode);
    }


    private FlameBreathExplosion explode(@Nullable ADragonBase pExploder, @Nullable DamageSource pDamageSource, @Nullable ExplosionDamageCalculator pContext, double pX, double pY, double pZ, float pSize, boolean pCausesFire, Explosion.BlockInteraction pMode) {
        FlameBreathExplosion explosion = new FlameBreathExplosion(this.level, pExploder, pDamageSource, pContext, pX, pY, pZ, pSize, true, pMode);
        if (ForgeEventFactory.onExplosionStart(this.level, explosion)) return explosion;
        explosion.explode();
        explosion.finalizeExplosion(true);
        return explosion;
    }

    private static class FlameBreathExplosion extends Explosion {

        public FlameBreathExplosion(Level pLevel, @Nullable Entity pSource, @Nullable DamageSource pDamageSource, @Nullable ExplosionDamageCalculator pDamageCalculator,
                                    double pToBlowX, double pToBlowY, double pToBlowZ, float pRadius, boolean pFire, BlockInteraction pBlockInteraction) {
            super(pLevel, pSource, pDamageSource, pDamageCalculator, pToBlowX, pToBlowY, pToBlowZ, pRadius, pFire, pBlockInteraction);
        }


        /**
         * Does the first part of the explosion (destroy blocks)
         */
        public void explode() {
            this.level.gameEvent(this.source, GameEvent.EXPLODE, new BlockPos(this.x, this.y, this.z));
            Set<BlockPos> set = Sets.newHashSet();
            int i = 16;

            for (int j = 0; j < 16; ++j) {
                for (int k = 0; k < 16; ++k) {
                    for (int l = 0; l < 16; ++l) {
                        if (j == 0 || j == 15 || k == 0 || k == 15 || l == 0 || l == 15) {
                            double d0 = ((float) j / 15.0F * 2.0F - 1.0F);
                            double d1 = ((float) k / 15.0F * 2.0F - 1.0F);
                            double d2 = ((float) l / 15.0F * 2.0F - 1.0F);
                            double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                            d0 /= d3;
                            d1 /= d3;
                            d2 /= d3;
                            float f = this.radius * (0.7F + this.level.random.nextFloat() * 0.6F); // weaken fury explosion by / 2
                            double d4 = this.x;
                            double d6 = this.y;
                            double d8 = this.z;

                            for (float f1 = 0.3F; f > 0.0F; f -= 0.22500001F) {
                                BlockPos blockpos = new BlockPos(d4, d6, d8);
                                BlockState blockstate = this.level.getBlockState(blockpos);
                                FluidState fluidstate = this.level.getFluidState(blockpos);
                                if (!this.level.isInWorldBounds(blockpos)) {
                                    break;
                                }

                                Optional<Float> optional = this.damageCalculator.getBlockExplosionResistance(this, this.level, blockpos, blockstate, fluidstate);
                                if (optional.isPresent()) {
                                    f -= (optional.get() + 0.3F) * 0.3F;
                                }

                                if (f > 0.0F && this.damageCalculator.shouldBlockExplode(this, this.level, blockpos, blockstate, f)) {
                                    set.add(blockpos);
                                }

                                d4 += d0 * (double) 0.3F;
                                d6 += d1 * (double) 0.3F;
                                d8 += d2 * (double) 0.3F;
                            }
                        }
                    }
                }
            }

            this.toBlow.addAll(set);
            float f2 = this.radius * 2.0F;
            int k1 = Mth.floor(this.x - (double) f2 - 1.0D);
            int l1 = Mth.floor(this.x + (double) f2 + 1.0D);
            int i2 = Mth.floor(this.y - (double) f2 - 1.0D);
            int i1 = Mth.floor(this.y + (double) f2 + 1.0D);
            int j2 = Mth.floor(this.z - (double) f2 - 1.0D);
            int j1 = Mth.floor(this.z + (double) f2 + 1.0D);
            List<Entity> list = this.level.getEntities(this.source, new AABB((double) k1, (double) i2, (double) j2, (double) l1, (double) i1, (double) j1));
            ForgeEventFactory.onExplosionDetonate(this.level, this, list, f2);
            Vec3 vec3 = new Vec3(this.x, this.y, this.z);

            for (int k2 = 0; k2 < list.size(); ++k2) {
                Entity entity = list.get(k2);
                if (!entity.ignoreExplosion()) {
                    double d12 = Math.sqrt(entity.distanceToSqr(vec3)) / (double) f2;
                    if (d12 <= 1.0D) {
                        double d5 = entity.getX() - this.x;
                        double d7 = (entity instanceof PrimedTnt ? entity.getY() : entity.getEyeY()) - this.y;
                        double d9 = entity.getZ() - this.z;
                        double d13 = Math.sqrt(d5 * d5 + d7 * d7 + d9 * d9);
                        if (d13 != 0.0D) {
                            d5 /= d13;
                            d7 /= d13;
                            d9 /= d13;
                            double d14 = (double) getSeenPercent(vec3, entity);
                            double d10 = (1.0D - d12) * d14;
                            entity.hurt(this.getDamageSource(), (float) ((int) ((d10 * d10 + d10) / 2.0D * 7.0D * (double) f2 + 1.0D)));
//                            entity.hurt(this.getDamageSource(), 22F);
                            double d11 = d10;
                            if (entity instanceof LivingEntity) {
                                d11 = ProtectionEnchantment.getExplosionKnockbackAfterDampener((LivingEntity) entity, d10);
                            }

                            entity.setDeltaMovement(entity.getDeltaMovement().add(d5 * d11, d7 * d11, d9 * d11));
                            if (entity instanceof Player) {
                                Player player = (Player) entity;
                                if (!player.isSpectator() && (!player.isCreative() || !player.getAbilities().flying)) {
                                    this.hitPlayers.put(player, new Vec3(d5 * d10, d7 * d10, d9 * d10));
                                }
                            }
                        }
                    }
                }
            }
        }

        /**
         * Does the second part of the explosion (sound, particles, drop spawn)
         */
        @Override
        public void finalizeExplosion(boolean pSpawnParticles) {
            if (this.level.isClientSide) {
                this.level.playLocalSound(this.x, this.y, this.z, SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 4.0F, (1.0F + (this.level.random.nextFloat() - this.level.random.nextFloat()) * 0.2F) * 0.7F, false);
            }

            boolean flag = this.blockInteraction != BlockInteraction.NONE;
            if (pSpawnParticles) {
                if (!(this.radius < 2.0F) && flag) {
                    this.level.addParticle(ParticleTypes.EXPLOSION_EMITTER, this.x, this.y, this.z, 1.0D, 0.0D, 0.0D);
                } else {
                    this.level.addParticle(ParticleTypes.EXPLOSION, this.x, this.y, this.z, 1.0D, 0.0D, 0.0D);
                }
            }

//            if (flag) {
//                ObjectArrayList<Pair<ItemStack, BlockPos>> objectarraylist = new ObjectArrayList<>();
//                Collections.shuffle(this.toBlow, this.level.random);
//
//                for (BlockPos blockpos : this.toBlow) {
//                    BlockState blockstate = this.level.getBlockState(blockpos);
//                    Block block = blockstate.getBlock();
//                    if (!blockstate.isAir()) {
//                        BlockPos blockpos1 = blockpos.immutable();
//                        this.level.getProfiler().push("explosion_blocks");
//                        if (blockstate.canDropFromExplosion(this.level, blockpos, this) && this.level instanceof ServerLevel) {
//                            BlockEntity blockentity = blockstate.hasBlockEntity() ? this.level.getBlockEntity(blockpos) : null;
//                            LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerLevel) this.level)).
//                                    withRandom(this.level.random).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(blockpos)).
//                                    withParameter(LootContextParams.TOOL, ItemStack.EMPTY).
//                                    withOptionalParameter(LootContextParams.BLOCK_ENTITY, blockentity).
//                                    withOptionalParameter(LootContextParams.THIS_ENTITY, this.source);
//                            if (this.blockInteraction == Explosion.BlockInteraction.DESTROY) {
//                                lootcontext$builder.withParameter(LootContextParams.EXPLOSION_RADIUS, this.radius);
//                            }
//
//                            blockstate.getDrops(lootcontext$builder).forEach((p_46074_) -> {
//                                addBlockDrops(objectarraylist, p_46074_, blockpos1);
//                            });
//                        }
//
//                        blockstate.onBlockExploded(this.level, blockpos, this);
//                        this.level.getProfiler().pop();
//                    }
//                }
//
//                for (Pair<ItemStack, BlockPos> pair : objectarraylist) {
//                    Block.popResource(this.level, pair.getSecond(), pair.getFirst());
//                }
//            }

            if (this.fire) {
                for (BlockPos blockpos2 : this.toBlow) {
                    if (this.random.nextInt(3) == 0 && this.level.getBlockState(blockpos2).isAir() && this.level.getBlockState(blockpos2.below()).isSolidRender(this.level, blockpos2.below())) {
                        this.level.setBlockAndUpdate(blockpos2, BaseFireBlock.getState(this.level, blockpos2));
                    }
                }
            }

        }
    }

    public static class ZipBreathProjectileRenderer extends EntityRenderer<ZipBreathProjectile> {

        public ZipBreathProjectileRenderer(EntityRendererProvider.Context p_174008_) {
            super(p_174008_);
        }

        @Override
        public ResourceLocation getTextureLocation(ZipBreathProjectile pEntity) {
            return null;
        }
    }
}

