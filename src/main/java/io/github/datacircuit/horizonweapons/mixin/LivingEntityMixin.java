package io.github.datacircuit.horizonweapons.mixin;

import io.github.datacircuit.horizonweapons.HorizonWeapons;
import io.github.datacircuit.horizonweapons.particle.ParticleManager;
import io.github.datacircuit.horizonweapons.registry.HorizonWeaponsDataComponents;
import io.github.datacircuit.horizonweapons.registry.HorizonWeaponsEffects;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.waypoints.WaypointTransmitter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Set;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable, WaypointTransmitter {

    public LivingEntityMixin(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Shadow
    public abstract boolean hasEffect(Holder<MobEffect> effect);

    @Shadow
    public abstract boolean removeEffect(Holder<MobEffect> effect);

    @Shadow
    public abstract double getAttributeValue(Holder<Attribute> attribute);

    @Shadow
    public abstract float getHealth();

    @Shadow
    public abstract void setHealth(float health);

    @Shadow
    public abstract float getMaxHealth();

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
            Level 4>= = 100%
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

    @Inject(method = "isDeadOrDying", at = @At("HEAD"))
    private void isDeadOrDying(CallbackInfoReturnable<Boolean> cir) {
        if ((LivingEntity) (Object) this instanceof ServerPlayer player) {
            if (this.getHealth() <= 0) {
                List<Entity> entities = level().getEntities(null, AABB.ofSize(position(), 60, 60, 60));

                List<Player> players = entities.stream().filter(entity -> entity instanceof Player).map(entity -> (Player) entity).toList();

                boolean isPlayerHoldingActiveBellOfGiving = players.stream().anyMatch(mPlayer -> {
                    for (InteractionHand hand : InteractionHand.values()) {
                        if (mPlayer.getItemInHand(hand).has(HorizonWeaponsDataComponents.BELL_OF_GIVING)) {
                            return mPlayer.getItemInHand(hand).get(HorizonWeaponsDataComponents.BELL_OF_GIVING).isActive();
                        }
                    }
                    return false;
                });

                if (isPlayerHoldingActiveBellOfGiving) {
                    ParticleManager.bell_of_giving(level(), position());
                    playSound(SoundEvents.BELL_RESONATE, 2f, 0.7f);
                    setHealth(getMaxHealth());
                    teleport(player.findRespawnPositionAndUseSpawnBlock(false, _ -> {}));
                }
            }
        }
    }
}
