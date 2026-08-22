package io.github.datacircuit.horizonweapons.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.ServerStatsCounter;
import net.minecraft.stats.Stats;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

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
