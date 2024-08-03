package com.github.minecraftschurlimods.easydatagenlib.mods.patchouli;

import com.github.minecraftschurlimods.easydatagenlib.mods.patchouli.regular.RegularBookBuilder;
import com.github.minecraftschurlimods.easydatagenlib.mods.patchouli.translated.TranslatedBookBuilder;
import com.google.gson.JsonObject;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public abstract class PatchouliBookProvider implements DataProvider {
    private final PackOutput.PathProvider bookJsonPathProvider;
    private final PackOutput.PathProvider bookContentPathProvider;
    private final CompletableFuture<HolderLookup.Provider> lookupProvider;
    private final String namespace;
    private final boolean includeClient;
    private final boolean includeServer;

    public PatchouliBookProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String namespace, boolean includeClient, boolean includeServer) {
        this.bookJsonPathProvider = output.createPathProvider(PackOutput.Target.DATA_PACK, "patchouli_books");
        this.bookContentPathProvider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "patchouli_books");
        this.lookupProvider = lookupProvider;
        this.namespace = namespace;
        this.includeClient = includeClient;
        this.includeServer = includeServer;
    }

    /**
     * Performs this provider's action.
     *
     * @param cache the cache
     * @return
     */
    @NotNull
    @Override
    public CompletableFuture<?> run(@NotNull CachedOutput cache) {
        return lookupProvider.thenCompose(provider -> {
            List<CompletableFuture<?>> futures = new ArrayList<>();
            addBooks(provider, book -> {
                if (includeServer) {
                    futures.add(saveBook(cache, book.toJson(), book.getId()));
                }
                if (includeClient) {
                    for (CategoryBuilder<?,?,?> category : book.getCategories()) {
                        futures.add(saveCategory(cache, category.toJson(), book.getId(), category.getId(), category.getLocale()));
                        for (var entry : category.getEntries()) {
                            futures.add(saveEntry(cache, entry.toJson(), book.getId(), entry.getId(), entry.getLocale()));
                        }
                    }
                }
            });
            return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        });
    }

    protected abstract void addBooks(HolderLookup.Provider lookupProvider, Consumer<BookBuilder<?,?,?>> consumer);

    private CompletableFuture<?> saveEntry(CachedOutput cache, JsonObject json, ResourceLocation bookId, ResourceLocation id, String locale) {
        Path outputPath = this.bookContentPathProvider.json(bookId.withSuffix("/" + locale + "/entries/" + id.getPath()));
        return DataProvider.saveStable(cache, json, outputPath);
    }

    private CompletableFuture<?> saveCategory(CachedOutput cache, JsonObject json, ResourceLocation bookId, ResourceLocation id, String locale) {
        Path outputPath = this.bookContentPathProvider.json(bookId.withSuffix("/" + locale + "/categories/" + id.getPath()));
        return DataProvider.saveStable(cache, json, outputPath);
    }

    private CompletableFuture<?> saveBook(CachedOutput cache, JsonObject json, ResourceLocation bookId) {
        Path outputPath = this.bookJsonPathProvider.json(bookId.withSuffix("/book"));
        return DataProvider.saveStable(cache, json, outputPath);
    }

    public RegularBookBuilder createBookBuilder(String id, String name, String landingText, HolderLookup.Provider registries) {
        return new RegularBookBuilder(ResourceLocation.fromNamespaceAndPath(this.namespace, id), name, landingText, this, registries);
    }

    public TranslatedBookBuilder createBookBuilder(String id, String name, String landingText, BiConsumer<String, String> langProvider, HolderLookup.Provider registries) {
        return new TranslatedBookBuilder(ResourceLocation.fromNamespaceAndPath(this.namespace, id), name, landingText, langProvider, this, registries);
    }

    /**
     * Gets a name for this provider, to use in logging.
     */
    @NotNull
    @Override
    public String getName() {
        return "Patchouli Book Provider";
    }
}
