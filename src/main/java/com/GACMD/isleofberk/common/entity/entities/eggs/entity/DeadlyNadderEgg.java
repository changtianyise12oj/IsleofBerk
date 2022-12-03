package com.GACMD.isleofberk.common.entity.entities.eggs.entity;

import com.GACMD.isleofberk.common.entity.entities.base.ADragonBase;
import com.GACMD.isleofberk.common.entity.entities.eggs.entity.base.ADragonEggBase;
import com.GACMD.isleofberk.common.entity.entities.eggs.entity.base.medium.ADragonMediumEggBase;
import com.GACMD.isleofberk.common.items.DragonEggItem;
import com.GACMD.isleofberk.registery.ModEntities;
import com.GACMD.isleofberk.registery.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import software.bernie.geckolib3.core.IAnimatable;

public class DeadlyNadderEgg extends ADragonMediumEggBase implements IAnimatable {
    public static final ResourceLocation NADDER_EGG = new ResourceLocation("isleofberk:textures/egg/deadly_nadder/egg_nadder.png");

    public DeadlyNadderEgg(EntityType<? extends ADragonEggBase> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public ResourceLocation getTextureLocation(ADragonEggBase dragonBase) {
        return NADDER_EGG;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    protected ADragonBase getDragonEggResult() {
        return ModEntities.DEADLY_NADDER.get().create(this.level);
    }

    @Override
    protected DragonEggItem getItemVersion() {
        return ModItems.NADDER_EGG.get();
    }

    @Override
    public Block getBlockParticle() {
        return Blocks.AMETHYST_BLOCK;
    }
}
