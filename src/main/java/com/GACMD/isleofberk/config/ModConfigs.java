package com.GACMD.isleofberk.config;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.config.configs.DragonSpawnConfig;
import com.GACMD.isleofberk.config.util.ConfigHelper;
import net.minecraftforge.fml.config.ModConfig;

public class ModConfigs
{
    public static DragonSpawnConfig spawnConfig = null;

    public static void registerConfigs() {
        spawnConfig = ConfigHelper.register(ModConfig.Type.COMMON, DragonSpawnConfig::new, createConfigName("spawns"));
    }

    /**
     * Helper method to make registering Config names easier
     */
    private static String createConfigName(String name) {
        return IsleofBerk.MOD_ID + "-" + name + ".toml";
    }
}