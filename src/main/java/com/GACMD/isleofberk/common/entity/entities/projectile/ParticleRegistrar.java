package com.GACMD.isleofberk.common.entity.entities.projectile;

import com.GACMD.isleofberk.IsleofBerk;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ParticleRegistrar {

    public static final DeferredRegister<ParticleType<?>> REGISTRAR = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, IsleofBerk.MOD_ID);

    public static RegistryObject<SimpleParticleType> FURY_DUST = REGISTRAR.register("fury_dust", () -> new SimpleParticleType(false));
    public static RegistryObject<SimpleParticleType> FLAME_TAIL = REGISTRAR.register("flame_tail", () -> new SimpleParticleType(false));
    public static RegistryObject<SimpleParticleType> FLAME = REGISTRAR.register("flame", () -> new SimpleParticleType(false));

}
