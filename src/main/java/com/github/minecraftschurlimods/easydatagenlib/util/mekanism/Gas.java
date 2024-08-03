package com.github.minecraftschurlimods.easydatagenlib.util.mekanism;

import net.minecraft.resources.ResourceLocation;

public class Gas extends Chemical {
    // Mekanism Gases
    public static final Gas HYDROGEN = new Gas(ResourceLocation.fromNamespaceAndPath("mekanism", "hydrogen"));
    public static final Gas OXYGEN = new Gas(ResourceLocation.fromNamespaceAndPath("mekanism", "oxygen"));
    public static final Gas STEAM = new Gas(ResourceLocation.fromNamespaceAndPath("mekanism", "steam"));
    public static final Gas CHLORINE = new Gas(ResourceLocation.fromNamespaceAndPath("mekanism", "chlorine"));
    public static final Gas SULFUR_DIOXIDE = new Gas(ResourceLocation.fromNamespaceAndPath("mekanism", "sulfur_dioxide"));
    public static final Gas SULFUR_TRIOXIDE = new Gas(ResourceLocation.fromNamespaceAndPath("mekanism", "sulfur_trioxide"));
    public static final Gas SULFURIC_ACID = new Gas(ResourceLocation.fromNamespaceAndPath("mekanism", "sulfuric_acid"));
    public static final Gas HYDROGEN_CHLORIDE = new Gas(ResourceLocation.fromNamespaceAndPath("mekanism", "hydrogen_chloride"));
    public static final Gas HYDROFLUORIC_ACID = new Gas(ResourceLocation.fromNamespaceAndPath("mekanism", "hydrofluoric_acid"));
    public static final Gas URANIUM_OXIDE = new Gas(ResourceLocation.fromNamespaceAndPath("mekanism", "uranium_oxide"));
    public static final Gas URANIUM_HEXAFLUORIDE = new Gas(ResourceLocation.fromNamespaceAndPath("mekanism", "uranium_hexafluoride"));
    public static final Gas ETHENE = new Gas(ResourceLocation.fromNamespaceAndPath("mekanism", "ethene"));
    public static final Gas SODIUM = new Gas(ResourceLocation.fromNamespaceAndPath("mekanism", "sodium"));
    public static final Gas SUPERHEATED_SODIUM = new Gas(ResourceLocation.fromNamespaceAndPath("mekanism", "superheated_sodium"));
    public static final Gas BRINE = new Gas(ResourceLocation.fromNamespaceAndPath("mekanism", "brine"));
    public static final Gas LITHIUM = new Gas(ResourceLocation.fromNamespaceAndPath("mekanism", "lithium"));
    public static final Gas OSMIUM = new Gas(ResourceLocation.fromNamespaceAndPath("mekanism", "osmium"));
    public static final Gas FISSILE_FUEL = new Gas(ResourceLocation.fromNamespaceAndPath("mekanism", "fissile_fuel"));
    public static final Gas NUCLEAR_WASTE = new Gas(ResourceLocation.fromNamespaceAndPath("mekanism", "nuclear_waste"));
    public static final Gas SPENT_NUCLEAR_WASTE = new Gas(ResourceLocation.fromNamespaceAndPath("mekanism", "spent_nuclear_waste"));
    public static final Gas PLUTONIUM = new Gas(ResourceLocation.fromNamespaceAndPath("mekanism", "plutonium"));
    public static final Gas POLONIUM = new Gas(ResourceLocation.fromNamespaceAndPath("mekanism", "polonium"));
    public static final Gas ANTIMATTER = new Gas(ResourceLocation.fromNamespaceAndPath("mekanism", "antimatter"));

    public Gas(ResourceLocation id) {
        super(id);
    }

    @Override
    public String getName() {
        return "gas";
    }
}
