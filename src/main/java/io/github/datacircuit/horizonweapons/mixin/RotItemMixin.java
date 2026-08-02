package io.github.datacircuit.horizonweapons.mixin;

import io.github.datacircuit.horizonweapons.HorizonWeapons;
import io.github.datacircuit.horizonweapons.item.apis.RotItemApi;
import io.github.datacircuit.horizonweapons.item.components.tooltip.RotTooltip;
import io.github.datacircuit.horizonweapons.item.weapon.HorizonWeapon;
import io.github.datacircuit.horizonweapons.registry.HorizonWeaponsDataComponents;
import net.fabricmc.fabric.api.item.v1.FabricItemStack;
import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.TypedDataComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class RotItemMixin implements RotItemApi, DataComponentHolder, ItemInstance, FabricItemStack {
    @Shadow
    public abstract void applyDamage(int newDamage, @Nullable ServerPlayer player, Consumer<Item> onBreak);

    @Shadow
    public abstract int getDamageValue();

    @Shadow
    public abstract Item getItem();

    @Shadow
    public abstract void setDamageValue(int value);

    @Shadow
    public abstract <T> @Nullable T set(TypedDataComponent<T> value);

    @Shadow
    public abstract <T> @Nullable T set(DataComponentType<T> type, @Nullable T value);

    @Shadow
    public abstract <T> @Nullable T remove(DataComponentType<? extends T> type);

    @Unique int rotPowerLevel = 0;
    @Unique int rotRemainingDuration = 0;
    @Unique int rotAccumulatedDamage = 0;
    @Unique boolean rotActive = false;
    @Unique
    ServerPlayer rotAttacker = null;

    @Inject(method = "inventoryTick", at = @At("HEAD"))
    void rotInventoryTick(Level level, Entity owner, EquipmentSlot slot, CallbackInfo ci) {
        if (rotActive) {
            set(HorizonWeaponsDataComponents.ROTTING_ITEM, new RotTooltip(rotRemainingDuration, rotAccumulatedDamage));

            rotRemainingDuration--;

            if (rotRemainingDuration <= 0) {
                rotActive = false;
                remove(HorizonWeaponsDataComponents.ROTTING_ITEM);
            } else {
                applyDamage(getDamageValue() + rotPowerLevel, null, _ -> {});
                rotAccumulatedDamage += rotPowerLevel;
            }
        }
    }

    @Override
    public void effectItem(ServerPlayer attacker, int duration) {
        if (rotActive) rotPowerLevel++;
        else {
            rotPowerLevel = 1;
            rotRemainingDuration = duration;
            rotActive = true;
            rotAttacker = attacker;
        }
    }

    @Override
    public int getRemainingDuration() {
        return rotRemainingDuration;
    }

    @Override
    public void setRemainingDuration(int duration) {
        rotRemainingDuration = duration;
    }

    @Override
    public void clearEffect() {
        rotActive = false;
        rotRemainingDuration = 0;
        rotPowerLevel = 0;
    }

    @Override
    public int getEffectPower() {
        return rotPowerLevel;
    }

    @Override
    public void setEffectPower(int power) {
        rotPowerLevel = power;
    }
}
