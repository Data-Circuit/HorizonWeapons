package io.github.datacircuit.horizonweapons.mixin;

import io.github.datacircuit.horizonweapons.item.apis.RotItemApi;
import io.github.datacircuit.horizonweapons.item.components.tooltip.RotTooltip;
import io.github.datacircuit.horizonweapons.registry.HorizonWeaponsDataComponents;
import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(ItemStack.class)
public abstract class RotItemMixin implements RotItemApi, DataComponentHolder {

    @Shadow
    @Nullable
    public abstract <T> T set(DataComponentType<? super T> component, @org.jetbrains.annotations.Nullable T value);

    @Shadow
    @Nullable
    public abstract <T> T remove(DataComponentType<? extends T> component);

    @Shadow
    public abstract void setDamageValue(int damage);

    @Shadow
    public abstract int getDamageValue();

    @Unique int horizonWeapons$rotPowerLevel = 0;
    @Unique int horizonWeapons$rotRemainingDuration = 0;
    @Unique int horizonWeapons$rotAccumulatedDamage = 0;
    @Unique boolean horizonWeapons$rotActive = false;
    @Unique
    ServerPlayer horizonWeapons$rotAttacker = null;

    @Inject(method = "inventoryTick", at = @At("HEAD"))
    void rotInventoryTick(Level level, Entity entity, int inventorySlot, boolean isCurrentItem, CallbackInfo ci) {
        if (horizonWeapons$rotActive) {
            set(HorizonWeaponsDataComponents.ROTTING_ITEM.get(), new RotTooltip(horizonWeapons$rotRemainingDuration, horizonWeapons$rotAccumulatedDamage));

            horizonWeapons$rotRemainingDuration--;

            if (horizonWeapons$rotRemainingDuration <= 0) {
                horizonWeapons$rotActive = false;
                remove(HorizonWeaponsDataComponents.ROTTING_ITEM.get());
            } else {
                setDamageValue(getDamageValue() + horizonWeapons$rotPowerLevel);
                horizonWeapons$rotAccumulatedDamage += horizonWeapons$rotPowerLevel;
            }
        }
    }

    @Override
    public void horizonWeapons$effectItem(ServerPlayer attacker, int duration) {
        if (horizonWeapons$rotActive) horizonWeapons$rotPowerLevel++;
        else {
            horizonWeapons$rotPowerLevel = 1;
            horizonWeapons$rotRemainingDuration = duration;
            horizonWeapons$rotActive = true;
            horizonWeapons$rotAttacker = attacker;
        }
    }

    @Override
    public int horizonWeapons$getRemainingDuration() {
        return horizonWeapons$rotRemainingDuration;
    }

    @Override
    public void horizonWeapons$setRemainingDuration(int duration) {
        horizonWeapons$rotRemainingDuration = duration;
    }

    @Override
    public void horizonWeapons$clearEffect() {
        horizonWeapons$rotActive = false;
        horizonWeapons$rotRemainingDuration = 0;
        horizonWeapons$rotPowerLevel = 0;
    }

    @Override
    public int horizonWeapons$getEffectPower() {
        return horizonWeapons$rotPowerLevel;
    }

    @Override
    public void horizonWeapons$setEffectPower(int power) {
        horizonWeapons$rotPowerLevel = power;
    }
}
