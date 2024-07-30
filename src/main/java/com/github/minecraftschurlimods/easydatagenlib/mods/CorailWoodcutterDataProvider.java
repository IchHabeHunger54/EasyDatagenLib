package com.github.minecraftschurlimods.easydatagenlib.mods;

import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeBuilder;
import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeProvider;
import com.github.minecraftschurlimods.easydatagenlib.util.JsonUtil;
import com.github.minecraftschurlimods.easydatagenlib.util.PotentiallyAbsentItemStack;
import com.google.gson.JsonObject;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.concurrent.CompletableFuture;

public abstract class CorailWoodcutterDataProvider<T extends AbstractRecipeBuilder<?>> extends AbstractRecipeProvider<T> {
    protected CorailWoodcutterDataProvider(String folder, String namespace, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(new ResourceLocation("corail_woodcutter", folder), namespace, output, registries);
    }

    public static class Sawing extends CorailWoodcutterDataProvider<Sawing.Builder> {
        public Sawing(String namespace, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super("woodcutting", namespace, output, registries);
        }

        /**
         * @param id     The recipe id to use.
         * @param input  The input ingredient to use.
         * @param output The id of the output item to use.
         * @param count  The output count to use.
         */
        public Builder builder(String id, Ingredient input, ResourceLocation output, int count) {
            return new Builder(this, new ResourceLocation(namespace, id), input, output, count);
        }

        /**
         * @param id     The recipe id to use.
         * @param input  The input ingredient to use.
         * @param output The id of the output item to use.
         */
        public Builder builder(String id, Ingredient input, ResourceLocation output) {
            return new Builder(this, new ResourceLocation(namespace, id), input, output);
        }

        /**
         * @param id     The recipe id to use.
         * @param input  The input ingredient to use.
         * @param output The output item to use.
         * @param count  The output count to use.
         */
        public Builder builder(String id, Ingredient input, Item output, int count) {
            return new Builder(this, new ResourceLocation(namespace, id), input, output, count);
        }

        /**
         * @param id     The recipe id to use.
         * @param input  The input ingredient to use.
         * @param output The output item to use.
         */
        public Builder builder(String id, Ingredient input, Item output) {
            return new Builder(this, new ResourceLocation(namespace, id), input, output);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final Ingredient input;
            private final PotentiallyAbsentItemStack output;

            protected Builder(Sawing provider, ResourceLocation id, Ingredient input, ResourceLocation output, int count) {
                super(id, provider);
                this.input = input;
                this.output = new PotentiallyAbsentItemStack(output, count); // doesn't support NBT
            }

            protected Builder(Sawing provider, ResourceLocation id, Ingredient input, ResourceLocation output) {
                this(provider, id, input, output, 1);
            }

            protected Builder(Sawing provider, ResourceLocation id, Ingredient input, Item output, int count) {
                this(provider, id, input, itemId(output), count);
            }

            protected Builder(Sawing provider, ResourceLocation id, Ingredient input, Item output) {
                this(provider, id, input, output, 1);
            }

            @Override
            protected void toJson(JsonObject json, HolderLookup.Provider registries) {
                json.add("ingredient", JsonUtil.toJson(input, registries));
                json.addProperty("result", output.item.toString());
                json.addProperty("count", output.count);
            }
        }
    }
}
