package com.GACMD.isleofberk.config;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.config.configs.DragonHatchTimeConfig;
import com.GACMD.isleofberk.config.configs.DragonSpawnConfig;
import com.GACMD.isleofberk.config.configs.ClientConfig;
import com.GACMD.isleofberk.config.configs.StatsConfig;
import com.GACMD.isleofberk.config.util.ConfigHelper;
import net.minecraftforge.fml.config.ModConfig;

public class ModConfigs
{
    public static DragonSpawnConfig spawnConfig = null;
    public static DragonHatchTimeConfig hatchTimeConfig = null;
    public static ClientConfig clientConfig = null;

    public static StatsConfig statsConfig = null;

    public static void registerConfigs() {
        spawnConfig = ConfigHelper.register(ModConfig.Type.COMMON, DragonSpawnConfig::new, createConfigName("spawns"));
        hatchTimeConfig = ConfigHelper.register(ModConfig.Type.COMMON, DragonHatchTimeConfig::new, createConfigName("hatch-times"));
        clientConfig = ConfigHelper.register(ModConfig.Type.CLIENT, ClientConfig::new, createConfigName("main"));
        statsConfig = ConfigHelper.register(ModConfig.Type.COMMON, StatsConfig::new, createConfigName("stats"));

    }

    /**
     * Helper method to make registering Config names easier
     */
    private static String createConfigName(String name) {
        return IsleofBerk.MOD_ID + "-" + name + ".toml";
    }
}