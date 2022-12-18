package com.GACMD.isleofberk.entity.projectile.breath_user.firebreaths;

import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideable;
import com.GACMD.isleofberk.entity.projectile.abase.BaseLinearFlightProjectile;
import com.GACMD.isleofberk.registery.ModEntities;
import com.GACMD.isleofberk.registery.ModParticles;
import com.google.common.collect.Sets;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class FireBreathProjectile extends BaseLinearFlightProjectile {

    public FireBreathProjectile(EntityType<? extends FireBreathProjectile> projectile, Level level) {
        super(projectile, level);
    }

    public FireBreathProjectile(ADragonBaseFlyingRideable dragonOwner, Vec3 throat, Vec3 end, Level level) {
        super(ModEntities.FIRE_PROJ.get(), dragonOwner, throat, end, level, 1);

    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    public void playParticles() {
        if (getProjectileSize() == 0) {
            for (int i = 0; i < 1; i++) {
                Vec3 vec3 = this.getDeltaMovement();
                double deltaX = vec3.x;
                double deltaY = vec3.y;
                double deltaZ = vec3.z;
                double dist = Math.ceil(Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ) * 1);
                for (double j = 0; j < dist; j++) {
                    double coeff = j / dist;
                    ParticleOptions particleOptions = ParticleTypes.SMALL_FLAME;
                    level.addParticle(particleOptions, true,
                            (double) (xo + deltaX * coeff),
                            (double) (yo + deltaY * coeff),
                            (double) (zo + deltaZ * coeff),
                            0.001525f * (random.nextFloat() - 0.3f),
                            0.001525f * (random.nextFloat() - 0.3f),
                            0.001525f * (random.nextFloat() - 0.3f));
                }
            }
        } else if (getProjectileSize() == 1) {
            for (int i = 0; i < 1; i++) {
                Vec3 vec3 = this.getDeltaMovement();
                double deltaX = vec3.x;
                double deltaY = vec3.y;
                double deltaZ = vec3.z;
                double dist = Math.ceil(Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ) * 6);
                for (double j = 0; j < dist; j++) {
                    double coeff = j / dist;
                    ParticleOptions particleOptions = ParticleTypes.FLAME;
                    level.addParticle(particleOptions, true,
                            (double) (xo + deltaX * coeff),
                            (double) (yo + deltaY * coeff) + 1.0F,
                            (double) (zo + deltaZ * coeff),
                            0.1525f * (random.nextFloat() - 0.5f),
                            0.1525f * (random.nextFloat() - 0.5f),
                            0.1525f * (random.nextFloat() - 0.5f));
                }
            }
        } else if (getProjectileSize() == 2) {
            for (int i = 0; i < 1; i++) {
                Vec3 vec3 = this.getDeltaMovement();
                double deltaX = vec3.x;
                double deltaY = vec3.y;
                double deltaZ = vec3.z;
                double dist = Math.ceil(Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ) * 12);
                for (double j = 0; j < dist; j++) {
                    double coeff = j / dist;
                    ParticleOptions particleOptions = ParticleTypes.FLAME;
                    level.addParticle(particleOptions, true,
                            (double) (xo + deltaX * coeff),
                            (double) (yo + deltaY * coeff) + 1.0F,
                            (double) (zo + deltaZ * coeff),
                            0.1525f * (random.nextFloat() - 0.5f),
                            0.1525f * (random.nextFloat() - 0.5f),
                            0.1525f * (random.nextFloat() - 0.5f));
                }
            }
        }
    }

    @Override
    protected boolean canHitEntity(Entity hitEntity) {
        return super.canHitEntity(hitEntity) && hitEntity != dragon;
    }

    @Override
    protected @NotNull ParticleOptions getTrailParticle() {
        return ModParticles.FLAME.get();
    }

    @Override
    protected int threshHoldForDeletion() {
        if (getProjectileSize() == 0) {
            return 10;
        } else {
            return 180;
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
     * @see net.minecraft.world.level.Explosion
     * @see Explosion#explode()
     */
    public Explosion explode(ADragonBase pEntity, double pX, double pY, double pZ, float pExplosionRadius, boolean pCausesFire, Explosion.BlockInteraction pMode) {
        return this.explode(pEntity, null, null, pX, pY, pZ, pExplosionRadius, pCausesFire, pMode);
    }

    private Explosion explode(@Nullable Entity pExploder, @Nullable DamageSource pDamageSource, @Nullable ExplosionDamageCalculator pContext, double pX, double pY, double pZ, float pSize, boolean pCausesFire, Explosion.BlockInteraction pMode) {
        FireBreathProjectile.FlameBreathExplosion explosion = new FireBreathProjectile.FlameBreathExplosion(this.level, pExploder, pDamageSource, pContext, pX, pY, pZ, pSize, true, pMode);
        if (net.minecraftforge.event.ForgeEventFactory.onExplosionStart(this.level, explosion)) return explosion;
        explosion.explode();
        explosion.finalizeExplosion(true);
        return explosion;
    }

    public static class FlameBreathExplosion extends Explosion {

        public FlameBreathExplosion(Level pLevel, @Nullable Entity pSource, @Nullable DamageSource pDamageSource, @Nullable ExplosionDamageCalculator pDamageCalculator,
                                    double pToBlowX, double pToBlowY, double pToBlowZ, float pRadius, boolean pFire, Explosion.BlockInteraction pBlockInteraction) {
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
            net.minecraftforge.event.ForgeEventFactory.onExplosionDetonate(this.level, this, list, f2);
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
            boolean flag = this.blockInteraction != Explosion.BlockInteraction.NONE;
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
                    if (this.random.nextInt(75) == 0 && this.level.getBlockState(blockpos2).isAir() && this.level.getBlockState(blockpos2.below()).isSolidRender(this.level, blockpos2.below())) {
                        this.level.setBlockAndUpdate(blockpos2, BaseFireBlock.getState(this.level, blockpos2));
                    }
                }
            }

        }
    }
}

