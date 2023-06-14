package com.github.minecraftschurlimods.easydatagenlib.mods;

import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeBuilder;
import com.github.minecraftschurlimods.easydatagenlib.api.AbstractRecipeProvider;
import com.github.minecraftschurlimods.easydatagenlib.util.JsonSerializable;
import com.github.minecraftschurlimods.easydatagenlib.util.PotentiallyAbsentItemStack;
import com.github.minecraftschurlimods.easydatagenlib.util.mekanism.*;
import com.google.gson.JsonObject;
import net.minecraft.data.PackOutput;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

public abstract class MekanismDataProvider<T extends AbstractRecipeBuilder<?>> extends AbstractRecipeProvider<T> {
    protected MekanismDataProvider(String folder, String namespace, PackOutput output) {
        super(new ResourceLocation("mekanism", folder), namespace, output);
    }
    //TODO Chemical Infusing, Compressing, Crystallizing, Dissolution, Energy Conversion, Evaporating, Injecting, Metallurgic Infusing, Nucleosynthesizing, Painting, Pigment Mixing, Purifying, Reaction, Rotary, Separating, Washing

    public static class Activating extends GasToGasRecipe {
        public Activating(String namespace, PackOutput output) {
            super("activating", namespace, output);
        }
    }

    public static class Centrifuging extends GasToGasRecipe {
        public Centrifuging(String namespace, PackOutput output) {
            super("centrifuging", namespace, output);
        }
    }

    public static class Combining extends IngredientToItemRecipe {
        public Combining(String namespace, PackOutput output) {
            super("combining", namespace, output);
        }

        /**
         * @param id          The recipe id to use.
         * @param input       The input ingredient to use.
         * @param inputCount  The input ingredient count to use.
         * @param extraInput  The extra input ingredient to use.
         * @param extraCount  The extra input ingredient count to use.
         * @param output      The id of the output item to use.
         * @param outputCount The output count to use.
         * @param tag         The output NBT tag to use.
         */
        public Builder builder(String id, Ingredient input, int inputCount, Ingredient extraInput, int extraCount, ResourceLocation output, int outputCount, CompoundTag tag) {
            return new Builder(this, new ResourceLocation(namespace, id), input, inputCount, extraInput, extraCount, output, outputCount, tag);
        }

        /**
         * @param id          The recipe id to use.
         * @param input       The input ingredient to use.
         * @param inputCount  The input ingredient count to use.
         * @param extraInput  The extra input ingredient to use.
         * @param extraCount  The extra input ingredient count to use.
         * @param output      The id of the output item to use.
         * @param outputCount The output count to use.
         */
        public Builder builder(String id, Ingredient input, int inputCount, Ingredient extraInput, int extraCount, ResourceLocation output, int outputCount) {
            return new Builder(this, new ResourceLocation(namespace, id), input, inputCount, extraInput, extraCount, output, outputCount);
        }

        /**
         * @param id         The recipe id to use.
         * @param input      The input ingredient to use.
         * @param inputCount The input ingredient count to use.
         * @param extraInput The extra input ingredient to use.
         * @param extraCount The extra input ingredient count to use.
         * @param output     The id of the output item to use.
         */
        public Builder builder(String id, Ingredient input, int inputCount, Ingredient extraInput, int extraCount, ResourceLocation output) {
            return new Builder(this, new ResourceLocation(namespace, id), input, inputCount, extraInput, extraCount, output);
        }

        /**
         * @param id          The recipe id to use.
         * @param input       The input ingredient to use.
         * @param inputCount  The input ingredient count to use.
         * @param extraInput  The extra input ingredient to use.
         * @param extraCount  The extra input ingredient count to use.
         * @param output      The id of the output item to use.
         * @param outputCount The output count to use.
         * @param tag         The output NBT tag to use.
         */
        public Builder builder(String id, Ingredient input, int inputCount, Ingredient extraInput, int extraCount, Item output, int outputCount, CompoundTag tag) {
            return new Builder(this, new ResourceLocation(namespace, id), input, inputCount, extraInput, extraCount, output, outputCount, tag);
        }

        /**
         * @param id          The recipe id to use.
         * @param input       The input ingredient to use.
         * @param inputCount  The input ingredient count to use.
         * @param extraInput  The extra input ingredient to use.
         * @param extraCount  The extra input ingredient count to use.
         * @param output      The id of the output item to use.
         * @param outputCount The output count to use.
         */
        public Builder builder(String id, Ingredient input, int inputCount, Ingredient extraInput, int extraCount, Item output, int outputCount) {
            return new Builder(this, new ResourceLocation(namespace, id), input, inputCount, extraInput, extraCount, output, outputCount);
        }

        /**
         * @param id         The recipe id to use.
         * @param input      The input ingredient to use.
         * @param inputCount The input ingredient count to use.
         * @param extraInput The extra input ingredient to use.
         * @param extraCount The extra input ingredient count to use.
         * @param output     The id of the output item to use.
         */
        public Builder builder(String id, Ingredient input, int inputCount, Ingredient extraInput, int extraCount, Item output) {
            return new Builder(this, new ResourceLocation(namespace, id), input, inputCount, extraInput, extraCount, output);
        }

        /**
         * @param id          The recipe id to use.
         * @param input       The input ingredient to use.
         * @param extraInput  The extra input ingredient to use.
         * @param extraCount  The extra input ingredient count to use.
         * @param output      The id of the output item to use.
         * @param outputCount The output count to use.
         * @param tag         The output NBT tag to use.
         */
        public Builder builder(String id, Ingredient input, Ingredient extraInput, int extraCount, ResourceLocation output, int outputCount, CompoundTag tag) {
            return new Builder(this, new ResourceLocation(namespace, id), input, extraInput, extraCount, output, outputCount, tag);
        }

