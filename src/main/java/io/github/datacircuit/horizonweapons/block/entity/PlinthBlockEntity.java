package io.github.datacircuit.horizonweapons.block.entity;

import io.github.datacircuit.horizonweapons.inventory.IInventory;
import io.github.datacircuit.horizonweapons.registry.HorizonWeaponsBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class PlinthBlockEntity extends BlockEntity implements IInventory {
    private final NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);

    public PlinthBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(HorizonWeaponsBlockEntities.PLINTH_BLOCK_ENTITY, worldPosition, blockState);
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        ContainerHelper.loadAllItems(input, this.items);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        ContainerHelper.saveAllItems(output, this.items);
        super.saveAdditional(output);
    }
}
