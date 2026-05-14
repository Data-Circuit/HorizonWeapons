package io.github.datacircuit.horizonweapons.effect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

public class AnchorEffect extends MobEffect {
    public AnchorEffect() {
        super(MobEffectCategory.HARMFUL, 0x222222);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int tickCount, int amplification) {
        return true;
    }

    @Override
    public boolean applyEffectTick(@NonNull ServerLevel serverLevel, @NonNull LivingEntity mob, int amplification) {
        if (mob instanceof ServerPlayer sp && sp.isFallFlying()) {
            Vec3 velocity = sp.getDeltaMovement();
            Vec3 newVelocity = velocity.scale(1.0 - .2f);
            sp.setDeltaMovement(newVelocity);
            sp.hurtMarked = true;
        }

        return true;
    }
}