        /**
         * @param id          The recipe id to use.
         * @param input       The input ingredient to use.
         * @param extraInput  The extra input ingredient to use.
         * @param extraCount  The extra input ingredient count to use.
         * @param output      The id of the output item to use.
         * @param outputCount The output count to use.
         */
        public Builder builder(String id, Ingredient input, Ingredient extraInput, int extraCount, ResourceLocation output, int outputCount) {
            return new Builder(this, new ResourceLocation(namespace, id), input, extraInput, extraCount, output, outputCount);
        }

        /**
         * @param id         The recipe id to use.
         * @param input      The input ingredient to use.
         * @param extraInput The extra input ingredient to use.
         * @param extraCount The extra input ingredient count to use.
         * @param output     The id of the output item to use.
         */
        public Builder builder(String id, Ingredient input, Ingredient extraInput, int extraCount, ResourceLocation output) {
            return new Builder(this, new ResourceLocation(namespace, id), input, extraInput, extraCount, output);
        }

        /**
         * @param id          The recipe id to use.
         * @param input       The input ingredient to use.
         * @param extraInput  The extra input ingredient to use.
         * @param extraCount  The extra input ingredient count to use.
         * @param output      The output item to use.
         * @param outputCount The output count to use.
         * @param tag         The output NBT tag to use.
         */
        public Builder builder(String id, Ingredient input, Ingredient extraInput, int extraCount, Item output, int outputCount, CompoundTag tag) {
            return new Builder(this, new ResourceLocation(namespace, id), input, extraInput, extraCount, output, outputCount, tag);
        }

        /**
         * @param id          The recipe id to use.
         * @param input       The input ingredient to use.
         * @param extraInput  The extra input ingredient to use.
         * @param extraCount  The extra input ingredient count to use.
         * @param output      The output item to use.
         * @param outputCount The output count to use.
         */
        public Builder builder(String id, Ingredient input, Ingredient extraInput, int extraCount, Item output, int outputCount) {
            return new Builder(this, new ResourceLocation(namespace, id), input, extraInput, extraCount, output, outputCount);
        }

        /**
         * @param id         The recipe id to use.
         * @param input      The input ingredient to use.
         * @param extraInput The extra input ingredient to use.
         * @param extraCount The extra input ingredient count to use.
         * @param output     The output item to use.
         */
        public Builder builder(String id, Ingredient input, Ingredient extraInput, int extraCount, Item output) {
            return new Builder(this, new ResourceLocation(namespace, id), input, extraInput, extraCount, output);
        }

        /**
         * @param id          The recipe id to use.
         * @param input       The input ingredient to use.
         * @param inputCount  The input ingredient count to use.
         * @param extraInput  The extra input ingredient to use.
         * @param output      The id of the output item to use.
         * @param outputCount The output count to use.
         * @param tag         The output NBT tag to use.
         */
        public Builder builder(String id, Ingredient input, int inputCount, Ingredient extraInput, ResourceLocation output, int outputCount, CompoundTag tag) {
            return new Builder(this, new ResourceLocation(namespace, id), input, inputCount, extraInput, output, outputCount, tag);
        }

        /**
         * @param id          The recipe id to use.
         * @param input       The input ingredient to use.
         * @param inputCount  The input ingredient count to use.
         * @param extraInput  The extra input ingredient to use.
         * @param output      The id of the output item to use.
         * @param outputCount The output count to use.
         */
        public Builder builder(String id, Ingredient input, int inputCount, Ingredient extraInput, ResourceLocation output, int outputCount) {
            return new Builder(this, new ResourceLocation(namespace, id), input, inputCount, extraInput, output, outputCount);
        }

        /**
         * @param id         The recipe id to use.
         * @param input      The input ingredient to use.
         * @param inputCount The input ingredient count to use.
         * @param extraInput The extra input ingredient to use.
         * @param output     The id of the output item to use.
         */
        public Builder builder(String id, Ingredient input, int inputCount, Ingredient extraInput, ResourceLocation output) {
            return new Builder(this, new ResourceLocation(namespace, id), input, inputCount, extraInput, output);
        }

        /**
         * @param id          The recipe id to use.
         * @param input       The input ingredient to use.
         * @param inputCount  The input ingredient count to use.
         * @param extraInput  The extra input ingredient to use.
         * @param output      The id of the output item to use.
         * @param outputCount The output count to use.
         * @param tag         The output NBT tag to use.
         */
        public Builder builder(String id, Ingredient input, int inputCount, Ingredient extraInput, Item output, int outputCount, CompoundTag tag) {
            return new Builder(this, new ResourceLocation(namespace, id), input, inputCount, extraInput, output, outputCount, tag);
        }

        /**
         * @param id          The recipe id to use.
         * @param input       The input ingredient to use.
         * @param inputCount  The input ingredient count to use.
         * @param extraInput  The extra input ingredient to use.
         * @param output      The id of the output item to use.
         * @param outputCount The output count to use.
         */
        public Builder builder(String id, Ingredient input, int inputCount, Ingredient extraInput, Item output, int outputCount) {
            return new Builder(this, new ResourceLocation(namespace, id), input, inputCount, extraInput, output, outputCount);
        }

        /**
         * @param id         The recipe id to use.
         * @param input      The input ingredient to use.
         * @param inputCount The input ingredient count to use.
         * @param extraInput The extra input ingredient to use.
         * @param output     The id of the output item to use.
         */
        public Builder builder(String id, Ingredient input, int inputCount, Ingredient extraInput, Item output) {
            return new Builder(this, new ResourceLocation(namespace, id), input, inputCount, extraInput, output);
        }

        /**
         * @param id          The recipe id to use.
         * @param input       The input ingredient to use.
         * @param extraInput  The extra input ingredient to use.
         * @param output      The id of the output item to use.
         * @param outputCount The output count to use.
         * @param tag         The output NBT tag to use.
         */
        public Builder builder(String id, Ingredient input, Ingredient extraInput, ResourceLocation output, int outputCount, CompoundTag tag) {
            return new Builder(this, new ResourceLocation(namespace, id), input, extraInput, output, outputCount, tag);
        }

