package com.github.minecraftschurlimods.easydatagenlib.util.thermal;

import com.github.minecraftschurlimods.easydatagenlib.util.JsonSerializable;
import com.github.minecraftschurlimods.easydatagenlib.util.JsonUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.crafting.Ingredient;

public class IngredientWithCount implements JsonSerializable {
    public final Ingredient ingredient;
    public final int count;

    public IngredientWithCount(Ingredient ingredient, int count) {
        this.ingredient = ingredient;
        this.count = count;
    }

    public IngredientWithCount(Ingredient ingredient) {
        this(ingredient, 1);
    }

    @Override
    public JsonElement toJson(HolderLookup.Provider registries) {
        JsonObject result = JsonUtil.toJson(ingredient, registries).getAsJsonObject();
        if (count > 1) {
            result.addProperty("count", count);
        }
        return result;
    }
}
