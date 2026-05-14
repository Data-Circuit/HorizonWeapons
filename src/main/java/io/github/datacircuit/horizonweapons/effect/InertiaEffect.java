package io.github.datacircuit.horizonweapons.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class InertiaEffect extends MobEffect {
    public InertiaEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xf5a327);}

    @Override
    public boolean shouldApplyEffectTickThisTick(int tickCount, int amplification) {
        return false;
    }
}
