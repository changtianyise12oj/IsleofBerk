package com.GACMD.isleofberk.config.configs;

import com.GACMD.isleofberk.config.util.ConfigHelper;
import com.GACMD.isleofberk.config.util.ConfigHelper.ConfigValueListener;
import net.minecraftforge.common.ForgeConfigSpec;
import org.lwjgl.system.CallbackI;

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
                        "10 | 1 | 5 | minecraft:swamp, minecraft:forest, minecraft:river",
                        "12 | 2 | 3 | minecraft:plains, minecraft:ocean"
                        ), o -> o instanceof String));
        builder.pop();

        builder.push("Terrible Terror");
        terribleTerrorData = subscriber.subscribe(builder
                .comment(" Usage described at the top.")
                .defineList("terrible_terror_data", List.of(
                        "20 | 3 | 6 | minecraft:desert",
                        "12 | 2 | 4 | minecraft:birch_forest"
                ), o -> o instanceof String));
        builder.pop();

        builder.push("Deadly Nadder");
        deadlyNadderData = subscriber.subscribe(builder
                .comment(" Usage described at the top.")
                .defineList("deadly_nadder_data", List.of(
                        "20 | 3 | 6 | minecraft:desert",
                        "12 | 2 | 4 | minecraft:birch_forest"
                ), o -> o instanceof String));
        builder.pop();

        builder.push("Gronckle");
        gronckleData = subscriber.subscribe(builder
                .comment(" Usage described at the top.")
                .defineList("gronckle_data", List.of(
                        "20 | 3 | 6 | minecraft:desert",
                        "12 | 2 | 4 | minecraft:birch_forest"
                ), o -> o instanceof String));
        builder.pop();

        builder.push("Hideous Zippleback");
        zipplebackData = subscriber.subscribe(builder
                .comment(" Usage described at the top.")
                .defineList("zippleback_data", List.of(
                        "20 | 3 | 6 | minecraft:desert",
                        "12 | 2 | 4 | minecraft:birch_forest"
                ), o -> o instanceof String));
        builder.pop();

        builder.push("Light Fury");
        lightFuryData = subscriber.subscribe(builder
                .comment(" Usage described at the top.")
                .defineList("light_fury_data", List.of(
                        "20 | 3 | 6 | minecraft:desert",
                        "12 | 2 | 4 | minecraft:birch_forest"
                ), o -> o instanceof String));
        builder.pop();

        builder.push("Night Fury");
        nightFuryData = subscriber.subscribe(builder
                .comment(" Usage described at the top.")
                .defineList("night_fury_data", List.of(
                        "20 | 3 | 6 | minecraft:desert",
                        "12 | 2 | 4 | minecraft:birch_forest"
                ), o -> o instanceof String));
        builder.pop();

        builder.push("Monstrous Nightmare");
        nightmareData = subscriber.subscribe(builder
                .comment(" Usage described at the top.")
                .defineList("nightmare_data", List.of(
                        "20 | 3 | 6 | minecraft:desert",
                        "12 | 2 | 4 | minecraft:birch_forest"
                ), o -> o instanceof String));
        builder.pop();

        builder.push("Skrill");
        skrillData = subscriber.subscribe(builder
                .comment(" Usage described at the top.")
                .defineList("skrill_data", List.of(
                        "20 | 3 | 6 | minecraft:desert",
                        "12 | 2 | 4 | minecraft:birch_forest"
                ), o -> o instanceof String));
        builder.pop();

        builder.push("Triple Stryke");
        tripleStrykeData = subscriber.subscribe(builder
                .comment(" Usage described at the top.")
                .defineList("triple_stryke_data", List.of(
                        "20 | 3 | 6 | minecraft:desert",
                        "12 | 2 | 4 | minecraft:birch_forest"
                ), o -> o instanceof String));
        builder.pop();

        // TODO #####################################################
        // TODO add config entries for other dragons and fix values
        // TODO #####################################################
    }
}