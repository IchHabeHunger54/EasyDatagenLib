package com.github.minecraftschurlimods.easydatagenlib.util.botanypots;

import com.github.minecraftschurlimods.easydatagenlib.util.JsonSerializable;
import com.github.minecraftschurlimods.easydatagenlib.util.JsonUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class DisplayState implements JsonSerializable {
    protected final ResourceLocation id;

    /**
     * @param id The id of the display state.
     */
    public DisplayState(ResourceLocation id) {
        this.id = id;
    }

    public static class Aging extends DisplayState {
        private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("botanypots", "aging");
        private final ResourceLocation block;

        /**
         * @param block The id of the {@link Block} associated with this display state.
         */
        public Aging(ResourceLocation block) {
            super(ID);
            this.block = block;
        }

        /**
         * @param block The {@link Block} associated with this display state.
         */
        public Aging(Block block) {
            this(Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(block)));
        }

        @Override
        public JsonElement toJson(HolderLookup.Provider registries) {
            JsonObject json = new JsonObject();
            json.addProperty("type", id.toString());
            json.addProperty("block", block.toString());
            return json;
        }
    }

    public static class Simple extends DisplayState {
        private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("botanypots", "simple");
        private final List<AxisAlignedRotation> rotation = new ArrayList<>();
        private final BlockState state;
        private Vec3 scale = null;
        private Vec3 offset = null;
        private boolean renderFluid = false;

        /**
         * @param state The {@link BlockState} associated with this display state.
         */
        public Simple(BlockState state) {
            super(ID);
            this.state = state;
        }

        /**
         * @param scale The scale to set.
         * @return This object, for chaining.
         */
        public Simple setScale(Vec3 scale) {
            this.scale = scale;
            return this;
        }

        /**
         * @param offset The offset to set.
         * @return This object, for chaining.
         */
        public Simple setOffset(Vec3 offset) {
            this.offset = offset;
            return this;
        }

        /**
         * Enables fluid rendering.
         * @return This object, for chaining.
         */
        public Simple renderFluid() {
            renderFluid = true;
            return this;
        }

        /**
         * @param rotation The {@link AxisAlignedRotation} to add.
         * @return This object, for chaining.
         */
        public Simple addRotation(AxisAlignedRotation rotation) {
            this.rotation.add(rotation);
            return this;
        }

        @Override
        public JsonElement toJson(HolderLookup.Provider registries) {
            JsonObject json = JsonUtil.toJson(state);
            if (scale != null) {
                json.add("scale", JsonUtil.toJson(scale));
            }
            if (offset != null) {
                json.add("offset", JsonUtil.toJson(offset));
            }
            if (!rotation.isEmpty()) {
                json.add("rotation", JsonUtil.toEnumList(rotation));
            }
            if (renderFluid) {
                json.addProperty("renderFluid", true);
            }
            return json;
        }
    }

    public static class Transitional extends DisplayState {
        private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("botanypots", "transitional");
        private final List<DisplayState> phases = new ArrayList<>();

        public Transitional() {
            super(ID);
        }

        /**
         * @param phase The {@link DisplayState} to add.
         * @return This object, for chaining.
         */
        public Transitional addPhase(DisplayState phase) {
            phases.add(phase);
            return this;
        }

        @Override
        public JsonElement toJson(HolderLookup.Provider registries) {
            JsonObject json = new JsonObject();
            json.addProperty("type", id.toString());
            json.add("phases", JsonUtil.toList(phases, registries));
            return json;
        }
    }
}
