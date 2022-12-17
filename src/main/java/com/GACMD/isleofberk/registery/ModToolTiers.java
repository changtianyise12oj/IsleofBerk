package com.GACMD.isleofberk.registery;

import com.GACMD.isleofberk.IsleofBerk;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class ModToolTiers {
    public static Tier GRONCKLE_IRON;
    public static Tier BELZIUM;

    static {
        GRONCKLE_IRON = TierSortingRegistry.registerTier(
                new ForgeTier(5, 2031, 9f, 4f, 24,
                        ModTags.Blocks.NEEDS_GRONCKLE_IRON_TOOL, () -> Ingredient.of(ModItems.GRONCKLE_IRON.get())),
                new ResourceLocation(IsleofBerk.MOD_ID, "gronckle_iron"), List.of(Tiers.NETHERITE), List.of());

        BELZIUM = TierSortingRegistry.registerTier(
                new ForgeTier(6, 2751, 12f, 5f, 28,
                        ModTags.Blocks.NEEDS_BELZIUM_TOOL, () -> Ingredient.of(ModItems.GRONCKLE_IRON.get())),
                new ResourceLocation(IsleofBerk.MOD_ID, "belzium"), List.of(Tiers.NETHERITE), List.of());
    }
}