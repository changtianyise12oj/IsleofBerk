package com.GACMD.isleofberk.event;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.client.gui.DragonInventoryScreen;
import com.GACMD.isleofberk.client.gui.event.DragonCameraEvent;
import com.GACMD.isleofberk.client.gui.event.ModGuiOverlays;
import com.GACMD.isleofberk.client.gui.event.RiderInventoryHandler;
import com.GACMD.isleofberk.common.entity.entities.dragons.deadlynadder.DeadlyNadderRender;
import com.GACMD.isleofberk.common.entity.entities.dragons.gronckle.GronckleRender;
import com.GACMD.isleofberk.common.entity.entities.dragons.nightfury.NightFuryRender;
import com.GACMD.isleofberk.common.entity.entities.dragons.speedstinger.SpeedStingerRender;
import com.GACMD.isleofberk.common.entity.entities.dragons.speedstingerleader.SpeedStingerLeaderRender;
import com.GACMD.isleofberk.common.entity.entities.dragons.stinger.StingerRender;
import com.GACMD.isleofberk.common.entity.entities.dragons.terrible_terror.TerribleTerrorRender;
import com.GACMD.isleofberk.common.entity.entities.dragons.tryiple_stryke.TripleStrykeRenderer;
import com.GACMD.isleofberk.common.entity.entities.eggs.entity.base.large.ADragonLargeEggRenderer;
import com.GACMD.isleofberk.common.entity.entities.eggs.entity.base.medium.MediumEggRenderer;
import com.GACMD.isleofberk.common.entity.entities.eggs.entity.base.small.SmallEggRenderer;
import com.GACMD.isleofberk.common.entity.entities.projectile.ParticleRegistrar;
import com.GACMD.isleofberk.common.entity.entities.projectile.breath_user.firebreaths.FireBreathProjectileRenderer;
import com.GACMD.isleofberk.common.entity.entities.projectile.breath_user.firebreaths.FlameParticle;
import com.GACMD.isleofberk.common.entity.entities.projectile.breath_user.poison.ZipBreathProjectile;
import com.GACMD.isleofberk.common.entity.entities.projectile.breath_user.poison.ZippleBackAOECloud;
import com.GACMD.isleofberk.common.entity.entities.projectile.other.nadder_spike.DeadlyNadderSpikeRenderer;
import com.GACMD.isleofberk.common.entity.entities.projectile.proj_user.fire_bolt.FireBoltParticle;
import com.GACMD.isleofberk.common.entity.entities.projectile.proj_user.fire_bolt.FireBoltRender;
import com.GACMD.isleofberk.common.entity.entities.projectile.proj_user.furybolt.FuryBoltParticle;
import com.GACMD.isleofberk.common.entity.entities.projectile.proj_user.furybolt.FuryBoltRender;
import com.GACMD.isleofberk.registery.ModContainerTypes;
import com.GACMD.isleofberk.registery.ModEntities;
import com.GACMD.isleofberk.registery.ModKeyBinds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = IsleofBerk.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientModEvent {

    public ClientModEvent(final IEventBus modBus, final IEventBus forgeBus) {
        modBus.addListener(this::registerParticleFactories);
    }

    private void registerParticleFactories(final ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particleEngine.register(ParticleRegistrar.FURY_DUST.get(), FuryBoltParticle.FuryBoltParticleProvider::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegistrar.FLAME_TAIL.get(), FireBoltParticle.FireBoltParticleProvider::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegistrar.FLAME.get(), FlameParticle.FlameParticleProvider::new);
    }

    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        // dragons
        event.registerEntityRenderer(ModEntities.NIGHT_FURY.get(), NightFuryRender::new);
        event.registerEntityRenderer(ModEntities.DEADLY_NADDER.get(), DeadlyNadderRender::new);
        event.registerEntityRenderer(ModEntities.TRIPLE_STRYKE.get(), TripleStrykeRenderer::new);
        event.registerEntityRenderer(ModEntities.STINGER.get(), StingerRender::new);
        event.registerEntityRenderer(ModEntities.TERRIBLE_TERROR.get(), TerribleTerrorRender::new);
        event.registerEntityRenderer(ModEntities.GRONCKLE.get(), GronckleRender::new);
        event.registerEntityRenderer(ModEntities.SPEED_STINGER.get(), SpeedStingerRender::new);
        event.registerEntityRenderer(ModEntities.SPEED_STINGER_LEADER.get(), SpeedStingerLeaderRender::new);

        // breath_weapons
        event.registerEntityRenderer(ModEntities.FURY_BOLT.get(), FuryBoltRender::new);
        event.registerEntityRenderer(ModEntities.FIRE_BOLT.get(), FireBoltRender::new);
        event.registerEntityRenderer(ModEntities.NADDER_SPIKE.get(), DeadlyNadderSpikeRenderer::new);
        event.registerEntityRenderer(ModEntities.ZIP_POISON.get(), ZipBreathProjectile.ZipBreathProjectileRenderer::new);
        event.registerEntityRenderer(ModEntities.FIRE_PROJ.get(), FireBreathProjectileRenderer::new);

        // misc
        event.registerEntityRenderer(ModEntities.ZIP_CLOUD.get(), ZippleBackAOECloud.ZippleBackAOECloudRenderer::new);

        // eggs
        // small
        event.registerEntityRenderer(ModEntities.SPEED_STINGER_EGG.get(), SmallEggRenderer::new);
        event.registerEntityRenderer(ModEntities.TERRIBLE_TERROR_EGG.get(), SmallEggRenderer::new);
        //medium
        event.registerEntityRenderer(ModEntities.NIGHT_FURY_EGG.get(), MediumEggRenderer::new);
        event.registerEntityRenderer(ModEntities.LIGHT_FURY_EGG.get(), MediumEggRenderer::new);
        event.registerEntityRenderer(ModEntities.NADDER_EGG.get(), MediumEggRenderer::new);
        event.registerEntityRenderer(ModEntities.GRONCKLE_EGG.get(), MediumEggRenderer::new);
        event.registerEntityRenderer(ModEntities.TRIPLE_STRYKE_EGG.get(), MediumEggRenderer::new);
        // large
        event.registerEntityRenderer(ModEntities.STINGER_EGG.get(), ADragonLargeEggRenderer::new);
        event.registerEntityRenderer(ModEntities.ZIPPLEBACK_EGG.get(), ADragonLargeEggRenderer::new);
        event.registerEntityRenderer(ModEntities.M_NIGHTMARE_EGG.get(), ADragonLargeEggRenderer::new);
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(DragonCameraEvent.class);
        MinecraftForge.EVENT_BUS.register(RiderInventoryHandler.class);
        ModKeyBinds.register(event);
        OverlayRegistry.registerOverlayTop("", ModGuiOverlays.PROJ_BAR_CHARGE);
        OverlayRegistry.registerOverlayTop("", ModGuiOverlays.BREATH_BAR_FUEL);
        event.enqueueWork(() ->
        {
            MenuScreens.register(ModContainerTypes.DRAGON_INV.get(), DragonInventoryScreen::new);
        });
    }
}
//    private void register2dItemModels(Supplier<Minecraft> minecraft,final EntityRenderersEvent.RegisterRenderers event) {
//        ItemRenderer renderer = minecraft.get().getItemRenderer();
//    }