        /**
         * @param id          The recipe id to use.
         * @param input       The input ingredient to use.
         * @param extraInput  The extra input ingredient to use.
         * @param output      The id of the output item to use.
         * @param outputCount The output count to use.
         */
        public Builder builder(String id, Ingredient input, Ingredient extraInput, ResourceLocation output, int outputCount) {
            return new Builder(this, new ResourceLocation(namespace, id), input, extraInput, output, outputCount);
        }

        /**
         * @param id         The recipe id to use.
         * @param input      The input ingredient to use.
         * @param extraInput The extra input ingredient to use.
         * @param output     The id of the output item to use.
         */
        public Builder builder(String id, Ingredient input, Ingredient extraInput, ResourceLocation output) {
            return new Builder(this, new ResourceLocation(namespace, id), input, extraInput, output);
        }

        /**
         * @param id          The recipe id to use.
         * @param input       The input ingredient to use.
         * @param extraInput  The extra input ingredient to use.
         * @param output      The output item to use.
         * @param outputCount The output count to use.
         * @param tag         The output NBT tag to use.
         */
        public Builder builder(String id, Ingredient input, Ingredient extraInput, Item output, int outputCount, CompoundTag tag) {
            return new Builder(this, new ResourceLocation(namespace, id), input, extraInput, output, outputCount, tag);
        }

        /**
         * @param id          The recipe id to use.
         * @param input       The input ingredient to use.
         * @param extraInput  The extra input ingredient to use.
         * @param output      The output item to use.
         * @param outputCount The output count to use.
         */
        public Builder builder(String id, Ingredient input, Ingredient extraInput, Item output, int outputCount) {
            return new Builder(this, new ResourceLocation(namespace, id), input, extraInput, output, outputCount);
        }

        /**
         * @param id         The recipe id to use.
         * @param input      The input ingredient to use.
         * @param extraInput The extra input ingredient to use.
         * @param output     The output item to use.
         */
        public Builder builder(String id, Ingredient input, Ingredient extraInput, Item output) {
            return new Builder(this, new ResourceLocation(namespace, id), input, extraInput, output);
        }

        public static class Builder extends IngredientToItemRecipe.Builder {
            private final IngredientWithAmount extraInput;

            protected Builder(Combining provider, ResourceLocation id, Ingredient input, int inputCount, Ingredient extraInput, int extraCount, ResourceLocation output, int outputCount, CompoundTag tag) {
                super(provider, id, input, inputCount, output, outputCount, tag);
                this.extraInput = new IngredientWithAmount(extraInput, extraCount);
            }

            protected Builder(Combining provider, ResourceLocation id, Ingredient input, int inputCount, Ingredient extraInput, int extraCount, ResourceLocation output, int outputCount) {
                super(provider, id, input, inputCount, output, outputCount);
                this.extraInput = new IngredientWithAmount(extraInput, extraCount);
            }

            protected Builder(Combining provider, ResourceLocation id, Ingredient input, int inputCount, Ingredient extraInput, int extraCount, ResourceLocation output) {
                super(provider, id, input, inputCount, output);
                this.extraInput = new IngredientWithAmount(extraInput, extraCount);
            }

            protected Builder(Combining provider, ResourceLocation id, Ingredient input, int inputCount, Ingredient extraInput, int extraCount, Item output, int outputCount, CompoundTag tag) {
                super(provider, id, input, inputCount, output, outputCount, tag);
                this.extraInput = new IngredientWithAmount(extraInput, extraCount);
            }

            protected Builder(Combining provider, ResourceLocation id, Ingredient input, int inputCount, Ingredient extraInput, int extraCount, Item output, int outputCount) {
                super(provider, id, input, inputCount, output, outputCount);
                this.extraInput = new IngredientWithAmount(extraInput, extraCount);
            }

            protected Builder(Combining provider, ResourceLocation id, Ingredient input, int inputCount, Ingredient extraInput, int extraCount, Item output) {
                super(provider, id, input, inputCount, output);
                this.extraInput = new IngredientWithAmount(extraInput, extraCount);
            }

            protected Builder(Combining provider, ResourceLocation id, Ingredient input, Ingredient extraInput, int extraCount, ResourceLocation output, int outputCount, CompoundTag tag) {
                super(provider, id, input, output, outputCount, tag);
                this.extraInput = new IngredientWithAmount(extraInput, extraCount);
            }

            protected Builder(Combining provider, ResourceLocation id, Ingredient input, Ingredient extraInput, int extraCount, ResourceLocation output, int outputCount) {
                super(provider, id, input, output, outputCount);
                this.extraInput = new IngredientWithAmount(extraInput, extraCount);
            }

            protected Builder(Combining provider, ResourceLocation id, Ingredient input, Ingredient extraInput, int extraCount, ResourceLocation output) {
                super(provider, id, input, output);
                this.extraInput = new IngredientWithAmount(extraInput, extraCount);
            }

            protected Builder(Combining provider, ResourceLocation id, Ingredient input, Ingredient extraInput, int extraCount, Item output, int outputCount, CompoundTag tag) {
                super(provider, id, input, output, outputCount, tag);
                this.extraInput = new IngredientWithAmount(extraInput, extraCount);
            }

            protected Builder(Combining provider, ResourceLocation id, Ingredient input, Ingredient extraInput, int extraCount, Item output, int outputCount) {
                super(provider, id, input, output, outputCount);
                this.extraInput = new IngredientWithAmount(extraInput, extraCount);
            }

            protected Builder(Combining provider, ResourceLocation id, Ingredient input, Ingredient extraInput, int extraCount, Item output) {
                super(provider, id, input, output);
                this.extraInput = new IngredientWithAmount(extraInput, extraCount);
            }

            protected Builder(Combining provider, ResourceLocation id, Ingredient input, int inputCount, Ingredient extraInput, ResourceLocation output, int outputCount, CompoundTag tag) {
                this(provider, id, input, inputCount, extraInput, 1, output, outputCount, tag);
            }

