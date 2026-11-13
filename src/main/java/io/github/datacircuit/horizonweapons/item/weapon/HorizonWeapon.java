package io.github.datacircuit.horizonweapons.item.weapon;

import io.github.datacircuit.horizonweapons.gods.God;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.jetbrains.annotations.NotNull;

public abstract class HorizonWeapon extends Item {

    protected final Tier material;

    protected HorizonWeapon(Tier material, float attackDamage, float attackSpeed, Properties properties) {
        this(material, SwordItem.createAttributes(material, attackDamage, attackSpeed), properties);
    }

    protected HorizonWeapon(Tier material, ItemAttributeModifiers attributes, Properties properties) {
        super(properties
                .attributes(attributes)
                .component(DataComponents.TOOL, SwordItem.createToolProperties())
        );
        this.material = material;
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        return true;
    }

    abstract public God getOriginalOwner();
}
