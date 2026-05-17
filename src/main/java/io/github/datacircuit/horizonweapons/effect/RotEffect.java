package io.github.datacircuit.horizonweapons.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class RotEffect extends MobEffect {
    public RotEffect() {
        super(MobEffectCategory.HARMFUL, 0x960b00);}

    @Override
    public boolean shouldApplyEffectTickThisTick(int tickCount, int amplification) {
        return false;
    }
}
