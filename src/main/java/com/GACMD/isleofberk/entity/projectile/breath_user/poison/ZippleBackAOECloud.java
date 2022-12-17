package com.GACMD.isleofberk.entity.projectile.breath_user.poison;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;

public class ZippleBackAOECloud extends AreaEffectCloud {

    private static final EntityDataAccessor<Float> DATA_ID_DAMAGE = SynchedEntityData.defineId(ZippleBackAOECloud.class, EntityDataSerializers.FLOAT);

    public ZippleBackAOECloud(EntityType<? extends AreaEffectCloud> cloud, Level level) {
        super(cloud, level);
    }

    public ZippleBackAOECloud(Level level, double x, double y, double z) {
        this(EntityType.AREA_EFFECT_CLOUD, level);
        this.setPos(x, y, z);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();

        this.entityData.define(DATA_ID_DAMAGE, 0.0F);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setDamage(pCompound.getFloat("damage"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putFloat("damage", this.getDamage());
    }

    @Override
    public void tick() {
        super.tick();

        if (checkInFire() || this.getDamage() > 1) {
            explode();
        }
    }


    /**
     * Check if hot enough to explode, on fire, on lava etc.
     * since minecraft has no temperature use light source
     * @return
     */
    private boolean checkInFire() {
        AABB aabb = this.getBoundingBox();
        float i = Mth.floor(aabb.minX);
        float j = Mth.ceil(aabb.maxX);
        float k = Mth.floor(aabb.minY);
        float l = Mth.ceil(aabb.maxY);
        float i1 = Mth.floor(aabb.minZ);
        float j1 = Mth.ceil(aabb.maxZ);
        boolean flag = false;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for (float k1 = i; k1 < j; ++k1) {
            for (float l1 = k - 1; l1 < l + 1; ++l1) {
                for (float i2 = i1; i2 < j1; ++i2) {
                    blockpos$mutableblockpos.set(k1, l1, i2);
                    BlockState state = level.getBlockState(blockpos$mutableblockpos);
                    if (level.getBlockState(blockpos$mutableblockpos).getBlock() instanceof BaseFireBlock
                            || level.getBlockState(blockpos$mutableblockpos).getBlock() == Blocks.LAVA
                            || level.getBlockState(blockpos$mutableblockpos).getBlock() instanceof CandleBlock
                            || level.getBlockState(blockpos$mutableblockpos).getBlock().getLightEmission(state, level, blockpos$mutableblockpos) > 4) {
                        flag = true;
                    }
                }
            }
        }

        return flag;
    }


    public void explode() {
        boolean flag = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this.getOwner());
        if (flag)
            this.level.explode(null, this.getX(), this.getY(), this.getZ(), this.getRadius() + 1, flag, Explosion.BlockInteraction.NONE);

        this.discard();
    }


    /**
     * Sets the damage taken from the last hit.
     */
    public void setDamage(float pDamageTaken) {
        this.entityData.set(DATA_ID_DAMAGE, pDamageTaken);
    }

    /**
     * Gets the damage taken from the last hit.
     */
    public float getDamage() {
        return this.entityData.get(DATA_ID_DAMAGE);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource pSource) {
        if (pSource == DamageSource.ON_FIRE || pSource == DamageSource.IN_FIRE || pSource == DamageSource.LAVA || pSource == DamageSource.explosion((LivingEntity) null)) {
            return false;
        } else {
            return super.isInvulnerableTo(pSource);
        }
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (this.isInvulnerableTo(pSource)) {
            return false;
        } else if (!this.level.isClientSide && !this.isRemoved()) {
            this.setDamage(this.getDamage() + pAmount * 10.0F);
            this.markHurt();
            this.gameEvent(GameEvent.ENTITY_DAMAGED, pSource.getEntity());

            // TODO: make the left zippleback head fire normal fire rounds but have a short fuel chance
            return true;

        } else {
            return true;
        }
    }


    @Override
    public boolean fireImmune() {
        return false;
    }

    public static class ZippleBackAOECloudRenderer extends EntityRenderer<ZippleBackAOECloud> {

        public ZippleBackAOECloudRenderer(EntityRendererProvider.Context p_174008_) {
            super(p_174008_);
        }

        @Override
        public ResourceLocation getTextureLocation(ZippleBackAOECloud pEntity) {
            return null;
        }
    }
}
