package com.GACMD.isleofberk.world.gen;

import com.GACMD.isleofberk.registery.ModEntities;
import com.GACMD.isleofberk.registery.ModTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.util.Arrays;
import java.util.Objects;

public class ModSpawnRegistration {


    public static void onEntitySpawn(final BiomeLoadingEvent event) {

        System.out.println("Called onEntitySpawn");

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

    private static void addMobSpawnOnSpecificBiomes(BiomeLoadingEvent event, MobCategory mobCategory, EntityType<?> entityType, int weight, int minGroupSize, int maxGroupSize, float probability, TagKey<Biome> biomes) {
        System.out.println("Event is: " + event.getName());
        System.out.println("Tag is: " + biomes.location());
    }
}
