package com.github.minecraftschurlimods.easydatagenlib.mods.patchouli;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.commands.arguments.item.ItemParser;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.tuple.Triple;

public class Util {
    public static String serializeStack(ItemStack stack, HolderLookup.Provider registries) {
        return new ItemInput(stack.getItemHolder(), stack.getComponentsPatch()).serialize(registries) + (stack.getCount() == 1 ? "" :("#" + stack.getCount()));
    }

    public static Triple<Holder<Item>, DataComponentPatch, Integer> deserializeStack(String string, HolderLookup.Provider registries) {
        StringReader reader = new StringReader(string.trim());
        ItemParser itemParser = new ItemParser(registries);
        try {
            ItemParser.ItemResult result = itemParser.parse(reader);
            int count = 1;
            if (reader.canRead()) {
                reader.expect('#');
                count = reader.readInt();
            }
            return Triple.of(result.item(), result.components(), count);
        } catch (CommandSyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
