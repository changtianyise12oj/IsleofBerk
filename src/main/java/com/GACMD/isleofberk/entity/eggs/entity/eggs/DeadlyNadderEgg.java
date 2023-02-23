package com.GACMD.isleofberk.entity.eggs.entity.eggs;

import com.GACMD.isleofberk.config.ModConfigs;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import com.GACMD.isleofberk.entity.eggs.entity.base.ADragonEggBase;
import com.GACMD.isleofberk.entity.eggs.entity.base.medium.ADragonMediumEggBase;
import com.GACMD.isleofberk.items.DragonEggItem;
import com.GACMD.isleofberk.registery.ModEntities;
import com.GACMD.isleofberk.registery.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import software.bernie.geckolib3.core.IAnimatable;

public class DeadlyNadderEgg extends ADragonMediumEggBase implements IAnimatable {
    public static final ResourceLocation NADDER_EGG = new ResourceLocation("isleofberk:textures/egg/deadly_nadder/egg_nadder.png");
    private final int hatchTime;

    public DeadlyNadderEgg(EntityType<? extends ADragonEggBase> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.hatchTime = ModConfigs.hatchTimeConfig.deadlyNadder.get();
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

    @Override
    protected int getHatchTime() {
        return this.hatchTime;
    }

    @Override
    public ItemStack getPickResult()
    {
        return new ItemStack(ModItems.NADDER_EGG.get());
    }
}