package io.github.datacircuit.horizonweapons.registry;

import io.github.datacircuit.horizonweapons.HorizonWeapons;
import net.fabricmc.fabric.api.registry.FabricPotionBrewingBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;

public class HorizonWeaponsPotions {
    public static final Holder<Potion> ANCHOR_POTION =
            Registry.registerForHolder(
                    BuiltInRegistries.POTION,
                    HorizonWeapons.id("anchor"),
                    new Potion("anchor",
                            new MobEffectInstance(
                                    HorizonWeaponsEffects.ANCHOR,
                                    3600
                            ))
            );
    public static final Holder<Potion> CONFUSION_POTION =
            Registry.registerForHolder(
                    BuiltInRegistries.POTION,
                    HorizonWeapons.id("confusion"),
                    new Potion("confusion",
                            new MobEffectInstance(
                                    HorizonWeaponsEffects.CONFUSION,
                                    3600
                            ))
            );
    public static final Holder<Potion> INERTIA_POTION =
            Registry.registerForHolder(
                    BuiltInRegistries.POTION,
                    HorizonWeapons.id("inertia"),
                    new Potion("inertia",
                            new MobEffectInstance(
                                    HorizonWeaponsEffects.INERTIA,
                                    3600
                            ))
            );
    public static final Holder<Potion> VITALITY_POTION =
            Registry.registerForHolder(
                    BuiltInRegistries.POTION,
                    HorizonWeapons.id("vitality"),
                    new Potion("vitality",
                            new MobEffectInstance(
                                    HorizonWeaponsEffects.VITALITY,
                                    3600
                            ))
            );

    public static void init() {
        FabricPotionBrewingBuilder.BUILD.register(builder -> {
            builder.addMix(
                    Potions.POISON,
                    Items.FIRE_CHARGE,
                    VITALITY_POTION
            );
            builder.addMix(
                    Potions.SLOWNESS,
                    Items.IRON_CHAIN,
                    ANCHOR_POTION
            );
            builder.addMix(
                    Potions.WEAKNESS,
                    Items.POISONOUS_POTATO,
                    CONFUSION_POTION
            );
            builder.addMix(
                    Potions.SWIFTNESS,
                    Items.PHANTOM_MEMBRANE,
                    INERTIA_POTION
            );
        });
    }
}
