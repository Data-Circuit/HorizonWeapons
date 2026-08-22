package io.github.datacircuit.horizonweapons.item.weapon;

import io.github.datacircuit.horizonweapons.gods.God;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public abstract class HorizonWeapon extends Item {

    protected final Tier material;

    protected HorizonWeapon(Tier material, float attackDamage, float attackSpeed, Properties properties) {
        super(properties.attributes(SwordItem.createAttributes(material, attackDamage, attackSpeed)));
        this.material = material;
    }

    abstract public God getOriginalOwner();
}
