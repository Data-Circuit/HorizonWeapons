package io.github.datacircuit.horizonweapons.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class VitalityEffect extends MobEffect {
    public VitalityEffect() {
        super(MobEffectCategory.HARMFUL, 0x7e21ce);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int tickCount, int amplification) {
        return false;
    }
}