            protected Builder(Combining provider, ResourceLocation id, Ingredient input, int inputCount, Ingredient extraInput, ResourceLocation output, int outputCount) {
                this(provider, id, input, inputCount, extraInput, 1, output, outputCount);
            }

            protected Builder(Combining provider, ResourceLocation id, Ingredient input, int inputCount, Ingredient extraInput, ResourceLocation output) {
                this(provider, id, input, inputCount, extraInput, 1, output);
            }

            protected Builder(Combining provider, ResourceLocation id, Ingredient input, int inputCount, Ingredient extraInput, Item output, int outputCount, CompoundTag tag) {
                this(provider, id, input, inputCount, extraInput, 1, output, outputCount, tag);
            }

            protected Builder(Combining provider, ResourceLocation id, Ingredient input, int inputCount, Ingredient extraInput, Item output, int outputCount) {
                this(provider, id, input, inputCount, extraInput, 1, output, outputCount);
            }

            protected Builder(Combining provider, ResourceLocation id, Ingredient input, int inputCount, Ingredient extraInput, Item output) {
                this(provider, id, input, inputCount, extraInput, 1, output);
            }

            protected Builder(Combining provider, ResourceLocation id, Ingredient input, Ingredient extraInput, ResourceLocation output, int outputCount, CompoundTag tag) {
                this(provider, id, input, extraInput, 1, output, outputCount, tag);
            }

            protected Builder(Combining provider, ResourceLocation id, Ingredient input, Ingredient extraInput, ResourceLocation output, int outputCount) {
                this(provider, id, input, extraInput, 1, output, outputCount);
            }

            protected Builder(Combining provider, ResourceLocation id, Ingredient input, Ingredient extraInput, ResourceLocation output) {
                this(provider, id, input, extraInput, 1, output);
            }

            protected Builder(Combining provider, ResourceLocation id, Ingredient input, Ingredient extraInput, Item output, int outputCount, CompoundTag tag) {
                this(provider, id, input, extraInput, 1, output, outputCount, tag);
            }

            protected Builder(Combining provider, ResourceLocation id, Ingredient input, Ingredient extraInput, Item output, int outputCount) {
                this(provider, id, input, extraInput, 1, output, outputCount);
            }

            protected Builder(Combining provider, ResourceLocation id, Ingredient input, Ingredient extraInput, Item output) {
                this(provider, id, input, extraInput, 1, output);
            }

            @Override
            protected void toJson(JsonObject json) {
                json.add("mainInput", input.toJson());
                json.add("extraInput", extraInput.toJson());
                json.add("output", output.toJson());
            }
        }
    }

    public static class Crushing extends IngredientToItemRecipe {
        public Crushing(String namespace, PackOutput output) {
            super("crushing", namespace, output);
        }
    }

    public static class Enriching extends IngredientToItemRecipe {
        public Enriching(String namespace, PackOutput output) {
            super("enriching", namespace, output);
        }
    }

    public static class GasConversion extends IngredientToTRecipe<Chemical.Stack<Gas>> {
        public GasConversion(String namespace, PackOutput output) {
            super("gas_conversion", namespace, output);
        }
    }

    public static class InfusionConversion extends IngredientToTRecipe<Chemical.Stack<InfuseType>> {
        public InfusionConversion(String namespace, PackOutput output) {
            super("infusion_conversion", namespace, output);
        }
    }

    public static class Oxidizing extends IngredientToTRecipe<Chemical.Stack<Gas>> {
        public Oxidizing(String namespace, PackOutput output) {
            super("oxidizing", namespace, output);
        }
    }

    public static class PigmentExtracting extends IngredientToTRecipe<Chemical.Stack<Pigment>> {
        public PigmentExtracting(String namespace, PackOutput output) {
            super("pigment_extracting", namespace, output);
        }
    }

    public static class Sawing extends IngredientToItemRecipe {
        public Sawing(String namespace, PackOutput output) {
            super("sawing", namespace, output);
        }

        /**
         * @param id          The recipe id to use.
         * @param input       The input ingredient to use.
         * @param inputCount  The input ingredient count to use.
         * @param output      The id of the output item to use.
         * @param outputCount The output count to use.
         * @param tag         The output NBT tag to use.
         */
        public Builder builder(String id, Ingredient input, int inputCount, ResourceLocation output, int outputCount, CompoundTag tag) {
            return new Builder(this, new ResourceLocation(namespace, id), input, inputCount, output, outputCount, tag);
        }

        /**
         * @param id          The recipe id to use.
         * @param input       The input ingredient to use.
         * @param inputCount  The input ingredient count to use.
         * @param output      The id of the output item to use.
         * @param outputCount The output count to use.
         */
        public Builder builder(String id, Ingredient input, int inputCount, ResourceLocation output, int outputCount) {
            return new Builder(this, new ResourceLocation(namespace, id), input, inputCount, output, outputCount);
        }

        /**
         * @param id         The recipe id to use.
         * @param input      The input ingredient to use.
         * @param inputCount The input ingredient count to use.
         * @param output     The id of the output item to use.
         */
        public Builder builder(String id, Ingredient input, int inputCount, ResourceLocation output) {
            return new Builder(this, new ResourceLocation(namespace, id), input, inputCount, output);
        }

        /**
         * @param id          The recipe id to use.
         * @param input       The input ingredient to use.
         * @param inputCount  The input ingredient count to use.
         * @param output      The output item to use.
         * @param outputCount The output count to use.
         * @param tag         The output NBT tag to use.
         */
        public Builder builder(String id, Ingredient input, int inputCount, Item output, int outputCount, CompoundTag tag) {
            return new Builder(this, new ResourceLocation(namespace, id), input, inputCount, output, outputCount, tag);
        }

        /**
         * @param id          The recipe id to use.
         * @param input       The input ingredient to use.
         * @param inputCount  The input ingredient count to use.
         * @param output      The output item to use.
         * @param outputCount The output count to use.
         */
        public Builder builder(String id, Ingredient input, int inputCount, Item output, int outputCount) {
            return new Builder(this, new ResourceLocation(namespace, id), input, inputCount, output, outputCount);
        }

