package com.GACMD.isleofberk.common.entity.entities.base;

import com.GACMD.isleofberk.common.entity.entities.projectile.abase.BaseLinearFlightProjectile;
import com.GACMD.isleofberk.common.entity.entities.projectile.proj_user.fire_bolt.FireBolt;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ADragonBaseFlyingRideableProjUser extends ADragonBaseFlyingRideable {

    public ADragonBaseFlyingRideableProjUser(EntityType<? extends ADragonBaseFlyingRideable> entityType, Level level) {
        super(entityType, level);
    }

    int explosionStrength;
    public int playerBoltBlastPendingScale = 0;
    public int playerBoltBlastPendingStopThreshold = 0;
    protected static final EntityDataAccessor<Integer> TICK_SINCE_LAST_FIRE = SynchedEntityData.defineId(ADragonBaseFlyingRideableProjUser.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Boolean> MARK_FIRED = SynchedEntityData.defineId(ADragonBaseFlyingRideableProjUser.class, EntityDataSerializers.BOOLEAN);

    public int getExplosionStrength() {
        return explosionStrength;
    }

    public void setExplosionStrength(int explosionStrength) {
        this.explosionStrength = explosionStrength;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TICK_SINCE_LAST_FIRE, 0);
        this.entityData.define(MARK_FIRED, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("markFired", isMarkFired());
        pCompound.putInt("ticksFire", getTicksSinceLastFire());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setTicksSinceLastFire(pCompound.getInt("ticksFire"));
        this.setMarkFired(pCompound.getBoolean("markFired"));
    }

    public boolean isMarkFired() {
        return this.entityData.get(MARK_FIRED);
    }

    public void setMarkFired(boolean fired) {
        this.entityData.set(MARK_FIRED, fired);
    }

    public int getTicksSinceLastFire() {
        return this.entityData.get(TICK_SINCE_LAST_FIRE);
    }

    public void setTicksSinceLastFire(int pType) {
        this.entityData.set(TICK_SINCE_LAST_FIRE, pType);
    }

    @Override
    public void tick() {
        super.tick();
        if (getControllingPassenger() != null && getControllingPassenger() instanceof Player rider) {
            Vec3 throat = getThroatPos(this);
            Vec3 riderLook = rider.getViewVector(1);

            int ticksLimit = getMaxPlayerBoltBlast();
            if (getControllingPassenger() != null) {
                if (isUsingAbility()) {
                    playerBoltBlastPendingStopThreshold++;

                    if (playerBoltBlastPendingScale <= ticksLimit + 1 && playerBoltBlastPendingStopThreshold < ticksLimit * 1.10) {
                        playerBoltBlastPendingScale++;
                    }

                    // slowly decrement to 13% blast if R is held too long to prevent collecting of overcharged breath attacks
                    if (playerBoltBlastPendingStopThreshold > ticksLimit * 1.10) {
                        if(playerBoltBlastPendingScale > ticksLimit * 0.30)
                        setPlayerBoltBlastPendingScale(playerBoltBlastPendingScale-=3);
                    }
                } else if (playerBoltBlastPendingScale > 0) {
                    playerBoltBlastPendingScale--;
                    playerBoltBlastPendingStopThreshold--;
                }
            }

            System.out.println("playerBoltBlastPendingScale " + playerBoltBlastPendingScale);
            System.out.println("playerBoltBlastPendingStopThreshold " + playerBoltBlastPendingStopThreshold);
            System.out.println("ticksLimit " + ticksLimit);

            if (getTicksSinceLastFire() > 0) {
                setTicksSinceLastFire(getTicksSinceLastFire() - 1);
            }

            if (getTicksSinceLastFire() < 2) {
                setMarkFired(false);
            } else {
                setMarkFired(true);
            }

            if (canFireProj()) {
                fireProjectile(riderLook, throat);
            }

            if(!isUsingAbility()) {
                setPlayerBoltBlastPendingStopThreshold(0);
            }
        }
    }

    /**
     * Add bonus damage to boss mobs with high health
     * damage is set by the dragon itself, some dragons have lesser damage
     *
     * @param dragon
     * @param entity
     * @return
     */
    @Override
    public float getProjectileDamage(ADragonBase dragon, Entity entity, BaseLinearFlightProjectile projectile) {
        if (projectile.getDamageTier() == 1) {
            return 21 + (entity instanceof LivingEntity livingEntity ? (float) Math.floor(livingEntity.getMaxHealth() * 0.05F) : 0F);
        } else if (projectile.getDamageTier() == 2) {
            return 27 + (entity instanceof LivingEntity livingEntity ? (float) Math.floor(livingEntity.getMaxHealth() * 0.10F) : 0F);
        } else if (projectile.getDamageTier() == 3) {
            return 30F + (entity instanceof LivingEntity livingEntity ? (float) Math.floor(livingEntity.getMaxHealth() * 0.20F) : 0F);
        } else if (projectile.getDamageTier() == 4) {
            return 37F + (entity instanceof LivingEntity livingEntity ? (float) Math.floor(livingEntity.getMaxHealth() * 0.30F) : 0F);
        }

        return 24;
    }

    public boolean canFireProj() {
        return !this.isBaby();
    }

    protected void fireProjectile(Vec3 riderLook, Vec3 throat) {
        if ((tier1() || tier2() || tier3() || tier4()) && !isUsingAbility()) {
            setTicksSinceLastFire(20);
            FireBolt bolt = new FireBolt(this, throat, riderLook, level, getExplosionStrength());
            bolt.shoot(riderLook, 1F);
            level.addFreshEntity(bolt);
            setPlayerBoltBlastPendingScale(0);
            setPlayerBoltBlastPendingStopThreshold(0);
        }
    }

    public int getPlayerBoltBlastPendingScale() {
        return playerBoltBlastPendingScale;
    }

    public void setPlayerBoltBlastPendingScale(int playerBoltBlastPendingScale) {
        this.playerBoltBlastPendingScale = playerBoltBlastPendingScale;
    }

    public int getPlayerBoltBlastPendingStopThreshold() {
        return playerBoltBlastPendingStopThreshold;
    }

    public void setPlayerBoltBlastPendingStopThreshold(int playerBoltBlastPendingStopThreshold) {
        this.playerBoltBlastPendingStopThreshold = playerBoltBlastPendingStopThreshold;
    }

    public int getMaxPlayerBoltBlast() {
        return 60;
    }

    public boolean tier1() {
        return getPlayerBoltBlastPendingScale() >= getMaxPlayerBoltBlast() * 0.10 && getPlayerBoltBlastPendingScale() < getMaxPlayerBoltBlast() * 0.50;
    }

    public boolean tier2() {
        return getPlayerBoltBlastPendingScale() >= getMaxPlayerBoltBlast() * 0.50 && getPlayerBoltBlastPendingScale() < getMaxPlayerBoltBlast() * 0.75;
    }

    public boolean tier3() {
        return getPlayerBoltBlastPendingScale() >= getMaxPlayerBoltBlast() * 0.75 && getPlayerBoltBlastPendingScale() < getMaxPlayerBoltBlast();
    }

    public boolean tier4() {
        return getPlayerBoltBlastPendingScale() >= getMaxPlayerBoltBlast();
    }
}
