package io.github.datacircuit.horizonweapons.registry;

import io.github.datacircuit.horizonweapons.HorizonWeapons;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

public class HorizonWeaponsParticles {
    public static final SimpleParticleType SPARKLE_PARTICLE = registerSimpleParticle("sparkle_particle");

    public static void init() {}

    public static SimpleParticleType registerSimpleParticle(String name) {
        SimpleParticleType particleType = FabricParticleTypes.simple();

        Registry.register(BuiltInRegistries.PARTICLE_TYPE, HorizonWeapons.id(name), particleType);
        return particleType;
    }
}
