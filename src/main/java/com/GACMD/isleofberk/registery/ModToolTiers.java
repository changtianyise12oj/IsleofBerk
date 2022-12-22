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
                new ForgeTier(2, 1561, 12F, 2.0F, 14,
                        ModTags.Blocks.NEEDS_GRONCKLE_IRON_TOOL, () -> Ingredient.of(ModItems.GRONCKLE_IRON.get())),
                new ResourceLocation(IsleofBerk.MOD_ID, "gronckle_iron"), List.of(Tiers.NETHERITE), List.of());

        BELZIUM = TierSortingRegistry.registerTier(
                new ForgeTier(3, 2031, 12f, 3f, 12,
                        ModTags.Blocks.NEEDS_BELZIUM_TOOL, () -> Ingredient.of(ModItems.GRONCKLE_IRON.get())),
                new ResourceLocation(IsleofBerk.MOD_ID, "belzium"), List.of(Tiers.NETHERITE), List.of());
    }
}