package com.GACMD.isleofberk.entity.eggs.entity.base.medium;

import com.GACMD.isleofberk.entity.eggs.entity.base.ADragonEggBase;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class ADragonMediumEggBase extends ADragonEggBase {
    public ADragonMediumEggBase(EntityType<? extends ADragonEggBase> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ADragonMediumEggBase(EntityType<? extends ADragonEggBase> pEntityType, Level pLevel, boolean canHatch) {
        this(pEntityType, pLevel);
        this.setCanHatch(canHatch);
        this.setTicksToHatch((byte) 1);
    }
}
