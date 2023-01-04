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

        USE_LARGER_SCALING = BUILDER.comment("Should use larger dragon scaling?").define("Should use larger dragon scaling?", true);
        N_FURY_SPAWN_CHANCE = BUILDER.comment("Night Fury Spawn Chances Lower decimal numbers means lower spawning").define("Night Fury Spawn Chances Lower decimal numbers means lower spawning?", 0.00005F);
        L_FURY_SPAWN_CHANCE = BUILDER.comment("Light Fury Spawn Chances Lower decimal numbers means lower spawning").define("Light Fury Spawn Chances Lower decimal numbers means lower spawning?", 0.00002F);
        GROCNKLE__SPAWN_CHANCE = BUILDER.comment("Gronckle Spawn Chances Lower decimal numbers means lower spawning").define("Gronckle Spawn Chances Lower decimal numbers means lower spawning?", 0.05F);
        NADDER_SPAWN_CHANCE = BUILDER.comment("Deadly Nadder Spawn Chances Lower decimal numbers means lower spawning").define("DeadlyNadder Spawn Chances Lower decimal numbers means lower spawning?", 0.05F);
        ZIPP_SPAWN_CHANCE = BUILDER.comment("Zippleback Spawn Chances Lower decimal numbers means lower spawning").define("Zippleback Spawn Chances Lower decimal numbers means lower spawning?", 0.04F);
        NIGHTMARE_SPAWN_CHANCE = BUILDER.comment("Monstrous Nightmare Spawn Chances Lower decimal numbers means lower spawning").define("Monstrous Nightmare Spawn Chances Lower decimal numbers means lower spawning?", 0.04F);
        STRYKE_SPAWN_CHANCE = BUILDER.comment("Triple Stryke Spawn Chances Lower decimal numbers means lower spawning").define("Triple Stryke Spawn Chances Lower decimal numbers means lower spawning?", 0.03F);
        TERROR_SPAWN_CHANCE = BUILDER.comment("Terrible Terror Spawn Chances Lower decimal numbers means lower spawning").define("Terrible Terror Spawn Chances Lower decimal numbers means lower spawning?", 0.06F);
        SPEED_STINGER_SPAWN_CHANCE = BUILDER.comment("Speed Stinger Spawn Chances Lower decimal numbers means lower spawning").define("Speed Stinger Spawn Chances Lower decimal numbers means lower spawning?", 0.07F);
        STINGER = BUILDER.comment("Stinger Spawn Chances Lower decimal numbers means lower spawning").define("Stinger Spawn Chances Lower decimal numbers means lower spawning?", 0.05F);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
