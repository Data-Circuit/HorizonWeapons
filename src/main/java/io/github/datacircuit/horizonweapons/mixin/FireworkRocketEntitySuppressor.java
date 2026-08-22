package io.github.datacircuit.horizonweapons.mixin;

import io.github.datacircuit.horizonweapons.registry.HorizonWeaponsEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FireworkRocketEntity.class)
public class FireworkRocketEntitySuppressor {
    @Shadow
    private LivingEntity attachedToEntity;

    @Shadow
    private int lifetime;

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if (attachedToEntity != null) {
            if (attachedToEntity.isFallFlying() && attachedToEntity.hasEffect(HorizonWeaponsEffects.ANCHOR))
                lifetime = 0;
        }
    }
}
