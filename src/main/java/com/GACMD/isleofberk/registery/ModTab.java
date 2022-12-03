package com.GACMD.isleofberk.registery;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModTab {

    public static final CreativeModeTab IOB_TAB = new CreativeModeTab("isleofberk") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.SPEED_STINGER_SPAWN_EGG.get());
        }
    };
}