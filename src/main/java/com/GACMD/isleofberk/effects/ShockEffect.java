package com.GACMD.isleofberk.effects;

import com.GACMD.isleofberk.network.ControlNetwork;
import com.GACMD.isleofberk.network.message.MessageShockParticle;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.PacketDistributor;

public class ShockEffect extends MobEffect {
    public ShockEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if(!livingEntity.getLevel().isClientSide())
            if (!livingEntity.isInvulnerableTo(DamageSource.ON_FIRE) && livingEntity.tickCount % 10 == 0) {
                // Spawns Particles
                ControlNetwork.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> livingEntity), new MessageShockParticle(livingEntity.getId()));
                // Applies Damage
                if (!livingEntity.hasEffect(MobEffects.FIRE_RESISTANCE))
                    livingEntity.hurt(DamageSource.LIGHTNING_BOLT, 1F);
            }
        super.applyEffectTick(livingEntity, amplifier);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}