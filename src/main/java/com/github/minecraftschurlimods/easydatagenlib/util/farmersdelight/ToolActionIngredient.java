package com.github.minecraftschurlimods.easydatagenlib.util.farmersdelight;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.ToolAction;
import net.neoforged.neoforge.common.crafting.ICustomIngredient;
import net.neoforged.neoforge.common.crafting.IngredientType;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public final class ToolActionIngredient implements ICustomIngredient {
    private final ToolAction toolAction;

    public ToolActionIngredient(ToolAction toolAction) {
        this.toolAction = toolAction;
    }

    public static Ingredient of(ToolAction toolAction) {
        return new Ingredient(new ToolActionIngredient(toolAction));
    }

    @Override
    public boolean test(@Nullable ItemStack stack) {
        return stack != null && stack.canPerformAction(toolAction);
    }

    @Override
    public Stream<ItemStack> getItems() {
        return Stream.empty();
    }

    @Override
    public boolean isSimple() {
        return false;
    }

    @Override
    public IngredientType<?> getType() {
        return null;
    }

    public JsonElement toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("type", "farmersdelight:tool_action");
        json.addProperty("action", toolAction.name());
        return json;
    }
}
