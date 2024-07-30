package com.github.minecraftschurlimods.easydatagenlib.mods.patchouli.regular;

import com.github.minecraftschurlimods.easydatagenlib.mods.patchouli.CategoryBuilder;
import com.github.minecraftschurlimods.easydatagenlib.mods.patchouli.Util;
import net.minecraft.world.item.ItemStack;

public class RegularCategoryBuilder extends CategoryBuilder<RegularBookBuilder, RegularCategoryBuilder, RegularEntryBuilder> {
    protected RegularCategoryBuilder(String id, String name, String description, String icon, RegularBookBuilder bookBuilder) {
        super(id, name, description, icon, bookBuilder);
    }

    @Override
    public RegularCategoryBuilder addSubCategory(String id, String name, String description, ItemStack icon) {
        return addSubCategory(id, name, description, Util.serializeStack(icon, getBookBuilder().getRegistries()));
    }

    @Override
    public RegularCategoryBuilder addSubCategory(String id, String name, String description, String icon) {
        return addSubCategory(new RegularCategoryBuilder(id, name, description, icon, bookBuilder));
    }

    @Override
    public RegularEntryBuilder addEntry(String id, String name, ItemStack icon) {
        return addEntry(id, name, Util.serializeStack(icon, getBookBuilder().getRegistries()));
    }

    @Override
    public RegularEntryBuilder addEntry(String id, String name, String icon) {
        return addEntry(new RegularEntryBuilder(id, name, icon, this));
    }
}
