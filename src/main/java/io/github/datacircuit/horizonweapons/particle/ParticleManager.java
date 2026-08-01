package io.github.datacircuit.horizonweapons.particle;

import io.github.datacircuit.horizonweapons.HorizonWeapons;
import mod.chloeprime.aaaparticles.api.common.AAALevel;
import mod.chloeprime.aaaparticles.api.common.ParticleEmitterInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ParticleManager {
    private static final ParticleEmitterInfo BELL_OF_GIVING = new ParticleEmitterInfo(HorizonWeapons.id("bell-of-giving"));

    public static void bell_of_giving(Level level, Vec3 pos) {
        AAALevel.addParticle(level, false, BELL_OF_GIVING.clone().position(pos));
    }
}
