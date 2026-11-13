package io.github.datacircuit.horizonweapons.item.weapon;

import io.github.datacircuit.horizonweapons.HorizonWeapons;
import io.github.datacircuit.horizonweapons.gods.God;
import io.github.datacircuit.horizonweapons.registry.HorizonWeaponsDamageTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.Unbreakable;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LidlessNeedleWeapon extends HorizonWeapon {

    public static final Tier MATERIAL = new SimpleTier(
            BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            1,
            0f,
            0f,
            22,
            () -> Ingredient.EMPTY
    );

    public LidlessNeedleWeapon(Properties properties) {
        super(
                MATERIAL,
                SwordItem.createAttributes(MATERIAL, 0.0f, -2.2f)
                        .withModifierAdded(
                                Attributes.ENTITY_INTERACTION_RANGE,
                                new AttributeModifier(
                                        HorizonWeapons.id("lidless_needle_attack_range"),
                                        0.5d,
                                        AttributeModifier.Operation.ADD_VALUE
                                ),
                                EquipmentSlotGroup.MAINHAND
                        ),
                properties.component(DataComponents.UNBREAKABLE, new Unbreakable(false))
        );
    }

    @Override
    public void postHurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        if (!attacker.level().isClientSide() && attacker.level() instanceof ServerLevel serverLevel) {
            DamageSource trueDamageSource = new DamageSource(
                    serverLevel.registryAccess()
                            .lookupOrThrow(Registries.DAMAGE_TYPE)
                            .getOrThrow(HorizonWeaponsDamageTypes.TRUE_DAMAGE_KEY),
                    attacker
            );
            target.invulnerableTime = 0;
            target.hurt(trueDamageSource, 4f);
        }

        super.postHurtEnemy(stack, target, attacker);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, List<Component> tooltipComponents, @NotNull TooltipFlag type) {
        tooltipComponents.add(Component.empty());
        tooltipComponents.add(Component.translatable("itemTooltip.horizonweapons.lidless_needle.1").withStyle(ChatFormatting.LIGHT_PURPLE));
        tooltipComponents.add(Component.translatable("itemTooltip.horizonweapons.lidless_needle.2").withStyle(ChatFormatting.LIGHT_PURPLE));
        tooltipComponents.add(Component.translatable("itemTooltip.horizonweapons.lidless_needle.3").withStyle(ChatFormatting.LIGHT_PURPLE));
    }

    @Override
    public God getOriginalOwner() {
        return God.SECRETS;
    }
}
