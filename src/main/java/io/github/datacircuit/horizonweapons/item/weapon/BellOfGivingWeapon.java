package io.github.datacircuit.horizonweapons.item.weapon;

import io.github.datacircuit.horizonweapons.HorizonWeapons;
import io.github.datacircuit.horizonweapons.gods.God;
import io.github.datacircuit.horizonweapons.item.components.tooltip.BellOfGivingActiveTooltip;
import io.github.datacircuit.horizonweapons.registry.HorizonWeaponsDataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

public class BellOfGivingWeapon extends HorizonWeapon {
    public static final ToolMaterial MATERIAL = new ToolMaterial(
            BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            1,
            0f,
            0f,
            22,
            TagKey.create(BuiltInRegistries.ITEM.key(), HorizonWeapons.id("repairs_unbreakable"))
    );

    public BellOfGivingWeapon(Properties properties) {
        super(MATERIAL, 0f, 0f, properties);
    }

    @Override
    public @NonNull InteractionResult use(@NonNull Level level, Player player, @NonNull InteractionHand hand) {
        if (player.getItemInHand(hand).has(HorizonWeaponsDataComponents.BELL_OF_GIVING)) {
            player.getItemInHand(hand).set(HorizonWeaponsDataComponents.BELL_OF_GIVING, new BellOfGivingActiveTooltip(
                    !player.getItemInHand(hand).get(HorizonWeaponsDataComponents.BELL_OF_GIVING).isActive()));
        }
        return super.use(level, player, hand);
    }

    @Override
    public God getOriginalOwner() {
        return God.CHARITY;
    }
}
