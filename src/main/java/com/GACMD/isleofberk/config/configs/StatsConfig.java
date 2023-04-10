package com.GACMD.isleofberk.config.configs;

import com.GACMD.isleofberk.config.util.ConfigHelper;
import net.minecraftforge.common.ForgeConfigSpec;

public class StatsConfig {

    // Nadder
    public ConfigHelper.ConfigValueListener<Double> nadderHealth;
    public ConfigHelper.ConfigValueListener<Double> nadderArmor;
    public ConfigHelper.ConfigValueListener<Double> nadderBite;
    public ConfigHelper.ConfigValueListener<Integer> nadderBreathCapacity;
    public ConfigHelper.ConfigValueListener<Integer> nadderBreathRegenSpeed;
    public ConfigHelper.ConfigValueListener<Integer> nadderBreathRegenAmount;
    public ConfigHelper.ConfigValueListener<Double> nadderSpikes;
    // Chonker
    public ConfigHelper.ConfigValueListener<Double> groncleHealth;
    public ConfigHelper.ConfigValueListener<Double> groncleArmor;
    public ConfigHelper.ConfigValueListener<Double> groncleBite;
    //Light Fury
    public ConfigHelper.ConfigValueListener<Double> lightFuryHealth;
    public ConfigHelper.ConfigValueListener<Double> lightFuryArmor;
    public ConfigHelper.ConfigValueListener<Double> lightFuryBite;
    //Nightmare
    public ConfigHelper.ConfigValueListener<Double> nightmareHealth;
    public ConfigHelper.ConfigValueListener<Double> nightmareArmor;
    public ConfigHelper.ConfigValueListener<Double> nightmareBite;
    public ConfigHelper.ConfigValueListener<Integer> nightmareBreathCapacity;
    public ConfigHelper.ConfigValueListener<Integer> nightmareBreathRegenSpeed;
    public ConfigHelper.ConfigValueListener<Integer> nightmareBreathRegenAmount;
    //Night Fury
    public ConfigHelper.ConfigValueListener<Double> nightFuryHealth;
    public ConfigHelper.ConfigValueListener<Double> nightFuryArmor;
    public ConfigHelper.ConfigValueListener<Double> nightFuryBite;
    //Night Light
    public ConfigHelper.ConfigValueListener<Double> nightLightHealth;
    public ConfigHelper.ConfigValueListener<Double> nightLightArmor;
    public ConfigHelper.ConfigValueListener<Double> nightLightBite;
    // Skrill
    public ConfigHelper.ConfigValueListener<Double> skrillHealth;
    public ConfigHelper.ConfigValueListener<Double> skrillArmor;
    public ConfigHelper.ConfigValueListener<Double> skrillBite;
    public ConfigHelper.ConfigValueListener<Integer> skrillBreathCapacity;
    public ConfigHelper.ConfigValueListener<Integer> skrillBreathRegenSpeed;
    public ConfigHelper.ConfigValueListener<Integer> skrillBreathRegenAmount;
    //Speed Stinger
    public ConfigHelper.ConfigValueListener<Double> sStingerHealth;
    public ConfigHelper.ConfigValueListener<Double> sStingerArmor;
    public ConfigHelper.ConfigValueListener<Double> sStingerBite;

    //Speed Stinger
    public ConfigHelper.ConfigValueListener<Double> sStingerLeadHealth;
    public ConfigHelper.ConfigValueListener<Double> sStingerLeadArmor;
    public ConfigHelper.ConfigValueListener<Double> sStingerLeadBite;

