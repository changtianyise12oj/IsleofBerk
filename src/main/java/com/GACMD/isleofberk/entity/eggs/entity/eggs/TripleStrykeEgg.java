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

public class TripleStrykeEgg extends ADragonMediumEggBase implements IAnimatable {
    public static final ResourceLocation TEXTURE = new ResourceLocation("isleofberk:textures/egg/triplestryke/triple_stryke_egg.png");

    public TripleStrykeEgg(EntityType<? extends ADragonEggBase> pEntityType, Level pLevel) {
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
        return ModEntities.TRIPLE_STRYKE.get().create(this.level);
    }

    @Override
    protected DragonEggItem getItemVersion() {
        return ModItems.TRIPLE_STRYKE_EGG.get();
    }

    @Override
    public Block getBlockParticle() {
        return Blocks.CRIMSON_HYPHAE;
    }

    @Override
    protected int getHatchTimeMinecraftDays() {
        return Util.mcDaysToMinutes(10);
    }
}
