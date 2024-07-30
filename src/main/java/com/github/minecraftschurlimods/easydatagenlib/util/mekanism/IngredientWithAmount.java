package com.github.minecraftschurlimods.easydatagenlib.util.mekanism;

import com.github.minecraftschurlimods.easydatagenlib.util.JsonSerializable;
import com.github.minecraftschurlimods.easydatagenlib.util.JsonUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.crafting.Ingredient;

public class IngredientWithAmount implements JsonSerializable {
    public final Ingredient ingredient;
    public final int amount;

    public IngredientWithAmount(Ingredient ingredient, int amount) {
        this.ingredient = ingredient;
        this.amount = amount;
    }

    public IngredientWithAmount(Ingredient ingredient) {
        this(ingredient, 1);
    }

    @Override
    public JsonElement toJson(HolderLookup.Provider registries) {
        JsonObject result = new JsonObject();
        result.add("ingredient", JsonUtil.toJson(ingredient, registries));
        if (amount > 1) {
            result.addProperty("amount", amount);
        }
        return result;
    }
}
