package io.github.datacircuit.horizonweapons.item.weapon;

import io.github.datacircuit.horizonweapons.HorizonWeapons;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Unit;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.AttackRange;

public class UnfinishedBladeWeapon extends HorizonWeapon {
    public static final ToolMaterial MATERIAL = new ToolMaterial(
            BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            1,
            0f,
            0f,
            22,
            TagKey.create(BuiltInRegistries.ITEM.key(), HorizonWeapons.id("repairs_unbreakable"))
    );

    public UnfinishedBladeWeapon(Properties properties) {
        super(MATERIAL, 0.f, -2.2f, properties.component(DataComponents.UNBREAKABLE, Unit.INSTANCE)
                .component(DataComponents.ATTACK_RANGE, new AttackRange(0.f, 3.5f, 0.f, 3.5f, .3f, 1.f)));
    }

    protected UnfinishedBladeWeapon(ToolMaterial material, float attackDamage, float attackSpeed, Properties properties) {
        super(material, attackDamage, attackSpeed, properties);
    }
}
