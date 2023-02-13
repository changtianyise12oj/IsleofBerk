package com.GACMD.isleofberk.registery;

import com.GACMD.isleofberk.IsleofBerk;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
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
    public static class Items {

        // taming food list
        public static final TagKey<Item> NADDER_TAME_FOOD = tag("nadder_tame_food");
        public static final TagKey<Item> GRONCKLE_TAME_FOOD = tag("gronckle_tame_food");
        public static final TagKey<Item> NIGHTMARE_TAME_FOOD = tag("nightmare_tame_food");
        public static final TagKey<Item> FURY_TAME_FOOD = tag("fury_tame_food");
        public static final TagKey<Item> SPEED_STINGER_TAME_FOOD = tag("speed_stinger_tame_food");
        public static final TagKey<Item> STINGER_TAME_FOOD = tag("stinger_tame_food");
        public static final TagKey<Item> TERRIBLE_TERROR_TAME_FOOD = tag("terrible_terror_tame_food");
        public static final TagKey<Item> TRIPLE_STRYKE_TAME_FOOD = tag("triple_stryke_tame_food");
        public static final TagKey<Item> ZIPPLEBACK_TAME_FOOD = tag("zippleback_tame_food");

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

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(IsleofBerk.MOD_ID, name));
        }
    }
}