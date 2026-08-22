package io.github.datacircuit.horizonweapons.item.weapon;

import io.github.datacircuit.horizonweapons.HorizonWeapons;
import io.github.datacircuit.horizonweapons.gods.God;
import io.github.datacircuit.horizonweapons.item.apis.RotItemApi;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Unbreakable;
import net.neoforged.neoforge.common.SimpleTier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BlackenedRootWeapon extends HorizonWeapon {
    public static final Tier MATERIAL = new SimpleTier(
            BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            1,
            0f,
            0f,
            22,
            () -> null
    );

    public BlackenedRootWeapon(Properties properties) {
        super(MATERIAL, 7.0f, -2.2f,
                properties.component(DataComponents.UNBREAKABLE, new Unbreakable(false))
                        .attributes(
                                ItemAttributeModifiers.builder()
                                        .add(
                                                Attributes.ENTITY_INTERACTION_RANGE,
                                                new AttributeModifier(HorizonWeapons.id("blackend_root_attack_range"), 0.5f, AttributeModifier.Operation.ADD_VALUE),
                                                EquipmentSlotGroup.HAND)
                                        .build()
                        )
        );
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, LivingEntity target, @NotNull LivingEntity attacker) {
        RotItemApi api = (RotItemApi) (Object) target.getMainHandItem();

        api.horizonWeapons$effectItem((ServerPlayer) attacker, 400);
        return false;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, List<Component> components, @NotNull TooltipFlag type) {
        components.add(Component.empty());
        components.add(Component.translatable("itemTooltip.horizonweapons.blackened_root.1").withStyle(ChatFormatting.LIGHT_PURPLE));
        components.add(Component.translatable("itemTooltip.horizonweapons.blackened_root.2").withStyle(ChatFormatting.LIGHT_PURPLE));
        components.add(Component.translatable("itemTooltip.horizonweapons.blackened_root.3").withStyle(ChatFormatting.LIGHT_PURPLE));
    }

    @Override
    public God getOriginalOwner() {
        return God.ROT;
    }
}
