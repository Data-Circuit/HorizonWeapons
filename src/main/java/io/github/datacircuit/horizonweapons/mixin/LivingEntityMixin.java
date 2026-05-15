package io.github.datacircuit.horizonweapons.mixin;

import io.github.datacircuit.horizonweapons.registry.HorizonWeaponsEffects;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow
    public abstract boolean hasEffect(Holder<MobEffect> effect);

    @Shadow
    public abstract boolean removeEffect(Holder<MobEffect> effect);

    @Shadow
    public abstract double getAttributeValue(Holder<Attribute> attribute);

    @Inject(method = "getArmorValue", at = @At("HEAD"), cancellable = true)
    public void getArmorValue(CallbackInfoReturnable<Integer> cir) {
        if (this.hasEffect(HorizonWeaponsEffects.VITALITY))
            cir.setReturnValue(Mth.floor(this.getAttributeValue(Attributes.ARMOR)) / 2);
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

    @Inject(method = "travel", at = @At("HEAD"))
    private void eliminateDrag(Vec3 input, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity.hasEffect(HorizonWeaponsEffects.INERTIA) && entity.isFallFlying()) {
            Vec3 velocity = entity.getDeltaMovement();
            Vec3 newVelocity = velocity.scale(1.01);
            entity.setDeltaMovement(newVelocity);
        }
    }

    @Inject(method = "hurtServer", at = @At("TAIL"))
    private void stripInertiaOnDamage(ServerLevel level, DamageSource source, float dmg, CallbackInfoReturnable<Boolean> cir) {
        if (this.hasEffect(HorizonWeaponsEffects.INERTIA)) {
            this.removeEffect(HorizonWeaponsEffects.INERTIA);
        }
    }
}
