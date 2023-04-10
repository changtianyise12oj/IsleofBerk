package com.GACMD.isleofberk.config.configs;

import com.GACMD.isleofberk.config.util.ConfigHelper;
import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {

    public ConfigHelper.ConfigValueListener<Boolean> transitionToggle;

    public ClientConfig(ForgeConfigSpec.Builder builder, ConfigHelper.Subscriber subscriber)
    {
        builder.comment(" Isle of Berk Main Config").push("Animation fix for shaders");
        transitionToggle = subscriber.subscribe(builder
                .comment(" Set to true if you experience issues with animations when using shaders")
                .define("disable_transition_ticks", false));
        builder.pop();
    }
}
