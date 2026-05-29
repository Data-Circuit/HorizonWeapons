package io.github.datacircuit.horizonweapons.registry;

import io.github.datacircuit.horizonweapons.HorizonWeapons;
import io.github.datacircuit.horizonweapons.item.components.tooltip.BellOfGivingActiveTooltip;
import net.fabricmc.fabric.api.item.v1.ItemComponentTooltipProviderRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;

public class HorizonWeaponsDataComponents {
    public static final DataComponentType<BellOfGivingActiveTooltip> BELL_OF_GIVING = Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            HorizonWeapons.id("bell_of_giving"),
            DataComponentType.<BellOfGivingActiveTooltip>builder().persistent(BellOfGivingActiveTooltip.CODEC).build()
    );

    public static void init() {
        registerComponentTooltips();
    }

    private static void registerComponentTooltips() {
        ItemComponentTooltipProviderRegistry.addAfter(DataComponents.DAMAGE, BELL_OF_GIVING);
    }
}
