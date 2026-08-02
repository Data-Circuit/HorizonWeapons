package io.github.datacircuit.horizonweapons.item.weapon;

import io.github.datacircuit.horizonweapons.HorizonWeapons;
import io.github.datacircuit.horizonweapons.gods.God;
import io.github.datacircuit.horizonweapons.item.components.tooltip.BellOfGivingActiveTooltip;
import io.github.datacircuit.horizonweapons.particle.ParticleManager;
import io.github.datacircuit.horizonweapons.registry.HorizonWeaponsDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jspecify.annotations.NonNull;

import java.util.function.Consumer;

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
            if (level.isClientSide()) {
                if (!player.getItemInHand(hand).get(HorizonWeaponsDataComponents.BELL_OF_GIVING).isActive()) {
                    player.playSound(SoundEvents.BEACON_ACTIVATE, 2f, 0.7f);
                    ParticleManager.bell_of_giving(level, player.position());
                    for (Entity e : level.getEntities(null, AABB.ofSize(player.position(), 60, 60, 60)).stream().filter(e -> e instanceof Player).toList()) {
                        e.playSound(SoundEvents.BELL_RESONATE, 2f, 0.7f);
                    }
                } else {
                    player.playSound(SoundEvents.BEACON_DEACTIVATE, 2f, 0.7f);
                }
            }
            player.getItemInHand(hand).set(HorizonWeaponsDataComponents.BELL_OF_GIVING, new BellOfGivingActiveTooltip(
                    !player.getItemInHand(hand).get(HorizonWeaponsDataComponents.BELL_OF_GIVING).isActive()));
        }
        return super.use(level, player, hand);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay displayComponent, Consumer<Component> textConsumer, TooltipFlag type) {
        textConsumer.accept(Component.empty());
        textConsumer.accept(Component.translatable("itemTooltip.horizonweapons.bell_of_giving.1").withStyle(ChatFormatting.LIGHT_PURPLE));
        textConsumer.accept(Component.translatable("itemTooltip.horizonweapons.bell_of_giving.2").withStyle(ChatFormatting.LIGHT_PURPLE));
        textConsumer.accept(Component.translatable("itemTooltip.horizonweapons.bell_of_giving.3").withStyle(ChatFormatting.LIGHT_PURPLE));
    }

    @Override
    public God getOriginalOwner() {
        return God.CHARITY;
    }
}
