package com.GACMD.isleofberk.common.entity.entities.eggs.entity.base.large;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.common.entity.entities.base.ADragonBase;
import com.GACMD.isleofberk.common.entity.entities.eggs.entity.base.ADragonEggBase;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class ADragonLargeEggBase extends ADragonEggBase {
    public ADragonLargeEggBase(EntityType<? extends ADragonEggBase> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ADragonLargeEggBase(EntityType<? extends ADragonEggBase> pEntityType, Level pLevel, boolean canHatch) {
        this(pEntityType, pLevel);
        this.setCanHatch(canHatch);
        this.setTicksToHatch((byte) 1);
    }


    public ResourceLocation getModelLocation(ADragonBase dragonBase) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "geo/egg/stinger_egg_model.geo.json");
    }
}
