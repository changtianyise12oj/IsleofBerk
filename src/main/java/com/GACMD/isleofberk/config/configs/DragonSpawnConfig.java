package com.GACMD.isleofberk.config.configs;

import com.GACMD.isleofberk.config.util.ConfigHelper;
import com.GACMD.isleofberk.config.util.ConfigHelper.ConfigValueListener;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class DragonSpawnConfig
{
    public ConfigValueListener<List<? extends String>> stingerData;
    public ConfigValueListener<List<? extends String>> terribleTerrorData;
    public ConfigValueListener<List<? extends String>> deadlyNadderData;
    public ConfigValueListener<List<? extends String>> gronckleData;
    public ConfigValueListener<List<? extends String>> zipplebackData;
    public ConfigValueListener<List<? extends String>> lightFuryData;
    public ConfigValueListener<List<? extends String>> nightFuryData;
    public ConfigValueListener<List<? extends String>> nightmareData;
    public ConfigValueListener<List<? extends String>> skrillData;
    public ConfigValueListener<List<? extends String>> tripleStrykeData;

    public DragonSpawnConfig(ForgeConfigSpec.Builder builder, ConfigHelper.Subscriber subscriber)
    {
        builder.comment(" Dragon Spawn Config").push("Stinger");
        stingerData = subscriber.subscribe(builder
                .comment("""
                Dragon spawn Biomes and Values can be adjusted using this Config.
                value 1: The spawn Weight, used for the given Biomes.
                value 2: The Minimum Count to spawn.
                value 3: The Maximum Count to spawn.
                value 4: The Biomes to spawn in.
                If there is a different desired value of weight/counts simply add a new list entry.""".indent(1))
                .defineList("stinger_data", List.of(
                        "10 | 2 | 4 | minecraft:sunflower_plains, minecraft:savanna, minecraft:savanna_plateau",
                        "2 | 0 | 2 | minecraft:badlands, minecraft:wooded_badlands, minecraft:eroded_badlands"
                ), o -> o instanceof String));
        builder.pop();

        builder.push("Terrible Terror");
        terribleTerrorData = subscriber.subscribe(builder
                .comment(" Usage described at the top.")
                .defineList("terrible_terror_data", List.of(
                        "1 | 0 | 3 | minecraft:stony_shore, minecraft:river, minecraft:beach",
                        "4 | 2 | 4 | minecraft:jungle, minecraft:bamboo_jungle, minecraft:dark_forest"
                ), o -> o instanceof String));
        builder.pop();

        builder.push("Deadly Nadder");
        deadlyNadderData = subscriber.subscribe(builder
                .comment(" Usage described at the top.")
                .defineList("deadly_nadder_data", List.of(
                        "2 | 2 | 3 | minecraft:snowy_plains, minecraft:meadow",
                        "4 | 2 | 3 | minecraft:windswept_hills, minecraft:windswept_gravelly_hills, minecraft:windswept_forest",
                        "6 | 2 | 4 | minecraft:sparse_jungle, minecraft:savanna, minecraft:savanna_plateau",
                        "9 | 1 | 3 | minecraft:grove, minecraft:forest, minecraft:flower_forest, minecraft:birch_forest, minecraft:old_growth_birch_forest, minecraft:windswept_savanna"
                ), o -> o instanceof String));
        builder.pop();

        builder.push("Gronckle");
        gronckleData = subscriber.subscribe(builder
                .comment(" Usage described at the top.")
                .defineList("gronckle_data", List.of(
                        "2 | 1 | 2 | minecraft:snowy_plains, minecraft:swamp, minecraft:meadow",
                        "5 | 2 | 3 | minecraft:old_growth_birch_forest, minecraft:savanna_plateau, minecraft:windswept_savanna",
                        "6 | 1 | 2 | minecraft:plains, minecraft:grove, minecraft:sunflower_plains, minecraft:savanna"
                ), o -> o instanceof String));
        builder.pop();

        builder.push("Hideous Zippleback");
        zipplebackData = subscriber.subscribe(builder
                .comment(" Usage described at the top.")
                .defineList("zippleback_data", List.of(
                        "6 | 2 | 3 | minecraft:sparse_jungle",
                        "1 | 0 | 1 | minecraft:ice_spikes, minecraft:snowy_plains, minecraft:swamp"
                ), o -> o instanceof String));
        builder.pop();

        builder.push("Light Fury");
        lightFuryData = subscriber.subscribe(builder
                .comment(" Usage described at the top.")
                .defineList("light_fury_data", List.of(
                        "3 | 0 | 1 | minecraft:jagged_peaks, minecraft:frozen_peaks, minecraft:windswept_forest, minecraft:birch_forest, minecraft:old_growth_birch_forest"
                ), o -> o instanceof String));
        builder.pop();

        builder.push("Night Fury");
        nightFuryData = subscriber.subscribe(builder
                .comment(" Usage described at the top.")
                .defineList("night_fury_data", List.of(
                        "2 | 0 | 1 | minecraft:snowy_slopes, minecraft:jagged_peaks, minecraft:frozen_peaks, minecraft:windswept_hills, minecraft:windswept_gravelly_hills, minecraft:stony_peaks"
                ), o -> o instanceof String));
        builder.pop();

        builder.push("Monstrous Nightmare");
        nightmareData = subscriber.subscribe(builder
                .comment(" Usage described at the top.")
                .defineList("nightmare_data", List.of(
                        "1 | 0 | 1 | minecraft:badlands, minecraft:wooded_badlands, minecraft:eroded_badlands",
                        "1 | 0 | 1 | minecraft:windswept_hills, minecraft:windswept_gravelly_hills, minecraft:windswept_forest"
                ), o -> o instanceof String));
        builder.pop();

        builder.push("Skrill");
        skrillData = subscriber.subscribe(builder
                .comment(" Usage described at the top.")
                .defineList("skrill_data", List.of(
                        "5 | 0 | 1 | minecraft:snowy_slopes, minecraft:jagged_peaks, minecraft:frozen_peaks, minecraft:stony_peaks"
                ), o -> o instanceof String));
        builder.pop();

        builder.push("Triple Stryke");
        tripleStrykeData = subscriber.subscribe(builder
                .comment(" Usage described at the top.")
                .defineList("triple_stryke_data", List.of(
                        "1 | 0 | 2 | minecraft:wooded_badlands, minecraft:badlands",
                        "5 | 1 | 3 | minecraft:snowy_taiga, minecraft:taiga, minecraft:old_growth_pine_taiga, minecraft:old_growth_spruce_taiga"
                ), o -> o instanceof String));
        builder.pop();
    }
}