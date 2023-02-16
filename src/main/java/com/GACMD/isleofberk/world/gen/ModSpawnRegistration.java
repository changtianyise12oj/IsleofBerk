package com.GACMD.isleofberk.world.gen;

import com.GACMD.isleofberk.registery.ModEntities;
import com.GACMD.isleofberk.registery.ModTags;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ModSpawnRegistration {

    public static void onEntitySpawn(final BiomeLoadingEvent event) {

        addMobSpawnOnSpecificBiomes(event, MobCategory.CREATURE, ModEntities.STINGER.get(), 1, 6, 10, 0.05F, ModTags.Biomes.STINGER_BIOMES);
        addMobSpawnOnSpecificBiomes(event, MobCategory.CREATURE, ModEntities.TERRIBLE_TERROR.get(), 5, 20, 20, 0.08F, ModTags.Biomes.TERRIBLE_TERROR_BIOMES);
        addMobSpawnOnSpecificBiomes(event, MobCategory.CREATURE, ModEntities.TRIPLE_STRYKE.get(), 1, 1, 1, 0.03F, ModTags.Biomes.TRIPLE_STRYKE_BIOMES);
        addMobSpawnOnSpecificBiomes(event, MobCategory.CREATURE, ModEntities.DEADLY_NADDER.get(), 3, 4, 6, 0.05F, ModTags.Biomes.DEADLY_NADDER_BIOMES);
        addMobSpawnOnSpecificBiomes(event, MobCategory.CREATURE, ModEntities.GRONCKLE.get(), 3, 4, 6, 0.05F, ModTags.Biomes.GRONCKLE_BIOMES);
        addMobSpawnOnSpecificBiomes(event, MobCategory.CREATURE, ModEntities.NIGHT_FURY.get(), 1, 1, 1, 0.00005F, ModTags.Biomes.NIGHTFURY_BIOMES);
        addMobSpawnOnSpecificBiomes(event, MobCategory.CREATURE, ModEntities.LIGHT_FURY.get(), 1, 1, 1, 0.00005F, ModTags.Biomes.LIGHTFURY_BIOMES);
        addMobSpawnOnSpecificBiomes(event, MobCategory.CREATURE, ModEntities.MONSTROUS_NIGHTMARE.get(), 1, 2, 3, 0.04F, ModTags.Biomes.MONSTROUS_NIGHTMARE_BIOMES);
        addMobSpawnOnSpecificBiomes(event, MobCategory.CREATURE, ModEntities.ZIPPLEBACK.get(), 1, 2, 3, 0.04F, ModTags.Biomes.HIDEOUS_ZIPPLEBACK_BIOMES);

    }

    /**
     * Adds entity spawning via BiomeDictionary so dragons spawn on biomes from other mods
     *
     * @param event       biomeloading event
     * @param entityType  the entity to spawn
     * @param weight      the cost of entity per spawn, less weight less spawning
     * @param probability ranged from 0 to 1; how many of them will spawn
     */
    private static void addMobSpawn(BiomeLoadingEvent event, List<Biome.BiomeCategory> categories, MobCategory mobCategory, EntityType<?> entityType,
                                    int weight, int minGroupSize, int maxGroupSize,
                                    float probability) {
        if (categories.contains(event.getCategory())) {
            MobSpawnSettings.SpawnerData spawnerData1 = new MobSpawnSettings.SpawnerData(entityType, weight, minGroupSize, maxGroupSize);

            event.getSpawns().creatureGenerationProbability(probability);
            event.getSpawns().addSpawn(mobCategory, spawnerData1);
        }
    }

    /**
     * Adds entity spawning via BiomeDictionary so dragons spawn on biomes from other mods
     *
     * @param event       biomeloading event
     * @param entityType  the entity to spawn
     * @param weight      the cost of entity per spawn, less weight less spawning
     * @param probability ranged from 0 to 1; how many of them will spawn
     */
    private static void addMobSpawnAllBiomes(BiomeLoadingEvent event, MobCategory mobCategory, EntityType<?> entityType,
                                             int weight, int minGroupSize, int maxGroupSize,
                                             float probability) {
        MobSpawnSettings.SpawnerData spawnerData1 = new MobSpawnSettings.SpawnerData(entityType, weight, minGroupSize, maxGroupSize);

        event.getSpawns().creatureGenerationProbability(probability);
        event.getSpawns().addSpawn(mobCategory, spawnerData1);
    }

    private static void addMobSpawnWithBlackList(BiomeLoadingEvent event, List<Biome.BiomeCategory> categories, List<Biome.BiomeCategory> blacklist,
                                                 MobCategory mobCategory, EntityType<?> entityType,
                                                 int weight, int minGroupSize, int maxGroupSize, float probability) {
        if (categories.contains(event.getCategory()) && !blacklist.contains(event.getCategory())) {
            MobSpawnSettings.SpawnerData spawnerData1 = new MobSpawnSettings.SpawnerData(entityType, weight, minGroupSize, maxGroupSize);

            event.getSpawns().creatureGenerationProbability(probability);
            event.getSpawns().addSpawn(mobCategory, spawnerData1);
        }
    }

    @SafeVarargs
    private static void addMobSpawnOnSpecificBiomes(BiomeLoadingEvent event, MobCategory mobCategory, EntityType<?> entityType,
                                                    int weight, int minGroupSize, int maxGroupSize, float probability, TagKey<Biome>... biomes) {
        boolean isBiomeSelected = Arrays.stream(biomes).map(TagKey::location).map(Object::toString).anyMatch(s -> s.equals(Objects.requireNonNull(event.getName()).toString()));
        if (isBiomeSelected) {
            MobSpawnSettings.SpawnerData spawnerData1 = new MobSpawnSettings.SpawnerData(entityType, weight, minGroupSize, maxGroupSize);

            event.getSpawns().creatureGenerationProbability(probability);
            event.getSpawns().addSpawn(mobCategory, spawnerData1);
        }
    }
}
