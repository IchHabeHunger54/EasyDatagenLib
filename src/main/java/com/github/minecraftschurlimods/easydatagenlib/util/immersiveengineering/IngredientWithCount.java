package com.github.minecraftschurlimods.easydatagenlib.util.immersiveengineering;

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
        JsonObject json = new JsonObject();
        if (count == 1) {
            json = JsonUtil.toJson(ingredient, registries).getAsJsonObject();
        } else {
            json.add("base_ingredient", JsonUtil.toJson(ingredient, registries));
            json.addProperty("count", count);
        }
        return json;
    }

    public static class WithChance extends IngredientWithCount {
        public final float chance;

        public WithChance(Ingredient ingredient, int count, float chance) {
            super(ingredient, count);
            this.chance = chance;
        }

        public WithChance(Ingredient ingredient, float chance) {
            super(ingredient);
            this.chance = chance;
        }

        @Override
        public JsonElement toJson(HolderLookup.Provider registries) {
            JsonObject json = super.toJson(registries).getAsJsonObject();
            json.addProperty("chance", chance);
            return json;
        }
    }
}
