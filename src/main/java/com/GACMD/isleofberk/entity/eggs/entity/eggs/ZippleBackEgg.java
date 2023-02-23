package com.GACMD.isleofberk.entity.eggs.entity.eggs;

import com.GACMD.isleofberk.config.ModConfigs;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import com.GACMD.isleofberk.entity.eggs.entity.base.ADragonEggBase;
import com.GACMD.isleofberk.entity.eggs.entity.base.large.ADragonLargeEggBase;
import com.GACMD.isleofberk.items.DragonEggItem;
import com.GACMD.isleofberk.registery.ModEntities;
import com.GACMD.isleofberk.registery.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class ZippleBackEgg extends ADragonLargeEggBase {

    public static final ResourceLocation TEXTURE = new ResourceLocation("isleofberk:textures/egg/zippleback/egg_zippleback.png");
    private final int hatchTime;

    public ZippleBackEgg(EntityType<? extends ADragonEggBase> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.hatchTime = ModConfigs.hatchTimeConfig.zippleback.get();
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
    protected int getHatchTime() {
        return this.hatchTime;
    }

    @Override
    public ItemStack getPickResult()
    {
        return new ItemStack(ModItems.ZIPPLEBACK_EGG.get());
    }
}
