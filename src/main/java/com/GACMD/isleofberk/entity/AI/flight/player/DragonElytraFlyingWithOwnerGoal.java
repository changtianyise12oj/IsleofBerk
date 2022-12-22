package com.GACMD.isleofberk.entity.AI.flight.player;

import com.GACMD.isleofberk.entity.base.dragon.ADragonBaseFlyingRideable;
import com.GACMD.isleofberk.entity.AI.flight.ADragonBaseBaseFlyingRideableGoal;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class DragonElytraFlyingWithOwnerGoal extends ADragonBaseBaseFlyingRideableGoal {

    public DragonElytraFlyingWithOwnerGoal(ADragonBaseFlyingRideable dragonBase) {
        super(dragonBase);
    }

    @Override
    public boolean canUse() {
        LivingEntity owner = dragon.getOwner();
        if(owner != null) {
            if (dragon.isVehicle()) {
                return false;
            }
            // terrors shouldn't fly with player, they need to mount you first
            if(!dragon.canBeMounted()) {
                return false;
            }

            if(!dragon.isDragonFollowing()) {
                return false;
            }

            // follow if elytra flying and owner is riding on the dragon to the sky
            return owner.isFallFlying() || (owner.getVehicle() != null && !owner.isOnGround()) || (owner.getVehicle() instanceof ADragonBaseFlyingRideable dragon && dragon.isDragonOnGround());
        }
        return false;
    }


    // add a tag for how many dragons are following the player, then arrange them in a group,
    @Override
    public void tick() {
        LivingEntity owner = dragon.getOwner();
        if (owner != null) {
            if(!dragon.isFlying()) {
                dragon.setIsFlying(true);
            }

                Vec3 movePos = new Vec3(owner.getX(), owner.getY() + 4, owner.getZ());
                // now count the index and make them spread, only happens when the entire class AI kicks in
                tailingDragons.put(owner.getUUID(), dragon);
                dragon.getNavigation().moveTo(movePos.x() + (tailingDragons.size() * 3), movePos.y(), movePos.z() + (tailingDragons.size() * 3), 4);

        }
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
}
