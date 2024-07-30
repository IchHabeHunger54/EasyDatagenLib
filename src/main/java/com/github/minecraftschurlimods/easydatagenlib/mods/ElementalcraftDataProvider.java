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

import java.util.concurrent.CompletableFuture;

public abstract class ElementalcraftDataProvider<T extends AbstractRecipeBuilder<?>> extends AbstractRecipeProvider<T> {
    protected ElementalcraftDataProvider(String folder, String namespace, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(new ResourceLocation("elementalcraft", folder), namespace, output, registries);
    }
    //TODO Binding, Crystallization, Infusion, Inscription, Pure Infusion, Spell Craft

    public static class Grinding extends IO {
        public Grinding(String namespace, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super("grinding", namespace, output, registries);
        }
    }

    public static class Sawing extends IO {
        public Sawing(String namespace, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super("sawing", namespace, output, registries);
        }
    }

    public static abstract class IO extends ElementalcraftDataProvider<IO.Builder> {
        protected IO(String folder, String namespace, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super(folder, namespace, output, registries);
        }

        /**
         * @param id            The recipe id to use.
         * @param input         The input ingredient to use.
         * @param output        The id of the output item to use.
         * @param count         The output count to use.
         * @param tag           The output NBT tag to use.
         * @param elementAmount The element amount to use.
         * @param luckRatio     The luck ratio to use.
         */
        public Builder builder(String id, Ingredient input, ResourceLocation output, int count, CompoundTag tag, int elementAmount, int luckRatio) {
            return new Builder(this, new ResourceLocation(namespace, id), input, output, count, tag, elementAmount, luckRatio);
        }

        /**
         * @param id            The recipe id to use.
         * @param input         The input ingredient to use.
         * @param output        The id of the output item to use.
         * @param count         The output count to use.
         * @param elementAmount The element amount to use.
         * @param luckRatio     The luck ratio to use.
         */
        public Builder builder(String id, Ingredient input, ResourceLocation output, int count, int elementAmount, int luckRatio) {
            return new Builder(this, new ResourceLocation(namespace, id), input, output, count, new CompoundTag(), elementAmount, luckRatio);
        }

        /**
         * @param id            The recipe id to use.
         * @param input         The input ingredient to use.
         * @param output        The id of the output item to use.
         * @param elementAmount The element amount to use.
         * @param luckRatio     The luck ratio to use.
         */
        public Builder builder(String id, Ingredient input, ResourceLocation output, int elementAmount, int luckRatio) {
            return new Builder(this, new ResourceLocation(namespace, id), input, output, 1, elementAmount, luckRatio);
        }

        /**
         * @param id            The recipe id to use.
         * @param input         The input ingredient to use.
         * @param output        The output item to use.
         * @param count         The output count to use.
         * @param tag           The output NBT tag to use.
         * @param elementAmount The element amount to use.
         * @param luckRatio     The luck ratio to use.
         */
        public Builder builder(String id, Ingredient input, Item output, int count, CompoundTag tag, int elementAmount, int luckRatio) {
            return new Builder(this, new ResourceLocation(namespace, id), input, itemId(output), count, tag, elementAmount, luckRatio);
        }

        /**
         * @param id            The recipe id to use.
         * @param input         The input ingredient to use.
         * @param output        The output item to use.
         * @param count         The output count to use.
         * @param elementAmount The element amount to use.
         * @param luckRatio     The luck ratio to use.
         */
        public Builder builder(String id, Ingredient input, Item output, int count, int elementAmount, int luckRatio) {
            return new Builder(this, new ResourceLocation(namespace, id), input, output, count, new CompoundTag(), elementAmount, luckRatio);
        }

        /**
         * @param id            The recipe id to use.
         * @param input         The input ingredient to use.
         * @param output        The output item to use.
         * @param elementAmount The element amount to use.
         * @param luckRatio     The luck ratio to use.
         */
        public Builder builder(String id, Ingredient input, Item output, int elementAmount, int luckRatio) {
            return new Builder(this, new ResourceLocation(namespace, id), input, output, 1, elementAmount, luckRatio);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final Ingredient input;
            private final PotentiallyAbsentItemStack output;
            private final int elementAmount;
            private final int luckRatio;

            protected Builder(IO provider, ResourceLocation id, Ingredient input, ResourceLocation output, int count, CompoundTag tag, int elementAmount, int luckRatio) {
                super(id, provider);
                this.input = input;
                this.output = new PotentiallyAbsentItemStack(output, count, tag);
                this.elementAmount = elementAmount;
                this.luckRatio = luckRatio;
            }

            protected Builder(IO provider, ResourceLocation id, Ingredient input, ResourceLocation output, int count, int elementAmount, int luckRatio) {
                this(provider, id, input, output, count, new CompoundTag(), elementAmount, luckRatio);
            }

            protected Builder(IO provider, ResourceLocation id, Ingredient input, ResourceLocation output, int elementAmount, int luckRatio) {
                this(provider, id, input, output, 1, elementAmount, luckRatio);
            }

            protected Builder(IO provider, ResourceLocation id, Ingredient input, Item output, int count, CompoundTag tag, int elementAmount, int luckRatio) {
                this(provider, id, input, itemId(output), count, tag, elementAmount, luckRatio);
            }

            protected Builder(IO provider, ResourceLocation id, Ingredient input, Item output, int count, int elementAmount, int luckRatio) {
                this(provider, id, input, output, count, new CompoundTag(), elementAmount, luckRatio);
            }

            protected Builder(IO provider, ResourceLocation id, Ingredient input, Item output, int elementAmount, int luckRatio) {
                this(provider, id, input, output, 1, elementAmount, luckRatio);
            }

            @Override
            protected void toJson(JsonObject json, HolderLookup.Provider registries) {
                json.add("input", JsonUtil.toJson(input, registries));
                json.add("output", output.toJson(registries));
                json.addProperty("element_amount", elementAmount);
                json.addProperty("luck_ratio", luckRatio);
            }
        }
    }
}
