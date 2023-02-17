package com.GACMD.isleofberk.world.gen;

import com.GACMD.isleofberk.config.ModConfigs;
import com.GACMD.isleofberk.config.util.SpawnDataStorage;
import com.GACMD.isleofberk.registery.ModEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModSpawnRegistration {

    private static final List<SpawnDataStorage> dragonEntries = new ArrayList<>();

    public static void onEntitySpawn(final BiomeLoadingEvent event) {
        if (dragonEntries.isEmpty()) {
            // Stinger
            for (String stingerEntry : ModConfigs.spawnConfig.stingerData.get()) {
                ModSpawnRegistration.addToCacheOrSkip(ModEntities.STINGER.get(), stingerEntry);
            }
            // Terrible Terror
            for (String terribleTerrorEntry : ModConfigs.spawnConfig.terribleTerrorData.get()) {
                ModSpawnRegistration.addToCacheOrSkip(ModEntities.TERRIBLE_TERROR.get(), terribleTerrorEntry);
            }

            // TODO ##################################
            // TODO add loops for the other Dragons
            // TODO ##################################
        }

        // After dragonEntries has been populated we add the Dragons to the Biomes
        ModSpawnRegistration.addDragonSpawns(event);


//        addMobSpawnOnSpecificBiomes(event, MobCategory.CREATURE, ModEntities.TRIPLE_STRYKE.get(), 1, 1, 1, 0.03F, ModTags.Biomes.TRIPLE_STRYKE_BIOMES);
//        addMobSpawnOnSpecificBiomes(event, MobCategory.CREATURE, ModEntities.DEADLY_NADDER.get(), 3, 4, 6, 0.05F, ModTags.Biomes.DEADLY_NADDER_BIOMES);
//        addMobSpawnOnSpecificBiomes(event, MobCategory.CREATURE, ModEntities.GRONCKLE.get(), 3, 4, 6, 0.05F, ModTags.Biomes.GRONCKLE_BIOMES);
//        addMobSpawnOnSpecificBiomes(event, MobCategory.CREATURE, ModEntities.NIGHT_FURY.get(), 1, 1, 1, 0.00005F, ModTags.Biomes.NIGHTFURY_BIOMES);
//        addMobSpawnOnSpecificBiomes(event, MobCategory.CREATURE, ModEntities.LIGHT_FURY.get(), 1, 1, 1, 0.00005F, ModTags.Biomes.LIGHTFURY_BIOMES);
//        addMobSpawnOnSpecificBiomes(event, MobCategory.CREATURE, ModEntities.MONSTROUS_NIGHTMARE.get(), 1, 2, 3, 0.04F, ModTags.Biomes.MONSTROUS_NIGHTMARE_BIOMES);
//        addMobSpawnOnSpecificBiomes(event, MobCategory.CREATURE, ModEntities.ZIPPLEBACK.get(), 1, 2, 3, 0.04F, ModTags.Biomes.HIDEOUS_ZIPPLEBACK_BIOMES);
    }

    private static void addDragonSpawns(BiomeLoadingEvent event)
    {
        ModSpawnRegistration.dragonEntries.forEach(storage -> {
            // If the Biomes contain the currently loading Biome we add the Dragon
            if (event.getName() != null && storage.getBiomes().contains(event.getName().toString())) {
                event.getSpawns().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(storage.getDragon(), storage.getWeight(), storage.getMinCount(), storage.getMaxCount()));
            }
        });
    }

    private static void addToCacheOrSkip(EntityType<?> dragon, String entry) {
        String trimmed = entry.replaceAll(" ", "");
        String[] array = trimmed.split("\\|");
        int weight;
        int minCount;
        int maxCount;
        // Length check
        if (array.length < 4) {
            System.err.println("Isle of Berk spawn config found less than 4 arguments:");
            System.err.println("Dragon: " + dragon.getRegistryName().getPath());
            System.err.println("Entry: " + entry + "\nSkipping...");
            return;
        } else if (array.length > 4) {
            System.err.println("Isle of Berk spawn config found more than 4 arguments:");
            System.err.println("Dragon: " + dragon.getRegistryName().getPath());
            System.err.println("Entry: " + entry + "\nSkipping...");
            return;
        }
        // Weight and Count check
        try {
            weight   = Integer.parseInt(array[0]); // Makes sure the Weight is an int
            minCount = Integer.parseInt(array[1]); // Makes sure the Min Count is an int
            maxCount = Integer.parseInt(array[2]); // Makes sure the Max Count is an int
        } catch (Exception ignored) {
            System.err.println("Isle of Berk spawn config found a value that isn't an integer:");
            System.err.println("Dragon: " + dragon.getRegistryName().getPath());
            System.err.println("Entry: " + entry + "\nSkipping...");
            return;
        }
        // Min needs to be smaller than max
        if(minCount >= maxCount) {
            System.err.println("Isle of Berk spawn config found Min value that is smaller than Max:");
            System.err.println("Dragon: " + dragon.getRegistryName().getPath());
            System.err.println("Entry: " + entry + "\nSkipping...");
            return;
        }
        // Values need to be greater than 0
        if (weight <= 0 || minCount <= 0) { // We don't have to check for maxCount, because the check above ensures it's bigger than minCount
            System.err.println("Isle of Berk spawn config found a value that is smaller or equal to 0:");
            System.err.println("Dragon: " + dragon.getRegistryName().getPath());
            System.err.println("Entry: " + entry + "\nSkipping...");
            return;
        }
        // Check Biome resource location validity
        for(String biome : array[3].split(",")) {
            if (biome.split(":").length != 2) {
                System.err.println("Isle of Berk spawn config found an invalid Biome:");
                System.err.println("Dragon: " + dragon.getRegistryName().getPath());
                System.err.println("Biome: " + biome + "\nSkipping...");
                return;
            }
        }
        // If no checks triggered we add the entry to the cache
        ModSpawnRegistration.dragonEntries.add(new SpawnDataStorage(dragon, weight, minCount, maxCount, Arrays.asList(array[3].split(","))));
    }
}