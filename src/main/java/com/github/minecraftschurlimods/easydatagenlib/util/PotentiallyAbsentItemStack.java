package com.github.minecraftschurlimods.easydatagenlib.util;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

/**
 * This class contains the necessary information for item stacks that will be written to disk by a data generator.
 * It contains an item id in {@link ResourceLocation} form, a count and an optional {@link CompoundTag}.
 * When serializing to JSON, no {@link ItemStack} will be used. Instead, the correct JSON syntax is recreated by hand.
 * This means that you can use item ids that may not be valid, e.g. from other mods, in your datagen.
 */
public class PotentiallyAbsentItemStack implements JsonSerializable {
    public final ResourceLocation item;
    public final int count;
    public DataComponentPatch patch;

    /**
     * Creates a new instance of this class. Use this if you want the output to have additional NBT data.
     *
     * @param item  The item id to use.
     * @param count The count to use.
     * @param patch The components to use.
     */
    public PotentiallyAbsentItemStack(ResourceLocation item, int count, DataComponentPatch patch) {
        this.item = item;
        this.count = count;
        this.patch = patch;
    }

    /**
     * Creates a new instance of this class. Use this if you want the output to have additional NBT data.
     *
     * @param item  The item id to use.
     * @param patch The components to use.
     */
    public PotentiallyAbsentItemStack(ResourceLocation item, DataComponentPatch patch) {
        this(item, 1, patch);
    }

    /**
     * Creates a new instance of this class.
     *
     * @param item  The item id to use.
     * @param count The count to use.
     */
    public PotentiallyAbsentItemStack(ResourceLocation item, int count) {
        this(item, count, DataComponentPatch.EMPTY);
    }

    /**
     * Creates a new instance of this class.
     *
     * @param item The item id to use.
     */
    public PotentiallyAbsentItemStack(ResourceLocation item) {
        this(item, 1, DataComponentPatch.EMPTY);
    }

    /**
     * @return The JSON representation of this object.
     */
    @Override
    public JsonObject toJson(HolderLookup.Provider registries) {
        JsonObject json = new JsonObject();
        if (item == null) throw new IllegalArgumentException("Cannot serialize an item stack without an item id!");
        json.addProperty("item", item.toString());
        if (count > 1) {
            json.addProperty("count", count);
        }
        if (!patch.isEmpty()) {
            json.add("components", DataComponentPatch.CODEC.encodeStart(registries.createSerializationContext(JsonOps.INSTANCE), patch).getOrThrow());
        }
        return json;
    }

    /**
     * Variant of {@link PotentiallyAbsentItemStack} that adds extra chance info.
     */
    public static class WithChance extends PotentiallyAbsentItemStack {
        public final float chance;

        /**
         * Creates a new instance of this class. Use this if you want the output to have additional NBT data.
         *
         * @param item  The item id to use.
         * @param count The count to use.
         * @param patch The components to use.
         */
        public WithChance(ResourceLocation item, int count, DataComponentPatch patch, float chance) {
            super(item, count, patch);
            this.chance = chance;
        }

        /**
         * Creates a new instance of this class. Use this if you want the output to have additional NBT data.
         *
         * @param item  The item id to use.
         * @param patch The components to use.
         */
        public WithChance(ResourceLocation item, DataComponentPatch patch, float chance) {
            this(item, 1, patch, chance);
        }

        /**
         * Creates a new instance of this class.
         *
         * @param item  The item id to use.
         * @param count The count to use.
         */
        public WithChance(ResourceLocation item, int count, float chance) {
            this(item, count, DataComponentPatch.EMPTY, chance);
        }

        /**
         * Creates a new instance of this class.
         *
         * @param item The item id to use.
         */
        public WithChance(ResourceLocation item, float chance) {
            this(item, 1, DataComponentPatch.EMPTY, chance);
        }

        @Override
        public JsonObject toJson(HolderLookup.Provider registries) {
            JsonObject json = super.toJson(registries);
            if (chance > 0 && chance != 1) {
                json.addProperty("chance", chance);
            }
            return json;
        }
    }
}
