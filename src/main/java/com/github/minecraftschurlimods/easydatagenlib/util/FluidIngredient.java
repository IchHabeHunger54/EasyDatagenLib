package com.github.minecraftschurlimods.easydatagenlib.util;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * {@see https://github.com/Creators-of-Create/Create/blob/mc1.18/dev/src/main/java/com/simibubi/create/foundation/fluid/FluidIngredient.java}
 */
public abstract class FluidIngredient implements Predicate<FluidStack>, JsonSerializable {
    public static final FluidIngredient EMPTY = FluidIngredient.of(new FluidStack(Fluids.EMPTY, 0));
    private List<FluidStack> matchingFluidStacks;
    protected int amount;

    protected abstract void read(JsonObject json, HolderLookup.Provider registries);

    protected abstract void write(JsonObject json, HolderLookup.Provider registries);

    protected abstract List<FluidStack> getMatches();

    public int getAmount() {
        return amount;
    }

    public List<FluidStack> getMatchingFluidStacks() {
        return matchingFluidStacks != null ? matchingFluidStacks : (matchingFluidStacks = getMatches());
    }

    public static FluidIngredient of(TagKey<Fluid> tag, int amount) {
        FluidTagIngredient ingredient = new FluidTagIngredient();
        ingredient.tag = tag;
        ingredient.amount = amount;
        return ingredient;
    }

    public static FluidIngredient of(Fluid fluid, int amount) {
        FluidStackIngredient ingredient = new FluidStackIngredient();
        ingredient.fluid = new FluidStack(fluid, amount);
        return ingredient;
    }

    public static FluidIngredient of(FluidStack fluidStack) {
        FluidStackIngredient ingredient = new FluidStackIngredient();
        ingredient.fluid = fluidStack.copy();
        ingredient.components = DataComponentPredicate.allOf(fluidStack.getComponents());
        return ingredient;
    }

    public static boolean isFluidIngredient(@Nullable JsonElement je) {
        if (je == null || je.isJsonNull() || !je.isJsonObject()) return false;
        JsonObject json = je.getAsJsonObject();
        return json.has("fluidTag") || json.has("fluid");
    }

    public static FluidIngredient fromJson(@Nullable JsonElement je, HolderLookup.Provider registries) {
        if (!isFluidIngredient(je)) throw new JsonSyntaxException("Invalid fluid ingredient: " + je);
        JsonObject json = je.getAsJsonObject();
        FluidIngredient ingredient = json.has("fluidTag") ? new FluidTagIngredient() : new FluidStackIngredient();
        ingredient.read(json, registries);
        if (!json.has("amount")) throw new JsonSyntaxException("Fluid ingredient has to define an amount");
        ingredient.amount = GsonHelper.getAsInt(json, "amount");
        return ingredient;
    }

    @Override
    public JsonObject toJson(HolderLookup.Provider registries) {
        JsonObject json = new JsonObject();
        write(json, registries);
        json.addProperty("amount", amount);
        return json;
    }

    public static class FluidStackIngredient extends FluidIngredient {
        protected FluidStack fluid;
        protected DataComponentPredicate components;

        @Override
        public boolean test(FluidStack t) {
            return t.getFluid().isSame(this.fluid.getFluid()) && t.getAmount() >= this.fluid.getAmount() && this.components.test(t);
        }

        @Override
        protected void read(JsonObject json, HolderLookup.Provider registries) {
            ResourceLocation id = new ResourceLocation(GsonHelper.getAsString(json, "fluid"));
            Fluid fluid = BuiltInRegistries.FLUID.get(id);
            if (fluid == Fluids.EMPTY) throw new JsonSyntaxException("Unknown fluid '" + id + "'");
            this.fluid = new FluidStack(fluid, GsonHelper.getAsInt(json, "amount"));
            if (json.has("components")) {
                this.components = DataComponentPredicate.CODEC.decode(registries.createSerializationContext(JsonOps.INSTANCE), json.get("components")).result().orElseThrow().getFirst();
            }
        }

        @Override
        protected void write(JsonObject json, HolderLookup.Provider registries) {
            json.addProperty("fluid", Objects.requireNonNull(BuiltInRegistries.FLUID.getKey(this.fluid.getFluid())).toString());
            json.add("components", DataComponentPredicate.CODEC.encodeStart(registries.createSerializationContext(JsonOps.INSTANCE), this.components).result().orElseThrow());
        }

        @Override
        protected List<FluidStack> getMatches() {
            return ImmutableList.of(new FluidStack(fluid.getFluid().builtInRegistryHolder(), amount, components.asPatch()));
        }
    }

    public static class FluidTagIngredient extends FluidIngredient {
        protected TagKey<Fluid> tag;

        @SuppressWarnings("deprecation")
        @Override
        public boolean test(FluidStack t) {
            if (tag == null) {
                for (FluidStack accepted : getMatchingFluidStacks()) {
                    if (accepted.getFluid().isSame(t.getFluid())) return true;
                }
                return false;
            }
            return t.getFluid().is(tag);
        }

        @Override
        protected void read(JsonObject json, HolderLookup.Provider registries) {
            tag = FluidTags.create(new ResourceLocation(GsonHelper.getAsString(json, "fluidTag")));
        }

        @Override
        protected void write(JsonObject json, HolderLookup.Provider registries) {
            json.addProperty("fluidTag", tag.location().toString());
        }

        @Override
        protected List<FluidStack> getMatches() {
            return BuiltInRegistries.FLUID.getTag(tag)
                    .stream()
                    .flatMap(HolderSet::stream)
                    .map(Holder::value)
                    .map(e -> e instanceof FlowingFluid ? ((FlowingFluid) e).getSource() : e)
                    .distinct()
                    .map(e -> new FluidStack(e, amount))
                    .toList();
        }
    }
}
