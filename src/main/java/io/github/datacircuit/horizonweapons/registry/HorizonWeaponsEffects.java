package io.github.datacircuit.horizonweapons.registry;

import io.github.datacircuit.horizonweapons.HorizonWeapons;
import io.github.datacircuit.horizonweapons.effect.AnchorEffect;
import io.github.datacircuit.horizonweapons.effect.ConfusionEffect;
import io.github.datacircuit.horizonweapons.effect.InertiaEffect;
import io.github.datacircuit.horizonweapons.effect.VitalityEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;

public class HorizonWeaponsEffects {
    public static final Holder<MobEffect> VITALITY =
            Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, HorizonWeapons.id("vitality"),
                    new VitalityEffect());
    public static final Holder<MobEffect> CONFUSION =
            Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, HorizonWeapons.id("confusion"),
                    new ConfusionEffect());
    public static final Holder<MobEffect> ANCHOR =
            Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, HorizonWeapons.id("anchor"),
                    new AnchorEffect());
    public static final Holder<MobEffect> INERTIA =
            Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, HorizonWeapons.id("inertia"),
                    new InertiaEffect());

    public static void init() {}
}
