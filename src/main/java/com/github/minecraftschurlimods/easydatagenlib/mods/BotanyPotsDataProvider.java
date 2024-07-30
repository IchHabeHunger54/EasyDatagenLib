package com.github.minecraftschurlimods.easydatagenlib.mods;

import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeBuilder;
import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeProvider;
import com.github.minecraftschurlimods.easydatagenlib.util.JsonUtil;
import com.github.minecraftschurlimods.easydatagenlib.util.PotentiallyAbsentItemStack;
import com.github.minecraftschurlimods.easydatagenlib.util.botanypots.DisplayState;
import com.github.minecraftschurlimods.easydatagenlib.util.botanypots.HarvestEntry;
import com.google.gson.JsonObject;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class BotanyPotsDataProvider<T extends AbstractRecipeBuilder<?>> extends AbstractRecipeProvider<T> {
    protected BotanyPotsDataProvider(String folder, String namespace, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(new ResourceLocation("botanypots", folder), namespace, output, registries);
    }
    //TODO Fertilizer, Pot Interaction

    public static class Crop extends BotanyPotsDataProvider<Crop.Builder> {
        public Crop(String namespace, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super("crop", namespace, output, registries);
        }

        /**
         * @param id       The recipe id to use.
         * @param input    The input ingredient to use.
         * @param duration The duration to use.
         */
        public Builder builder(String id, Ingredient input, int duration) {
            return new Builder(this, new ResourceLocation(namespace, id), input, duration);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final List<String> categories = new ArrayList<>();
            private final List<DisplayState> display = new ArrayList<>();
            private final List<HarvestEntry> outputs = new ArrayList<>();
            private final Ingredient input;
            private final int duration;
            private int lightLevel = 0;

            protected Builder(Crop provider, ResourceLocation id, Ingredient input, int duration) {
                super(id, provider);
                this.input = input;
                this.duration = duration;
            }

            /**
             * Sets the light level of this recipe.
             *
             * @param lightLevel The light level to use.
             */
            public Builder setLightLevel(int lightLevel) {
                this.lightLevel = Mth.clamp(lightLevel, 0, 15);
                return this;
            }

            /**
             * Adds a category to this recipe.
             *
             * @param category The category to add.
             */
            public Builder addCategory(String category) {
                categories.add(category);
                return this;
            }

            /**
             * Adds a display state to this recipe.
             *
             * @param display The display state to add.
             */
            public Builder addDisplay(DisplayState display) {
                this.display.add(display);
                return this;
            }

            /**
             * Adds an output to this recipe.
             *
             * @param output   The id of the output item to add.
             * @param chance The chance that this output will be used.
             * @param minRolls The min rolls of this output.
             * @param maxRolls The max rolls of this output.
             */
            public Builder addOutput(ResourceLocation output, float chance, int minRolls, int maxRolls) {
                outputs.add(new HarvestEntry(output, chance, minRolls, maxRolls));
                return this;
            }

            /**
             * Adds an output to this recipe.
             *
             * @param output   The id of the output item to add.
             * @param minRolls The min rolls of this output.
             * @param maxRolls The max rolls of this output.
             */
            public Builder addOutput(ResourceLocation output, int minRolls, int maxRolls) {
                return addOutput(output, 1, minRolls, maxRolls);
            }

            /**
             * Adds an output to this recipe.
             *
             * @param output The id of the output item to add.
             * @param chance The chance of this output to occur.
             */
            public Builder addOutput(ResourceLocation output, float chance) {
                return addOutput(output, chance, 1, 1);
            }

            /**
             * Adds an output to this recipe.
             *
             * @param output The id of the output item to add.
             */
            public Builder addOutput(ResourceLocation output) {
                return addOutput(output, 1);
            }

            /**
             * Adds an output to this recipe.
             *
             * @param output   The output item to add.
             * @param chance The chance that this output will be used.
             * @param minRolls The min rolls of this output.
             * @param maxRolls The max rolls of this output.
             */
            public Builder addOutput(Item output, float chance, int minRolls, int maxRolls) {
                return addOutput(itemId(output), chance, minRolls, maxRolls);
            }

            /**
             * Adds an output to this recipe.
             *
             * @param output   The output item to add.
             * @param minRolls The min rolls of this output.
             * @param maxRolls The max rolls of this output.
             */
            public Builder addOutput(Item output, int minRolls, int maxRolls) {
                return addOutput(output, 1, minRolls, maxRolls);
            }

            /**
             * Adds an output to this recipe.
             *
             * @param output The output item to add.
             * @param chance The chance of this output to occur.
             */
            public Builder addOutput(Item output, float chance) {
                return addOutput(output, chance, 1, 1);
            }

            /**
             * Adds an output to this recipe.
             *
             * @param output The output item to add.
             */
            public Builder addOutput(Item output) {
                return addOutput(output, 1);
            }

            @Override
            protected void toJson(JsonObject json, HolderLookup.Provider registries) {
                json.add("seed", JsonUtil.toJson(input, registries));
                json.addProperty("growthTicks", duration);
                json.add("display", JsonUtil.singleOrArray(JsonUtil.toList(display, registries)));
                if (lightLevel != 0) {
                    json.addProperty("lightLevel", lightLevel);
                }
                json.add("categories", JsonUtil.toStringList(categories));
                json.add("drops", JsonUtil.toList(outputs, e -> {
                    JsonObject object = new JsonObject();
                    object.addProperty("chance", e.stack instanceof PotentiallyAbsentItemStack.WithChance withChance ? withChance.chance : 1f);
                    JsonObject output = new JsonObject();
                    output.addProperty("item", e.stack.item.toString());
                    object.add("output", output);
                    return object;
                }));
            }
        }
    }

    public static class Soil extends BotanyPotsDataProvider<Soil.Builder> {
        public Soil(String namespace, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super("soil", namespace, output, registries);
        }

        /**
         * @param id      The recipe id to use.
         * @param input   The input ingredient to use.
         * @param display The {@link DisplayState} to use.
         */
        public Builder builder(String id, Ingredient input, DisplayState display) {
            return new Builder(this, new ResourceLocation(namespace, id), input, display);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final List<String> categories = new ArrayList<>();
            private final Ingredient input;
            private final DisplayState display;
            private float growthModifier = 1;
            private int lightLevel = 0;

            protected Builder(Soil provider, ResourceLocation id, Ingredient input, DisplayState display) {
                super(id, provider);
                this.input = input;
                this.display = display;
            }

            /**
             * Sets the growth modifier of this recipe.
             *
             * @param growthModifier The growth modifier to use.
             */
            public Builder setGrowthModifier(float growthModifier) {
                this.growthModifier = growthModifier;
                return this;
            }

            /**
             * Sets the light level of this recipe.
             *
             * @param lightLevel The light level to use.
             */
            public Builder setLightLevel(int lightLevel) {
                this.lightLevel = Mth.clamp(lightLevel, 0, 15);
                return this;
            }

            /**
             * Adds a category to this recipe.
             *
             * @param category The category to add.
             */
            public Builder addCategory(String category) {
                categories.add(category);
                return this;
            }

            @Override
            protected void toJson(JsonObject json, HolderLookup.Provider registries) {
                json.add("input", JsonUtil.toJson(input, registries));
                json.addProperty("growthModifier", growthModifier);
                json.add("display", display.toJson(registries));
                if (lightLevel != 0) {
                    json.addProperty("lightLevel", lightLevel);
                }
                json.add("categories", JsonUtil.toStringList(categories));
            }
        }
    }
}
