package com.github.minecraftschurlimods.easydatagenlib.api;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

import java.util.concurrent.CompletableFuture;

/**
 * The abstract parent class for anything that generates recipes.
 *
 * @param <T> The builder class associated with this provider.
 * @see <a href="https://github.com/MinecraftschurliMods/EasyDatagenLib/wiki/Custom-Datagen-Base-Classes">Custom Datagen Base Classes documentation</a>
 */
public abstract class AbstractRecipeProvider<T extends AbstractRecipeBuilder<?>> extends AbstractDataProvider<T> {
    private final ResourceLocation recipeType;
    private final String name;

    /**
     * @param recipeType The recipe type to use. Also determines the output folder.
     * @param namespace  The namespace to use.
     * @param output     The data generator to use.
     */
    protected AbstractRecipeProvider(ResourceLocation recipeType, String namespace, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(namespace, "recipes/" + (recipeType.getNamespace().equals(namespace) ? "" : "compat/" + recipeType.getNamespace() + "/") + recipeType.getPath(), PackOutput.Target.DATA_PACK, output, registries);
        this.recipeType = recipeType;
        this.name = makeName(recipeType);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    protected JsonObject toJson(T builder, HolderLookup.Provider registries) {
        if (!recipeType.getNamespace().equals(namespace)) {
            builder.addCondition(new ModLoadedCondition(recipeType.getNamespace()));
        }
        JsonObject json = super.toJson(builder, registries);
        json.addProperty("type", recipeType.toString());
        ICondition.writeConditions(JsonOps.INSTANCE, json, builder.conditions);
        return json;
    }

    private String makeName(ResourceLocation recipeType) {
        String s = recipeType.toString().replace(':', ' ').replace('_', ' ').strip();
        StringBuilder builder = new StringBuilder(s.substring(0, 1).toUpperCase());
        for (int i = 1; i < s.length(); i++) {
            char c = s.charAt(i);
            builder.append(c);
            if (c == ' ') {
                builder.append(String.valueOf(s.charAt(i + 1)).toUpperCase());
                i++;
            }
        }
        return builder.append('[').append(namespace).append(']').toString();
    }
}
