package io.github.datacircuit.horizonweapons.registry;

import io.github.datacircuit.horizonweapons.HorizonWeapons;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;

public class HorizonWeaponsArmorMaterials {
    public static final DeferredRegister<ArmorMaterial> REGISTER = DeferredRegister.create(Registries.ARMOR_MATERIAL, HorizonWeapons.MOD_ID);

    public static final Holder<ArmorMaterial> AIR_DEFENSE = REGISTER.register("air_defense", () -> new ArmorMaterial(
                Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                    map.put(ArmorItem.Type.BOOTS, 2);
                    map.put(ArmorItem.Type.LEGGINGS, 6);
                    map.put(ArmorItem.Type.CHESTPLATE, 8);
                    map.put(ArmorItem.Type.HELMET, 3);
                }),
                20,
                SoundEvents.ARMOR_EQUIP_GENERIC,
                () -> Ingredient.of(Tags.Items.INGOTS_IRON),
                List.of(
                        new ArmorMaterial.Layer(
                                HorizonWeapons.id("air_defence")
                        ),
                        new ArmorMaterial.Layer(
                                HorizonWeapons.id("air_defence"), "_overlay", true
                        )
                ),
                0,
                0
            )
    );

    public static void init(IEventBus bus) {
        REGISTER.register(bus);
    }
}
