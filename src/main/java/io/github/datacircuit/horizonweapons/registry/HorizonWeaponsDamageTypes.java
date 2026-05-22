package io.github.datacircuit.horizonweapons.registry;

import io.github.datacircuit.horizonweapons.HorizonWeapons;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public class HorizonWeaponsDamageTypes {
    public static final ResourceKey<DamageType> TRUE_DAMAGE_KEY = ResourceKey.create(
            Registries.DAMAGE_TYPE,
            HorizonWeapons.id("true_damage")
    );
}
