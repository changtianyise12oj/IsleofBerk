package com.GACMD.isleofberk.common.entity.entities.eggs.entity;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.common.entity.entities.base.ADragonBase;
import com.GACMD.isleofberk.common.entity.entities.dragons.speedstinger.SpeedStinger;
import com.GACMD.isleofberk.common.entity.entities.eggs.entity.base.ADragonEggBase;
import com.GACMD.isleofberk.common.entity.entities.eggs.entity.base.small.ADragonSmallEggBase;
import com.GACMD.isleofberk.common.items.DragonEggItem;
import com.GACMD.isleofberk.registery.ModEntities;
import com.GACMD.isleofberk.registery.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class SpeedStingerEgg extends ADragonSmallEggBase {
    public static final ResourceLocation SPEED_STINGER = new ResourceLocation("isleofberk:textures/egg/speedstinger/egg_speed_stinger_0.png");
    public static final ResourceLocation FLOUTSCOUT = new ResourceLocation("isleofberk:textures/egg/speedstinger/egg_floutscout_1.png");
    public static final ResourceLocation ICE_BREAKER = new ResourceLocation("isleofberk:textures/egg/speedstinger/egg_ice_breaker_2.png");
    public static final ResourceLocation SWEET_STING = new ResourceLocation("isleofberk:textures/egg/speedstinger/egg_sweet_sting_3.png");

    public SpeedStingerEgg(EntityType<? extends ADragonSmallEggBase> animal, Level world) {
        super(animal, world);
        this.dragonResult = new SpeedStinger(ModEntities.SPEED_STINGER.get(), level);
        this.setDragonVariant(0);
    }

    public ResourceLocation getModelLocation(ADragonBase dragonBase) {
        return new ResourceLocation(IsleofBerk.MOD_ID, "geo/egg/small_egg_model.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(ADragonEggBase dragonBase) {
        switch (getDragonVariant()) {
            default:
            case 0:
                return SPEED_STINGER;
            case 1:
                return FLOUTSCOUT;
            case 2:
                return ICE_BREAKER;
            case 3:
                return SWEET_STING;
        }
    }

    public Block getBlockParticle() {
        return switch (getDragonVariant()) {
            case 0 -> Blocks.BEE_NEST;
            case 1 -> Blocks.BASALT;
            case 2 -> Blocks.PACKED_ICE;
            case 3 -> Blocks.PUMPKIN;
            default -> throw new IllegalStateException("Unexpected value: " + getDragonVariant());
        };
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (!level.isClientSide() && !isRemoved()) {
            DragonEggItem item = ModItems.SPEED_STINGER_EGG.get();
            DragonEggItem item1 = ModItems.SPEED_STINGER_EGG_FLOUTSCOOUT.get();
            DragonEggItem item2 = ModItems.SPEED_STINGER_EGG_ICE_BREAKER.get();
            DragonEggItem item3 = ModItems.SPEED_STINGER_EGG_SWEET_STING.get();
            switch (getDragonVariant()) {
                case 0 -> {
                    this.spawnAtLocation(item);
                    this.discard();
                    return true;
                }
                case 1 -> {
                    this.spawnAtLocation(item1);
                    this.discard();
                    return true;
                }
                case 2 -> {
                    this.spawnAtLocation(item2);
                    this.discard();
                    return true;
                }
                case 3 -> {
                    this.spawnAtLocation(item3);
                    this.discard();
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    protected ADragonBase getDragonEggResult() {
        return ModEntities.SPEED_STINGER.get().create(this.level);
    }

    @Override
    protected int particleEggShellCount() {
        return 6;
    }
}
