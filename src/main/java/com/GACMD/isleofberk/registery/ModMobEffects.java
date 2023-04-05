package com.GACMD.isleofberk.registery;

import com.GACMD.isleofberk.IsleofBerk;
import com.GACMD.isleofberk.effects.ShockEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMobEffects
{
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, IsleofBerk.MOD_ID);

    public static final RegistryObject<MobEffect> SHOCK = EFFECTS.register("shock", () -> new ShockEffect(MobEffectCategory.HARMFUL, 0x5454ff).addAttributeModifier(Attributes.MOVEMENT_SPEED, "d040fbfe-d9ca-4645-8dff-94f880c96cb0", -0.1F, AttributeModifier.Operation.MULTIPLY_TOTAL));
}