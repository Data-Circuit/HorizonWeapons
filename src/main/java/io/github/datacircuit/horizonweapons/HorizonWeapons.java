package io.github.datacircuit.horizonweapons;

import io.github.datacircuit.horizonweapons.registry.*;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HorizonWeapons implements ModInitializer {

    public static final String MOD_ID = "horizonweapons";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        HorizonWeaponsEffects.init();
        HorizonWeaponsItems.init();
        HorizonWeaponsPotions.init();
        HorizonWeaponsBlocks.init();
        HorizonWeaponsBlockEntities.init();
    }

    public static Identifier id(String name) {
        return Identifier.fromNamespaceAndPath(MOD_ID, name);
    }
}