        /**
         * @param id         The recipe id to use.
         * @param input      The input ingredient to use.
         * @param inputCount The input ingredient count to use.
         * @param output     The output item to use.
         */
        public Builder builder(String id, Ingredient input, int inputCount, Item output) {
            return new Builder(this, new ResourceLocation(namespace, id), input, inputCount, output);
        }

        /**
         * @param id          The recipe id to use.
         * @param input       The input ingredient to use.
         * @param output      The id of the output item to use.
         * @param outputCount The output count to use.
         * @param tag         The output NBT tag to use.
         */
        public Builder builder(String id, Ingredient input, ResourceLocation output, int outputCount, CompoundTag tag) {
            return new Builder(this, new ResourceLocation(namespace, id), input, output, outputCount, tag);
        }

        /**
         * @param id          The recipe id to use.
         * @param input       The input ingredient to use.
         * @param output      The id of the output item to use.
         * @param outputCount The output count to use.
         */
        public Builder builder(String id, Ingredient input, ResourceLocation output, int outputCount) {
            return new Builder(this, new ResourceLocation(namespace, id), input, output, outputCount);
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
         * @param id          The recipe id to use.
         * @param input       The input ingredient to use.
         * @param output      The output item to use.
         * @param outputCount The output count to use.
         * @param tag         The output NBT tag to use.
         */
        public Builder builder(String id, Ingredient input, Item output, int outputCount, CompoundTag tag) {
            return new Builder(this, new ResourceLocation(namespace, id), input, output, outputCount, tag);
        }

        /**
         * @param id          The recipe id to use.
         * @param input       The input ingredient to use.
         * @param output      The output item to use.
         * @param outputCount The output count to use.
         */
        public Builder builder(String id, Ingredient input, Item output, int outputCount) {
            return new Builder(this, new ResourceLocation(namespace, id), input, output, outputCount);
        }

        /**
         * @param id     The recipe id to use.
         * @param input  The input ingredient to use.
         * @param output The output item to use.
         */
        public Builder builder(String id, Ingredient input, Item output) {
            return new Builder(this, new ResourceLocation(namespace, id), input, output);
        }

        public static class Builder extends IngredientToItemRecipe.Builder {
            private PotentiallyAbsentItemStack secondaryOutput = null;
            private float chance = 1;

            protected Builder(Sawing provider, ResourceLocation id, Ingredient input, int inputCount, ResourceLocation output, int outputCount, CompoundTag tag) {
                super(provider, id, input, inputCount, output, outputCount, tag);
            }

            protected Builder(Sawing provider, ResourceLocation id, Ingredient input, int inputCount, ResourceLocation output, int outputCount) {
                this(provider, id, input, inputCount, output, outputCount, new CompoundTag());
            }

            protected Builder(Sawing provider, ResourceLocation id, Ingredient input, int inputCount, ResourceLocation output) {
                this(provider, id, input, inputCount, output, 1);
            }

            protected Builder(Sawing provider, ResourceLocation id, Ingredient input, int inputCount, Item output, int outputCount, CompoundTag tag) {
                this(provider, id, input, inputCount, itemId(output), outputCount, tag);
            }

            protected Builder(Sawing provider, ResourceLocation id, Ingredient input, int inputCount, Item output, int outputCount) {
                this(provider, id, input, inputCount, output, outputCount, new CompoundTag());
            }

            protected Builder(Sawing provider, ResourceLocation id, Ingredient input, int inputCount, Item output) {
                this(provider, id, input, inputCount, output, 1);
            }

            protected Builder(Sawing provider, ResourceLocation id, Ingredient input, ResourceLocation output, int outputCount, CompoundTag tag) {
                this(provider, id, input, 1, output, outputCount, tag);
            }

            protected Builder(Sawing provider, ResourceLocation id, Ingredient input, ResourceLocation output, int outputCount) {
                this(provider, id, input, output, outputCount, new CompoundTag());
            }

            protected Builder(Sawing provider, ResourceLocation id, Ingredient input, ResourceLocation output) {
                this(provider, id, input, output, 1);
            }

            protected Builder(Sawing provider, ResourceLocation id, Ingredient input, Item output, int outputCount, CompoundTag tag) {
                this(provider, id, input, itemId(output), outputCount, tag);
            }

            protected Builder(Sawing provider, ResourceLocation id, Ingredient input, Item output, int outputCount) {
                this(provider, id, input, output, outputCount, new CompoundTag());
            }

            protected Builder(Sawing provider, ResourceLocation id, Ingredient input, Item output) {
                this(provider, id, input, output, 1);
            }

            /**
             * Sets the secondary output of this recipe.
             *
             * @param output The id of the output item to use.
             */
            public Builder setSecondaryOutput(ResourceLocation output) {
                return setSecondaryOutput(output, 1);
            }

            /**
             * Sets the secondary output of this recipe.
             *
             * @param output The output item to use.
             */
            public Builder setSecondaryOutput(Item output) {
                return setSecondaryOutput(output, 1);
            }

            /**
             * Sets the secondary output of this recipe.
             *
             * @param output      The id of the output item to use.
             * @param outputCount The output count to use.
             * @param tag         The output NBT tag to use.
             * @param chance      The chance that this output will be used.
             */
            public Builder setSecondaryOutput(ResourceLocation output, int outputCount, CompoundTag tag, float chance) {
                this.secondaryOutput = new PotentiallyAbsentItemStack(output, outputCount, tag);
                this.chance = chance;
                return this;
            }

            /**
             * Sets the secondary output to use.
             *
             * @param output      The id of the output item to use.
             * @param outputCount The output count to use.
             * @param chance      The chance that this output will be used.
             */
            public Builder setSecondaryOutput(ResourceLocation output, int outputCount, float chance) {
                return setSecondaryOutput(output, outputCount, new CompoundTag(), chance);
            }

            /**
             * Sets the secondary output to use.
             *
             * @param output The id of the output item to use.
             * @param chance The chance that this output will be used.
             */
            public Builder setSecondaryOutput(ResourceLocation output, float chance) {
                return setSecondaryOutput(output, 1, chance);
            }

            /**
             * Sets the secondary output to use.
             *
             * @param output      The id of the output item to use.
             * @param outputCount The output count to use.
             * @param tag         The output NBT tag to use.
             */
            public Builder setSecondaryOutput(ResourceLocation output, int outputCount, CompoundTag tag) {
                return setSecondaryOutput(output, outputCount, tag, 1);
            }

            /**
             * Sets the secondary output to use.
             *
             * @param output      The id of the output item to use.
             * @param outputCount The output count to use.
             */
            public Builder setSecondaryOutput(ResourceLocation output, int outputCount) {
                return setSecondaryOutput(output, outputCount, new CompoundTag());
            }

            /**
             * Sets the secondary output to use.
             *
             * @param output      The output item to use.
             * @param outputCount The output count to use.
             * @param tag         The output NBT tag to use.
             * @param chance      The chance that this output will be used.
             */
            public Builder setSecondaryOutput(Item output, int outputCount, CompoundTag tag, float chance) {
                return setSecondaryOutput(itemId(output), outputCount, tag, chance);
            }

            /**
             * Sets the secondary output to use.
             *
             * @param output      The output item to use.
             * @param outputCount The output count to use.
             * @param chance      The chance that this output will be used.
             */
            public Builder setSecondaryOutput(Item output, int outputCount, float chance) {
                return setSecondaryOutput(output, outputCount, new CompoundTag(), chance);
            }

            /**
             * Sets the secondary output to use.
             *
             * @param output The output item to use.
             * @param chance The chance that this output will be used.
             */
            public Builder setSecondaryOutput(Item output, float chance) {
                return setSecondaryOutput(output, 1, chance);
            }

            /**
             * Sets the secondary output to use.
             *
             * @param output      The output item to use.
             * @param outputCount The output count to use.
             * @param tag         The output NBT tag to use.
             */
            public Builder setSecondaryOutput(Item output, int outputCount, CompoundTag tag) {
                return setSecondaryOutput(itemId(output), outputCount, tag);
            }

            /**
             * Sets the secondary output to use.
             *
             * @param output      The output item to use.
             * @param outputCount The output count to use.
             */
            public Builder setSecondaryOutput(Item output, int outputCount) {
                return setSecondaryOutput(output, outputCount, new CompoundTag());
            }

            @Override
            protected void toJson(JsonObject json) {
                json.add("input", input.toJson());
                json.add("mainOutput", output.toJson());
                if (secondaryOutput != null && chance > 0 && chance <= 1) {
                    json.add("secondaryOutput", secondaryOutput.toJson());
                    json.addProperty("secondaryChance", chance);
                }
            }
        }
    }

