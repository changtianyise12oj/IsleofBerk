package com.GACMD.isleofberk.event;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.entity.dragons.deadlynadder.DeadlyNadder;
import com.GACMD.isleofberk.entity.dragons.gronckle.Gronckle;
import com.GACMD.isleofberk.entity.dragons.lightfury.LightFury;
import com.GACMD.isleofberk.entity.dragons.montrous_nightmare.MonstrousNightmare;
import com.GACMD.isleofberk.entity.dragons.nightfury.NightFury;
import com.GACMD.isleofberk.entity.dragons.skrill.Skrill;
import com.GACMD.isleofberk.entity.dragons.speedstinger.SpeedStinger;
import com.GACMD.isleofberk.entity.dragons.speedstingerleader.SpeedStingerLeader;
import com.GACMD.isleofberk.entity.dragons.stinger.Stinger;
import com.GACMD.isleofberk.entity.dragons.terrible_terror.TerribleTerror;
import com.GACMD.isleofberk.entity.dragons.triple_stryke.TripleStryke;
import com.GACMD.isleofberk.entity.dragons.zippleback.ZippleBack;
import com.GACMD.isleofberk.entity.eggs.entity.eggs.*;
import com.GACMD.isleofberk.registery.ModEntities;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = IsleofBerk.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonModEvent {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        /**
         * Dragons
         */
        event.put(ModEntities.NIGHT_FURY.get(), NightFury.createAttributes().build());
        event.put(ModEntities.LIGHT_FURY.get(), LightFury.createAttributes().build());
        event.put(ModEntities.TRIPLE_STRYKE.get(), TripleStryke.createAttributes().build());
        event.put(ModEntities.DEADLY_NADDER.get(), DeadlyNadder.createAttributes().build());
        event.put(ModEntities.GRONCKLE.get(), Gronckle.createAttributes().build());
        event.put(ModEntities.MONSTROUS_NIGHTMARE.get(), MonstrousNightmare.createAttributes().build());
        event.put(ModEntities.ZIPPLEBACK.get(), ZippleBack.createAttributes().build());
        event.put(ModEntities.TERRIBLE_TERROR.get(), TerribleTerror.createAttributes().build());
        event.put(ModEntities.SPEED_STINGER_LEADER.get(), SpeedStingerLeader.createAttributes().build());
        event.put(ModEntities.SPEED_STINGER.get(), SpeedStinger.createAttributes().build());
        event.put(ModEntities.STINGER.get(), Stinger.createAttributes().build());
        event.put(ModEntities.SKRILL.get(), Skrill.createAttributes().build());

        /**
         * Eggs
         */
        event.put(ModEntities.NIGHT_FURY_EGG.get(), NightFuryEgg.createAttributes().build());
        event.put(ModEntities.LIGHT_FURY_EGG.get(), LightFuryEgg.createAttributes().build());
        event.put(ModEntities.TRIPLE_STRYKE_EGG.get(), SpeedStingerEgg.createAttributes().build());
        event.put(ModEntities.NADDER_EGG.get(), DeadlyNadderEgg.createAttributes().build());
        event.put(ModEntities.GRONCKLE_EGG.get(), GronkleEgg.createAttributes().build());
        event.put(ModEntities.M_NIGHTMARE_EGG.get(), SpeedStingerEgg.createAttributes().build());
        event.put(ModEntities.ZIPPLEBACK_EGG.get(), SpeedStingerEgg.createAttributes().build());
        event.put(ModEntities.TERRIBLE_TERROR_EGG.get(), SpeedStingerEgg.createAttributes().build());
        event.put(ModEntities.SPEED_STINGER_EGG.get(), SpeedStingerEgg.createAttributes().build());
        event.put(ModEntities.STINGER_EGG.get(), SpeedStingerEgg.createAttributes().build());
        event.put(ModEntities.SKRILL_EGG.get(), SkrillEgg.createAttributes().build());
    }
}