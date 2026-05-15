package io.github.datacircuit.horizonweapons;

import io.github.datacircuit.horizonweapons.registry.HorizonWeaponsEffects;
import io.github.datacircuit.horizonweapons.registry.HorizonWeaponsItems;
import io.github.datacircuit.horizonweapons.registry.HorizonWeaponsPotions;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.Identifier;

public class HorizonWeapons implements ModInitializer {

    public static final String MOD_ID = "horizonweapons";

    @Override
    public void onInitialize() {
        HorizonWeaponsEffects.init();
        HorizonWeaponsItems.init();
        HorizonWeaponsPotions.init();
    }

    public static Identifier id(String name) {
        return Identifier.fromNamespaceAndPath(MOD_ID, name);
    }
}
