package io.github.datacircuit.horizonweapons.registry;

import io.github.datacircuit.horizonweapons.HorizonWeapons;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber
public class HorizonWeaponsPotions {
    public static final DeferredRegister<Potion> REGISTER = DeferredRegister.create(Registries.POTION, HorizonWeapons.MOD_ID);

    public static final DeferredHolder<Potion, Potion> ANCHOR_POTION = REGISTER.register("anchor", () -> new Potion("anchor", new MobEffectInstance(HorizonWeaponsEffects.ANCHOR, 3600)));
    public static final DeferredHolder<Potion, Potion> ROT_POTION = REGISTER.register("rot", () -> new Potion("rot", new MobEffectInstance(HorizonWeaponsEffects.ROT, 3600)));
    public static final DeferredHolder<Potion, Potion> CONFUSION_POTION = REGISTER.register("confusion", () -> new Potion("confusion", new MobEffectInstance(HorizonWeaponsEffects.CONFUSION, 3600)));
    public static final DeferredHolder<Potion, Potion> STRONG_CONFUSION_POTION = REGISTER.register("strong_confusion", () -> new Potion("confusion", new MobEffectInstance(HorizonWeaponsEffects.CONFUSION, 3600, 1)));
    public static final DeferredHolder<Potion, Potion> STRONGER_CONFUSION_POTION = REGISTER.register("stronger_confusion", () -> new Potion("confusion", new MobEffectInstance(HorizonWeaponsEffects.CONFUSION, 3600, 2)));
    public static final DeferredHolder<Potion, Potion> STRONGEST_CONFUSION_POTION = REGISTER.register("strongest_confusion", () -> new Potion("confusion", new MobEffectInstance(HorizonWeaponsEffects.CONFUSION, 3600, 3)));
    public static final DeferredHolder<Potion, Potion> INERTIA_POTION = REGISTER.register("inertia", () -> new Potion("inertia", new MobEffectInstance(HorizonWeaponsEffects.INERTIA, 3600)));
    public static final DeferredHolder<Potion, Potion> VITALITY_POTION = REGISTER.register("vitality", () -> new Potion("vitality", new MobEffectInstance(HorizonWeaponsEffects.VITALITY, 3600)));

    @SubscribeEvent
    public static void registerBrewingRecipes(RegisterBrewingRecipesEvent event) {
        PotionBrewing.Builder builder = event.getBuilder();

        builder.addMix(Potions.POISON, Items.FIRE_CHARGE, VITALITY_POTION);
        builder.addMix(Potions.SLOWNESS, Items.CHAIN, ANCHOR_POTION);
        builder.addMix(Potions.WEAKNESS, Items.POISONOUS_POTATO, CONFUSION_POTION);
        builder.addMix(CONFUSION_POTION, Items.REDSTONE, STRONG_CONFUSION_POTION);
        builder.addMix(STRONG_CONFUSION_POTION, Items.REDSTONE, STRONGER_CONFUSION_POTION);
        builder.addMix(STRONGER_CONFUSION_POTION, Items.REDSTONE, STRONGEST_CONFUSION_POTION);
        builder.addMix(Potions.SWIFTNESS, Items.PHANTOM_MEMBRANE, INERTIA_POTION);
    }

    public static void init(IEventBus bus) {
        REGISTER.register(bus);
    }
}
