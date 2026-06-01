package io.github.datacircuit.horizonweapons.item;

import io.github.datacircuit.horizonweapons.gods.God;
import io.github.datacircuit.horizonweapons.item.components.tooltip.BellOfGivingActiveTooltip;
import io.github.datacircuit.horizonweapons.registry.HorizonWeaponsDataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

public class BellOfGiving extends Item {
    public BellOfGiving(Properties properties) {
        super(properties);
    }

    @Override
    public @NonNull InteractionResult use(@NonNull Level level, Player player, @NonNull InteractionHand hand) {
        if (player.getItemInHand(hand).has(HorizonWeaponsDataComponents.BELL_OF_GIVING)) {
            player.getItemInHand(hand).set(HorizonWeaponsDataComponents.BELL_OF_GIVING, new BellOfGivingActiveTooltip(
                    !player.getItemInHand(hand).get(HorizonWeaponsDataComponents.BELL_OF_GIVING).isActive()));
        }
        return super.use(level, player, hand);
    }
}
