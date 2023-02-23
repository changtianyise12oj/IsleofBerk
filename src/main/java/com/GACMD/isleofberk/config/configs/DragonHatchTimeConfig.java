package com.GACMD.isleofberk.config.configs;

import com.GACMD.isleofberk.config.util.ConfigHelper;
import com.GACMD.isleofberk.config.util.ConfigHelper.ConfigValueListener;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class DragonHatchTimeConfig
{
    public ConfigValueListener<Integer> terribleTerror;
    public ConfigValueListener<Integer> speedStinger;
    public ConfigValueListener<Integer> floutscoutSpeedStinger;
    public ConfigValueListener<Integer> iceBreakerSpeedStinger;
    public ConfigValueListener<Integer> sweetStingSpeedStinger;
    public ConfigValueListener<Integer> deadlyNadder;
    public ConfigValueListener<Integer> nightFury;
    public ConfigValueListener<Integer> lightFury;
    public ConfigValueListener<Integer> gronckle;
    public ConfigValueListener<Integer> tripleStryke;
    public ConfigValueListener<Integer> stinger;
    public ConfigValueListener<Integer> zippleback;
    public ConfigValueListener<Integer> nightmare;
//    public ConfigValueListener<Integer> skrillData;

    public DragonHatchTimeConfig(ForgeConfigSpec.Builder builder, ConfigHelper.Subscriber subscriber)
    {
        builder.comment(" Dragon Hatch Time Config").push("Terrible Terror");
        terribleTerror = subscriber.subscribe(builder
                .comment(" The amount of Ticks it takes for the Dragon to hatch. (20 ticks = 1 second)")
                .defineInRange("terrible_terror_time", 200, 1, 1728000));
        builder.pop();
        builder.push("Speed Stinger");
        speedStinger = subscriber.subscribe(builder
                .comment(" The amount of Ticks it takes for the Dragon to hatch. (20 ticks = 1 second)")
                .defineInRange("speed_stinger_time", 200, 1, 1728000));
        builder.pop();
        builder.push("Floutscout Speed Stinger");
        floutscoutSpeedStinger = subscriber.subscribe(builder
                .comment(" The amount of Ticks it takes for the Dragon to hatch. (20 ticks = 1 second)")
                .defineInRange("floutscout_speed_stinger_time", 200, 1, 1728000));
        builder.pop();
        builder.push("Ice Breaker Speed Stinger");
        iceBreakerSpeedStinger = subscriber.subscribe(builder
                .comment(" The amount of Ticks it takes for the Dragon to hatch. (20 ticks = 1 second)")
                .defineInRange("ice_breaker_speed_stinger_time", 200, 1, 1728000));
        builder.pop();
        builder.push("Sweet Sting Speed Stinger");
        sweetStingSpeedStinger = subscriber.subscribe(builder
                .comment(" The amount of Ticks it takes for the Dragon to hatch. (20 ticks = 1 second)")
                .defineInRange("sweet_stinger_speed_stinger_time", 200, 1, 1728000));
        builder.pop();
        builder.push("Deadly Nadder");
        deadlyNadder = subscriber.subscribe(builder
                .comment(" The amount of Ticks it takes for the Dragon to hatch. (20 ticks = 1 second)")
                .defineInRange("deadly_nadder_time", 200, 1, 1728000));
        builder.pop();
        builder.push("Night Fury");
        nightFury = subscriber.subscribe(builder
                .comment(" The amount of Ticks it takes for the Dragon to hatch. (20 ticks = 1 second)")
                .defineInRange("night_fury_time", 200, 1, 1728000));
        builder.pop();
        builder.push("Light Fury");
        lightFury = subscriber.subscribe(builder
                .comment(" The amount of Ticks it takes for the Dragon to hatch. (20 ticks = 1 second)")
                .defineInRange("light_fury_time", 200, 1, 1728000));
        builder.pop();
        builder.push("Gronckle");
        gronckle = subscriber.subscribe(builder
                .comment(" The amount of Ticks it takes for the Dragon to hatch. (20 ticks = 1 second)")
                .defineInRange("gronckle_time", 200, 1, 1728000));
        builder.pop();
        builder.push("Triple Stryke");
        tripleStryke = subscriber.subscribe(builder
                .comment(" The amount of Ticks it takes for the Dragon to hatch. (20 ticks = 1 second)")
                .defineInRange("triple_stryke_time", 200, 1, 1728000));
        builder.pop();
        builder.push("Stinger");
        stinger = subscriber.subscribe(builder
                .comment(" The amount of Ticks it takes for the Dragon to hatch. (20 ticks = 1 second)")
                .defineInRange("stinger_time", 200, 1, 1728000));
        builder.pop();
        builder.push("Zippleback");
        zippleback = subscriber.subscribe(builder
                .comment(" The amount of Ticks it takes for the Dragon to hatch. (20 ticks = 1 second)")
                .defineInRange("zippleback_time", 200, 1, 1728000));
        builder.pop();
        builder.push("Nightmare");
        nightmare = subscriber.subscribe(builder
                .comment(" The amount of Ticks it takes for the Dragon to hatch. (20 ticks = 1 second)")
                .defineInRange("nightmare_time", 200, 1, 1728000));
        builder.pop();
    }
}