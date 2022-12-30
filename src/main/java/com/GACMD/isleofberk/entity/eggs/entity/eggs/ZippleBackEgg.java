package com.GACMD.isleofberk.entity.eggs.entity.eggs;

import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import com.GACMD.isleofberk.entity.eggs.entity.base.ADragonEggBase;
import com.GACMD.isleofberk.entity.eggs.entity.base.large.ADragonLargeEggBase;
import com.GACMD.isleofberk.items.DragonEggItem;
import com.GACMD.isleofberk.registery.ModEntities;
import com.GACMD.isleofberk.registery.ModItems;
import com.GACMD.isleofberk.util.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class ZippleBackEgg extends ADragonLargeEggBase {

    public static final ResourceLocation TEXTURE = new ResourceLocation("isleofberk:textures/egg/zippleback/egg_zippleback.png");

    public ZippleBackEgg(EntityType<? extends ADragonEggBase> pEntityType, Level pLevel) {
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
        return ModEntities.ZIPPLEBACK.get().create(this.level);
    }

    @Override
    protected DragonEggItem getItemVersion() {
        return ModItems.ZIPPLEBACK_EGG.get();
    }

    @Override
    public Block getBlockParticle() {
        return Blocks.WET_SPONGE;
    }

    @Override
    protected int getHatchTimeMinecraftDays() {
        return Util.mcDaysToMinutes(10);
    }
}
