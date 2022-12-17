package com.GACMD.isleofberk.common.entity.entities.dragons.skrill;

import com.GACMD.isleofberk.common.entity.entities.base.dragon.ADragonBaseFlyingRideable;
import com.GACMD.isleofberk.common.entity.entities.base.dragon.ADragonBaseFlyingRideableBreathUser;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import software.bernie.geckolib3.core.IAnimatable;

public class Skrill extends ADragonBaseFlyingRideableBreathUser implements IAnimatable {

    public Skrill(EntityType<? extends ADragonBaseFlyingRideable> entityType, Level level) {
        super(entityType, level);
    }

    //  Attributes
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 120.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.4F)
                .add(Attributes.FLYING_SPEED, 0.08F)
                .add(Attributes.ATTACK_DAMAGE, 4.5F)
                .add(Attributes.FOLLOW_RANGE, 4.5F)
                .add(ForgeMod.SWIM_SPEED.get(), 2);
    }

}
