package io.github.datacircuit.horizonweapons.mixin;

import mod.chloeprime.aaaparticles.client.Debug;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Debug.class)
public class AAAParticlesDebugMixin {
    @Inject(method = "keyPressed0", at = @At("HEAD"), cancellable = true)
    private static void keyPressed0(Minecraft client, int action, KeyEvent keyEvent, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "leftClick", at = @At("HEAD"), cancellable = true)
    private static void leftClick(Player player, InteractionHand hand, CallbackInfo ci) {
        ci.cancel();
    }
}
