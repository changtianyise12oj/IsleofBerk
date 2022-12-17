package com.GACMD.isleofberk.common.entity.entities.eggs.entity;

import com.GACMD.isleofberk.common.entity.entities.base.dragon.ADragonBase;
import com.GACMD.isleofberk.common.entity.entities.eggs.entity.base.ADragonEggBase;
import com.GACMD.isleofberk.common.entity.entities.eggs.entity.base.large.ADragonLargeEggBase;
import com.GACMD.isleofberk.common.items.DragonEggItem;
import com.GACMD.isleofberk.registery.ModEntities;
import com.GACMD.isleofberk.registery.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class MonstrousNightmareEgg extends ADragonLargeEggBase {

    public static final ResourceLocation TEXTURE = new ResourceLocation("isleofberk:textures/egg/monstrous_nightmare/egg_nightmare.png");

    public MonstrousNightmareEgg(EntityType<? extends ADragonEggBase> pEntityType, Level pLevel) {
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

    @Override
    protected ADragonBase getDragonEggResult() {
        return ModEntities.MONSTROUS_NIGHTMARE.get().create(this.level);
    }

    @Override
    protected DragonEggItem getItemVersion() {
        return ModItems.MONSTROUS_NIGHTMARE_EGG.get();
    }
    @Override
    public Block getBlockParticle() {
        return Blocks.STRIPPED_ACACIA_LOG;
    }
}
