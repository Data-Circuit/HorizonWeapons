package io.github.datacircuit.horizonweapons.material;

import io.github.datacircuit.horizonweapons.HorizonWeapons;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.Map;

public class AirDefenceArmorMaterial {
    public static final ResourceKey<EquipmentAsset> AIR_DEFENCE_ARMOR_MATERIAL_KEY =
            ResourceKey.create(EquipmentAssets.ROOT_ID, Identifier.fromNamespaceAndPath(HorizonWeapons.MOD_ID, "air_defence"));

    public static final int BASE_DURABILITY = 15;

    public static final TagKey<Item> REPAIRS_AIR_DEFENCE_ARMOR =
            TagKey.create(BuiltInRegistries.ITEM.key(), Identifier.fromNamespaceAndPath(HorizonWeapons.MOD_ID, "repairs_air_defence_armor"));

    public static final ArmorMaterial INSTANCE = new ArmorMaterial(
            BASE_DURABILITY,
            Map.of(
                    ArmorType.HELMET, 3,
                    ArmorType.CHESTPLATE, 8,
                    ArmorType.LEGGINGS, 6,
                    ArmorType.BOOTS, 3
            ),
            5,
            SoundEvents.ARMOR_EQUIP_LEATHER,
            0.0f,
            0.0f,
            REPAIRS_AIR_DEFENCE_ARMOR,
            AIR_DEFENCE_ARMOR_MATERIAL_KEY
    );
}
