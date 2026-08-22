package io.github.datacircuit.horizonweapons.registry;

import io.github.datacircuit.horizonweapons.HorizonWeapons;
import io.github.datacircuit.horizonweapons.effect.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class HorizonWeaponsEffects {
    public static final DeferredRegister<MobEffect> REGISTER = DeferredRegister.create(Registries.MOB_EFFECT, HorizonWeapons.MOD_ID);

    public static final DeferredHolder<MobEffect, MobEffect> VITALITY = REGISTER.register("vitality", VitalityEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> CONFUSION = REGISTER.register("confusion", ConfusionEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> ANCHOR = REGISTER.register("anchor", AnchorEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> INERTIA = REGISTER.register("inertia", InertiaEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> ROT = REGISTER.register("rot", RotEffect::new);

    public static void init(IEventBus bus) {
        REGISTER.register(bus);
    }
}
