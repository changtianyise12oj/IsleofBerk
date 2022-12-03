package com.GACMD.isleofberk.registery;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.common.items.DragonEggItem;
import com.GACMD.isleofberk.common.items.DragonSpawnEggItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems {

    private ModItems() {
    }

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, IsleofBerk.MOD_ID);

    /**
     * ORES
     */

    public static final RegistryObject<Item> BELZIUM = ITEMS.register("belzium", () ->
            new Item(new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(64)));

    /**
     * SPAWN EGG ITEMS
     */

    public static final RegistryObject<DragonSpawnEggItem> NIGHT_FURY_SPAWN_EGG = ITEMS.register("nightfury_spawn_egg", () ->
            new DragonSpawnEggItem(ModEntities.NIGHT_FURY, 0xffffff, 0xffffff,"nightfury_spawn_egg_info",  new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(64)));

    public static final RegistryObject<DragonSpawnEggItem> TRIPLE_STRYKE_SPAWN_EGG = ITEMS.register("triple_stryke_spawn_egg", () ->
            new DragonSpawnEggItem(ModEntities.TRIPLE_STRYKE, 0xffffff, 0xffffff,"triple_stryke_spawn_egg_info", new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(64)));

    public static final RegistryObject<DragonSpawnEggItem> STINGER_SPAWN_EGG = ITEMS.register("stinger_spawn_egg", () ->
            new DragonSpawnEggItem(ModEntities.STINGER, 0xffffff, 0xffffff,"stinger_spawn_egg_info", new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(64)));

    public static final RegistryObject<DragonSpawnEggItem> SPEED_STINGER_SPAWN_EGG = ITEMS.register("speed_stinger_spawn_egg", () ->
            new DragonSpawnEggItem(ModEntities.SPEED_STINGER, 0xffffff, 0xffffff,"speed_stinger_spawn_egg_info", new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(64)));

    public static final RegistryObject<DragonSpawnEggItem> SPEED_STINGER_LEADER_SPAWN_EGG = ITEMS.register("speed_stinger_leader_spawn_egg", () ->
            new DragonSpawnEggItem(ModEntities.SPEED_STINGER_LEADER, 0xffffff, 0xffffff,"speed_stinger_leader_spawn_egg_info", new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(64)));

    public static final RegistryObject<DragonSpawnEggItem> TERRIBLE_TERROR_SPAWN_EGG = ITEMS.register("terrible_terror_spawn_egg", () ->
            new DragonSpawnEggItem(ModEntities.TERRIBLE_TERROR, 0xffffff, 0xffffff,"terrible_terror_spawn_egg_info", new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(64)));

    public static final RegistryObject<DragonSpawnEggItem> DEADLY_NADDER_SPAWN_EGG = ITEMS.register("deadly_nadder_spawn_egg", () ->
            new DragonSpawnEggItem(ModEntities.DEADLY_NADDER, 0xffffff, 0xffffff, "nadder_spawn_egg_info",new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(64)));

    public static final RegistryObject<DragonSpawnEggItem> GRONCKLE_SPAWN_EGG = ITEMS.register("gronckle_spawn_egg", () ->
            new DragonSpawnEggItem(ModEntities.GRONCKLE, 0xffffff, 0xffffff,"gronckle_spawn_egg_info", new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(64)));

    /**
     * EGG ITEMS
     */

    // Small
    public static final RegistryObject<DragonEggItem> TERRIBLE_TERROR_EGG = ITEMS.register("terrible_terror_egg", () -> new DragonEggItem(new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(4), ModEntities.TERRIBLE_TERROR_EGG));
    public static final RegistryObject<DragonEggItem> SPEED_STINGER_EGG = ITEMS.register("speed_stinger_egg", () -> new DragonEggItem(new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(4), ModEntities.SPEED_STINGER_EGG, 0));
    public static final RegistryObject<DragonEggItem> SPEED_STINGER_EGG_FLOUTSCOOUT = ITEMS.register("speed_stinger_egg_floutscout", () -> new DragonEggItem(new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(4), ModEntities.SPEED_STINGER_EGG, 1));
    public static final RegistryObject<DragonEggItem> SPEED_STINGER_EGG_ICE_BREAKER = ITEMS.register("speed_stinger_egg_ice_breaker", () -> new DragonEggItem(new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(4), ModEntities.SPEED_STINGER_EGG, 2));
    public static final RegistryObject<DragonEggItem> SPEED_STINGER_EGG_SWEET_STING = ITEMS.register("speed_stinger_egg_sweet_sting", () -> new DragonEggItem(new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(4), ModEntities.SPEED_STINGER_EGG, 3));

    // Medium
    public static final RegistryObject<DragonEggItem> NADDER_EGG = ITEMS.register("nadder_egg", () -> new DragonEggItem(new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(4), ModEntities.NADDER_EGG));
    public static final RegistryObject<DragonEggItem> NIGHT_FURY_EGG = ITEMS.register("night_fury_egg", () -> new DragonEggItem(new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(4), ModEntities.NIGHT_FURY_EGG));
    public static final RegistryObject<DragonEggItem> LIGHT_FURY_EGG = ITEMS.register("light_fury_egg", () -> new DragonEggItem(new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(4), ModEntities.LIGHT_FURY_EGG));
    public static final RegistryObject<DragonEggItem> GRONCKLE_EGG = ITEMS.register("gronckle_egg", () -> new DragonEggItem(new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(4), ModEntities.GRONCKLE_EGG));
    public static final RegistryObject<DragonEggItem> TRIPLE_STRYKE_EGG = ITEMS.register("triple_stryke_egg", () -> new DragonEggItem(new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(4), ModEntities.TRIPLE_STRYKE_EGG));

    // Large
    public static final RegistryObject<DragonEggItem> STINGER_EGG = ITEMS.register("stinger_egg", () -> new DragonEggItem(new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(4), ModEntities.STINGER_EGG));
    public static final RegistryObject<DragonEggItem> ZIPPLEBACK_EGG = ITEMS.register("zippleback_egg", () -> new DragonEggItem(new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(4), ModEntities.ZIPPLEBACK_EGG));
    public static final RegistryObject<DragonEggItem> MONSTROUS_NIGHTMARE_EGG = ITEMS.register("monstrous_nightmare_egg", () -> new DragonEggItem(new Item.Properties().tab(ModTab.IOB_TAB).stacksTo(4), ModEntities.M_NIGHTMARE_EGG));

}
