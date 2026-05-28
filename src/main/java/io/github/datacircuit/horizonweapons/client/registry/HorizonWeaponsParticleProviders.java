package io.github.datacircuit.horizonweapons.client.registry;

import io.github.datacircuit.horizonweapons.registry.HorizonWeaponsParticles;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.minecraft.client.particle.EndRodParticle;

public class HorizonWeaponsParticleProviders {
    public static void init() {
        ParticleProviderRegistry.getInstance().register(HorizonWeaponsParticles.SPARKLE_PARTICLE, EndRodParticle.Provider::new);
    }
}
