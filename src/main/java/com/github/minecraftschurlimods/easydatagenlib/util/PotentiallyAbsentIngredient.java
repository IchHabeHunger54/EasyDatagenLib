package com.github.minecraftschurlimods.easydatagenlib.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.crafting.ICustomIngredient;
import net.neoforged.neoforge.common.crafting.IngredientType;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Class that allows mimicking an ingredient of an item that is not present during compile time by simply using the item's registry name.
 * This should not be used outside datagen, as there is no validation whatsoever!
 */
public class PotentiallyAbsentIngredient implements ICustomIngredient {
    private final ResourceLocation[] items;

    protected PotentiallyAbsentIngredient(ResourceLocation... items) {
        this.items = items;
    }

    public static Ingredient of(ResourceLocation... items) {
        return new Ingredient(new PotentiallyAbsentIngredient(items));
    }

    public JsonElement toJson() {
        if (items.length == 1) {
            JsonObject json = new JsonObject();
            json.addProperty("item", items[0].toString());
            return json;
        } else {
            JsonArray array = new JsonArray();
            for (ResourceLocation item : items) {
                JsonObject json = new JsonObject();
                json.addProperty("item", item.toString());
                array.add(json);
            }
            return array;
        }
    }

    @Override
    public boolean test(ItemStack stack) {
        return Arrays.stream(items).anyMatch(stack.getItemHolder()::is);
    }

    @Override
    public Stream<ItemStack> getItems() {
        return Stream.empty();
    }

    @Override
    public boolean isSimple() {
        return false;
    }

    @Override
    public IngredientType<?> getType() {
        return null;
    }
}
