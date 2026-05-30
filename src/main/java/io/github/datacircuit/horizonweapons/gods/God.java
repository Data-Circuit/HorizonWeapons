package io.github.datacircuit.horizonweapons.gods;

import com.mojang.serialization.Codec;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;
import org.jspecify.annotations.NonNull;

public enum God implements StringRepresentable {
    SOUND("god.horizonweapons.sound"),
    TIME("god.horizonweapons.time"),
    GRIEVING("god.horizonweapons.grieving"),
    JUSTICE("god.horizonweapons.justice"),
    ROT("god.horizonweapons.rot"),
    POTENTIAL("god.horizonweapons.potential"),
    HUNGER("god.horizonweapons.hunger"),
    DEATH("god.horizonweapons.death"),
    SECRETS("god.horizonweapons.secrets"),
    CHARITY("god.horizonweapons.charity");

    private final String key;

    God(String key) {
        this.key = key;
    }

    Component getText() {
        return Component.translatable(key);
    }

    Component getChosenText(Component playerName) {
        return Component.translatable(key + ".chosen", playerName);
    }

    public static final Codec<God> CODEC = StringRepresentable.fromEnum(God::values);

    @Override
    public @NonNull String getSerializedName() {
        return this.key;
    }
}
