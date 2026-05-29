package io.github.datacircuit.horizonweapons.item.components.tooltip;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import org.jspecify.annotations.NonNull;

import java.util.function.Consumer;

public record BellOfGivingActiveTooltip(boolean isActive) implements TooltipProvider {
    @Override
    public void addToTooltip(Item.@NonNull TooltipContext context, @NonNull Consumer<Component> consumer, @NonNull TooltipFlag flag, @NonNull DataComponentGetter components) {
        if (isActive) {
            consumer.accept(Component.translatable("item.horizonweapons.bell_of_giving.active"));
        } else {
            consumer.accept(Component.translatable("item.horizonweapons.bell_of_giving.inactive"));
        }
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    public static final Codec<BellOfGivingActiveTooltip> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            Codec.BOOL.fieldOf("isActive").forGetter(BellOfGivingActiveTooltip::isActive)
    ).apply(builder, BellOfGivingActiveTooltip::new));
}
