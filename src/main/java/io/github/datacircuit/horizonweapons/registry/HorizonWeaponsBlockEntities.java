package io.github.datacircuit.horizonweapons.registry;

import io.github.datacircuit.horizonweapons.HorizonWeapons;
import io.github.datacircuit.horizonweapons.block.entity.PlinthBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class HorizonWeaponsBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTER = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, HorizonWeapons.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PlinthBlockEntity>> PLINTH_BLOCK_ENTITY = REGISTER.register(
            "plinth",
            () -> BlockEntityType.Builder.of(
                    PlinthBlockEntity::new,
                    HorizonWeaponsBlocks.PLINTH.get()
            ).build(null)
    );

    public static void init(IEventBus bus) {
        REGISTER.register(bus);
    }
}
