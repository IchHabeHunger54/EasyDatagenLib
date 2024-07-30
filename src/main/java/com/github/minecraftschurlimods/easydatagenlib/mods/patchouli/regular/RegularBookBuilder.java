package com.github.minecraftschurlimods.easydatagenlib.mods.patchouli.regular;

import com.github.minecraftschurlimods.easydatagenlib.mods.patchouli.BookBuilder;
import com.github.minecraftschurlimods.easydatagenlib.mods.patchouli.PatchouliBookProvider;
import com.github.minecraftschurlimods.easydatagenlib.mods.patchouli.Util;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class RegularBookBuilder extends BookBuilder<RegularBookBuilder, RegularCategoryBuilder, RegularEntryBuilder> {
    public RegularBookBuilder(ResourceLocation id, String name, String landingText, PatchouliBookProvider provider, HolderLookup.Provider registries) {
        super(id, name, landingText, provider, registries);
    }

    @Override
    public RegularCategoryBuilder addCategory(final String id, final String name, final String description, final ItemStack icon) {
        return this.addCategory(id, name, description, Util.serializeStack(icon, getRegistries()));
    }

    @Override
    public RegularCategoryBuilder addCategory(final String id, final String name, final String description, final String icon) {
        return this.addCategory(new RegularCategoryBuilder(id, name, description, icon, this));
    }
}
