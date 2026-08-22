package io.github.datacircuit.horizonweapons.registry;

import io.github.datacircuit.horizonweapons.HorizonWeapons;
import io.github.datacircuit.horizonweapons.block.PlinthBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class HorizonWeaponsBlocks {
    public static final DeferredRegister.Blocks REGISTER = DeferredRegister.createBlocks(HorizonWeapons.MOD_ID);

    public static final DeferredBlock<PlinthBlock> PLINTH = REGISTER.registerBlock("plinth", PlinthBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SMOOTH_STONE_SLAB).explosionResistance(3600000));

    public static void init(IEventBus bus) {
        REGISTER.register(bus);
    }
}
