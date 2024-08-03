package com.github.minecraftschurlimods.easydatagenlib.mods;

import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeBuilder;
import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeProvider;
import com.github.minecraftschurlimods.easydatagenlib.util.FluidIngredient;
import com.github.minecraftschurlimods.easydatagenlib.util.JsonUtil;
import com.github.minecraftschurlimods.easydatagenlib.util.PotentiallyAbsentFluidStack;
import com.github.minecraftschurlimods.easydatagenlib.util.PotentiallyAbsentItemStack;
import com.github.minecraftschurlimods.easydatagenlib.util.create.HeatRequirement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluid;
import org.apache.commons.lang3.SerializationException;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public abstract class CreateDataProvider<T extends AbstractRecipeBuilder<?>> extends AbstractRecipeProvider<T> {
    protected CreateDataProvider(String folder, String namespace, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(ResourceLocation.fromNamespaceAndPath("create", folder), namespace, output, registries);
    }

    public static class Compacting extends Processing {
        public Compacting(String namespace, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super("compacting", namespace, output, registries);
        }
    }

    public static class Crushing extends Processing {
        public Crushing(String namespace, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super("crushing", namespace, output, registries);
        }
    }

    public static class Cutting extends Processing {
        public Cutting(String namespace, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super("cutting", namespace, output, registries);
        }
    }

    public static class Deploying extends Processing {
        public Deploying(String namespace, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super("deploying", namespace, output, registries);
        }
    }

    public static class Emptying extends Processing {
        public Emptying(String namespace, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super("emptying", namespace, output, registries);
        }
    }

    public static class Filling extends Processing {
        public Filling(String namespace, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super("filling", namespace, output, registries);
        }
    }

    public static class Haunting extends Processing {
        public Haunting(String namespace, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super("haunting", namespace, output, registries);
        }
    }

    public static class ItemApplication extends Processing {
        public ItemApplication(String namespace, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super("item_application", namespace, output, registries);
        }
    }

    public static class MechanicalCrafting extends CreateDataProvider<MechanicalCrafting.Builder> {
        public MechanicalCrafting(String namespace, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super("mechanical_crafting", namespace, output, registries);
        }

        /**
         * @param id    The recipe id to use.
         * @param item  The id of the output item to use.
         * @param count The output count to use.
         * @param patch The output components to use.
         */
        public Builder builder(String id, ResourceLocation item, int count, DataComponentPatch patch) {
            return new Builder(this, ResourceLocation.fromNamespaceAndPath(namespace, id), item, count, patch);
        }

        /**
         * @param id    The recipe id to use.
         * @param item  The id of the output item to use.
         * @param count The output count to use.
         */
        public Builder builder(String id, ResourceLocation item, int count) {
            return new Builder(this, ResourceLocation.fromNamespaceAndPath(namespace, id), item, count);
        }

        /**
         * @param id   The recipe id to use.
         * @param item The id of the output item to use.
         */
        public Builder builder(String id, ResourceLocation item) {
            return new Builder(this, ResourceLocation.fromNamespaceAndPath(namespace, id), item);
        }

        /**
         * @param id    The recipe id to use.
         * @param item  The output item to use.
         * @param count The output count to use.
         * @param patch The output components to use.
         */
        public Builder builder(String id, Item item, int count, DataComponentPatch patch) {
            return new Builder(this, ResourceLocation.fromNamespaceAndPath(namespace, id), item, count, patch);
        }

        /**
         * @param id    The recipe id to use.
         * @param item  The output item to use.
         * @param count The output count to use.
         */
        public Builder builder(String id, Item item, int count) {
            return new Builder(this, ResourceLocation.fromNamespaceAndPath(namespace, id), item, count);
        }

        /**
         * @param id   The recipe id to use.
         * @param item The output item to use.
         */
        public Builder builder(String id, Item item) {
            return new Builder(this, ResourceLocation.fromNamespaceAndPath(namespace, id), item);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final List<String> pattern = new ArrayList<>();
            private final Map<Character, Ingredient> key = new HashMap<>();
            private final PotentiallyAbsentItemStack output;
            private boolean acceptMirrored = true;

            protected Builder(MechanicalCrafting provider, ResourceLocation id, ResourceLocation item, int count, DataComponentPatch patch) {
                super(id, provider);
                output = new PotentiallyAbsentItemStack(item, count, patch);
            }

            protected Builder(MechanicalCrafting provider, ResourceLocation id, ResourceLocation item, int count) {
                this(provider, id, item, count, DataComponentPatch.EMPTY);
            }

            protected Builder(MechanicalCrafting provider, ResourceLocation id, ResourceLocation item) {
                this(provider, id, item, 1, DataComponentPatch.EMPTY);
            }

            protected Builder(MechanicalCrafting provider, ResourceLocation id, Item item, int count, DataComponentPatch patch) {
                this(provider, id, itemId(item), count, patch);
            }

            protected Builder(MechanicalCrafting provider, ResourceLocation id, Item item, int count) {
                this(provider, id, item, count, DataComponentPatch.EMPTY);
            }

            protected Builder(MechanicalCrafting provider, ResourceLocation id, Item item) {
                this(provider, id, item, 1, DataComponentPatch.EMPTY);
            }

            /**
             * Sets this recipe's acceptMirrored property to false.
             */
            public Builder dontAcceptMirrored() {
                acceptMirrored = false;
                return this;
            }

            /**
             * Adds a pattern line to this recipe.
             *
             * @param pattern The pattern line to add.
             */
            public Builder pattern(String pattern) {
                this.pattern.add(pattern);
                return this;
            }

            /**
             * Adds a key to this recipe.
             *
             * @param key   The key to add.
             * @param value The value to associate with the key.
             */
            public Builder key(char key, Ingredient value) {
                this.key.put(key, value);
                return this;
            }

            @Override
            protected void toJson(JsonObject json, HolderLookup.Provider registries) {
                validate();
                json.add("pattern", JsonUtil.toStringList(pattern));
                JsonObject key = new JsonObject();
                for (Map.Entry<Character, Ingredient> s : this.key.entrySet()) {
                    json.add(String.valueOf(s.getKey()), JsonUtil.toJson(s.getValue(), registries));
                }
                json.add("key", key);
                json.add("result", output.toJson(registries));
                json.addProperty("acceptMirrored", acceptMirrored);
            }

            private void validate() {
                if (pattern.isEmpty()) throw new IllegalStateException("No pattern defined for recipe " + id);
                Set<Character> set = new HashSet<>(key.keySet());
                set.remove(' ');
                for (String s : pattern) {
                    for (int i = 0; i < s.length(); i++) {
                        char c = s.charAt(i);
                        if (!key.containsKey(c) && c != ' ')
                            throw new IllegalStateException("Pattern in recipe " + id + " uses undefined symbol '" + c + "'");
                        set.remove(c);
                    }
                }
                if (!set.isEmpty())
                    throw new IllegalStateException("Ingredients are defined but not used in pattern for recipe " + id);
            }
        }
    }

    public static class Milling extends Processing {
        public Milling(String namespace, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super("milling", namespace, output, registries);
        }
    }

    public static class Mixing extends Processing {
        public Mixing(String namespace, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super("mixing", namespace, output, registries);
        }
    }

    public static class Pressing extends Processing {
        public Pressing(String namespace, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super("pressing", namespace, output, registries);
        }
    }

    public static class SandpaperPolishing extends Processing {
        public SandpaperPolishing(String namespace, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super("sandpaper_polishing", namespace, output, registries);
        }
    }

    public static class SequencedAssembly extends CreateDataProvider<SequencedAssembly.Builder> {
        public SequencedAssembly(String namespace, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super("sequenced_assembly", namespace, output, registries);
        }

        /**
         * @param id               The recipe id to use.
         * @param input            The input ingredient to use.
         * @param transitionalItem The id of the transitional item to use.
         */
        public Builder builder(String id, Ingredient input, ResourceLocation transitionalItem) {
            return new Builder(this, ResourceLocation.fromNamespaceAndPath(namespace, id), input, transitionalItem);
        }

        /**
         * @param id               The recipe id to use.
         * @param input            The input ingredient to use.
         * @param transitionalItem The transitional item to use.
         */
        public Builder builder(String id, Ingredient input, Item transitionalItem) {
            return new Builder(this, ResourceLocation.fromNamespaceAndPath(namespace, id), input, transitionalItem);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final List<Processing.Builder> sequence = new ArrayList<>();
            private final List<Pair<PotentiallyAbsentItemStack, Float>> outputs = new ArrayList<>();
            private final Ingredient input;
            private final PotentiallyAbsentItemStack transitionalItem;
            private int loops = 1;

            protected Builder(SequencedAssembly provider, ResourceLocation id, Ingredient input, ResourceLocation transitionalItem) {
                super(id, provider);
                this.input = input;
                this.transitionalItem = new PotentiallyAbsentItemStack(transitionalItem);
            }

            protected Builder(SequencedAssembly provider, ResourceLocation id, Ingredient input, Item transitionalItem) {
                this(provider, id, input, itemId(transitionalItem));
            }

            /**
             * Sets the amount of loops of this recipe.
             *
             * @param loops The amount of loops to use.
             */
            public Builder setLoops(int loops) {
                if (loops < 1)
                    throw new IllegalArgumentException("Recipe " + id + "has an illegal loop count of " + loops);
                this.loops = loops;
                return this;
            }

            /**
             * Adds a processing step to this recipe.
             *
             * @param processing The processing step to add.
             */
            public Builder addProcessing(Processing.Builder processing) {
                sequence.add(processing);
                return this;
            }

            /**
             * Adds an output to this recipe.
             *
             * @param output The output item to use.
             * @param weight The weight of this output.
             */
            public Builder addOutput(Item output, float weight) {
                return addOutput(itemId(output), weight);
            }

            /**
             * Adds an output to this recipe.
             *
             * @param output The id of the output item to use.
             * @param weight The weight of this output.
             */
            public Builder addOutput(ResourceLocation output, float weight) {
                outputs.add(new Pair<>(new PotentiallyAbsentItemStack(output), weight));
                return this;
            }

            @Override
            protected void toJson(JsonObject json, HolderLookup.Provider registries) {
                json.add("ingredient", JsonUtil.toJson(input, registries));
                json.add("transitionalItem", transitionalItem.toJson(registries));
                json.addProperty("loops", loops);
                json.add("sequence", JsonUtil.toList(sequence, e -> {
                    JsonObject o = new JsonObject();
                    e.toJson(o, registries);
                    return o;
                }));
                json.add("results", JsonUtil.toList(outputs, e -> {
                    JsonObject o = new JsonObject();
                    o.addProperty("item", e.getFirst().item.toString());
                    o.addProperty("chance", e.getSecond());
                    return o;
                }));
            }
        }
    }

    public static class Splashing extends Processing {
        public Splashing(String namespace, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super("splashing", namespace, output, registries);
        }
    }

    /**
     * Note: Not all recipes can actually handle all information. However, Create's recipe specification allows everything to be specified for all recipes.
     * {@see https://github.com/Creators-of-Create/Create/blob/mc1.18/dev/src/main/java/com/simibubi/create/content/contraptions/processing/ProcessingRecipeBuilder.java}
     */
    public static abstract class Processing extends CreateDataProvider<Processing.Builder> {
        protected Processing(String folder, String namespace, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super(folder, namespace, output, registries);
        }

        /**
         * @param id       The id of this recipe.
         * @param duration The duration of this recipe.
         */
        public Builder builder(String id, int duration) {
            return new Builder(this, ResourceLocation.fromNamespaceAndPath(namespace, id), duration);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final List<Ingredient> inputs = new ArrayList<>();
            private final List<FluidIngredient> fluidInputs = new ArrayList<>();
            private final List<PotentiallyAbsentItemStack> outputs = new ArrayList<>();
            private final List<PotentiallyAbsentFluidStack> fluidOutputs = new ArrayList<>();
            private final int duration;
            private boolean keepHeldItem = false;
            private HeatRequirement heatRequirement = HeatRequirement.NONE;

            protected Builder(Processing provider, ResourceLocation id, int duration) {
                super(id, provider);
                this.duration = duration;
            }

            /**
             * Sets this recipe's keepHeldItem property to true.
             */
            public void keepHeldItem() {
                keepHeldItem = true;
            }

            /**
             * Sets the heat requirement of this recipe.
             *
             * @param heatRequirement The heat requirement to use.
             */
            public void setHeatRequirement(HeatRequirement heatRequirement) {
                this.heatRequirement = heatRequirement;
            }

            /**
             * Adds an input ingredient to this recipe.
             *
             * @param input The input ingredient to add.
             */
            public Builder addInput(Ingredient input) {
                inputs.add(input);
                return this;
            }

            /**
             * Adds an input fluid ingredient to this recipe.
             *
             * @param input The input fluid ingredient to add.
             */
            public Builder addInput(FluidIngredient input) {
                fluidInputs.add(input);
                return this;
            }

            /**
             * Adds an output to this recipe.
             *
             * @param item   The output item to use.
             * @param count  The output count to use.
             * @param chance The chance that this output will be used.
             */
            public Builder addOutput(ResourceLocation item, int count, float chance) {
                outputs.add(new PotentiallyAbsentItemStack.WithChance(item, count, chance));
                return this;
            }

            /**
             * Adds an output to this recipe.
             *
             * @param item  The output item to use.
             * @param count The output count to use.
             */
            public Builder addOutput(ResourceLocation item, int count) {
                return addOutput(item, count, 1);
            }

            /**
             * Adds an output to this recipe.
             *
             * @param item   The output item to use.
             * @param chance The chance that this output will be used.
             */
            public Builder addOutput(ResourceLocation item, float chance) {
                return addOutput(item, 1, chance);
            }

            /**
             * Adds an output to this recipe.
             *
             * @param item The output item to use.
             */
            public Builder addOutput(ResourceLocation item) {
                return addOutput(item, 1, 1);
            }

            /**
             * Adds an output to this recipe.
             *
             * @param item   The output item to use.
             * @param count  The output count to use.
             * @param chance The chance that this output will be used.
             */
            public Builder addOutput(Item item, int count, float chance) {
                return addOutput(itemId(item), count, chance);
            }

            /**
             * Adds an output to this recipe.
             *
             * @param item  The output item to use.
             * @param count The output count to use.
             */
            public Builder addOutput(Item item, int count) {
                return addOutput(item, count, 1);
            }

            /**
             * Adds an output to this recipe.
             *
             * @param item   The output item to use.
             * @param chance The chance that this output will be used.
             */
            public Builder addOutput(Item item, float chance) {
                return addOutput(item, 1, chance);
            }

            /**
             * Adds an output to this recipe.
             *
             * @param item The output item to use.
             */
            public Builder addOutput(Item item) {
                return addOutput(item, 1, 1);
            }

            /**
             * Adds an output to this recipe.
             *
             * @param fluid  The output fluid.
             * @param count  The output count.
             * @param chance The chance that this output will be used.
             */
            public Builder addOutputFluid(ResourceLocation fluid, int count, float chance) {
                fluidOutputs.add(new PotentiallyAbsentFluidStack.WithChance(fluid, count, chance));
                return this;
            }

            /**
             * Adds an output to this recipe.
             *
             * @param fluid The output fluid.
             * @param count The output count.
             */
            public Builder addOutputFluid(ResourceLocation fluid, int count) {
                return addOutputFluid(fluid, count, 1);
            }

            /**
             * Adds an output to this recipe.
             *
             * @param fluid  The output fluid.
             * @param chance The chance that this output will be used.
             */
            public Builder addOutputFluid(ResourceLocation fluid, float chance) {
                return addOutputFluid(fluid, 1, chance);
            }

            /**
             * Adds an output to this recipe.
             *
             * @param fluid The output fluid.
             */
            public Builder addOutputFluid(ResourceLocation fluid) {
                return addOutputFluid(fluid, 1, 1);
            }

            /**
             * Adds an output to this recipe.
             *
             * @param fluid  The output fluid.
             * @param count  The output count.
             * @param chance The chance that this output will be used.
             */
            public Builder addOutputFluid(Fluid fluid, int count, float chance) {
                return addOutputFluid(fluidId(fluid), count, chance);
            }

            /**
             * Adds an output to this recipe.
             *
             * @param fluid The output fluid.
             * @param count The output count.
             */
            public Builder addOutputFluid(Fluid fluid, int count) {
                return addOutputFluid(fluid, count, 1);
            }

            /**
             * Adds an output to this recipe.
             *
             * @param fluid  The output fluid.
             * @param chance The chance that this output will be used.
             */
            public Builder addOutputFluid(Fluid fluid, float chance) {
                return addOutputFluid(fluid, 1, chance);
            }

            /**
             * Adds an output to this recipe.
             *
             * @param fluid The output fluid.
             */
            public Builder addOutputFluid(Fluid fluid) {
                return addOutputFluid(fluid, 1, 1);
            }

            @Override
            protected void toJson(JsonObject json, HolderLookup.Provider registries) {
                if (duration <= 0)
                    throw new SerializationException("This recipe needs a duration of at least 1!");
                json.addProperty("processingTime", duration);
                if (keepHeldItem) {
                    json.addProperty("keepHeldItem", true);
                }
                if (heatRequirement != HeatRequirement.NONE) {
                    json.addProperty("heatRequirement", heatRequirement.toString());
                }
                json.add("ingredients", JsonUtil.mergeArrays(JsonUtil.toIngredientList(inputs, registries), JsonUtil.toList(fluidInputs, registries)));
                json.add("results", JsonUtil.mergeArrays(JsonUtil.toList(outputs, registries), JsonUtil.toList(fluidOutputs, registries)));
            }
        }
    }
}
