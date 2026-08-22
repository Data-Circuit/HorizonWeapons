package io.github.datacircuit.horizonweapons.item.apis;

import net.minecraft.server.level.ServerPlayer;

public interface RotItemApi {
    void horizonWeapons$effectItem(ServerPlayer attacker, int duration);
    int horizonWeapons$getRemainingDuration();
    void horizonWeapons$setRemainingDuration(int duration);
    void horizonWeapons$clearEffect();

    int horizonWeapons$getEffectPower();
    void horizonWeapons$setEffectPower(int power);
}
