package io.github.datacircuit.horizonweapons.item.weapon;

import io.github.datacircuit.horizonweapons.HorizonWeapons;
import io.github.datacircuit.horizonweapons.gods.God;
import io.github.datacircuit.horizonweapons.registry.HorizonWeaponsDamageTypes;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Unit;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.AttackRange;
import org.jspecify.annotations.NonNull;

public class LidlessNeedleWeapon extends HorizonWeapon {

    public static final ToolMaterial MATERIAL = new ToolMaterial(
            BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            1,
            0f,
            0f,
            22,
            TagKey.create(BuiltInRegistries.ITEM.key(), HorizonWeapons.id("repairs_unbreakable"))
    );

    public LidlessNeedleWeapon(Properties properties) {
        super(MATERIAL, 0.f, -2.2f, properties.component(DataComponents.UNBREAKABLE, Unit.INSTANCE)
                .component(DataComponents.ATTACK_RANGE, new AttackRange(0.f, 3.5f, 0.f, 3.5f, .3f, 1.f)));
    }

    @Override
    public void hurtEnemy(@NonNull ItemStack stack, @NonNull LivingEntity target, @NonNull LivingEntity attacker) {
        if (!attacker.level().isClientSide() && attacker.level() instanceof ServerLevel serverLevel) {

            DamageSource trueDamageSource = new DamageSource(
                    serverLevel.registryAccess()
                            .lookupOrThrow(Registries.DAMAGE_TYPE)
                            .getOrThrow(HorizonWeaponsDamageTypes.TRUE_DAMAGE_KEY),
                    attacker
            );

            target.hurtServer(serverLevel, trueDamageSource, 7.f);
        }

        super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public God getOriginalOwner() {
        return God.SECRETS;
    }
}