    public StatsConfig(ForgeConfigSpec.Builder builder, ConfigHelper.Subscriber subscriber) {
        builder.comment(" Dragon Attributes Config");

        // Nadder
        builder.push("Deadly Nadder");
        nadderHealth = subscriber.subscribe(builder.defineInRange("Health", 80D, 1, 10000));
        nadderArmor = subscriber.subscribe(builder.defineInRange("Armor", 2D, 0, 10000));
        nadderBite = subscriber.subscribe(builder.defineInRange("Bite", 80D, 1, 10000));
        nadderBreathCapacity = subscriber.subscribe(builder.defineInRange("Breath Capacity", 280, 1, 10000));
        nadderBreathRegenSpeed = subscriber.subscribe(builder.defineInRange("Breath Regen Speed", 30, 1, 10000));
        nadderBreathRegenAmount = subscriber.subscribe(builder.defineInRange("Breath Regen Amount", 32, 1, 10000));
        nadderSpikes = subscriber.subscribe(builder.defineInRange("Spikes Damage", 6D, 1, 10000));
        builder.pop();

        // Gronckle
        builder.push("Gronckle");
        groncleHealth = subscriber.subscribe(builder.defineInRange("Health", 100.0D, 1, 10000));
        groncleArmor = subscriber.subscribe(builder.defineInRange("Armor", 8D, 0, 10000));
        groncleBite = subscriber.subscribe(builder.defineInRange("Bite", 8D, 1, 10000));
        builder.pop();

        // Light Fury
        builder.push("Light Fury");
        lightFuryHealth = subscriber.subscribe(builder.defineInRange("Health", 80.0D, 1, 10000));
        lightFuryArmor = subscriber.subscribe(builder.defineInRange("Armor", 0.5D, 0, 10000));
        lightFuryBite = subscriber.subscribe(builder.defineInRange("Bite", 6D, 1, 10000));
        builder.pop();

        // Nightmare
        builder.push("Nightmare");
        nightmareHealth = subscriber.subscribe(builder.defineInRange("Health", 200.0D, 1, 10000));
        nightmareArmor = subscriber.subscribe(builder.defineInRange("Armor", 6D, 0, 10000));
        nightmareBite = subscriber.subscribe(builder.defineInRange("Bite", 10D, 1, 10000));
        nightmareBreathCapacity = subscriber.subscribe(builder.defineInRange("Breath Capacity", 350, 1, 10000));
        nightmareBreathRegenSpeed = subscriber.subscribe(builder.defineInRange("Breath Regen Speed", 30, 1, 10000));
        nightmareBreathRegenAmount = subscriber.subscribe(builder.defineInRange("Breath Regen Amount", 4, 1, 10000));
        builder.pop();

        // Night Fury
        builder.push("Night Fury");
        nightFuryHealth = subscriber.subscribe(builder.defineInRange("Health", 100.0D, 1, 10000));
        nightFuryArmor = subscriber.subscribe(builder.defineInRange("Armor", 1D, 0, 10000));
        nightFuryBite = subscriber.subscribe(builder.defineInRange("Bite", 8D, 1, 10000));
        builder.pop();

        // Night Light
        builder.push("Night Light");
        nightLightHealth = subscriber.subscribe(builder.defineInRange("Health", 100.0D, 1, 10000));
        nightLightArmor = subscriber.subscribe(builder.defineInRange("Armor", 1D, 0, 10000));
        nightLightBite = subscriber.subscribe(builder.defineInRange("Bite", 8D, 1, 10000));
        builder.pop();

        // Skrill
        builder.push("Skrill");
        skrillHealth = subscriber.subscribe(builder.defineInRange("Health", 100.0D, 1, 10000));
        skrillArmor = subscriber.subscribe(builder.defineInRange("Armor", 4D, 0, 10000));
        skrillBite = subscriber.subscribe(builder.defineInRange("Bite", 6D, 1, 10000));
        skrillBreathCapacity = subscriber.subscribe(builder.defineInRange("Breath Capacity", 350, 1, 10000));
        skrillBreathRegenSpeed = subscriber.subscribe(builder.defineInRange("Breath Regen Speed", 30, 1, 10000));
        skrillBreathRegenAmount = subscriber.subscribe(builder.defineInRange("Breath Regen Amount", 4, 1, 10000));
        builder.pop();

        // Speed Stinger
        builder.push("Speed Stinger");
        sStingerHealth = subscriber.subscribe(builder.defineInRange("Health", 30.0D, 1, 10000));
        sStingerArmor = subscriber.subscribe(builder.defineInRange("Armor", 1D, 0, 10000));
        sStingerBite = subscriber.subscribe(builder.defineInRange("Bite", 6D, 1, 10000));
        builder.pop();

        // Speed Stinger Leader
        builder.push("Speed Stinger Leader");
        sStingerLeadHealth = subscriber.subscribe(builder.defineInRange("Health", 40.0D, 1, 10000));
        sStingerLeadArmor = subscriber.subscribe(builder.defineInRange("Armor", 3D, 0, 10000));
        sStingerLeadBite = subscriber.subscribe(builder.defineInRange("Bite", 10D, 1, 10000));
        builder.pop();
    }
}
