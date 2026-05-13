package io.github.datacircuit.horizonweapons.mixin;

import io.github.datacircuit.horizonweapons.registry.HorizonWeaponsEffects;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow
    public abstract boolean hasEffect(Holder<MobEffect> effect);

    @Inject(method = "getArmorValue", at = @At("HEAD"), cancellable = true)
    public void getArmorValue(CallbackInfoReturnable<Integer> cir) {
        if (this.hasEffect(HorizonWeaponsEffects.VITALITY))
            cir.setReturnValue(0);
    }
}
