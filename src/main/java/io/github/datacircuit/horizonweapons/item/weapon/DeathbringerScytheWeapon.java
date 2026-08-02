package io.github.datacircuit.horizonweapons.item.weapon;

import io.github.datacircuit.horizonweapons.HorizonWeapons;
import io.github.datacircuit.horizonweapons.gods.God;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.AttackRange;
import org.jspecify.annotations.NonNull;

public class DeathbringerScytheWeapon extends HorizonWeapon {
    public static final ToolMaterial MATERIAL = new ToolMaterial(
            BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            1,
            0f,
            0f,
            22,
            TagKey.create(BuiltInRegistries.ITEM.key(), HorizonWeapons.id("repairs_unbreakable"))
    );

    public DeathbringerScytheWeapon(Properties properties) {
        super(MATERIAL, 6.f, -1f,
                properties.component(DataComponents.UNBREAKABLE, Unit.INSTANCE)
                        .component(DataComponents.ATTACK_RANGE,
                                new AttackRange(0.f, 10.f, 0.f, 10.0f, .3f, 1.f)));
    }

    @Override
    public void hurtEnemy(@NonNull ItemStack itemStack, @NonNull LivingEntity mob, LivingEntity attacker) {
        if (attacker.isShiftKeyDown()) {
            double distance = mob.distanceTo(attacker);
            double factor = Math.log(distance);
            mob.setDeltaMovement(attacker.getHeadLookAngle().scale(factor).reverse());
        }
    }

    @Override
    public God getOriginalOwner() {
        return God.DEATH;
    }
}
