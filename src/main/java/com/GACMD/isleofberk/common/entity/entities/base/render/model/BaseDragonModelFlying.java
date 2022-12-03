package com.GACMD.isleofberk.common.entity.entities.base.render.model;

import com.GACMD.isleofberk.common.entity.entities.base.ADragonBaseFlyingRideable;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.core.IAnimatable;

public class BaseDragonModelFlying<T extends ADragonBaseFlyingRideable & IAnimatable> extends BaseDragonModel<T> {

    public BaseDragonModelFlying(EntityRendererProvider.Context renderManager) {
        super();
    }

    @Override
    public void setMolangQueries(IAnimatable animatable, double currentTick) {
//        MolangParser parser = GeckoLibCache.getInstance().parser;
//        Minecraft minecraftInstance = Minecraft.getInstance();
//        if (!(animatable instanceof ADragonBaseFlyingRideable)) {
//            return;
//        }
//
//        if(animatable instanceof ADragonBaseFlyingRideable dragon) {
//            parser.setValue("query.xRot", dragon.getXRot());
//            parser.setValue("query.xRot0", dragon.xRotO);
//        }

        super.setMolangQueries(animatable, currentTick);
    }
}
