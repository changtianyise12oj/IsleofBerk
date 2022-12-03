package com.GACMD.isleofberk.world.feature;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;

public class ModPlacedFeatures {

    public static final Holder<PlacedFeature> BELZIUM_ORE_PLACED = PlacementUtils.register("belzium_ore_placed",
            ModConfiguredFeatures.BELZIUM_ORE, ModOrePlacement.commonOrePlacement(500, // VeinsPerChunk
                    HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(25))));

}