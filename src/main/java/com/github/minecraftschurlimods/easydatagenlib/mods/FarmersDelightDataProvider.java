package com.github.minecraftschurlimods.easydatagenlib.mods;

import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeBuilder;
import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeProvider;
import com.github.minecraftschurlimods.easydatagenlib.util.JsonUtil;
import com.github.minecraftschurlimods.easydatagenlib.util.PotentiallyAbsentItemStack;
import com.google.gson.JsonObject;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class FarmersDelightDataProvider<T extends AbstractRecipeBuilder<?>> extends AbstractRecipeProvider<T> {
    protected FarmersDelightDataProvider(String folder, String namespace, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(new ResourceLocation("farmersdelight", folder), namespace, output, registries);
    }

    public static class Cooking extends FarmersDelightDataProvider<Cooking.Builder> {
        public Cooking(String namespace, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super("cooking", namespace, output, registries);
        }

        /**
         * @param id         The recipe id to use.
         * @param duration   The duration to use.
         * @param experience The amount of experience this recipe awards.
         * @param output     The id of the output item to use.
         * @param count      The output count to use.
         */
        public Builder builder(String id, int duration, float experience, ResourceLocation output, int count) {
            return new Builder(this, new ResourceLocation(namespace, id), duration, experience, output, count);
        }

        /**
         * @param id         The recipe id to use.
         * @param duration   The duration to use.
         * @param experience The amount of experience this recipe awards.
         * @param output     The id of the output item to use.
         */
        public Builder builder(String id, int duration, float experience, ResourceLocation output) {
            return new Builder(this, new ResourceLocation(namespace, id), duration, experience, output);
        }

        /**
         * @param id         The recipe id to use.
         * @param duration   The duration to use.
         * @param experience The amount of experience this recipe awards.
         * @param output     The output item to use.
         * @param count      The output count to use.
         */
        public Builder builder(String id, int duration, float experience, Item output, int count) {
            return new Builder(this, new ResourceLocation(namespace, id), duration, experience, output, count);
        }

        /**
         * @param id         The recipe id to use.
         * @param duration   The duration to use.
         * @param experience The amount of experience this recipe awards.
         * @param output     The output item to use.
         */
        public Builder builder(String id, int duration, float experience, Item output) {
            return new Builder(this, new ResourceLocation(namespace, id), duration, experience, output);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final List<Ingredient> ingredients = new ArrayList<>();
            private final int duration;
            private final float experience;
            private final PotentiallyAbsentItemStack output;
            private PotentiallyAbsentItemStack container = null;
            private String recipeBookTab = null;

            protected Builder(Cooking provider, ResourceLocation id, int duration, float experience, ResourceLocation output, int count) {
                super(id, provider);
                this.duration = duration;
                this.experience = experience;
                this.output = new PotentiallyAbsentItemStack(output, count); // doesn't support NBT
            }

            protected Builder(Cooking provider, ResourceLocation id, int duration, float experience, ResourceLocation output) {
                this(provider, id, duration, experience, output, 1);
            }

            protected Builder(Cooking provider, ResourceLocation id, int duration, float experience, Item output, int count) {
                this(provider, id, duration, experience, itemId(output), count);
            }

            protected Builder(Cooking provider, ResourceLocation id, int duration, float experience, Item output) {
                this(provider, id, duration, experience, output, 1);
            }

            /**
             * Sets the container item of this recipe.
             *
             * @param container The id of the container item to use.
             */
            public Builder setContainer(ResourceLocation container) {
                this.container = new PotentiallyAbsentItemStack(container);
                return this;
            }

            /**
             * Sets the container item of this recipe.
             *
             * @param container The container item to use.
             */
            public Builder setContainer(Item container) {
                return setContainer(itemId(container));
            }

            /**
             * Sets the recipe book tab of this recipe.
             *
             * @param recipeBookTab The recipe book tab to use.
             */
            public Builder setRecipeBookTab(String recipeBookTab) {
                this.recipeBookTab = recipeBookTab;
                return this;
            }

            /**
             * Adds an input ingredient to this recipe.
             *
             * @param input The input ingredient to add.
             */
            public Builder addInput(Ingredient input) {
                ingredients.add(input);
                return this;
            }

            @Override
            protected void toJson(JsonObject json, HolderLookup.Provider registries) {
                json.addProperty("cookingtime", duration);
                json.addProperty("experience", experience);
                json.add("result", output.toJson(registries));
                if (recipeBookTab != null) {
                    json.addProperty("recipe_book_tab", recipeBookTab);
                }
                if (container != null) {
                    json.add("container", container.toJson(registries));
                }
                json.add("ingredients", JsonUtil.toIngredientList(ingredients, registries));
            }
        }
    }

    public static class Cutting extends FarmersDelightDataProvider<Cutting.Builder> {
        public Cutting(String namespace, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super("cutting", namespace, output, registries);
        }

        /**
         * @param id    The recipe id to use.
         * @param input The input ingredient to use.
         * @param tool  The tool to use.
         */
        public Builder builder(String id, Ingredient input, Ingredient tool) {
            return new Builder(this, new ResourceLocation(namespace, id), input, tool);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final List<PotentiallyAbsentItemStack> outputs = new ArrayList<>();
            private final Ingredient input;
            private final Ingredient tool;
            private String sound;

            protected Builder(Cutting provider, ResourceLocation id, Ingredient input, Ingredient tool) {
                super(id, provider);
                this.input = input;
                this.tool = tool;
            }

            /**
             * Sets the sound of this recipe.
             *
             * @param sound The sound to use.
             */
            public Builder setSound(String sound) {
                this.sound = sound;
                return this;
            }

            /**
             * Adds an output to this recipe.
             *
             * @param item   The id of the output item to use.
             * @param count  The output count to use.
             * @param tag    The output NBT tag to use.
             * @param chance The chance that this output will be used.
             */
            public Builder addOutput(ResourceLocation item, int count, CompoundTag tag, float chance) {
                outputs.add(new PotentiallyAbsentItemStack.WithChance(item, count, tag, chance));
                return this;
            }

            /**
             * Adds an output to this recipe.
             *
             * @param item  The id of the output item to use.
             * @param count The output count to use.
             * @param tag   The output NBT tag to use.
             */
            public Builder addOutput(ResourceLocation item, int count, CompoundTag tag) {
                return addOutput(item, count, tag, 1);
            }

            /**
             * Adds an output to this recipe.
             *
             * @param item  The id of the output item to use.
             * @param count The output count to use.
             */
            public Builder addOutput(ResourceLocation item, int count) {
                return addOutput(item, count, new CompoundTag());
            }

            /**
             * Adds an output to this recipe.
             *
             * @param item The id of the output item to use.
             */
            public Builder addOutput(ResourceLocation item) {
                return addOutput(item, 1);
            }

            /**
             * Adds an output to this recipe.
             *
             * @param item   The item to use.
             * @param count  The output count to use.
             * @param tag    The output NBT tag to use.
             * @param chance The chance that this output will be used.
             */
            public Builder addOutput(Item item, int count, CompoundTag tag, float chance) {
                return addOutput(itemId(item), count, tag, chance);
            }

            /**
             * Adds an output to this recipe.
             *
             * @param item  The item to use.
             * @param count The output count to use.
             * @param tag   The output NBT tag to use.
             */
            public Builder addOutput(Item item, int count, CompoundTag tag) {
                return addOutput(item, count, tag, 1);
            }

            /**
             * Adds an output to this recipe.
             *
             * @param item  The item to use.
             * @param count The output count to use.
             */
            public Builder addOutput(Item item, int count) {
                return addOutput(item, count, new CompoundTag());
            }

            /**
             * Adds an output to this recipe.
             *
             * @param item The item to use.
             */
            public Builder addOutput(Item item) {
                return addOutput(item, 1);
            }

            @Override
            protected void toJson(JsonObject json, HolderLookup.Provider registries) {
                json.add("ingredients", JsonUtil.toIngredientList(List.of(input), registries));
                json.add("tool", JsonUtil.toJson(tool, registries));
                if (sound != null) {
                    json.addProperty("sound", sound);
                }
                json.add("result", JsonUtil.toList(outputs, registries));
            }
        }
    }
}
