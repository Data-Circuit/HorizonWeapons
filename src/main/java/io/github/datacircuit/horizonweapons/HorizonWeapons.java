package io.github.datacircuit.horizonweapons;

import io.github.datacircuit.horizonweapons.registry.HorizonWeaponsEffects;
import net.fabricmc.api.ModInitializer;

public class HorizonWeapons implements ModInitializer {

    public static final String MOD_ID = "horizonweapons";

    @Override
    public void onInitialize() {
        HorizonWeaponsEffects.init();
    }
}
