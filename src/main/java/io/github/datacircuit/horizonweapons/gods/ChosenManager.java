package io.github.datacircuit.horizonweapons.gods;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import io.github.datacircuit.horizonweapons.HorizonWeapons;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.saveddata.SavedData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@EventBusSubscriber
public class ChosenManager extends SavedData {
    private static ChosenManager INSTANCE;

    private static final Codec<ChosenManager> CODEC = Codec.unboundedMap(UUIDUtil.STRING_CODEC, God.CODEC).xmap(
            ChosenManager::new,
            ChosenManager::getGods
    );

    public static ChosenManager create() {
        return new ChosenManager();
    }

    public static ChosenManager load(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        ChosenManager data;
        data = CODEC.decode(RegistryOps.create(NbtOps.INSTANCE, lookupProvider), tag).result().get().getFirst();
        return data;
    }

    @Override
    public @NotNull CompoundTag save(@NotNull CompoundTag compoundTag, HolderLookup.@NotNull Provider provider) {
        DataResult<Tag> res = CODEC.encode(this, RegistryOps.create(NbtOps.INSTANCE, provider), compoundTag);
        return (CompoundTag) res.result().get();
    }

    public static void loadInstance(MinecraftServer server) {
        HorizonWeapons.LOGGER.info("Loading data");
        ServerLevel level = server.getLevel(ServerLevel.OVERWORLD);

        if (level == null) {
            INSTANCE = new ChosenManager();
        }

        INSTANCE = level.getDataStorage().computeIfAbsent(new Factory<>(ChosenManager::create, ChosenManager::load), "gods");
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
        setDirty();
    }

    public God getGod(Player player) {
        if (!godsMapping.containsKey(player.getUUID())) return null;
        return godsMapping.get(player.getUUID());
    }

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        ChosenManager.loadInstance(event.getServer());
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        HorizonWeapons.LOGGER.info("Attempting to assign god");
        ChosenManager.getInstance().assign(event.getEntity());
    }
}
