package com.GACMD.isleofberk.world.gen;

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

    public static void SSspawnOnCaves(final LivingSpawnEvent.CheckSpawn event) {

    }

    // default probability is 0.007F
    // mesa seed 172070159003879
    // plains seed 4009
    public static void onEntitySpawn(final BiomeLoadingEvent event) {
        // TODO: stingers are the only ones working try speed stingers. maybe it is caused by missing offspring data?
        //  stingers alone spawn but deadly nadders alone won't
        //  ride client/server messages, jumping, breath, ability, are called even if vehicle is not present
        // TODO: method works addMobSpawn
        // TODO: nadder spawns if set to ADragonBaseRideableGround

        // TODO: STINGER
        addMobSpawnWithBlackList(event, List.of(Biome.BiomeCategory.SAVANNA, Biome.BiomeCategory.MESA, Biome.BiomeCategory.PLAINS), List.of(Biome.BiomeCategory.MOUNTAIN),
                MobCategory.CREATURE, ModEntities.STINGER.get(), 1, 6, 10, 0.10F);

        // TODO: SPEED STINGER
        // cave
        addMobSpawn(event, List.of(Biome.BiomeCategory.UNDERGROUND),
                MobCategory.MONSTER, ModEntities.SPEED_STINGER.get(),
                3, 3, 4, 0.08F); // 0.15F 0.025f

        // taiga
        addMobSpawn(event, List.of(Biome.BiomeCategory.TAIGA),
                MobCategory.MONSTER, ModEntities.SPEED_STINGER.get(),
                3, 3, 4, 0.08F); // 0.15F 0.025f

        // ice
        addMobSpawnOnSpecificBiomes(event, MobCategory.MONSTER, ModEntities.SPEED_STINGER.get(),
                3, 3, 4, 0.08F, Biomes.FROZEN_OCEAN, Biomes.FROZEN_PEAKS,
                Biomes.FROZEN_RIVER, Biomes.DEEP_FROZEN_OCEAN);

        // jungle
        addMobSpawn(event,List.of(Biome.BiomeCategory.JUNGLE), MobCategory.MONSTER, ModEntities.SPEED_STINGER.get(),
                3, 3, 4, 0.08F);

        // TODO: TERRIBLE TERROR
        addMobSpawn(event, List.of(Biome.BiomeCategory.BEACH, Biome.BiomeCategory.JUNGLE, Biome.BiomeCategory.DESERT),
                MobCategory.CREATURE, ModEntities.TERRIBLE_TERROR.get(),
                3, 6, 10, 0.10F); // 0.1F
        addMobSpawnOnSpecificBiomes(event, MobCategory.CREATURE, ModEntities.TERRIBLE_TERROR.get(),
                2, 20, 20, 0.10F, Biomes.STONY_SHORE);

        // TODO: TRIPLE STRYKE
        addMobSpawn(event, List.of(Biome.BiomeCategory.MESA, Biome.BiomeCategory.TAIGA, Biome.BiomeCategory.MOUNTAIN), MobCategory.CREATURE,
                ModEntities.TRIPLE_STRYKE.get(),
                2, 1, 2, 0.05F); // 0.08F
        addMobSpawnOnSpecificBiomes(event, MobCategory.CREATURE, ModEntities.TRIPLE_STRYKE.get(),
                2, 1, 1, 0.05F, Biomes.STONY_SHORE);

        // TODO: DEADLY_NADDER
        addMobSpawn(event, List.of(Biome.BiomeCategory.PLAINS, Biome.BiomeCategory.MOUNTAIN, Biome.BiomeCategory.FOREST, Biome.BiomeCategory.SAVANNA, Biome.BiomeCategory.BEACH), MobCategory.CREATURE,
                ModEntities.DEADLY_NADDER.get(),
                3, 3, 5, 0.10F); // 0,035F
        addMobSpawnOnSpecificBiomes(event, MobCategory.CREATURE, ModEntities.DEADLY_NADDER.get(),
                3, 4, 6, 0.10F, Biomes.STONY_SHORE);

        // TODO: GRONCKLE
        addMobSpawn(event, List.of(Biome.BiomeCategory.PLAINS, Biome.BiomeCategory.ICY, Biome.BiomeCategory.FOREST, Biome.BiomeCategory.SWAMP, Biome.BiomeCategory.SAVANNA), MobCategory.CREATURE,
                ModEntities.GRONCKLE.get(),
                3, 3, 5, 0.10F); // 0.035F
        addMobSpawnOnSpecificBiomes(event, MobCategory.CREATURE, ModEntities.GRONCKLE.get(),
                3, 4, 6, 0.10F, Biomes.STONY_SHORE);

        // TODO: NIGHT FURY
        addMobSpawnOnSpecificBiomes(event, MobCategory.CREATURE, ModEntities.NIGHT_FURY.get(),
                2, 1, 1, 0.07F, Biomes.STONY_SHORE, Biomes.FROZEN_PEAKS, Biomes.JAGGED_PEAKS, Biomes.STONY_PEAKS,
                Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_GRAVELLY_HILLS, Biomes.WINDSWEPT_FOREST);

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
