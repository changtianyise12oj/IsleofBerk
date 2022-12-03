package com.GACMD.isleofberk.common.entity.entities.eggs.entity.base.small;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.common.entity.entities.base.ADragonBase;
import com.GACMD.isleofberk.common.entity.entities.eggs.entity.base.ADragonEggBase;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;

public abstract class ADragonSmallEggBase extends ADragonEggBase implements IAnimatable {

    public ADragonSmallEggBase(EntityType<? extends ADragonEggBase>  pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ADragonSmallEggBase(EntityType<? extends ADragonEggBase> pEntityType, Level pLevel, boolean canHatch) {
        this(pEntityType, pLevel);
        this.setCanHatch(canHatch);
        this.setTicksToHatch((byte) 1);
    }

    public ResourceLocation getModelLocation(ADragonBase dragonBase) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "geo/egg/small_egg_model.geo.json");
    }

    @Override
    public void registerControllers(AnimationData data) {

    }

}
