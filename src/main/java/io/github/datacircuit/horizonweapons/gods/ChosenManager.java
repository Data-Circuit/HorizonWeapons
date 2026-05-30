package io.github.datacircuit.horizonweapons.gods;

import com.mojang.serialization.Codec;
import io.github.datacircuit.horizonweapons.HorizonWeapons;
import io.github.datacircuit.horizonweapons.block.entity.PlinthBlockEntity;
import io.github.datacircuit.horizonweapons.item.weapon.HorizonWeapon;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLevelEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.core.UUIDUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import org.spongepowered.asm.mixin.injection.Inject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChosenManager extends SavedData {
    private static ChosenManager INSTANCE;

    private static final Codec<ChosenManager> CODEC = Codec.unboundedMap(UUIDUtil.STRING_CODEC, God.CODEC).xmap(
            ChosenManager::new,
            ChosenManager::getGods
    );

    private static final SavedDataType<ChosenManager> TYPE = new SavedDataType<>(
            HorizonWeapons.id("chosen"),
            ChosenManager::new,
            CODEC,
            null
    );

    public static void loadInstance(MinecraftServer server) {
        HorizonWeapons.LOGGER.info("Loading data");
        ServerLevel level = server.getLevel(ServerLevel.OVERWORLD);

        if (level == null) {
            INSTANCE = new ChosenManager();
        }

        INSTANCE = level.getDataStorage().computeIfAbsent(TYPE);
    }

    private Map<UUID, God> getGods() {
        return godsMapping;
    }

    private HashMap<UUID, God> godsMapping = new HashMap<>();

    private ChosenManager() {}

    private ChosenManager(Map<UUID, God> map) {
        godsMapping = new HashMap<>(map);
    }

    public static ChosenManager getInstance() {
        return INSTANCE;
    }

    public void assign(Player player) {
        if (godsMapping.containsKey(player.getUUID())) return;

        int idx = player.level().getRandom().nextInt(0, God.values().length);

        God god = God.values()[idx];

        godsMapping.put(player.getUUID(), god);
        player.sendSystemMessage(god.getChosenText(player.getDisplayName()));
    }

    public God getGod(Player player) {
        if (!godsMapping.containsKey(player.getUUID())) return null;
        return godsMapping.get(player.getUUID());
    }

    public static void initCommon() {
        ServerLifecycleEvents.SERVER_STARTED.register(ChosenManager::loadInstance);
        ServerPlayerEvents.JOIN.register(player -> {
            HorizonWeapons.LOGGER.info("Attempting to assign god");
            ChosenManager.getInstance().assign(player);
        });
    }
}
