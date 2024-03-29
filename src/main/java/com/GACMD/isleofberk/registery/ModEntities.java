package com.GACMD.isleofberk.registery;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.entity.dragons.deadlynadder.DeadlyNadder;
import com.GACMD.isleofberk.entity.dragons.gronckle.Gronckle;
import com.GACMD.isleofberk.entity.dragons.lightfury.LightFury;
import com.GACMD.isleofberk.entity.dragons.montrous_nightmare.MonstrousNightmare;
import com.GACMD.isleofberk.entity.dragons.nightfury.NightFury;
import com.GACMD.isleofberk.entity.dragons.nightlight.NightLight;
import com.GACMD.isleofberk.entity.dragons.skrill.Skrill;
import com.GACMD.isleofberk.entity.dragons.speedstinger.SpeedStinger;
import com.GACMD.isleofberk.entity.dragons.speedstingerleader.SpeedStingerLeader;
import com.GACMD.isleofberk.entity.dragons.stinger.Stinger;
import com.GACMD.isleofberk.entity.dragons.terrible_terror.TerribleTerror;
import com.GACMD.isleofberk.entity.dragons.triple_stryke.TripleStryke;
import com.GACMD.isleofberk.entity.dragons.zippleback.ZippleBack;
import com.GACMD.isleofberk.entity.eggs.entity.eggs.*;
import com.GACMD.isleofberk.entity.projectile.breath_user.firebreaths.FireBreathProjectile;
import com.GACMD.isleofberk.entity.projectile.breath_user.firebreaths.FireBreathSmallProjectile;
import com.GACMD.isleofberk.entity.projectile.breath_user.poison.ZipBreathProjectile;
import com.GACMD.isleofberk.entity.projectile.breath_user.poison.ZippleBackAOECloud;
import com.GACMD.isleofberk.entity.projectile.breath_user.skrill_lightning.SkrillLightning;
import com.GACMD.isleofberk.entity.projectile.other.nadder_spike.DeadlyNadderSpike;
import com.GACMD.isleofberk.entity.projectile.proj_user.fire_bolt.FireBolt;
import com.GACMD.isleofberk.entity.projectile.proj_user.furybolt.FuryBolt;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    private static final float PIXEL = 0.0625F;

    private ModEntities() {
    }

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, IsleofBerk.MOD_ID);

    /**
     * Dragons
     */
    public static final RegistryObject<EntityType<NightFury>> NIGHT_FURY = ENTITIES.register("night_fury",
            () -> EntityType.Builder.of(NightFury::new, MobCategory.CREATURE).sized(1.8f, 1.8f)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "night_fury").toString()));

    public static final RegistryObject<EntityType<LightFury>> LIGHT_FURY = ENTITIES.register("light_fury",
            () -> EntityType.Builder.of(LightFury::new, MobCategory.CREATURE).sized(1.8f, 1.8f)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "light_fury").toString()));

    public static final RegistryObject<EntityType<NightLight>> NIGHT_LIGHT = ENTITIES.register("night_light",
            () -> EntityType.Builder.of(NightLight::new, MobCategory.CREATURE).sized(1.8f, 1.8f)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "night_light").toString()));

    public static final RegistryObject<EntityType<TripleStryke>> TRIPLE_STRYKE = ENTITIES.register("triple_stryke",
            () -> EntityType.Builder.of(TripleStryke::new, MobCategory.CREATURE).sized(1.8f, 1.8f)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "triple_stryke").toString()));

    public static final RegistryObject<EntityType<Skrill>> SKRILL = ENTITIES.register("skrill",
            () -> EntityType.Builder.of(Skrill::new, MobCategory.CREATURE).sized(1.8f, 1.8f)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "skrill").toString()));

    public static final RegistryObject<EntityType<DeadlyNadder>> DEADLY_NADDER = ENTITIES.register("deadly_nadder",
            () -> EntityType.Builder.of(DeadlyNadder::new, MobCategory.CREATURE).sized(1.8f, 1.8f)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "deadly_nadder").toString()));

    public static final RegistryObject<EntityType<Gronckle>> GRONCKLE = ENTITIES.register("gronckle",
            () -> EntityType.Builder.of(Gronckle::new, MobCategory.CREATURE).sized(1.8f, 1.8f)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "gronckle").toString()));

    public static final RegistryObject<EntityType<MonstrousNightmare>> MONSTROUS_NIGHTMARE = ENTITIES.register("monstrous_nightmare",
            () -> EntityType.Builder.of(MonstrousNightmare::new, MobCategory.CREATURE).sized(2.6f, 2.6f)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "monstrous_nightmare").toString()));

    public static final RegistryObject<EntityType<ZippleBack>> ZIPPLEBACK = ENTITIES.register("zippleback",
            () -> EntityType.Builder.of(ZippleBack::new, MobCategory.CREATURE).sized(2.0f, 2.0f)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "zippleback").toString()));

    public static final RegistryObject<EntityType<SpeedStingerLeader>> SPEED_STINGER_LEADER = ENTITIES.register("speed_stinger_leader",
            () -> EntityType.Builder.of(SpeedStingerLeader::new, MobCategory.CREATURE).sized(1.4f, 1.8f)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "stinger").toString()));

    public static final RegistryObject<EntityType<SpeedStinger>> SPEED_STINGER = ENTITIES.register("speed_stinger",
            () -> EntityType.Builder.of(SpeedStinger::new, MobCategory.CREATURE).sized(1.2f, 1.4f)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "stinger").toString()));

    public static final RegistryObject<EntityType<Stinger>> STINGER = ENTITIES.register("stinger",
            () -> EntityType.Builder.of(Stinger::new, MobCategory.CREATURE).sized(1.2f, 2.2f)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "stinger").toString()));

    public static final RegistryObject<EntityType<TerribleTerror>> TERRIBLE_TERROR = ENTITIES.register("terrible_terror",
            () -> EntityType.Builder.of(TerribleTerror::new, MobCategory.CREATURE).sized(0.8f, 0.3f)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "terrible_terror").toString()));

    /**
     * Projectiles
     */
    public static final RegistryObject<EntityType<FuryBolt>> FURY_BOLT = ENTITIES.register("fury_bolt",
            () -> EntityType.Builder.<FuryBolt>of(FuryBolt::new, MobCategory.MISC).sized(2.2F, 2.2F)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "fury_bolt").toString()));

    public static final RegistryObject<EntityType<SkrillLightning>> SKRILL_LIGHTNING = ENTITIES.register("skrill_lightning",
            () -> EntityType.Builder.<SkrillLightning>of(SkrillLightning::new, MobCategory.MISC).sized(2.2F, 2.2F)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "skrill_lightning").toString()));

    public static final RegistryObject<EntityType<DeadlyNadderSpike>> NADDER_SPIKE = ENTITIES.register("nadder_spike",
            () -> EntityType.Builder.<DeadlyNadderSpike>of(DeadlyNadderSpike::new, MobCategory.MISC).sized(0.7F, 0.7f)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "nadder_spike").toString()));

    public static final RegistryObject<EntityType<FireBolt>> FIRE_BOLT = ENTITIES.register("fire_bolt",
            () -> EntityType.Builder.<FireBolt>of(FireBolt::new, MobCategory.MISC).sized(2.2F, 2.2F)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "fire_bolt").toString()));

    public static final RegistryObject<EntityType<ZipBreathProjectile>> ZIP_POISON = ENTITIES.register("zip_poison",
            () -> EntityType.Builder.<ZipBreathProjectile>of(ZipBreathProjectile::new, MobCategory.MISC).sized(2.2F, 2.2F)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "zip_poison").toString()));

    public static final RegistryObject<EntityType<ZippleBackAOECloud>> ZIP_CLOUD = ENTITIES.register("zip_cloud",
            () -> EntityType.Builder.<ZippleBackAOECloud>of(ZippleBackAOECloud::new, MobCategory.MISC).sized(6F, 6F).
                    clientTrackingRange(10).updateInterval(Integer.MAX_VALUE)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "zip_cloud").toString()));

    public static final RegistryObject<EntityType<FireBreathProjectile>> FIRE_PROJ = ENTITIES.register("fire_proj",
            () -> EntityType.Builder.<FireBreathProjectile>of(FireBreathProjectile::new, MobCategory.MISC).sized(2.2F, 2.2F)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "fire_proj").toString()));

    public static final RegistryObject<EntityType<FireBreathSmallProjectile>> FIRE_SMALL_PROJ = ENTITIES.register("fire_small_proj",
            () -> EntityType.Builder.<FireBreathSmallProjectile>of(FireBreathSmallProjectile::new, MobCategory.MISC).sized(1.5F, 1.5F)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "fire_small_proj").toString()));


    /**
     * Eggs
     */

    // Small
    public static final RegistryObject<EntityType<SpeedStingerEgg>> SPEED_STINGER_EGG = ENTITIES.register("speed_stinger_egg",
            () -> EntityType.Builder.of(SpeedStingerEgg::new, MobCategory.MISC).sized(PIXEL * 5, PIXEL * 7).fireImmune()
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "speed_stinger_egg").toString()));

    public static final RegistryObject<EntityType<TerribleTerrorEgg>> TERRIBLE_TERROR_EGG = ENTITIES.register("terrible_terror_egg",
            () -> EntityType.Builder.of(TerribleTerrorEgg::new, MobCategory.MISC).sized(PIXEL * 2.5F, PIXEL * 3.5F).fireImmune()
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "speed_stinger_egg").toString()));

    // Medium
    public static final RegistryObject<EntityType<NightFuryEgg>> NIGHT_FURY_EGG = ENTITIES.register("night_fury_egg",
            () -> EntityType.Builder.of(NightFuryEgg::new, MobCategory.MISC).sized(PIXEL * 7, PIXEL * 9)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "night_fury_egg").toString()));

    public static final RegistryObject<EntityType<NightLightEgg>> NIGHT_LIGHT_EGG = ENTITIES.register("night_light_egg",
            () -> EntityType.Builder.of(NightLightEgg::new, MobCategory.MISC).sized(PIXEL * 7, PIXEL * 9)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "night_light_egg").toString()));
    public static final RegistryObject<EntityType<LightFuryEgg>> LIGHT_FURY_EGG = ENTITIES.register("light_fury_egg",
            () -> EntityType.Builder.of(LightFuryEgg::new, MobCategory.MISC).sized(PIXEL * 7, PIXEL * 9)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "light_fury_egg").toString()));

    public static final RegistryObject<EntityType<TripleStrykeEgg>> TRIPLE_STRYKE_EGG = ENTITIES.register("triple_stryke_egg",
            () -> EntityType.Builder.of(TripleStrykeEgg::new, MobCategory.MISC).sized(PIXEL * 7, PIXEL * 9)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "triple_stryke_egg").toString()));

    public static final RegistryObject<EntityType<SkrillEgg>> SKRILL_EGG = ENTITIES.register("skrill_egg",
            () -> EntityType.Builder.of(SkrillEgg::new, MobCategory.MISC).sized(PIXEL * 7, PIXEL * 9)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "skrill_egg").toString()));

    public static final RegistryObject<EntityType<DeadlyNadderEgg>> NADDER_EGG = ENTITIES.register("nadder_egg",
            () -> EntityType.Builder.of(DeadlyNadderEgg::new, MobCategory.MISC).sized(PIXEL * 7, PIXEL * 9)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "nadder_egg").toString()));

    public static final RegistryObject<EntityType<GronkleEgg>> GRONCKLE_EGG = ENTITIES.register("gronckle_egg",
            () -> EntityType.Builder.of(GronkleEgg::new, MobCategory.MISC).sized(PIXEL * 7, PIXEL * 9)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "gronckle_egg").toString()));

    // Large
    public static final RegistryObject<EntityType<MonstrousNightmareEgg>> M_NIGHTMARE_EGG = ENTITIES.register("m_nightmare_egg",
            () -> EntityType.Builder.of(MonstrousNightmareEgg::new, MobCategory.MISC).sized(PIXEL * 10, PIXEL * 13)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "m_nightmare_egg").toString()));

    public static final RegistryObject<EntityType<ZippleBackEgg>> ZIPPLEBACK_EGG = ENTITIES.register("zippleback_egg",
            () -> EntityType.Builder.of(ZippleBackEgg::new, MobCategory.MISC).sized(PIXEL * 10, PIXEL * 13)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "zippleback_egg").toString()));

    public static final RegistryObject<EntityType<StingerEgg>> STINGER_EGG = ENTITIES.register("stinger_egg",
            () -> EntityType.Builder.of(StingerEgg::new, MobCategory.MISC).sized(PIXEL * 10, PIXEL * 13)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "stinger_egg").toString()));
}