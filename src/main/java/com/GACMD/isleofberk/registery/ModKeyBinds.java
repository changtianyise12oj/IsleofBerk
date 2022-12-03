package com.GACMD.isleofberk.registery;

import com.GACMD.isleofberk.IsleofBerk;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
public class ModKeyBinds {

    public static KeyMapping keyDown;
    public static KeyMapping keyAbilty;
    public static KeyMapping keySecondAbilty;

    public static void register(FMLClientSetupEvent event) {
        keyDown = create("keyDown", GLFW.GLFW_KEY_LEFT_ALT);
        keyAbilty = create("keyAbility", GLFW.GLFW_KEY_R);
        keySecondAbilty = create("keySpecialAbility", GLFW.GLFW_KEY_X);

        ClientRegistry.registerKeyBinding(keyDown);
        ClientRegistry.registerKeyBinding(keyAbilty);
        ClientRegistry.registerKeyBinding(keySecondAbilty);

    }

    private static KeyMapping create(String name, int key) {
        return new KeyMapping("key." + IsleofBerk.MOD_ID + "." + name, key, "key.category." + IsleofBerk.MOD_ID);
    }

}
