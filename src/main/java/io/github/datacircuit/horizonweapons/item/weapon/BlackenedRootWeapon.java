package io.github.datacircuit.horizonweapons.item.weapon;

import io.github.datacircuit.horizonweapons.HorizonWeapons;
import io.github.datacircuit.horizonweapons.effect.RotEffect;
import io.github.datacircuit.horizonweapons.registry.HorizonWeaponsEffects;
import io.github.datacircuit.horizonweapons.registry.HorizonWeaponsPotions;
import net.fabricmc.fabric.mixin.entity.event.effect.MobEffectMixin;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Unit;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.AttackRange;
import org.jspecify.annotations.NonNull;

public class BlackenedRootWeapon extends HorizonWeapon {
    public static final ToolMaterial MATERIAL = new ToolMaterial(
            BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            1,
            0f,
            0f,
            22,
            TagKey.create(BuiltInRegistries.ITEM.key(), HorizonWeapons.id("repairs_unbreakable"))
    );

    public BlackenedRootWeapon(Properties properties) {
        super(MATERIAL, 6.0f, -2.4f,
                properties.component(DataComponents.UNBREAKABLE, Unit.INSTANCE)
                        .component(DataComponents.ATTACK_RANGE,
                                new AttackRange(0.f, 3.5f, 0.f, 3.5f, .3f, 1.f)));
    }

    @Override
    public void hurtEnemy(@NonNull ItemStack stack, @NonNull LivingEntity target, @NonNull LivingEntity attacker) {
        target.addEffect(new MobEffectInstance(HorizonWeaponsEffects.ROT, 20, 0));
    }

    protected BlackenedRootWeapon(ToolMaterial material, float attackDamage, float attackSpeed, Properties properties) {
        super(material, attackDamage, attackSpeed, properties);
    }
}
