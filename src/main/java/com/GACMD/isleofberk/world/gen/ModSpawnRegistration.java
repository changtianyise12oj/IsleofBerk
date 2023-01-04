package com.GACMD.isleofberk.world.gen;

import com.GACMD.isleofberk.config.CommonConfig;
import com.GACMD.isleofberk.registery.ModEntities;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ModSpawnRegistration {


    // default probability is 0.007F
    // mesa seed 172070159003879
    // plains seed 4009
    public static void onEntitySpawn(final BiomeLoadingEvent event) {

        /**
        * STINGER
         */
        addMobSpawnWithBlackList(event, List.of(Biome.BiomeCategory.SAVANNA, Biome.BiomeCategory.MESA, Biome.BiomeCategory.PLAINS), List.of(Biome.BiomeCategory.MOUNTAIN),
                MobCategory.CREATURE, ModEntities.STINGER.get(), 1, 6, 10, CommonConfig.STINGER.get());

        /**
         * SPEED STINGER
         */
        addMobSpawn(event, List.of(Biome.BiomeCategory.UNDERGROUND, Biome.BiomeCategory.JUNGLE, Biome.BiomeCategory.TAIGA),
                MobCategory.MONSTER, ModEntities.SPEED_STINGER.get(),
                3, 6, 8, CommonConfig.SPEED_STINGER_SPAWN_CHANCE.get());

        addMobSpawnOnSpecificBiomes(event, MobCategory.MONSTER, ModEntities.SPEED_STINGER.get(),
                3, 6, 8, CommonConfig.SPEED_STINGER_SPAWN_CHANCE.get(), Biomes.FROZEN_OCEAN, Biomes.FROZEN_PEAKS,
                Biomes.FROZEN_RIVER, Biomes.DEEP_FROZEN_OCEAN, Biomes.ICE_SPIKES);

        /**
         * TERRIBLE TERROR
         */
        addMobSpawn(event, List.of(Biome.BiomeCategory.BEACH),
                MobCategory.CREATURE, ModEntities.TERRIBLE_TERROR.get(),
                3, 6, 10, CommonConfig.TERROR_SPAWN_CHANCE.get()); // 0.1F
        addMobSpawnOnSpecificBiomes(event, MobCategory.CREATURE, ModEntities.TERRIBLE_TERROR.get(),
                2, 20, 20, CommonConfig.TERROR_SPAWN_CHANCE.get(), Biomes.STONY_SHORE);

        /**
         * TRIPLE STRYKE
         */
        addMobSpawn(event, List.of(Biome.BiomeCategory.MESA), MobCategory.CREATURE,
                ModEntities.TRIPLE_STRYKE.get(),
                1, 1, 1, CommonConfig.STRYKE_SPAWN_CHANCE.get()); // 0.08F
        addMobSpawnOnSpecificBiomes(event, MobCategory.CREATURE, ModEntities.TRIPLE_STRYKE.get(),
                1, 1, 1, CommonConfig.STRYKE_SPAWN_CHANCE.get(), Biomes.SNOWY_TAIGA);

        /**
         * DEADLY NADDER
         */

        addMobSpawn(event, List.of(Biome.BiomeCategory.SAVANNA), MobCategory.CREATURE,
                ModEntities.DEADLY_NADDER.get(),
                1, 1, 1, CommonConfig.NADDER_SPAWN_CHANCE.get()); // 0.08F
        addMobSpawnOnSpecificBiomes(event, MobCategory.CREATURE, ModEntities.DEADLY_NADDER.get(),
                3, 4, 6, CommonConfig.NADDER_SPAWN_CHANCE.get(), Biomes.FOREST, Biomes.FLOWER_FOREST, Biomes.BIRCH_FOREST, Biomes.GROVE, Biomes.SNOWY_PLAINS, Biomes.SPARSE_JUNGLE, Biomes.MEADOW);

        /**
         * GRONCKLE
         */

        addMobSpawn(event, List.of(Biome.BiomeCategory.SAVANNA), MobCategory.CREATURE,
                ModEntities.GRONCKLE.get(),
                1, 1, 1, CommonConfig.GROCNKLE__SPAWN_CHANCE.get()); // 0.08F
        addMobSpawnOnSpecificBiomes(event, MobCategory.CREATURE, ModEntities.GRONCKLE.get(),
                3, 4, 6, CommonConfig.GROCNKLE__SPAWN_CHANCE.get(), Biomes.PLAINS, Biomes.SUNFLOWER_PLAINS, Biomes.OLD_GROWTH_BIRCH_FOREST, Biomes.SWAMP);

        /**
         * NIGHT FURY
         */
        addMobSpawnOnSpecificBiomes(event, MobCategory.CREATURE, ModEntities.NIGHT_FURY.get(),
                1, 1, 1, CommonConfig.N_FURY_SPAWN_CHANCE.get(), Biomes.FROZEN_PEAKS, Biomes.JAGGED_PEAKS, Biomes.STONY_PEAKS,
                Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_GRAVELLY_HILLS);
        addMobSpawnOnSpecificBiomes(event, MobCategory.CREATURE, ModEntities.NIGHT_FURY.get(),
                1, 1, 1, 0.00005F, Biomes.STONY_SHORE, Biomes.OLD_GROWTH_BIRCH_FOREST, Biomes.BIRCH_FOREST);

        /**
         * LIGHT FURY
         */
        addMobSpawnOnSpecificBiomes(event, MobCategory.CREATURE, ModEntities.LIGHT_FURY.get(),
                1, 1, 1, CommonConfig.L_FURY_SPAWN_CHANCE.get(), Biomes.FROZEN_PEAKS, Biomes.STONY_PEAKS,
                Biomes.WINDSWEPT_FOREST);

        addMobSpawnOnSpecificBiomes(event, MobCategory.CREATURE, ModEntities.LIGHT_FURY.get(),
                1, 1, 1, 0.00005F, Biomes.STONY_SHORE, Biomes.OLD_GROWTH_BIRCH_FOREST, Biomes.BIRCH_FOREST);

        /**
         * MONSTROUS NIGHTMARE
         */
        addMobSpawnOnSpecificBiomes(event, MobCategory.CREATURE, ModEntities.MONSTROUS_NIGHTMARE.get(),
                1, 2, 3, CommonConfig.NIGHTMARE_SPAWN_CHANCE.get(), Biomes.STONY_SHORE, Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_GRAVELLY_HILLS, Biomes.WINDSWEPT_FOREST,
                Biomes.BADLANDS, Biomes.WOODED_BADLANDS, Biomes.ERODED_BADLANDS);

        /**
         * HIDEOUS ZIPPLEBACK
         */
        addMobSpawnOnSpecificBiomes(event, MobCategory.CREATURE, ModEntities.ZIPPLEBACK.get(),
                1, 2, 3, CommonConfig.ZIPP_SPAWN_CHANCE.get(), Biomes.SWAMP, Biomes.SPARSE_JUNGLE, Biomes.SNOWY_PLAINS, Biomes.ICE_SPIKES);

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
                                                    int weight, int minGroupSize, int maxGroupSize, float probability, ResourceKey<Biome>... biomes) {
        boolean isBiomeSelected = Arrays.stream(biomes).map(ResourceKey::location).map(Object::toString).anyMatch(s -> s.equals(Objects.requireNonNull(event.getName()).toString()));
        if (isBiomeSelected) {
            MobSpawnSettings.SpawnerData spawnerData1 = new MobSpawnSettings.SpawnerData(entityType, weight, minGroupSize, maxGroupSize);

            event.getSpawns().creatureGenerationProbability(probability);
            event.getSpawns().addSpawn(mobCategory, spawnerData1);
        }
    }
}
