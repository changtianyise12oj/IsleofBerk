package com.GACMD.isleofberk.entity.eggs.entity.eggs;

import com.GACMD.isleofberk.config.ModConfigs;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import com.GACMD.isleofberk.entity.eggs.entity.base.ADragonEggBase;
import com.GACMD.isleofberk.entity.eggs.entity.base.medium.ADragonMediumEggBase;
import com.GACMD.isleofberk.items.DragonEggItem;
import com.GACMD.isleofberk.registery.ModEntities;
import com.GACMD.isleofberk.registery.ModItems;
import com.GACMD.isleofberk.util.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import software.bernie.geckolib3.core.IAnimatable;

public class LightFuryEgg extends ADragonMediumEggBase implements IAnimatable {

    public static final ResourceLocation TEXTURE = new ResourceLocation("isleofberk:textures/egg/light_fury/light_fury_egg.png");
    private final int hatchTime;

    public LightFuryEgg(EntityType<? extends ADragonEggBase> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.hatchTime = ModConfigs.hatchTimeConfig.lightFury.get();
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
        return ModEntities.LIGHT_FURY.get().create(this.level);
    }

    @Override
    public Block getBlockParticle() {
        return Blocks.BRAIN_CORAL_BLOCK;
    }

    @Override
    protected DragonEggItem getItemVersion() {
        return ModItems.LIGHT_FURY_EGG.get();
    }

    @Override
    protected int getHatchTime() {
        return this.hatchTime;
    }

    @Override
    public ItemStack getPickResult()
    {
        return new ItemStack(ModItems.LIGHT_FURY_EGG.get());
    }
}