    public static class Smelting extends IngredientToItemRecipe {
        public Smelting(String namespace, PackOutput output) {
            super("smelting", namespace, output);
        }
    }

    public static abstract class Abstract1To1Recipe<I extends JsonSerializable, O extends JsonSerializable, B extends Abstract1To1Recipe.Builder<I, O>> extends MekanismDataProvider<B> {
        protected Abstract1To1Recipe(String folder, String namespace, PackOutput output) {
            super(folder, namespace, output);
        }

        public static class Builder<I extends JsonSerializable, O extends JsonSerializable> extends AbstractRecipeBuilder<Builder<I, O>> {
            protected final I input;
            protected final O output;

            protected Builder(Abstract1To1Recipe<I, O, Builder<I, O>> provider, ResourceLocation id, I input, O output) {
                super(id, provider);
                this.input = input;
                this.output = output;
            }

            @Override
            protected void toJson(JsonObject json) {
                json.add("input", input.toJson());
                json.add("output", output.toJson());
            }
        }
    }

    public static abstract class IngredientToTRecipe<T extends JsonSerializable> extends Abstract1To1Recipe<IngredientWithAmount, T, IngredientToTRecipe.Builder<T>> {
        protected IngredientToTRecipe(String folder, String namespace, PackOutput output) {
            super(folder, namespace, output);
        }

        /**
         * @param id          The recipe id to use.
         * @param input       The input ingredient to use.
         * @param outputCount The input ingredient count to use.
         * @param output      The output chemical to use.
         */
        public Builder<T> builder(String id, Ingredient input, int outputCount, T output) {
            return new Builder<>(this, new ResourceLocation(namespace, id), input, outputCount, output);
        }

        /**
         * @param id     The recipe id to use.
         * @param input  The input ingredient to use.
         * @param output The output chemical to use.
         */
        public Builder<T> builder(String id, Ingredient input, T output) {
            return new Builder<>(this, new ResourceLocation(namespace, id), input, output);
        }

        public static class Builder<T extends JsonSerializable> extends Abstract1To1Recipe.Builder<IngredientWithAmount, T> {
            protected Builder(IngredientToTRecipe<T> provider, ResourceLocation id, Ingredient input, int outputCount, T output) {
                super((Abstract1To1Recipe<IngredientWithAmount, T, Abstract1To1Recipe.Builder<IngredientWithAmount,T>>) (Object) provider, id, new IngredientWithAmount(input, outputCount), output);
            }

            protected Builder(IngredientToTRecipe<T> provider, ResourceLocation id, Ingredient input, T output) {
                this(provider, id, input, 1, output);
            }
        }
    }

    public static abstract class TToItemRecipe<T extends JsonSerializable> extends Abstract1To1Recipe<T, PotentiallyAbsentItemStack, TToItemRecipe.Builder<T>> {
        protected TToItemRecipe(String folder, String namespace, PackOutput output) {
            super(folder, namespace, output);
        }

        /**
         * @param id          The recipe id to use.
         * @param input       The input ingredient to use.
         * @param output      The id of the output item to use.
         * @param outputCount The output count to use.
         * @param tag         The output NBT tag to use.
         */
        public Builder<T> builder(String id, T input, ResourceLocation output, int outputCount, CompoundTag tag) {
            return new Builder<>(this, new ResourceLocation(namespace, id), input, output, outputCount, tag);
        }

