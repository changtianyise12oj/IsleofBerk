package com.GACMD.isleofberk.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Boolean> USE_LARGER_SCALING;
    public static final ForgeConfigSpec.ConfigValue<Float> N_FURY_SPAWN_PROBABILITY;
    public static final ForgeConfigSpec.ConfigValue<Float> L_FURY_SPAWN_PROBABILITY;
    public static final ForgeConfigSpec.ConfigValue<Float> GROCNKLE__SPAWN_PROBABILITY;
    public static final ForgeConfigSpec.ConfigValue<Float> NADDER_SPAWN_PROBABILITY;
    public static final ForgeConfigSpec.ConfigValue<Float> ZIPP_SPAWN_PROBABILITY;
    public static final ForgeConfigSpec.ConfigValue<Float> NIGHTMARE_SPAWN_PROBABILITY;
    public static final ForgeConfigSpec.ConfigValue<Float> STRYKE_SPAWN_PROBABILITY;
    public static final ForgeConfigSpec.ConfigValue<Float> TERROR_SPAWN_PROBABILITY;
    public static final ForgeConfigSpec.ConfigValue<Float> SPEED_STINGER_SPAWN_PROBABILITY;
    public static final ForgeConfigSpec.ConfigValue<Float> STINGER_SPAWN_PROBABILITY;

    public static final ForgeConfigSpec.ConfigValue<Integer> N_FURY_SPAWN_WEIGHT;
    public static final ForgeConfigSpec.ConfigValue<Integer> L_FURY_SPAWN_WEIGHT;
    public static final ForgeConfigSpec.ConfigValue<Integer> GROCNKLE__SPAWN_WEIGHT;
    public static final ForgeConfigSpec.ConfigValue<Integer> NADDER_SPAWN_WEIGHT;
    public static final ForgeConfigSpec.ConfigValue<Integer> ZIPP_SPAWN_WEIGHT;
    public static final ForgeConfigSpec.ConfigValue<Integer> NIGHTMARE_SPAWN_WEIGHT;
    public static final ForgeConfigSpec.ConfigValue<Integer> STRYKE_SPAWN_WEIGHT;
    public static final ForgeConfigSpec.ConfigValue<Integer> TERROR_SPAWN_WEIGHT;
    public static final ForgeConfigSpec.ConfigValue<Integer> SPEED_STINGER_SPAWN_WEIGHT;
    public static final ForgeConfigSpec.ConfigValue<Integer> STINGER_SPAWN_WEIGHT;

    public static final ForgeConfigSpec.ConfigValue<Integer> SS_STINGER_EGG_DROP_CHANCE;

    static {
        BUILDER.push("Isle of Berk Config");

        USE_LARGER_SCALING = BUILDER.comment("Should use larger dragon scaling?").define("Should use larger dragon scaling?", true);
        N_FURY_SPAWN_PROBABILITY = BUILDER.comment("Night Fury Spawn Chances, Lower decimal numbers means lower spawning").define("Night Fury Spawn Chances, Lower decimal numbers means lower spawning?", 0.00005F);
        L_FURY_SPAWN_PROBABILITY = BUILDER.comment("Light Fury Spawn Chances, Lower decimal numbers means lower spawning").define("Light Fury Spawn Chances, Lower decimal numbers means lower spawning?", 0.00002F);
        GROCNKLE__SPAWN_PROBABILITY = BUILDER.comment("Gronckle Spawn Chances, Lower decimal numbers means lower spawning").define("Gronckle Spawn Chances, Lower decimal numbers means lower spawning?", 0.05F);
        NADDER_SPAWN_PROBABILITY = BUILDER.comment("Deadly Nadder Spawn Chances, Lower decimal numbers means lower spawning").define("DeadlyNadder Spawn Chances, Lower decimal numbers means lower spawning?", 0.05F);
        ZIPP_SPAWN_PROBABILITY = BUILDER.comment("Zippleback Spawn Chances, Lower decimal numbers means lower spawning").define("Zippleback Spawn Chances, Lower decimal numbers means lower spawning?", 0.04F);
        NIGHTMARE_SPAWN_PROBABILITY = BUILDER.comment("Monstrous Nightmare Spawn Chances, Lower decimal numbers means lower spawning").define("Monstrous Nightmare Spawn Chances, Lower decimal numbers means lower spawning?", 0.04F);
        STRYKE_SPAWN_PROBABILITY = BUILDER.comment("Triple Stryke Spawn Chances, Lower decimal numbers means lower spawning").define("Triple Stryke Spawn Chances, Lower decimal numbers means lower spawning?", 0.03F);
        TERROR_SPAWN_PROBABILITY = BUILDER.comment("Terrible Terror Spawn Chances, Lower decimal numbers means lower spawning").define("Terrible Terror Spawn Chances, Lower decimal numbers means lower spawning?", 0.06F);
        SPEED_STINGER_SPAWN_PROBABILITY = BUILDER.comment("Speed Stinger Spawn Chances, Lower decimal numbers means lower spawning").define("Speed Stinger Spawn Chances, Lower decimal numbers means lower spawning?", 0.00008F);
        STINGER_SPAWN_PROBABILITY = BUILDER.comment("Stinger Spawn Chances, Lower decimal numbers means lower spawning").define("Stinger Spawn Chances, Lower decimal numbers means lower spawning?", 0.05F);

        N_FURY_SPAWN_WEIGHT = BUILDER.comment("Night Fury Spawn Weight, Lower weight means lower spawning, weight is defined by the value of this entity to spawn first before another entity").define("Night Fury Spawn Weight, Lower weight means lower spawning, weight is defined by the value of this entity to spawn first before another entity?", 1);
        L_FURY_SPAWN_WEIGHT = BUILDER.comment("Light Fury Spawn Weight, Lower weight means lower spawning, weight is defined by the value of this entity to spawn first before another entity").define("Light Fury Spawn Weight, Lower weight means lower spawning, weight is defined by the value of this entity to spawn first before another entity?", 1);
        GROCNKLE__SPAWN_WEIGHT = BUILDER.comment("Gronckle Spawn Weight, Lower weight means lower spawning, weight is defined by the value of this entity to spawn first before another entity").define("Gronckle Spawn Weight, Lower weight means lower spawning, weight is defined by the value of this entity to spawn first before another entity?", 3);
        NADDER_SPAWN_WEIGHT = BUILDER.comment("Deadly Nadder Spawn Weight, Lower weight means lower spawning, weight is defined by the value of this entity to spawn first before another entity").define("DeadlyNadder Spawn Weight, Lower weight means lower spawning, weight is defined by the value of this entity to spawn first before another entity?", 3);
        ZIPP_SPAWN_WEIGHT = BUILDER.comment("Zippleback Spawn Weight, Lower weight means lower spawning, weight is defined by the value of this entity to spawn first before another entity").define("Zippleback Spawn Weight, Lower weight means lower spawning, weight is defined by the value of this entity to spawn first before another entity?", 1);
        NIGHTMARE_SPAWN_WEIGHT = BUILDER.comment("Monstrous Nightmare Spawn Weight, Lower weight means lower spawning, weight is defined by the value of this entity to spawn first before another entity").define("Monstrous Nightmare Spawn Weight, Lower weight means lower spawning, weight is defined by the value of this entity to spawn first before another entity?", 1);
        STRYKE_SPAWN_WEIGHT = BUILDER.comment("Triple Stryke Spawn Weight, Lower weight means lower spawning, weight is defined by the value of this entity to spawn first before another entity").define("Triple Stryke Spawn Weight, Lower weight means lower spawning, weight is defined by the value of this entity to spawn first before another entity?", 1);
        TERROR_SPAWN_WEIGHT = BUILDER.comment("Terrible Terror Spawn Weight, Lower weight means lower spawning, weight is defined by the value of this entity to spawn first before another entity").define("Terrible Terror Spawn Weight, Lower weight means lower spawning, weight is defined by the value of this entity to spawn first before another entity?", 3);
        SPEED_STINGER_SPAWN_WEIGHT = BUILDER.comment("Speed Stinger Spawn Weight, Lower weight means lower spawning, weight is defined by the value of this entity to spawn first before another entity").define("Speed Stinger Spawn Weight, Lower weight means lower spawning, weight is defined by the value of this entity to spawn first before another entity?", 45);
        STINGER_SPAWN_WEIGHT = BUILDER.comment("Stinger Spawn Weight, Lower weight means lower spawning, weight is defined by the value of this entity to spawn first before another entity").define("Stinger Spawn Weight, Lower weight means lower spawning, weight is defined by the value of this entity to spawn first before another entity?", 1);

        SS_STINGER_EGG_DROP_CHANCE = BUILDER.comment("Speed Stinger egg drop chance, gets a 1 to x chance to drop an egg, higher value maens higher chance, added here since more ss spawning means more chance to drop eggs").define("Speed Stinger egg drop chance, gets a 1 to x chance to drop an egg, higher value maens higher chance, added here since more ss spawning means more chance to drop eggs?", 8);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
