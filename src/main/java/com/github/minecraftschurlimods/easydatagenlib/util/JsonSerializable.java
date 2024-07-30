package com.github.minecraftschurlimods.easydatagenlib.util;

import com.google.gson.JsonElement;
import net.minecraft.core.HolderLookup;

/**
 * Helper interface for classes that can be serialized to JSON.
 */
public interface JsonSerializable {
    JsonElement toJson(HolderLookup.Provider registries);
}
