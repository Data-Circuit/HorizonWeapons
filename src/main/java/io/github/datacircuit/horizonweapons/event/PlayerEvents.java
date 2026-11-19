package io.github.datacircuit.horizonweapons.event;

import io.github.datacircuit.horizonweapons.particle.ParticleManager;
import io.github.datacircuit.horizonweapons.registry.HorizonWeaponsDataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

import java.util.List;

@EventBusSubscriber
public class PlayerEvents {

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        if ((LivingEntity)event.getEntity() instanceof ServerPlayer player) {
            if (player.getHealth() <= 0) {
                List<Entity> entities = player.level().getEntities(player, AABB.ofSize(player.position(), 60, 60, 60));

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
                    ParticleManager.bell_of_giving(player.level(), player.position());
                    player.playSound(SoundEvents.BELL_RESONATE, 2f, 0.7f);
                    player.setHealth(player.getMaxHealth());
                    DimensionTransition loc = player.findRespawnPositionAndUseSpawnBlock(true, ignored -> {});
                    player.changeDimension(loc);
                    event.setCanceled(true);
                }
            }
        }
    }
}
