package com.GACMD.isleofberk.common.entity.entities.eggs.entity;

import com.GACMD.isleofberk.common.entity.entities.eggs.entity.base.ADragonEggBase;
import com.GACMD.isleofberk.common.entity.entities.eggs.entity.base.medium.ADragonMediumEggBase;
import com.GACMD.isleofberk.common.items.DragonEggItem;
import com.GACMD.isleofberk.registery.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import software.bernie.geckolib3.core.IAnimatable;

public class LightFuryEgg extends ADragonMediumEggBase implements IAnimatable {

    public static final ResourceLocation TEXTURE = new ResourceLocation("isleofberk:textures/egg/light_fury/light_fury_egg.png");

    public LightFuryEgg(EntityType<? extends ADragonEggBase> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public ResourceLocation getTextureLocation(ADragonEggBase dragonBase) {
        return TEXTURE;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

//    @Override
//    protected ADragonBase getDragonEggResult() {
//        return EntityInit.DEADLY_NADDER.get().create(this.level);
//    }

    @Override
    public Block getBlockParticle() {
        return Blocks.BRAIN_CORAL_BLOCK;
    }

    @Override
    protected DragonEggItem getItemVersion() {
        return ModItems.LIGHT_FURY_EGG.get();
    }
}
