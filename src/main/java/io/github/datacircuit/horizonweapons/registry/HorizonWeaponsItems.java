package io.github.datacircuit.horizonweapons.registry;

import io.github.datacircuit.horizonweapons.HorizonWeapons;
import io.github.datacircuit.horizonweapons.block.PlinthBlock;
import io.github.datacircuit.horizonweapons.item.weapon.BlackenedRootWeapon;
import io.github.datacircuit.horizonweapons.item.weapon.DeathbringerScytheWeapon;
import io.github.datacircuit.horizonweapons.item.weapon.LidlessNeedleWeapon;
import io.github.datacircuit.horizonweapons.material.AirDefenceArmorMaterial;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class HorizonWeaponsItems {
    public static final Item AIR_DEFENCE_HELMET = register(
            "air_defence_helmet",
            Item::new,
            new Item.Properties().humanoidArmor(AirDefenceArmorMaterial.INSTANCE, ArmorType.HELMET)
                    .durability(ArmorType.HELMET.getDurability(AirDefenceArmorMaterial.BASE_DURABILITY))
    );
    public static final Item AIR_DEFENCE_CHESTPLATE = register(
            "air_defence_chestplate",
            Item::new,
            new Item.Properties().humanoidArmor(AirDefenceArmorMaterial.INSTANCE, ArmorType.CHESTPLATE)
                    .durability(ArmorType.CHESTPLATE.getDurability(AirDefenceArmorMaterial.BASE_DURABILITY))
    );
    public static final Item AIR_DEFENCE_LEGGINGS = register(
            "air_defence_leggings",
            Item::new,
            new Item.Properties().humanoidArmor(AirDefenceArmorMaterial.INSTANCE, ArmorType.LEGGINGS)
                    .durability(ArmorType.LEGGINGS.getDurability(AirDefenceArmorMaterial.BASE_DURABILITY))
    );
    public static final Item AIR_DEFENCE_BOOTS = register(
            "air_defence_boots",
            Item::new,
            new Item.Properties().humanoidArmor(AirDefenceArmorMaterial.INSTANCE, ArmorType.BOOTS)
                    .durability(ArmorType.BOOTS.getDurability(AirDefenceArmorMaterial.BASE_DURABILITY))
    );
    public static final Item DEATHBRINGER_SCYTHE = register(
            "deathbringer_scythe",
            DeathbringerScytheWeapon::new,
            new Item.Properties()
    );
    public static final Item BLACKENED_ROOT = register(
            "blackened_root",
            BlackenedRootWeapon::new,
            new Item.Properties()
    );
    public static final Item LIDLESS_NEEDLE = register(
            "lidless_needle",
            LidlessNeedleWeapon::new,
            new Item.Properties()
    );
    public static final ResourceKey<@NotNull CreativeModeTab> HORIZON_WEAPONS_TAB_KEY = ResourceKey.create(
            BuiltInRegistries.CREATIVE_MODE_TAB.key(), Identifier.fromNamespaceAndPath(HorizonWeapons.MOD_ID, "creative_tab")
    );
    public static final CreativeModeTab HORIZON_WEAPONS_TAB = FabricCreativeModeTab.builder()
            .icon(() -> new ItemStack(HorizonWeaponsItems.LIDLESS_NEEDLE))
            .title(Component.translatable("key.category.horizonweapons"))
            .displayItems((params, output) -> {
                output.accept(AIR_DEFENCE_HELMET);
                output.accept(AIR_DEFENCE_CHESTPLATE);
                output.accept(AIR_DEFENCE_LEGGINGS);
                output.accept(AIR_DEFENCE_BOOTS);
                output.accept(DEATHBRINGER_SCYTHE);
                output.accept(BLACKENED_ROOT);
                output.accept(LIDLESS_NEEDLE);
            })
            .build();

    public static <T extends Item> T register(String name, Function<Item.Properties, T> itemFactory, Item.Properties settings) {
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, HorizonWeapons.id(name));

        T item = itemFactory.apply(settings.setId(itemKey));

        Registry.register(BuiltInRegistries.ITEM, itemKey, item);

        return item;
    }

    public static void init() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, HORIZON_WEAPONS_TAB_KEY, HORIZON_WEAPONS_TAB);
    }
}
