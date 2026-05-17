package io.github.datacircuit.horizonweapons.effect;

import io.github.datacircuit.horizonweapons.HorizonWeapons;
import io.github.datacircuit.horizonweapons.item.weapon.HorizonWeapon;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RotEffect extends MobEffect {
    public RotEffect() {
        super(MobEffectCategory.HARMFUL, 0x75481d);}

    @Override
    public boolean shouldApplyEffectTickThisTick(int tickCount, int amplification) {
        return (tickCount % 20) == 0;
    }

    @Override
    public boolean applyEffectTick(@NonNull ServerLevel serverLevel, @NonNull LivingEntity mob, int amplification) {
        if (mob instanceof ServerPlayer player) {
            List<ItemStack> items = new ArrayList<>(player.getInventory().getNonEquipmentItems());
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                if (!items.contains(player.getInventory().equipment.get(slot)))
                    items.add(player.getInventory().equipment.get(slot));
            }
            items = items.stream()
                    .filter(Objects::nonNull)
                    .filter(stack -> stack.getItem().getCreatorNamespace(stack).equals("minecraft"))
                    .filter(stack -> stack.getComponents().has(DataComponents.WEAPON) || stack.getComponents().has(DataComponents.EQUIPPABLE))
                    .toList();

            HorizonWeapons.LOGGER.info(items.toString());
/*
            if (items.isEmpty()) {
                return super.applyEffectTick(serverLevel, mob, amplification);
            }
*/
            int index = serverLevel.getRandom().nextInt(items.size());

            ItemStack stack = items.get(index);

            HorizonWeapons.LOGGER.info(stack.toString());

            stack.applyDamage(stack.getDamageValue() - 3, player, item -> {});
        }

        return super.applyEffectTick(serverLevel, mob, amplification);
    }
}
