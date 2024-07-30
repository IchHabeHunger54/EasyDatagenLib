package com.github.minecraftschurlimods.easydatagenlib.api;

import com.google.gson.JsonObject;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

/**
 * The abstract parent class of builder classes used by {@link AbstractDataProvider} for generating data contents.
 */
public abstract class AbstractDataBuilder<T extends AbstractDataBuilder<T>> {
    private final AbstractDataProvider<T> provider;
    public final ResourceLocation id;

    /**
     * Creates a new builder with the given id.
     *
     * @param id The id to use. Should be unique within the same data provider and the same namespace.
     */
    public AbstractDataBuilder(ResourceLocation id, AbstractDataProvider<T> provider) {
        this.id = id;
        this.provider = provider;
    }

    /**
     * Adds this builder's contents to the given {@link JsonObject}.
     */
    protected abstract void toJson(JsonObject json, HolderLookup.Provider registries);

    /**
     * Shortcut to get a block's registry name.
     *
     * @param block The block to get the registry name for.
     * @return The registry name of the given block.
     */
    @SuppressWarnings("ConstantConditions")
    protected static ResourceLocation blockId(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    /**
     * Shortcut to get an item's registry name.
     *
     * @param item The item to get the registry name for.
     * @return The registry name of the given item.
     */
    @SuppressWarnings("ConstantConditions")
    protected static ResourceLocation itemId(Item item) {
        return BuiltInRegistries.ITEM.getKey(item);
    }

    /**
     * Shortcut to get a fluid's registry name.
     *
     * @param fluid The fluid to get the registry name for.
     * @return The registry name of the given fluid.
     */
    @SuppressWarnings("ConstantConditions")
    protected static ResourceLocation fluidId(Fluid fluid) {
        return BuiltInRegistries.FLUID.getKey(fluid);
    }

    public void build() {
        provider.add((T) this);
    }
}
