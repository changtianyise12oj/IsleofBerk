package com.GACMD.isleofberk.registery;

import com.GACMD.isleofberk.IsleofBerk;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;


public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> NEEDS_GRONCKLE_IRON_TOOL  = tag("needs_gronckle_iron_tool");
        public static final TagKey<Block> NEEDS_BELZIUM_TOOL        = tag("needs_belzium_tool");

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(IsleofBerk.MOD_ID, name));
        }
    }

    public static class Biomes
    {
        public static final TagKey<Biome> SPEED_STINGER_BIOMES          = tag("spawn_locations/speed_stinger_biomes");
        public static final TagKey<Biome> SWEET_STING_BIOMES            = tag("spawn_locations/sweet_sting_biomes");
        public static final TagKey<Biome> FLOUTSCOUT_BIOMES             = tag("spawn_locations/floutscout_biomes");
        public static final TagKey<Biome> ICE_BREAKER_BIOMES            = tag("spawn_locations/ice_breaker_biomes");

        // TODO do you need all these tags, now that spawning is config driven?
        // TODO some seem to be used but best I can tell there are no files for them?
        // TODO someone further investigate this...

        private static TagKey<Biome> tag(String name) {
            return TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(IsleofBerk.MOD_ID, name));
        }
    }

    public static class Items {
        // Taming Food
        public static final TagKey<Item> NADDER_TAME_FOOD           = tag("taming/nadder_tame_food");
        public static final TagKey<Item> GRONCKLE_TAME_FOOD         = tag("taming/gronckle_tame_food");
        public static final TagKey<Item> NIGHTMARE_TAME_FOOD        = tag("taming/nightmare_tame_food");
        public static final TagKey<Item> FURY_TAME_FOOD             = tag("taming/fury_tame_food");
        public static final TagKey<Item> SPEED_STINGER_TAME_FOOD    = tag("taming/speed_stinger_tame_food");
        public static final TagKey<Item> STINGER_TAME_FOOD          = tag("taming/stinger_tame_food");
        public static final TagKey<Item> TERRIBLE_TERROR_TAME_FOOD  = tag("taming/terrible_terror_tame_food");
        public static final TagKey<Item> TRIPLE_STRYKE_TAME_FOOD    = tag("taming/triple_stryke_tame_food");
        public static final TagKey<Item> ZIPPLEBACK_TAME_FOOD       = tag("taming/zippleback_tame_food");
        public static final TagKey<Item> SKRILL_TAME_FOOD           = tag("taming/skrill_tame_food");

        // Breeding Food
        public static final TagKey<Item> NADDER_BREED_FOOD          = tag("breeding/nadder_breed_food");
        public static final TagKey<Item> GRONCKLE_BREED_FOOD        = tag("breeding/gronckle_breed_food");
        public static final TagKey<Item> NIGHTMARE_BREED_FOOD       = tag("breeding/nightmare_breed_food");
        public static final TagKey<Item> FURY_BREED_FOOD            = tag("breeding/fury_breed_food");
        public static final TagKey<Item> SPEED_STINGER_BREED_FOOD   = tag("breeding/speed_stinger_breed_food");
        public static final TagKey<Item> STINGER_BREED_FOOD         = tag("breeding/stinger_breed_food");
        public static final TagKey<Item> TERRIBLE_TERROR_BREED_FOOD = tag("breeding/terrible_terror_breed_food");
        public static final TagKey<Item> TRIPLE_STRYKE_BREED_FOOD   = tag("breeding/triple_stryke_breed_food");
        public static final TagKey<Item> ZIPPLEBACK_BREED_FOOD      = tag("breeding/zippleback_breed_food");
        public static final TagKey<Item> SKRILL_BREED_FOOD          = tag("breeding/skrill_breed_food");

        // Gronckle's Iron
        public static final TagKey<Item> GRONCKLE_IRON_INGREDIENTS = tag("special/gronckle_iron_ingredients");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(IsleofBerk.MOD_ID, name));
        }
    }
}