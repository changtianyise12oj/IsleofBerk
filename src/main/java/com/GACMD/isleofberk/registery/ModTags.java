package com.GACMD.isleofberk.registery;

import com.GACMD.isleofberk.IsleofBerk;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
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
}