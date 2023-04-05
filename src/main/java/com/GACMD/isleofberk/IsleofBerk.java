package com.GACMD.isleofberk;

import com.GACMD.isleofberk.config.ModConfigs;
import com.GACMD.isleofberk.entity.base.dragon.ADragonBase;
import com.GACMD.isleofberk.entity.dragons.speedstinger.SpeedStinger;
import com.GACMD.isleofberk.registery.*;
import com.GACMD.isleofberk.network.ControlNetwork;
import com.GACMD.isleofberk.event.ClientModEvent;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

@Mod(IsleofBerk.MOD_ID)
public class IsleofBerk
{
    public static final String MOD_ID = "isleofberk";
    private static final Logger LOGGER = LogManager.getLogger();

    public IsleofBerk() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus(),
                forgeBus = MinecraftForge.EVENT_BUS;
        eventBus.addListener(this::setup);

        ModEntities.ENTITIES.register(eventBus);
        ModItems.ITEMS.register(eventBus);
        ModBlocks.BLOCKS.register(eventBus);
        ModParticles.REGISTRAR.register(eventBus);
        ModSounds.SOUND_EVENTS.register(eventBus);
        ModContainerTypes.CONTAINER_TYPES.register(eventBus);
        ModMobEffects.EFFECTS.register(eventBus);

        MinecraftForge.EVENT_BUS.register(this);
        GeckoLib.initialize();
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> new ClientModEvent(eventBus,forgeBus));

        ModConfigs.registerConfigs();
    }

    private void setup(final FMLCommonSetupEvent event) {
        ControlNetwork.init();
//        BlockInit.doBlockSetup();

        event.enqueueWork(() -> {
            SpawnPlacements.register(ModEntities.NIGHT_FURY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ADragonBase::checkAnimalSpawnRules);
            SpawnPlacements.register(ModEntities.LIGHT_FURY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ADragonBase::checkAnimalSpawnRules);
            SpawnPlacements.register(ModEntities.SKRILL.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ADragonBase::checkAnimalSpawnRules);
            SpawnPlacements.register(ModEntities.TRIPLE_STRYKE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ADragonBase::checkAnimalSpawnRules);
            SpawnPlacements.register(ModEntities.DEADLY_NADDER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ADragonBase::checkAnimalSpawnRules);
            SpawnPlacements.register(ModEntities.GRONCKLE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ADragonBase::checkAnimalSpawnRules);
            SpawnPlacements.register(ModEntities.MONSTROUS_NIGHTMARE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ADragonBase::checkAnimalSpawnRules);
            SpawnPlacements.register(ModEntities.ZIPPLEBACK.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ADragonBase::checkAnimalSpawnRules);
            SpawnPlacements.register(ModEntities.TERRIBLE_TERROR.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ADragonBase::checkAnimalSpawnRules);
            SpawnPlacements.register(ModEntities.SPEED_STINGER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpeedStinger::checkSpeedStingerSpawnRules);
            SpawnPlacements.register(ModEntities.STINGER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ADragonBase::checkAnimalSpawnRules);
        });
    }
}
