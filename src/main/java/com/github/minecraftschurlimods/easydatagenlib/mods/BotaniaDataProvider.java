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
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

public abstract class BotaniaDataProvider<T extends AbstractRecipeBuilder<?>> extends AbstractRecipeProvider<T> {
    protected BotaniaDataProvider(String folder, String namespace, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(new ResourceLocation("botania", folder), namespace, output, registries);
    }
    //TODO Brew, Elven Trade, Orechid, Orechid Ignem, Petal Apothecary, Pure Daisy, Runic Altar, Terra Plate

    public static class Infusing extends BotaniaDataProvider<Infusing.Builder> {
        public Infusing(String namespace, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super("mana_infusion", namespace, output, registries);
        }

        /**
         * @param id     The recipe id to use.
         * @param mana   The amount of mana to use.
         * @param input  The input ingredient to use.
         * @param output The id of the output item to use.
         * @param count  The output count to use.
         * @param tag    The output tag to use.
         */
        public Builder builder(String id, int mana, Ingredient input, ResourceLocation output, int count, CompoundTag tag) {
            return new Builder(this, new ResourceLocation(namespace, id), mana, input, output, count, tag);
        }

        /**
         * @param id     The recipe id to use.
         * @param mana   The amount of mana to use.
         * @param input  The input ingredient to use.
         * @param output The id of the output item to use.
         * @param count  The output count to use.
         */
        public Builder builder(String id, int mana, Ingredient input, ResourceLocation output, int count) {
            return new Builder(this, new ResourceLocation(namespace, id), mana, input, output, count);
        }

        /**
         * @param id     The recipe id to use.
         * @param mana   The amount of mana to use.
         * @param input  The input ingredient to use.
         * @param output The id of the output item to use.
         */
        public Builder builder(String id, int mana, Ingredient input, ResourceLocation output) {
            return new Builder(this, new ResourceLocation(namespace, id), mana, input, output);
        }

        /**
         * @param id     The recipe id to use.
         * @param mana   The amount of mana to use.
         * @param input  The input ingredient to use.
         * @param output The output item to use.
         * @param count  The output count to use.
         * @param tag    The output tag to use.
         */
        public Builder builder(String id, int mana, Ingredient input, Item output, int count, CompoundTag tag) {
            return new Builder(this, new ResourceLocation(namespace, id), mana, input, output, count, tag);
        }

        /**
         * @param id     The recipe id to use.
         * @param mana   The amount of mana to use.
         * @param input  The input ingredient to use.
         * @param output The output item to use.
         * @param count  The output count to use.
         */
        public Builder builder(String id, int mana, Ingredient input, Item output, int count) {
            return new Builder(this, new ResourceLocation(namespace, id), mana, input, output, count);
        }

        /**
         * @param id     The recipe id to use.
         * @param mana   The amount of mana to use.
         * @param input  The input ingredient to use.
         * @param output The output item to use.
         */
        public Builder builder(String id, int mana, Ingredient input, Item output) {
            return new Builder(this, new ResourceLocation(namespace, id), mana, input, output);
        }

        public static class Builder extends AbstractRecipeBuilder<Builder> {
            private final int mana;
            private final Ingredient input;
            private final PotentiallyAbsentItemStack output;
            private String group;
            private ResourceLocation catalyst;

            protected Builder(Infusing provider, ResourceLocation id, int mana, Ingredient input, ResourceLocation output, int count, CompoundTag tag) {
                super(id, provider);
                this.mana = mana;
                this.input = input;
                this.output = new PotentiallyAbsentItemStack(output, count, tag);
            }

            protected Builder(Infusing provider, ResourceLocation id, int mana, Ingredient input, ResourceLocation output, int count) {
                this(provider, id, mana, input, output, count, new CompoundTag());
            }

            protected Builder(Infusing provider, ResourceLocation id, int mana, Ingredient input, ResourceLocation output) {
                this(provider, id, mana, input, output, 1);
            }

            protected Builder(Infusing provider, ResourceLocation id, int mana, Ingredient input, Item output, int count, CompoundTag tag) {
                this(provider, id, mana, input, itemId(output), count, tag);
            }

            protected Builder(Infusing provider, ResourceLocation id, int mana, Ingredient input, Item output, int count) {
                this(provider, id, mana, input, output, count, new CompoundTag());
            }

            protected Builder(Infusing provider, ResourceLocation id, int mana, Ingredient input, Item output) {
                this(provider, id, mana, input, output, 1);
            }

            /**
             * Sets the group of this recipe.
             *
             * @param group The group to use.
             */
            public Builder setGroup(String group) {
                this.group = group;
                return this;
            }

            /**
             * Sets the catalyst of this recipe.
             *
             * @param catalyst The catalyst to use.
             */
            public Builder setCatalyst(ResourceLocation catalyst) {
                this.catalyst = catalyst;
                return this;
            }

            /**
             * Sets the catalyst of this recipe.
             *
             * @param catalyst The catalyst to use.
             */
            public Builder setCatalyst(Block catalyst) {
                return setCatalyst(blockId(catalyst));
            }

            @Override
            protected void toJson(JsonObject json, HolderLookup.Provider registries) {
                json.addProperty("mana", mana);
                json.add("input", JsonUtil.toJson(input, registries));
                json.add("output", output.toJson(registries));
                if (group != null) {
                    json.addProperty("group", group);
                }
                if (catalyst != null) {
                    JsonObject c = new JsonObject();
                    c.addProperty("type", "block");
                    c.addProperty("block", catalyst.toString());
                    json.add("catalyst", c);
                }
            }
        }
    }
}
