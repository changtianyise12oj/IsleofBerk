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
        public static final TagKey<Block> NEEDS_GRONCKLE_IRON_TOOL
                = tag("needs_gronckle_iron_tool");

        public static final TagKey<Block> NEEDS_BELZIUM_TOOL
                = tag("needs_belzium_tool");


        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(IsleofBerk.MOD_ID, name));
        }

        private static TagKey<Block> forgeTag(String name) {
            return BlockTags.create(new ResourceLocation("forge", name));
        }
    }

    public static class Biomes
    {
        public static final TagKey<Biome> DEADLY_NADDER_BIOMES         = tag("deadly_nadder_biomes");
        public static final TagKey<Biome> GRONCKLE_BIOMES        = tag("gronckle_biomes");
        public static final TagKey<Biome> HIDEOUS_ZIPPLEBACK_BIOMES       = tag("hideous_zippleback_biomes");
        public static final TagKey<Biome> MONSTROUS_NIGHTMARE_BIOMES    = tag("monstrous_nightmare_biomes");
        public static final TagKey<Biome> TERRIBLE_TERROR_BIOMES        = tag("terrible_terror_biomes");
        public static final TagKey<Biome> SPEED_STINGER_BIOMES        = tag("speed_stinger_biomes");
        public static final TagKey<Biome> SWEET_STING_BIOMES          = tag("sweet_sting_biomes");
        public static final TagKey<Biome> FLOUTSCOUT_BIOMES          = tag("floutscout_biomes");
        public static final TagKey<Biome> ICE_BREAKER_BIOMES          = tag("ice_breaker_biomes");
        public static final TagKey<Biome> NIGHTFURY_BIOMES    = tag("nightfury_biomes");
        public static final TagKey<Biome> LIGHTFURY_BIOMES          = tag("lightfury_biomes");
        public static final TagKey<Biome> TRIPLE_STRYKE_BIOMES          = tag("triple_stryke_biomes");
        public static final TagKey<Biome> STINGER_BIOMES        = tag("stinger_biomes");
        public static final TagKey<Biome> SKRILL_BIOMES          = tag("skrill_biomes");

        private static TagKey<Biome> tag(String name)
        {
            return TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(IsleofBerk.MOD_ID, name));
        }
    }

    public static class Items {

        // taming food list
        public static final TagKey<Item> NADDER_TAME_FOOD           = tag("nadder_tame_food");
        public static final TagKey<Item> GRONCKLE_TAME_FOOD         = tag("gronckle_tame_food");
        public static final TagKey<Item> NIGHTMARE_TAME_FOOD        = tag("nightmare_tame_food");
        public static final TagKey<Item> FURY_TAME_FOOD             = tag("fury_tame_food");
        public static final TagKey<Item> SPEED_STINGER_TAME_FOOD    = tag("speed_stinger_tame_food");
        public static final TagKey<Item> STINGER_TAME_FOOD          = tag("stinger_tame_food");
        public static final TagKey<Item> TERRIBLE_TERROR_TAME_FOOD  = tag("terrible_terror_tame_food");
        public static final TagKey<Item> TRIPLE_STRYKE_TAME_FOOD    = tag("triple_stryke_tame_food");
        public static final TagKey<Item> ZIPPLEBACK_TAME_FOOD       = tag("zippleback_tame_food");
        public static final TagKey<Item> SKRILL_TAME_FOOD           = tag("skrill_tame_food");

        // breeding food list
        public static final TagKey<Item> NADDER_BREED_FOOD = tag("nadder_breed_food");
        public static final TagKey<Item> GRONCKLE_BREED_FOOD = tag("gronckle_breed_food");
        public static final TagKey<Item> NIGHTMARE_BREED_FOOD = tag("nightmare_breed_food");
        public static final TagKey<Item> FURY_BREED_FOOD = tag("fury_breed_food");
        public static final TagKey<Item> SPEED_STINGER_BREED_FOOD = tag("speed_stinger_breed_food");
        public static final TagKey<Item> STINGER_BREED_FOOD = tag("stinger_breed_food");
        public static final TagKey<Item> TERRIBLE_TERROR_BREED_FOOD = tag("terrible_terror_breed_food");
        public static final TagKey<Item> TRIPLE_STRYKE_BREED_FOOD = tag("triple_stryke_breed_food");
        public static final TagKey<Item> ZIPPLEBACK_BREED_FOOD = tag("zippleback_breed_food");
        public static final TagKey<Item> SKRILL_BREED_FOOD = tag("skrill_breed_food");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(IsleofBerk.MOD_ID, name));
        }
    }
}