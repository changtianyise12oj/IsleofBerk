package com.GACMD.isleofberk.common.entity.entities.base;

import com.GACMD.isleofberk.common.entity.entities.projectile.breath_user.firebreaths.FireBreathProjectile;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ADragonBaseFlyingRideableBreathUser extends ADragonBaseFlyingRideable {

    private static final EntityDataAccessor<Integer> FRST_FUEL = SynchedEntityData.defineId(ADragonBaseFlyingRideableBreathUser.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SEC_FUEL = SynchedEntityData.defineId(ADragonBaseFlyingRideableBreathUser.class, EntityDataSerializers.INT);

    public ADragonBaseFlyingRideableBreathUser(EntityType<? extends ADragonBaseFlyingRideable> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        // fuel here spawns on world by default but does not define maximum amounts of fuel
        this.entityData.define(FRST_FUEL, getMaxFuel());
        this.entityData.define(SEC_FUEL, getMaxSecondFuel());
    }

    public int getRemainingFuel() {
        return this.entityData.get(FRST_FUEL);
    }

    public void setRemainingFuel(int fuel) {
        this.entityData.set(FRST_FUEL, fuel);
    }

    public int getMaxFuel() {
        return 150;
    }

    public int getRemainingSecondFuel() {
        return this.entityData.get(SEC_FUEL);
    }

    public void setRemainingSecondFuel(int fuel) {
        this.entityData.set(SEC_FUEL, fuel);
    }

    public int getMaxSecondFuel() {
        return 25;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setRemainingFuel(pCompound.getInt("remaining_fuel"));
        this.setRemainingSecondFuel(pCompound.getInt("remaining_fuel2"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("remaining_fuel", getRemainingFuel());
        pCompound.putInt("remaining_fuel2", getRemainingSecondFuel());
    }

    /**
     * meant for terrible terrors
     *
     * @return
     */
    protected boolean canUseBreathNormally() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        // consume fuel
        if (isUsingAbility()) {
            if (getControllingPassenger() != null && getControllingPassenger() instanceof Player player) {
//                if (!player.isCreative())
                modifyFuel(-1);
            } else {
                modifyFuel(-1);
            }
        }
        if (isUsingSECONDAbility()) {
            modifySecondaryFuel(-1);
        }

        // regen fuel regardless of hunger bar
        if (getRandom().nextInt(50) == 1) {
            modifyFuel(2);
        }


        // regen secondary fuel regardless, it only holds 25 units
//        if (getHunger() >= getMaxHunger() * 0.75) {
        if (getRandom().nextInt(250) == 1) {
            modifySecondaryFuel(4);
        }
//        }

        if (getControllingPassenger() != null && getControllingPassenger() instanceof Player rider && canUseBreathNormally()) {
            Vec3 throat = getThroatPos(this);
            Vec3 riderLook = rider.getViewVector(1);

            if (isUsingAbility() && canUseBreath()) {
                firePrimary(riderLook, throat);
            }

            if (isUsingSECONDAbility() && canUseSecondaryFire()) {
                fireSecondary(riderLook, throat);
            }
        }

        // when hunger is less than half and if fuel is greater than half of the max, reduce
//        if (getHunger() < 50) {
//            if (random.nextInt(50) == 0)
//                if (getRemainingFuel() > getRemainingFuel() * 0.50)
//                    modifyFuel(-5);
//
//            if (getRemainingSecondFuel() > getRemainingSecondFuel() * 0.50)
//                modifySecondaryFuel(-5);
//        }

//        Vec3 throat = getThroatPos(this);
//        level.addParticle(ParticleTypes.HAPPY_VILLAGER, throat.x, throat.y, throat.z, 1, 1, 1);
    }

    protected void firePrimary(Vec3 riderLook, Vec3 throat) {
        FireBreathProjectile fireProj = new FireBreathProjectile(this, throat, riderLook, level);
        fireProj.shoot(riderLook, 1F, 7F);
        level.addFreshEntity(fireProj);
    }

    protected void fireSecondary(Vec3 riderLook, Vec3 throat) {

    }

    protected boolean canUseBreath() {
        return getRemainingFuel() > 0 && !isBaby();
    }

    protected boolean canUseSecondaryFire() {
        if (getControllingPassenger() != null && getControllingPassenger() instanceof Player player) {
            if (player.isCreative()) {
                return true;
            }
        }

        return getRemainingSecondFuel() > 0;
//        return true;

    }

    public void modifyFuel(int x) {
        int i = Mth.clamp(this.getRemainingFuel() + x, 0, this.getMaxFuel());
        this.setRemainingFuel(i);
    }

    public void modifySecondaryFuel(int x) {
        int i = Mth.clamp(this.getRemainingSecondFuel() + x, 0, this.getMaxSecondFuel());
        this.setRemainingSecondFuel(i);
    }

}
