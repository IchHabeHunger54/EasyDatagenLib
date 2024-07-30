package com.github.minecraftschurlimods.easydatagenlib.mods;

import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeBuilder;
import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeProvider;
import com.github.minecraftschurlimods.easydatagenlib.util.JsonUtil;
import com.github.minecraftschurlimods.easydatagenlib.util.PotentiallyAbsentFluidStack;
import com.github.minecraftschurlimods.easydatagenlib.util.PotentiallyAbsentItemStack;
import com.google.gson.JsonObject;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluid;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class IntegratedDynamicsDataProvider<T extends AbstractRecipeBuilder<?>> extends AbstractRecipeProvider<T> {
    protected IntegratedDynamicsDataProvider(String folder, String namespace, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(new ResourceLocation("integrateddynamics", folder), namespace, output, registries);
    }
    //TODO Drying Basin, Mechanical Drying Basin

    public static class MechanicalSqueezing extends IntegratedDynamicsDataProvider<MechanicalSqueezing.Builder> {
        public MechanicalSqueezing(String namespace, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super("mechanical_squeezer", namespace, output, registries);
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
            private final List<PotentiallyAbsentItemStack> outputs = new ArrayList<>();
            private final Ingredient input;
            private final int duration;
            private PotentiallyAbsentFluidStack outputFluid = null;

            protected Builder(MechanicalSqueezing provider, ResourceLocation id, Ingredient input, int duration) {
                super(id, provider);
                this.input = input;
                this.duration = duration;
            }

            /**
             * Sets the output fluid of this recipe.
             *
             * @param fluid  The id of the output fluid to use.
             * @param amount The output amount to use.
             * @param tag    The output NBT tag to use.
             */
            public Builder setOutputFluid(ResourceLocation fluid, int amount, CompoundTag tag) {
                this.outputFluid = new PotentiallyAbsentFluidStack(fluid, amount, tag);
                return this;
            }

            /**
             * Sets the output fluid of this recipe.
             *
             * @param fluid  The id of the output fluid to use.
             * @param amount The output amount to use.
             */
            public Builder setOutputFluid(ResourceLocation fluid, int amount) {
                return setOutputFluid(fluid, amount, new CompoundTag());
            }

            /**
             * Sets the output fluid of this recipe.
             *
             * @param fluid The id of the output fluid to use.
             */
            public Builder setOutputFluid(ResourceLocation fluid) {
                return setOutputFluid(fluid, 1);
            }

            /**
             * Sets the output fluid of this recipe.
             *
             * @param fluid  The output fluid to use.
             * @param amount The output amount to use.
             * @param tag    The output NBT tag to use.
             */
            public Builder setOutputFluid(Fluid fluid, int amount, CompoundTag tag) {
                return setOutputFluid(fluidId(fluid), amount, tag);
            }

            /**
             * Sets the output fluid of this recipe.
             *
             * @param fluid  The output fluid to use.
             * @param amount The output amount to use.
             */
            public Builder setOutputFluid(Fluid fluid, int amount) {
                return setOutputFluid(fluid, amount, new CompoundTag());
            }

            /**
             * Sets the output fluid of this recipe.
             *
             * @param fluid The output fluid to use.
             */
            public Builder setOutputFluid(Fluid fluid) {
                return setOutputFluid(fluid, 1);
            }

            /**
             * Adds an output item to this recipe.
             *
             * @param item   The id of the output item to use.
             * @param amount The output amount to use.
             * @param tag    The output NBT tag to use.
             * @param chance The chance that this output will be used.
             */
            public Builder addItem(ResourceLocation item, int amount, CompoundTag tag, float chance) {
                outputs.add(new PotentiallyAbsentItemStack.WithChance(item, amount, tag, chance));
                return this;
            }

            /**
             * Adds an output item to this recipe.
             *
             * @param item   The id of the output item to use.
             * @param amount The output amount to use.
             * @param chance The chance that this output will be used.
             */
            public Builder addItem(ResourceLocation item, int amount, float chance) {
                return addItem(item, amount, new CompoundTag(), chance);
            }

            /**
             * Adds an output item to this recipe.
             *
             * @param item   The id of the output item to use.
             * @param chance The chance that this output will be used.
             */
            public Builder addItem(ResourceLocation item, float chance) {
                return addItem(item, 1, chance);
            }

            /**
             * Adds an output item to this recipe.
             *
             * @param item   The output item to use.
             * @param amount The output amount to use.
             * @param tag    The output NBT tag to use.
             * @param chance The chance that this output will be used.
             */
            public Builder addItem(Item item, int amount, CompoundTag tag, float chance) {
                return addItem(itemId(item), amount, tag, chance);
            }

            /**
             * Adds an output item to this recipe.
             *
             * @param item   The output item to use.
             * @param amount The output amount to use.
             * @param chance The chance that this output will be used.
             */
            public Builder addItem(Item item, int amount, float chance) {
                return addItem(item, amount, new CompoundTag(), chance);
            }

            /**
             * Adds an output item to this recipe.
             *
             * @param item   The output item to use.
             * @param chance The chance that this output will be used.
             */
            public Builder addItem(Item item, float chance) {
                return addItem(item, 1, chance);
            }

            /**
             * Adds an output item to this recipe.
             *
             * @param item   The id of the output item to use.
             * @param amount The output amount to use.
             * @param tag    The output NBT tag to use.
             */
            public Builder addItem(ResourceLocation item, int amount, CompoundTag tag) {
                return addItem(item, amount, tag, 1);
            }

            /**
             * Adds an output item to this recipe.
             *
             * @param item   The id of the output item to use.
             * @param amount The output amount to use.
             */
            public Builder addItem(ResourceLocation item, int amount) {
                return addItem(item, amount, new CompoundTag());
            }

            /**
             * Adds an output item to this recipe.
             *
             * @param item The id of the output item to use.
             */
            public Builder addItem(ResourceLocation item) {
                return addItem(item, 1);
            }

            /**
             * Adds an output item to this recipe.
             *
             * @param item   The output item to use.
             * @param amount The output amount to use.
             * @param tag    The output NBT tag to use.
             */
            public Builder addItem(Item item, int amount, CompoundTag tag) {
                return addItem(itemId(item), amount, tag);
            }

            /**
             * Adds an output item to this recipe.
             *
             * @param item   The output item to use.
             * @param amount The output amount to use.
             */
            public Builder addItem(Item item, int amount) {
                return addItem(item, amount, new CompoundTag());
            }

            /**
             * Adds an output item to this recipe.
             *
             * @param item The output item to use.
             */
            public Builder addItem(Item item) {
                return addItem(item, 1);
            }

            @Override
            protected void toJson(JsonObject json, HolderLookup.Provider registries) {
                json.add("item", JsonUtil.toJson(input, registries));
                json.addProperty("duration", duration);
                JsonObject output = new JsonObject();
                output.add("items", JsonUtil.toList(outputs, registries));
                if (outputFluid != null) {
                    output.add("fluid", outputFluid.toJson(registries));
                }
                json.add("result", output);
            }
        }
    }

    public static class Squeezing extends IntegratedDynamicsDataProvider<Squeezing.Builder> {
        public Squeezing(String namespace, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super("squeezer", namespace, output, registries);
        }

        /**
         * @param id    The recipe id to use.
         * @param input The input ingredient to use.
         */
        public Builder builder(String id, Ingredient input) {
            return new Builder(this, new ResourceLocation(namespace, id), input);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final List<PotentiallyAbsentItemStack> outputs = new ArrayList<>();
            private final Ingredient input;
            private PotentiallyAbsentFluidStack outputFluid = null;

            protected Builder(Squeezing provider, ResourceLocation id, Ingredient input) {
                super(id, provider);
                this.input = input;
            }

            /**
             * Sets the output fluid of this recipe.
             *
             * @param fluid  The id of the output fluid to use.
             * @param amount The output amount to use.
             * @param tag    The output NBT tag to use.
             */
            public Builder setOutputFluid(ResourceLocation fluid, int amount, CompoundTag tag) {
                this.outputFluid = new PotentiallyAbsentFluidStack(fluid, amount, tag);
                return this;
            }

            /**
             * Sets the output fluid of this recipe.
             *
             * @param fluid  The id of the output fluid to use.
             * @param amount The output amount to use.
             */
            public Builder setOutputFluid(ResourceLocation fluid, int amount) {
                return setOutputFluid(fluid, amount, new CompoundTag());
            }

            /**
             * Sets the output fluid of this recipe.
             *
             * @param fluid The id of the output fluid to use.
             */
            public Builder setOutputFluid(ResourceLocation fluid) {
                return setOutputFluid(fluid, 1);
            }

            /**
             * Sets the output fluid of this recipe.
             *
             * @param fluid  The output fluid to use.
             * @param amount The output amount to use.
             * @param tag    The output NBT tag to use.
             */
            public Builder setOutputFluid(Fluid fluid, int amount, CompoundTag tag) {
                return setOutputFluid(fluidId(fluid), amount, tag);
            }

            /**
             * Sets the output fluid of this recipe.
             *
             * @param fluid  The output fluid to use.
             * @param amount The output amount to use.
             */
            public Builder setOutputFluid(Fluid fluid, int amount) {
                return setOutputFluid(fluid, amount, new CompoundTag());
            }

            /**
             * Sets the output fluid of this recipe.
             *
             * @param fluid The output fluid to use.
             */
            public Builder setOutputFluid(Fluid fluid) {
                return setOutputFluid(fluid, 1);
            }

            /**
             * Adds an output item to this recipe.
             *
             * @param item   The id of the output item to use.
             * @param amount The output amount to use.
             * @param tag    The output NBT tag to use.
             * @param chance The chance that this output will be used.
             */
            public Builder addItem(ResourceLocation item, int amount, CompoundTag tag, float chance) {
                outputs.add(new PotentiallyAbsentItemStack.WithChance(item, amount, tag, chance));
                return this;
            }

            /**
             * Adds an output item to this recipe.
             *
             * @param item   The id of the output item to use.
             * @param amount The output amount to use.
             * @param chance The chance that this output will be used.
             */
            public Builder addItem(ResourceLocation item, int amount, float chance) {
                return addItem(item, amount, new CompoundTag(), chance);
            }

            /**
             * Adds an output item to this recipe.
             *
             * @param item   The id of the output item to use.
             * @param chance The chance that this output will be used.
             */
            public Builder addItem(ResourceLocation item, float chance) {
                return addItem(item, 1, chance);
            }

            /**
             * Adds an output item to this recipe.
             *
             * @param item   The output item to use.
             * @param amount The output amount to use.
             * @param tag    The output NBT tag to use.
             * @param chance The chance that this output will be used.
             */
            public Builder addItem(Item item, int amount, CompoundTag tag, float chance) {
                return addItem(itemId(item), amount, tag, chance);
            }

            /**
             * Adds an output item to this recipe.
             *
             * @param item   The output item to use.
             * @param amount The output amount to use.
             * @param chance The chance that this output will be used.
             */
            public Builder addItem(Item item, int amount, float chance) {
                return addItem(item, amount, new CompoundTag(), chance);
            }

            /**
             * Adds an output item to this recipe.
             *
             * @param item   The output item to use.
             * @param chance The chance that this output will be used.
             */
            public Builder addItem(Item item, float chance) {
                return addItem(item, 1, chance);
            }

            /**
             * Adds an output item to this recipe.
             *
             * @param item   The id of the output item to use.
             * @param amount The output amount to use.
             * @param tag    The output NBT tag to use.
             */
            public Builder addItem(ResourceLocation item, int amount, CompoundTag tag) {
                return addItem(item, amount, tag, 1);
            }

            /**
             * Adds an output item to this recipe.
             *
             * @param item   The id of the output item to use.
             * @param amount The output amount to use.
             */
            public Builder addItem(ResourceLocation item, int amount) {
                return addItem(item, amount, new CompoundTag());
            }

            /**
             * Adds an output item to this recipe.
             *
             * @param item The id of the output item to use.
             */
            public Builder addItem(ResourceLocation item) {
                return addItem(item, 1);
            }

            /**
             * Adds an output item to this recipe.
             *
             * @param item   The output item to use.
             * @param amount The output amount to use.
             * @param tag    The output NBT tag to use.
             */
            public Builder addItem(Item item, int amount, CompoundTag tag) {
                return addItem(itemId(item), amount, tag);
            }

            /**
             * Adds an output item to this recipe.
             *
             * @param item   The output item to use.
             * @param amount The output amount to use.
             */
            public Builder addItem(Item item, int amount) {
                return addItem(item, amount, new CompoundTag());
            }

            /**
             * Adds an output item to this recipe.
             *
             * @param item The output item to use.
             */
            public Builder addItem(Item item) {
                return addItem(item, 1);
            }

            @Override
            protected void toJson(JsonObject json, HolderLookup.Provider registries) {
                json.add("item", JsonUtil.toJson(input, registries));
                JsonObject output = new JsonObject();
                output.add("items", JsonUtil.toList(outputs, registries));
                if (outputFluid != null) {
                    output.add("fluid", outputFluid.toJson(registries));
                }
                json.add("result", output);
            }
        }
    }
}
