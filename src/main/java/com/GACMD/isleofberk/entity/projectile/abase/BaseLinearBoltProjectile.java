package com.GACMD.isleofberk.entity.projectile.abase;

import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideableProjUser;
import com.GACMD.isleofberk.entity.projectile.breath_user.firebreaths.FireBreathProjectile;
import com.GACMD.isleofberk.entity.projectile.proj_user.fire_bolt.FireBolt;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class BaseLinearBoltProjectile extends BaseLinearFlightProjectile {

    public ADragonBaseFlyingRideableProjUser dragon;

    public BaseLinearBoltProjectile(EntityType<? extends AbstractHurtingProjectile> type, Level level) {
        super(type, level);
    }

    public BaseLinearBoltProjectile(EntityType<? extends AbstractHurtingProjectile> type, ADragonBaseFlyingRideableProjUser owner, Vec3 throat, Vec3 end, Level level, int strengthRadius) {
        super(type, owner, throat, end, level, strengthRadius);
        this.dragon = owner;
    }

    /**
     * Similar to setArrowHeading, it's point the throwable entity to a x, y, z direction.
     */
    public void shoot(Vec3 end, float partialTicks) {
        if (partialTicks == 1) {
            if (dragon.tier1()) {
                this.setDamageTier(1);
            } else if (dragon.tier2()) {
                this.setDamageTier(2);
            } else if (dragon.tier3()) {
                this.setDamageTier(3);
            } else if (dragon.tier4()) {
                this.setDamageTier(4);
            }
            super.shoot(end, partialTicks, 5);
        }
    }

    /**
     * Custom Explosion method used for making explosions with DragonSoulFire.
     *
     * @return The Explosion Object
     * @see net.minecraft.world.level.Explosion
     * @see Explosion#explode()
     */
    /**
     * Custom Explosion method used for making explosions with DragonSoulFire.
     *
     * @return The Explosion Object
     * @see Explosion
     * @see Explosion#explode()
     */
    public Explosion explode(ADragonBase pEntity, double pX, double pY, double pZ, float pExplosionRadius, boolean pCausesFire, Explosion.BlockInteraction pMode) {
        return this.explode(pEntity, null, null, pX, pY, pZ, pExplosionRadius, pCausesFire, pMode);
    }


    public Explosion explode(ADragonBase pExploder, @Nullable DamageSource pDamageSource, @Nullable ExplosionDamageCalculator pContext, double pX, double pY, double pZ, float pSize, boolean pCausesFire, Explosion.BlockInteraction pMode) {
        // Fire is always disabled in the Explosion constructor, to avoid the placement of regular fire
        // we have a custom way of placing fire.
        FireBoltExplosion explosion = new FireBoltExplosion(this.level, pExploder, pDamageSource, pContext, pX, pY, pZ, pSize, pCausesFire, pMode);
        if (ForgeEventFactory.onExplosionStart(this.level, explosion)) return explosion;
        explosion.explode();
        explosion.finalizeExplosion(true);
        return explosion;
    }

    private static class FireBoltExplosion extends Explosion {
        public FireBoltExplosion(Level pLevel, @Nullable Entity pSource, @Nullable DamageSource pDamageSource, @Nullable ExplosionDamageCalculator pDamageCalculator,
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
                            double d0 = (double) ((float) j / 15.0F * 2.0F - 1.0F);
                            double d1 = (double) ((float) k / 15.0F * 2.0F - 1.0F);
                            double d2 = (double) ((float) l / 15.0F * 2.0F - 1.0F);
                            double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                            d0 /= d3;
                            d1 /= d3;
                            d2 /= d3;
                            float f = this.radius * (0.7F + this.level.random.nextFloat() * 0.6F);
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
                            if (entity != this.source)
                                entity.hurt(this.getDamageSource(), (float) ((int) ((d10 * d10 + d10) / 2.0D * 7.0D * (double) f2 + 1.0D)));
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
        public void finalizeExplosion(boolean pSpawnParticles) {

            boolean flag = this.blockInteraction != BlockInteraction.NONE;
            if (pSpawnParticles) {
                if (!(this.radius < 2.0F) && flag) {
                    this.level.addParticle(ParticleTypes.EXPLOSION_EMITTER, this.x, this.y, this.z, 1.0D, 0.0D, 0.0D);
                } else {
                    this.level.addParticle(ParticleTypes.EXPLOSION, this.x, this.y, this.z, 1.0D, 0.0D, 0.0D);
                }
            }

            if (flag) {
                ObjectArrayList<Pair<ItemStack, BlockPos>> objectarraylist = new ObjectArrayList<>();
                Collections.shuffle(this.toBlow, this.level.random);

                for (BlockPos blockpos : this.toBlow) {
                    BlockState blockstate = this.level.getBlockState(blockpos);
                    Block block = blockstate.getBlock();
                    if (!blockstate.isAir()) {
                        BlockPos blockpos1 = blockpos.immutable();
                        this.level.getProfiler().push("explosion_blocks");
                        if (blockstate.canDropFromExplosion(this.level, blockpos, this) && this.level instanceof ServerLevel) {
                            BlockEntity blockentity = blockstate.hasBlockEntity() ? this.level.getBlockEntity(blockpos) : null;
                            LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerLevel) this.level)).withRandom(this.level.random).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(blockpos)).withParameter(LootContextParams.TOOL, ItemStack.EMPTY).withOptionalParameter(LootContextParams.BLOCK_ENTITY, blockentity).withOptionalParameter(LootContextParams.THIS_ENTITY, this.source);
                            if (this.blockInteraction == BlockInteraction.DESTROY) {
                                lootcontext$builder.withParameter(LootContextParams.EXPLOSION_RADIUS, this.radius);
                            }

                            blockstate.getDrops(lootcontext$builder).forEach((p_46074_) -> {
                                addBlockDrops(objectarraylist, p_46074_, blockpos1);
                            });
                        }

                        blockstate.onBlockExploded(this.level, blockpos, this);
                        this.level.getProfiler().pop();
                    }
                }

                for (Pair<ItemStack, BlockPos> pair : objectarraylist) {
                    Block.popResource(this.level, pair.getSecond(), pair.getFirst());
                }
            }

            if (this.fire) {
                for (BlockPos blockpos2 : this.toBlow) {
                    if (this.random.nextInt(3) == 0 && this.level.getBlockState(blockpos2).isAir() && this.level.getBlockState(blockpos2.below()).isSolidRender(this.level, blockpos2.below())) {
                        this.level.setBlockAndUpdate(blockpos2, BaseFireBlock.getState(this.level, blockpos2));
                    }
                }
            }

        }
    }
}