package com.GACMD.isleofberk.entity.eggs.entity.eggs;

import com.GACMD.isleofberk.config.ModConfigs;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import com.GACMD.isleofberk.entity.dragons.speedstinger.SpeedStinger;
import com.GACMD.isleofberk.entity.eggs.entity.base.ADragonEggBase;
import com.GACMD.isleofberk.entity.eggs.entity.base.large.ADragonLargeEggBase;
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

public class StingerEgg extends ADragonLargeEggBase implements IAnimatable {
    public static final ResourceLocation MUDMASHER = new ResourceLocation("isleofberk:textures/egg/stinger/mudmasher_0.png");
    public static final ResourceLocation WILDROAR = new ResourceLocation("isleofberk:textures/egg/stinger/wildroar_1.png");
    private final int hatchTime;

    public StingerEgg(EntityType<? extends ADragonLargeEggBase> animal, Level world) {
        super(animal, world);
        this.dragonResult = new SpeedStinger(ModEntities.SPEED_STINGER.get(), level);
        this.setDragonVariant(0);
        this.hatchTime = ModConfigs.hatchTimeConfig.stinger.get();
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

    @Override
    protected int getHatchTime() {
        return this.hatchTime;
    }

    @Override
    public ItemStack getPickResult()
    {
        return new ItemStack(ModItems.STINGER_EGG.get());
    }
}
