package com.GACMD.isleofberk.network.message.util;

import com.GACMD.isleofberk.network.message.MessageShockParticle;
import com.GACMD.isleofberk.registery.ModParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class ClientPacketHandlerClass {
    public static void handleSpawnShockParticles(MessageShockParticle msg, Supplier<NetworkEvent.Context> ctx) {
        Random rand = new Random();
        for (int i = 0; i < 2; i++) {
            if(Minecraft.getInstance().level != null) {
                Entity entity = Minecraft.getInstance().level.getEntity(msg.id);
                if(entity != null) {
                    float rangeMod = 0.8F;
                    // Lightning Particles
                    double posX = entity.getX() - ((entity.getBbWidth() + rangeMod) / 2F) + ((entity.getBbWidth() + rangeMod) * rand.nextFloat());
                    double posY = entity.getY() + entity.getBbHeight() * rand.nextFloat();
                    double posZ = entity.getZ() - ((entity.getBbWidth() + rangeMod) / 2F) + ((entity.getBbWidth() + rangeMod) * rand.nextFloat());
                    Minecraft.getInstance().level.addParticle(ModParticles.SKRILL_SKILL_PARTICLES.get(), posX, posY, posZ, 0, 0, 0);
                    // Electric Spark Particles
                    posX = entity.getX() - ((entity.getBbWidth() + rangeMod) / 2F) + ((entity.getBbWidth() + rangeMod) * rand.nextFloat());
                    posY = entity.getY() + entity.getBbHeight() * rand.nextFloat();
                    posZ = entity.getZ() - ((entity.getBbWidth() + rangeMod) / 2F) + ((entity.getBbWidth() + rangeMod) * rand.nextFloat());
                    Minecraft.getInstance().level.addParticle(ParticleTypes.ELECTRIC_SPARK, posX, posY, posZ, 0, 0, 0);
                }
            }
        }
    }
}