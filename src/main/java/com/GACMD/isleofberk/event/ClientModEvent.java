package com.GACMD.isleofberk.event;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.entity.dragons.nightlight.NightLightRender;
import com.GACMD.isleofberk.entity.dragons.skrill.SkrillRenderer;
import com.GACMD.isleofberk.entity.projectile.breath_user.firebreaths.FireBreathSmallProjectileRenderer;
import com.GACMD.isleofberk.entity.projectile.breath_user.skrill_lightning.LightningRender;
import com.GACMD.isleofberk.particles.*;
import com.GACMD.isleofberk.gui.DragonInventoryScreen;
import com.GACMD.isleofberk.gui.event.DragonCameraEvent;
import com.GACMD.isleofberk.gui.event.ModGuiOverlays;
import com.GACMD.isleofberk.gui.event.RiderInventoryHandler;
import com.GACMD.isleofberk.entity.dragons.deadlynadder.DeadlyNadderRender;
import com.GACMD.isleofberk.entity.dragons.gronckle.GronckleRender;
import com.GACMD.isleofberk.entity.dragons.lightfury.LightFuryRender;
import com.GACMD.isleofberk.entity.dragons.montrous_nightmare.MonstrousNightmareRender;
import com.GACMD.isleofberk.entity.dragons.nightfury.NightFuryRender;
import com.GACMD.isleofberk.entity.dragons.speedstinger.SpeedStingerRender;
import com.GACMD.isleofberk.entity.dragons.speedstingerleader.SpeedStingerLeaderRender;
import com.GACMD.isleofberk.entity.dragons.stinger.StingerRender;
import com.GACMD.isleofberk.entity.dragons.terrible_terror.TerribleTerrorRender;
import com.GACMD.isleofberk.entity.dragons.triple_stryke.TripleStrykeRenderer;
import com.GACMD.isleofberk.entity.dragons.zippleback.ZippleBackRenderer;
import com.GACMD.isleofberk.entity.eggs.entity.base.large.ADragonLargeEggRenderer;
import com.GACMD.isleofberk.entity.eggs.entity.base.medium.MediumEggRenderer;
import com.GACMD.isleofberk.entity.eggs.entity.base.small.SmallEggRenderer;
import com.GACMD.isleofberk.registery.ModParticles;
import com.GACMD.isleofberk.entity.projectile.breath_user.firebreaths.FireBreathProjectileRenderer;
import com.GACMD.isleofberk.entity.projectile.breath_user.poison.ZipBreathProjectile;
import com.GACMD.isleofberk.entity.projectile.breath_user.poison.ZippleBackAOECloud;
import com.GACMD.isleofberk.entity.projectile.other.nadder_spike.DeadlyNadderSpikeRenderer;
import com.GACMD.isleofberk.entity.projectile.proj_user.fire_bolt.FireBoltRender;
import com.GACMD.isleofberk.entity.projectile.proj_user.furybolt.FuryBoltRender;
import com.GACMD.isleofberk.registery.ModContainerTypes;
import com.GACMD.isleofberk.registery.ModEntities;
import com.GACMD.isleofberk.registery.ModKeyBinds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.particle.HugeExplosionSeedParticle;
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
        Minecraft.getInstance().particleEngine.register(ModParticles.NIGHT_FURY_DUST.get(), FuryBoltParticle.FuryBoltParticleProvider::new);
        Minecraft.getInstance().particleEngine.register(ModParticles.LIGHT_FURY_DUST.get(), FuryBoltParticle.FuryBoltParticleProvider::new);
        Minecraft.getInstance().particleEngine.register(ModParticles.SKRILL_SKILL_PARTICLES.get(), SkrillSkillParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ModParticles.SKRILL_LIGHTNING_PARTICLES.get(), SkrillLightningParticle.SkrillLightningParticleProvider::new);
        Minecraft.getInstance().particleEngine.register(ModParticles.FLAME_TAIL.get(), FireBoltParticle.FireBoltParticleProvider::new);
        Minecraft.getInstance().particleEngine.register(ModParticles.FLAME.get(), FlameParticle.FlameParticleProvider::new);
        Minecraft.getInstance().particleEngine.register(ModParticles.FIRE_COAT.get(), FireCoatParticle.FireCoatParticleProvider::new);
        Minecraft.getInstance().particleEngine.register(ModParticles.GAS.get(), GasParticle.GasParticleProvider::new);
        Minecraft.getInstance().particleEngine.register(ModParticles.LIGHTNING_AOE_EMITTER.get(), new LightningAoEEmitter.Provider());
        Minecraft.getInstance().particleEngine.register(ModParticles.GAS_AOE_EMITTER.get(), new GasAoEEmitter.Provider());
    }

    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        /**
         * Dragons
         */
        event.registerEntityRenderer(ModEntities.NIGHT_FURY.get(), NightFuryRender::new);
        event.registerEntityRenderer(ModEntities.LIGHT_FURY.get(), LightFuryRender::new);
        event.registerEntityRenderer(ModEntities.NIGHT_LIGHT.get(), NightLightRender::new);
        event.registerEntityRenderer(ModEntities.SKRILL.get(), SkrillRenderer::new);
        event.registerEntityRenderer(ModEntities.TRIPLE_STRYKE.get(), TripleStrykeRenderer::new);
        event.registerEntityRenderer(ModEntities.DEADLY_NADDER.get(), DeadlyNadderRender::new);
        event.registerEntityRenderer(ModEntities.GRONCKLE.get(), GronckleRender::new);
        event.registerEntityRenderer(ModEntities.MONSTROUS_NIGHTMARE.get(), MonstrousNightmareRender::new);
        event.registerEntityRenderer(ModEntities.ZIPPLEBACK.get(), ZippleBackRenderer::new);
        event.registerEntityRenderer(ModEntities.TERRIBLE_TERROR.get(), TerribleTerrorRender::new);
        event.registerEntityRenderer(ModEntities.SPEED_STINGER_LEADER.get(), SpeedStingerLeaderRender::new);
        event.registerEntityRenderer(ModEntities.SPEED_STINGER.get(), SpeedStingerRender::new);
        event.registerEntityRenderer(ModEntities.STINGER.get(), StingerRender::new);

        /**
         * Dragon Breathe
         */
        event.registerEntityRenderer(ModEntities.FURY_BOLT.get(), FuryBoltRender::new);
        event.registerEntityRenderer(ModEntities.SKRILL_LIGHTNING.get(), LightningRender::new);
        event.registerEntityRenderer(ModEntities.FIRE_BOLT.get(), FireBoltRender::new);
        event.registerEntityRenderer(ModEntities.NADDER_SPIKE.get(), DeadlyNadderSpikeRenderer::new);
        event.registerEntityRenderer(ModEntities.ZIP_POISON.get(), ZipBreathProjectile.ZipBreathProjectileRenderer::new);
        event.registerEntityRenderer(ModEntities.FIRE_PROJ.get(), FireBreathProjectileRenderer::new);
        event.registerEntityRenderer(ModEntities.FIRE_SMALL_PROJ.get(), FireBreathSmallProjectileRenderer::new);

        /**
         * Misc
         */
        event.registerEntityRenderer(ModEntities.ZIP_CLOUD.get(), ZippleBackAOECloud.ZippleBackAOECloudRenderer::new);

        /**
         * Eggs
         */
        // Small
        event.registerEntityRenderer(ModEntities.SPEED_STINGER_EGG.get(), SmallEggRenderer::new);
        event.registerEntityRenderer(ModEntities.TERRIBLE_TERROR_EGG.get(), SmallEggRenderer::new);
        // Medium
        event.registerEntityRenderer(ModEntities.NIGHT_FURY_EGG.get(), MediumEggRenderer::new);
        event.registerEntityRenderer(ModEntities.NIGHT_LIGHT_EGG.get(), MediumEggRenderer::new);
        event.registerEntityRenderer(ModEntities.LIGHT_FURY_EGG.get(), MediumEggRenderer::new);
        event.registerEntityRenderer(ModEntities.NADDER_EGG.get(), MediumEggRenderer::new);
        event.registerEntityRenderer(ModEntities.GRONCKLE_EGG.get(), MediumEggRenderer::new);
        event.registerEntityRenderer(ModEntities.TRIPLE_STRYKE_EGG.get(), MediumEggRenderer::new);
        event.registerEntityRenderer(ModEntities.SKRILL_EGG.get(), MediumEggRenderer::new);
        // Large
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

