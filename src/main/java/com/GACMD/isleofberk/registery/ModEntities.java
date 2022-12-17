package com.GACMD.isleofberk.registery;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.common.entity.entities.dragons.deadlynadder.DeadlyNadder;
import com.GACMD.isleofberk.common.entity.entities.dragons.gronckle.Gronckle;
import com.GACMD.isleofberk.common.entity.entities.dragons.lightfury.LightFury;
import com.GACMD.isleofberk.common.entity.entities.dragons.montrous_nightmare.MonstrousNightmare;
import com.GACMD.isleofberk.common.entity.entities.dragons.nightfury.NightFury;
import com.GACMD.isleofberk.common.entity.entities.dragons.speedstinger.SpeedStinger;
import com.GACMD.isleofberk.common.entity.entities.dragons.speedstingerleader.SpeedStingerLeader;
import com.GACMD.isleofberk.common.entity.entities.dragons.stinger.Stinger;
import com.GACMD.isleofberk.common.entity.entities.dragons.terrible_terror.TerribleTerror;
import com.GACMD.isleofberk.common.entity.entities.dragons.tryiple_stryke.TripleStryke;
import com.GACMD.isleofberk.common.entity.entities.dragons.zippleback.ZippleBack;
import com.GACMD.isleofberk.common.entity.entities.eggs.entity.*;
import com.GACMD.isleofberk.common.entity.entities.projectile.breath_user.firebreaths.FireBreathProjectile;
import com.GACMD.isleofberk.common.entity.entities.projectile.breath_user.poison.ZipBreathProjectile;
import com.GACMD.isleofberk.common.entity.entities.projectile.breath_user.poison.ZippleBackAOECloud;
import com.GACMD.isleofberk.common.entity.entities.projectile.other.nadder_spike.DeadlyNadderSpike;
import com.GACMD.isleofberk.common.entity.entities.projectile.proj_user.fire_bolt.FireBolt;
import com.GACMD.isleofberk.common.entity.entities.projectile.proj_user.furybolt.FuryBolt;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {

    private ModEntities() {
    }

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, IsleofBerk.MOD_ID);


    public static final RegistryObject<EntityType<NightFury>> NIGHT_FURY = ENTITIES.register("night_fury",
            () -> EntityType.Builder.of(NightFury::new, MobCategory.CREATURE).sized(1.8f, 2.0f)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "night_fury").toString()));

    public static final RegistryObject<EntityType<TripleStryke>> TRIPLE_STRYKE = ENTITIES.register("triple_stryke",
            () -> EntityType.Builder.of(TripleStryke::new, MobCategory.CREATURE).sized(1.8f, 2.0f)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "triple_stryke").toString()));

    public static final RegistryObject<EntityType<Gronckle>> GRONCKLE = ENTITIES.register("gronckle",
            () -> EntityType.Builder.of(Gronckle::new, MobCategory.CREATURE).sized(1.6f, 1.6f)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "gronckle").toString()));

    public static final RegistryObject<EntityType<Stinger>> STINGER = ENTITIES.register("stinger",
            () -> EntityType.Builder.of(Stinger::new, MobCategory.CREATURE).sized(1.2f, 2.2f)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "stinger").toString()));

    public static final RegistryObject<EntityType<SpeedStinger>> SPEED_STINGER = ENTITIES.register("speed_stinger",
            () -> EntityType.Builder.of(SpeedStinger::new, MobCategory.CREATURE).sized(1.2f, 1.4f)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "stinger").toString()));

    public static final RegistryObject<EntityType<SpeedStingerLeader>> SPEED_STINGER_LEADER = ENTITIES.register("speed_stinger_leader",
            () -> EntityType.Builder.of(SpeedStingerLeader::new, MobCategory.CREATURE).sized(1.4f, 1.6f)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "stinger").toString()));

    public static final RegistryObject<EntityType<TerribleTerror>> TERRIBLE_TERROR = ENTITIES.register("terrible_terror",
            () -> EntityType.Builder.of(TerribleTerror::new, MobCategory.CREATURE).sized(0.8f, 0.3f)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "terrible_terror").toString()));

    public static final RegistryObject<EntityType<DeadlyNadder>> DEADLY_NADDER = ENTITIES.register("deadly_nadder",
            () -> EntityType.Builder.of(DeadlyNadder::new, MobCategory.CREATURE).sized(1.8f, 1.8f)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "deadly_nadder").toString()));

    public static final RegistryObject<EntityType<LightFury>> LIGHT_FURY = ENTITIES.register("light_fury",
            () -> EntityType.Builder.of(LightFury::new, MobCategory.CREATURE).sized(1.8f, 1.8f)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "light_fury").toString()));

    public static final RegistryObject<EntityType<MonstrousNightmare>> MONSTROUS_NIGHTMARE = ENTITIES.register("monstrous_nightmare",
            () -> EntityType.Builder.of(MonstrousNightmare::new, MobCategory.CREATURE).sized(2.8f, 2.4f)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "monstrous_nightmare").toString()));

    public static final RegistryObject<EntityType<ZippleBack>> ZIPPLEBACK = ENTITIES.register("zippleback",
            () -> EntityType.Builder.of(ZippleBack::new, MobCategory.CREATURE).sized(2.4f, 1.9f)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "zippleback").toString()));

    // projectiles
    public static final RegistryObject<EntityType<FuryBolt>> FURY_BOLT = ENTITIES.register("fury_bolt",
            () -> EntityType.Builder.<FuryBolt>of(FuryBolt::new, MobCategory.MISC).sized(0.6f, 0.6f)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "fury_bolt").toString()));

    public static final RegistryObject<EntityType<DeadlyNadderSpike>> NADDER_SPIKE = ENTITIES.register("nadder_spike",
            () -> EntityType.Builder.<DeadlyNadderSpike>of(DeadlyNadderSpike::new, MobCategory.MISC).sized(0.5F, 0.5f)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "nadder_spike").toString()));

    public static final RegistryObject<EntityType<FireBolt>> FIRE_BOLT = ENTITIES.register("fire_bolt",
            () -> EntityType.Builder.<FireBolt>of(FireBolt::new, MobCategory.MISC).sized(0.6f, 0.6f)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "fire_bolt").toString()));

    public static final RegistryObject<EntityType<ZipBreathProjectile>> ZIP_POISON = ENTITIES.register("zip_poison",
            () -> EntityType.Builder.<ZipBreathProjectile>of(ZipBreathProjectile::new, MobCategory.MISC).sized(0.6f, 0.6f)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "zip_poison").toString()));

    public static final RegistryObject<EntityType<ZippleBackAOECloud>> ZIP_CLOUD = ENTITIES.register("zip_cloud",
            () -> EntityType.Builder.<ZippleBackAOECloud>of(ZippleBackAOECloud::new, MobCategory.MISC).sized(6F, 6F).
                    clientTrackingRange(10).updateInterval(Integer.MAX_VALUE)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "zip_cloud").toString()));

    public static final RegistryObject<EntityType<FireBreathProjectile>> FIRE_PROJ = ENTITIES.register("fire_proj",
            () -> EntityType.Builder.<FireBreathProjectile>of(FireBreathProjectile::new, MobCategory.MISC).sized(0.6f, 0.6f)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "fire_proj").toString()));

    // eggs
    public static final RegistryObject<EntityType<SpeedStingerEgg>> SPEED_STINGER_EGG = ENTITIES.register("speed_stinger_egg",
            () -> EntityType.Builder.of(SpeedStingerEgg::new, MobCategory.MISC).sized(0.5f, 0.5f).fireImmune()
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "speed_stinger_egg").toString()));

    public static final RegistryObject<EntityType<TerribleTerrorEgg>> TERRIBLE_TERROR_EGG = ENTITIES.register("terrible_terror_egg",
            () -> EntityType.Builder.of(TerribleTerrorEgg::new, MobCategory.MISC).sized(0.5f, 0.5f).fireImmune()
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "speed_stinger_egg").toString()));

    // large
    public static final RegistryObject<EntityType<StingerEgg>> STINGER_EGG = ENTITIES.register("stinger_egg",
            () -> EntityType.Builder.of(StingerEgg::new, MobCategory.MISC).sized(0.7f, 0.7f)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "stinger_egg").toString()));

    public static final RegistryObject<EntityType<MonstrousNightmareEgg>> M_NIGHTMARE_EGG = ENTITIES.register("m_nightmare_egg",
            () -> EntityType.Builder.of(MonstrousNightmareEgg::new, MobCategory.MISC).sized(0.7f, 0.7f)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "m_nightmare_egg").toString()));

    public static final RegistryObject<EntityType<ZippleBackEgg>> ZIPPLEBACK_EGG = ENTITIES.register("zippleback_egg",
            () -> EntityType.Builder.of(ZippleBackEgg::new, MobCategory.MISC).sized(0.7f, 0.7f)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "zippleback_egg").toString()));

    // medium
    public static final RegistryObject<EntityType<NightFuryEgg>> NIGHT_FURY_EGG = ENTITIES.register("night_fury_egg",
            () -> EntityType.Builder.of(NightFuryEgg::new, MobCategory.MISC).sized(0.5f, 0.5f)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "night_fury_egg").toString()));

    public static final RegistryObject<EntityType<LightFuryEgg>> LIGHT_FURY_EGG = ENTITIES.register("light_fury_egg",
            () -> EntityType.Builder.of(LightFuryEgg::new, MobCategory.MISC).sized(0.5f, 0.5f)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "light_fury_egg").toString()));

    public static final RegistryObject<EntityType<GronkleEgg>> GRONCKLE_EGG = ENTITIES.register("gronckle_egg",
            () -> EntityType.Builder.of(GronkleEgg::new, MobCategory.MISC).sized(0.5f, 0.5f)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "gronckle_egg").toString()));

    public static final RegistryObject<EntityType<DeadlyNadderEgg>> NADDER_EGG = ENTITIES.register("nadder_egg",
            () -> EntityType.Builder.of(DeadlyNadderEgg::new, MobCategory.MISC).sized(0.5f, 0.5f)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "nadder_egg").toString()));

    public static final RegistryObject<EntityType<TripleStrykeEgg>> TRIPLE_STRYKE_EGG = ENTITIES.register("triple_stryke_egg",
            () -> EntityType.Builder.of(TripleStrykeEgg::new, MobCategory.MISC).sized(0.5f, 0.5f)
                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "triple_stryke_egg").toString()));
}

//    public static final RegistryObject<EntityType<Entity>> BELZIUM_ITEM_ENTITY = ENTITIES.register("belzium_item_entity",
//            () -> EntityType.Builder.of(ItemBelzium.EntityItemBelzium::new, MobCategory.CREATURE).sized(0.4f, 2.2f)
//                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "belzium_item_entity").toString()));

//    public static final RegistryObject<EntityType<ItemBelzium.EntityItemBelzium>> BELZIUM_ITEM_ENTITY = ENTITIES.register("belzium_item_entity",
//            () -> EntityType.Builder.of(ItemBelzium.EntityItemBelzium::new, MobCategory.CREATURE).sized(0.4f, 2.2f)
//                    .build(new ResourceLocation(IsleofBerk.MOD_ID, "belzium_item_entity").toString()));