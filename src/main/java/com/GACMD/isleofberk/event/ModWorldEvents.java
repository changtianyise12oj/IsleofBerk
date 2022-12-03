package com.GACMD.isleofberk.event;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.world.gen.ModOreGeneration;
import com.GACMD.isleofberk.world.gen.ModSpawnRegistration;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = IsleofBerk.MOD_ID)
public class ModWorldEvents {

    @SubscribeEvent
    public static void biomeLoadingEvent(final BiomeLoadingEvent event) {
        ModSpawnRegistration.onEntitySpawn(event);
        ModOreGeneration.generateOres(event);
    }

}
