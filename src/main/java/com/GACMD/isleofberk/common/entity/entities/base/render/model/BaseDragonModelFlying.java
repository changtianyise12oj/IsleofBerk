package com.GACMD.isleofberk.common.entity.entities.base.render.model;

import com.GACMD.isleofberk.common.entity.entities.base.ADragonBaseFlyingRideable;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.core.IAnimatable;

public class BaseDragonModelFlying<T extends ADragonBaseFlyingRideable & IAnimatable> extends BaseDragonModel<T> {

    public BaseDragonModelFlying(EntityRendererProvider.Context renderManager) {
        super();
    }


}
