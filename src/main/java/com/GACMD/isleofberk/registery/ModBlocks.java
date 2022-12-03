package com.GACMD.isleofberk.registery;

import com.GACMD.isleofberk.IsleofBerk;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, IsleofBerk.MOD_ID);


    public static final RegistryObject<Block> BELZIUM_BLOCK = registerBlock("belzium_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(8f)), ModTab.IOB_TAB);

    public static final RegistryObject<Block> BELZIUM_ORE = registerBlock("belzium_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(8f)), ModTab.IOB_TAB);

    public static final RegistryObject<Block> RAW_GRONCKLE_IRON_BLOCK = registerBlock("raw_gronckle_iron_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(8f)), ModTab.IOB_TAB);

    public static final RegistryObject<Block> GRONCKLE_RION_BLOCK = registerBlock("gronckle_iron_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(8f)), ModTab.IOB_TAB);

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block,
                                                                            CreativeModeTab tab) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
