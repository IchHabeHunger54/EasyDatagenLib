package com.github.minecraftschurlimods.easydatagenlib.util.mekanism;

import net.minecraft.resources.ResourceLocation;

public class InfuseType extends Chemical {
    //Mekanism Infuse Types
    public static final InfuseType BIO = new InfuseType(ResourceLocation.fromNamespaceAndPath("mekanism", "bio"));
    public static final InfuseType CARBON = new InfuseType(ResourceLocation.fromNamespaceAndPath("mekanism", "carbon"));
    public static final InfuseType DIAMOND = new InfuseType(ResourceLocation.fromNamespaceAndPath("mekanism", "diamond"));
    public static final InfuseType FUNGI = new InfuseType(ResourceLocation.fromNamespaceAndPath("mekanism", "fungi"));
    public static final InfuseType GOLD = new InfuseType(ResourceLocation.fromNamespaceAndPath("mekanism", "gold"));
    public static final InfuseType REDSTONE = new InfuseType(ResourceLocation.fromNamespaceAndPath("mekanism", "redstone"));
    public static final InfuseType REFINED_OBSIDIAN = new InfuseType(ResourceLocation.fromNamespaceAndPath("mekanism", "refined_obsidian"));
    public static final InfuseType TIN = new InfuseType(ResourceLocation.fromNamespaceAndPath("mekanism", "tin"));

    public InfuseType(ResourceLocation id) {
        super(id);
    }

    @Override
    public String getName() {
        return "infuse_type";
    }
}
