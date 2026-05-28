package io.github.datacircuit.horizonweapons.client.render;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;

public class PlinthBlockEntityRenderState extends BlockEntityRenderState {
    private ItemStack stack;
    private Direction direction;

    final ItemStackRenderState itemStackRenderState = new ItemStackRenderState();

    public ItemStack getStack() {
        return stack;
    }

    public void setStack(ItemStack stack) {
        this.stack = stack;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
