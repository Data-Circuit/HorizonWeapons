package io.github.datacircuit.horizonweapons.mixin;

import io.github.datacircuit.horizonweapons.registry.HorizonWeaponsEffects;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow
    public abstract boolean hasEffect(Holder<MobEffect> effect);

    @Inject(method = "getArmorValue", at = @At("HEAD"), cancellable = true)
    public void getArmorValue(CallbackInfoReturnable<Integer> cir) {
        if (this.hasEffect(HorizonWeaponsEffects.VITALITY))
            cir.setReturnValue(0);
    }

    @Inject(method = "hurtServer", at = @At("HEAD"), cancellable = true)
    public void hurtServer(ServerLevel level, DamageSource source, float damage, CallbackInfoReturnable<Boolean> cir) {
        if (source.getEntity() instanceof LivingEntity entity && entity.hasEffect(HorizonWeaponsEffects.CONFUSION)) {
            /*
            Level 0 = 20%
            Level 1 = 25%
            Level 2 = 30%
            Level 3 = 35%
             */
            int amplifier = entity.getEffect(HorizonWeaponsEffects.CONFUSION).getAmplifier();

            int percentBlock;
            if (amplifier >= 4)
                percentBlock = 100;
            else {
                percentBlock = 20 + 5 * amplifier;
            }

            boolean shouldBlock = (level.getRandom().nextIntBetweenInclusive(0, 100) < percentBlock);

            if (shouldBlock) cir.setReturnValue(false);
        }
    }
}