        /**
         * @param id          The recipe id to use.
         * @param input       The input ingredient to use.
         * @param output      The id of the output item to use.
         * @param outputCount The output count to use.
         */
        public Builder<T> builder(String id, T input, ResourceLocation output, int outputCount) {
            return new Builder<>(this, new ResourceLocation(namespace, id), input, output, outputCount);
        }

        /**
         * @param id     The recipe id to use.
         * @param input  The input ingredient to use.
         * @param output The id of the output item to use.
         */
        public Builder<T> builder(String id, T input, ResourceLocation output) {
            return new Builder<>(this, new ResourceLocation(namespace, id), input, output);
        }

        /**
         * @param id          The recipe id to use.
         * @param input       The input ingredient to use.
         * @param output      The output item to use.
         * @param outputCount The output count to use.
         * @param tag         The output NBT tag to use.
         */
        public Builder<T> builder(String id, T input, Item output, int outputCount, CompoundTag tag) {
            return new Builder<>(this, new ResourceLocation(namespace, id), input, output, outputCount, tag);
        }

        /**
         * @param id          The recipe id to use.
         * @param input       The input ingredient to use.
         * @param output      The output item to use.
         * @param outputCount The output count to use.
         */
        public Builder<T> builder(String id, T input, Item output, int outputCount) {
            return new Builder<>(this, new ResourceLocation(namespace, id), input, output, outputCount);
        }

        /**
         * @param id     The recipe id to use.
         * @param input  The input ingredient to use.
         * @param output The output item to use.
         */
        public Builder<T> builder(String id, T input, Item output) {
            return new Builder<>(this, new ResourceLocation(namespace, id), input, output);
        }

        public static class Builder<T extends JsonSerializable> extends Abstract1To1Recipe.Builder<T, PotentiallyAbsentItemStack> {
            protected Builder(TToItemRecipe<T> provider, ResourceLocation id, T input, ResourceLocation output, int outputCount, CompoundTag tag) {
                super((Abstract1To1Recipe<T, PotentiallyAbsentItemStack, Abstract1To1Recipe.Builder<T, PotentiallyAbsentItemStack>>) (Object) provider, id, input, new PotentiallyAbsentItemStack(output, outputCount, tag));
            }

            protected Builder(TToItemRecipe<T> provider, ResourceLocation id, T input, ResourceLocation output, int outputCount) {
                this(provider, id, input, output, outputCount, new CompoundTag());
            }

            protected Builder(TToItemRecipe<T> provider, ResourceLocation id, T input, ResourceLocation output) {
                this(provider, id, input, output, 1);
            }

            protected Builder(TToItemRecipe<T> provider, ResourceLocation id, T input, Item output, int outputCount, CompoundTag tag) {
                this(provider, id, input, itemId(output), outputCount, tag);
            }

            protected Builder(TToItemRecipe<T> provider, ResourceLocation id, T input, Item output, int outputCount) {
                this(provider, id, input, output, outputCount, new CompoundTag());
            }

            protected Builder(TToItemRecipe<T> provider, ResourceLocation id, T input, Item output) {
                this(provider, id, input, output, 1);
            }
        }
    }

    public static abstract class IngredientToItemRecipe extends Abstract1To1Recipe<IngredientWithAmount, PotentiallyAbsentItemStack, IngredientToItemRecipe.Builder> {
        protected IngredientToItemRecipe(String folder, String namespace, PackOutput output) {
            super(folder, namespace, output);
        }

        /**
         * @param id          The recipe id to use.
         * @param input       The input ingredient to use.
         * @param inputCount  The input ingredient count to use.
         * @param output      The id of the output item to use.
         * @param outputCount The output count to use.
         * @param tag         The output NBT tag to use.
         */
        public Builder builder(String id, Ingredient input, int inputCount, ResourceLocation output, int outputCount, CompoundTag tag) {
            return new Builder(this, new ResourceLocation(namespace, id), input, inputCount, output, outputCount, tag);
        }

        /**
         * @param id          The recipe id to use.
         * @param input       The input ingredient to use.
         * @param inputCount  The input ingredient count to use.
         * @param output      The id of the output item to use.
         * @param outputCount The output count to use.
         */
        public Builder builder(String id, Ingredient input, int inputCount, ResourceLocation output, int outputCount) {
            return new Builder(this, new ResourceLocation(namespace, id), input, inputCount, output, outputCount);
        }

        /**
         * @param id         The recipe id to use.
         * @param input      The input ingredient to use.
         * @param inputCount The input ingredient count to use.
         * @param output     The id of the output item to use.
         */
        public Builder builder(String id, Ingredient input, int inputCount, ResourceLocation output) {
            return new Builder(this, new ResourceLocation(namespace, id), input, inputCount, output);
        }

        /**
         * @param id          The recipe id to use.
         * @param input       The input ingredient to use.
         * @param inputCount  The input ingredient count to use.
         * @param output      The id of the output item to use.
         * @param outputCount The output count to use.
         * @param tag         The output NBT tag to use.
         */
        public Builder builder(String id, Ingredient input, int inputCount, Item output, int outputCount, CompoundTag tag) {
            return new Builder(this, new ResourceLocation(namespace, id), input, inputCount, output, outputCount, tag);
        }

        /**
         * @param id          The recipe id to use.
         * @param input       The input ingredient to use.
         * @param inputCount  The input ingredient count to use.
         * @param output      The id of the output item to use.
         * @param outputCount The output count to use.
         */
        public Builder builder(String id, Ingredient input, int inputCount, Item output, int outputCount) {
            return new Builder(this, new ResourceLocation(namespace, id), input, inputCount, output, outputCount);
        }

        /**
         * @param id         The recipe id to use.
         * @param input      The input ingredient to use.
         * @param inputCount The input ingredient count to use.
         * @param output     The id of the output item to use.
         */
        public Builder builder(String id, Ingredient input, int inputCount, Item output) {
            return new Builder(this, new ResourceLocation(namespace, id), input, inputCount, output);
        }

