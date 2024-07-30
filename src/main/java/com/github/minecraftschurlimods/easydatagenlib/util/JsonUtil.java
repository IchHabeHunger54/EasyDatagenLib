package com.github.minecraftschurlimods.easydatagenlib.util;

import com.github.minecraftschurlimods.easydatagenlib.util.farmersdelight.ToolActionIngredient;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.function.Function;

public class JsonUtil {
    /**
     * @param arrays The {@link JsonArray}s to merge.
     * @return The merged {@link JsonArray}.
     */
    public static JsonArray mergeArrays(JsonArray... arrays) {
        JsonArray result = new JsonArray();
        for (JsonArray array : arrays) {
            result.addAll(array);
        }
        return result;
    }

    /**
     * @param array The {@link JsonArray} to handle.
     * @return If the {@link JsonArray} contains one element, the element is returned. Otherwise, the {@link JsonArray} is returned.
     */
    public static JsonElement singleOrArray(JsonArray array) {
        return array.size() == 1 ? array.get(0) : array;
    }

    /**
     * @param list     A list.
     * @param function A function that determines how the list elements will be converted into JSON.
     * @param <T>      The list element type.
     * @return A {@link JsonArray}, constructed from the given list.
     */
    public static <T> JsonArray toList(List<T> list, Function<? super T, JsonElement> function) {
        JsonArray array = new JsonArray();
        list.forEach(e -> array.add(function.apply(e)));
        return array;
    }

    /**
     * @param list       A list of {@link JsonSerializable}s.
     * @param registries
     * @return A {@link JsonArray}, constructed from the given list.
     */
    public static JsonArray toList(List<? extends JsonSerializable> list, HolderLookup.Provider registries) {
        return toList(list, jsonSerializable -> jsonSerializable.toJson(registries));
    }

    /**
     * @param list A list of {@link Boolean}s.
     * @return A {@link JsonArray}, constructed from the given list.
     */
    public static JsonArray toBooleanList(List<Boolean> list) {
        return toList(list, JsonPrimitive::new);
    }

    /**
     * @param list A list of {@link Number}s.
     * @return A {@link JsonArray}, constructed from the given list.
     */
    public static JsonArray toNumberList(List<Number> list) {
        return toList(list, JsonPrimitive::new);
    }

    /**
     * @param list A list of {@link String}s.
     * @return A {@link JsonArray}, constructed from the given list.
     */
    public static JsonArray toStringList(List<String> list) {
        return toList(list, JsonPrimitive::new);
    }

    /**
     * @param list A list of {@link Character}s.
     * @return A {@link JsonArray}, constructed from the given list.
     */
    public static JsonArray toCharList(List<Character> list) {
        return toStringList(list.stream().map(Object::toString).toList());
    }

    /**
     * @param list A list of {@link Enum} values.
     * @return A {@link JsonArray}, constructed from the given list.
     */
    public static JsonArray toEnumList(List<? extends Enum<?>> list) {
        return toStringList(list.stream().map(Enum::name).toList());
    }

    /**
     * @param list A list of {@link Ingredient}s.
     * @return A {@link JsonArray}, constructed from the given list.
     */
    public static JsonArray toIngredientList(List<? extends Ingredient> list, HolderLookup.Provider registries) {
        return toList(list, ingredient -> toJson(ingredient, registries));
    }

    /**
     * Converts a {@link Vec3} to a {@link JsonObject}.
     *
     * @param vec3 The {@link Vec3} to convert.
     * @return A {@link JsonObject}, constructed from the given parameters.
     */
    public static JsonObject toJson(Vec3 vec3) {
        JsonObject json = new JsonObject();
        json.addProperty("x", vec3.x);
        json.addProperty("y", vec3.y);
        json.addProperty("z", vec3.z);
        return json;
    }

    /**
     * Converts a {@link BlockState} to a {@link JsonObject}.
     *
     * @param state The {@link BlockState} to convert.
     * @return A {@link JsonObject}, constructed from the given parameters.
     */
    public static JsonObject toJson(BlockState state) {
        JsonObject json = new JsonObject();
        json.addProperty("block", BuiltInRegistries.BLOCK.getKey(state.getBlock()).toString());
        JsonObject properties = propertiesToJson(state);
        if (!properties.isEmpty()) {
            json.add("properties", propertiesToJson(state));
        }
        return json;
    }
    
    public static JsonElement toJson(Ingredient ingredient, HolderLookup.Provider registries) {
        if (ingredient.getCustomIngredient() instanceof PotentiallyAbsentIngredient potentiallyAbsentIngredient) {
            return potentiallyAbsentIngredient.toJson();
        }
        if (ingredient.getCustomIngredient() instanceof ToolActionIngredient toolActionIngredient) {
            return toolActionIngredient.toJson();
        }
        return Ingredient.CODEC.encodeStart(registries.createSerializationContext(JsonOps.INSTANCE), ingredient).getOrThrow();
    }

    /**
     * Converts a {@link BlockState}'s properties to a {@link JsonElement}.
     *
     * @param state The {@link BlockState} of which to convert the properties.
     * @return A {@link JsonElement}, constructed from the given parameters.
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static JsonObject propertiesToJson(BlockState state) {
        JsonObject json = new JsonObject();
        for (Property property : state.getProperties()) {
            if (property instanceof IntegerProperty ip) {
                json.addProperty(property.getName(), state.getValue(ip));
            } else if (property instanceof BooleanProperty bp) {
                json.addProperty(property.getName(), state.getValue(bp));
            } else {
                json.addProperty(property.getName(), property.getName(state.getValue(property)));
            }
        }
        return json;
    }
}
