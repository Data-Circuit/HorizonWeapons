package io.github.datacircuit.horizonweapons.registry;

import io.github.datacircuit.horizonweapons.HorizonWeapons;
import net.fabricmc.fabric.api.registry.FabricPotionBrewingBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
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
    public static final Holder<Potion> STRONG_CONFUSION_POTION =
            Registry.registerForHolder(
                    BuiltInRegistries.POTION,
                    HorizonWeapons.id("strong_confusion"),
                    new Potion("confusion",
                            new MobEffectInstance(
                                    HorizonWeaponsEffects.CONFUSION,
                                    3600,
                                    1
                            ))
            );
    public static final Holder<Potion> STRONGER_CONFUSION_POTION =
            Registry.registerForHolder(
                    BuiltInRegistries.POTION,
                    HorizonWeapons.id("stronger_confusion"),
                    new Potion("confusion",
                            new MobEffectInstance(
                                    HorizonWeaponsEffects.CONFUSION,
                                    3600,
                                    2
                            ))
            );
    public static final Holder<Potion> STRONGEST_CONFUSION_POTION =
            Registry.registerForHolder(
                    BuiltInRegistries.POTION,
                    HorizonWeapons.id("strongest_confusion"),
                    new Potion("confusion",
                            new MobEffectInstance(
                                    HorizonWeaponsEffects.CONFUSION,
                                    3600,
                                    3
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
                    CONFUSION_POTION,
                    Items.REDSTONE,
                    STRONG_CONFUSION_POTION
            );
            builder.addMix(
                    STRONG_CONFUSION_POTION,
                    Items.REDSTONE,
                    STRONGER_CONFUSION_POTION
            );
            builder.addMix(
                    STRONGER_CONFUSION_POTION,
                    Items.REDSTONE,
                    STRONGEST_CONFUSION_POTION
            );
            builder.addMix(
                    Potions.SWIFTNESS,
                    Items.PHANTOM_MEMBRANE,
                    INERTIA_POTION
            );
        });
    }
}
