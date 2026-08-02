package io.github.datacircuit.horizonweapons.item.components.tooltip;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.function.Consumer;

public record RotTooltip(int duration, int damage) implements TooltipProvider {
    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> consumer, TooltipFlag flag, DataComponentGetter components) {
        consumer.accept(Component.literal("Rotting").withStyle(ChatFormatting.RED, ChatFormatting.BOLD));
        consumer.accept(Component.literal("%.1fs remaining".formatted((double) duration / 20)).withStyle(ChatFormatting.GRAY));
        consumer.accept(Component.literal("%d damage applied".formatted(damage)).withStyle(ChatFormatting.GRAY));
    }

    public static final Codec<RotTooltip> CODEC = RecordCodecBuilder.create( builder -> builder.group(
            Codec.INT.fieldOf("duration").forGetter(RotTooltip::duration),
            Codec.INT.fieldOf("damage").forGetter(RotTooltip::damage)
    ).apply(builder, RotTooltip::new));
}
