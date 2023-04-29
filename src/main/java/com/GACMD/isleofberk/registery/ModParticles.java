package com.GACMD.isleofberk.registery;

import com.GACMD.isleofberk.IsleofBerk;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {

    public static final DeferredRegister<ParticleType<?>> REGISTRAR = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, IsleofBerk.MOD_ID);

    public static RegistryObject<SimpleParticleType> NIGHT_FURY_DUST = REGISTRAR.register("night_fury_dust", () -> new SimpleParticleType(false));
    public static RegistryObject<SimpleParticleType> LIGHT_FURY_DUST = REGISTRAR.register("light_fury_dust", () -> new SimpleParticleType(false));
    public static RegistryObject<SimpleParticleType> SKRILL_SKILL_PARTICLES = REGISTRAR.register("skrill_skill_particles", () -> new SimpleParticleType(false));
    public static RegistryObject<SimpleParticleType> SKRILL_LIGHTNING_PARTICLES = REGISTRAR.register("skrill_lightning_particles", () -> new SimpleParticleType(false));
    public static RegistryObject<SimpleParticleType> FLAME_TAIL = REGISTRAR.register("flame_tail", () -> new SimpleParticleType(false));
    public static RegistryObject<SimpleParticleType> FLAME = REGISTRAR.register("flame", () -> new SimpleParticleType(false));

    public static RegistryObject<SimpleParticleType> FIRE_COAT = REGISTRAR.register("fire_coat", () -> new SimpleParticleType(false));

    public static RegistryObject<SimpleParticleType> GAS = REGISTRAR.register("gas", () -> new SimpleParticleType(false));

    public static RegistryObject<SimpleParticleType> LIGHTNING_AOE_EMITTER = REGISTRAR.register("lightning_aoe_emitter", () -> new SimpleParticleType(false));

    public static RegistryObject<SimpleParticleType> GAS_AOE_EMITTER = REGISTRAR.register("gas_aoe_emitter", () -> new SimpleParticleType(false));


}
