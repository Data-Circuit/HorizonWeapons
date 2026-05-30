package io.github.datacircuit.horizonweapons.mixin;

import io.github.datacircuit.horizonweapons.HorizonWeapons;
import io.github.datacircuit.horizonweapons.block.entity.PlinthBlockEntity;
import io.github.datacircuit.horizonweapons.gods.ChosenManager;
import io.github.datacircuit.horizonweapons.item.weapon.HorizonWeapon;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.ServerStatsCounter;
import net.minecraft.stats.Stats;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {
    @Shadow
    @Final
    private ServerStatsCounter stats;

    @Unique
    public int horizonweapons$getKills() {
        int mobKills = this.stats.getValue(Stats.CUSTOM.get(Stats.MOB_KILLS));
        int playerKills = this.stats.getValue(Stats.CUSTOM.get(Stats.PLAYER_KILLS));

        return mobKills + playerKills;
    }
}