        /**
         * @param id          The recipe id to use.
         * @param input       The input ingredient to use.
         * @param output      The id of the output item to use.
         * @param outputCount The output count to use.
         * @param tag         The output NBT tag to use.
         */
        public Builder builder(String id, Ingredient input, ResourceLocation output, int outputCount, CompoundTag tag) {
            return new Builder(this, new ResourceLocation(namespace, id), input, output, outputCount, tag);
        }

        /**
         * @param id          The recipe id to use.
         * @param input       The input ingredient to use.
         * @param output      The id of the output item to use.
         * @param outputCount The output count to use.
         */
        public Builder builder(String id, Ingredient input, ResourceLocation output, int outputCount) {
            return new Builder(this, new ResourceLocation(namespace, id), input, output, outputCount);
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
         * @param id          The recipe id to use.
         * @param input       The input ingredient to use.
         * @param output      The output item to use.
         * @param outputCount The output count to use.
         * @param tag         The output NBT tag to use.
         */
        public Builder builder(String id, Ingredient input, Item output, int outputCount, CompoundTag tag) {
            return new Builder(this, new ResourceLocation(namespace, id), input, output, outputCount, tag);
        }

        /**
         * @param id          The recipe id to use.
         * @param input       The input ingredient to use.
         * @param output      The output item to use.
         * @param outputCount The output count to use.
         */
        public Builder builder(String id, Ingredient input, Item output, int outputCount) {
            return new Builder(this, new ResourceLocation(namespace, id), input, output, outputCount);
        }

        /**
         * @param id     The recipe id to use.
         * @param input  The input ingredient to use.
         * @param output The output item to use.
         */
        public Builder builder(String id, Ingredient input, Item output) {
            return new Builder(this, new ResourceLocation(namespace, id), input, output);
        }

        public static class Builder extends Abstract1To1Recipe.Builder<IngredientWithAmount, PotentiallyAbsentItemStack> {
            protected Builder(IngredientToItemRecipe provider, ResourceLocation id, Ingredient input, int inputCount, ResourceLocation output, int outputCount, CompoundTag tag) {
                super((Abstract1To1Recipe<IngredientWithAmount, PotentiallyAbsentItemStack, Abstract1To1Recipe.Builder<IngredientWithAmount, PotentiallyAbsentItemStack>>) (Object) provider, id, new IngredientWithAmount(input, inputCount), new PotentiallyAbsentItemStack(output, outputCount, tag));
            }

            protected Builder(IngredientToItemRecipe provider, ResourceLocation id, Ingredient input, int inputCount, ResourceLocation output, int outputCount) {
                this(provider, id, input, inputCount, output, outputCount, new CompoundTag());
            }

            protected Builder(IngredientToItemRecipe provider, ResourceLocation id, Ingredient input, int inputCount, ResourceLocation output) {
                this(provider, id, input, inputCount, output, 1);
            }

            protected Builder(IngredientToItemRecipe provider, ResourceLocation id, Ingredient input, int inputCount, Item output, int outputCount, CompoundTag tag) {
                this(provider, id, input, inputCount, itemId(output), outputCount, tag);
            }

            protected Builder(IngredientToItemRecipe provider, ResourceLocation id, Ingredient input, int inputCount, Item output, int outputCount) {
                this(provider, id, input, inputCount, output, outputCount, new CompoundTag());
            }

            protected Builder(IngredientToItemRecipe provider, ResourceLocation id, Ingredient input, int inputCount, Item output) {
                this(provider, id, input, inputCount, output, 1);
            }

            protected Builder(IngredientToItemRecipe provider, ResourceLocation id, Ingredient input, ResourceLocation output, int outputCount, CompoundTag tag) {
                this(provider, id, input, 1, output, outputCount, tag);
            }

            protected Builder(IngredientToItemRecipe provider, ResourceLocation id, Ingredient input, ResourceLocation output, int outputCount) {
                this(provider, id, input, output, outputCount, new CompoundTag());
            }

            protected Builder(IngredientToItemRecipe provider, ResourceLocation id, Ingredient input, ResourceLocation output) {
                this(provider, id, input, output, 1);
            }

            protected Builder(IngredientToItemRecipe provider, ResourceLocation id, Ingredient input, Item output, int outputCount, CompoundTag tag) {
                this(provider, id, input, itemId(output), outputCount, tag);
            }

            protected Builder(IngredientToItemRecipe provider, ResourceLocation id, Ingredient input, Item output, int outputCount) {
                this(provider, id, input, output, outputCount, new CompoundTag());
            }

            protected Builder(IngredientToItemRecipe provider, ResourceLocation id, Ingredient input, Item output) {
                this(provider, id, input, output, 1);
            }
        }
    }

    public static abstract class GasToGasRecipe extends Abstract1To1Recipe<Chemical.Stack<Gas>, Chemical.Stack<Gas>, GasToGasRecipe.Builder> {
        protected GasToGasRecipe(String folder, String namespace, PackOutput output) {
            super(folder, namespace, output);
        }

        /**
         * @param id     The recipe id to use.
         * @param input  The input gas stack to use.
         * @param output The output gas stack to use.
         */
        public Builder builder(String id, Chemical.Stack<Gas> input, Chemical.Stack<Gas> output) {
            return new Builder(this, new ResourceLocation(namespace, id), input, output);
        }

        public static class Builder extends Abstract1To1Recipe.Builder<Chemical.Stack<Gas>, Chemical.Stack<Gas>> {
            protected Builder(GasToGasRecipe provider, ResourceLocation id, Chemical.Stack<Gas> input, Chemical.Stack<Gas> output) {
                super((Abstract1To1Recipe<Chemical.Stack<Gas>, Chemical.Stack<Gas>, Abstract1To1Recipe.Builder<Chemical.Stack<Gas>, Chemical.Stack<Gas>>>) (Object) provider, id, input, output);
            }
        }
    }
}
