package com.github.minecraftschurlimods.easydatagenlib.mods.patchouli.page;

import com.github.minecraftschurlimods.easydatagenlib.mods.patchouli.AbstractPageBuilder;
import com.github.minecraftschurlimods.easydatagenlib.mods.patchouli.EntryBuilder;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;

public class EmptyPageBuilder extends AbstractPageBuilder<EmptyPageBuilder> {
    private final boolean drawFiller;

    public EmptyPageBuilder(boolean drawFiller, EntryBuilder<?,?,?> entryBuilder) {
        super(ResourceLocation.fromNamespaceAndPath("patchouli", "empty"), entryBuilder);
        this.drawFiller = drawFiller;
    }

    @Override
    protected void serialize(JsonObject json) {
        json.addProperty("draw_filler", drawFiller);
    }
}
