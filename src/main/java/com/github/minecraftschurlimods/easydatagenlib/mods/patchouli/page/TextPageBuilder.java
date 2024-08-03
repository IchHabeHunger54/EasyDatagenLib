package com.github.minecraftschurlimods.easydatagenlib.mods.patchouli.page;

import com.github.minecraftschurlimods.easydatagenlib.mods.patchouli.AbstractPageBuilder;
import com.github.minecraftschurlimods.easydatagenlib.mods.patchouli.EntryBuilder;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;

public class TextPageBuilder extends AbstractPageBuilder<TextPageBuilder> {
    private final String text;
    private final String title;

    public TextPageBuilder(String text, String title, EntryBuilder<?,?,?> entryBuilder) {
        super(ResourceLocation.fromNamespaceAndPath("patchouli", "text"), entryBuilder);
        this.text = text;
        this.title = title;
    }

    public TextPageBuilder(String text, EntryBuilder<?,?,?> entryBuilder) {
        super(ResourceLocation.fromNamespaceAndPath("patchouli", "text"), entryBuilder);
        this.text = text;
        this.title = null;
    }

    @Override
    protected void serialize(JsonObject json) {
        json.addProperty("text", text);
        if (title != null) {
            json.addProperty("title", title);
        }
    }
}
