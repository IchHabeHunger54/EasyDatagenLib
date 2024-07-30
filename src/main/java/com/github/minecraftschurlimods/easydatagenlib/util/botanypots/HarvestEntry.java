package com.github.minecraftschurlimods.easydatagenlib.util.botanypots;

import com.github.minecraftschurlimods.easydatagenlib.util.JsonSerializable;
import com.github.minecraftschurlimods.easydatagenlib.util.PotentiallyAbsentItemStack;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;

public class HarvestEntry implements JsonSerializable {
    public final PotentiallyAbsentItemStack stack;
    public final int minRolls;
    public final int maxRolls;

    public HarvestEntry(ResourceLocation item, float chance, int minRolls, int maxRolls) {
        this.stack = new PotentiallyAbsentItemStack.WithChance(item, chance);
        this.minRolls = minRolls;
        this.maxRolls = maxRolls;
        if (minRolls < 0 || maxRolls < 0) throw new IllegalArgumentException("Rolls must not be negative!");
        if (minRolls > maxRolls) throw new IllegalArgumentException("Min rolls must not be greater than max rolls!");
    }

    @Override
    public JsonElement toJson(HolderLookup.Provider registries) {
        JsonObject json = stack.toJson(registries);
        if (minRolls != 1 && maxRolls != 1) {
            json.addProperty("minRolls", minRolls);
            json.addProperty("maxRolls", maxRolls);
        }
        return json;
    }
}
