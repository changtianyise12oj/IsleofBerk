package com.GACMD.isleofberk.entity.eggs.entity.eggs;

import com.GACMD.isleofberk.config.ModConfigs;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import com.GACMD.isleofberk.entity.eggs.entity.base.ADragonEggBase;
import com.GACMD.isleofberk.entity.eggs.entity.base.medium.ADragonMediumEggBase;
import com.GACMD.isleofberk.items.DragonEggItem;
import com.GACMD.isleofberk.registery.ModEntities;
import com.GACMD.isleofberk.registery.ModItems;
import com.GACMD.isleofberk.registery.ModParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import software.bernie.geckolib3.core.IAnimatable;

public class SkrillEgg extends ADragonMediumEggBase implements IAnimatable {
    public static final ResourceLocation TEXTURE = new ResourceLocation("isleofberk:textures/egg/skrill/egg_skrill.png");
    private final int hatchTime;

    public SkrillEgg(EntityType<? extends ADragonEggBase> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.hatchTime = ModConfigs.hatchTimeConfig.skrill.get();
    }

    @Override
    public void tick() {
        super.tick();
        // Spawn Lightning Particle sometimes
        if(level.isClientSide() && !this.isCold() && this.tickCount % 40 == 0 && random.nextInt(6) == 0) {
            level.addParticle(ModParticles.SKRILL_SKILL_PARTICLES.get(), this.position().x() - 0.5F + random.nextFloat(), this.position().y() + 0.2 + random.nextFloat() * 0.6F, this.position().z() - 0.5F + random.nextFloat(), 0, 0, 0);
        }
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
        return ModEntities.SKRILL.get().create(this.level);
    }

    @Override
    protected DragonEggItem getItemVersion() {
        return ModItems.SKRILL_EGG.get();
    }

    @Override
    public Block getBlockParticle() {
        return Blocks.OBSIDIAN;
    }

    @Override
    protected int getHatchTime() {
        return this.hatchTime;
    }

    @Override
    public ItemStack getPickResult()
    {
        return new ItemStack(ModItems.SKRILL_EGG.get());
    }
}
