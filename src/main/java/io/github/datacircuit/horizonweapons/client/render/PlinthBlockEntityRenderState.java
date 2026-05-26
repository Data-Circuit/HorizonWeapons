package io.github.datacircuit.horizonweapons.client.render;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.item.ItemStack;

public class PlinthBlockEntityRenderState extends BlockEntityRenderState {
    private ItemStack stack;

    final ItemStackRenderState itemStackRenderState = new ItemStackRenderState();

    public ItemStack getStack() {
        return stack;
    }

    public void setStack(ItemStack stack) {
        this.stack = stack;
    }
}
