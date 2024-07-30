package com.github.minecraftschurlimods.easydatagenlib.mods;

import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeBuilder;
import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeProvider;
import com.github.minecraftschurlimods.easydatagenlib.util.JsonUtil;
import com.github.minecraftschurlimods.easydatagenlib.util.PotentiallyAbsentItemStack;
import com.github.minecraftschurlimods.easydatagenlib.util.immersiveengineering.ClocheRenderType;
import com.github.minecraftschurlimods.easydatagenlib.util.immersiveengineering.IngredientWithCount;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class ImmersiveEngineeringDataProvider<T extends AbstractRecipeBuilder<?>> extends AbstractRecipeProvider<T> {
    protected ImmersiveEngineeringDataProvider(String folder, String namespace, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(new ResourceLocation("immersiveengineering", folder), namespace, output, registries);
    }
    //TODO Alloy Furnace, Blast Furnace, Blast Furnace Fuel, Bottling Machine, Cloche Fertilizer, Coke Oven, Fermenter, Generator Fuel, Metal Press, Mineral Mix, Mixer, Refinery, Squeezer, Thermoelectric Source

    public static class ArcFurnace extends ImmersiveEngineeringDataProvider<ArcFurnace.Builder> {
        public ArcFurnace(String namespace, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super("arc_furnace", namespace, output, registries);
        }

        /**
         * @param id       The recipe id to use.
         * @param duration The duration to use.
         * @param energy   The amount of energy to use.
         * @param input    The input ingredient to use.
         * @param count    The input count to use.
         */
        public Builder builder(String id, int duration, int energy, Ingredient input, int count) {
            return new Builder(this, new ResourceLocation(namespace, id), duration, energy, input, count);
        }

        /**
         * @param id       The recipe id to use.
         * @param duration The duration to use.
         * @param energy   The amount of energy to use.
         * @param input    The input ingredient to use.
         */
        public Builder builder(String id, int duration, int energy, Ingredient input) {
            return new Builder(this, new ResourceLocation(namespace, id), duration, energy, input);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final List<IngredientWithCount> secondaryInputs = new ArrayList<>();
            private final List<IngredientWithCount> outputs = new ArrayList<>();
            private final List<IngredientWithCount> secondaryOutputs = new ArrayList<>();
            private final int duration;
            private final int energy;
            private final IngredientWithCount input;
            private IngredientWithCount slag;

            protected Builder(ArcFurnace provider, ResourceLocation id, int duration, int energy, Ingredient input, int count) {
                super(id, provider);
                this.duration = duration;
                this.energy = energy;
                this.input = new IngredientWithCount(input, count);
            }

            protected Builder(ArcFurnace provider, ResourceLocation id, int duration, int energy, Ingredient input) {
                this(provider, id, duration, energy, input, 1);
            }

            /**
             * Sets the slag output of this recipe.
             *
             * @param slag The slag output to use.
             */
            public Builder setSlag(Ingredient slag) {
                return setSlag(slag, 1);
            }

            /**
             * Sets the slag output of this recipe.
             *
             * @param slag  The slag output to use.
             * @param count The slag output count to use.
             */
            public Builder setSlag(Ingredient slag, int count) {
                this.slag = new IngredientWithCount(slag, count);
                return this;
            }

            /**
             * Adds a secondary input to this recipe.
             *
             * @param input The secondary input to add.
             * @param count The secondary input count to use.
             */
            public Builder addSecondaryInput(Ingredient input, int count) {
                secondaryInputs.add(new IngredientWithCount(input, count));
                return this;
            }

            /**
             * Adds a secondary input to this recipe.
             *
             * @param input The secondary input ingredient to add.
             */
            public Builder addSecondaryInput(Ingredient input) {
                return addSecondaryInput(input, 1);
            }

            /**
             * Adds an output to this recipe.
             *
             * @param output The output ingredient to add.
             * @param count  The output count to use.
             */
            public Builder addOutput(Ingredient output, int count) {
                outputs.add(new IngredientWithCount(output, count));
                return this;
            }

            /**
             * Adds an output to this recipe.
             *
             * @param output The output ingredient to add.
             */
            public Builder addOutput(Ingredient output) {
                return addOutput(output, 1);
            }

            /**
             * Adds a secondary output to this recipe.
             *
             * @param secondary The secondary output to add.
             * @param count     The secondary output count to use.
             * @param chance    The chance that this output will be used.
             */
            public Builder addSecondaryOutput(Ingredient secondary, int count, float chance) {
                secondaryOutputs.add(new IngredientWithCount.WithChance(secondary, count, chance));
                return this;
            }

            /**
             * Adds a secondary output to this recipe.
             *
             * @param secondary The secondary output to add.
             * @param chance    The chance that this output will be used.
             */
            public Builder addSecondaryOutput(Ingredient secondary, float chance) {
                return addSecondaryOutput(secondary, 1, chance);
            }

            /**
             * Adds a secondary output to this recipe.
             *
             * @param secondary The secondary ingredient to add.
             * @param count     The secondary output count to use.
             */
            public Builder addSecondaryOutput(Ingredient secondary, int count) {
                return addSecondaryOutput(secondary, count, 1);
            }

            /**
             * Adds a secondary output to this recipe.
             *
             * @param secondary The secondary ingredient to add.
             */
            public Builder addSecondaryOutput(Ingredient secondary) {
                return addSecondaryOutput(secondary, 1);
            }

            @Override
            protected void toJson(JsonObject json, HolderLookup.Provider registries) {
                json.addProperty("time", duration);
                json.addProperty("energy", energy);
                json.add("input", input.toJson(registries));
                if (slag != null) {
                    json.add("slag", slag.toJson(registries));
                }
                if (outputs.isEmpty()) throw new IllegalStateException("Results cannot be empty for recipe" + id + "!");
                json.add("results", JsonUtil.toList(outputs, registries));
                if (!secondaryInputs.isEmpty()) {
                    json.add("additives", JsonUtil.toList(secondaryInputs, registries));
                }
                if (!secondaryOutputs.isEmpty()) {
                    json.add("secondaries", JsonUtil.toList(secondaryOutputs, registries));
                }
            }
        }
    }

    public static class Cloche extends ImmersiveEngineeringDataProvider<Cloche.Builder> {
        public Cloche(String namespace, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super("cloche", namespace, output, registries);
        }

        /**
         * @param id         The recipe id to use.
         * @param time       The time this recipe requires.
         * @param input      The input ingredient to use.
         * @param soil       The soil ingredient to use.
         * @param renderType The render type to use in the recipe renderer.
         * @param block      The id of the block to use in the recipe renderer.
         */
        public Builder builder(String id, int time, Ingredient input, Ingredient soil, ClocheRenderType renderType, ResourceLocation block) {
            return new Builder(this, new ResourceLocation(namespace, id), time, input, soil, renderType, block);
        }

        /**
         * @param id         The recipe id to use.
         * @param time       The time this recipe requires.
         * @param input      The input ingredient to use.
         * @param soil       The soil ingredient to use.
         * @param renderType The render type to use in the recipe renderer.
         * @param block      The block to use in the recipe renderer.
         */
        public Builder builder(String id, int time, Ingredient input, Ingredient soil, ClocheRenderType renderType, Block block) {
            return new Builder(this, new ResourceLocation(namespace, id), time, input, soil, renderType, block);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final List<IngredientWithCount> outputs = new ArrayList<>();
            private final int time;
            private final Ingredient input;
            private final Ingredient soil;
            private final ClocheRenderType renderType;
            private final ResourceLocation block;

            protected Builder(Cloche provider, ResourceLocation id, int time, Ingredient input, Ingredient soil, ClocheRenderType renderType, ResourceLocation block) {
                super(id, provider);
                this.time = time;
                this.input = input;
                this.soil = soil;
                this.renderType = renderType;
                this.block = block;
            }

            protected Builder(Cloche provider, ResourceLocation id, int time, Ingredient input, Ingredient soil, ClocheRenderType renderType, Block block) {
                this(provider, id, time, input, soil, renderType, blockId(block));
            }

            /**
             * Adds an output to this recipe.
             *
             * @param output The output ingredient to add.
             * @param count  The output ingredient count to use.
             */
            public Builder addOutput(Ingredient output, int count) {
                outputs.add(new IngredientWithCount(output, count));
                return this;
            }

            /**
             * Adds an output to this recipe.
             *
             * @param output The output ingredient to add.
             */
            public Builder addOutput(Ingredient output) {
                return addOutput(output, 1);
            }

            @Override
            protected void toJson(JsonObject json, HolderLookup.Provider registries) {
                json.addProperty("time", time);
                json.add("input", JsonUtil.toJson(input, registries));
                json.add("soil", JsonUtil.toJson(soil, registries));
                json.add("results", JsonUtil.toList(outputs, registries));
                JsonObject render = new JsonObject();
                render.addProperty("type", renderType.toString());
                render.addProperty("block", block.toString());
                json.add("render", render);
            }
        }
    }

    public static class Crusher extends ImmersiveEngineeringDataProvider<Crusher.Builder> {
        public Crusher(String namespace, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super("crusher", namespace, output, registries);
        }

        /**
         * @param id     The recipe id to use.
         * @param energy The amount of energy to use.
         * @param input  The input ingredient to use.
         * @param output The output ingredient to use.
         * @param count  The output count to use.
         */
        public Builder builder(String id, int energy, Ingredient input, Ingredient output, int count) {
            return new Builder(this, new ResourceLocation(namespace, id), energy, input, output, count);
        }

        /**
         * @param id     The recipe id to use.
         * @param energy The amount of energy to use.
         * @param input  The input ingredient to use.
         * @param output The output ingredient to use.
         */
        public Builder builder(String id, int energy, Ingredient input, Ingredient output) {
            return new Builder(this, new ResourceLocation(namespace, id), energy, input, output);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final List<IngredientWithCount> secondaryOutputs = new ArrayList<>();
            private final int energy;
            private final Ingredient input;
            private final IngredientWithCount output;

            protected Builder(Crusher provider, ResourceLocation id, int energy, Ingredient input, Ingredient output, int count) {
                super(id, provider);
                this.energy = energy;
                this.input = input;
                this.output = new IngredientWithCount(output, count);
            }

            protected Builder(Crusher provider, ResourceLocation id, int energy, Ingredient input, Ingredient output) {
                this(provider, id, energy, input, output, 1);
            }

            /**
             * Adds a secondary output to this recipe.
             *
             * @param secondary The secondary output to add.
             * @param count     The secondary output count to use.
             * @param chance    The chance that this output will be used.
             */
            public Builder addSecondary(Ingredient secondary, int count, float chance) {
                secondaryOutputs.add(new IngredientWithCount.WithChance(secondary, count, chance));
                return this;
            }

            /**
             * Adds a secondary output to this recipe.
             *
             * @param secondary The secondary output to add.
             * @param chance    The chance that this output will be used.
             */
            public Builder addSecondary(Ingredient secondary, float chance) {
                return addSecondary(secondary, 1, chance);
            }

            /**
             * Adds a secondary output to this recipe.
             *
             * @param secondary The secondary output to add.
             * @param count     The secondary output count to use.
             */
            public Builder addSecondary(Ingredient secondary, int count) {
                return addSecondary(secondary, count, 1);
            }

            /**
             * Adds a secondary output to this recipe.
             *
             * @param secondary The secondary output to add.
             */
            public Builder addSecondary(Ingredient secondary) {
                return addSecondary(secondary, 1);
            }

            @Override
            protected void toJson(JsonObject json, HolderLookup.Provider registries) {
                json.addProperty("energy", energy);
                json.add("input", JsonUtil.toJson(input, registries));
                json.add("result", output.toJson(registries));
                json.add("secondaries", JsonUtil.toList(secondaryOutputs, registries));
            }
        }
    }

    public static class Sawmill extends ImmersiveEngineeringDataProvider<Sawmill.Builder> {
        public Sawmill(String namespace, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super("sawmill", namespace, output, registries);
        }

        /**
         * @param id     The recipe id to use.
         * @param energy The amount of energy to use.
         * @param input  The input ingredient to use.
         * @param output The id of the output item to use.
         * @param count  The output count to use.
         */
        public Builder builder(String id, int energy, Ingredient input, ResourceLocation output, int count) {
            return new Builder(this, new ResourceLocation(namespace, id), energy, input, output, count);
        }

        /**
         * @param id     The recipe id to use.
         * @param energy The amount of energy to use.
         * @param input  The input ingredient to use.
         * @param output The id of the output item to use.
         */
        public Builder builder(String id, int energy, Ingredient input, ResourceLocation output) {
            return new Builder(this, new ResourceLocation(namespace, id), energy, input, output);
        }

        /**
         * @param id     The recipe id to use.
         * @param energy The amount of energy to use.
         * @param input  The input ingredient to use.
         * @param output The output item to use.
         * @param count  The output count to use.
         */
        public Builder builder(String id, int energy, Ingredient input, Item output, int count) {
            return new Builder(this, new ResourceLocation(namespace, id), energy, input, output, count);
        }

        /**
         * @param id     The recipe id to use.
         * @param energy The amount of energy to use.
         * @param input  The input ingredient to use.
         * @param output The output item to use.
         */
        public Builder builder(String id, int energy, Ingredient input, Item output) {
            return new Builder(this, new ResourceLocation(namespace, id), energy, input, output);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final List<Pair<IngredientWithCount, Boolean>> secondaryOutputs = new ArrayList<>();
            private final int energy;
            private final Ingredient input;
            private final PotentiallyAbsentItemStack output;
            private IngredientWithCount stripped;

            protected Builder(Sawmill provider, ResourceLocation id, int energy, Ingredient input, ResourceLocation output, int count) {
                super(id, provider);
                this.energy = energy;
                this.input = input;
                this.output = new PotentiallyAbsentItemStack(output, count); // doesn't support NBT
            }

            protected Builder(Sawmill provider, ResourceLocation id, int energy, Ingredient input, ResourceLocation output) {
                this(provider, id, energy, input, output, 1);
            }

            protected Builder(Sawmill provider, ResourceLocation id, int energy, Ingredient input, Item output, int count) {
                this(provider, id, energy, input, itemId(output), count);
            }

            protected Builder(Sawmill provider, ResourceLocation id, int energy, Ingredient input, Item output) {
                this(provider, id, energy, input, output, 1);
            }

            /**
             * Sets the stripped output of this recipe.
             *
             * @param stripped The stripped output to use.
             */
            public Builder setStrippedOutput(Ingredient stripped) {
                return setStrippedOutput(stripped, 1);
            }

            /**
             * Sets the stripped output of this recipe.
             *
             * @param stripped The stripped output to use.
             * @param count    The stripped output count to use.
             */
            public Builder setStrippedOutput(Ingredient stripped, int count) {
                this.stripped = new IngredientWithCount(stripped, count);
                return this;
            }

            /**
             * Adds a secondary output to this recipe.
             *
             * @param secondary The secondary output to add.
             * @param count     The secondary output count to use.
             * @param stripping Whether this should apply during a stripping or during a non-stripping operation.
             */
            public Builder addSecondaryOutput(Ingredient secondary, int count, boolean stripping) {
                secondaryOutputs.add(Pair.of(new IngredientWithCount(secondary, count), stripping));
                return this;
            }

            /**
             * Adds a secondary output to this recipe.
             *
             * @param secondary The secondary output to add.
             * @param stripping Whether this should apply during a stripping or during a non-stripping operation.
             */
            public Builder addSecondaryOutput(Ingredient secondary, boolean stripping) {
                return addSecondaryOutput(secondary, 1, stripping);
            }

            @Override
            protected void toJson(JsonObject json, HolderLookup.Provider registries) {
                json.addProperty("energy", energy);
                json.add("input", JsonUtil.toJson(input, registries));
                json.add("result", output.toJson(registries));
                if (stripped != null) {
                    json.add("stripped", stripped.toJson(registries));
                }
                json.add("secondaries", JsonUtil.toList(secondaryOutputs, e -> {
                    JsonObject o = new JsonObject();
                    o.add("output", e.getFirst().toJson(registries));
                    o.addProperty("stripping", e.getSecond());
                    return o;
                }));
            }
        }
    }
}
