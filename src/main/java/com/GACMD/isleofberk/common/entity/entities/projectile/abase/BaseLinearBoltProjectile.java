package com.GACMD.isleofberk.common.entity.entities.projectile.abase;

import com.GACMD.isleofberk.common.entity.entities.base.dragon.ADragonBase;
import com.GACMD.isleofberk.common.entity.entities.base.dragon.ADragonBaseFlyingRideableProjUser;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class BaseLinearBoltProjectile extends BaseLinearFlightProjectile {

    public ADragonBaseFlyingRideableProjUser dragon;

    public BaseLinearBoltProjectile(EntityType<? extends AbstractHurtingProjectile> type, Level level) {
        super(type, level);
    }

    public BaseLinearBoltProjectile(EntityType<? extends AbstractHurtingProjectile> type, ADragonBaseFlyingRideableProjUser owner, Vec3 throat, Vec3 end, Level level, int strengthRadius) {
        super(type, owner, throat, end, level, strengthRadius);
        this.dragon = owner;
    }

    @Override
    protected Explosion explode(ADragonBase dragon, double x, double y, double z, float explosionStrength, boolean flag, Explosion.BlockInteraction blockInteraction) {
        return null;
    }

    /**
     * Similar to setArrowHeading, it's point the throwable entity to a x, y, z direction.
     */
    public void shoot(Vec3 end, float partialTicks, int inaccuracy) {
        if (partialTicks == 1) {
            super.shoot(end, partialTicks, inaccuracy);

            if (dragon.tier1()) {
                this.setDamageTier(1);
            } else if (dragon.tier2()) {
                this.setDamageTier(2);
            } else if (dragon.tier3()) {
                this.setDamageTier(3);
            } else if (dragon.tier4()) {
                this.setDamageTier(4);
            }
        }
    }
}
