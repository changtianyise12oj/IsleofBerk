package com.GACMD.isleofberk.registery;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.items.DragonEggItem;
import com.GACMD.isleofberk.items.DragonSpawnEggItem;
import com.GACMD.isleofberk.items.DragonEggSeparateVariantItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems {

    private ModItems() {
    }

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, IsleofBerk.MOD_ID);

    /**
     * METALS & GEMS
     */

    public static final RegistryObject<Item> BELZIUM = ITEMS.register("belzium", () ->
            new Item(new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(64)));

    public static final RegistryObject<Item> RAW_GRONCKLE_IRON = ITEMS.register("raw_gronckle_iron", () ->
            new Item(new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(64)));

    public static final RegistryObject<Item> GRONCKLE_IRON = ITEMS.register("gronckle_iron", () ->
            new Item(new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(64)));

    /**
     * Tools
     */

    public static final RegistryObject<Item> GRONCKLE_IRON_PICKAXE = ITEMS.register("gronckle_iron_pickaxe",
            () -> new PickaxeItem(ModToolTiers.GRONCKLE_IRON, 2, -2.8F,
                    new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(1)));

    public static final RegistryObject<Item> GRONCKLE_IRON_AXE = ITEMS.register("gronckle_iron_axe",
            () -> new AxeItem(ModToolTiers.GRONCKLE_IRON, 7, -3.1F,
                    new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(1)));

    public static final RegistryObject<Item> GRONCKLE_IRON_DOUBLEAXE = ITEMS.register("gronckle_iron_doubleaxe",
            () -> new AxeItem(ModToolTiers.GRONCKLE_IRON, 8, -3.2F,
                    new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(1)));

    public static final RegistryObject<Item> GRONCKLE_IRON_SWORD = ITEMS.register("gronckle_iron_sword",
            () -> new SwordItem(ModToolTiers.GRONCKLE_IRON, 5, -2.4F,
                    new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(1)));

    public static final RegistryObject<Item> GRONCKLE_IRON_DAGGER = ITEMS.register("gronckle_iron_dagger",
            () -> new SwordItem(ModToolTiers.GRONCKLE_IRON, 1, -1.2F,
                    new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(1)));

    public static final RegistryObject<Item> GRONCKLE_IRON_MACE = ITEMS.register("gronckle_iron_mace",
            () -> new SwordItem(ModToolTiers.GRONCKLE_IRON, 5, -2.4F,
                    new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(1)));

    public static final RegistryObject<Item> GRONCKLE_IRON_HAMMER = ITEMS.register("gronckle_iron_hammer",
            () -> new PickaxeItem(ModToolTiers.GRONCKLE_IRON, 8, -3.2F,
                    new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(1)));

    public static final RegistryObject<Item> BELZIUM_PICKAXE = ITEMS.register("belzium_pickaxe",
            () -> new PickaxeItem(ModToolTiers.BELZIUM, 3, -2.8F,
                    new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(1)));

    public static final RegistryObject<Item> BELZIUM_AXE = ITEMS.register("belzium_axe",
            () -> new AxeItem(ModToolTiers.BELZIUM, 8, -3.1F,
                    new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(1)));

    public static final RegistryObject<Item> BELZIUM_DOUBLEAXE = ITEMS.register("belzium_doubleaxe",
            () -> new AxeItem(ModToolTiers.BELZIUM, 9, -3.2F,
                    new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(1)));

    public static final RegistryObject<Item> BELZIUM_SWORD = ITEMS.register("belzium_sword",
            () -> new SwordItem(ModToolTiers.BELZIUM, 6, -2.4F,
                    new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(1)));

    public static final RegistryObject<Item> BELZIUM_DAGGER = ITEMS.register("belzium_dagger",
            () -> new SwordItem(ModToolTiers.BELZIUM, 2, -1.2F,
                    new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(1)));

    public static final RegistryObject<Item> BELZIUM_MACE = ITEMS.register("belzium_mace",
            () -> new SwordItem(ModToolTiers.BELZIUM, 6, -2.4F,
                    new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(1)));

    public static final RegistryObject<Item> BELZIUM_HAMMER = ITEMS.register("belzium_hammer",
            () -> new PickaxeItem(ModToolTiers.BELZIUM, 9, -3.2F,
                    new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(1)));

    /**
     * SPAWN EGG ITEMS
     */

    public static final RegistryObject<DragonSpawnEggItem> NIGHT_FURY_SPAWN_EGG = ITEMS.register("nightfury_spawn_egg", () -> new DragonSpawnEggItem(ModEntities.NIGHT_FURY, 0xffffff, 0xffffff,"nightfury_spawn_egg_info",  new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(64)));
    public static final RegistryObject<DragonSpawnEggItem> LIGHT_FURY_SPAWN_EGG = ITEMS.register("lightfury_spawn_egg", () -> new DragonSpawnEggItem(ModEntities.LIGHT_FURY, 0xffffff, 0xffffff,"lightfury_spawn_egg_info", new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(64)));

    public static final RegistryObject<DragonSpawnEggItem> NIGHT_LIGHT_SPAWN_EGG = ITEMS.register("nightlight_spawn_egg", () -> new DragonSpawnEggItem(ModEntities.NIGHT_LIGHT, 0xffffff, 0xffffff,"nightlight_spawn_egg_info",  new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(64)));
    public static final RegistryObject<DragonSpawnEggItem> TRIPLE_STRYKE_SPAWN_EGG = ITEMS.register("triple_stryke_spawn_egg", () -> new DragonSpawnEggItem(ModEntities.TRIPLE_STRYKE, 0xffffff, 0xffffff,"triple_stryke_spawn_egg_info", new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(64)));
    public static final RegistryObject<DragonSpawnEggItem> DEADLY_NADDER_SPAWN_EGG = ITEMS.register("deadly_nadder_spawn_egg", () -> new DragonSpawnEggItem(ModEntities.DEADLY_NADDER, 0xffffff, 0xffffff, "nadder_spawn_egg_info",new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(64)));
    public static final RegistryObject<DragonSpawnEggItem> GRONCKLE_SPAWN_EGG = ITEMS.register("gronckle_spawn_egg", () -> new DragonSpawnEggItem(ModEntities.GRONCKLE, 0xffffff, 0xffffff,"gronckle_spawn_egg_info", new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(64)));
    public static final RegistryObject<DragonSpawnEggItem> MONSTROUS_NIGHTMARE_SPAWN_EGG = ITEMS.register("monstrous_nightmare_spawn_egg", () -> new DragonSpawnEggItem(ModEntities.MONSTROUS_NIGHTMARE, 0xffffff, 0xffffff,"monstrous_nightmare_spawn_egg_info", new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(64)));
    public static final RegistryObject<DragonSpawnEggItem> ZIPPLEBACK_SPAWN_EGG = ITEMS.register("zippleback_spawn_egg", () -> new DragonSpawnEggItem(ModEntities.ZIPPLEBACK, 0xffffff, 0xffffff,"zippleback_spawn_egg_info", new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(64)));
    public static final RegistryObject<DragonSpawnEggItem> TERRIBLE_TERROR_SPAWN_EGG = ITEMS.register("terrible_terror_spawn_egg", () -> new DragonSpawnEggItem(ModEntities.TERRIBLE_TERROR, 0xffffff, 0xffffff,"terrible_terror_spawn_egg_info", new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(64)));
    public static final RegistryObject<DragonSpawnEggItem> SPEED_STINGER_LEADER_SPAWN_EGG = ITEMS.register("speed_stinger_leader_spawn_egg", () -> new DragonSpawnEggItem(ModEntities.SPEED_STINGER_LEADER, 0xffffff, 0xffffff,"speed_stinger_leader_spawn_egg_info", new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(64)));
    public static final RegistryObject<DragonSpawnEggItem> SPEED_STINGER_SPAWN_EGG = ITEMS.register("speed_stinger_spawn_egg", () -> new DragonSpawnEggItem(ModEntities.SPEED_STINGER, 0xffffff, 0xffffff,"speed_stinger_spawn_egg_info", new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(64)));
    public static final RegistryObject<DragonSpawnEggItem> STINGER_SPAWN_EGG = ITEMS.register("stinger_spawn_egg", () -> new DragonSpawnEggItem(ModEntities.STINGER, 0xffffff, 0xffffff,"stinger_spawn_egg_info", new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(64)));
    public static final RegistryObject<DragonSpawnEggItem> SKRILL_SPAWN_EGG = ITEMS.register("skrill_spawn_egg", () -> new DragonSpawnEggItem(ModEntities.SKRILL, 0xffffff, 0xffffff,"skrill_spawn_egg_info", new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(64)));

    /**
     * EGG ITEMS
     */

    // Small
    public static final RegistryObject<DragonEggItem> TERRIBLE_TERROR_EGG = ITEMS.register("terrible_terror_egg", () -> new DragonEggItem(new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(4), ModEntities.TERRIBLE_TERROR_EGG));
    public static final RegistryObject<DragonEggItem> SPEED_STINGER_EGG = ITEMS.register("speed_stinger_egg", () -> new DragonEggSeparateVariantItem(new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(4), ModEntities.SPEED_STINGER_EGG, 0));
    public static final RegistryObject<DragonEggItem> SPEED_STINGER_EGG_FLOUTSCOOUT = ITEMS.register("speed_stinger_egg_floutscout", () -> new DragonEggSeparateVariantItem(new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(4), ModEntities.SPEED_STINGER_EGG, 1));
    public static final RegistryObject<DragonEggItem> SPEED_STINGER_EGG_ICE_BREAKER = ITEMS.register("speed_stinger_egg_ice_breaker", () -> new DragonEggSeparateVariantItem(new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(4), ModEntities.SPEED_STINGER_EGG, 2));
    public static final RegistryObject<DragonEggItem> SPEED_STINGER_EGG_SWEET_STING = ITEMS.register("speed_stinger_egg_sweet_sting", () -> new DragonEggSeparateVariantItem(new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(4), ModEntities.SPEED_STINGER_EGG, 3));

    // Medium
    public static final RegistryObject<DragonEggItem> NADDER_EGG = ITEMS.register("nadder_egg", () -> new DragonEggItem(new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(4), ModEntities.NADDER_EGG));
    public static final RegistryObject<DragonEggItem> NIGHT_FURY_EGG = ITEMS.register("night_fury_egg", () -> new DragonEggItem(new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(4), ModEntities.NIGHT_FURY_EGG));

    public static final RegistryObject<DragonEggItem> NIGHT_LIGHT_EGG = ITEMS.register("night_light_egg", () -> new DragonEggItem(new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(4), ModEntities.NIGHT_LIGHT_EGG));
    public static final RegistryObject<DragonEggItem> LIGHT_FURY_EGG = ITEMS.register("light_fury_egg", () -> new DragonEggItem(new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(4), ModEntities.LIGHT_FURY_EGG));
    public static final RegistryObject<DragonEggItem> GRONCKLE_EGG = ITEMS.register("gronckle_egg", () -> new DragonEggItem(new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(4), ModEntities.GRONCKLE_EGG));
    public static final RegistryObject<DragonEggItem> TRIPLE_STRYKE_EGG = ITEMS.register("triple_stryke_egg", () -> new DragonEggItem(new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(4), ModEntities.TRIPLE_STRYKE_EGG));
    public static final RegistryObject<DragonEggItem> SKRILL_EGG = ITEMS.register("skrill_egg", () -> new DragonEggItem(new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(4), ModEntities.SKRILL_EGG));

    // Large
    public static final RegistryObject<DragonEggItem> STINGER_EGG = ITEMS.register("stinger_egg", () -> new DragonEggItem(new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(4), ModEntities.STINGER_EGG));
    public static final RegistryObject<DragonEggItem> ZIPPLEBACK_EGG = ITEMS.register("zippleback_egg", () -> new DragonEggItem(new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(4), ModEntities.ZIPPLEBACK_EGG));
    public static final RegistryObject<DragonEggItem> MONSTROUS_NIGHTMARE_EGG = ITEMS.register("monstrous_nightmare_egg", () -> new DragonEggItem(new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(4), ModEntities.M_NIGHTMARE_EGG));

}
