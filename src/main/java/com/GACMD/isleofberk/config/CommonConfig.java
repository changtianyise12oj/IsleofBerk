package com.GACMD.isleofberk.config;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Boolean> USE_LARGER_SCALING;

    static {
        BUILDER.push("Isle of Berk Config");

        USE_LARGER_SCALING = BUILDER.comment(new TranslatableComponent("Should use larger dragon scales?").toString()).define(new TranslatableComponent("Should use larger dragon scales?").toString(), true);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
