package com.GACMD.isleofberk.config;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Boolean> USE_LARGER_SCALING;
    public static final ForgeConfigSpec.ConfigValue<Float> N_FURY_SPAWN_CHANCE;
    public static final ForgeConfigSpec.ConfigValue<Float> L_FURY_SPAWN_CHANCE;
    public static final ForgeConfigSpec.ConfigValue<Float> GROCNKLE__SPAWN_CHANCE;
    public static final ForgeConfigSpec.ConfigValue<Float> NADDER_SPAWN_CHANCE;
    public static final ForgeConfigSpec.ConfigValue<Float> ZIPP_SPAWN_CHANCE;
    public static final ForgeConfigSpec.ConfigValue<Float> NIGHTMARE_SPAWN_CHANCE;
    public static final ForgeConfigSpec.ConfigValue<Float> STRYKE_SPAWN_CHANCE;
    public static final ForgeConfigSpec.ConfigValue<Float> TERROR_SPAWN_CHANCE;
    public static final ForgeConfigSpec.ConfigValue<Float> SPEED_STINGER_SPAWN_CHANCE;
    public static final ForgeConfigSpec.ConfigValue<Float> STINGER;

    static {
        BUILDER.push("Isle of Berk Config");

        USE_LARGER_SCALING = BUILDER.comment("Should use larger dragon scales?").define("Should use larger dragon scales?", true);
        N_FURY_SPAWN_CHANCE = BUILDER.comment("Night Fury Spawn Chance").define("Night Fury Spawn Chance?", 0.05F);
        L_FURY_SPAWN_CHANCE = BUILDER.comment("Light Fury Spawn Chance").define("Light Fury Spawn Chance?", 0.05F);
        GROCNKLE__SPAWN_CHANCE = BUILDER.comment("Gronckle Spawn Chance").define("Gronckle Spawn Chance?", 0.05F);
        NADDER_SPAWN_CHANCE = BUILDER.comment("Deadly Nadder Spawn Chance").define("DeadlyNadder Spawn Chance?", 0.05F);
        ZIPP_SPAWN_CHANCE = BUILDER.comment("Zippleback Spawn Chance").define("Zippleback Spawn Chance?", 0.04F);
        NIGHTMARE_SPAWN_CHANCE = BUILDER.comment("Monstrous Nightmare Spawn Chance").define("Monstrous Nightmare Spawn Chance?", 0.04F);
        STRYKE_SPAWN_CHANCE = BUILDER.comment("Triple Stryke Spawn Chance").define("Triple Stryke Spawn Chance?", 0.03F);
        TERROR_SPAWN_CHANCE = BUILDER.comment("Terrible Terror Spawn Chance").define("Terrible Terror Spawn Chance?", 0.06F);
        SPEED_STINGER_SPAWN_CHANCE = BUILDER.comment("Speed Stinger Spawn Chance").define("Speed Stinger Spawn Chance?", 0.08F);
        STINGER = BUILDER.comment("Stinger Spawn Chance").define("Stinger Spawn Chance?", 0.05F);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
