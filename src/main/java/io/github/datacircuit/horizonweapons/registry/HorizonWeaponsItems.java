package io.github.datacircuit.horizonweapons.registry;

import io.github.datacircuit.horizonweapons.HorizonWeapons;
import io.github.datacircuit.horizonweapons.item.weapon.*;
import io.github.datacircuit.horizonweapons.material.AirDefenceArmorMaterial;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class HorizonWeaponsItems {
    public static final DeferredRegister.Items REGISTER = DeferredRegister.createItems(HorizonWeapons.MOD_ID);

    public static final DeferredItem<Item> AIR_DEFENCE_HELMET = REGISTER.register(
            "air_defence_helmet",
            () -> new ArmorItem(HorizonWeaponsArmorMaterials.AIR_DEFENSE, ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(AirDefenceArmorMaterial.BASE_DURABILITY)))
    );
    public static final DeferredItem<Item> AIR_DEFENCE_CHESTPLATE = REGISTER.register(
            "air_defence_chestplate",
            () -> new ArmorItem(HorizonWeaponsArmorMaterials.AIR_DEFENSE, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(AirDefenceArmorMaterial.BASE_DURABILITY)))
    );
    public static final DeferredItem<Item> AIR_DEFENCE_LEGGINGS = REGISTER.register(
            "air_defence_leggings",
            () -> new ArmorItem(HorizonWeaponsArmorMaterials.AIR_DEFENSE, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(AirDefenceArmorMaterial.BASE_DURABILITY)))
    );
    public static final DeferredItem<Item> AIR_DEFENCE_BOOTS = REGISTER.register(
            "air_defence_boots",
            () -> new ArmorItem(HorizonWeaponsArmorMaterials.AIR_DEFENSE, ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(AirDefenceArmorMaterial.BASE_DURABILITY)))
    );
    public static final DeferredItem<Item> DEATHBRINGER_SCYTHE = REGISTER.registerItem(
            "deathbringer_scythe",
            DeathbringerScytheWeapon::new,
            new Item.Properties()
    );
    public static final DeferredItem<Item> BLACKENED_ROOT = REGISTER.registerItem(
            "blackened_root",
            BlackenedRootWeapon::new,
            new Item.Properties()
    );
    public static final DeferredItem<Item> LIDLESS_NEEDLE = REGISTER.registerItem(
            "lidless_needle",
            LidlessNeedleWeapon::new,
            new Item.Properties()
    );
    public static final DeferredItem<Item> BELL_OF_GIVING = REGISTER.registerItem(
            "bell_of_giving",
            BellOfGivingWeapon::new,
            new Item.Properties()
    );
    public static final DeferredItem<Item> UNFINISHED_BLADE = REGISTER.registerItem(
            "unfinished_blade",
            UnfinishedBladeWeapon::new,
            new Item.Properties()
    );
    public static final DeferredItem<BlockItem> PLINTH = REGISTER.registerSimpleBlockItem(HorizonWeaponsBlocks.PLINTH);

    public static final DeferredRegister<CreativeModeTab> TAB_REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, HorizonWeapons.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB = TAB_REGISTER.register("creative_tab", () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(HorizonWeaponsItems.BLACKENED_ROOT.get()))
            .title(Component.translatable("key.category.horizonweapons"))
            .displayItems((params, output) -> {
                output.accept(AIR_DEFENCE_HELMET.get());
                output.accept(AIR_DEFENCE_CHESTPLATE.get());
                output.accept(AIR_DEFENCE_LEGGINGS.get());
                output.accept(AIR_DEFENCE_BOOTS.get());
                output.accept(DEATHBRINGER_SCYTHE.get());
                output.accept(BLACKENED_ROOT.get());
                output.accept(LIDLESS_NEEDLE.get());
                output.accept(BELL_OF_GIVING.get());
                output.accept(UNFINISHED_BLADE.get());
                output.accept(PLINTH.get());
            }).build());

    public static void init(IEventBus bus) {
        REGISTER.register(bus);
        TAB_REGISTER.register(bus);
    }
}
