package com.github.minecraftschurlimods.easydatagenlib.util;

import com.google.gson.JsonObject;
import net.minecraft.core.HolderLookup;
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
    public CompoundTag tag;

    /**
     * Creates a new instance of this class. Use this if you want the output to have additional NBT data.
     *
     * @param item  The item id to use.
     * @param count The count to use.
     * @param tag   The NBT to use. May be null.
     */
    public PotentiallyAbsentItemStack(ResourceLocation item, int count, CompoundTag tag) {
        this.item = item;
        this.count = count;
        this.tag = tag;
    }

    /**
     * Creates a new instance of this class. Use this if you want the output to have additional NBT data.
     *
     * @param item  The item id to use.
     * @param tag   The NBT to use. May be null.
     */
    public PotentiallyAbsentItemStack(ResourceLocation item, CompoundTag tag) {
        this(item, 1, tag);
    }

    /**
     * Creates a new instance of this class.
     *
     * @param item  The item id to use.
     * @param count The count to use.
     */
    public PotentiallyAbsentItemStack(ResourceLocation item, int count) {
        this(item, count, new CompoundTag());
    }

    /**
     * Creates a new instance of this class.
     *
     * @param item The item id to use.
     */
    public PotentiallyAbsentItemStack(ResourceLocation item) {
        this(item, 1, new CompoundTag());
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
        if (!tag.isEmpty()) {
            json.addProperty("nbt", tag.toString());
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
         * @param tag   The NBT to use. May be null.
         */
        public WithChance(ResourceLocation item, int count, CompoundTag tag, float chance) {
            super(item, count, tag);
            this.chance = chance;
        }

        /**
         * Creates a new instance of this class. Use this if you want the output to have additional NBT data.
         *
         * @param item  The item id to use.
         * @param tag   The NBT to use. May be null.
         */
        public WithChance(ResourceLocation item, CompoundTag tag, float chance) {
            this(item, 1, tag, chance);
        }

        /**
         * Creates a new instance of this class.
         *
         * @param item  The item id to use.
         * @param count The count to use.
         */
        public WithChance(ResourceLocation item, int count, float chance) {
            this(item, count, new CompoundTag(), chance);
        }

        /**
         * Creates a new instance of this class.
         *
         * @param item The item id to use.
         */
        public WithChance(ResourceLocation item, float chance) {
            this(item, 1, new CompoundTag(), chance);
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
