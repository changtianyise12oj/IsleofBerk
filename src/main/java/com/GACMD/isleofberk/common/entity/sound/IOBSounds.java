package com.GACMD.isleofberk.common.entity.sound;

import com.GACMD.isleofberk.IsleofBerk;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class IOBSounds {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, IsleofBerk.MOD_ID);

    public static final RegistryObject<SoundEvent> FURY_GROWL_0 = registerSoundEvent("entity.night_fury.growl");
    public static final RegistryObject<SoundEvent> FURY_DEATH_0 = registerSoundEvent("entity.night_fury.death");
    public static final RegistryObject<SoundEvent> FURY_BITE_0 = registerSoundEvent("entity.night_fury.bite");
    public static final RegistryObject<SoundEvent> FURY_HURT_0 = registerSoundEvent("entity.night_fury.hurt");

    public static final RegistryObject<SoundEvent> SS_GROWL_0 = registerSoundEvent("entity.speed_stinger.growl");
    public static final RegistryObject<SoundEvent> SS_DEATH_0 = registerSoundEvent("entity.speed_stinger.death");
    public static final RegistryObject<SoundEvent> SS_BITE_0 = registerSoundEvent("entity.speed_stinger.bite");
    public static final RegistryObject<SoundEvent> SS_HURT_0 = registerSoundEvent("entity.speed_stinger.hurt");

    public static final RegistryObject<SoundEvent> STINGER_GROWL_0 = registerSoundEvent("entity.stinger.growl");
    public static final RegistryObject<SoundEvent> STINGER_DEATH_0 = registerSoundEvent("entity.stinger.death");
    public static final RegistryObject<SoundEvent> STINGER_BITE_0 = registerSoundEvent("entity.stinger.bite");
    public static final RegistryObject<SoundEvent> STINGER_HURT_0 = registerSoundEvent("entity.stinger.hurt");

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(IsleofBerk.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
