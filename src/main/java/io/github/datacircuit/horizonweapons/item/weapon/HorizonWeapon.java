package io.github.datacircuit.horizonweapons.item.weapon;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;

public abstract class HorizonWeapon extends Item {

    protected final ToolMaterial material;

    protected HorizonWeapon(ToolMaterial material, float attackDamage, float attackSpeed, Properties properties) {
        super(properties.sword(material, attackDamage, attackSpeed));
        this.material = material;
    }
}
