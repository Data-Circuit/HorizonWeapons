package io.github.datacircuit.horizonweapons.effect;

import io.github.datacircuit.horizonweapons.HorizonWeapons;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RotEffect extends MobEffect {

    public RotEffect() {
        super(MobEffectCategory.HARMFUL, 0x75481d);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int tickCount, int amplification) {
        return (tickCount % 20) == 0;
    }

    @Override
    public boolean applyEffectTick(@NotNull LivingEntity mob, int amplification) {
        if (mob instanceof ServerPlayer player) {
            List<ItemStack> candidates = new ArrayList<>();

            for (EquipmentSlot slot : EquipmentSlot.values()) {
                ItemStack stack = player.getItemBySlot(slot);
                if (!stack.isEmpty()) {
                    candidates.add(stack);
                }
            }

            List<ItemStack> items = candidates.stream()
                    .filter(Objects::nonNull)
                    .filter(stack -> !stack.isEmpty())
                    .filter(stack -> {
                        // Safe namespace check via BuiltInRegistries
                        String namespace = BuiltInRegistries.ITEM.getKey(stack.getItem()).getNamespace();
                        return "minecraft".equals(namespace);
                    })
                    .filter(stack -> stack.getMaxDamage() > 0)
                    .toList();

            HorizonWeapons.LOGGER.info("Rot candidates found: {}", items.size());

            if (items.isEmpty()) {
                return super.applyEffectTick(mob, amplification);
            }

            int index = mob.level().getRandom().nextInt(items.size());
            ItemStack targetStack = items.get(index);
            HorizonWeapons.LOGGER.info("Rotting item: {}", targetStack);

            EquipmentSlot breakSlot = getEquipmentSlotForStack(player, targetStack);

            targetStack.hurtAndBreak(3, (ServerLevel) mob.level(), player, item -> player.onEquippedItemBroken(item, breakSlot));
        }
        return super.applyEffectTick(mob, amplification);
    }

    private EquipmentSlot getEquipmentSlotForStack(ServerPlayer player, ItemStack stack) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (player.getItemBySlot(slot) == stack) {
                return slot;
            }
        }
        return EquipmentSlot.MAINHAND;
    }
}
