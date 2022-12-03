package com.GACMD.isleofberk.registery;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.client.gui.DragonContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModContainerTypes {

    public static final DeferredRegister<MenuType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, IsleofBerk.MOD_ID);

    public static final RegistryObject<MenuType<DragonContainerMenu>> DRAGON_INV = CONTAINER_TYPES.register("dragon_inv",
            () -> IForgeMenuType.create((windowId, inv, data) -> new DragonContainerMenu(windowId, inv, data.readInt())));
}
