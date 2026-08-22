package io.github.datacircuit.horizonweapons.block.entity;

import io.github.datacircuit.horizonweapons.inventory.IInventory;
import io.github.datacircuit.horizonweapons.item.weapon.DeathbringerScytheWeapon;
import io.github.datacircuit.horizonweapons.particle.ParticleManager;
import io.github.datacircuit.horizonweapons.registry.HorizonWeaponsBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class PlinthBlockEntity extends BlockEntity implements IInventory {
    private final NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);

    public PlinthBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(HorizonWeaponsBlockEntities.PLINTH_BLOCK_ENTITY.get(), worldPosition, blockState);
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);
        ContainerHelper.loadAllItems(tag, this.items, registries);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);
        ContainerHelper.saveAllItems(tag, this.items, registries);
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (!level.isClientSide()) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider registries) {
        return saveWithoutMetadata(registries);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, PlinthBlockEntity entity) {
        if (!entity.items.isEmpty()) {
            if (entity.items.getFirst().getItem().getClass().equals(DeathbringerScytheWeapon.class)) {
                ParticleManager.bell_of_giving(level, pos.getCenter());
            }
        }
    }
}
