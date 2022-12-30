package com.GACMD.isleofberk.entity.eggs.entity.eggs;

import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import com.GACMD.isleofberk.entity.eggs.entity.base.ADragonEggBase;
import com.GACMD.isleofberk.entity.eggs.entity.base.medium.ADragonMediumEggBase;
import com.GACMD.isleofberk.items.DragonEggItem;
import com.GACMD.isleofberk.registery.ModEntities;
import com.GACMD.isleofberk.registery.ModItems;
import com.GACMD.isleofberk.util.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import software.bernie.geckolib3.core.IAnimatable;

public class NightFuryEgg extends ADragonMediumEggBase implements IAnimatable {
    public static final ResourceLocation TEXTURE = new ResourceLocation("isleofberk:textures/egg/nightfury/egg_night_fury.png");

    public NightFuryEgg(EntityType<? extends ADragonEggBase> pEntityType, Level pLevel) {
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
        return ModEntities.NIGHT_FURY.get().create(this.level);
    }

    @Override
    protected DragonEggItem getItemVersion() {
        return ModItems.NIGHT_FURY_EGG.get();
    }

    @Override
    public Block getBlockParticle() {
        return Blocks.BLACKSTONE;
    }

    @Override
    protected int getHatchTimeMinecraftDays() {
        return Util.mcDaysToMinutes(12);
    }
}
