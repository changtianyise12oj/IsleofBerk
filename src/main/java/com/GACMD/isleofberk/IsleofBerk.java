package com.GACMD.isleofberk;

import com.GACMD.isleofberk.common.entity.entities.base.ADragonBase;
import com.GACMD.isleofberk.common.entity.entities.dragons.speedstinger.SpeedStinger;
import com.GACMD.isleofberk.common.entity.entities.projectile.ParticleRegistrar;
import com.GACMD.isleofberk.common.entity.network.ControlNetwork;
import com.GACMD.isleofberk.common.entity.sound.IOBSounds;
import com.GACMD.isleofberk.event.ClientModEvent;
import com.GACMD.isleofberk.registery.ModBlocks;
import com.GACMD.isleofberk.registery.ModContainerTypes;
import com.GACMD.isleofberk.registery.ModEntities;
import com.GACMD.isleofberk.registery.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(IsleofBerk.MOD_ID)
public class IsleofBerk // /kill @e[type=!isleofberk:stinger,type=! player]
{
    // Directly reference a log4j logger.
    public static final String MOD_ID = "isleofberk";
    private static final Logger LOGGER = LogManager.getLogger();

    public IsleofBerk() {
        // Register the setup method for modloading
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus(),
                forgeBus = MinecraftForge.EVENT_BUS;
        eventBus.addListener(this::setup);

        ModEntities.ENTITIES.register(eventBus);
        ModItems.ITEMS.register(eventBus);
        ModBlocks.BLOCKS.register(eventBus);
        ParticleRegistrar.REGISTRAR.register(eventBus);
        IOBSounds.SOUND_EVENTS.register(eventBus);
        ModContainerTypes.CONTAINER_TYPES.register(eventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        GeckoLib.initialize();
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> new ClientModEvent(eventBus,forgeBus));
    }

    private void setup(final FMLCommonSetupEvent event) {
        ControlNetwork.init();
//        BlockInit.doBlockSetup();

        event.enqueueWork(() -> {
            SpawnPlacements.register(ModEntities.STINGER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ADragonBase::checkAnimalSpawnRules);
            SpawnPlacements.register(ModEntities.SPEED_STINGER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpeedStinger::checkSpeedStingerSpawnRules);
            SpawnPlacements.register(ModEntities.NIGHT_FURY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ADragonBase::checkAnimalSpawnRules);
            SpawnPlacements.register(ModEntities.DEADLY_NADDER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ADragonBase::checkAnimalSpawnRules);
            SpawnPlacements.register(ModEntities.TRIPLE_STRYKE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ADragonBase::checkAnimalSpawnRules);
            SpawnPlacements.register(ModEntities.TERRIBLE_TERROR.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ADragonBase::checkAnimalSpawnRules);
            SpawnPlacements.register(ModEntities.GRONCKLE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ADragonBase::checkAnimalSpawnRules);
        });
    }
}
