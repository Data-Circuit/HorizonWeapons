package io.github.datacircuit.horizonweapons.registry;

import io.github.datacircuit.horizonweapons.HorizonWeapons;
import io.github.datacircuit.horizonweapons.item.components.tooltip.BellOfGivingActiveTooltip;
import io.github.datacircuit.horizonweapons.item.components.tooltip.RotTooltip;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class HorizonWeaponsDataComponents {
    public static final DeferredRegister.DataComponents REGISTER = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, HorizonWeapons.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<BellOfGivingActiveTooltip>> BELL_OF_GIVING = REGISTER.registerComponentType(
            "bell_of_giving",
            builder -> builder.persistent(BellOfGivingActiveTooltip.CODEC)
    );
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<RotTooltip>> ROTTING_ITEM = REGISTER.registerComponentType(
            "rotting_item",
            builder -> builder.persistent(RotTooltip.CODEC)
    );

    public static void init(IEventBus bus) {
        REGISTER.register(bus);
    }
}
