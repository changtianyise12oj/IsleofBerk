package com.GACMD.isleofberk.registery;

import com.GACMD.isleofberk.IsleofBerk;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, IsleofBerk.MOD_ID);

    public static final RegistryObject<SoundEvent> NIGHT_FURY_GROWL                 = registerSoundEvent("entity.night_fury.growl");
    public static final RegistryObject<SoundEvent> NIGHT_FURY_HURT                  = registerSoundEvent("entity.night_fury.hurt");
    public static final RegistryObject<SoundEvent> NIGHT_FURY_DEATH                 = registerSoundEvent("entity.night_fury.death");
    public static final RegistryObject<SoundEvent> NIGHT_FURY_TAME                  = registerSoundEvent("entity.night_fury.tame");
    public static final RegistryObject<SoundEvent> NIGHT_FURY_SLEEP                 = registerSoundEvent("entity.night_fury.sleep");
    public static final RegistryObject<SoundEvent> NIGHT_FURY_FIRE                  = registerSoundEvent("entity.night_fury.fire");

    public static final RegistryObject<SoundEvent> LIGHT_FURY_GROWL                 = registerSoundEvent("entity.light_fury.growl");
    public static final RegistryObject<SoundEvent> LIGHT_FURY_HURT                  = registerSoundEvent("entity.light_fury.hurt");
    public static final RegistryObject<SoundEvent> LIGHT_FURY_DEATH                 = registerSoundEvent("entity.light_fury.death");
    public static final RegistryObject<SoundEvent> LIGHT_FURY_TAME                  = registerSoundEvent("entity.light_fury.tame");
    public static final RegistryObject<SoundEvent> LIGHT_FURY_SLEEP                 = registerSoundEvent("entity.light_fury.sleep");
    public static final RegistryObject<SoundEvent> LIGHT_FURY_FIRE                  = registerSoundEvent("entity.light_fury.fire");

    public static final RegistryObject<SoundEvent> TRIPLE_STRYKE_GROWL              = registerSoundEvent("entity.triple_stryke.growl");
    public static final RegistryObject<SoundEvent> TRIPLE_STRYKE_HURT               = registerSoundEvent("entity.triple_stryke.hurt");
    public static final RegistryObject<SoundEvent> TRIPLE_STRYKE_DEATH              = registerSoundEvent("entity.triple_stryke.death");
    public static final RegistryObject<SoundEvent> TRIPLE_STRYKE_TAME               = registerSoundEvent("entity.triple_stryke.tame");
    public static final RegistryObject<SoundEvent> TRIPLE_STRYKE_SLEEP              = registerSoundEvent("entity.triple_stryke.sleep");
    public static final RegistryObject<SoundEvent> TRIPLE_STRYKE_FIRE               = registerSoundEvent("entity.triple_stryke.fire");

    public static final RegistryObject<SoundEvent> DEADLY_NADDER_GROWL              = registerSoundEvent("entity.deadly_nadder.growl");
    public static final RegistryObject<SoundEvent> DEADLY_NADDER_HURT               = registerSoundEvent("entity.deadly_nadder.hurt");
    public static final RegistryObject<SoundEvent> DEADLY_NADDER_DEATH              = registerSoundEvent("entity.deadly_nadder.death");
    public static final RegistryObject<SoundEvent> DEADLY_NADDER_TAME               = registerSoundEvent("entity.deadly_nadder.tame");
    public static final RegistryObject<SoundEvent> DEADLY_NADDER_SLEEP              = registerSoundEvent("entity.deadly_nadder.sleep");
    public static final RegistryObject<SoundEvent> DEADLY_NADDER_FIRE               = registerSoundEvent("entity.deadly_nadder.fire");

    public static final RegistryObject<SoundEvent> GRONCKLE_GROWL                   = registerSoundEvent("entity.gronckle.growl");
    public static final RegistryObject<SoundEvent> GRONCKLE_HURT                    = registerSoundEvent("entity.gronckle.hurt");
    public static final RegistryObject<SoundEvent> GRONCKLE_DEATH                   = registerSoundEvent("entity.gronckle.death");
    public static final RegistryObject<SoundEvent> GRONCKLE_TAME                    = registerSoundEvent("entity.gronckle.tame");
    public static final RegistryObject<SoundEvent> GRONCKLE_SLEEP                   = registerSoundEvent("entity.gronckle.sleep");
    public static final RegistryObject<SoundEvent> GRONCKLE_FIRE                    = registerSoundEvent("entity.gronckle.fire");
    public static final RegistryObject<SoundEvent> GRONCKLE_SPIT_IRON               = registerSoundEvent("entity.gronckle.spit_iron");

    public static final RegistryObject<SoundEvent> MONSTROUS_NIGHTMARE_GROWL        = registerSoundEvent("entity.monstrous_nightmare.growl");
    public static final RegistryObject<SoundEvent> MONSTROUS_NIGHTMARE_HURT         = registerSoundEvent("entity.monstrous_nightmare.hurt");
    public static final RegistryObject<SoundEvent> MONSTROUS_NIGHTMARE_DEATH        = registerSoundEvent("entity.monstrous_nightmare.death");
    public static final RegistryObject<SoundEvent> MONSTROUS_NIGHTMARE_TAME         = registerSoundEvent("entity.monstrous_nightmare.tame");
    public static final RegistryObject<SoundEvent> MONSTROUS_NIGHTMARE_SLEEP        = registerSoundEvent("entity.monstrous_nightmare.sleep");
    public static final RegistryObject<SoundEvent> MONSTROUS_NIGHTMARE_FIRE         = registerSoundEvent("entity.monstrous_nightmare.fire");

    public static final RegistryObject<SoundEvent> HIDEOUS_ZIPPLEBACK_GROWL                 = registerSoundEvent("entity.zippleback.growl");
    public static final RegistryObject<SoundEvent> HIDEOUS_ZIPPLEBACK_HURT                  = registerSoundEvent("entity.zippleback.hurt");
    public static final RegistryObject<SoundEvent> HIDEOUS_ZIPPLEBACK_DEATH                 = registerSoundEvent("entity.zippleback.death");
    public static final RegistryObject<SoundEvent> HIDEOUS_ZIPPLEBACK_TAME                  = registerSoundEvent("entity.zippleback.tame");
    public static final RegistryObject<SoundEvent> HIDEOUS_ZIPPLEBACK_SLEEP                 = registerSoundEvent("entity.zippleback.sleep");
    public static final RegistryObject<SoundEvent> HIDEOUS_ZIPPLEBACK_LIGHTER               = registerSoundEvent("entity.zippleback.lighter");
    public static final RegistryObject<SoundEvent> HIDEOUS_ZIPPLEBACK_POISON_BREATH         = registerSoundEvent("entity.zippleback.poison_breath");

    public static final RegistryObject<SoundEvent> TERRIBLE_TERROR_GROWL            = registerSoundEvent("entity.terrible_terror.growl");
    public static final RegistryObject<SoundEvent> TERRIBLE_TERROR_HURT             = registerSoundEvent("entity.terrible_terror.hurt");
    public static final RegistryObject<SoundEvent> TERRIBLE_TERROR_DEATH            = registerSoundEvent("entity.terrible_terror.death");
    public static final RegistryObject<SoundEvent> TERRIBLE_TERROR_TAME             = registerSoundEvent("entity.terrible_terror.tame");
    public static final RegistryObject<SoundEvent> TERRIBLE_TERROR_SLEEP            = registerSoundEvent("entity.terrible_terror.sleep");
    public static final RegistryObject<SoundEvent> TERRIBLE_TERROR_FIRE             = registerSoundEvent("entity.terrible_terror.fire");

    public static final RegistryObject<SoundEvent> SPEED_STINGER_GROWL              = registerSoundEvent("entity.speed_stinger.growl");
    public static final RegistryObject<SoundEvent> SPEED_STINGER_HURT               = registerSoundEvent("entity.speed_stinger.hurt");
    public static final RegistryObject<SoundEvent> SPEED_STINGER_DEATH              = registerSoundEvent("entity.speed_stinger.death");
    public static final RegistryObject<SoundEvent> SPEED_STINGER_SLEEP              = registerSoundEvent("entity.speed_stinger.sleep");

    public static final RegistryObject<SoundEvent> STINGER_GROWL                    = registerSoundEvent("entity.stinger.growl");
    public static final RegistryObject<SoundEvent> STINGER_HURT                     = registerSoundEvent("entity.stinger.hurt");
    public static final RegistryObject<SoundEvent> STINGER_DEATH                    = registerSoundEvent("entity.stinger.death");
    public static final RegistryObject<SoundEvent> STINGER_TAME                     = registerSoundEvent("entity.stinger.tame");
    public static final RegistryObject<SoundEvent> STINGER_SLEEP                    = registerSoundEvent("entity.stinger.sleep");

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(IsleofBerk.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
