package com.GACMD.isleofberk.config.util;

import net.minecraft.world.entity.EntityType;

import java.util.List;

public record SpawnDataStorage(EntityType<?> dragon, int weight, int minCount, int maxCount, List<String> biomes) {

    public EntityType<?> getDragon() {
        return this.dragon;
    }

    public int getWeight() {
        return this.weight;
    }

    public int getMinCount() {
        return this.minCount;
    }

    public int getMaxCount() {
        return this.maxCount;
    }

    public List<String> getBiomes() {
        return this.biomes;
    }
}