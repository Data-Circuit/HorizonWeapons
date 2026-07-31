package io.github.datacircuit.horizonweapons.item.apis;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public interface RotItemApi {
    void effectItem(ServerPlayer attacker, int duration);
    int getRemainingDuration();
    void setRemainingDuration(int duration);
    void clearEffect();

    int getEffectPower();
    void setEffectPower(int power);
}
