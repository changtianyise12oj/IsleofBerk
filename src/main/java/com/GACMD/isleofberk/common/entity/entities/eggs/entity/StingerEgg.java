package com.GACMD.isleofberk.common.entity.entities.eggs.entity;

import com.GACMD.isleofberk.common.entity.entities.base.dragon.ADragonBase;
import com.GACMD.isleofberk.common.entity.entities.dragons.speedstinger.SpeedStinger;
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
import software.bernie.geckolib3.core.IAnimatable;

public class StingerEgg extends ADragonLargeEggBase implements IAnimatable {
    public static final ResourceLocation MUDMASHER = new ResourceLocation("isleofberk:textures/egg/stinger/mudmasher_0.png");
    public static final ResourceLocation WILDROAR = new ResourceLocation("isleofberk:textures/egg/stinger/wildroar_1.png");

    public StingerEgg(EntityType<? extends ADragonLargeEggBase> animal, Level world) {
        super(animal, world);
        this.dragonResult = new SpeedStinger(ModEntities.SPEED_STINGER.get(), level);
        this.setDragonVariant(0);
    }

    @Override
    public ResourceLocation getTextureLocation(ADragonEggBase dragonBase) {
        return WILDROAR;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    protected DragonEggItem getItemVersion() {
        return ModItems.STINGER_EGG.get();
    }

    @Override
    public ADragonBase getDragonEggResult() {
        return ModEntities.STINGER.get().create(this.level);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public Block getBlockParticle() {
        return Blocks.LARGE_FERN;
    }
}
